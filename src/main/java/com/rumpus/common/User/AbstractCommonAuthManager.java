package com.rumpus.common.User;

import java.util.Set;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.rumpus.common.Dao.User.IAuthorityDao;
import com.rumpus.common.Dao.User.IUserAuthorityDao;
import com.rumpus.common.Dao.User.IUserDao;
import com.rumpus.common.Service.User.AbstractUserService;
import com.rumpus.common.Service.User.UserSecurityService;

abstract public class AbstractCommonAuthManager<USER extends AbstractCommonUser<USER, USER_META>,
        USER_META extends AbstractCommonUserMetaData<USER_META>>
        extends
            AbstractUserService<USER, USER_META>
        implements
            AuthenticationManager {

    public AbstractCommonAuthManager(
            IUserDao<USER, USER_META> dao,
            IUserAuthorityDao userAuthorityDao,
            IAuthorityDao authorityDao,
            UserSecurityService userSecurityService,
            PasswordEncoder passwordEncoder) {
        super(
                dao,
                userAuthorityDao,
                authorityDao,
                userSecurityService,
                null,
                passwordEncoder);
    }

    @Override
    public Authentication authenticate(Authentication authentication)
            throws AuthenticationException {
        LOG("AbstractCommonAuthManager::authenticate");
        final String name = authentication.getName();
        final String password = authentication.getCredentials().toString();

        if (this.userIsAuthenticated(name, password)) {
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

    abstract public Set<GrantedAuthority> getAuthorities(String name);
}
