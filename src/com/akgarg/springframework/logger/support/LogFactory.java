package com.akgarg.springframework.logger.support;

import com.akgarg.springframework.logger.LogLevel;
import com.akgarg.springframework.logger.Logger;
import com.akgarg.springframework.logger.exception.IllegalOperationException;
import com.akgarg.springframework.logger.exception.LogFactoryException;

/**
 * @author Akhilesh Garg
 * @since 26-02-2023
 */
public class LogFactory {

    private static Logger logger;
    private static LogLevel logLevel = LogLevel.INFO;

    private LogFactory() {
        throw new IllegalOperationException("Instantiation of singleton LogFactory is not allowed");
    }

    public static Logger getDefaultLogger() {
        if (logger == null) {
            try {
                logger = new ConsoleLogger(logLevel);
            } catch (Exception e) {
                throw new LogFactoryException("Exception creating logger instance through LoggerFactory", e);
            }
        }

        return logger;
    }

    public static void setLogLevel(final LogLevel level) {
        logLevel = level;
    }

}
