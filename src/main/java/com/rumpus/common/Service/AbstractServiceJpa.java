package com.rumpus.common.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.rumpus.common.ICommon;
import com.rumpus.common.Dao.IDao;
import com.rumpus.common.Dao.IDaoJpa;
import com.rumpus.common.Logger.AbstractCommonLogger.LogLevel;
import com.rumpus.common.Model.AbstractModel;

abstract public class AbstractServiceJpa<MODEL extends AbstractModel<MODEL>> extends AbstractService<MODEL> {

    @Autowired protected IDaoJpa<MODEL> daoJpa;

    public AbstractServiceJpa(String name, IDao<MODEL> dao) {
        super(name, dao);
    }

    @Override
    public MODEL getById(String id) {
        LOG_THIS("getById(id)");
        return this.dao.getById(id);
    }

    @Override
    public List<MODEL> getByColumnValue(String column, String value) {
        return this.dao.getByColumnValue(column, value);
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
