package com.rumpus.common;

import org.springframework.security.core.GrantedAuthority;

// TODO should I list authorities in this class?
public class CommonAuthority implements GrantedAuthority {

    private String authority;

    public CommonAuthority(String authority) {
        this.authority = authority;
    }
    public CommonAuthority(GrantedAuthority authority) {
        this.authority = authority.getAuthority();
    }

    @Override
    public String getAuthority() {
        return this.authority;
    }
    
}
