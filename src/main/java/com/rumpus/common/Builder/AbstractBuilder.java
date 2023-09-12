package com.rumpus.common.Builder;

import com.rumpus.common.AbstractCommonObject;

abstract class AbstractBuilder extends AbstractCommonObject {

    // public static final String NAME = "Builder";
    protected StringBuilder builder;

    public AbstractBuilder(String name) {
        super(name);
        this.builder = new StringBuilder();
    }

    public AbstractBuilder(String name, String... args) {
        super(name);
        this.builder = new StringBuilder();
        for(String arg : args) {
            this.builder.append(arg).append(" ");
        }
    }

    public void clear() {
        this.builder = new StringBuilder();
    }

    public void info() {
        LOG.info(this.toString());
    }
    public void error() {
        LOG.error(this.toString());
    }

    public void append(String... appendees) {
        for(String appendee : appendees) {
            this.builder.append(appendee);
        }
    }

    public StringBuilder getStringBuilder() {
        return this.builder;
    }

    @Override
    public String toString() {
        return this.builder.toString();
    }
}
