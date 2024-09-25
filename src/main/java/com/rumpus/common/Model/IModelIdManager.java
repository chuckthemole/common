package com.rumpus.common.Model;

import com.rumpus.common.ICommon;

/**
 * Interface for Model Id Managers
 * 
 * TODO: should this inherit from a common IdManager interface?
 */
public interface IModelIdManager<ID_TYPE> extends ICommon {
    /**
     * Generate a unique id
     * 
     * @return unique id
     */
    public ID_TYPE generateId();

    /**
     * Validate an id
     * 
     * @param id to validate
     * @return true if the id is valid, false otherwise
     */
    public boolean validateId(Object id);
}