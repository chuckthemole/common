package com.rumpus.common.Config.Security;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

@Configuration
public class SecurityConfig {

    public static final String BEAN_JDBC_USER_DETAILS_MANAGER = "jdbcUserDetailsManager";
    public static final String BEAN_AUTHENTICATION_PROVIDER = "authenticationProvider";
    public static final String BEAN_PASSWORD_ENCODER = "passwordEncoder";

    @Bean(BEAN_PASSWORD_ENCODER)
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean(BEAN_JDBC_USER_DETAILS_MANAGER)
    public JdbcUserDetailsManager jdbcUserDetailsManager(
            DataSource dataSource) {

        return new JdbcUserDetailsManager(dataSource);
    }

    @Bean(BEAN_AUTHENTICATION_PROVIDER)
    @DependsOn(BEAN_JDBC_USER_DETAILS_MANAGER)
    public DaoAuthenticationProvider authenticationProvider(
            JdbcUserDetailsManager userDetailsManager,
            PasswordEncoder passwordEncoder) {

        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();

        provider.setUserDetailsService(userDetailsManager);
        provider.setPasswordEncoder(passwordEncoder);

        return provider;
    }

}
