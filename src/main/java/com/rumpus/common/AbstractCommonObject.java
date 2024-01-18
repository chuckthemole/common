package com.rumpus.common;

abstract public class AbstractCommonObject implements ICommon {

    protected final String name;

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
        LOG.info("AbstractCommonObject::toString()  Should you be here? Maybe override toString() in your class?");
        return this.name;
    }

    @Override
    public boolean equals(Object obj) {
        LOG.info("AbstractCommonObject::equals()  Should you be here? Maybe override equals() in your class?");
        if(obj == null) {
            return false;
        }
        if(!(obj instanceof AbstractCommonObject)) {
            return false;
        }
        AbstractCommonObject other = (AbstractCommonObject) obj;
        return this.name.equals(other.name);
    }
}
