package com.akgarg.springframework.util;

import java.util.Collection;

/**
 * @author Akhilesh Garg
 * @since 26-02-2023
 */
public class Assert {

    public static void notNull(final Object object, final String message) {
        if (object == null) {
            throw new NullPointerException(message);
        }
    }

    public static void nonEmpty(final String string, final String message) {
        notNull(string, message);

        if (string.trim().isEmpty()) {
            throw new IllegalStateException(message);
        }
    }

    public static void nonEmpty(final Collection<?> collection, final String message) {
        notNull(collection, message);

        if (collection.isEmpty()) {
            throw new IllegalStateException(message);
        }
    }

}
