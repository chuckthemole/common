package com.rumpus.common.User;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.rumpus.common.Dao.IDao;
import com.rumpus.common.Model.AbstractModel;
import com.rumpus.common.Service.AbstractService;

abstract public class AbstractCommonAuthManager<MODEL extends AbstractModel<MODEL>> extends AbstractService<MODEL> implements AuthenticationManager, UserDetailsService {

    public AbstractCommonAuthManager(String name, IDao<MODEL> dao) {
        super(name, dao);
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        LOG("AbstractCommonAuthManager::authenticate");
        final String name = authentication.getName();
        final String password = authentication.getCredentials().toString();
        
        if (userIsAuthenticated(name, password)) {
            LOG("User is authenticated, returning token.");
            // use the credentials
            // and authenticate against the third-party system
            return new UsernamePasswordAuthenticationToken(name, password, getAuthorities(name));
        } else {
            LOG("User is NOT authenticated");
            return null;
        }

        // authentication.setAuthenticated(true);
        // return authentication;
    }

    abstract public boolean userIsAuthenticated(String name, String password);
    abstract public java.util.Set<GrantedAuthority> getAuthorities(String name);
}
