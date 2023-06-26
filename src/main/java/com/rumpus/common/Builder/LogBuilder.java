package com.rumpus.common.Builder;

public class LogBuilder extends Builder {

    public static final String NAME = "LogBuilder";

    public LogBuilder(String... args) {
        super(NAME, args);
    }

    public static LogBuilder logBuilderFromStringArgs(String... args) {
        return new LogBuilder(args);
    }
    public static LogBuilder logBuilderFromStackTraceElementArray(StackTraceElement[] stack) {
        String[] elements = new String[stack.length];
        for(int i = 0; i < stack.length; i++) {
            elements[i] = new StringBuilder(stack[i].toString()).append("\n").toString();
        }
        return new LogBuilder(elements);
    }
}
