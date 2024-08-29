package com.rumpus.common.Cache;

import java.util.Optional;

import com.rumpus.common.ICommon;

public interface ICache<KEY, VALUE> extends ICommon {
    /**
     * Put a key-value pair into the cache.
     * 
     * @param key
     * @param value
     * @return true if the key-value pair was successfully put into the cache, false otherwise
     */
    public boolean put(KEY key, VALUE value);

    /**
     * Get the value associated with the key from the cache.
     * 
     * @param key
     * @return the value associated with the key, if it exists in the cache
     */
    public Optional<VALUE> get(KEY key);

    /**
     * @return the size of the cache
     */
    public int size();

    /**
     * @return true if the cache is empty, false otherwise
     */
    public boolean isEmpty();

    /**
     * Clear the cache.
     */
    public void clear();

    /**
     * @return the cache as a map
     */
    public java.util.Map<KEY, VALUE> getMap();
}
