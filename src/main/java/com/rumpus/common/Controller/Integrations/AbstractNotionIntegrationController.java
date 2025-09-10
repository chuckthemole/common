package com.rumpus.common.Controller.Integrations;

import com.rumpus.common.Integrations.NotionIntegration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Abstract controller for exposing Notion integration endpoints.
 * <p>
 * Note: The consumer application is responsible for adding the @RestController
 * annotation when subclassing this abstract controller. This allows flexibility
 * to integrate the controller into different Spring Boot applications without
 * enforcing it here.
 * </p>
 * <p>
 * This abstract controller currently exposes a single endpoint to query a
 * Notion database. Additional endpoints for creating or updating pages can
 * be added by extending this class.
 * </p>
 */
public abstract class AbstractNotionIntegrationController {

    /**
     * The Notion integration service used to interact with the Notion API.
     * Encapsulates authentication, request headers, and common API calls.
     */
    private final NotionIntegration notion;

    /**
     * Constructor for AbstractNotionIntegrationController.
     *
     * @param notion The Notion integration token to authenticate API requests.
     *               Typically injected from application properties in the consumer
     *               app.
     */
    public AbstractNotionIntegrationController(NotionIntegration notion) {
        this.notion = notion;
    }

    /**
     * Fetch all rows from a specific Notion database.
     * <p>
     * Example usage:
     * GET /integrations/notion/database/<databaseId>
     * </p>
     *
     * @param databaseId The ID of the Notion database to query.
     * @return The raw JSON response from the Notion API as a String.
     * @throws Exception If an error occurs while querying the Notion API.
     */
    @GetMapping("/integrations/notion/database/{databaseId}")
    public String getDatabase(@PathVariable String databaseId) throws Exception {
        // Delegate the query to the NotionIntegration service
        return notion.queryDatabase(databaseId);
    }

    /**
     * Provides access to the underlying NotionIntegration service for subclasses.
     * <p>
     * Useful if subclasses want to implement additional endpoints like creating
     * or updating pages.
     * </p>
     *
     * @return the NotionIntegration instance
     */
    protected NotionIntegration getNotionIntegration() {
        return notion;
    }
}
