package com.rumpus.common.Service;

import java.util.List;

import com.rumpus.common.AbstractCommonObject;
import com.rumpus.common.Dao.IDaoJpa;
import com.rumpus.common.Model.AbstractModel;

abstract public class AbstractServiceJpa<MODEL extends AbstractModel<MODEL, ID>, ID> extends AbstractCommonObject
        implements
        IService<MODEL, ID> {

    /**
     * The data access object for this service.
     */
    private IDaoJpa<MODEL, ID> daoJpa;

    public AbstractServiceJpa(IDaoJpa<MODEL, ID> daoJpa) {
        this.daoJpa = daoJpa;
    }

    @Override
    public MODEL getById(ID id) {
        LOG("getById(id)");
        return this.daoJpa.getReferenceById(id);
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
    public boolean remove(ID id) {
        LOG("remove(id)");
        this.daoJpa.deleteById(id);
        return true; // TODO: obvious hack for now
    }

    @Override
    public MODEL update(ID id, MODEL updatedModel) { // TODO: doesn't need id
        LOG("update()");
        return this.daoJpa.save(updatedModel);
    }
}
