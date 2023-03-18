package com.akgarg.springframework.context;

import com.akgarg.springframework.bean.factory.BeanDefinition;

import java.lang.reflect.Method;

/**
 * @author Akhilesh Garg
 * @since 01-03-2023
 */
public interface AnnotationProcessorMetadata {

    BeanDefinition getBeanDefinition();

    Object getInstance();

    Method getMethod();

    String getResolveType();

}
