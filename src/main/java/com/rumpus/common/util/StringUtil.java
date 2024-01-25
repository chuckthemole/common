package com.rumpus.common.util;

/**
 * Feel free to implement static string helper methods here.
 * If you are doing something repeatedly in your code, consider adding it here.
 * 
 * @brief String utilities
 */
public class StringUtil implements com.rumpus.common.ICommon {

    /**
     * Build a string from args
     * <p>
     * TODO: should this be Object or String?
     * 
     * @param args args to build string from
     * @return string built from args
     */
    public static String buildStringFromArgs(Object... args) {
        StringBuilder stringBuilder = new StringBuilder();
        for(Object arg : args) {
            stringBuilder.append(arg);
        }
        return stringBuilder.toString();
    }
    
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
     * @brief remove surrounding chars
     * 
     * @return string without surrounding chars, if string is surrounded by given chars, otherwise returns original string
     */
    public static String trimStartAndEnd(String inputString, char startChar, char endChar) {
        if(StringUtil.isSurrounded(inputString, startChar, endChar)) {
            return inputString.substring(1, inputString.length() - 1);
        }
        LOG.info("String is not surrounded by given chars. Returning original string.");
        return inputString;
    }

    /**
     * @brief remove surrounding chars. Same as the overload but uses same char for beginning and end.
     * 
     * @param inputString string to trim
     * @param inputChar char to trim
     * @return string without surrounding chars, if string is surrounded by given char, otherwise returns original string
     */
    public static String trimStartAndEnd(String inputString, char inputChar) {
        return StringUtil.trimStartAndEnd(inputString, inputChar, inputChar);
    }

    /**
     * @brief remove surrounding String sequence. 
     * 
     * @param trimmee string to trim
     * @param trimmer string to trim
     * @return string without surrounding String, if string is surrounded by given String, otherwise returns original string
     */
    public static String trimStartAndEnd(String trimee, String trimmer) {
        char[] chars = trimmer.toCharArray();
        // trim char by char using loop. if the string is not returned trim break the loop and return original string.
        String modifiedString = String.valueOf(trimee);
        int charsLength = chars.length;
        for(int i = 0; i < charsLength; i++) {
            // LOG.info("Trimming char: " + chars[i] + " from string: " + modifiedString + ".");
            String previous = String.valueOf(modifiedString);
            modifiedString = StringUtil.trimStartAndEnd(modifiedString, chars[i], chars[charsLength - 1 - i]);
            if(previous.equals(modifiedString)) {
                LOG.info("String is not surrounded by given chars. Returning original string.");
                return trimee;
            }
        }
        return modifiedString;
    }

    // trimStartOrEnd will trim the start or end of the string if it starts or ends with the given char.
    public static String trimStartOrEnd(String inputString, char inputChar) {
        if(StringUtil.isStringNullOrEmpty(inputString)) {
            return inputString;
        }
        return trimEnd(trimBegin(inputString, inputChar), inputChar);
    }

    // trimStartOrEnd same as above but uses String instead of char
    public static String trimStartOrEnd(String inputString, String inputStringToTrim) {
        if(StringUtil.isStringNullOrEmpty(inputString)) {
            return inputString;
        }
        return trimEnd(trimBegin(inputString, inputStringToTrim), inputStringToTrim);
    }

    /**
     * @brief remove beginning char
     * 
     * @return string without beginning char, if string starts with given char, otherwise returns original string
     */
    public static String trimBegin(String inputString, char inputChar) {
        if(StringUtil.isStringNullOrEmpty(inputString)) {
            return inputString;
        }
        if(inputString.charAt(0) != inputChar) {
            LOG.info("Input string does not start with given char. Returning original string.");
            return inputString;
        }
        return inputString.substring(1);
    }

    /**
     * @brief remove beginning String
     * 
     * @return string without beginning String, if string starts with given String, otherwise returns original string
     */
    public static String trimBegin(String inputString, String inputStringToTrim) {
        if(StringUtil.isStringNullOrEmpty(inputString)) {
            return inputString;
        }
        if(!inputString.startsWith(inputStringToTrim)) {
            LOG.info("Input string does not start with given String. Returning original string.");
            return inputString;
        }
        return inputString.substring(inputStringToTrim.length());
    }

    /**
     * @brief remove end char
     * @param inpuString string to trim
     * @return string without end char, if string ends with given char, otherwise returns original string
     */
    public static String trimEnd(String inpuString, char inputChar) {
        if(StringUtil.isStringNullOrEmpty(inpuString)) {
            return inpuString;
        }
        if(inpuString.charAt(inpuString.length() - 1) != inputChar) {
            LOG.info("Input string does not end with given char. Returning original string.");
            return inpuString;
        }
        return inpuString.substring(0, inpuString.length() - 1);
    }

    /**
     * @brief remove end String
     * @param inpuString string to trim
     * @return string without end String, if string ends with given String, otherwise returns original string
     */
    public static String trimEnd(String inpuString, String inputStringToTrim) {
        if(StringUtil.isStringNullOrEmpty(inpuString)) {
            return inpuString;
        }
        if(!inpuString.endsWith(inputStringToTrim)) {
            LOG.info("Input string does not end with given String. Returning original string.");
            return inpuString;
        }
        return inpuString.substring(0, inpuString.length() - inputStringToTrim.length());
    }

    /**
     * Note: if you do NOT want to add a char to the beginning or end of the string, pass null for that char.
     * 
     * @brief add char to beginning and/or end of string
     * 
     * @param inpuString string to add char to
     * @param startChar char to add to beginning of string
     * @param endChar char to add to end of string
     * @return string with given chars added to beginning and/or end of string
     */
    public static String addCharToStartAndOrEnd(String inpuString, Character startChar, Character endChar) {
        StringBuilder stringBuilder = new StringBuilder();

        if(StringUtil.isStringNullOrEmpty(inpuString)) {
            return inpuString;
        }
        if(startChar != null) {
            stringBuilder.append(startChar).append(inpuString);
        }
        if(endChar != null) {
            stringBuilder.append(endChar);
        }
        return stringBuilder.toString();

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

    /**
     * @brief Checks if string is null or empty
     * @param str string to check
     * @return true if string is null or empty
     */
    public static boolean isStringNullOrEmpty(String str) {
        if(str == null || str.length() == 0) {
            LOG.info("Input string is null or empty.");
            return true;
        }
        return false;
    }

    //////////////////////////////////////////////
    ///////// JSON UTILITIES /////////////////////
    //////////////////////////////////////////////
    public static String prettyPrintJson(String json) {
        com.google.gson.Gson gson = new com.google.gson.GsonBuilder().setPrettyPrinting().create();
        com.google.gson.JsonElement je = null;
        try {
            je = com.google.gson.JsonParser.parseString(json);
        } catch (com.google.gson.JsonSyntaxException e) {
            LOG("The given json is not valid, returning original json:\n", json);
            return json;
        }
        return gson.toJson(je);
    }

    private static void LOG(String... args) {
        com.rumpus.common.Builder.LogBuilder.logBuilderFromStringArgsNoSpaces(StringUtil.class, args).info();
    }
}
