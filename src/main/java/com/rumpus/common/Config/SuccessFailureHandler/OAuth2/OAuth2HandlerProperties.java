package com.rumpus.common.Config.SuccessFailureHandler.OAuth2;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "properties.security.oauth2.redirect")
public class OAuth2HandlerProperties {

    private String successRedirectUrl;
    private String failureRedirectUrl;

    public OAuth2HandlerProperties() {
    }

    public String getSuccessRedirectUrl() {
        return successRedirectUrl;
    }

    public void setSuccessRedirectUrl(String successRedirectUrl) {
        this.successRedirectUrl = successRedirectUrl;
    }

    public String getFailureRedirectUrl() {
        return failureRedirectUrl;
    }

    public void setFailureRedirectUrl(String failureRedirectUrl) {
        this.failureRedirectUrl = failureRedirectUrl;
    }

}
