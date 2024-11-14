package com.rumpus.common.Model;

import com.rumpus.common.AbstractCommonObject;

/**
 * Parent class for AbstractMetaData.
 * <p>
 * Currently using this as a buffer between AbstractMetaData and AbstractCommonObject.
 * <p>
 * This is needed since AbstractMetaData is Serializable. The parent class above the last Serializable class needs to have a no-arg constructor and AbstractCommonObject does not.
 */
abstract public class AbstractMeta extends AbstractCommonObject {
    public AbstractMeta() {}
}
