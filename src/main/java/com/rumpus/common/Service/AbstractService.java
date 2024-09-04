package com.rumpus.common.Service;

import java.util.List;

import com.rumpus.common.AbstractCommonObject;
import com.rumpus.common.Dao.IDao;
import com.rumpus.common.Model.AbstractModel;

abstract public class AbstractService<MODEL extends AbstractModel<MODEL>> extends AbstractCommonObject implements IService<MODEL> {

    protected IDao<MODEL> dao;

    public AbstractService(String name, IDao<MODEL> dao) {
        super(name);
        this.dao = dao;
    }

    @Override
    public MODEL getById(String id) {
        LOG("Service::getById(id)");
        return this.dao.getById(id);
    }

    @Override
    public List<MODEL> getByColumnValue(String column, String value) {
        return this.dao.getByColumnValue(column, value);
    }

    @Override
    public List<MODEL> getAll() {
        LOG("Service::getAll()");
        return this.dao.getAll();
    }

    @Override
    public MODEL add(MODEL rumpusModel) {
        LOG("Service::add()");
        return this.dao.add(rumpusModel);
    }

    @Override
    public boolean remove(int id) {
        LOG("Service::remove(id)");
        return this.dao.remove(id);
    }

    @Override
    public boolean remove(String name) {
        LOG("Service::remove(name)");
        return this.dao.remove(name);
    }

    @Override
    public MODEL update(String id, MODEL updatedModel) {
        LOG("Service::update()");
        return this.dao.update(id, updatedModel);
    }
}
