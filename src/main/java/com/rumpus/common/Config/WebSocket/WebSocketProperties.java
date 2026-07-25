package com.rumpus.common.Config.WebSocket;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Configuration properties for Spring WebSocket/STOMP messaging.
 *
 * <p>
 * These properties configure the WebSocket endpoint exposed by the application
 * as well as the destination prefixes used by Spring's messaging framework.
 *
 * <p>
 * Example:
 *
 * <pre>
 * properties:
 *   websocket:
 *     endpoint: /ws
 *     application-destination-prefix: /app
 *     broker-destination-prefixes:
 *       - /topic
 *       - /queue
 * </pre>
 */
@ConfigurationProperties(prefix = "properties.websocket")
public class WebSocketProperties {

    /**
     * Endpoint clients connect to when establishing a WebSocket connection.
     */
    private String endpoint = "/ws";

    /**
     * Prefix used for messages handled by @MessageMapping methods.
     */
    private String applicationDestinationPrefix = "/app";

    /**
     * Prefixes handled by Spring's simple message broker.
     */
    private List<String> brokerDestinationPrefixes = List.of("/topic");

    /**
     * Allowed origins
     */
    private List<String> allowedOrigins = List.of("*");

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getApplicationDestinationPrefix() {
        return applicationDestinationPrefix;
    }

    public void setApplicationDestinationPrefix(String applicationDestinationPrefix) {
        this.applicationDestinationPrefix = applicationDestinationPrefix;
    }

    public List<String> getBrokerDestinationPrefixes() {
        return brokerDestinationPrefixes;
    }

    public void setBrokerDestinationPrefixes(List<String> brokerDestinationPrefixes) {
        this.brokerDestinationPrefixes = brokerDestinationPrefixes;
    }

    public List<String> getAllowedOrigins() {
        return this.allowedOrigins;
    }

    public void setAllowedOrigins(List<String> allowedOrigins) {
        this.allowedOrigins = allowedOrigins;
    }
}
