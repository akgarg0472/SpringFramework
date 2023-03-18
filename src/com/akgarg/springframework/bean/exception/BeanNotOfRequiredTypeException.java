package com.akgarg.springframework.bean.exception;

/**
 * @author Akhilesh Garg
 * @since 28-02-2023
 */
public class BeanNotOfRequiredTypeException extends RuntimeException {

    public BeanNotOfRequiredTypeException(final String message) {
        super(message);
    }

}
