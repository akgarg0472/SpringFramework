package com.akgarg.springframework.logger.exception;

/**
 * @author Akhilesh Garg
 * @since 18-03-2023
 */
public class LogFactoryException extends RuntimeException {

    public LogFactoryException(final String message, final Throwable throwable) {
        super(message, throwable);
    }

}
