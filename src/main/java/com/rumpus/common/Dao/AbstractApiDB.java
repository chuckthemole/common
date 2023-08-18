package com.rumpus.common.Dao;

import com.rumpus.common.AbstractCommonObject;
import com.rumpus.common.Dao.jdbc.Mapper;
import com.rumpus.common.Model.AbstractModel;
import com.rumpus.common.Model.ModelUniqueIdManager;

// TODO have a list of registered ApiDBs
// Right now the only one is ApiDBJdbc.java
// Maybe make this a singleton?

public abstract class AbstractApiDB<MODEL extends AbstractModel<MODEL>> extends AbstractCommonObject {

    protected boolean initialized;
    protected String table;
    protected String metaTable;
    protected Mapper<MODEL> mapper;
    protected static ModelUniqueIdManager idManager;

    static {
        AbstractApiDB.idManager = ModelUniqueIdManager.getSingletonInstance();
    }

    public AbstractApiDB(String name, AbstractApiDB<MODEL> api) {
        super(name);
        this.table = api.table;
        this.metaTable = api.metaTable;
        this.mapper = api.mapper;
        this.initialized = api.initialized;
    }
    public AbstractApiDB(String name, String table, String metaTable, Mapper<MODEL> mapper) {
        super(name);
        this.table = table;
        this.metaTable = metaTable;
        this.mapper = mapper;
        this.initialized = true;
    }
}
