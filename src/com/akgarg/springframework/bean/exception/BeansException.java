package com.akgarg.springframework.bean.exception;

/**
 * @author Akhilesh Garg
 * @since 26-02-2023
 */
public class BeansException extends RuntimeException {

    public BeansException(final String message) {
        super(message);
    }

}
