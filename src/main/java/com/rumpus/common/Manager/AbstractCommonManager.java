package com.rumpus.common.Manager;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.rumpus.common.AbstractCommonObject;

/**
 * Abstract class for handling objects (MANAGEES) that implement IManageable. This class implements Map.
 * <p>
 * TODO: Should I just change the name of this class to AbstractCommonMap? Seems less confusing. - Chuck
 */
public abstract class AbstractCommonManager<KEY, MANAGEE extends IManageable> extends AbstractCommonObject implements Map<KEY, MANAGEE> {

    private Map<KEY, MANAGEE> manageeMap; // map of: (key: managee id, value: managee)
    private boolean allowUseOfAutoGeneratedKey; // TODO: this is not used yet

    public AbstractCommonManager(boolean allowUseOfAutoGeneratedKey) {
        this.manageeMap = new HashMap<>();
        this.allowUseOfAutoGeneratedKey = allowUseOfAutoGeneratedKey;
    }

    public AbstractCommonManager(Map<KEY, MANAGEE> manageeMap, boolean allowUseOfAutoGeneratedKey) {
        this.manageeMap = manageeMap;
        this.allowUseOfAutoGeneratedKey = allowUseOfAutoGeneratedKey;
    }

    /**
     * Create an empty managee. Does not add it to the manageeMap.
     * 
     * @return The created managee.
     */
    public abstract MANAGEE createEmptyManagee();
    /**
     * Create a managee with the given name and add it to the manageeMap.
     * 
     * @param name The name of the managee to create.
     * @return The created managee.
     */
    public abstract MANAGEE createEmptyManagee(String name);

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
    public MANAGEE put(KEY key, MANAGEE value) {
        return this.manageeMap.put(key, value);
    }
    @Override
    public MANAGEE remove(Object key) {
        return this.manageeMap.remove(key);
    }
    @Override
    public void putAll(Map<? extends KEY, ? extends MANAGEE> m) {
        this.manageeMap.putAll(m);
    }
    @Override
    public void clear() {
        this.manageeMap.clear();
    }
    @Override
    public Set<KEY> keySet() {
        return this.manageeMap.keySet();
    }
    @Override
    public Collection<MANAGEE> values() {
        return this.manageeMap.values();
    }
    @Override
    public Set<Entry<KEY, MANAGEE>> entrySet() {
        return this.manageeMap.entrySet();
    }

    @Override // TODO: look at this more closely
    public boolean equals(Object o) {
        return this.manageeMap.equals(o);
    }
}
