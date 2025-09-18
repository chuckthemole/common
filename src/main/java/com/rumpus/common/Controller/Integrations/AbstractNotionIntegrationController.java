package com.rumpus.common.Controller.Integrations;

import com.rumpus.common.Integrations.NotionIntegration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;

/**
 * Abstract controller for exposing multiple Notion integration endpoints.
 * <p>
 * Note: The consumer application is responsible for adding the @RestController
 * annotation when subclassing this abstract controller. This allows flexibility
 * to integrate the controller into different Spring Boot applications without
 * enforcing it here.
 * </p>
 * <p>
 * This abstract controller currently exposes a single endpoint to query a
 * Notion database for multiple integrations. Additional endpoints for creating
 * or updating pages can be added by extending this class.
 * </p>
 */
public abstract class AbstractNotionIntegrationController {

    /**
     * Map of Notion integration services used to interact with the Notion API.
     * <p>
     * Each integration is identified by a unique key (e.g., environment or name).
     * Encapsulates authentication, request headers, and common API calls.
     * </p>
     */
    private final Map<String, NotionIntegration> notionMap;

    /**
     * Constructor for AbstractNotionIntegrationController.
     *
     * @param notionMap A map of NotionIntegration instances keyed by identifier.
     *                  Typically injected from application configuration in the
     *                  consumer app.
     */
    public AbstractNotionIntegrationController(Map<String, NotionIntegration> notionMap) {
        this.notionMap = notionMap;
    }

    /**
     * Fetch all rows from a specific Notion database for a given integration.
     * <p>
     * Example usage:
     * GET /integrations/notion/{integrationKey}/database/{databaseId}
     * </p>
     *
     * @param integrationKey The key identifying which Notion integration to use.
     * @param databaseId     The ID of the Notion database to query.
     * @return The raw JSON response from the Notion API as a String.
     * @throws Exception If an error occurs while querying the Notion API.
     */
    @GetMapping("/integrations/notion/{integrationKey}/database/{databaseId}")
    public String getDatabase(
            @PathVariable String integrationKey,
            @PathVariable String databaseId) throws Exception {
        // Retrieve the correct NotionIntegration service from the map
        NotionIntegration notion = notionMap.get(integrationKey);
        if (notion == null) {
            throw new IllegalArgumentException(
                    "No Notion integration found for key: " + integrationKey);
        }
        // Delegate the query to the selected NotionIntegration service
        return notion.queryDatabase(databaseId);
    }

    /**
     * Provides access to the underlying NotionIntegration service for subclasses.
     * <p>
     * Useful if subclasses want to implement additional endpoints like creating
     * or updating pages for a specific integration.
     * </p>
     *
     * @param key The key identifying which Notion integration to access.
     * @return The NotionIntegration instance associated with the given key
     */
    protected NotionIntegration getNotionIntegration(String key) {
        return notionMap.get(key);
    }
}
