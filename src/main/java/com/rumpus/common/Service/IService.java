package com.rumpus.common.Service;

import com.rumpus.common.Manager.IManageable;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rumpus.common.Model.AbstractModel;

/**
 * Service interface
 * <p>
 * This interface defines the methods that all services must implement
 * <p>
 * This interface is used by the {@link AbstractService} class
 */
@Service
public interface IService<MODEL extends AbstractModel<MODEL>> extends IManageable {

    /**
     * Get the MODEL with the given id
     * 
     * @param id MODEL id to look for
     * @return MODEL if found, null if not
     */
    public MODEL getById(String id);

    /**
     * Get the MODEL with the given column name and value
     * 
     * @param column Column name to look for
     * @param value Column value to look for
     * @return MODEL if found, null if not
     */
    public java.util.List<MODEL> getByColumnValue(String column, String value);

    /**
     * Get all the MODELs from this service
     * 
     * @return List of MODELs
     */
    public List<MODEL> getAll();

    /**
     * Add a MODEL using this service
     * 
     * @param rumpusModel MODEL to add
     * @return MODEL if added, null if not
     */
    @Transactional(rollbackFor = Exception.class)
    public MODEL add(MODEL rumpusModel);

    /**
     * Remove a MODEL using this service
     * 
     * @param id MODEL id to remove
     * @return true if removed, false if not
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean remove(String id);

    /**
     * Update a MODEL using this service
     * 
     * @param id MODEL id to update
     * @param updatedModel MODEL to update with
     * @return MODEL if updated, null if not
     */
    @Transactional(rollbackFor = Exception.class)
    public MODEL update(String id, MODEL updatedModel);
}
