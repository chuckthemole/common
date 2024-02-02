package com.rumpus.common.Server;

import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;

/**
 * Used to customize the port of a server.
 * <p>
 * Be sure to add the {@link org.springframework.stereotype.Component} annotation to the child class.
 */
public class AbstractServerPortCustomizer extends com.rumpus.common.AbstractCommonObject implements WebServerFactoryCustomizer<ConfigurableWebServerFactory> {

    // TODO: make a port manager, or look for one if I started one earlier.

    private String port;

    public AbstractServerPortCustomizer(String name, String port) {
        super(name);
        this.port = port;
    }

    @Override
    public void customize(ConfigurableWebServerFactory factory) {
        factory.setPort(Integer.valueOf(this.port));
    }

    public String getPort() {
        return this.port;
    }

    public void setPort(String port) {
        this.port = port;
        return;
    }
}

