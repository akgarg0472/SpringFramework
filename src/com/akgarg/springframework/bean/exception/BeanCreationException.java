package com.akgarg.springframework.bean.exception;

/**
 * @author Akhilesh Garg
 * @since 28-02-2023
 */
public class BeanCreationException extends RuntimeException {

    public BeanCreationException(final String message) {
        super(message);
    }

    public BeanCreationException(final String message, final Throwable throwable) {
        super(message, throwable);
    }

}
