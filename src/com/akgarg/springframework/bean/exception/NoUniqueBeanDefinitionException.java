package com.akgarg.springframework.bean.exception;

/**
 * @author Akhilesh Garg
 * @since 26-02-2023
 */
public class NoUniqueBeanDefinitionException extends RuntimeException {

    public NoUniqueBeanDefinitionException(final String message) {
        super(message);
    }

}
