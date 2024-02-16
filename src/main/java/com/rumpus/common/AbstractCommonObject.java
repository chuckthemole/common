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

    // TODO: think about getting rid of these LOG methods and use the LOG methods in ICommon
    /**
     * Top level AbstractCommonObject LOG method. Uses info level.
     * 
     * @param args The message to log
     */
    protected void LOG(String... args) {
        com.rumpus.common.Builder.LogBuilder.logBuilderFromStringArgsNoSpaces(this.getClass(), args).info();
    }

    /**
     * Top level AbstractCommonObject LOG method. Uses the specified level.
     * 
     * @param level the level to log the message at
     * @param args The message to log
     */
    protected void LOG(com.rumpus.common.Logger.AbstractCommonLogger.LogLevel level, String... args) {
        com.rumpus.common.Builder.LogBuilder.logBuilderFromStringArgsNoSpaces(this.getClass(), args).log(level);
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
        LOG("AbstractCommonObject::toString()  Should you be here? Maybe override toString() in your class?");
        return this.name;
    }

    @Override
    public boolean equals(Object obj) {
        LOG("AbstractCommonObject::equals()  Should you be here? Maybe override equals() in your class?");
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
