package com.akgarg.springframework.logger.exception;

/**
 * @author Akhilesh Garg
 * @since 18-03-2023
 */
public class IllegalOperationException extends RuntimeException {

    public IllegalOperationException(final String message) {
        super(message);
    }

}
