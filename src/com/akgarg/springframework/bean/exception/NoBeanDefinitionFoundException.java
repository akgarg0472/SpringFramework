package com.akgarg.springframework.bean.exception;

/**
 * @author Akhilesh Garg
 * @since 26-02-2023
 */
public class NoBeanDefinitionFoundException extends RuntimeException {

    public NoBeanDefinitionFoundException(final String message) {
        super(message);
    }

}
