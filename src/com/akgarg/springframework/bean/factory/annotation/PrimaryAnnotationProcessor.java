package com.akgarg.springframework.bean.factory.annotation;

import com.akgarg.springframework.bean.exception.InvalidBeanResolverTypeException;
import com.akgarg.springframework.bean.factory.BeanDefinition;
import com.akgarg.springframework.bean.factory.BeanResolverMetadata;
import com.akgarg.springframework.context.AnnotationProcessor;
import com.akgarg.springframework.context.AnnotationProcessorMetadata;
import com.akgarg.springframework.util.Assert;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * @author Akhilesh Garg
 * @since 28-02-2023
 */
public final class PrimaryAnnotationProcessor implements AnnotationProcessor {

    @Override
    public void process(final AnnotationProcessorMetadata metadata) {
        Assert.notNull(metadata, "PrimaryAnnotationProcessor metadata can't be null");
        Assert.notNull(metadata.getBeanDefinition(), "PrimaryAnnotationProcessor BeanDefinition can't be null");
        Assert.nonEmpty(metadata.getResolveType(), "PrimaryAnnotationProcessor resolveType is invalid " + metadata.getResolveType());

        this.doProcess(metadata);
    }

    private void doProcess(final AnnotationProcessorMetadata metadata) {
        final String resolveType = metadata.getResolveType();

        if (isResolveTypeValid(resolveType)) {
            doResolve(metadata);
        } else {
            throw new IllegalStateException("Invalid bean resolver type provided " + resolveType);
        }
    }

    private void doResolve(final AnnotationProcessorMetadata metadata) {
        final BeanDefinition beanDefinition = metadata.getBeanDefinition();
        final String resolveType = metadata.getResolveType();

        Assert.notNull(beanDefinition, "AnnotationProcessor BeanDefinition can't be null");
        Assert.nonEmpty(resolveType, "AnnotationProcessor resolve type is invalid");

        final Annotation annotation;

        if (BeanResolverMetadata.RESOLVE_METHOD_TYPE.equals(resolveType)) {
            final Method method = metadata.getMethod();
            Assert.notNull(method, "AnnotationProcessor Method can't be null");
            annotation = method.getAnnotation(Primary.class);
        } else if (BeanResolverMetadata.RESOLVE_CLASS_TYPE.equals(resolveType)) {
            final Object instance = metadata.getInstance();
            Assert.notNull(instance, "AnnotationProcessor Bean can't be null");
            annotation = instance.getClass().getAnnotation(Primary.class);
        } else {
            throw new InvalidBeanResolverTypeException("Invalid bean resolver type " + resolveType);
        }

        if (annotation != null) {
            beanDefinition.setPrimary(true);
        }
    }

    private boolean isResolveTypeValid(final String resolveType) {
        return BeanResolverMetadata.RESOLVE_METHOD_TYPE.equals(resolveType) || BeanResolverMetadata.RESOLVE_CLASS_TYPE.equals(resolveType);
    }

}
