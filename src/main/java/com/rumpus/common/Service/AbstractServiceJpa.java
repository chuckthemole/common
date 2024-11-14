package com.rumpus.common.Service;

import java.util.List;

import com.rumpus.common.AbstractCommonObject;
import com.rumpus.common.Dao.IDaoJpa;
import com.rumpus.common.Log.ICommonLogger.LogLevel;
import com.rumpus.common.Model.AbstractModel;

abstract public class AbstractServiceJpa<MODEL extends AbstractModel<MODEL, ?>> extends AbstractCommonObject implements IService<MODEL> {

    /**
     * The data access object for this service.
     */
    private IDaoJpa<MODEL> daoJpa;

    public AbstractServiceJpa(IDaoJpa<MODEL> daoJpa) {
        this.daoJpa = daoJpa;
    }

    @Override
    public MODEL getById(String id) {
        LOG("getById(id)");
        return this.daoJpa.getById(id);
    }

    @Override
    public List<MODEL> getByColumnValue(String column, String value) {
        LOG(LogLevel.ERROR, "getByColumnValue(column, value) not implemented");
        return null;
    }

    @Override
    public List<MODEL> getAll() {
        LOG("getAll()");
        return this.daoJpa.findAll();
    }

    @Override
    public MODEL add(MODEL rumpusModel) {
        LOG("add()");
        return this.daoJpa.save(rumpusModel);
    }

    @Override
    public boolean remove(String id) {
        LOG("remove(id)");
        this.daoJpa.deleteById(id);
        return true; // TODO: obvious hack for now
    }

    @Override
    public MODEL update(String id, MODEL updatedModel) { // TODO: doesn't need id
        LOG("update()");
        return this.daoJpa.save(updatedModel);
    }
}
