package com.rumpus.common.Integrations;

import java.util.HashMap;
import java.util.Map;

/**
 * Enum representing supported Notion resource types.
 * <p>
 * Each resource type corresponds to a kind of object in the Notion API.
 * This enum provides safe mapping between a string value and the strongly-typed
 * enum,
 * making it easier to parse configuration files, JSON payloads, or database
 * fields.
 */
public enum NotionResourceType {

    /** A Notion database resource. */
    DATABASE("database"),

    /** A Notion page resource. */
    PAGE("page"),

    /** A Notion block resource (e.g., paragraph, heading, list item). */
    BLOCK("block"),

    /** A Notion user resource. */
    USER("user"),

    /** A Notion comment resource. */
    COMMENT("comment");

    private final String value;

    private static final Map<String, NotionResourceType> LOOKUP = new HashMap<>();

    static {
        for (NotionResourceType type : NotionResourceType.values()) {
            LOOKUP.put(type.value, type);
        }
    }

    NotionResourceType(String value) {
        this.value = value;
    }

    /**
     * Returns the lowercase string value associated with this resource type.
     * Useful for serialization or config mapping.
     *
     * @return string value of the resource type
     */
    public String getValue() {
        return value;
    }

    /**
     * Resolves a {@link NotionResourceType} from its string value.
     *
     * @param value the string value (case-insensitive)
     * @return the corresponding enum constant
     * @throws IllegalArgumentException if the value does not match any type
     */
    public static NotionResourceType fromValue(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Value must not be null or blank");
        }
        NotionResourceType type = LOOKUP.get(value.toLowerCase());
        if (type == null) {
            throw new IllegalArgumentException("Unknown NotionResourceType: " + value);
        }
        return type;
    }

    @Override
    public String toString() {
        return value;
    }
}
