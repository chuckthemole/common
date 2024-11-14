package com.rumpus.common.User;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;

import com.rumpus.common.Dao.IUserDao;
import com.rumpus.common.Service.AbstractUserService;

abstract public class AbstractCommonAuthManager
<
    USER extends AbstractCommonUser<USER, USER_META>,
    USER_META extends AbstractCommonUserMetaData<USER_META>
> extends AbstractUserService<USER, USER_META> implements AuthenticationManager {

    public AbstractCommonAuthManager(IUserDao<USER, USER_META> dao) {
        super(dao);
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
