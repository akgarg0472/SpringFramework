package com.akgarg.springframework.logger;

/**
 * @author Akhilesh Garg
 * @since 23-03-2023
 */
public enum AnsiColorCodes {

    RED("\033[0;31m"),
    GREEN("\033[0;32m"),
    YELLOW("\033[0;33m"),
    CYAN("\033[0;36m"),
    BLUE("\033[34m"),
    RESET_MARKER("\u001B[0m");

    private final String colorCode;

    AnsiColorCodes(final String colorCode) {
        this.colorCode = colorCode;
    }

    public String code() {
        return colorCode;
    }
}
