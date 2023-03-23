package com.akgarg.springframework.bean.exception;

/**
 * @author Akhilesh Garg
 * @since 23-03-2023
 */
public class BeanDefinitionHolderCreatorException extends RuntimeException {

    public BeanDefinitionHolderCreatorException(final String message, final Throwable throwable) {
        super(message, throwable);
    }

}
