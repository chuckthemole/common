package com.rumpus.common.Service;

import java.util.List;

import com.rumpus.common.Model.AbstractModel;

public interface IService<T extends AbstractModel<T>> {
    T get(int id);
    T get(String name);
    /**
     * 
     * @param id MODEL id to look for
     * @return MODEL if found, null if not
     */
    T getById(String id);
    List<T> getAll();
    T add(T rumpusModel);
    boolean remove(int id);
    boolean remove(String name);
    T update(String id, T updatedModel);
    // T update(String id, T updatedModel, String condition);
}
