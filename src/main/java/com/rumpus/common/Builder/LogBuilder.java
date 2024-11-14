package com.rumpus.common.Builder;

public class LogBuilder extends AbstractBuilder {

    private LogBuilder(boolean addSpaces, String... args) {
        super(addSpaces, args);
    }

    private LogBuilder(Class<?> clazz, boolean addSpaces, String... args) {
        super(addSpaces, args);
    }

    /**
     * This method is used to create a LogBuilder from a String array of arguments, using the default logger class.
     * <p>
     * It is the same as logBuilderFromStringArgsNoSpaces, except that it adds spaces between the arguments
     * <p>
     * Example: logBuilderFromStringArgs("a", "b", "c") returns "a b c"
     * 
     * @param args the arguments to add to the LogBuilder
     * @return the LogBuilder
     */
    public static LogBuilder logBuilderFromStringArgs(String... args) {
        return new LogBuilder(true, args);
    }

    /**
     * This method is used to create a LogBuilder from a String array of arguments, using the provided logger class.
     * <p>
     * It is the same as logBuilderFromStringArgsNoSpaces, except that it adds spaces between the arguments
     * <p>
     * Example: logBuilderFromStringArgs("a", "b", "c") returns "a b c"
     * 
     * @param clazz the logger class to use
     * @param args the arguments to add to the LogBuilder
     * @return the LogBuilder
     */
    public static LogBuilder logBuilderFromStringArgs(Class<?> clazz, String... args) {
        return new LogBuilder(clazz, true, args);
    }

    /**
     * This method is used to create a LogBuilder from a String array of arguments, using the default logger class.
     * <p>
     * It is the same as logBuilderFromStringArgs, except that it does not add spaces between the arguments
     * <p>
     * Example: logBuilderFromStringArgsNoSpaces("a", "b", "c") returns "abc"
     * 
     * @param args the arguments to add to the LogBuilder
     * @return the LogBuilder
     */
    public static LogBuilder logBuilderFromStringArgsNoSpaces(String... args) {
        return new LogBuilder(false, args);
    }

    /**
     * This method is used to create a LogBuilder from a String array of arguments, using the provided logger class.
     * <p>
     * It is the same as logBuilderFromStringArgs, except that it does not add spaces between the arguments
     * <p>
     * Example: logBuilderFromStringArgsNoSpaces("a", "b", "c") returns "abc"
     * 
     * @param clazz the logger class to use
     * @param args the arguments to add to the LogBuilder
     * @return the LogBuilder
     */
    public static LogBuilder logBuilderFromStringArgsNoSpaces(Class<?> clazz, String... args) {
        return new LogBuilder(clazz, false, args);
    }

    /**
     * This method is used to create a LogBuilder from a StackTraceElement array, using the default logger class.
     * <p>
     * It is the same as logBuilderFromStringArgsNoSpaces, except that it adds a new line between the arguments
     * <p>
     * Example: logBuilderFromStackTraceElementArray("a", "b", "c") returns "a\nb\nc"
     * 
     * @param message the message to add to the LogBuilder
     * @param stack the StackTraceElement array to add to the LogBuilder
     * @return the LogBuilder
     */
    public static LogBuilder logBuilderFromStackTraceElementArray(String message, StackTraceElement[] stack) {
        String[] elements = new String[stack.length + 1];
        StringBuilder sb = new StringBuilder();
        elements[0] = sb.append(message).append("\n").toString();
        for(int i = 1; i <= stack.length; i++) {
            elements[i] = new StringBuilder(stack[i - 1].toString()).append("\n").toString();
        }
        return LogBuilder.logBuilderFromStringArgs(elements);
    }

    /**
     * This method is used to create a LogBuilder from a StackTraceElement array, using the provided logger class.
     * <p>
     * It is the same as logBuilderFromStringArgsNoSpaces, except that it adds a new line between the arguments
     * <p>
     * Example: logBuilderFromStackTraceElementArray("a", "b", "c") returns "a\nb\nc"
     * 
     * @param clazz the logger class to use
     * @param message the message to add to the LogBuilder
     * @param stack the StackTraceElement array to add to the LogBuilder
     * @return the LogBuilder
     */
    public static LogBuilder logBuilderFromStackTraceElementArray(Class<?> clazz, String message, StackTraceElement[] stack) {
        String[] elements = new String[stack.length + 1];
        StringBuilder sb = new StringBuilder();
        elements[0] = sb.append(message).append("\n").toString();
        for(int i = 1; i <= stack.length; i++) {
            elements[i] = new StringBuilder(stack[i - 1].toString()).append("\n").toString();
        }
        return new LogBuilder(clazz, true, elements);
    }

    /**
     * This method is used to create a LogBuilder from a String array of arguments, using the default logger class.
     * <p>
     * It is the same as logBuilderFromStringArgsNoSpaces, except that it adds a new line between the arguments
     * <p>
     * Example: logBuilderFromStringArgsNewLine("a", "b", "c") returns "a\nb\nc"
     * 
     * @param args the arguments to add to the LogBuilder
     * @return the LogBuilder
     */
    public static LogBuilder logBuilderFromStringArgsNewLine(String... args) {
        StringBuilder sb = new StringBuilder();
        for(String arg : args) {
            sb.append(arg).append("\n");
        }
        return new LogBuilder(false, sb.toString());
    }

    /**
     * This method is used to create a LogBuilder from a String array of arguments, using the provided logger class.
     * <p>
     * It is the same as logBuilderFromStringArgsNoSpaces, except that it adds a new line between the arguments
     * <p>
     * Example: logBuilderFromStringArgsNewLine("a", "b", "c") returns "a\nb\nc"
     * 
     * @param clazz the logger class to use
     * @param args the arguments to add to the LogBuilder
     * @return the LogBuilder
     */
    public static LogBuilder logBuilderFromStringArgsNewLine(Class<?> clazz, String... args) {
        StringBuilder sb = new StringBuilder();
        for(String arg : args) {
            sb.append(arg).append("\n");
        }
        return new LogBuilder(clazz, false, sb.toString());
    }

    /**
     * This method is used to create a LogBuilder from a Map of Strings, using the default logger class.
     * <p>
     * It prints the key and value pairs in the map with a new line between each pair
     * <p>
     * Example: logBuilderFromMap(Map.of("a", "b", "c", "d")) returns "a: b\nc: d"
     * 
     * @param map the map to add to the LogBuilder
     * @return the LogBuilder
     */
    public static LogBuilder logBuilderFromMap(java.util.Map<String, String> map) {
        StringBuilder sb = new StringBuilder();
        for(java.util.Map.Entry<String, String> entry : map.entrySet()) {
            sb.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
        }
        return new LogBuilder(false, sb.toString());
    }

    /**
     * This method is used to create a LogBuilder from a Map of Strings, using the provided logger class.
     * <p>
     * It prints the key and value pairs in the map with a new line between each pair
     * <p>
     * Example: logBuilderFromMap(Map.of("a", "b", "c", "d")) returns "a: b\nc: d"
     * 
     * @param clazz the logger class to use
     * @param map the map to add to the LogBuilder
     * @return the LogBuilder
     */
    public static LogBuilder logBuilderFromMap(Class<?> clazz, java.util.Map<String, String> map) {
        StringBuilder sb = new StringBuilder();
        for(java.util.Map.Entry<String, String> entry : map.entrySet()) {
            sb.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
        }
        return new LogBuilder(clazz, false, sb.toString());
    }

    /**
     * This method is used to create a LogBuilder from a Map of Strings and prints the keys only, using the default logger class.
     * <p>
     * It prints the keys in the map with a space between each key
     * <p>
     * Example: logBuilderFromMapKeys(Map.of("a", "b", "c", "d")) returns "{ a b }"
     * 
     * @param map the map to add to the LogBuilder
     * @return the LogBuilder
     */
    public static LogBuilder logBuilderFromMapKeys(java.util.Map<String, String> map) {
        StringBuilder sb = new StringBuilder();
        sb.append("{ ");
        for(String key : map.keySet()) {
            sb.append(key).append(" ");
        }
        sb.append(" }");
        return new LogBuilder(false, sb.toString());
    }

    /**
     * This method is used to create a LogBuilder from a Map of Strings and prints the keys only, using the provided logger class.
     * <p>
     * It prints the keys in the map with a space between each key
     * <p>
     * Example: logBuilderFromMapKeys(Map.of("a", "b", "c", "d")) returns "{ a b }"
     * 
     * @param clazz the logger class to use
     * @param map the map to add to the LogBuilder
     * @return the LogBuilder
     */
    public static LogBuilder logBuilderFromMapKeys(Class<?> clazz, java.util.Map<String, String> map) {
        StringBuilder sb = new StringBuilder();
        sb.append("{ ");
        for(String key : map.keySet()) {
            sb.append(key).append(" ");
        }
        sb.append(" }");
        return new LogBuilder(clazz, false, sb.toString());
    }

    /**
     * This method is used to create a LogBuilder from a Set of Strings, using the default logger class.
     * <p>
     * It prints the elements in the set with a space between each element
     * <p>
     * Example: logBuilderFromSet(Set.of("a", "b", "c")) returns "{ a b c }"
     * 
     * @param set the set to add to the LogBuilder
     * @return the LogBuilder
     */
    public static LogBuilder logBuilderFromSet(java.util.Set<String> set) {
        StringBuilder sb = new StringBuilder();
        sb.append("{ ");
        for(String element : set) {
            sb.append(element).append(" ");
        }
        sb.append(" }");
        return new LogBuilder(false, sb.toString());
    }

    /**
     * This method is used to create a LogBuilder from a Set of Strings, using the default logger class.
     * <p>
     * It prints the elements in the set with a space between each element
     * <p>
     * Example: logBuilderFromSet(Set.of("a", "b", "c")) returns "{ a b c }"
     * 
     * @param clazz the logger class to use
     * @param set the set to add to the LogBuilder
     * @return the LogBuilder
     */
    public static LogBuilder logBuilderFromSet(Class<?> clazz, java.util.Set<String> set) {
        StringBuilder sb = new StringBuilder();
        sb.append("{ ");
        for(String element : set) {
            sb.append(element).append(" ");
        }
        sb.append(" }");
        return new LogBuilder(clazz, false, sb.toString());
    }
}
