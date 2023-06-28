package com.rumpus.common.Config;

import java.sql.Driver;

import org.springframework.jdbc.datasource.embedded.ConnectionProperties;

/**
 * TODO: not using this class atm. Maybe look into how we can use with common config.
 */
abstract public class AbstractConnectionProperties implements ConnectionProperties {

    private Class<?> driverClass;
    private String url;
    private String username;
    private String password;

    public AbstractConnectionProperties() {}
    public AbstractConnectionProperties(String driverClass, String url, String username, String password) throws ClassNotFoundException {
        this.driverClass = Class.forName(driverClass);
        if(!Driver.class.isAssignableFrom(this.driverClass)) {
            throw new IllegalArgumentException("ERROR: the driver class provided is not a subclass of Driver.");
        }
        this.url = url;
        this.username = username;
        this.password = password;
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
}
