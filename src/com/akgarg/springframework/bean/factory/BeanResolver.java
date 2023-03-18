package com.akgarg.springframework.bean.factory;

import java.lang.reflect.Method;

/**
 * @author Akhilesh Garg
 * @since 27-02-2023
 */
public interface BeanResolver {

    String resolveName(Method method);

    String resolveName(Class<?> clazz);

    String resolveInitMethod(Method method);

    boolean resolveAutowiredCandidate(Method method);

}
