package com.rumpus.common.Config;

import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.DependsOn;

/**
 * Used to customize the port of a server.
 * <p>
 * Be sure to add the {@link org.springframework.stereotype.Component} annotation to the child class.
 */
@org.springframework.stereotype.Component
@DependsOn({AbstractCommonConfig.BEAN_PORT_MANAGER}) // uses commonPortManager in init
public abstract class AbstractServerPortCustomizer extends com.rumpus.common.AbstractCommonObject implements WebServerFactoryCustomizer<ConfigurableWebServerFactory> {

    // TODO: make a port manager, or look for one if I started one earlier.

    private String port;

    /**
     * 
     * @param name
     * @param port
     * @param tryNewPortIfTaken
     */
    public AbstractServerPortCustomizer(String name, String port, boolean tryNewPortIfTaken) {
        super(name);
        this.init(port, tryNewPortIfTaken);
    }

    private void init(final String port, final boolean tryNewPortIfTaken) {

        // verify the port with the port manager, if not verified and tryNewPortIfTaken is true, then create a new random port, else set the port to null
        if(com.rumpus.common.Server.Port.PortManager.verifyPort(AbstractCommonConfig.commonPortManager, port)) {
            this.port = port;
        } else if(tryNewPortIfTaken) {
            this.port = AbstractCommonConfig.commonPortManager.createRandomPort().getPort();
        } else {
            this.port = null;
            LOG_THIS("Setting port to null.");
        }
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

    private static void LOG_THIS(String... args) {
        com.rumpus.common.Builder.LogBuilder.logBuilderFromStringArgsNoSpaces(AbstractServerPortCustomizer.class, args).info();
    }
}

