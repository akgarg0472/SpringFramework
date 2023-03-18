package com.akgarg.springframework.bean.factory;

import java.lang.reflect.Method;

/**
 * @author Akhilesh Garg
 * @since 01-03-2023
 */
public interface BeanResolverMetadata {

    String RESOLVE_METHOD_TYPE = "METHOD";
    String RESOLVE_CLASS_TYPE = "CLASS";

    String getResolveType();

    Object getInstance();

    Method getMethod();

    String getBeanName();

    String getBeanInitMethod();

    boolean isAutowiredCandidate();

}
