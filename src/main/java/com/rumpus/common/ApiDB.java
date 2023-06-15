package com.rumpus.common;

import java.util.List;
import java.util.Map;

// TODO have a list of registered ApiDBs
// Right now the only one is ApiDBJdbc.java
// Maybe make this a singleton?

//TODO: think about moving orm layer stuff into a package

abstract class ApiDB<MODEL extends Model<MODEL>> extends RumpusObject implements IApiDB<MODEL> {

    protected boolean initialized;
    protected String table;
    protected Mapper<MODEL> mapper;
    protected static IUniqueIdManager idManager;

    static {
        ApiDB.idManager = UniqueIdManager.getSingletonInstance();
    }

    public ApiDB(String name, String table, Mapper<MODEL> mapper) {
        super(name);
        this.table = table;
        this.mapper = mapper;
        this.initialized = true;
    }
}
