package com.rumpus.common.Manager;

import com.rumpus.common.AbstractCommonObject;
import com.rumpus.common.util.StringUtil;
import com.rumpus.common.util.UniqueId.AbstractUniqueIdManager;

abstract public class AbstractCommonManagerIdKey<MANAGEE extends ISetItem> extends AbstractCommonObject implements java.util.Set<MANAGEE> {

    private static class IdManager extends AbstractUniqueIdManager {
        private static final String NAME = "AbstractCommonSetIdManager";
        private IdManager() {
            super(NAME);
        }
    }

    private static AbstractUniqueIdManager uniqueIdManager;
    private java.util.Map<String, MANAGEE> manageeSet;

    static {
        uniqueIdManager = new IdManager();
    }

    public AbstractCommonManagerIdKey(String name) {
        super(name);
        this.manageeSet = new java.util.HashMap<>();
        AbstractCommonManagerIdKey.uniqueIdManager.createUniqueIdSetWithDefaultLength(name);
    }

    /**
     * Get the managee with the given key.
     * <p>
     * this uses {@link java.util.Map#get(Object)} to get the managee.
     * 
     * @param key the key of the managee to get
     * @return the managee with the given key or null if there is no managee with the given key
     */
    public MANAGEE get(String key) {
        return this.manageeSet.get(key);
    }

    public MANAGEE update(String key, MANAGEE managee) {
        return this.manageeSet.put(key, managee);
    }

    @Override
    public boolean add(MANAGEE managee) {
        if(managee == null || managee.hasUniqueId()) {
            return false;
        }
        managee.setUniqueId(AbstractCommonManagerIdKey.uniqueIdManager.generateAndReceiveIdForGivenSet(this.name));
        this.manageeSet.put(managee.getUniqueId(), managee);
        return true;
    }

    @Override
    public boolean addAll(java.util.Collection<? extends MANAGEE> manageeSet) {
        boolean result = true;
        for(MANAGEE managee : manageeSet) {
            result = result && this.add(managee);
        }
        return result;
    }

    @Override
    public void clear() {
        this.manageeSet.clear();
    }

    @Override
    public boolean contains(Object managee) {
        @SuppressWarnings("unchecked")
        MANAGEE manageeTypecast = (MANAGEE) managee;
        return this.manageeSet.containsKey(manageeTypecast.getUniqueId());
    }

    @Override
    public boolean containsAll(java.util.Collection<?> manageeSet) {
        for(MANAGEE managee : this.manageeSet.values()) {
            if(!this.contains(managee)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean isEmpty() {
        return this.manageeSet.isEmpty();
    }

    @Override
    public java.util.Iterator<MANAGEE> iterator() {
        return this.manageeSet.values().iterator();
    }

    @Override
    public boolean remove(Object managee) {
        if(managee == null || !this.contains(managee)) {
            return false;
        }
        @SuppressWarnings("unchecked")
        MANAGEE manageeTypecast = (MANAGEE) managee;
        return this.manageeSet.remove(manageeTypecast.getUniqueId()) != null;
    }

    @Override
    public boolean removeAll(java.util.Collection<?> manageeSet) {
        boolean result = true;
        for(Object managee : manageeSet) {
            result = result && this.remove(managee);
        }
        return result;
    }

    @Override
    public boolean retainAll(java.util.Collection<?> manageeSet) {
        for(MANAGEE managee : this.manageeSet.values()) {
            if(!manageeSet.contains(managee)) {
                this.remove(managee);
            }
        }
        return true;
    }

    @Override
    public int size() {
        return this.manageeSet.size();
    }

    @Override
    public Object[] toArray() {
        return this.manageeSet.values().toArray();
    }

    @Override
    public <T> T[] toArray(T[] manageeSet) {
        return this.manageeSet.values().toArray(manageeSet);
    }

    @Override // TODO look at this
    public String toString() {
        // use string builder to build the json string of the managee set
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[");
        for(MANAGEE managee : this.manageeSet.values()) {
            stringBuilder.append("\"").append(managee.toString()).append("\"").append(",");
        }
        if(this.manageeSet.size() > 0) {
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        }
        stringBuilder.append("]");
        return StringUtil.prettyPrintJson(stringBuilder.toString());
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null || !(obj instanceof AbstractCommonManagerIdKey)) {
            return false;
        }
        @SuppressWarnings("unchecked")
        AbstractCommonManagerIdKey<MANAGEE> objTypecast = (AbstractCommonManagerIdKey<MANAGEE>) obj;
        return this.name.equals(objTypecast.name) && this.manageeSet.equals(objTypecast.manageeSet);
    }
}
