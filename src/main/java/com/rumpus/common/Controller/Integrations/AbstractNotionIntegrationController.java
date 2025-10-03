package com.rumpus.common.Controller.Integrations;

import com.rumpus.common.Integrations.NotionIntegration;
import com.rumpus.common.Integrations.NotionIntegrationRegistry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Abstract controller for exposing Notion integration endpoints (CRUD).
 *
 * <p>
 * Note: This class is abstract. Consumers must annotate subclasses with
 * {@code @RestController} and (optionally) {@code @RequestMapping}
 * to make endpoints available in a Spring Boot application.
 * </p>
 */
public abstract class AbstractNotionIntegrationController {

    // ==============================
    // URL Constants
    // ==============================

    private static final String BASE_PATH = "/integrations/notion/{integrationKey}";
    private static final String DATABASE_PATH = BASE_PATH + "/database/{databaseId}";
    private static final String PAGE_PATH = BASE_PATH + "/page/{pageId}";
    private static final String PAGES_PATH = BASE_PATH + "/pages";

    // ==============================
    // Fields
    // ==============================

    private final Map<String, NotionIntegration> notionMap;
    @Autowired
    private NotionIntegrationRegistry notionRegistry;

    // ==============================
    // Constructor
    // ==============================

    /**
     * @param notionMap A map of NotionIntegration instances keyed by identifier.
     *                  Typically injected via configuration in the consumer app.
     */
    public AbstractNotionIntegrationController(Map<String, NotionIntegration> notionMap) {
        this.notionMap = notionMap;
    }

    // ==============================
    // CRUD Endpoints
    // ==============================

    /**
     * Query a Notion database.
     */
    @GetMapping(DATABASE_PATH)
    public ResponseEntity<String> getDatabase(
            @PathVariable String integrationKey,
            @PathVariable String databaseId) {
        return handleRequest(integrationKey, notion -> notion.queryDatabase(databaseId));
    }

    /**
     * Create a new page in a Notion database.
     */
    @PostMapping(PAGES_PATH)
    public ResponseEntity<String> createPage(
            @PathVariable String integrationKey,
            @RequestBody String jsonBody,
            @RequestParam(required = false) String databaseId) {
        return handleRequest(integrationKey, notion -> notion.createPage(databaseId, jsonBody));
    }

    /**
     * Update a Notion page.
     */
    @PutMapping(PAGE_PATH)
    public ResponseEntity<String> updatePage(
            @PathVariable String integrationKey,
            @PathVariable String pageId,
            @RequestBody String jsonBody) {
        return handleRequest(integrationKey, notion -> notion.updatePage(pageId, jsonBody));
    }

    /**
     * Delete (archive) a Notion page.
     */
    @DeleteMapping(PAGE_PATH)
    public ResponseEntity<String> deletePage(
            @PathVariable String integrationKey,
            @PathVariable String pageId) {
        return handleRequest(integrationKey, notion -> notion.deletePage(pageId));
    }

    // ==============================
    // Helpers
    // ==============================

    /**
     * Provides access to a specific NotionIntegration.
     */
    protected NotionIntegration getNotionIntegration(String key) {
        return notionMap.get(key);
    }

    /**
     * Generic handler for wrapping Notion API calls safely.
     */
    private ResponseEntity<String> handleRequest(
            String integrationKey,
            NotionOperation operation) {
        NotionIntegration notion = notionMap.get(integrationKey);
        if (notion == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No Notion integration found for key: " + integrationKey);
        }

        try {
            String result = operation.execute(notion);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error calling Notion API: " + e.getMessage());
        }
    }

    /**
     * Functional interface for wrapping lambda calls to NotionIntegration.
     */
    @FunctionalInterface
    private interface NotionOperation {
        String execute(NotionIntegration notion) throws Exception;
    }
}
