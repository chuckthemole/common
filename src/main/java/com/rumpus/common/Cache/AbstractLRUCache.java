package com.rumpus.common.Cache;

import java.util.Optional;

import com.rumpus.common.AbstractCommonObject;

abstract public class AbstractLRUCache<KEY, VALUE extends CacheElement<KEY, ?>> extends AbstractCommonObject implements ICache<KEY, VALUE> {

    static final int MAX_ENTRIES = 100;

    private int capacity;
    private java.util.LinkedHashMap<KEY, VALUE> linkedHashMap;


    public AbstractLRUCache(int capacity) {
        
        this.capacity = capacity;
        this.linkedHashMap = new java.util.LinkedHashMap<KEY, VALUE>(capacity, 0.75f, true) {
            @Override
            protected boolean removeEldestEntry(java.util.Map.Entry<KEY, VALUE> eldest) {
                return size() > capacity;
            }
        };
    }

    @Override
    public boolean put(KEY key, VALUE value) {
        return this.linkedHashMap.put(key, value) != null;
    }

    @Override
    public Optional<VALUE> get(KEY key) {
        if(this.linkedHashMap.containsKey(key)) {
            VALUE value = this.linkedHashMap.get(key);
            return Optional.of(value);
        }
        return Optional.empty(); // key not found
    }

    @Override
    public int size() {
        return this.linkedHashMap.size();
    }

    @Override
    public boolean isEmpty() {
        return this.linkedHashMap.isEmpty();
    }

    @Override
    public void clear() {
        this.linkedHashMap.clear();
    }

    @Override
    public java.util.Map<KEY, VALUE> getMap() {
        return this.linkedHashMap;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("  capacity: ").append(this.capacity);
        // print the cache in order of lru
        sb.append("  Cache: ");
        for(KEY key : this.linkedHashMap.keySet()) {
            sb.append(this.linkedHashMap.get(key).toString());
            // sb.append(key).append(" -> ").append(this.cache.get(key)).append(", ");
        }
        return sb.toString();
    }

    private static void LOG_THIS(String... args) {
        com.rumpus.common.ICommon.LOG(AbstractLRUCache.class, args);
    }

    private static void LOG_THIS(com.rumpus.common.Log.ICommonLogger.LogLevel level, String... args) {
        com.rumpus.common.ICommon.LOG(AbstractLRUCache.class, level, args);
    }
}
