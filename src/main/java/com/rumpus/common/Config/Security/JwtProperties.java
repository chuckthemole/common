package com.rumpus.common.Config.Security;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "properties.jwt")
public class JwtProperties {

    public static final String JWT_SECRET_VALUE_ANNOTATION = "${properties.jwt.secret}";
    public static final String JWT_SECRET_EXPIRATION_ANNOTATION = "${properties.jwt.expiration}";

    private String secret;
    private long expiration;

    JwtProperties() {

    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public long getExpiration() {
        return expiration;
    }

    public void setExpiration(long expiration) {
        this.expiration = expiration;
    }
}
