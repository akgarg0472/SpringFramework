package com.akgarg.springframework.util;

import com.akgarg.springframework.bean.factory.annotation.Bean;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author Akhilesh Garg
 * @since 26-02-2023
 */
public final class ReflectionUtils {

    private ReflectionUtils() {
        throw new UnsupportedOperationException();
    }

    public static Object createInstanceForClass(final Class<?> clazz) {
        try {
            return clazz.newInstance();
        } catch (Exception e) {
            return null;
        }
    }

    public static Object invokeMethod(
            final Object instance, final Method method
    ) throws InvocationTargetException, IllegalAccessException {
        method.setAccessible(true);
        return method.invoke(instance);
    }

    public static boolean isMethodBeanCandidate(final Method method) {
        return method.getAnnotation(Bean.class) != null;
    }

}
