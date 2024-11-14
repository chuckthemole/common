package com.rumpus.common.Service;

import java.util.List;

import com.rumpus.common.AbstractCommonObject;
import com.rumpus.common.Dao.IDao;
import com.rumpus.common.Model.AbstractModel;

abstract public class AbstractService<MODEL extends AbstractModel<MODEL, ?>> extends AbstractCommonObject implements IService<MODEL> {

    /**
     * The data access object for this service.
     */
    private IDao<MODEL> dao;

    public AbstractService(IDao<MODEL> dao) {
        this.dao = dao;
    }

    @Override
    public MODEL getById(String id) {
        LOG("getById(id)");
        return this.dao.getById(id);
    }

    @Override
    public List<MODEL> getByColumnValue(String column, String value) {
        return this.dao.getByColumnValue(column, value);
    }

    @Override
    public List<MODEL> getAll() {
        LOG("getAll()");
        return this.dao.getAll();
    }

    @Override
    public MODEL add(MODEL rumpusModel) {
        LOG("add()");
        return this.dao.add(rumpusModel);
    }

    @Override
    public boolean remove(String id) {
        LOG("remove(id)");
        return this.dao.remove(id);
    }

    @Override
    public MODEL update(String id, MODEL updatedModel) {
        LOG("update()");
        return this.dao.update(id, updatedModel);
    }
}
