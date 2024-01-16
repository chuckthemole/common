package com.rumpus.common.Config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Scope;
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
    @Autowired protected static ApplicationContext applicationContext;

    // Scopes: https://www.baeldung.com/spring-bean-scopes
    /**
     * The container will create a single instance of this bean.
     */
    protected final String SCOPE_SINGLETON = "singleton";
    /**
     * The container will create a new instance of the bean each time a request for that specific bean is made.
     */
    protected final String SCOPE_PROTOTYPE = "prototype";

    protected static final String URL = "url";
	protected static final String USER = "username";
	protected static final String DRIVER = "driver";
	protected static final String PASSWORD = "password";

    @Bean
    @Scope(SCOPE_SINGLETON)
	protected DataSource dataSource() {
		DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
		driverManagerDataSource.setUrl(this.environment.getProperty(AbstractCommonConfig.URL));
		driverManagerDataSource.setUsername(this.environment.getProperty(AbstractCommonConfig.USER));
		driverManagerDataSource.setPassword(this.environment.getProperty(AbstractCommonConfig.PASSWORD));
		driverManagerDataSource.setDriverClassName(this.environment.getProperty(AbstractCommonConfig.DRIVER));
		return driverManagerDataSource;
	}

    @Bean
    @Scope(SCOPE_SINGLETON)
	public JdbcUserDetailsManager jdbcUserDetailsManager() {
		JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager();

		jdbcUserDetailsManager.setDataSource(dataSource());
        return jdbcUserDetailsManager;

        // CommonJdbcUserManager manager = new CommonJdbcUserManager(dataSource);
        // manager.manager().setUsersByUsernameQuery(SET_USERS_QUERY);
		// return manager.manager();
	}
    
    @Bean
    @Scope(SCOPE_SINGLETON)
    @DependsOn("jdbcUserDetailsManager")
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(jdbcUserDetailsManager());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    @Scope(SCOPE_SINGLETON)
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
