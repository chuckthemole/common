package com.rumpus.common.Dao;

import java.util.List;
import java.util.Map;

import com.rumpus.common.ICommon;
import com.rumpus.common.Dao.jdbc.Mapper;
import com.rumpus.common.Model.AbstractModel;

public interface IDao<MODEL extends AbstractModel<MODEL>> extends ICommon {
    MODEL get(String name);
    /**
     * 
     * @param id to search 
     * @return MODEl if found, null if not found
     */
    MODEL getById(String id);
    MODEL get(int id);
    List<MODEL> getByColumnValue(String column, String value);
    List<MODEL> get(Map<String, String> constraints);
    List<MODEL> getAll();
    MODEL add(MODEL model); // maybe call create
    MODEL update(String id, MODEL updatedModel);
    // MODEL update(String id, MODEL updatedModel, String condition);
    boolean remove(int id);
    boolean remove(String id);
    boolean removeAll();
    String getTable();
    Mapper<MODEL> getMapper();
    // public int setApiDB(IApiDB<MODEL> api);

    /**
     * Method that returns the number of entries from a table that meet some
     * criteria (where clause params)
     *
     * @param params
     *            sql parameters
     * @return the number of records meeting the criteria
     */
    // long countAll(Map<String, Object> params);
    
    // Function<MODEL, MODEL> getAddFunction();

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
