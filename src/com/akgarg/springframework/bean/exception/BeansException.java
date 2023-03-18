package com.akgarg.springframework.bean.exception;

/**
 * @author Akhilesh Garg
 * @since 26-02-2023
 */
public class BeansException extends RuntimeException {

    public BeansException(final String message) {
        super(message);
    }

    public BeansException(final String message, final Throwable throwable) {
        super(message, throwable);
    }

}
