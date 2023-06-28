package com.rumpus.common.Builder;

public class StringBuilderHelper extends Builder {

    public StringBuilderHelper(String name) {
        super(name);
    }
    
    public static String buildString(String... args) {
        StringBuilder sb = new StringBuilder();
        for(String arg : args) {
            sb.append(arg);
        }
        return sb.toString();
    }
}
