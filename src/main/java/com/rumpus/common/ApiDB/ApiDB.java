package com.rumpus.common.ApiDB;

import java.util.List;

import com.rumpus.common.Mapper;
import com.rumpus.common.Model;
import com.rumpus.common.RumpusObject;

public class ApiDB<MODEL extends Model<MODEL>, API extends Api> extends RumpusObject implements IApiDB<MODEL, API> {
    protected final API apiDB;
    protected String table;
    protected Mapper<MODEL> mapper;

    public ApiDB(String name, String table, API apiDB) {
        super(name);
        this.table = table;
        this.apiDB = apiDB;
    }

    public int setTable(String table) {
        this.table = table;
        return SUCCESS;
    }

    @Override
    public boolean remove(int id) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public MODEL get(int id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<MODEL> getAll() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public MODEL add(MODEL model) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Mapper<MODEL> getMapper() {
        // TODO Auto-generated method stub
        return null;
    }
}
