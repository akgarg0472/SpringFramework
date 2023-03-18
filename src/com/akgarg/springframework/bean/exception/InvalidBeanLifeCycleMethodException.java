package com.akgarg.springframework.bean.exception;

/**
 * @author Akhilesh Garg
 * @since 18-03-2023
 */
public class InvalidBeanLifeCycleMethodException extends RuntimeException {

    public InvalidBeanLifeCycleMethodException(final String message) {
        super(message);
    }

}
