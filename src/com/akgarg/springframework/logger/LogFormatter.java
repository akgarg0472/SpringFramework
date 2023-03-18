package com.akgarg.springframework.logger;

/**
 * @author Akhilesh Garg
 * @since 18-03-2023
 */
public interface LogFormatter {

    Object format(LogLevel logLevel, final Class<?> clazz, String message);

}
