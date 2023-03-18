package com.akgarg.springframework.util;

import com.akgarg.springframework.logger.Logger;
import com.akgarg.springframework.logger.support.LogFactory;

/**
 * @author Akhilesh Garg
 * @since 02-03-2023
 */
public final class ClassLoaderUtils {

    private static final Logger logger = LogFactory.getDefaultLogger();

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
            logger.fatal(ClassLoaderUtils.class, "Exception occurred while obtaining ClassLoader");
        }

        return classLoader;
    }

}
