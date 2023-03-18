package com.akgarg.springframework.util;

/**
 * @author Akhilesh Garg
 * @since 27-02-2023
 */
public final class StringUtils {

    private StringUtils() {
        throw new UnsupportedOperationException();
    }

    public static boolean isNonNullString(final String string) {
        return string != null;
    }

    public static boolean isNonBlankString(final String string) {
        return isNonNullString(string) && string.trim().length() > 0;
    }

    public static String convertStringToCamelCase(final String str) {
        if (isNonBlankString(str)) {
            char firstChar = str.charAt(0);

            if (str.length() > 1 && Character.isAlphabetic(firstChar)) {
                firstChar = Character.toLowerCase(firstChar);
                return firstChar + str.substring(1);
            }
        }

        return str;
    }

}
