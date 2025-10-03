package com.rumpus.common.Integrations;

import org.springframework.core.env.Environment;

import java.util.Arrays;

/**
 * Utility class for loading Notion integration entries from application
 * properties.
 */
public final class NotionIntegrationLoader {

    // Prevent instantiation
    private NotionIntegrationLoader() {
    }

    /**
     * Loads Notion integration entries from the given property in the environment
     * into the provided registry.
     *
     * Example property format in application.yml:
     *
     * notion:
     * databases:
     * tasks=1a4b11ab09b344d59cd654016930ccf0,leaderboard=264cee7d24dc81b7b071e37ae2576148
     *
     * @param environment  the Spring Environment to pull properties from
     * @param registry     the registry to populate
     * @param propertyKey  the full property key to load (e.g., "notion.databases")
     * @param resourceType the type of Notion resource to assign to all entries
     * @return the populated registry
     */
    public static NotionIntegrationRegistry load(
            Environment environment,
            NotionIntegrationRegistry registry,
            String propertyKey,
            NotionResourceType resourceType) {
        String notionIntegrationEntries = environment.getProperty(propertyKey);

        if (notionIntegrationEntries == null || notionIntegrationEntries.isBlank()) {
            return registry; // nothing to load
        }

        Arrays.stream(notionIntegrationEntries.split(","))
                .map(String::trim)
                .filter(entry -> !entry.isEmpty())
                .forEach(entry -> {
                    String[] parts = entry.split("=", 2); // split into key=value
                    if (parts.length == 2) {
                        String name = parts[0].trim();
                        String value = parts[1].trim();

                        NotionIntegrationEntry notionEntry = new NotionIntegrationEntry(
                                name,
                                resourceType,
                                value);

                        registry.register(notionEntry);
                    } else {
                        throw new IllegalArgumentException(
                                "Invalid Notion integration entry for property '" + propertyKey + "': " + entry);
                    }
                });

        return registry;
    }
}
