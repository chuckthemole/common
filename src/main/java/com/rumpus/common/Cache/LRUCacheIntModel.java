package com.rumpus.common.Cache;

import com.rumpus.common.Model.AbstractModel;

/**
 * Using this for testing purposes. Should probably define the model type in the future.
 */
public class LRUCacheIntModel extends AbstractLRUCache<Integer, CacheElement<Integer, ? extends AbstractModel<?>>> {

    private static final String NAME = "LRUCacheIntModel";

    private LRUCacheIntModel(int size) {
        super(NAME, size);
    }

    public static LRUCacheIntModel create(int size) {
        return new LRUCacheIntModel(size);
    }
    
}