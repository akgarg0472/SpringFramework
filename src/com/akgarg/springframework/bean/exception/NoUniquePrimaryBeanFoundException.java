package com.akgarg.springframework.bean.exception;

/**
 * @author Akhilesh Garg
 * @since 28-02-2023
 */
public class NoUniquePrimaryBeanFoundException extends RuntimeException {

    public NoUniquePrimaryBeanFoundException(final String message) {
        super(message);
    }

}
