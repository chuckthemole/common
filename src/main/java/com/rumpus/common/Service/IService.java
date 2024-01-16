package com.rumpus.common.Service;

import com.rumpus.common.Manager.IManageable;
import java.util.List;

import com.rumpus.common.Model.AbstractModel;

public interface IService<MODEL extends AbstractModel<MODEL>> extends IManageable {
    MODEL get(int id);
    MODEL get(String name);
    /**
     * 
     * @param id MODEL id to look for
     * @return MODEL if found, null if not
     */
    MODEL getById(String id);
    List<MODEL> getAll();
    MODEL add(MODEL rumpusModel);
    boolean remove(int id);
    boolean remove(String name);
    MODEL update(String id, MODEL updatedModel);
    // MODEL update(String id, MODEL updatedModel, String condition);
}
