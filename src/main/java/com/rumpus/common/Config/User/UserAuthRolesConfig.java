package com.rumpus.common.Config.User;

import javax.sql.DataSource;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.rumpus.common.Config.Database.DatabaseConfig;
import com.rumpus.common.Dao.User.IUserAuthorityDao;
import com.rumpus.common.Dao.User.jdbc.UserAuthorityDaoJdbc;
import com.rumpus.common.Dao.User.jdbc.UserAuthorityRowMapper;

@Configuration
@EnableConfigurationProperties(UserRolesProperties.class)
@Import({
        DatabaseConfig.class
})
public class UserAuthRolesConfig {

    @Bean
    public IUserAuthorityDao userAuthorityDao(
            DataSource dataSource,
            UserRolesProperties userRolesProperties) {
        final UserAuthorityRowMapper authorityRowMapper = new UserAuthorityRowMapper();

        IUserAuthorityDao userAuthorityDao = new UserAuthorityDaoJdbc(
                dataSource,
                userRolesProperties.getUserAuthoritiesTable(),
                userRolesProperties.getUsersTable(),
                userRolesProperties.getAuthDefinitionTable(),
                authorityRowMapper);

        return userAuthorityDao;
    }
}
