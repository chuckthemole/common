package com.rumpus.common.Dao;

import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;

import com.rumpus.common.ICommon;
import com.rumpus.common.Model.AbstractModel;

/**
 * Interface for Dao classes
 * 
 * @param <MODEL> the model to use
 * @see AbstractModel
 * @see AbstractDao
 */
public interface IDao<MODEL extends AbstractModel<MODEL, ?>> extends ICommon {

    /**
     * Get a model by its id
     * 
     * @param id to search 
     * @return MODEl if found, null if not found
     */
    MODEL getById(String id);

    /**
     * Get a model by a column value
     * 
     * @param column to search
     * @param value to search
     * @return MODEL if found, null if not found
     */
    List<MODEL> getByColumnValue(String column, String value);

    /**
     * Get a model by a set of constraints
     * 
     * @param constraints to search
     * @return MODEL if found, null if not found
     */
    List<MODEL> getByConstraints(Map<String, String> constraints);

    /**
     * Get all models
     * 
     * @return {@link List} of all models
     */
    List<MODEL> getAll();

    /**
     * Add a model to the database
     * 
     * @param model to add
     * @return MODEL if added, null if not
     */
    MODEL add(MODEL model);

    /**
     * Update a model in the database
     * 
     * @param id of the model to update
     * @param updatedModel to update
     * @return MODEL if updated, null if not
     */
    MODEL update(String id, MODEL updatedModel);

    /**
     * Remove a model from the database
     * 
     * @param id of the model to remove
     * @return true if removed, false if not
     */
    boolean remove(String id);

    /**
     * Remove all models from the database
     * 
     * @return true if removed, false if not
     */
    boolean removeAll();

    /**
     * Get the table name
     * 
     * @return table name
     */
    String getTable();

    /**
     * Set the table
     * 
     * @param table to set
     */
    public void setTable(String table);

    /**
     * Get the meta table name
     * 
     * @return meta table name
     */
    String getMetaTable();

    /**
     * Set the meta table
     * 
     * @param metaTable to set
     */
    public void setMetaTable(String metaTable);

    /**
     * Get the mapper
     * 
     * @return {@link RowMapper} for this model
     */
    RowMapper<MODEL> getMapper();

    /**
     * Creates a set that keeps track of ids for the given name
     * this has a default length for ids, managed by UniqueIdManager
     * 
     * @param name the name of the set to register
     */
    public static void registerIdSet(final String name) {
        AbstractDao.idManager.createUniqueIdSetWithDefaultLength(name);
    }
    /**
     * Creates a set that keeps track of ids for the given name
     * 
     * @param name the name of the set to register
     * @param length the length of the ids in this set
     */
    public static void registerIdSet(final String name, final int length) {
        AbstractDao.idManager.createUniqueIdSetWithSetLength(name, length);
    }
}
