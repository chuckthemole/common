package com.rumpus.common.Builder;

public class LogBuilder extends AbstractBuilder {

    public static final String NAME = "LogBuilder";

    public LogBuilder(String... args) {
        super(NAME, args);
    }

    public static LogBuilder logBuilderFromStringArgs(String... args) {
        return new LogBuilder(args);
    }
    public static LogBuilder logBuilderFromStackTraceElementArray(String message, StackTraceElement[] stack) {
        String[] elements = new String[stack.length + 1];
        StringBuilder sb = new StringBuilder();
        elements[0] = sb.append(message).append("\n").toString();
        for(int i = 1; i <= stack.length; i++) {
            elements[i] = new StringBuilder(stack[i - 1].toString()).append("\n").toString();
        }
        return new LogBuilder(elements);
    }
}
