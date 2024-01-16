package com.rumpus.common.Service;

import com.rumpus.common.Manager.IManageable;
import java.util.List;

import com.rumpus.common.Model.AbstractModel;

/**
 * Service interface
 * <p>
 * This interface defines the methods that all services must implement
 * <p>
 * This interface is used by the {@link AbstractService} class
 */
public interface IService<MODEL extends AbstractModel<MODEL>> extends IManageable {
    /**
     * Get the MODEL with the given id
     * 
     * @param id MODEL id to look for
     * @return MODEL if found, null if not
     */
    MODEL get(int id);

    /**
     * Get the MODEL with the given name
     * 
     * @param name MODEL name to look for
     * @return MODEL if found, null if not
     */
    MODEL get(String name);

    /**
     * Get the MODEL with the given id
     * 
     * @param id MODEL id to look for
     * @return MODEL if found, null if not
     */
    MODEL getById(String id);

    /**
     * Get all the MODELs from this service
     * 
     * @return List of MODELs
     */
    List<MODEL> getAll();

    /**
     * Add a MODEL using this service
     * 
     * @param rumpusModel MODEL to add
     * @return MODEL if added, null if not
     */
    MODEL add(MODEL rumpusModel);

    /**
     * Remove a MODEL using this service
     * 
     * @param id MODEL id to remove
     * @return true if removed, false if not
     */
    boolean remove(int id);

    /**
     * Remove a MODEL using this service
     * 
     * @param name MODEL name to remove
     * @return true if removed, false if not
     */
    boolean remove(String name);

    /**
     * Update a MODEL using this service
     * 
     * @param id MODEL id to update
     * @param updatedModel MODEL to update with
     * @return MODEL if updated, null if not
     */
    MODEL update(String id, MODEL updatedModel);
}
