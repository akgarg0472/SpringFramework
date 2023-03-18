package com.akgarg.springframework.logger;

/**
 * @author Akhilesh Garg
 * @since 26-02-2023
 */
public enum LogLevel {

    ERROR(1), WARN(2), INFO(3), DEBUG(4), FATAL(5), TRACE(6);

    private final int level;

    LogLevel(final int level) {
        this.level = level;
    }

    public int level() {
        return this.level;
    }

}
