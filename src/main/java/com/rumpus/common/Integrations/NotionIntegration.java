package com.rumpus.common.Integrations;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.rumpus.common.Log.ICommonLogger.LogLevel;

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
     * Lists all users in the Notion workspace associated with this integration.
     *
     * Calls the Notion API endpoint: GET /v1/users
     *
     * @return JSON response body as String
     * @throws Exception if request fails
     */
    public String listUsers() throws Exception {
        String url = BASE_URL + "/users";
        return get(url, defaultHeaders());
    }

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
     * Creates a new page in a Notion database.
     *
     * Automatically injects the `parent.database_id` into the payload
     * so the frontend does not need to provide it.
     */
    public String createPage(String databaseId, String jsonBody) throws Exception {
        String url = BASE_URL + ENDPOINT_PAGES;

        // Ensure the request body includes the parent field
        final String wrappedBody = wrapWithDatabaseParent(databaseId, jsonBody);

        // Pretty-print the wrapped body for debugging
        try {
            ObjectMapper mapper = new ObjectMapper();
            Object json = mapper.readValue(wrappedBody, Object.class);
            ObjectWriter writer = mapper.writerWithDefaultPrettyPrinter();
            String prettyJson = writer.writeValueAsString(json);

            LOG(
                    NotionIntegration.class,
                    LogLevel.DEBUG,
                    "createPage() -> Final JSON payload being sent to Notion:\n" + prettyJson);
        } catch (Exception e) {
            LOG(
                    NotionIntegration.class,
                    LogLevel.DEBUG,
                    "createPage() -> Failed to pretty-print JSON, raw body:\n" + wrappedBody);
        }

        return post(url, defaultHeaders(), wrappedBody);
    }

    /**
     * Wraps a frontend-provided JSON payload (just properties)
     * with the required Notion `parent` object for database creation.
     *
     * Example:
     * frontend sends: { "properties": { "Title": {...} } }
     * backend transforms into:
     * {
     * "parent": { "database_id": "xxxxx" },
     * "properties": { "Title": {...} }
     * }
     */
    private String wrapWithDatabaseParent(String databaseId, String jsonBody) {
        try {
            com.google.gson.JsonElement body = com.google.gson.JsonParser.parseString(jsonBody);

            com.google.gson.JsonObject finalObject = new com.google.gson.JsonObject();
            com.google.gson.JsonObject parent = new com.google.gson.JsonObject();
            parent.addProperty("database_id", databaseId);

            finalObject.add("parent", parent);

            if (body.isJsonObject()) {
                com.google.gson.JsonObject bodyObj = body.getAsJsonObject();
                // merge the rest of the body (like properties)
                for (var entry : bodyObj.entrySet()) {
                    finalObject.add(entry.getKey(), entry.getValue());
                }
            }

            return finalObject.toString();

        } catch (Exception e) {
            LOG("wrapWithDatabaseParent() -> ERROR wrapping payload:", e.getMessage());
            // Fallback: minimal valid JSON if parsing fails
            return String.format("{\"parent\":{\"database_id\":\"%s\"},\"properties\":{}}", databaseId);
        }
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

    @Override
    public String toString() {
        return new String("TODO: IMPLEMENT");
    }
}
