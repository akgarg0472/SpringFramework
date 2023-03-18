package com.akgarg.springframework.logger.support;

import com.akgarg.springframework.logger.LogFormatter;
import com.akgarg.springframework.logger.LogLevel;
import com.akgarg.springframework.util.Assert;

import java.io.Console;
import java.io.PrintWriter;

/**
 * @author Akhilesh Garg
 * @since 26-02-2023
 */
public class ConsoleLoggerImpl extends AbstractLogger {

    private final LogFormatter logFormatter;
    private final int logLevel;
    private boolean enableInfo;
    private boolean enableError;
    private boolean enableDebug;
    private boolean enableFatal;
    private boolean enableWarn;
    private boolean enableTrace;

    public ConsoleLoggerImpl(final LogLevel logLevel) {
        Assert.notNull(logLevel, "Log Level can't be null");

        this.logFormatter = new ConsoleLogLogFormatter();
        this.logLevel = logLevel.level();
        setLogLevel();
    }

    @Override
    public void log(final LogLevel level, final Class<?> clazz, final String message) {
        Assert.notNull(level, "Log Level can't be null");
        Assert.notNull(clazz, "Log Class can't be null");
        Assert.notNull(message, "Log message can't be null");

        final String formattedLogMessage = this.logFormatter.format(
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

    private void setLogLevel() {
        final int errorLevel = LogLevel.ERROR.level(); // 1
        final int warnLevel = LogLevel.WARN.level(); // 2
        final int infoLevel = LogLevel.INFO.level(); // 3
        final int debugLevel = LogLevel.DEBUG.level();// 4
        final int fatalLevel = LogLevel.FATAL.level(); // 5
        final int traceLevel = LogLevel.TRACE.level(); // 6

        if (errorLevel <= this.logLevel) this.enableError = true;
        if (warnLevel <= this.logLevel) this.enableWarn = true;
        if (infoLevel <= this.logLevel) this.enableInfo = true;
        if (debugLevel <= this.logLevel) this.enableDebug = true;
        if (fatalLevel <= this.logLevel) this.enableFatal = true;
        if (traceLevel <= this.logLevel) this.enableTrace = true;
    }

}
