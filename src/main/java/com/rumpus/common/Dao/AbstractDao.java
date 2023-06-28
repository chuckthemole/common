package com.rumpus.common.Dao;

import java.util.List;
import java.util.Map;

import com.rumpus.common.Model.AbstractModel;
import com.rumpus.common.AbstractCommonObject;
import com.rumpus.common.Dao.jdbc.Mapper;

public abstract class AbstractDao<MODEL extends AbstractModel<MODEL>> extends AbstractCommonObject implements IDao<MODEL> {

    protected IApiDB<MODEL> api;
    protected final String table;
    protected final String metaTable;

    public AbstractDao(String table, String metaTable, String name) {
        super(name);
        this.api = null;
        this.table = table;
        this.metaTable = metaTable;
    }
    public AbstractDao(IApiDB<MODEL> api, String table, String metaTable, String name) {
        super(name);
        this.api = api;
        this.table = table;
        this.metaTable = metaTable;
    }

    @Override
    public boolean remove(int id) {
        LOG.info("Dao::remove()");
        return this.api.isInitialized() ? this.api.remove(id) : false;
    }

    @Override
    public boolean remove(String name) {
        LOG.info("Dao::remove()");
        return this.api.isInitialized() ? this.api.remove(name) : false;
    }

    @Override
    public boolean removeAll() {
        LOG.info("Dao::removeAll()");
        return this.api.isInitialized() ? this.api.removeAll() : false;
    }

    @Override
    public MODEL get(int id) {
        LOG.info("Dao::get()");
        return this.api.isInitialized() ? this.api.get(id) : null;
    }

    @Override
    public MODEL get(String name) {
        LOG.info("Dao::get()");
        return this.api.isInitialized() ? this.api.get(name) : null;
    }

    @Override
    public List<MODEL> get(Map<String, String> constraints) {
        LOG.info("Dao::get()");
        return this.api.isInitialized() ? this.api.get(constraints) : null;
    }

    @Override
    public MODEL getById(String id) {
        LOG.info("Dao::getById()");
        return this.api.getById(id);
    }

    @Override
    public List<MODEL> getAll() {
        LOG.info("Dao::getAll()");
        return this.api.isInitialized() ? this.api.getAll() : null;
    }

    @Override
    public MODEL add(MODEL model) {
        LOG.info("Dao::add()");
        return this.api.isInitialized() ? this.api.add(model) : null;
    }

    @Override
    public MODEL update(String id, MODEL updatedModel) {
        LOG.info("Dao::update()");
        return this.api.update(id, updatedModel);
    }

    // @Override
    // public MODEL update(String id, MODEL updatedModel, String condition) {
    //     LOG.info("Dao::update()");
    //     return this.api.update(id, updatedModel, condition);
    // }

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
    public Mapper<MODEL> getMapper() {
        LOG.info("Dao::getMapper()");
        return this.api.getMapper();
    }

    @Override
    public int setApiDB(IApiDB<MODEL> api) {
        this.api = api;
        return SUCCESS;
    }
}
