package com.akgarg.springframework.logger.exception;

/**
 * @author Akhilesh Garg
 * @since 18-03-2023
 */
public class InvalidLogLevelException extends RuntimeException {

    public InvalidLogLevelException(final String message) {
        super(message);
    }

}
