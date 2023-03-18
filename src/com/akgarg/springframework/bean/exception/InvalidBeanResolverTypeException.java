package com.akgarg.springframework.bean.exception;

/**
 * @author Akhilesh Garg
 * @since 01-03-2023
 */
public class InvalidBeanResolverTypeException extends RuntimeException {

    public InvalidBeanResolverTypeException(final String message) {
        super(message);
    }

}
