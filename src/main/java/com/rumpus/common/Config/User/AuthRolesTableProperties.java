package com.rumpus.common.Config.User;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "properties.tables")
public class AuthRolesTableProperties {

    private String authorityTable;

    AuthRolesTableProperties() {
    }

    public String getAuthorityTable() {
        return authorityTable;
    }

    public void setAuthorityTable(String authorityTable) {
        this.authorityTable = authorityTable;
    }
}
