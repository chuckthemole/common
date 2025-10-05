package com.rumpus.common.Integrations;

import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.core.env.Environment;

import com.rumpus.common.AbstractCommonObject;
import com.rumpus.common.Log.ICommonLogger.LogLevel;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Utility class for loading Notion integration entries from application
 * properties and appending structured debug output for diagnostics.
 *
 * <p>
 * This class is typically used inside a Spring @Bean method during
 * configuration. Because Spring logging might not be fully initialized yet,
 * this loader supports passing in a {@link StringBuilder} to collect
 * human-readable debug output that can later be printed or logged (e.g., from
 * a @PostConstruct).
 */
public final class NotionIntegrationLoader extends AbstractCommonObject {

    // Prevent instantiation — this is a static utility class.
    private NotionIntegrationLoader() {
    }

    /**
     * Loads Notion integration entries from the given environment property into the
     * provided registry, and appends diagnostic output to a provided log
     * accumulator.
     *
     * @param environment  The Spring {@link Environment} to pull configuration
     *                     values from.
     * @param registry     The {@link NotionIntegrationRegistry} to populate.
     * @param propertyKey  The property key to load (e.g., "notion.databases").
     * @param resourceType The {@link NotionResourceType} for all loaded entries.
     * @param debugOutput  A {@link StringBuilder} used to accumulate debug text.
     *                     Must not be null — an error is logged if it is.
     */
    public static void load(
            Environment environment,
            NotionIntegrationRegistry registry,
            String propertyKey,
            NotionResourceType resourceType,
            final StringBuilder debugOutput) {

        // Guard against null debugOutput
        if (debugOutput == null) {
            LOG(NotionIntegrationLoader.class, LogLevel.ERROR,
                    "NotionIntegrationLoader::load() -> debugOutput is null. Caller must pass a valid StringBuilder instance.");
            return;
        }

        // Validate required parameters
        Objects.requireNonNull(environment, "Environment must not be null");
        Objects.requireNonNull(registry, "Registry must not be null");
        Objects.requireNonNull(propertyKey, "Property key must not be null");
        Objects.requireNonNull(resourceType, "Resource type must not be null");

        List<String> entries = readNotionEntries(environment, propertyKey, debugOutput);
        if (entries.isEmpty()) {
            String msg = String.format("No Notion integration entries found for key '%s'. Skipping load.%n",
                    propertyKey);
            debugOutput.append(msg);
            LOG_THIS(msg);
            return;
        }

        String joinedEntries = String.join(",", entries);
        String msg = String.format("Loading Notion integration entries for key '%s': %s%n", propertyKey, joinedEntries);
        debugOutput.append(msg);
        LOG_THIS(msg);

        // Register each entry
        entries.forEach(entry -> registerEntry(entry, resourceType, registry, propertyKey, debugOutput));

        String completeMsg = String.format(
                "Completed loading Notion integrations for '%s'. Registry now contains %d entries.%n",
                propertyKey, registry.size());
        debugOutput.append(completeMsg);
        LOG_THIS(completeMsg);
    }

    // --- Private helpers ---

    /**
     * Reads Notion integration entries from environment using Binder for YAML
     * list support.
     */
    private static List<String> readNotionEntries(
            Environment environment,
            String propertyKey,
            StringBuilder debugOutput) {
        // Attempt 1: read as single comma-separated String
        String singleString = environment.getProperty(propertyKey);
        if (singleString != null && !singleString.isBlank()) {
            String msg = String.format("Read property '%s' as single string: %s%n", propertyKey, singleString);
            debugOutput.append(msg);
            LOG_THIS(msg);
            return Arrays.asList(singleString.split(","));
        }

        // Attempt 2: read as YAML list using Binder
        try {
            List<String> list = Binder.get(environment)
                    .bind(propertyKey, Bindable.listOf(String.class))
                    .orElse(Collections.emptyList());

            if (!list.isEmpty()) {
                String msg = String.format("Read property '%s' as YAML list: %s%n", propertyKey, list);
                debugOutput.append(msg);
                LOG_THIS(msg);
            }

            return list;
        } catch (Exception e) {
            String errMsg = String.format("Failed to read property '%s' as list: %s%n", propertyKey, e.getMessage());
            debugOutput.append(errMsg);
            LOG_THIS(errMsg);
            return Collections.emptyList();
        }
    }

    /**
     * Parses and registers a single entry into the registry.
     */
    private static void registerEntry(String entry, NotionResourceType resourceType, NotionIntegrationRegistry registry,
            String propertyKey, StringBuilder debugOutput) {
        try {
            String[] parts = entry.split("=", 2);
            if (parts.length != 2) {
                throw new IllegalArgumentException("Invalid entry format: " + entry);
            }

            String name = parts[0].trim();
            String value = parts[1].trim();

            if (name.isEmpty() || value.isEmpty()) {
                throw new IllegalArgumentException("Empty key or value in entry: " + entry);
            }

            NotionIntegrationEntry notionEntry = new NotionIntegrationEntry(name, resourceType, value);
            registry.register(notionEntry);

            String msg = String.format("Registered Notion integration entry: %s%n", notionEntry);
            debugOutput.append(msg);
            LOG_THIS(msg);

        } catch (Exception e) {
            String errMsg = String.format(
                    "Failed to parse/register Notion integration entry '%s' for property '%s': %s%n",
                    entry, propertyKey, e.getMessage());
            debugOutput.append(errMsg);
            LOG_THIS(errMsg);
        }
    }

    /**
     * Simple diagnostic method for quick inspection.
     */
    @Override
    public String toString() {
        return "NotionIntegrationLoader{" +
                "class=" + this.getClass().getSimpleName() +
                ", purpose='Utility for loading Notion integration configs from Environment'}";
    }

    /**
     * Local helper for consistent debug-level logging.
     */
    private static void LOG_THIS(String message) {
        LOG(NotionIntegrationLoader.class, LogLevel.DEBUG, message);
    }
}
