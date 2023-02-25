package com.rumpus.common.Session;

import java.util.List;

import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;

// TODO abstract this out for different web customizing
public class CommonWebSecurity implements WebSecurityCustomizer {

    private static final String[] IGNORING = {"/null", "/what"};

    @Override
    public void customize(WebSecurity web) {
        web.ignoring().requestMatchers(IGNORING);
    }
    
}
