package com.rumpus.common;

import java.util.List;

public interface IService<T extends IModel<T>> extends IRumpusObject {
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
    T update(String id, T updatedModel, String condition);
}
