package com.rumpus.common.Dao;

import java.util.List;
import java.util.Map;

import com.rumpus.common.Model.AbstractModel;
import com.rumpus.common.Dao.jdbc.Mapper;

/**
 * Abstract Data Access Object (Dao)
 */
public abstract class AbstractDao<MODEL extends AbstractModel<MODEL>> extends AbstractApiDB<MODEL> {

    public AbstractDao(String name, String table, String metaTable, Mapper<MODEL> mapper) {
        super(name, table, metaTable, mapper);
        this.table = table;
        this.metaTable = metaTable;
    }

    abstract public boolean remove(int id);
    abstract public boolean remove(String name);
    abstract public boolean removeAll();
    abstract public MODEL get(int id);
    abstract public MODEL get(String name);
    abstract public List<MODEL> get(Map<String, String> constraints);
    abstract public MODEL getById(String id);
    abstract public List<MODEL> getAll();
    abstract public MODEL add(MODEL model);
    abstract public MODEL update(String id, MODEL updatedModel);
    // abstract public long countAll(Map<String, Object> params);
    abstract public Mapper<MODEL> getMapper();
    // abstract public int setApiDB(AbstractApiDB<MODEL> api);

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
