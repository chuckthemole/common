package com.rumpus.common.Dao;

import java.util.List;
import java.util.Map;

import com.rumpus.common.IUniqueIdManager;
import com.rumpus.common.Mapper;
import com.rumpus.common.AbstractModel;
import com.rumpus.common.RumpusObject;
import com.rumpus.common.UniqueIdManager;

// TODO have a list of registered ApiDBs
// Right now the only one is ApiDBJdbc.java
// Maybe make this a singleton?

//TODO: think about moving orm layer stuff into a package

public abstract class AbstractApiDB<MODEL extends AbstractModel<MODEL>> extends RumpusObject implements IApiDB<MODEL> {

    protected boolean initialized;
    protected String table;
    protected Mapper<MODEL> mapper;
    protected static IUniqueIdManager idManager;

    static {
        AbstractApiDB.idManager = UniqueIdManager.getSingletonInstance();
    }

    public AbstractApiDB(String name, String table, Mapper<MODEL> mapper) {
        super(name);
        this.table = table;
        this.mapper = mapper;
        this.initialized = true;
    }
}
