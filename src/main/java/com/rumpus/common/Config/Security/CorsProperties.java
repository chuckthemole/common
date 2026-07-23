package com.rumpus.common.Config.Security;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "properties.frontend")
public class CorsProperties {

    private List<String> origins;
    private List<String> methods;
    private List<String> headers;
    private boolean credentials;

    public CorsProperties() {
    }

    public List<String> getOrigins() {
        return origins;
    }

    public void setOrigins(List<String> origins) {
        this.origins = origins;
    }

    public List<String> getMethods() {
        return methods;
    }

    public void setMethods(List<String> methods) {
        this.methods = methods;
    }

    public List<String> getHeaders() {
        return headers;
    }

    public void setHeaders(List<String> headers) {
        this.headers = headers;
    }

    public boolean isCredentials() {
        return credentials;
    }

    public void setCredentials(boolean credentials) {
        this.credentials = credentials;
    }
}
