package com.rumpus.common;

import java.util.List;
import java.util.Map;

public class Dao<T extends Model<T>> extends RumpusObject implements IDao<T> {

    private final static String NAME = "rawDao";
    protected IApiDB<T> api;
    protected final String table;

    // public Dao() {
    //     super(NAME);
    //     this.api = new ApiDB<>();
    //     this.table = NO_NAME;
    // }
    public Dao(String table, String name) {
        super(name);
        this.api = null;
        this.table = table;
    }
    public Dao(IApiDB<T> api, String table, String name) {
        super(name);
        this.api = api;
        this.table = table;
    }

    @Override
    public boolean remove(int id) {
        LOG.info("Dao::remove()");
        return this.api.isInitialized() ? this.api.remove(id) : false;
    }

    @Override
    public boolean remove(String id) {
        LOG.info("Dao::remove()");
        return this.api.isInitialized() ? this.api.remove(id) : false;
    }

    @Override
    public boolean removeAll() {
        LOG.info("Dao::removeAll()");
        return this.api.isInitialized() ? this.api.removeAll() : false;
    }

    @Override
    public T get(int id) {
        LOG.info("Dao::get()");
        return this.api.isInitialized() ? this.api.get(id) : null;
    }

    @Override
    public T get(String id) {
        LOG.info("Dao::get()");
        return this.api.isInitialized() ? this.api.get(id) : null;
    }

    @Override
    public List<T> get(Map<String, String> constraints) {
        LOG.info("Dao::get()");
        return this.api.isInitialized() ? this.api.get(constraints) : null;
    }

    @Override
    public List<T> getAll() {
        LOG.info("Dao::getAll()");
        return this.api.isInitialized() ? this.api.getAll() : null;
    }

    @Override
    public T add(T model) {
        LOG.info("Dao::add()");
        return this.api.isInitialized() ? this.api.add(model) : null;
    }

    @Override
    public T update(T model) {
        LOG.info("Dao::update()");
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long countAll(Map<String, Object> params) {
        LOG.info("Dao::countAll()");
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public String getTable() {
        LOG.info("Dao::getTable()");
        return this.table;
    }

    @Override
    public Mapper<T> getMapper() {
        LOG.info("Dao::getMapper()");
        return this.api.getMapper();
    }

    @Override
    public int setApiDB(IApiDB<T> api) {
        this.api = api;
        return SUCCESS;
    }
}
