package com.rumpus.common.util;

import com.rumpus.common.ICommon;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import com.rumpus.common.Builder.LogBuilder;

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
        for (Object arg : args) {
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
        if (inputString.charAt(0) == start && inputString.charAt(inputString.length() - 1) == end) {
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
     * @return string without surrounding chars, if string is surrounded by given
     *         chars, otherwise returns original string
     */
    public static String trimStartAndEnd(String inputString, char startChar, char endChar) {
        if (StringUtil.isSurrounded(inputString, startChar, endChar)) {
            return inputString.substring(1, inputString.length() - 1);
        }
        LOG("String is not surrounded by given chars. Returning original string.");
        return inputString;
    }

    /**
     * @brief remove surrounding chars. Same as the overload but uses same char for
     *        beginning and end.
     * 
     * @param inputString string to trim
     * @param inputChar   char to trim
     * @return string without surrounding chars, if string is surrounded by given
     *         char, otherwise returns original string
     */
    public static String trimStartAndEnd(String inputString, char inputChar) {
        return StringUtil.trimStartAndEnd(inputString, inputChar, inputChar);
    }

    /**
     * @brief remove surrounding String sequence.
     * 
     * @param trimmee string to trim
     * @param trimmer string to trim
     * @return string without surrounding String, if string is surrounded by given
     *         String, otherwise returns original string
     */
    public static String trimStartAndEnd(String trimee, String trimmer) {
        char[] chars = trimmer.toCharArray();
        // trim char by char using loop. if the string is not returned trim break the
        // loop and return original string.
        String modifiedString = String.valueOf(trimee);
        int charsLength = chars.length;
        for (int i = 0; i < charsLength; i++) {
            // LOG("Trimming char: " + chars[i] + " from string: " + modifiedString + ".");
            String previous = String.valueOf(modifiedString);
            modifiedString = StringUtil.trimStartAndEnd(modifiedString, chars[i], chars[charsLength - 1 - i]);
            if (previous.equals(modifiedString)) {
                LOG("String is not surrounded by given chars. Returning original string.");
                return trimee;
            }
        }
        return modifiedString;
    }

    // trimStartOrEnd will trim the start or end of the string if it starts or ends
    // with the given char.
    public static String trimStartOrEnd(String inputString, char inputChar) {
        if (StringUtil.isStringNullOrEmpty(inputString)) {
            return inputString;
        }
        return trimEnd(trimBegin(inputString, inputChar), inputChar);
    }

    // trimStartOrEnd same as above but uses String instead of char
    public static String trimStartOrEnd(String inputString, String inputStringToTrim) {
        if (StringUtil.isStringNullOrEmpty(inputString)) {
            return inputString;
        }
        return trimEnd(trimBegin(inputString, inputStringToTrim), inputStringToTrim);
    }

    /**
     * @brief remove beginning char
     * 
     * @return string without beginning char, if string starts with given char,
     *         otherwise returns original string
     */
    public static String trimBegin(String inputString, char inputChar) {
        if (StringUtil.isStringNullOrEmpty(inputString)) {
            return inputString;
        }
        if (inputString.charAt(0) != inputChar) {
            LOG("Input string does not start with given char. Returning original string.");
            return inputString;
        }
        return inputString.substring(1);
    }

    /**
     * @brief remove beginning String
     * 
     * @return string without beginning String, if string starts with given String,
     *         otherwise returns original string
     */
    public static String trimBegin(String inputString, String inputStringToTrim) {
        if (StringUtil.isStringNullOrEmpty(inputString)) {
            return inputString;
        }
        if (!inputString.startsWith(inputStringToTrim)) {
            LOG("Input string does not start with given String. Returning original string.");
            return inputString;
        }
        return inputString.substring(inputStringToTrim.length());
    }

    /**
     * @brief remove end char
     * @param inpuString string to trim
     * @return string without end char, if string ends with given char, otherwise
     *         returns original string
     */
    public static String trimEnd(String inpuString, char inputChar) {
        if (StringUtil.isStringNullOrEmpty(inpuString)) {
            return inpuString;
        }
        if (inpuString.charAt(inpuString.length() - 1) != inputChar) {
            LOG("Input string does not end with given char. Returning original string.");
            return inpuString;
        }
        return inpuString.substring(0, inpuString.length() - 1);
    }

    /**
     * @brief remove end String
     * @param inpuString string to trim
     * @return string without end String, if string ends with given String,
     *         otherwise returns original string
     */
    public static String trimEnd(String inpuString, String inputStringToTrim) {
        if (StringUtil.isStringNullOrEmpty(inpuString)) {
            return inpuString;
        }
        if (!inpuString.endsWith(inputStringToTrim)) {
            LOG("Input string does not end with given String. Returning original string.");
            return inpuString;
        }
        return inpuString.substring(0, inpuString.length() - inputStringToTrim.length());
    }

    /**
     * Note: if you do NOT want to add a char to the beginning or end of the string,
     * pass null for that char.
     * 
     * @brief add char to beginning and/or end of string
     * 
     * @param inpuString string to add char to
     * @param startChar  char to add to beginning of string
     * @param endChar    char to add to end of string
     * @return string with given chars added to beginning and/or end of string
     */
    public static String addCharToStartAndOrEnd(String inpuString, Character startChar, Character endChar) {
        StringBuilder stringBuilder = new StringBuilder();

        if (StringUtil.isStringNullOrEmpty(inpuString)) {
            return inpuString;
        }
        if (startChar != null) {
            stringBuilder.append(startChar).append(inpuString);
        }
        if (endChar != null) {
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
        return (StringUtil.isSurrounded(inpuString, Character.valueOf('\''))
                || StringUtil.isSurrounded(inpuString, Character.valueOf('"')));
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
        if (str == null || str.length() == 0) {
            LOG("Input string is null or empty.");
            return true;
        }
        return false;
    }

    /**
     * Parses a comma-separated string into a list of trimmed, non-empty values.
     * <p>
     * This utility method is useful for converting configuration or user input
     * fields (e.g., CSV lists from environment variables or form data) into
     * a clean list of strings. It automatically trims whitespace around each
     * entry and ignores empty values.
     * </p>
     *
     * <h3>Behavior</h3>
     * <ul>
     * <li>If {@code csv} is {@code null} or empty, returns an empty list.</li>
     * <li>Whitespace surrounding each value is removed.</li>
     * <li>Empty values (e.g., consecutive commas) are filtered out.</li>
     * </ul>
     *
     * <h3>Example</h3>
     * 
     * <pre>{@code
     * List<String> values = JsonUtil.parseCsv("apple, banana, , orange");
     * // Result: ["apple", "banana", "orange"]
     * }</pre>
     *
     * @param csv a comma-separated string to parse; may be {@code null} or empty
     * @return an immutable list of trimmed, non-empty values
     */
    public static List<String> parseCsv(String csv) {
        if (csv == null || csv.isEmpty())
            return List.of();
        return Arrays.stream(csv.split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .toList();
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

    /**
     * Filters and transforms a JSON string containing a top-level "results" array
     * based on
     * inclusion, exclusion, and conditional filtering rules.
     * <p>
     * This method allows selective inclusion and exclusion of fields for each
     * object
     * within the "results" array. Additionally, it supports exclusion of entire
     * objects
     * based on field-value matching criteria (defined in {@code excludeEntries}).
     * </p>
     *
     * <h3>Behavior</h3>
     * <ul>
     * <li>If {@code includeFields} is non-empty, only the specified fields are
     * retained.</li>
     * <li>If {@code includeFields} is empty, all fields are retained unless
     * excluded.</li>
     * <li>Fields listed in {@code excludeFields} are always removed from the
     * result.</li>
     * <li>Objects are excluded entirely if they match any key/value pair in
     * {@code excludeEntries}.</li>
     * </ul>
     *
     * <h3>Special Cases</h3>
     * <ul>
     * <li>If {@code includeFields} contains "email" and an object lacks a top-level
     * "email" field,
     * the method will attempt to extract it from a nested "person" object (e.g.
     * {@code person.email}).</li>
     * </ul>
     *
     * <h3>Example</h3>
     * 
     * <pre>{@code
     * Map<String, List<String>> excludeEntries = Map.of("name", List.of("admin", "test"));
     * String result = JsonUtil.filterJson(jsonString,
     *         List.of("id", "name", "email"),
     *         List.of("password"),
     *         excludeEntries);
     * }</pre>
     *
     * @param json           the input JSON string containing a "results" array
     * @param includeFields  list of field names to include; if empty, all fields
     *                       are included
     * @param excludeFields  list of field names to remove from each object
     * @param excludeEntries a mapping of field names to lists of values; objects
     *                       matching these
     *                       key/value pairs are excluded entirely
     * @return a formatted JSON string with filtered results; returns the original
     *         JSON if parsing fails
     *
     * @see #shouldExclude(JsonNode, Map)
     */
    public static String filterJson(
            String json,
            List<String> includeFields,
            List<String> excludeFields,
            Map<String, List<String>> excludeEntries) {

        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(json);

            ArrayNode results = (ArrayNode) root.path("results");
            ArrayNode filteredResults = mapper.createArrayNode();

            for (JsonNode item : results) {
                // Check if this item should be excluded
                if (shouldExclude(item, excludeEntries)) {
                    LOG("filterJson() -> Skipping excluded item:", item.toString());
                    continue;
                }

                ObjectNode filteredItem = mapper.createObjectNode();

                // If includeFields specified → only keep those
                if (!includeFields.isEmpty()) {
                    for (String field : includeFields) {
                        if (item.has(field)) {
                            filteredItem.set(field, item.get(field));
                        } else if (field.equals("email")) {
                            // special case for nested email
                            JsonNode person = item.path("person");
                            if (person.has("email")) {
                                filteredItem.put("email", person.get("email").asText());
                            }
                        }
                    }
                } else {
                    // otherwise copy all fields
                    filteredItem.setAll((ObjectNode) item);
                }

                // Apply excludeFields if needed
                for (String field : excludeFields) {
                    filteredItem.remove(field);
                }

                filteredResults.add(filteredItem);
            }

            ((ObjectNode) root).set("results", filteredResults);
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(root);

        } catch (Exception e) {
            LOG("filterJson() -> ERROR parsing JSON:", e.getMessage());
            return json; // Fallback to unmodified JSON
        }
    }

    private static boolean shouldExclude(JsonNode node, Map<String, List<String>> excludeEntries) {
        if (excludeEntries == null || excludeEntries.isEmpty()) {
            return false;
        }

        for (Map.Entry<String, List<String>> entry : excludeEntries.entrySet()) {
            String key = entry.getKey();
            List<String> valuesToExclude = entry.getValue();

            JsonNode valueNode = node.path(key);
            if (!valueNode.isMissingNode()) {
                String value = valueNode.asText();
                if (valuesToExclude.contains(value)) {
                    return true;
                }
            }
        }

        return false;
    }

    private static void LOG(String... args) {
        final String log = LogBuilder.logBuilderFromStringArgsNoSpaces(StringUtil.class, args).toString();
        ICommon.LOG(StringUtil.class, log);
    }
}
