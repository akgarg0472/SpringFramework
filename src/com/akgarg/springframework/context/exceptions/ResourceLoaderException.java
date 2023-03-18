package com.akgarg.springframework.context.exceptions;

/**
 * @author Akhilesh Garg
 * @since 02-03-2023
 */
public class ResourceLoaderException extends RuntimeException {

    public ResourceLoaderException(final String message, final Throwable throwable) {
        super(message, throwable);
    }

}
