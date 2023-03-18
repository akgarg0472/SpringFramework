package com.akgarg.springframework.util;

/**
 * @author Akhilesh Garg
 * @since 02-03-2023
 */
public final class ClassLoaderUtils {

    private ClassLoaderUtils() {
        throw new UnsupportedOperationException();
    }

    public static ClassLoader getClassLoader() {
        ClassLoader classLoader = null;

        try {
            classLoader = Thread.currentThread().getContextClassLoader();

            if (classLoader == null) {
                classLoader = ClassLoaderUtils.getClassLoader();

                if (classLoader == null) {
                    classLoader = ClassLoader.getSystemClassLoader();
                }
            }

        } catch (Throwable e) {
            // unable to find class loader
        }

        return classLoader;
    }

}
