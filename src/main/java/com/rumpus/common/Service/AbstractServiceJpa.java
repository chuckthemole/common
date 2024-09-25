package com.rumpus.common.Service;

import java.util.List;

import com.rumpus.common.AbstractCommonObject;
import com.rumpus.common.ICommon;
import com.rumpus.common.Dao.IDaoJpa;
import com.rumpus.common.Logger.AbstractCommonLogger.LogLevel;
import com.rumpus.common.Model.AbstractModel;

abstract public class AbstractServiceJpa<MODEL extends AbstractModel<MODEL, ?>> extends AbstractCommonObject implements IService<MODEL> {

    /**
     * The data access object for this service.
     */
    private IDaoJpa<MODEL> daoJpa;

    public AbstractServiceJpa(String name, IDaoJpa<MODEL> daoJpa) {
        super(name);
        this.daoJpa = daoJpa;
    }

    @Override
    public MODEL getById(String id) {
        LOG_THIS("getById(id)");
        return this.daoJpa.getById(id);
    }

    @Override
    public List<MODEL> getByColumnValue(String column, String value) {
        LOG_THIS(LogLevel.ERROR, "getByColumnValue(column, value) not implemented");
        return null;
    }

    @Override
    public List<MODEL> getAll() {
        LOG_THIS("getAll()");
        return this.daoJpa.findAll();
    }

    @Override
    public MODEL add(MODEL rumpusModel) {
        LOG_THIS("add()");
        return this.daoJpa.save(rumpusModel);
    }

    @Override
    public boolean remove(String id) {
        LOG_THIS("remove(id)");
        this.daoJpa.deleteById(id);
        return true; // TODO: obvious hack for now
    }

    @Override
    public MODEL update(String id, MODEL updatedModel) { // TODO: doesn't need id
        LOG_THIS("update()");
        return this.daoJpa.save(updatedModel);
    }

    private static void LOG_THIS(String... args) {
        ICommon.LOG(AbstractServiceJpa.class, args);
    }

    private static void LOG_THIS(LogLevel level, String... args) {
        ICommon.LOG(AbstractServiceJpa.class, level, args);
    }
}
