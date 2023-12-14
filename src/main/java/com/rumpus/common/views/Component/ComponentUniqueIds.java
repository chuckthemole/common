package com.rumpus.common.views.Component;

import java.io.Serializable;

import com.rumpus.common.util.UniqueId.AbstractUniqueIdManager;

public class ComponentUniqueIds extends AbstractUniqueIdManager implements Serializable { // TODO should this be Serializable?

    private static final String NAME = "UniqueIdManager";
    private static ComponentUniqueIds singletonInstance = null; // TODO: was geting some bugs with getSingletonInstatnce(), look into

    private ComponentUniqueIds() {
        super(NAME);
    }

    /**
     * Factory static constructor
     * 
     * @return instance of this class
     */
    public static synchronized ComponentUniqueIds getSingletonInstance() {
        return singletonInstance == null ? new ComponentUniqueIds() : singletonInstance;
    }
}
