package com.rumpus.common.Builder;

import com.rumpus.common.AbstractCommonObject;

public abstract class AbstractBuilder extends AbstractCommonObject {

    // public static final String NAME = "Builder";
    protected StringBuilder builder;

    public AbstractBuilder(String name) {
        super(name);
        this.builder = new StringBuilder();
    }

    /**
     * This method is used to create a Builder from a String array of arguments
     * <p>
     * By default this will add spaces between the arguments
     * 
     * @param name the name of the Builder
     * @param args the arguments to add to the Builder
     */
    public AbstractBuilder(String name, String... args) {
        super(name);
        this.builder = new StringBuilder();
        for(String arg : args) {
            this.builder.append(arg).append(" ");
        }
    }

    /**
     * This method is used to create a Builder from a String array of arguments
     * 
     * @param name the name of the Builder
     * @param addSpaces whether or not to add spaces between the arguments
     * @param args the arguments to add to the Builder
     */
    public AbstractBuilder(String name, boolean addSpaces, String... args) {
        super(name);
        this.builder = new StringBuilder();
        for(String arg : args) {
            if(addSpaces) {
                this.builder.append(arg).append(" ");
            } else {
                this.builder.append(arg);
            }
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
