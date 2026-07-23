package com.rumpus.common.Config.Integration.Notion;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "properties.notion")
public class NotionProperties {

    private Tokens token = new Tokens();

    /**
     * Maps logical database names to their Notion IDs.
     *
     * Example:
     *
     * properties: notion: databases: employees: xxxxx projects: yyyyy
     */
    private Map<String, String> databases = new HashMap<>();

    public NotionProperties() {
    }

    public Tokens getToken() {
        return token;
    }

    public void setToken(Tokens token) {
        this.token = token;
    }

    public Map<String, String> getDatabases() {
        return databases;
    }

    public void setDatabases(Map<String, String> databases) {
        this.databases = databases;
    }

    public static class Tokens {

        private String console;
        private String projectManagement;

        public Tokens() {
        }

        public String getConsole() {
            return console;
        }

        public void setConsole(String console) {
            this.console = console;
        }

        public String getProjectManagement() {
            return projectManagement;
        }

        public void setProjectManagement(String projectManagement) {
            this.projectManagement = projectManagement;
        }
    }
}
