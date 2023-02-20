package com.rumpus.common;

import javax.sql.DataSource;

import org.springframework.jdbc.datasource.embedded.ConnectionProperties;
import org.springframework.jdbc.datasource.embedded.DataSourceFactory;

public class DSFactory implements DataSourceFactory {

    private ConnectionProperties properties;
    private DataSource dataSource;

    public DSFactory(ConnectionProperties properties, DataSource dataSource) {
        this.properties = properties;
        this.dataSource = dataSource;
    }

    @Override
    public ConnectionProperties getConnectionProperties() {
        return properties;
    }

    @Override
    public DataSource getDataSource() {
        return this.dataSource;
    }
    
}
