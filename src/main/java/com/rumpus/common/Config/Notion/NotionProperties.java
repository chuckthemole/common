package com.rumpus.common.Config.Notion;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "properties.notion")
public class NotionProperties {

    private List<String> database;

    public NotionProperties() {
    }

    public List<String> getDatabase() {
        return database;
    }

    public void setDatabase(List<String> database) {
        this.database = database;
    }
}
