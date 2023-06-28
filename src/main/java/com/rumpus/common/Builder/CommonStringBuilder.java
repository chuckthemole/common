package com.rumpus.common.Builder;

public class CommonStringBuilder extends AbstractBuilder {

    private static final String NAME = "CommonStringBuilder";

    private CommonStringBuilder(String... args) {
        super(NAME, args);
    }

    /**
     * 
     * @param args strings to build
     * @return CommonStringBuilder object
     */
    public static CommonStringBuilder stringBuilder(String... args) {
        return new CommonStringBuilder(args);
    }

    /**
     * 
     * @param args strings to build
     * @return a built string
     */
    public static String buildString(String... args) {
        return new CommonStringBuilder(args).toString();
    }

    // TODO build public static method that takes an array of arrays. Each array is a line. Maybe call something like 'buildStringLineByLine'
    
}
