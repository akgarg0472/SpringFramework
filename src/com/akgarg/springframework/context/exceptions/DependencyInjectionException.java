package com.akgarg.springframework.context.exceptions;

/**
 * @author Akhilesh Garg
 * @since 01-03-2023
 */
public class DependencyInjectionException extends RuntimeException {

    public DependencyInjectionException(final String message, final Throwable throwable) {
        super(message, throwable);
    }

}
