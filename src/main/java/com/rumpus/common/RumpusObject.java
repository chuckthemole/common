package com.rumpus.common;

public class RumpusObject extends Rumpus implements IRumpusObject {
    protected final String name;

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
