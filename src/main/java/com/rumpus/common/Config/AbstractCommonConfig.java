package com.rumpus.common.Config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

import com.rumpus.common.AbstractCommon;

/**
 * @author Chuck Thomas
 * 
 * Common config for web app. Using jdbc template right now. Should abstract this to allow other impls.
 * TODO: think about making this into an annotation.
 */
public abstract class AbstractCommonConfig extends AbstractCommon {

    @Autowired protected Environment environment;
    @Autowired protected ApplicationContext applicationContext;


    protected final String URL = "url";
	protected final String USER = "username";
	protected final String DRIVER = "driver";
	protected final String PASSWORD = "password";

    @Bean
	protected DataSource dataSource() {
		DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
		driverManagerDataSource.setUrl(environment.getProperty(URL));
		driverManagerDataSource.setUsername(environment.getProperty(USER));
		driverManagerDataSource.setPassword(environment.getProperty(PASSWORD));
		driverManagerDataSource.setDriverClassName(environment.getProperty(DRIVER));
		return driverManagerDataSource;
	}

    @Bean
	public JdbcUserDetailsManager jdbcUserDetailsManager() {
		JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager();

		jdbcUserDetailsManager.setDataSource(dataSource());
        return jdbcUserDetailsManager;

        // CommonJdbcUserManager manager = new CommonJdbcUserManager(dataSource);
        // manager.manager().setUsersByUsernameQuery(SET_USERS_QUERY);
		// return manager.manager();
	}
    
    @Bean
    @DependsOn("jdbcUserDetailsManager")
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(jdbcUserDetailsManager());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
