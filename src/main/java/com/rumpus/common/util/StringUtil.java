package com.rumpus.common.util;

public class StringUtil {
    
    /**
     * @brief Checks if string is surrouned in given char
     * 
     * @return true if string is surrounded by given chars
     */
    public static boolean isSurrounded(String inputString, char start, char end) {
        if(inputString.charAt(0) == start && inputString.charAt(inputString.length() - 1) == end) {
            return true;
        }
        return false;
    }

    /**
     * @brief is surrounded by same char at beginning and end
     * 
     * @return true if string is surrounded by same given char
     */
    public static boolean isSurrounded(String inpuString, char inputChar) {
        return StringUtil.isSurrounded(inpuString, inputChar, inputChar);
    }

    /**
     * @brief is surrounded by "" or ''
     * 
     * @return true if string is surrounded by "" or ''
     */
    public static boolean isQuoted(String inpuString) {
        return (StringUtil.isSurrounded(inpuString, Character.valueOf('\'')) || StringUtil.isSurrounded(inpuString, Character.valueOf('"')));
    }

    /**
     * 
     * @param str string to quote
     * @return single quoted string
     */
    public static String singleQuote(String str) {
        StringBuilder sb = new StringBuilder();
        sb.append("'").append(str).append("'");
        return sb.toString();
    }

    /**
     * 
     * @param str string to quote
     * @return double quoted string
     */
    public static String doubleQuote(String str) {
        StringBuilder sb = new StringBuilder();
        sb.append("\"").append(str).append("\"");
        return sb.toString();
    }
}
