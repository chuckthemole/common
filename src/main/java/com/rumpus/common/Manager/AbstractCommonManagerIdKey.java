package com.rumpus.common.Manager;

import com.rumpus.common.AbstractCommonObject;
import com.rumpus.common.util.StringUtil;
import com.rumpus.common.util.UniqueId.AbstractUniqueIdManager;

/**
 * This class is used to manage a {@link java.util.Set} of objects. It can act like a map with a key of the unique id of the managees.
 * <p>
 * It is a {@link java.util.Set} of {@link ISetItem} objects.
 * <p>
 * It is abstract and must be extended by a child class.
 */
abstract public class AbstractCommonManagerIdKey<MANAGEE extends ISetItem> extends AbstractCommonObject implements java.util.Set<MANAGEE> {

    /**
     * The {@link com.rumpus.common.util.UniqueId.IdSet} object used to manage the unique ids of the managees.
     */
    private com.rumpus.common.util.UniqueId.IdSet idSet;

    /**
     * The {@link java.util.Map} of {@link ISetItem} objects. Used to make this class implement {@link java.util.Set}, and have a get(key) method.
     */
    private java.util.Map<String, MANAGEE> manageeSet;

    /**
     * Ctor for {@link AbstractCommonManagerIdKey}.
     * <p>
     * This ctor creates a {@link java.util.Map} to hold the managees.
     * <p>
     * This ctor also creates a {@link AbstractUniqueIdManager} to manage the unique ids of the managees.
     * 
     * @param name the name of the child class
     * @param collectionName the name of the collection of managees
     */
    public AbstractCommonManagerIdKey() {
        this.manageeSet = new java.util.HashMap<>();
        this.idSet = com.rumpus.common.util.UniqueId.IdSet.createEmptyIdSetWithDefaultLength();
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

    /**
     * Add a managee to the manageeSet. Same as {@link AbstractCommonManagerIdKey#add(ISetItem)} but returns the id of the managee.
     * 
     * @param managee the managee to add
     * @return the id of the managee or null if there is an error
     */
    public String addThenReturnId(MANAGEE managee) {
        if(managee == null) {
            LOG("MANAGEE is null");
            return null;
        }
        if(this.contains(managee)) { // sets are unique
            LOG("MANAGEE already exists");
            return null;
        }
        String id = this.idSet.add();
        managee.setUniqueId(id);
        this.manageeSet.put(id, managee);
        return id;
    }

    @Override
    public boolean add(MANAGEE managee) {
        LOG("AbstractCommonManagerIdKey::add()");
        if(managee == null) {
            LOG("MANAGEE is null");
            return false;
        }
        if(this.contains(managee)) { // sets are unique
            LOG("MANAGEE already exists");
            return false;
        }
        managee.setUniqueId(this.idSet.add());
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
        return this.manageeSet.containsValue(manageeTypecast);
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

    /**
     * Check if the sets are equal by checking if the sets contain the same managees.
     */
    @Override
    public boolean equals(Object obj) {
        // LOG("AbstractCommonManagerIdKey::equals()");
        if(obj == null || !(obj instanceof AbstractCommonManagerIdKey)) {
            LOG("obj is null or not an instance of AbstractCommonManagerIdKey");
            return false;
        }
        @SuppressWarnings("unchecked")
        AbstractCommonManagerIdKey<MANAGEE> objTypecast = (AbstractCommonManagerIdKey<MANAGEE>) obj;
        int count = objTypecast.size(); // verify the sets are the same size
        for(MANAGEE manageeValue : this.manageeSet.values()) {
            if(!objTypecast.contains(manageeValue)) {
                // LOG("obj does not contain managee: ", manageeValue.toString());
                return false;
            }
            count--;
        }
        return count == 0 ? true : false; // count should be 0 if the sets are equal
    }
}
