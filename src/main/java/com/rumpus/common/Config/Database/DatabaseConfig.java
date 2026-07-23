package com.rumpus.common.Config.Database;

import javax.sql.DataSource;

import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
@EnableConfigurationProperties(DataSourceProperties.class)
public class DatabaseConfig {

    public static final String BEAN_DATA_SOURCE = "dataSource";
    public static final String BEAN_SQL_DIALECT = "sqlDialect";
    public static final String BEAN_DSL_CONTEXT = "dslContext";

    @Bean
    DataSource dataSource(DataSourceProperties props) {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(props.getDriver());
        dataSource.setUrl(props.getUrl());
        dataSource.setUsername(props.getUsername());
        dataSource.setPassword(props.getPassword());
        return dataSource;
    }

    @Bean
    SQLDialect sqlDialect(DataSourceProperties props) {
        return SQLDialect.valueOf(props.getDialect());
    }

    @Bean
    @DependsOn({"dataSource", "sqlDialect"})
    public DSLContext dslContext(DataSource dataSource, SQLDialect sqlDialect) {
        return DSL.using(dataSource, sqlDialect);
    }
}
