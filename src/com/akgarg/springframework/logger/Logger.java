package com.akgarg.springframework.logger;

/**
 * @author Akhilesh Garg
 * @since 26-02-2023
 */
public interface Logger {

    void info(Class<?> clazz, String message);

    void warn(Class<?> clazz, String message);

    void error(Class<?> clazz, String message);

    void debug(Class<?> clazz, String message);

    void fatal(Class<?> clazz, String message);

    void trace(Class<?> clazz, String message);

    boolean isInfoEnabled();

    boolean isWarnEnabled();

    boolean isErrorEnabled();

    boolean isDebugEnabled();

    boolean isFatalEnabled();

    boolean isTraceEnabled();

}
