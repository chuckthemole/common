package com.rumpus.common.Config;

import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;

import com.rumpus.common.AbstractCommonObject;
import com.rumpus.common.Server.Port.IPort;

/**
 * Used to customize the port of a server.
 * <p>
 * Be sure to add the {@link org.springframework.stereotype.Component}
 * annotation to the child class.
 * <p>
 * Also, be sure to add the
 * {@link org.springframework.beans.factory.annotation.Autowired} annotation to
 * the child class's constructor, to inject the port.
 * <p>
 * Look at RumpusPortCustomizer and AdminPortCustomizer for examples.
 * <p>
 * The bean for the port is created in the
 * {@link com.rumpus.common.Config.AbstractCommonConfig} class.
 */
public abstract class AbstractServerPortCustomizer extends AbstractCommonObject
        implements
            WebServerFactoryCustomizer<ConfigurableWebServerFactory> {

    private IPort port;

    /**
     * Ctor
     *
     * @param port
     *            port to set
     */
    public AbstractServerPortCustomizer(IPort port) {
        if (port != null) {
            this.port = port;
        } else {
            LOG("Port is null. Setting port to NO_PORT.");
            com.rumpus.common.Server.Port.Port.create(IPort.NO_PORT);
        }
    }

    @Override
    public void customize(ConfigurableWebServerFactory factory) {
        LOG("AbstractServerPortCustomizer::customize()");
        LOG("Setting port to ", this.port.getPort(), ".");
        if (this.port == null) {
            LOG("Port is null. Not customize setting port.");
            return;
        }
        if (this.port.getPort() == null) {
            LOG("Port.getPort() null. Not customize setting port.");
            return;
        }
        if (this.port.getPort().equals(IPort.NO_PORT)) {
            LOG("Port is NO_PORT. Not customize setting port.");
        }
        factory.setPort(Integer.valueOf(this.port.getPort()));
    }

    public String getPort() {
        return this.port.getPort();
    }

    public void setPort(String port) {
        this.port.setPort(port);
    }
}
