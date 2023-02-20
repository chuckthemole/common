package com.rumpus.common;

import java.sql.Driver;

import org.springframework.jdbc.datasource.embedded.ConnectionProperties;

public class DBConnectionProperties implements ConnectionProperties {

    private Class<?> driverClass;
    private String url;
    private String username;
    private String password;

    public DBConnectionProperties() {}
    public DBConnectionProperties(String driverClass, String url, String username, String password) throws ClassNotFoundException {
        this.driverClass = Class.forName(driverClass);
        if(!Driver.class.isAssignableFrom(this.driverClass)) {
            throw new IllegalArgumentException("ERROR: the driver class provided is not a subclass of Driver.");
        }
        this.url = url;
        this.username = username;
        this.password = password;
    }
    public static DBConnectionProperties create(String driverClass, String url, String username, String password) throws ClassNotFoundException {
        return new DBConnectionProperties(driverClass, url, username, password);
    }

    @Override
    public void setDriverClass(Class<? extends Driver> driverClass) {
        this.driverClass = driverClass;
    }

    @Override
    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }

    public Class<? extends Driver> getDriverClass() {
        return (Class<? extends Driver>) this.driverClass;
    }
    public String getUrl() {
        return this.url;
    }
    public String getUsername() {
        return this.username;
    }
    public String getPassword() {
        return this.password;
    }
    
}
