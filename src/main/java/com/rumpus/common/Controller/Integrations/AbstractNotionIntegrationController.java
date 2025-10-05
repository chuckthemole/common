package com.rumpus.common.Controller.Integrations;

import com.rumpus.common.AbstractCommonObject;
import com.rumpus.common.Integrations.NotionIntegration;
import com.rumpus.common.Integrations.NotionIntegrationEntry;
import com.rumpus.common.Integrations.NotionIntegrationRegistry;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Abstract controller for exposing Notion integration endpoints (CRUD)
 * without requiring databaseId or pageId in the URL.
 *
 * Provides detailed debug logging through LOG() calls for tracing execution,
 * parameter values, and API failures.
 */
public abstract class AbstractNotionIntegrationController extends AbstractCommonObject {

    // ==============================
    // URL Constants
    // ==============================
    private static final String BASE_PATH = "/integrations/notion/{integrationKey}";
    private static final String DATABASE_PATH = BASE_PATH + "/database";
    private static final String PAGE_PATH = BASE_PATH + "/page";
    private static final String PAGES_PATH = BASE_PATH + "/pages";

    // ==============================
    // Fields
    // ==============================
    private final Map<String, NotionIntegration> notionMap;
    private final NotionIntegrationRegistry notionRegistry;

    // ==============================
    // Constructor
    // ==============================
    public AbstractNotionIntegrationController(
            Map<String, NotionIntegration> notionMap,
            NotionIntegrationRegistry notionRegistry) {
        this.notionMap = notionMap;
        this.notionRegistry = notionRegistry;
    }

    // ==============================
    // Debug Utilities
    // ==============================

    /**
     * Logs all entries currently registered in the NotionIntegrationRegistry.
     * Includes the name, Notion ID, and any other relevant details.
     */
    protected void debugNotionRegistry() {
        if (notionRegistry == null) {
            LOG("debugNotionRegistry() -> WARNING: notionRegistry is null (no entries to display).");
            return;
        }

        LOG("debugNotionRegistry() -> Listing all entries in NotionIntegrationRegistry:");
        Map<String, NotionIntegrationEntry> entries = notionRegistry.getAll();
        if (entries == null || entries.isEmpty()) {
            LOG("debugNotionRegistry() -> Registry is empty.");
            return;
        }

        for (Map.Entry<String, NotionIntegrationEntry> entry : entries.entrySet()) {
            String name = entry.getKey();
            NotionIntegrationEntry notionEntry = entry.getValue();
            final String id = notionEntry != null ? notionEntry.getNotionId() : "null";
            LOG(" - Entry:", name, "Notion ID:", id, "Object:", notionEntry.toString());
        }
        LOG("debugNotionRegistry() -> Registry listing complete. Total entries:", String.valueOf(entries.size()));
    }

    /**
     * Logs all NotionIntegration instances currently loaded in the map.
     * Includes their integration key and a summary via toString().
     */
    protected void debugNotionMap() {
        if (notionMap == null) {
            LOG("debugNotionMap() -> WARNING: notionMap is null (no integrations loaded).");
            return;
        }

        LOG("debugNotionMap() -> Listing all NotionIntegration instances:");
        if (notionMap.isEmpty()) {
            LOG("debugNotionMap() -> No NotionIntegration instances found.");
            return;
        }

        for (Map.Entry<String, NotionIntegration> entry : notionMap.entrySet()) {
            String key = entry.getKey();
            NotionIntegration notion = entry.getValue();
            LOG(" - Integration key:", key,
                    "Object:", notion != null ? notion.toString() : "null");
        }
        LOG("debugNotionMap() -> Integration listing complete. Total integrations:",
                String.valueOf(notionMap.size()));
    }

    // ==============================
    // CRUD Endpoints
    // ==============================

    @GetMapping(DATABASE_PATH)
    public ResponseEntity<String> getDatabase(
            @PathVariable String integrationKey,
            @RequestParam String name) {

        LOG("AbstractNotionIntegrationController::getDatabase() -> called with integrationKey:", integrationKey,
                "name:", name);

        return handleRequest(integrationKey, notion -> {
            LOG("getDatabase() -> Fetching databaseId from notionRegistry for name:", name);
            String databaseId = getNotionIdSafely(name);
            LOG("getDatabase() -> Querying database with ID:", databaseId);
            String response = notion.queryDatabase(databaseId);
            LOG("getDatabase() -> Query successful for database:", name);
            return response;
        });
    }

    @PostMapping(PAGES_PATH)
    public ResponseEntity<String> createPage(
            @PathVariable String integrationKey,
            @RequestBody String jsonBody,
            @RequestParam String name) {

        LOG("AbstractNotionIntegrationController::createPage() -> called with integrationKey:", integrationKey,
                "database name:", name);
        LOG("createPage() -> Payload length:", String.valueOf(jsonBody != null ? jsonBody.length() : 0));

        return handleRequest(integrationKey, notion -> {
            LOG("createPage() -> Fetching databaseId from notionRegistry for name:", name);
            String databaseId = getNotionIdSafely(name);
            LOG("createPage() -> Creating page in databaseId:", databaseId);
            String response = notion.createPage(databaseId, jsonBody);
            LOG("createPage() -> Page creation successful for database:", name);
            return response;
        });
    }

    @PutMapping(PAGE_PATH)
    public ResponseEntity<String> updatePage(
            @PathVariable String integrationKey,
            @RequestParam String name,
            @RequestBody String jsonBody) {

        LOG("AbstractNotionIntegrationController::updatePage() -> called with integrationKey:", integrationKey,
                "page name:", name);
        LOG("updatePage() -> Payload length:", String.valueOf(jsonBody != null ? jsonBody.length() : 0));

        return handleRequest(integrationKey, notion -> {
            LOG("updatePage() -> Fetching pageId from notionRegistry for name:", name);
            String pageId = getNotionIdSafely(name);
            LOG("updatePage() -> Updating pageId:", pageId);
            String response = notion.updatePage(pageId, jsonBody);
            LOG("updatePage() -> Update successful for page:", name);
            return response;
        });
    }

    @DeleteMapping(PAGE_PATH)
    public ResponseEntity<String> deletePage(
            @PathVariable String integrationKey,
            @RequestParam String name) {

        LOG("AbstractNotionIntegrationController::deletePage() -> called with integrationKey:", integrationKey,
                "page name:", name);

        return handleRequest(integrationKey, notion -> {
            LOG("deletePage() -> Fetching pageId from notionRegistry for name:", name);
            String pageId = getNotionIdSafely(name);
            LOG("deletePage() -> Archiving (deleting) pageId:", pageId);
            String response = notion.deletePage(pageId);
            LOG("deletePage() -> Delete successful for page:", name);
            return response;
        });
    }

    // ==============================
    // Helpers
    // ==============================

    private String getNotionIdSafely(String name) {
        if (notionRegistry == null) {
            LOG("getNotionIdSafely() -> ERROR: notionRegistry is null");
            throw new IllegalStateException("Notion registry not initialized");
        }

        final NotionIntegrationEntry notionEntry = notionRegistry.get(name);
        if (notionEntry == null) {
            LOG("getNotionIdSafely() -> ERROR: No registry entry found for name:", name);
            throw new IllegalArgumentException("No Notion registry entry for name: " + name);
        }

        String id = notionEntry.getNotionId();
        LOG("getNotionIdSafely() -> Found Notion ID for name:", name, "id:", id);
        return id;
    }

    protected NotionIntegration getNotionIntegration(String key) {
        LOG("getNotionIntegration() -> Fetching integration with key:", key);
        NotionIntegration notion = notionMap.get(key);
        if (notion == null) {
            LOG("getNotionIntegration() -> WARNING: No NotionIntegration found for key:", key);
        } else {
            LOG("getNotionIntegration() -> Found NotionIntegration for key:", key);
        }
        return notion;
    }

    private ResponseEntity<String> handleRequest(String integrationKey, NotionOperation operation) {
        LOG("handleRequest() -> Starting request for integrationKey:", integrationKey);

        NotionIntegration notion = notionMap.get(integrationKey);
        if (notion == null) {
            LOG("handleRequest() -> ERROR: No NotionIntegration found for key:", integrationKey);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No Notion integration found for key: " + integrationKey);
        }

        try {
            LOG("handleRequest() -> Executing operation for key:", integrationKey);
            String result = operation.execute(notion);
            LOG("handleRequest() -> Operation successful for key:", integrationKey);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            LOG("handleRequest() -> ERROR during Notion API call for key:", integrationKey,
                    "Exception type:", e.getClass().getSimpleName(),
                    "Message:", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error calling Notion API: " + e.getMessage());
        }
    }

    // ==============================
    // Functional Interface
    // ==============================
    @FunctionalInterface
    private interface NotionOperation {
        String execute(NotionIntegration notion) throws Exception;
    }
}
