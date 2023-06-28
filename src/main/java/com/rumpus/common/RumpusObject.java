package com.rumpus.common;

abstract public class RumpusObject extends Rumpus implements IRumpusObject {
    
    private final static String NAME = "RumpusObject";
    protected final String name;

    public RumpusObject() {
        this.name = NAME;
    }
    public RumpusObject(String name) {
        this.name = name;
    }

    @Override
    public String name() {
        return this.name;
    }
    
    @Override
    public String toString() {
        return this.name;
    }
}
