package com.rumpus.common;

abstract public class AbstractCommonObject extends AbstractCommon {
    
    private final static String NAME = "RumpusObject";
    protected final String name;

    public AbstractCommonObject() {
        this.name = NAME;
    }
    public AbstractCommonObject(String name) {
        this.name = name;
    }

    public String name() {
        return this.name;
    }
    
    @Override
    public String toString() {
        return this.name;
    }
}
