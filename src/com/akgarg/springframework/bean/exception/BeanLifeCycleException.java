package com.akgarg.springframework.bean.exception;

/**
 * @author Akhilesh Garg
 * @since 18-03-2023
 */
public class BeanLifeCycleException extends RuntimeException {

    public BeanLifeCycleException(final String message, final Throwable throwable) {
        super(message, throwable);
    }

}
