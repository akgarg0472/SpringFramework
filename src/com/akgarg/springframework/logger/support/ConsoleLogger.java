package com.akgarg.springframework.logger.support;

import com.akgarg.springframework.logger.LogFormatter;
import com.akgarg.springframework.logger.LogLevel;
import com.akgarg.springframework.util.Assert;

/**
 * @author Akhilesh Garg
 * @since 26-02-2023
 */
public class ConsoleLogger extends AbstractLogger {

    private final LogFormatter logFormatter;
    private boolean enableInfo;
    private boolean enableError;
    private boolean enableDebug;
    private boolean enableFatal;
    private boolean enableWarn;
    private boolean enableTrace;

    public ConsoleLogger(final LogLevel logLevel) {
        this.logFormatter = new ConsoleLoggerLogFormatter();

        Assert.notNull(logLevel, "Log Level can't be null");
        Assert.notNull(logFormatter, "Log Formatter can't be null");

        setLogLevel(logLevel);
    }

    @Override
    public void log(final LogLevel level, final Class<?> clazz, final String message) {
        Assert.notNull(level, "Log Level can't be null");
        Assert.notNull(clazz, "Log Class can't be null");
        Assert.notNull(message, "Log message can't be null");

        final String formattedLogMessage = (String) this.logFormatter.format(
                level,
                clazz,
                message
        );

        System.out.println(formattedLogMessage);
    }

    @Override
    public boolean isInfoEnabled() {
        return this.enableInfo;
    }

    @Override
    public boolean isWarnEnabled() {
        return this.enableWarn;
    }

    @Override
    public boolean isErrorEnabled() {
        return this.enableError;
    }

    @Override
    public boolean isDebugEnabled() {
        return this.enableDebug;
    }

    @Override
    public boolean isFatalEnabled() {
        return this.enableFatal;
    }

    @Override
    public boolean isTraceEnabled() {
        return this.enableTrace;
    }

    @Override
    public void changeLogLevel(final LogLevel logLevel) {
        this.setLogLevel(logLevel);
    }

    private void setLogLevel(final LogLevel logLevel) {
        final int errorLevel = LogLevel.ERROR.level();
        final int warnLevel = LogLevel.WARN.level();
        final int infoLevel = LogLevel.INFO.level();
        final int debugLevel = LogLevel.DEBUG.level();
        final int fatalLevel = LogLevel.FATAL.level();
        final int traceLevel = LogLevel.TRACE.level();
        final int _logLevel = logLevel.level();

        if (errorLevel <= _logLevel) this.enableError = true;
        if (warnLevel <= _logLevel) this.enableWarn = true;
        if (infoLevel <= _logLevel) this.enableInfo = true;
        if (debugLevel <= _logLevel) this.enableDebug = true;
        if (fatalLevel <= _logLevel) this.enableFatal = true;
        if (traceLevel <= _logLevel) this.enableTrace = true;
    }

}
