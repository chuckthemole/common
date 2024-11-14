package com.rumpus.common.Cache;

import com.rumpus.common.AbstractCommonObject;

/**
 * CacheElement is a container for a key-value pair to be stored in a cache.
 */
public class CacheElement<KEY, VALUE> extends AbstractCommonObject {

    private KEY key;
    private VALUE value;

    private CacheElement(KEY key, VALUE value) {
        
        this.key = key;
        this.value = value;
    }

    public static <KEY, VALUE> CacheElement<KEY, VALUE> create(KEY key, VALUE value) {
        return new CacheElement<>(key, value);
    }

    public KEY getKey() {
        return this.key;
    }

    public VALUE getValue() {
        return this.value;
    }

    @Override
    public String toString() {
        return "CacheElement [key=" + key + ", value=" + value + "]";
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) {
            return true;
        }
        if(obj == null) {
            return false;
        }
        if(CacheElement.class != obj.getClass()) {
            return false;
        }
        CacheElement<?, ?> other = (CacheElement<?, ?>) obj;
        if(key == null) {
            if(other.key != null) {
                return false;
            }
        } else if(!key.equals(other.key)) {
            return false;
        }
        if(value == null) {
            if(other.value != null) {
                return false;
            }
        } else if(!value.equals(other.value)) {
            return false;
        }
        return true;
    }
}
