package com.rumpus.common.User;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.security.core.Authentication;

public class CommonAuthentication implements Authentication {

    private String name;
    private Set<CommonAuthority> authorities;
    private String password;
    private String username;
    private Map<String, String> details;
    private boolean authenticated;

    public CommonAuthentication(String name, Set<CommonAuthority> authorities, String password, String username, Map<String, String> details, boolean authenticated) {
        this.name = name;
        this.authorities = authorities;
        this.password = password;
        this.username = username;
        this.details = details;
        this.authenticated = authenticated;
    }
    public CommonAuthentication(Authentication auth) {
        this.name = auth.getName();
        this.authorities.addAll(getAuthorities(auth));
        this.password = auth.getCredentials().toString();
        this.username = auth.getPrincipal().toString();

        if(auth.getDetails().getClass().equals(Map.class)) {
            this.details = (Map<String, String>) auth.getDetails();
        }
        this.authenticated = auth.isAuthenticated();
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Set<CommonAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getCredentials() {
        return this.password;
    }

    @Override
    public Map<String, String> getDetails() {
        return this.details;
    }

    @Override
    public String getPrincipal() {
        return this.username;
    }

    @Override
    public boolean isAuthenticated() {
        return this.authenticated;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        this.authenticated = isAuthenticated;
    }

    private Set<CommonAuthority> getAuthorities(Authentication auth) {
        Set<CommonAuthority> authorities = new HashSet<>();
        auth.getAuthorities().stream().forEach(grantedAuth -> {
            authorities.add(new CommonAuthority(grantedAuth));
        });
        return authorities;
    }
    
}
