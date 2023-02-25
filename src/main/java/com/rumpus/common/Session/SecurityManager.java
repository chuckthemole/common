package com.rumpus.common.Session;

import javax.sql.DataSource;

import org.springframework.security.provisioning.JdbcUserDetailsManager;

import com.rumpus.common.CommonJdbc;

public class SecurityManager extends CommonJdbc {

    protected static JdbcUserDetailsManager userManager;

    public static JdbcUserDetailsManager createUserManager(DataSource dataSource) {
        SecurityManager.userManager = new JdbcUserDetailsManager();
        SecurityManager.userManager.setJdbcTemplate(CommonJdbc.jdbcTemplate);
        SecurityManager.userManager.setDataSource(dataSource);
        return SecurityManager.userManager;
    }
}