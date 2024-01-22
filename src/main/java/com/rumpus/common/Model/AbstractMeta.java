package com.rumpus.common.Model;

/**
 * Parent class for AbstractMetaData.
 * <p>
 * Currently using this as a buffer between AbstractMetaData and AbstractCommonObject.
 * <p>
 * This is needed since AbstractMetaData is Serializable. The parent class above the last Serializable class needs to have a no-arg constructor and AbstractCommonObject does not.
 */
abstract public class AbstractMeta extends com.rumpus.common.AbstractCommonObject {

    private static final String NAME = "AbstractMeta";

    public AbstractMeta() {
        super(NAME);
    }
    public AbstractMeta(String name) {
        super(name);
    }
}
