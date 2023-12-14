package com.rumpus.common;

abstract public class AbstractCommonObject extends AbstractCommon {
    
    private final static String NAME = "RumpusObject";
    protected final String name;

    public AbstractCommonObject() { // TODO: should I let users create objects without a name? prolly not
        this.name = NAME;
    }
    public AbstractCommonObject(String name) {
        this.name = name;
    }

    /**
     * Top level AbstractCommonObject name getter.
     * <p>
     * Returns the name of this object.
     * 
     * @return the name of this object
     */
    public String name() {
        return this.name;
    }
    
    /**
     * Top level AbstractCommonObject toString() method.
     * <p>
     * Returns the name of this object.
     * 
     * @return the name of this object
     */
    @Override
    public String toString() {
        return this.name;
    }
}
