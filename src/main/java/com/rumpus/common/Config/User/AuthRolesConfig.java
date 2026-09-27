package com.rumpus.common.Config.User;

import javax.sql.DataSource;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Import;

import com.rumpus.common.Config.Database.DatabaseConfig;
import com.rumpus.common.Dao.User.IAuthorityDao;
import com.rumpus.common.Dao.User.jdbc.AuthorityDaoJdbc;
import com.rumpus.common.Dao.User.jdbc.AuthorityRowMapper;
import com.rumpus.common.Service.User.AuthorityService;
import com.rumpus.common.Service.User.IAuthorityService;

@Configuration
@EnableConfigurationProperties(AuthRolesProperties.class)
@Import({
        DatabaseConfig.class
})
public class AuthRolesConfig {
    @Bean
    public IAuthorityDao authorityDao(
            DataSource dataSource,
            AuthRolesProperties authRolesTableProperties) {
        AuthorityRowMapper authorityRowMapper = new AuthorityRowMapper();
        return new AuthorityDaoJdbc(
                dataSource,
                authRolesTableProperties.getAuthorityTable(),
                authorityRowMapper);
    }

    @Bean
    @DependsOn("authorityDao")
    public IAuthorityService authorityService(
            IAuthorityDao authorityDao) {
        return new AuthorityService(authorityDao);
    }
}
