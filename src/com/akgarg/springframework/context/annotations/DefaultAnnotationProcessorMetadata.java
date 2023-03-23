package com.akgarg.springframework.context.annotations;

import com.akgarg.springframework.bean.factory.BeanDefinition;
import com.akgarg.springframework.bean.factory.BeanResolverMetadata;
import com.akgarg.springframework.context.AnnotationProcessorMetadata;
import com.akgarg.springframework.util.Assert;

import java.lang.reflect.Method;

/**
 * @author Akhilesh Garg
 * @since 28-02-2023
 */
public final class DefaultAnnotationProcessorMetadata implements AnnotationProcessorMetadata {

    private final BeanDefinition beanDefinition;
    private final Object instance;
    private final Method method;
    private final String resolveType;

    private DefaultAnnotationProcessorMetadata(
            final BeanResolverMetadata metadata,
            final BeanDefinition beanDefinition
    ) {
        Assert.notNull(metadata, "Metadata can't be empty");
        this.instance = metadata.getInstance();
        this.method = metadata.getMethod();
        this.resolveType = metadata.getResolveType();
        this.beanDefinition = beanDefinition;
    }

    public static DefaultAnnotationProcessorMetadata of(
            final BeanResolverMetadata metadata, final BeanDefinition definition
    ) {
        return new DefaultAnnotationProcessorMetadata(metadata, definition);
    }

    @Override
    public BeanDefinition getBeanDefinition() {
        return beanDefinition;
    }

    @Override
    public Object getInstance() {
        return instance;
    }

    @Override
    public Method getMethod() {
        return method;
    }

    @Override
    public String getResolveType() {
        return resolveType;
    }

}
