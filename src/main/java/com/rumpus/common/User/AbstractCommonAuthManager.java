package com.rumpus.common.User;

import java.util.ArrayList;
import java.util.Set;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;

import com.rumpus.common.Dao.IDao;
import com.rumpus.common.Model.AbstractModel;
import com.rumpus.common.Service.UserService;

abstract public class AbstractCommonAuthManager<MODEL extends AbstractModel<MODEL>> extends UserService<MODEL> implements AuthenticationManager {

    public AbstractCommonAuthManager(String name, IDao<MODEL> dao) {
        super(name, dao);
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        LOG.info("AbstractCommonAuthManager::authenticate");
        final String name = authentication.getName();
        final String password = authentication.getCredentials().toString();
        
        if (userIsAuthenticated(name, password)) {
            LOG.info("User is authenticated, returning token.");
            // use the credentials
            // and authenticate against the third-party system
            return new UsernamePasswordAuthenticationToken(name, password, getAuthorities(name));
        } else {
            LOG.info("User is NOT authenticated");
            return null;
        }

        // authentication.setAuthenticated(true);
        // return authentication;
    }

    abstract public boolean userIsAuthenticated(String name, String password);
    abstract public Set<GrantedAuthority> getAuthorities(String name);
}
