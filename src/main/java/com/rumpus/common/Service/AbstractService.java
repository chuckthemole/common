package com.rumpus.common.Service;

import java.util.List;

import com.rumpus.common.AbstractCommonObject;
import com.rumpus.common.Dao.IDao;
import com.rumpus.common.Model.AbstractModel;

abstract public class AbstractService<MODEL extends AbstractModel<MODEL, ID>, ID> extends AbstractCommonObject
        implements
        IService<MODEL, ID> {

    /**
     * The data access object for this service.
     */
    final protected IDao<MODEL, ID> dao;

    public AbstractService(IDao<MODEL, ID> dao) {
        this.dao = dao;
    }

    @Override
    public MODEL getById(ID id) {
        LOG("getById(id)");
        return this.dao.getById(id).orElseThrow();
    }

    @Override
    public List<MODEL> getAll() {
        LOG("getAll()");

        List<MODEL> models = this.dao.getAll();

        return models == null
                ? List.of()
                : models;
    }

    @Override
    public MODEL add(MODEL rumpusModel) {
        LOG("add()");
        return this.dao.add(rumpusModel);
    }

    @Override
    public boolean remove(ID id) {
        LOG("remove(id)");
        return this.dao.remove(id);
    }

    @Override
    public MODEL update(ID id, MODEL updatedModel) {
        LOG("update()");
        return this.dao.update(id, updatedModel);
    }
}
