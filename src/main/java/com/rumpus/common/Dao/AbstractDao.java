package com.rumpus.common.Dao;

import java.util.Map;

import com.rumpus.common.Model.AbstractModel;
import com.rumpus.common.Model.ModelUniqueIdManager;
import com.rumpus.common.AbstractCommonObject;
import com.rumpus.common.Dao.jdbc.Mapper;

/**
 * Abstract Data Access Object (Dao)
 */
public abstract class AbstractDao<MODEL extends AbstractModel<MODEL>> extends AbstractCommonObject implements IDao<MODEL> {

    protected boolean initialized;
    protected String table;
    protected String metaTable;
    protected Mapper<MODEL> mapper;
    protected static ModelUniqueIdManager idManager; //TODO: why am I using this?

    static {
        AbstractDao.idManager = ModelUniqueIdManager.getSingletonInstance();
    }

    public AbstractDao(
        String name,
        String table,
        String metaTable,
        Mapper<MODEL> mapper) {
            super(name);
            this.table = table;
            this.metaTable = metaTable;
            this.mapper = mapper;
            this.initialized = true;
            this.metaTable = metaTable;
    }

    public String getTable() {
        return this.table;
    }

    public String getMetaTable() {
        return this.metaTable;
    }

    abstract public void insert(String sqlInsertStatement, Map<String, Object> modelMap);
    abstract public MODEL onInsert(MODEL model, final String sql);
    abstract public MODEL onGet(final String sql);
    abstract public MODEL onGet(final String sql, final String name);

    public boolean isInitialized() {
        return this.initialized;
    }
}
