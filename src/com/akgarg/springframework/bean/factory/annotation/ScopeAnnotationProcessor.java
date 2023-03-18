package com.akgarg.springframework.bean.factory.annotation;

import com.akgarg.springframework.bean.factory.BeanDefinition;
import com.akgarg.springframework.bean.factory.BeanFactory;
import com.akgarg.springframework.bean.factory.BeanResolverMetadata;
import com.akgarg.springframework.context.AnnotationProcessor;
import com.akgarg.springframework.context.AnnotationProcessorMetadata;
import com.akgarg.springframework.logger.Logger;
import com.akgarg.springframework.logger.support.LogFactory;
import com.akgarg.springframework.util.Assert;
import com.akgarg.springframework.util.StringUtils;

import java.lang.reflect.Method;

/**
 * @author Akhilesh Garg
 * @since 28-02-2023
 */
public final class ScopeAnnotationProcessor implements AnnotationProcessor {

    private static final Logger logger = LogFactory.getDefaultLogger();

    @Override
    public void process(final AnnotationProcessorMetadata metadata) {
        Assert.notNull(metadata, "ScopeAnnotationProcessor metadata can't be null");
        Assert.notNull(metadata.getBeanDefinition(), "ScopeAnnotationProcessor BeanDefinition can't be null");
        Assert.nonEmpty(metadata.getResolveType(), "ScopeAnnotationProcessor resolveType is invalid " + metadata.getResolveType());
        this.doProcess(metadata);
    }

    private void doProcess(final AnnotationProcessorMetadata metadata) {
        final String resolveType = metadata.getResolveType();

        if (isBeanResolveTypeValid(resolveType)) {
            doResolve(metadata);
        } else {
            throw new IllegalStateException("Invalid bean resolver type provided " + resolveType);
        }
    }

    private void doResolve(final AnnotationProcessorMetadata metadata) {
        final BeanDefinition beanDefinition = metadata.getBeanDefinition();
        Assert.notNull(beanDefinition, "AnnotationProcessor BeanDefinition can't be null");

        final String beanName = beanDefinition.getBeanName();
        final String beanType = beanDefinition.getBean().getClass().getName();
        final Scope annotation;

        logger.debug(ScopeAnnotationProcessor.class, "Resolving for bean=" + beanDefinition.getBeanName() + " and for type=" + beanType);

        if (BeanResolverMetadata.RESOLVE_METHOD_TYPE.equals(metadata.getResolveType())) {
            final Method method = metadata.getMethod();
            Assert.notNull(method, "AnnotationProcessor Method can't be null");
            annotation = method.getAnnotation(Scope.class);
        } else {
            final Object instance = metadata.getInstance();
            Assert.notNull(instance, "AnnotationProcessor Bean can't be null");
            annotation = instance.getClass().getAnnotation(Scope.class);
        }

        if (annotation != null) {
            final String scope = annotation.name();

            if (StringUtils.isNonBlankString(scope)) {
                if (!isScopeValid(scope)) {
                    throw new IllegalStateException("Invalid scope value provided " + scope);
                }

                if (BeanFactory.SCOPE_PROTOTYPE.equals(scope)) {
                    logger.trace(ScopeAnnotationProcessor.class, "Marking bean=" + beanName + " of type=" + beanType + " as singleton=false and prototype=true");
                    beanDefinition.setSingleton(false);
                    beanDefinition.setPrototype(true);
                } else {
                    logger.trace(getClass(), "Marking bean=" + beanName + " of type=" + beanType + " as singleton=true and prototype=false");
                    beanDefinition.setSingleton(true);
                    beanDefinition.setPrototype(false);
                }

                beanDefinition.setScope(scope);
            }
        }
    }

    private boolean isBeanResolveTypeValid(final String resolveType) {
        return BeanResolverMetadata.RESOLVE_METHOD_TYPE.equals(resolveType) ||
                BeanResolverMetadata.RESOLVE_CLASS_TYPE.equals(resolveType);
    }

    private boolean isScopeValid(final String scope) {
        return BeanFactory.SCOPE_PROTOTYPE.equals(scope) ||
                BeanFactory.SCOPE_SINGLETON.equals(scope);
    }

}
