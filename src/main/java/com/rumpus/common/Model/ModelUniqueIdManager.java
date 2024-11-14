package com.rumpus.common.Model;

import java.io.Serializable;

import com.rumpus.common.util.UniqueId.AbstractUniqueIdManager;

// TODO: need to serialize this data when application ends and reload when application starts so we don't have dupes

/**
 * Singleton class to keep track of each model's unique id. Each model's set is identified by its NAME
 */
public class ModelUniqueIdManager extends AbstractUniqueIdManager implements Serializable { // TODO should this be Serializable?

    private static ModelUniqueIdManager singletonInstance = null; // TODO: was geting some bugs with getSingletonInstatnce(), look into

    private ModelUniqueIdManager() {}

    /**
     * Factory static constructor
     * 
     * @return instance of this class
     */
    public static synchronized ModelUniqueIdManager getSingletonInstance() {
        return singletonInstance == null ? new ModelUniqueIdManager() : singletonInstance;
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'toString'");
    }
}
