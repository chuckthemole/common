package com.rumpus.common.Config.Security;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "properties.security.jwt")
public class JwtProperties {

    // TODO: I think we can remove these constants and
    // just use the annotations directly in JwtService.
    public static final String JWT_SECRET_VALUE_ANNOTATION = "${properties.security.jwt.secret}";
    public static final String JWT_SECRET_EXPIRATION_ANNOTATION = "${properties.security.jwt.expiration}";

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
