package com.rumpus.common.Config;

import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;

/**
 * Used to customize the port of a server.
 * <p>
 * Be sure to add the {@link org.springframework.stereotype.Component} annotation to the child class.
 * <p>
 * Also, be sure to add the {@link org.springframework.beans.factory.annotation.Autowired} annotation to the child class's constructor, to inject the port.
 * <p>
 * Look at RumpusPortCustomizer and AdminPortCustomizer for examples.
 * <p>
 * The bean for the port is created in the {@link com.rumpus.common.Config.AbstractCommonConfig} class.
 */
public abstract class AbstractServerPortCustomizer extends AbstractCommonConfig implements WebServerFactoryCustomizer<ConfigurableWebServerFactory> {

    private com.rumpus.common.Server.Port.IPort port;

    /**
     * Ctor
     * @param port port to set
     */
    public AbstractServerPortCustomizer(com.rumpus.common.Server.Port.IPort port) {
        if(port != null) {
            this.port = port;
        } else {
            LOG_THIS_ERROR("Port is null. Setting port to NO_PORT.");
            com.rumpus.common.Server.Port.Port.create(com.rumpus.common.Server.Port.IPort.NO_PORT);
        }
    }

    @Override
    public void customize(ConfigurableWebServerFactory factory) {
        LOG_THIS("AbstractServerPortCustomizer::customize()");
        LOG_THIS("Setting port to ", this.port.getPort(), ".");
        if(this.port != null) {
            factory.setPort(Integer.valueOf(this.port.getPort()));
        } else {
            LOG_THIS("Port is null. Not customize setting port.");
        }
    }

    public String getPort() {
        return this.port.getPort();
    }

    private static void LOG_THIS(String... args) {
        com.rumpus.common.Builder.LogBuilder.logBuilderFromStringArgsNoSpaces(AbstractServerPortCustomizer.class, args).info();
    }
    private static void LOG_THIS_ERROR(String... args) {
        com.rumpus.common.Builder.LogBuilder.logBuilderFromStringArgsNoSpaces(AbstractServerPortCustomizer.class, args).error();
    }
}

