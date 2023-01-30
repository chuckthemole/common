package com.rumpus.common;

import java.util.List;

public interface IService<T extends IModel<T>> extends IRumpusObject {
    T get(int id);
    List<T> getAll();
    T add(T rumpusModel);
    boolean remove(int id);
}
