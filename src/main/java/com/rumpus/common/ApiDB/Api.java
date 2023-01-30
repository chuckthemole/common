package com.rumpus.common.ApiDB;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;

import com.rumpus.common.Mapper;
import com.rumpus.common.Model;
import com.rumpus.common.RumpusObject;

public class Api<MODEL extends Model<MODEL>> extends RumpusObject implements IApi<MODEL> {

    protected final String table;
    protected final Mapper<MODEL> mapper;

    public Api(String name, String table, Mapper<MODEL> mapper) {
        super(name);
        this.table = table;
        this.mapper = mapper;
    }

    // Add api's below to Api class extention
    // public class JDBC extends Api<MODEL> {
    //     protected static JdbcTemplate jdbcTemplate;
    //     static {jdbcTemplate = new JdbcTemplate();}
    // }

    @Override
    public boolean remove(int id) {
        LOG.info("Api::remove()");
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public MODEL get(int id) {
        LOG.info("Api::get()");
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<MODEL> getAll() {
        LOG.info("Api::getAll()");
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public MODEL add(MODEL model) {
        LOG.info("Api::add()");
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Mapper<MODEL> getMapper() {
        LOG.info("Api::getMapper()");
        // TODO Auto-generated method stub
        return null;
    }
}
