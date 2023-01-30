package com.rumpus.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.rumpus.common.ApiDB.Api;
import com.rumpus.common.ApiDB.ApiDB;
import com.rumpus.common.ApiDB.IApi;
import com.rumpus.common.ApiDB.IApiDB;

public class Dao<T extends Model<T>> extends RumpusObject implements IDao<T> {

    private final static String NAME = "rawDao";
    // protected IApiDB<T, API> apiDB;
    IApi<T> api;
    // protected static Map<String, IApiDB<T>> apiNames;
    // protected static JdbcTemplate jdbcTemplate; // maybe abstract this to allow for other implementations
    // protected final String apiName;
    protected final String table;
    protected final String idName;
    // protected final Mapper<T> mapper;
    // protected final Function<T, T> add;

    static {
        // jdbcTemplate = new JdbcTemplate();
        // apiNames = new HashMap<>();
    }

    public Dao(IApi<T> api, String table, String idName) {
        super(NAME);
        this.api = api;
        // this.mapper = api.mapper;
        // this.add = add;
        this.table = table;
        this.idName = idName;
    }

    // public static int addApiDB(String name, IApiDB<?> apiName) {
    //     return apiNames.put(name, apiName) == null ? ERROR : SUCCESS;
    // }

    // @Autowired
    // public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
    //     Dao.jdbcTemplate = jdbcTemplate;
    // }

    @Override
    public boolean remove(int id) {
        LOG.info("Dao::remove()");
        // // TODO: Check dependencies to delete
        // StringBuilder sb = new StringBuilder();
        // sb.append("DELETE FROM ")
        //     .append(table)
        //     .append(" WHERE ")
        //     .append(id)
        //     .append(" = ?;");
        // final String sql = sb.toString();
        // return jdbcTemplate.update(sql, id) > 0;
        return api.remove(id);    
    }

    @Override
    public T get(int id) {
        LOG.info("Dao::get()");
        // StringBuilder sb = new StringBuilder();
        // sb.append("SELECT * FROM ")
        //     .append(table)
        //     .append(" WHERE ")
        //     .append(id)
        //     .append(" = ?;");
        // final String sql = sb.toString();
        // LOG.info(sql);
        // return jdbcTemplate.queryForObject(sql, mapper, id);
        return api.get(id);
    }

    @Override
    public List<T> getAll() {
        LOG.info("Dao::getAll()");
        // StringBuilder sb = new StringBuilder();
        // sb.append("SELECT * FROM ").append(table).append(";");
        // final String sql = sb.toString();
        // LOG.info(sql);
        // List<T> objects = jdbcTemplate.query(sql, mapper);
        // return objects;
        return api.getAll();
    }

    @Override
    public T add(T model) {
        LOG.info("Dao::add()");
        // return add.apply(model);
        return api.add(model);
    }

    @Override
    public String getTable() {
        return this.table;
    }

    @Override
    public Mapper<T> getMapper() {
        return this.api.getMapper();
    }

    // @Override
    // public Function<T, T> getAddFunction() {
    //     return this.add;
    // }

    @Override
    public boolean removeAll() {
        // LOG.info("Dao::removeAll()");
        // // TODO: Check dependencies to delete
        // StringBuilder sb = new StringBuilder();
        // sb.append("DELETE FROM ")
        //     .append(table)
        //     .append(";");
        // final String sql = sb.toString();
        // return jdbcTemplate.update(sql) > 0;
        return false;
    }

    @Override
    public T update(T model) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long countAll(Map<String, Object> params) {
        // TODO Auto-generated method stub
        return 0;
    }
}
