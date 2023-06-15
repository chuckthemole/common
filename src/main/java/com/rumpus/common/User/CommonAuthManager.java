package com.rumpus.common.User;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

public class CommonAuthManager implements AuthenticationManager {

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // TODO FILL OUT
        authentication.setAuthenticated(true);
        return authentication;
    }
    
}
