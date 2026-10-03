package com.rumpus.common.Config.User;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "properties.tables")
public class UserRolesProperties {

    private String userAuthoritiesTable;
    private String authDefinitionTable;
    private String usersTable;

    UserRolesProperties() {
    }

    public String getUserAuthoritiesTable() {
        return userAuthoritiesTable;
    }

    public void setUserAuthoritiesTable(String userAuthoritiesTable) {
        this.userAuthoritiesTable = userAuthoritiesTable;
    }

    public String getAuthDefinitionTable() {
        return authDefinitionTable;
    }

    public void setAuthDefinitionTable(String authDefinitionTable) {
        this.authDefinitionTable = authDefinitionTable;
    }

    public String getUsersTable() {
        return usersTable;
    }

    public void setUsersTable(String usersTable) {
        this.usersTable = usersTable;
    }
}
