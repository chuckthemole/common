package com.rumpus.common;

import org.springframework.security.core.GrantedAuthority;

public class Authority implements GrantedAuthority {

    private String authority;

    public Authority(String authority) {
        this.authority = authority;
    }
    public Authority(GrantedAuthority authority) {
        this.authority = authority.getAuthority();
    }

    @Override
    public String getAuthority() {
        return this.authority;
    }
    
}
