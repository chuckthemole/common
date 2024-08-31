package com.rumpus.common.Dao;

import java.util.List;
import java.util.Map;

import com.rumpus.common.Model.AbstractModel;
import com.rumpus.common.Model.ModelUniqueIdManager;
import com.rumpus.common.AbstractCommonObject;
import com.rumpus.common.Dao.jdbc.Mapper;

/**
 * Abstract Data Access Object (Dao)
 */
public abstract class AbstractDao<MODEL extends AbstractModel<MODEL>>
    extends AbstractCommonObject implements IDao<MODEL> {

    protected boolean initialized;
    protected String table;
    protected String metaTable;
    protected Mapper<MODEL> mapper;
    protected static ModelUniqueIdManager idManager; //TODO: why am I using this?

    static {
        AbstractDao.idManager = ModelUniqueIdManager.getSingletonInstance();
    }

    // TODO: see if i'm using this?
    public AbstractDao(String name, AbstractDao<MODEL> dao) {
        super(name);
        this.table = dao.table;
        this.metaTable = dao.metaTable;
        this.mapper = dao.mapper;
        this.initialized = dao.initialized;
    }

    public AbstractDao(String name, String table, String metaTable, Mapper<MODEL> mapper) {
        super(name);
        this.table = table;
        this.metaTable = metaTable;
        this.mapper = mapper;
        this.initialized = true;
        this.metaTable = metaTable;
    }

    // abstract public boolean remove(int id);
    // abstract public boolean remove(String name);
    // abstract public boolean removeAll();
    // abstract public MODEL get(int id);
    // abstract public MODEL get(String name);
    // abstract public List<MODEL> get(Map<String, String> constraints);
    // abstract public MODEL getById(String id);
    // abstract public List<MODEL> getAll();
    // abstract public MODEL add(MODEL model);
    // abstract public MODEL update(String id, MODEL updatedModel);
    // abstract public long countAll(Map<String, Object> params);
    // abstract public Mapper<MODEL> getMapper();
    // abstract public int setApiDB(AbstractApiDB<MODEL> dao);

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
