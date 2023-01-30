package com.rumpus.common.ApiDB;

import javax.sql.DataSource;
import javax.xml.crypto.Data;

import com.rumpus.common.RumpusObject;

public class RumpusDataSource extends RumpusObject implements IRumpusDataSource {

    private DataSource dataSource;

    public RumpusDataSource(String name, DataSource dataSource) {
        super(name);
        this.dataSource = dataSource;
    }
    
    @Override
    public DataSource get() {
        return dataSource;
    }
    @Override
    public int set(DataSource dataSource) {
        this.dataSource = dataSource;
        return SUCCESS;
    }
}
