package com.rumpus.common;

import java.util.List;
import java.util.Map;

// TODO have a list of registered ApiDBs
// Right now the only one is ApiDBJdbc.java

public class ApiDB<MODEL extends Model<MODEL>> extends RumpusObject implements IApiDB<MODEL> {

    private static final String NAME = "rawApiDB";
    private boolean initialized;
    protected String table;
    protected Mapper<MODEL> mapper;

    public ApiDB(){
        super(NAME);
        this.initialized = false;
    }
    public ApiDB(String name, String table, Mapper<MODEL> mapper) {
        super(name);
        this.table = table;
        this.mapper = mapper;
        this.initialized = true;
    }

    @Override
    public boolean isInitialized() {
        return this.initialized;
    }

    @Override
    public boolean remove(int id) {
        LOG.info("ApiDB::remove()");
        return false;
    }

    @Override
    public boolean removeAll() {
        LOG.info("ApiDB::removeAll()");
        return false;
    }

    @Override
    public MODEL get(int id) {
        LOG.info("ApiDB::get()");
        return null;
    }

    @Override
    public List<MODEL> get(Map<String, String> constraints) {
        LOG.info("ApiDB::get()");
        return null;
    }

    @Override
    public List<MODEL> getAll() {
        LOG.info("ApiDB::getAll()");
        return null;
    }

    @Override
    public MODEL add(MODEL model) {
        LOG.info("ApiDB::add()");
        return null;
    }

    @Override
    public Mapper<MODEL> getMapper() {
        LOG.info("ApiDB::getMapper()");
        return null;
    }

    public int setTable(String table) {
        this.table = table;
        return SUCCESS;
    }

    public int setMapper(Mapper<MODEL> mapper) {
        this.mapper = mapper;
        return SUCCESS;
    }
}
