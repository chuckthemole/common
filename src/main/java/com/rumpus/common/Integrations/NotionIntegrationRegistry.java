package com.rumpus.common.Integrations;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Registry for managing {@link NotionIntegrationEntry} objects.
 * <p>
 * Provides a centralized lookup for Notion integration mappings,
 * allowing services to register, retrieve, and manage Notion resources
 * by their internal key name.
 */
public class NotionIntegrationRegistry {

    /**
     * Internal thread-safe storage of integration entries, keyed by entry name.
     */
    private final Map<String, NotionIntegrationEntry> integrations = new ConcurrentHashMap<>();

    /**
     * Registers a new {@link NotionIntegrationEntry}.
     * If an entry with the same name already exists, it will be overwritten.
     *
     * @param entry the integration entry to register (must not be null)
     * @throws IllegalArgumentException if entry or its name is null/blank
     */
    public void register(NotionIntegrationEntry entry) {
        if (entry == null || entry.getName() == null || entry.getName().isBlank()) {
            throw new IllegalArgumentException("Integration entry and its name must not be null or blank");
        }
        integrations.put(entry.getName(), entry);
    }

    /**
     * Retrieves a {@link NotionIntegrationEntry} by its key name.
     *
     * @param key the key name
     * @return the integration entry, or null if not found
     */
    public NotionIntegrationEntry get(String key) {
        return integrations.get(key);
    }

    /**
     * Checks if an entry exists for the given key name.
     *
     * @param key the key name
     * @return true if an entry exists, false otherwise
     */
    public boolean contains(String key) {
        return integrations.containsKey(key);
    }

    /**
     * Removes an entry from the registry by key.
     *
     * @param key the key name
     * @return the removed entry, or null if none existed
     */
    public NotionIntegrationEntry deregister(String key) {
        return integrations.remove(key);
    }

    /**
     * Returns an unmodifiable view of all registered entries.
     *
     * @return all registered integration entries
     */
    public Map<String, NotionIntegrationEntry> getAll() {
        return Collections.unmodifiableMap(integrations);
    }

    /**
     * Returns the number of registered entries.
     *
     * @return count of entries
     */
    public int size() {
        return integrations.size();
    }

    @Override
    public String toString() {
        return "NotionIntegrationRegistry{" +
                "integrations=" + integrations +
                '}';
    }
}
