package com.rumpus.common.Config.WebSocket;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.security.config.annotation.web.socket.EnableWebSocketSecurity;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import com.rumpus.common.AbstractCommonObject;

/**
 * Configures Spring WebSocket/STOMP messaging.
 *
 * <p>
 * This configuration enables Spring's STOMP message broker and exposes a
 * configurable WebSocket endpoint for browser and application clients.
 *
 * <h2>Responsibilities</h2>
 * <ul>
 * <li>Registers the application's WebSocket endpoint.</li>
 * <li>Configures application destination prefixes.</li>
 * <li>Configures the in-memory STOMP message broker.</li>
 * <li>Enables Spring Security integration for WebSocket messaging.</li>
 * </ul>
 *
 * <h2>Typical use cases</h2>
 * <ul>
 * <li>Live dashboards</li>
 * <li>Notifications</li>
 * <li>Chat applications</li>
 * <li>Real-time collaboration</li>
 * </ul>
 *
 * <h2>When this configuration is unnecessary</h2>
 *
 * Applications exposing only REST APIs generally do not require this
 * configuration.
 */
@Configuration
@EnableWebSocketMessageBroker
@EnableWebSocketSecurity
@EnableConfigurationProperties(WebSocketProperties.class)
public class WebSocketConfig extends AbstractCommonObject
        implements
            WebSocketMessageBrokerConfigurer {

    private final WebSocketProperties properties;

    public WebSocketConfig(WebSocketProperties properties) {
        this.properties = properties;
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {

        LOG("Registering WebSocket endpoint: ",
                properties.getEndpoint());

        registry.addEndpoint(properties.getEndpoint())
                .setAllowedOriginPatterns(
                        properties.getAllowedOrigins()
                                .toArray(String[]::new));
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {

        LOG("Configuring WebSocket message broker.");

        registry.setApplicationDestinationPrefixes(
                properties.getApplicationDestinationPrefix());

        registry.enableSimpleBroker(
                properties.getBrokerDestinationPrefixes()
                        .toArray(String[]::new));
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'toString'");
    }
}
