package com.rumpus.common.Service;

import java.util.List;

import com.rumpus.common.AbstractCommonObject;
import com.rumpus.common.Dao.IDao;
import com.rumpus.common.Model.AbstractModel;

// import org.springframework.stereotype.Service; TODO: maybe use this annotation here, if so, need to change name of class, since currently conflicts with same name

// @Service
abstract public class AbstractService<MODEL extends AbstractModel<MODEL>> extends AbstractCommonObject {

    protected static final String NAME = "rawService";

    protected IDao<MODEL> dao;

    public AbstractService() {super(NAME);} // TODO: Delete?
    public AbstractService(String name, IDao<MODEL> dao) {
        super(name);
        this.dao = dao;
    }

    public MODEL get(int id) {
        LOG.info("Service::get(id)");
        return this.dao.get(id);
    }

    public MODEL get(String name) {
        LOG.info("Service::get(name)");
        return this.dao.get(name);
    }

    public MODEL getById(String id) {
        LOG.info("Service::getById(id)");
        return this.dao.getById(id);
    }

    public List<MODEL> getAll() {
        LOG.info("Service::getAll()");
        return this.dao.getAll();
    }

    public MODEL add(MODEL rumpusModel) {
        LOG.info("Service::add()");
        return this.dao.add(rumpusModel);
    }

    public boolean remove(int id) {
        LOG.info("Service::remove(id)");
        return this.dao.remove(id);
    }

    public boolean remove(String name) {
        LOG.info("Service::remove(name)");
        return this.dao.remove(name);
    }

    public MODEL update(String id, MODEL updatedModel) {
        LOG.info("Service::update()");
        return this.dao.update(id, updatedModel);
    }
    // @Override
    // public MODEL update(String id, MODEL updatedModel, String condition) {
    //     return this.dao.update(id, updatedModel, condition);
    // }
}
