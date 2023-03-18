package com.akgarg.springframework.bean.exception;

/**
 * @author Akhilesh Garg
 * @since 26-02-2023
 */
public class NoSuchBeanDefinitionException extends RuntimeException {

    public NoSuchBeanDefinitionException(final String message) {
        super(message);
    }

}
