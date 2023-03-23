package com.akgarg.springframework.logger.support;

import com.akgarg.springframework.logger.AnsiColorCodes;
import com.akgarg.springframework.logger.LogFormatter;
import com.akgarg.springframework.logger.LogLevel;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Akhilesh Garg
 * @since 18-03-2023
 */
public class ConsoleLogFormatter implements LogFormatter {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss,SSS");

    @SuppressWarnings("unchecked")
    @Override
    public <T> T format(
            final LogLevel logLevel,
            final Class<?> clazz,
            final String message,
            final Class<? extends T> type
    ) {
        final String threadName = Thread.currentThread().getName();

        return (T) (getLogColor(logLevel) + getTimeStamp() +
                " [" + threadName + "] " +
                logLevel.name() + " " +
                getTrimmedQualifiedClassName(clazz.getName()) + " - " +
                message + AnsiColorCodes.RESET_MARKER.code());
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
            case INFO:
                return AnsiColorCodes.GREEN.code();

            case DEBUG:
                return AnsiColorCodes.CYAN.code();

            case ERROR:
            case FATAL:
                return AnsiColorCodes.RED.code();

            case WARN:
                return AnsiColorCodes.YELLOW.code();

            default:
                return AnsiColorCodes.BLUE.code();
        }
    }

}
