package com.akgarg.springframework.logger.support;

import com.akgarg.springframework.logger.LogFormatter;
import com.akgarg.springframework.logger.LogLevel;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Akhilesh Garg
 * @since 18-03-2023
 */
public class ConsoleLogLogFormatter implements LogFormatter {

    private static final String RED = "\033[0;31m";
    private static final String GREEN = "\033[0;32m";
    private static final String YELLOW = "\033[0;33m";
    private static final String WHITE = "\033[0;37m";
    private static final String RESET_MARKET = "\u001B[0m";

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss,SSS");

    @Override
    public String format(final LogLevel logLevel, final Class<?> clazz, final String message) {
        final String threadName = Thread.currentThread().getName();

        return getLogColor(logLevel) +
                getTimeStamp() +
                " [" + threadName + "] " +
                logLevel.name() +
                " " + getTrimmedQualifiedClassName(clazz.getName()) +
                " - " + message +
                RESET_MARKET;
    }

    private String getTimeStamp() {
        return DATE_FORMAT.format(new Date());
    }

    private String getTrimmedQualifiedClassName(final String className) {
        final String[] packageNames = className.split("\\.");
        final StringBuilder sb = new StringBuilder();

        for (int i = 0; i < packageNames.length - 1; i++) {
            sb.append(packageNames[i].charAt(0)).append(".");
        }

        sb.append(packageNames[packageNames.length - 1]);

        return sb.toString();
    }

    private String getLogColor(final LogLevel level) {
        switch (level) {
            case ERROR:
            case FATAL:
                return RED;

            case INFO:
                return GREEN;

            case WARN:
                return YELLOW;

            default:
                return WHITE;
        }
    }

}
