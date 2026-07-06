package com.rumpus.common.Dao;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Describes database table metadata used by DAO implementations.
 *
 * <p>
 * This abstraction removes hard-coded assumptions about fixed table layouts
 * (e.g. only "main + meta"). Instead, it allows DAOs to define one or more
 * logical tables they operate on.
 * </p>
 *
 * <p>
 * Common keys:
 * <ul>
 * <li>{@code main} - primary table (required)</li>
 * <li>{@code meta} - optional metadata table</li>
 * <li>{@code audit} - optional audit/log table</li>
 * </ul>
 * </p>
 */
public final class TableDefinition {

    public static final String MAIN = "main";
    public static final String META = "meta";
    public static final String AUDIT = "audit";

    private final Map<String, String> tables;

    /**
     * Create a TableDefinition with a prebuilt map of table mappings.
     *
     * @param tables map of logical table names to physical DB table names
     */
    private TableDefinition(Map<String, String> tables) {
        this.tables = Collections.unmodifiableMap(new HashMap<>(tables));
    }

    /**
     * Creates a builder for TableDefinition.
     *
     * @return new Builder instance
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Gets a table name by logical key.
     *
     * @param key logical table key (e.g. "main", "meta")
     * @return physical table name
     */
    public String get(String key) {
        return tables.get(key);
    }

    /**
     * Gets the primary table (required).
     *
     * @return main table name
     * @throws IllegalStateException if not defined
     */
    public String getMain() {
        return require(MAIN);
    }

    /**
     * Gets the metadata table if present.
     *
     * @return meta table name or null if not defined
     */
    public String getMeta() {
        return tables.get(META);
    }

    /**
     * Gets the audit table if present.
     *
     * @return audit table name or null if not defined
     */
    public String getAudit() {
        return tables.get(AUDIT);
    }

    /**
     * Returns all table mappings (immutable).
     *
     * @return map of all table definitions
     */
    public Map<String, String> getAll() {
        return tables;
    }

    /**
     * Requires a table to exist for a given key.
     */
    private String require(String key) {
        String value = tables.get(key);
        if (value == null || value.isBlank()) {
            throw new IllegalStateException("Missing required table definition: " + key);
        }
        return value;
    }

    @Override
    public String toString() {
        return "TableDefinition" + tables;
    }

    /**
     * Builder for TableDefinition.
     */
    public static class Builder {

        private final Map<String, String> tables = new HashMap<>();

        /**
         * Sets the main table (required for most DAOs).
         */
        public Builder main(String tableName) {
            putRequired(MAIN, tableName);
            return this;
        }

        /**
         * Sets a metadata table (optional).
         */
        public Builder meta(String tableName) {
            tables.put(META, tableName);
            return this;
        }

        /**
         * Sets an audit table (optional).
         */
        public Builder audit(String tableName) {
            tables.put(AUDIT, tableName);
            return this;
        }

        /**
         * Adds a custom logical table mapping.
         */
        public Builder table(String key, String tableName) {
            Objects.requireNonNull(key, "key");
            Objects.requireNonNull(tableName, "tableName");
            tables.put(key, tableName);
            return this;
        }

        /**
         * Builds the immutable TableDefinition.
         */
        public TableDefinition build() {
            if (!tables.containsKey(MAIN)) {
                throw new IllegalStateException("Main table must be defined");
            }
            return new TableDefinition(tables);
        }

        private void putRequired(String key, String value) {
            if (value == null || value.isBlank()) {
                throw new IllegalArgumentException(key + " table cannot be null/blank");
            }
            tables.put(key, value);
        }
    }
}