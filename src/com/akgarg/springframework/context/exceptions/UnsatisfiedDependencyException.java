package com.akgarg.springframework.context.exceptions;

/**
 * @author Akhilesh Garg
 * @since 01-03-2023
 */
public class UnsatisfiedDependencyException extends RuntimeException {

    public UnsatisfiedDependencyException(final String message) {
        super(message);
    }

    public UnsatisfiedDependencyException(final String message, final Exception throwable) {
        super(message, throwable);
    }

}
