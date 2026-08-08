package com.rumpus.common.Service;

import java.util.List;
import java.util.UUID;

import com.rumpus.common.AbstractCommonObject;
import com.rumpus.common.Dao.IDao;
import com.rumpus.common.Model.AbstractModel;

abstract public class AbstractService<
        MODEL extends AbstractModel<MODEL, ?>> extends AbstractCommonObject
        implements
            IService<MODEL> {

    /**
     * The data access object for this service.
     */
    final protected IDao<MODEL> dao;

    public AbstractService(IDao<MODEL> dao) {
        this.dao = dao;
    }

    @Override
    public MODEL getById(UUID id) {
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
    public boolean remove(UUID id) {
        LOG("remove(id)");
        return this.dao.remove(id);
    }

    @Override
    public MODEL update(UUID id, MODEL updatedModel) {
        LOG("update()");
        return this.dao.update(id, updatedModel);
    }
}
