package com.rumpus.common.Manager;

public interface ISetItem extends IManageable {
    /**
     * Sets the unique id of the object.
     * 
     * @param id the unique id of the object
     */
    public void setUniqueId(String id);

    /**
     * Gets the unique id of the object.
     * 
     * @return the unique id of the object or null if there is no unique id
     */
    public String getUniqueId();

    /**
     * Checks if the object has a unique id.
     * 
     * @return true if the object has a unique id, false otherwise
     */
    public boolean hasUniqueId();
}
