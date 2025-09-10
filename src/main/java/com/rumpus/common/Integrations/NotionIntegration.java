package com.rumpus.common.Integrations;

import java.util.HashMap;
import java.util.Map;

public class NotionIntegration extends AbstractIntegration {

    private final String token;
    private final String notionVersion = "2022-06-28";

    public NotionIntegration(String token) {
        this.token = token;
    }

    private Map<String, String> defaultHeaders() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + token);
        headers.put("Notion-Version", notionVersion);
        headers.put("Content-Type", "application/json");
        return headers;
    }

    public String queryDatabase(String databaseId) throws Exception {
        String url = "https://api.notion.com/v1/databases/" + databaseId + "/query";
        return post(url, defaultHeaders(), "{}"); // Notion requires POST
    }

    public String createPage(String databaseId, String jsonBody) throws Exception {
        String url = "https://api.notion.com/v1/pages";
        return post(url, defaultHeaders(), jsonBody);
    }

    // You can add updatePage, deletePage, etc. similarly
}
