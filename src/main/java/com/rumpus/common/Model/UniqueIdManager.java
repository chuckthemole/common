package com.rumpus.common.Model;

import java.io.Serializable;

import com.rumpus.common.util.AbstractUniqueIdManager;

// TODO: need to serialize this data when application ends and reload when application starts so we don't have dupes

/**
 * Singleton class to keep track of each model's unique id. Each model's set is identified by its NAME
 */
public class UniqueIdManager extends AbstractUniqueIdManager implements Serializable { // TODO should this be Serializable?

    private static final String NAME = "UniqueIdManager";
    private static UniqueIdManager singletonInstance = null; // TODO: was geting some bugs with getSingletonInstatnce(), look into

    private UniqueIdManager() {
        super(NAME);
    }

    /**
     * Factory static constructor
     * 
     * @return instance of this class
     */
    public static synchronized UniqueIdManager getSingletonInstance() {
        return singletonInstance == null ? new UniqueIdManager() : singletonInstance;
    }
}
