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
    public MODEL get(int id) {
        LOG.info("Service::get(id)");
        return this.dao.get(id);
    }

    @Override
    public MODEL get(String name) {
        LOG.info("Service::get(name)");
        return this.dao.get(name);
    }

    @Override
    public MODEL getById(String id) {
        LOG.info("Service::getById(id)");
        return this.dao.getById(id);
    }

    @Override
    public List<MODEL> getAll() {
        LOG.info("Service::getAll()");
        return this.dao.getAll();
    }

    @Override
    public MODEL add(MODEL rumpusModel) {
        LOG.info("Service::add()");
        return this.dao.add(rumpusModel);
    }

    @Override
    public boolean remove(int id) {
        LOG.info("Service::remove(id)");
        return this.dao.remove(id);
    }

    @Override
    public boolean remove(String name) {
        LOG.info("Service::remove(name)");
        return this.dao.remove(name);
    }

    @Override
    public MODEL update(String id, MODEL updatedModel) {
        LOG.info("Service::update()");
        return this.dao.update(id, updatedModel);
    }
}
