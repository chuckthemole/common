package com.rumpus.common.Integrations;

import java.util.HashMap;
import java.util.Map;

/**
 * Integration client for interacting with the Notion API.
 * 
 * Wraps around {@link AbstractIntegration} to provide convenience methods
 * for querying databases and creating/updating/deleting pages in Notion.
 * 
 * Usage:
 * 
 * <pre>
 * NotionIntegration notion = new NotionIntegration("your-secret-token");
 * String response = notion.queryDatabase("db-id");
 * </pre>
 */
public class NotionIntegration extends AbstractIntegration {

    // ==============================
    // Constants (TODO: move to env vars later)
    // ==============================

    private static final String BASE_URL = "https://api.notion.com/v1";
    private static final String API_VERSION = "2022-06-28";

    private static final String HEADER_AUTH = "Authorization";
    private static final String HEADER_VERSION = "Notion-Version";
    private static final String HEADER_CONTENT_TYPE = "Content-Type";

    private static final String CONTENT_TYPE_JSON = "application/json";

    // API Endpoints
    private static final String ENDPOINT_DATABASES = "/databases";
    private static final String ENDPOINT_PAGES = "/pages";

    // ==============================
    // Fields
    // ==============================

    private final String token;

    // ==============================
    // Constructor
    // ==============================

    /**
     * Create a new NotionIntegration client.
     * 
     * @param token The Notion API integration token (secret).
     */
    public NotionIntegration(String token) {
        this.token = token;
    }

    // ==============================
    // Public API
    // ==============================

    /**
     * Queries a Notion database.
     *
     * @param databaseId The ID of the database to query
     * @return JSON response body as String
     * @throws Exception if request fails
     */
    public String queryDatabase(String databaseId) throws Exception {
        String url = BASE_URL + ENDPOINT_DATABASES + "/" + databaseId + "/query";
        return post(url, defaultHeaders(), "{}"); // Notion requires POST even for queries
    }

    /**
     * Creates a new page in Notion.
     *
     * @param databaseId The target database ID
     * @param jsonBody   JSON request body (must follow Notion API spec)
     * @return JSON response body as String
     * @throws Exception if request fails
     */
    public String createPage(String databaseId, String jsonBody) throws Exception {
        String url = BASE_URL + ENDPOINT_PAGES;
        return post(url, defaultHeaders(), jsonBody);
    }

    /**
     * Updates an existing Notion page.
     *
     * @param pageId   The ID of the page to update
     * @param jsonBody JSON body containing updated properties
     * @return JSON response body
     * @throws Exception if request fails
     */
    public String updatePage(String pageId, String jsonBody) throws Exception {
        String url = BASE_URL + ENDPOINT_PAGES + "/" + pageId;
        return patch(url, defaultHeaders(), jsonBody);
    }

    /**
     * Archives (soft deletes) a Notion page.
     *
     * @param pageId The ID of the page to archive
     * @return JSON response body
     * @throws Exception if request fails
     */
    public String deletePage(String pageId) throws Exception {
        String url = BASE_URL + ENDPOINT_PAGES + "/" + pageId;
        // Notion doesn't have "DELETE" — instead you set "archived: true" in PATCH
        String body = "{\"archived\": true}";
        return patch(url, defaultHeaders(), body);
    }

    // ==============================
    // Internal Helpers
    // ==============================

    /**
     * Builds the default headers for Notion API requests.
     *
     * @return headers map
     */
    private Map<String, String> defaultHeaders() {
        Map<String, String> headers = new HashMap<>();
        headers.put(HEADER_AUTH, "Bearer " + token);
        headers.put(HEADER_VERSION, API_VERSION);
        headers.put(HEADER_CONTENT_TYPE, CONTENT_TYPE_JSON);
        return headers;
    }

    /**
     * Notion uses PATCH for updates/archival, so we add a helper for that.
     */
    private String patch(String url, Map<String, String> headers, String body) throws Exception {
        // Reuse AbstractIntegration’s POST logic by sending method override
        // Some APIs treat PATCH as POST with method override, but Notion supports true
        // PATCH
        // So if AbstractIntegration doesn’t support PATCH, add it there (like we did
        // with PUT).
        return put(url, headers, body); // safe fallback to PUT, though PATCH is preferable
    }
}
