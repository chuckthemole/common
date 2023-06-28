package com.rumpus.common.Model;

/**
 * Class to keep track of ids of models. 
 * Right now we are keeping a singleton instance in ApiDB, registering a new id set in a model's dao, and adding ids on db insert.
 */
public interface IUniqueIdManager {
    /**
     * 
     * @param setName the name of the set to create
     */
    public void createUniqueIdSetWithDefaultLength(final String setName);
    /**
     * 
     * @param setName the name of the set to create
     * @param length the length of the ids
     */
    public void createUniqueIdSetWithSetLength(final String setName, final int length);

    /**
     * 
     * @param name the name of the set of ids to add to
     * @return the generated id or null if there is an error
     */
    public String add(final String name);

    /**
     * 
     * @param key key to check
     * @return true if the id manager contains key
     */
    public boolean contains(final String key);

    /**
     * 
     * @param name the name of the set of ids
     * @param id the id to remove
     * @return true if the set contained the element
     */
    public boolean remove(final String name, final String id);
}
