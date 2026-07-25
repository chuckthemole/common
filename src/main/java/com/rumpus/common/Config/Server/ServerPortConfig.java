package com.rumpus.common.Config.Server;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;

import com.rumpus.common.AbstractCommonObject;
import com.rumpus.common.Server.Port.IPort;

/**
 * Configures the embedded web server's listening port.
 *
 * <p>
 * This configuration creates a {@link WebServerFactoryCustomizer} that
 * configures the embedded servlet container (Tomcat, Jetty, Undertow, etc.)
 * using the application's {@link IPort} bean.
 *
 * <h2>How it works</h2>
 *
 * <p>
 * Spring Boot automatically discovers every {@link WebServerFactoryCustomizer}
 * bean during startup and invokes it before the embedded web server is created.
 *
 * <p>
 * This configuration simply reads the application's configured {@link IPort}
 * bean and applies that value as the server's listening port.
 *
 * <h2>Typical usage</h2>
 *
 * Import this configuration into the consuming application:
 *
 * <pre>
 * &#64;Configuration
 * &#64;Import(CommonServerPortConfig.class)
 * public class ServerConfig {
 * }
 * </pre>
 *
 * Then provide an {@link IPort} bean:
 *
 * <pre>
 * &#64;Bean
 * public IPort serverPort() {
 *     return Port.create("8081");
 * }
 * </pre>
 *
 * <h2>Disabling port customization</h2>
 *
 * If the supplied {@link IPort} contains {@link IPort#NO_PORT}, this
 * configuration intentionally does not customize the embedded server, allowing
 * Spring Boot's normal port configuration (typically {@code server.port} or the
 * default of {@code 8080}) to be used.
 *
 * <h2>When to use</h2>
 *
 * Use this configuration when an application's server port is provided by
 * dependency injection rather than directly from Spring Boot configuration.
 * This is useful when the port is selected dynamically or shared through a
 * common configuration framework.
 */
@Configuration
public class ServerPortConfig extends AbstractCommonObject {

    /**
     * Creates the customizer responsible for configuring the embedded web server's
     * listening port.
     *
     * @param port
     *            application port bean
     * @return server factory customizer
     */
    @Bean
    public WebServerFactoryCustomizer<ConfigurableWebServerFactory> serverPortCustomizer(
            IPort port) {

        return factory -> {

            LOG("CommonServerPortConfig::serverPortCustomizer()");

            if (port == null) {
                LOG("No IPort bean found. Leaving server port unchanged.");
                return;
            }

            String value = port.getPort();

            if (value == null) {
                LOG("IPort returned a null port. Leaving server port unchanged.");
                return;
            }

            if (IPort.NO_PORT.equals(value)) {
                LOG("IPort is configured as NO_PORT. Leaving server port unchanged.");
                return;
            }

            LOG("Configuring embedded server port to ", value, ".");

            factory.setPort(Integer.parseInt(value));
        };
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'toString'");
    }
}
