package com.rumpus.common.Service;

import java.util.List;

import com.rumpus.common.AbstractCommonObject;
import com.rumpus.common.ICommon;
import com.rumpus.common.Dao.IDao;
import com.rumpus.common.Logger.AbstractCommonLogger.LogLevel;
import com.rumpus.common.Model.AbstractModel;
import com.rumpus.common.Serializer.ICommonSerializer;

abstract public class AbstractService<MODEL extends AbstractModel<MODEL, ?>> extends AbstractCommonObject implements IService<MODEL> {

    /**
     * The data access object for this service.
     */
    private IDao<MODEL> dao;

    public AbstractService(String name, IDao<MODEL> dao) {
        super(name);
        this.dao = dao;
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
        return this.dao.getAll();
    }

    @Override
    public MODEL add(MODEL rumpusModel) {
        LOG_THIS("add()");
        return this.dao.add(rumpusModel);
    }

    @Override
    public boolean remove(String id) {
        LOG_THIS("remove(id)");
        return this.dao.remove(id);
    }

    @Override
    public MODEL update(String id, MODEL updatedModel) {
        LOG_THIS("update()");
        return this.dao.update(id, updatedModel);
    }

    private static void LOG_THIS(String... args) {
        ICommon.LOG(AbstractService.class, args);
    }

    private static void LOG_THIS(LogLevel level, String... args) {
        ICommon.LOG(AbstractService.class, level, args);
    }
}
