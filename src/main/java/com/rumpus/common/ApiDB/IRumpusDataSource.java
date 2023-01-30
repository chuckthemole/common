package com.rumpus.common.ApiDB;

import javax.sql.DataSource;

import com.rumpus.common.IRumpusObject;

public interface IRumpusDataSource extends IRumpusObject {
    public DataSource get();
    public int set(DataSource dataSource);
}
