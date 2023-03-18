package com.akgarg.springframework.logger.support;

import com.akgarg.springframework.logger.LogLevel;
import com.akgarg.springframework.logger.Logger;

/**
 * @author Akhilesh Garg
 * @since 26-02-2023
 */
public abstract class AbstractLogger implements Logger {

    public abstract void log(LogLevel level, Class<?> clazz, String message);

    @Override
    public void info(Class<?> clazz, String message) {
        if (this.isInfoEnabled()) {
            this.log(LogLevel.INFO, clazz, message);
        }
    }

    @Override
    public void warn(Class<?> clazz, String message) {
        if (this.isWarnEnabled()) {
            this.log(LogLevel.WARN, clazz, message);
        }
    }

    @Override
    public void error(Class<?> clazz, String message) {
        if (this.isErrorEnabled()) {
            this.log(LogLevel.ERROR, clazz, message);
        }
    }

    @Override
    public void debug(Class<?> clazz, String message) {
        if (this.isDebugEnabled()) {
            this.log(LogLevel.DEBUG, clazz, message);
        }
    }

    @Override
    public void fatal(Class<?> clazz, String message) {
        if (this.isFatalEnabled()) {
            this.log(LogLevel.FATAL, clazz, message);
        }
    }

    @Override
    public void trace(Class<?> clazz, String message) {
        if (this.isTraceEnabled()) {
            this.log(LogLevel.TRACE, clazz, message);
        }
    }

}
