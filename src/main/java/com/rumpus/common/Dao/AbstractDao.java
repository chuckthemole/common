package com.rumpus.common.Dao;

import java.util.Map;

import org.springframework.jdbc.core.RowMapper;

import com.rumpus.common.Model.AbstractModel;
import com.rumpus.common.Model.ModelUniqueIdManager;
import com.rumpus.common.AbstractCommonObject;

/**
 * Abstract Data Access Object (Dao)
 * Implementations of {@link IDao} should extend this class
 */
public abstract class AbstractDao<MODEL extends AbstractModel<MODEL>> extends AbstractCommonObject implements IDao<MODEL> {

    /**
     * The table name
     */
    protected String table;
    /**
     * The meta table name
     */
    protected String metaTable;
    /**
     * The {@link RowMapper} for this Dao. Used with JDBC.
     */
    protected RowMapper<MODEL> mapper;
    /**
     * The {@link ModelUniqueIdManager} for this Dao
     */
    protected static ModelUniqueIdManager idManager;

    static {
        AbstractDao.idManager = ModelUniqueIdManager.getSingletonInstance();
    }

    public AbstractDao(
        String name,
        String table,
        String metaTable,
        RowMapper<MODEL> mapper) {
            super(name);
            this.table = table;
            this.metaTable = metaTable;
            this.mapper = mapper;
            this.metaTable = metaTable;
    }

    @Override
    public String getTable() {
        return this.table;
    }

    @Override
    public void setTable(String table) {
        this.table = table;
    }

    @Override
    public String getMetaTable() {
        return this.metaTable;
    }

    @Override
    public void setMetaTable(String metaTable) {
        this.metaTable = metaTable;
    }

    @Override
    public RowMapper<MODEL> getMapper() {
        return this.mapper;
    }

    abstract public void insert(String sqlInsertStatement, Map<String, Object> modelMap);
    abstract public MODEL onInsert(MODEL model, final String sql);
    abstract public MODEL onGet(final String sql);
    abstract public MODEL onGet(final String sql, final String name);
}
