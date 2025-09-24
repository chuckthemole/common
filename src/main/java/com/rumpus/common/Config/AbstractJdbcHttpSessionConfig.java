package com.rumpus.common.Config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.session.jdbc.config.annotation.web.http.EnableJdbcHttpSession;
import org.springframework.session.jdbc.config.annotation.web.http.JdbcHttpSessionConfiguration;
import org.springframework.session.web.context.AbstractHttpSessionApplicationInitializer;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.boot.context.properties.ConfigurationProperties;
import com.rumpus.common.ICommon;

// TODO: Need to look at Sessions more closely and plan how to implement.

// @Configuration(proxyBeanMethods = false)
@EnableJdbcHttpSession
@ConfigurationProperties(prefix = "properties")
// @org.springframework.context.annotation.PropertySource(value =
// "classpath:properties.yml", factory =
// com.rumpus.common.Config.Properties.yaml.YamlPropertySourceFactory.class)
public abstract class AbstractJdbcHttpSessionConfig extends AbstractHttpSessionApplicationInitializer
        implements ICommon {

    private static final String TABLE_NAME = "session";

    @Autowired
    public DataSource dataSource;

    public AbstractJdbcHttpSessionConfig() {
        super(JdbcHttpSessionConfiguration.class);
    }

    @Bean
    @DependsOn({ "dataSource" })
    public PlatformTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource);
    }

    // Not using rn
    // @Bean
    // @DependsOn({"transactionManager"})
    // public TransactionTemplate transactionTemplate(PlatformTransactionManager
    // manager) {
    // return new TransactionTemplate(manager);
    // }
}
