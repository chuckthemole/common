package com.rumpus.common.User;

import org.springframework.security.core.Authentication;

public interface ICommonAuthentication extends Authentication {

    /**
     * Get the authentication
     * 
     * @return the {@link Authentication}
     */
    public Authentication getAuthentication();
}
