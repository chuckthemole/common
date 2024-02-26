package com.rumpus.common.User;

public interface ICommonAuthentication extends org.springframework.security.core.Authentication {

    /**
     * Get the authentication
     * 
     * @return the {@link org.springframework.security.core.Authentication}
     */
    public org.springframework.security.core.Authentication getAuthentication();
}
