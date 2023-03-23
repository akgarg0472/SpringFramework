package com.akgarg.springframework.bean.support;

import com.akgarg.springframework.bean.exception.BeanCreationException;
import com.akgarg.springframework.bean.factory.BeanDefinition;
import com.akgarg.springframework.bean.factory.BeanDefinitionResolver;
import com.akgarg.springframework.bean.factory.BeanResolverMetadata;
import com.akgarg.springframework.bean.factory.annotation.PrimaryAnnotationProcessor;
import com.akgarg.springframework.bean.factory.annotation.ScopeAnnotationProcessor;
import com.akgarg.springframework.context.AnnotationProcessor;
import com.akgarg.springframework.context.annotations.DefaultAnnotationProcessorMetadata;
import com.akgarg.springframework.logger.Logger;
import com.akgarg.springframework.logger.support.LogFactory;
import com.akgarg.springframework.util.Assert;
import com.akgarg.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;

/**
 * @author Akhilesh Garg
 * @since 27-02-2023
 */
public final class BeanAnnotationBeanDefinitionResolver implements BeanDefinitionResolver {

    private static final Logger logger = LogFactory.getDefaultLogger();
    private final Collection<AnnotationProcessor> annotationProcessors;

    public BeanAnnotationBeanDefinitionResolver() {
        this.annotationProcessors = new ArrayList<>();
        this.initAnnotationProcessors();
        Assert.nonEmpty(this.annotationProcessors, "AnnotationProcessors can't be empty");
    }

    @Override
    public BeanDefinition resolve(final BeanResolverMetadata metadata) {
        final Object instance = metadata.getInstance();
        final Method method = metadata.getMethod();
        final String beanName = metadata.getBeanName();
        final String resolveType = metadata.getResolveType();
        final String initMethod = metadata.getBeanInitMethod();
        final String destroyMethod = metadata.getBeanDestroyMethod();
        final boolean autowiredCandidate = metadata.isAutowiredCandidate();

        logger.debug(
                BeanAnnotationBeanDefinitionResolver.class,
                "Resolving @Bean for bean=" + beanName + " for resolve type=" + resolveType
        );

        try {
            Object bean;

            if (BeanResolverMetadata.RESOLVE_METHOD_TYPE.equals(resolveType)) {
                bean = ReflectionUtils.invokeMethod(instance, method);
            } else {
                bean = instance;
            }

            if (bean == null) {
                bean = NullBean.getInstance();
            }

            final BeanDefinition definition = new DefaultBeanDefinition();
            definition.setBeanName(beanName);
            definition.setBean(bean);
            definition.setBeanInitMethod(initMethod);
            definition.setBeanDestroyMethod(destroyMethod);
            definition.setAutowireCandidate(autowiredCandidate);

            for (final AnnotationProcessor processor : annotationProcessors) {
                final DefaultAnnotationProcessorMetadata annotationProcessorMetadata = DefaultAnnotationProcessorMetadata.of(
                        metadata,
                        definition
                );

                processor.process(annotationProcessorMetadata);
            }

            return definition;

        } catch (Exception e) {
            throw new BeanCreationException("Error creating bean with name '" + beanName + "'", e);
        }
    }

    private void initAnnotationProcessors() {
        this.annotationProcessors.add(new ScopeAnnotationProcessor());
        this.annotationProcessors.add(new PrimaryAnnotationProcessor());
    }

}
