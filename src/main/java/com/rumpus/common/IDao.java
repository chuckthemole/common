package com.rumpus.common;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

import com.rumpus.common.ApiDB.Api;
import com.rumpus.common.ApiDB.ApiDB;
import com.rumpus.common.ApiDB.IApiDB;

public interface IDao<T extends Model<T>> extends IRumpusObject {
    T get(int id);
    List<T> getAll();
    T add(T model); // maybe call create
    T update(T model);
    boolean remove(int id);
    boolean removeAll();
    String getTable();
    Mapper<T> getMapper();

    /**
     * Method that returns the number of entries from a table that meet some
     * criteria (where clause params)
     *
     * @param params
     *            sql parameters
     * @return the number of records meeting the criteria
     */
    long countAll(Map<String, Object> params);
    
    // Function<T, T> getAddFunction();
}
