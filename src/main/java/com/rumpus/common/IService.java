package com.rumpus.common;

import java.util.List;

public interface IService<T extends IModel<T>> extends IRumpusObject {
    T get(int id);
    T get(String name);
    List<T> getAll();
    T add(T rumpusModel);
    boolean remove(int id);
    boolean remove(String name);
    T update(String oldModelKey, T updatedModel);
    T update(String oldModelKey, T updatedModel, String condition);
}
