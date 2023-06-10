package com.rumpus.common.Builder;

import com.rumpus.common.RumpusObject;

abstract class Builder extends RumpusObject {

    // public static final String NAME = "Builder";
    protected StringBuilder builder;

    public Builder(String name) {
        super(name);
        this.builder = new StringBuilder();
    }

    public Builder(String name, String... args) {
        super(name);
        this.builder = new StringBuilder();
        for(String arg : args) {
            builder.append(arg);
        }
    }

    public void clear() {
        this.builder = new StringBuilder();
    }

    public void info() {
        LOG.info(this.toString());
    }

    @Override
    public String toString() {
        return this.builder.toString();
    }
}
