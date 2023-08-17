package com.rumpus.common.Manager;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.rumpus.common.AbstractCommonObject;

/**
 * Abstract class for handling objects (MANAGEES) that implement IManageable. This class implements Map.
 */
public abstract class AbstractCommonManager<MANAGEE extends IManageable> extends AbstractCommonObject implements Map<String, MANAGEE> {

    private Map<String, MANAGEE> manageeMap; // map of: (key: managee id, value: managee)

    public AbstractCommonManager(String name) {
        super(name);
        this.manageeMap = new HashMap<>();
    }

    public AbstractCommonManager(Map<String, MANAGEE> manageeMap) {
        this.manageeMap = manageeMap;
    }

    /**
     * Same as put() but creates auto generated key
     * Returns key instead of value
     * 
     * @param value to add
     * @return 
     */
    public String add(MANAGEE value) {
        MANAGEE managee = this.manageeMap.put(KEYHOLDER, value);
        return null;
    }

    @Override
    public int size() {
        return this.manageeMap.size();
    }
    @Override
    public boolean isEmpty() {
        return this.manageeMap.isEmpty();
    }
    @Override
    public boolean containsKey(Object key) {
        return this.manageeMap.containsKey(key);
    }
    @Override
    public boolean containsValue(Object value) {
        return this.manageeMap.containsValue(value);
    }
    @Override
    public MANAGEE get(Object key) {
        return this.manageeMap.get(key);
    }
    @Override
    public MANAGEE put(String key, MANAGEE value) {
        return this.manageeMap.put(key, value);
    }
    @Override
    public MANAGEE remove(Object key) {
        return this.manageeMap.remove(key);
    }
    @Override
    public void putAll(Map<? extends String, ? extends MANAGEE> m) {
        this.manageeMap.putAll(m);
    }
    @Override
    public void clear() {
        this.manageeMap.clear();
    }
    @Override
    public Set<String> keySet() {
        return this.manageeMap.keySet();
    }
    @Override
    public Collection<MANAGEE> values() {
        return this.manageeMap.values();
    }
    @Override
    public Set<Entry<String, MANAGEE>> entrySet() {
        return this.manageeMap.entrySet();
    }
}
