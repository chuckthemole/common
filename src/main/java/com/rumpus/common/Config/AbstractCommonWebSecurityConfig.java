package com.rumpus.common.Config;

import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

public abstract class AbstractCommonWebSecurityConfig extends AbstractCommonConfig {
    abstract public UserDetailsService userDetailsService();
    abstract public DaoAuthenticationProvider authenticationProvider();
    abstract public PasswordEncoder passwordEncoder();
}
