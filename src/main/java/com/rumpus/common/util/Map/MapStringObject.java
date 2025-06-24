package com.rumpus.common.util.Map;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Utility class for working with Map<String, Object> instances.
 * Provides type-safe getters, string formatting, and common operations.
 */
public class MapStringObject {

    private final Map<String, Object> map;

    // Constructors
    public MapStringObject() {
        this.map = new HashMap<>();
    }

    public MapStringObject(Map<String, Object> map) {
        this.map = map != null ? map : new HashMap<>();
    }

    public MapStringObject(int initialCapacity) {
        this.map = new HashMap<>(initialCapacity);
    }

    // Factory methods
    public static MapStringObject of() {
        return new MapStringObject();
    }

    public static MapStringObject of(String key, Object value) {
        MapStringObject mso = new MapStringObject();
        mso.put(key, value);
        return mso;
    }

    public static MapStringObject of(String k1, Object v1, String k2, Object v2) {
        MapStringObject mso = new MapStringObject();
        mso.put(k1, v1).put(k2, v2);
        return mso;
    }

    public static MapStringObject from(Map<String, Object> map) {
        return new MapStringObject(map);
    }

    // Basic operations
    public MapStringObject put(String key, Object value) {
        map.put(key, value);
        return this;
    }

    public MapStringObject putIfNotNull(String key, Object value) {
        if (value != null) {
            map.put(key, value);
        }
        return this;
    }

    public MapStringObject putAll(Map<String, Object> other) {
        if (other != null) {
            map.putAll(other);
        }
        return this;
    }

    public Object get(String key) {
        return map.get(key);
    }

    public boolean containsKey(String key) {
        return map.containsKey(key);
    }

    public boolean isEmpty() {
        return map.isEmpty();
    }

    public int size() {
        return map.size();
    }

    public Set<String> keySet() {
        return map.keySet();
    }

    public Collection<Object> values() {
        return map.values();
    }

    public Map<String, Object> asMap() {
        return new HashMap<>(map);
    }

    // Type-safe getters with defaults
    public String getString(String key) {
        return getString(key, null);
    }

    public String getString(String key, String defaultValue) {
        Object value = map.get(key);
        if (value == null)
            return defaultValue;
        return value.toString();
    }

    public Integer getInteger(String key) {
        return getInteger(key, null);
    }

    public Integer getInteger(String key, Integer defaultValue) {
        Object value = map.get(key);
        if (value == null)
            return defaultValue;

        if (value instanceof Integer)
            return (Integer) value;
        if (value instanceof Number)
            return ((Number) value).intValue();

        try {
            return Integer.valueOf(value.toString());
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    public Long getLong(String key) {
        return getLong(key, null);
    }

    public Long getLong(String key, Long defaultValue) {
        Object value = map.get(key);
        if (value == null)
            return defaultValue;

        if (value instanceof Long)
            return (Long) value;
        if (value instanceof Number)
            return ((Number) value).longValue();

        try {
            return Long.valueOf(value.toString());
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    public Double getDouble(String key) {
        return getDouble(key, null);
    }

    public Double getDouble(String key, Double defaultValue) {
        Object value = map.get(key);
        if (value == null)
            return defaultValue;

        if (value instanceof Double)
            return (Double) value;
        if (value instanceof Number)
            return ((Number) value).doubleValue();

        try {
            return Double.valueOf(value.toString());
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    public BigDecimal getBigDecimal(String key) {
        return getBigDecimal(key, null);
    }

    public BigDecimal getBigDecimal(String key, BigDecimal defaultValue) {
        Object value = map.get(key);
        if (value == null)
            return defaultValue;

        if (value instanceof BigDecimal)
            return (BigDecimal) value;
        if (value instanceof Number)
            return BigDecimal.valueOf(((Number) value).doubleValue());

        try {
            return new BigDecimal(value.toString());
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    public Boolean getBoolean(String key) {
        return getBoolean(key, null);
    }

    public Boolean getBoolean(String key, Boolean defaultValue) {
        Object value = map.get(key);
        if (value == null)
            return defaultValue;

        if (value instanceof Boolean)
            return (Boolean) value;

        String stringValue = value.toString().toLowerCase();
        if ("true".equals(stringValue) || "1".equals(stringValue) || "yes".equals(stringValue)) {
            return true;
        } else if ("false".equals(stringValue) || "0".equals(stringValue) || "no".equals(stringValue)) {
            return false;
        }

        return defaultValue;
    }

    public LocalDate getLocalDate(String key) {
        return getLocalDate(key, null);
    }

    public LocalDate getLocalDate(String key, LocalDate defaultValue) {
        Object value = map.get(key);
        if (value == null)
            return defaultValue;

        if (value instanceof LocalDate)
            return (LocalDate) value;

        try {
            return LocalDate.parse(value.toString());
        } catch (DateTimeParseException e) {
            return defaultValue;
        }
    }

    public LocalDateTime getLocalDateTime(String key) {
        return getLocalDateTime(key, null);
    }

    public LocalDateTime getLocalDateTime(String key, LocalDateTime defaultValue) {
        Object value = map.get(key);
        if (value == null)
            return defaultValue;

        if (value instanceof LocalDateTime)
            return (LocalDateTime) value;

        try {
            return LocalDateTime.parse(value.toString());
        } catch (DateTimeParseException e) {
            return defaultValue;
        }
    }

    @SuppressWarnings("unchecked")
    public List<Object> getList(String key) {
        Object value = map.get(key);
        if (value == null)
            return null;

        if (value instanceof List)
            return (List<Object>) value;
        if (value instanceof Collection)
            return new ArrayList<>((Collection<Object>) value);

        return null;
    }

    @SuppressWarnings("unchecked")
    public Map<String, Object> getMap(String key) {
        Object value = map.get(key);
        if (value == null)
            return null;

        if (value instanceof Map)
            return (Map<String, Object>) value;

        return null;
    }

    // String formatting methods
    public String toFormattedString() {
        return toFormattedString(0);
    }

    public String toFormattedString(int indent) {
        if (map.isEmpty()) {
            return "{}";
        }

        String indentStr = "  ".repeat(indent);
        String itemIndentStr = "  ".repeat(indent + 1);

        StringBuilder sb = new StringBuilder();
        sb.append("{\n");

        List<String> sortedKeys = map.keySet().stream().sorted().collect(Collectors.toList());

        for (int i = 0; i < sortedKeys.size(); i++) {
            String key = sortedKeys.get(i);
            Object value = map.get(key);

            sb.append(itemIndentStr).append(key).append(": ");
            sb.append(formatValue(value, indent + 1));

            if (i < sortedKeys.size() - 1) {
                sb.append(",");
            }
            sb.append("\n");
        }

        sb.append(indentStr).append("}");
        return sb.toString();
    }

    private String formatValue(Object value, int indent) {
        if (value == null) {
            return "null";
        } else if (value instanceof String) {
            return "\"" + value + "\"";
        } else if (value instanceof Map) {
            @SuppressWarnings("unchecked")
            MapStringObject nested = new MapStringObject((Map<String, Object>) value);
            return nested.toFormattedString(indent);
        } else if (value instanceof Collection) {
            Collection<?> collection = (Collection<?>) value;
            if (collection.isEmpty()) {
                return "[]";
            }
            return "[" + collection.stream()
                    .map(item -> formatValue(item, indent))
                    .collect(Collectors.joining(", ")) + "]";
        } else {
            return value.toString();
        }
    }

    public String toSimpleString() {
        return map.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .map(entry -> entry.getKey() + "=" +
                        (entry.getValue() == null ? "null" : entry.getValue().toString()))
                .collect(Collectors.joining(", ", "{", "}"));
    }

    public String toKeyValueString() {
        return toKeyValueString(": ");
    }

    public String toKeyValueString(String separator) {
        return map.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .map(entry -> entry.getKey() + separator +
                        (entry.getValue() == null ? "null" : entry.getValue().toString()))
                .collect(Collectors.joining("\n"));
    }

    // Utility methods
    public MapStringObject filterByKeys(String... keys) {
        Set<String> keySet = new HashSet<>(Arrays.asList(keys));
        Map<String, Object> filtered = map.entrySet().stream()
                .filter(entry -> keySet.contains(entry.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        return new MapStringObject(filtered);
    }

    public MapStringObject filterByKeyPrefix(String prefix) {
        Map<String, Object> filtered = map.entrySet().stream()
                .filter(entry -> entry.getKey().startsWith(prefix))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        return new MapStringObject(filtered);
    }

    public MapStringObject excludeKeys(String... keys) {
        Set<String> keySet = new HashSet<>(Arrays.asList(keys));
        Map<String, Object> filtered = map.entrySet().stream()
                .filter(entry -> !keySet.contains(entry.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        return new MapStringObject(filtered);
    }

    public MapStringObject excludeNullValues() {
        Map<String, Object> filtered = map.entrySet().stream()
                .filter(entry -> entry.getValue() != null)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        return new MapStringObject(filtered);
    }

    public MapStringObject copy() {
        return new MapStringObject(new HashMap<>(map));
    }

    public MapStringObject deepCopy() {
        Map<String, Object> deepCopied = new HashMap<>();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            Object value = entry.getValue();
            if (value instanceof Map) {
                @SuppressWarnings("unchecked")
                MapStringObject nested = new MapStringObject((Map<String, Object>) value);
                deepCopied.put(entry.getKey(), nested.deepCopy().asMap());
            } else if (value instanceof List) {
                deepCopied.put(entry.getKey(), new ArrayList<>((List<?>) value));
            } else {
                deepCopied.put(entry.getKey(), value);
            }
        }
        return new MapStringObject(deepCopied);
    }

    // Validation methods
    public boolean hasAllKeys(String... keys) {
        return Arrays.stream(keys).allMatch(map::containsKey);
    }

    public boolean hasAnyKey(String... keys) {
        return Arrays.stream(keys).anyMatch(map::containsKey);
    }

    public List<String> getMissingKeys(String... requiredKeys) {
        return Arrays.stream(requiredKeys)
                .filter(key -> !map.containsKey(key))
                .collect(Collectors.toList());
    }

    // Static helper methods for Map<String, Object>

    /**
     * Converts a Map<String, Object> to a formatted JSON-like string
     */
    public static String toString(Map<String, Object> map) {
        if (map == null)
            return "null";
        return new MapStringObject(map).toFormattedString();
    }

    /**
     * Converts a Map<String, Object> to a simple one-line string
     */
    public static String toSimpleString(Map<String, Object> map) {
        if (map == null)
            return "null";
        return new MapStringObject(map).toSimpleString();
    }

    /**
     * Converts a Map<String, Object> to key-value pairs string
     */
    public static String toKeyValueString(Map<String, Object> map) {
        if (map == null)
            return "null";
        return new MapStringObject(map).toKeyValueString();
    }

    /**
     * Converts a Map<String, Object> to key-value pairs with custom separator
     */
    public static String toKeyValueString(Map<String, Object> map, String separator) {
        if (map == null)
            return "null";
        return new MapStringObject(map).toKeyValueString(separator);
    }

    /**
     * Safe string getter from Map<String, Object>
     */
    public static String getString(Map<String, Object> map, String key) {
        return getString(map, key, null);
    }

    /**
     * Safe string getter from Map<String, Object> with default value
     */
    public static String getString(Map<String, Object> map, String key, String defaultValue) {
        if (map == null)
            return defaultValue;
        return new MapStringObject(map).getString(key, defaultValue);
    }

    /**
     * Safe integer getter from Map<String, Object>
     */
    public static Integer getInteger(Map<String, Object> map, String key) {
        return getInteger(map, key, null);
    }

    /**
     * Safe integer getter from Map<String, Object> with default value
     */
    public static Integer getInteger(Map<String, Object> map, String key, Integer defaultValue) {
        if (map == null)
            return defaultValue;
        return new MapStringObject(map).getInteger(key, defaultValue);
    }

    /**
     * Safe boolean getter from Map<String, Object>
     */
    public static Boolean getBoolean(Map<String, Object> map, String key) {
        return getBoolean(map, key, null);
    }

    /**
     * Safe boolean getter from Map<String, Object> with default value
     */
    public static Boolean getBoolean(Map<String, Object> map, String key, Boolean defaultValue) {
        if (map == null)
            return defaultValue;
        return new MapStringObject(map).getBoolean(key, defaultValue);
    }

    /**
     * Checks if map is null or empty
     */
    public static boolean isNullOrEmpty(Map<String, Object> map) {
        return map == null || map.isEmpty();
    }

    /**
     * Checks if map is not null and not empty
     */
    public static boolean isNotEmpty(Map<String, Object> map) {
        return map != null && !map.isEmpty();
    }

    /**
     * Safely gets the size of a map (returns 0 if null)
     */
    public static int safeSize(Map<String, Object> map) {
        return map == null ? 0 : map.size();
    }

    /**
     * Checks if map contains all specified keys
     */
    public static boolean hasAllKeys(Map<String, Object> map, String... keys) {
        if (map == null || keys == null)
            return false;
        return Arrays.stream(keys).allMatch(map::containsKey);
    }

    /**
     * Checks if map contains any of the specified keys
     */
    public static boolean hasAnyKey(Map<String, Object> map, String... keys) {
        if (map == null || keys == null)
            return false;
        return Arrays.stream(keys).anyMatch(map::containsKey);
    }

    /**
     * Gets missing keys from a map
     */
    public static List<String> getMissingKeys(Map<String, Object> map, String... requiredKeys) {
        if (requiredKeys == null)
            return Collections.emptyList();
        if (map == null)
            return Arrays.asList(requiredKeys);

        return Arrays.stream(requiredKeys)
                .filter(key -> !map.containsKey(key))
                .collect(Collectors.toList());
    }

    /**
     * Creates a new map excluding null values
     */
    public static Map<String, Object> excludeNullValues(Map<String, Object> map) {
        if (map == null)
            return new HashMap<>();

        return map.entrySet().stream()
                .filter(entry -> entry.getValue() != null)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    /**
     * Creates a new map with only specified keys
     */
    public static Map<String, Object> filterByKeys(Map<String, Object> map, String... keys) {
        if (map == null || keys == null)
            return new HashMap<>();

        Set<String> keySet = new HashSet<>(Arrays.asList(keys));
        return map.entrySet().stream()
                .filter(entry -> keySet.contains(entry.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    /**
     * Creates a new map excluding specified keys
     */
    public static Map<String, Object> excludeKeys(Map<String, Object> map, String... keys) {
        if (map == null)
            return new HashMap<>();
        if (keys == null)
            return new HashMap<>(map);

        Set<String> keySet = new HashSet<>(Arrays.asList(keys));
        return map.entrySet().stream()
                .filter(entry -> !keySet.contains(entry.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    /**
     * Merges multiple maps into one (later maps override earlier ones)
     */
    @SafeVarargs
    public static Map<String, Object> merge(Map<String, Object>... maps) {
        Map<String, Object> result = new HashMap<>();
        if (maps != null) {
            for (Map<String, Object> map : maps) {
                if (map != null) {
                    result.putAll(map);
                }
            }
        }
        return result;
    }

    /**
     * Creates a map from alternating key-value arguments
     * Example: createMap("name", "John", "age", 30)
     */
    public static Map<String, Object> createMap(Object... keyValuePairs) {
        if (keyValuePairs == null || keyValuePairs.length == 0) {
            return new HashMap<>();
        }

        if (keyValuePairs.length % 2 != 0) {
            throw new IllegalArgumentException("Key-value pairs must be even number of arguments");
        }

        Map<String, Object> map = new HashMap<>();
        for (int i = 0; i < keyValuePairs.length; i += 2) {
            String key = keyValuePairs[i].toString();
            Object value = keyValuePairs[i + 1];
            map.put(key, value);
        }

        return map;
    }

    /**
     * Flattens a nested map structure using dot notation
     * Example: {"user": {"name": "John"}} becomes {"user.name": "John"}
     */
    public static Map<String, Object> flatten(Map<String, Object> map) {
        return flatten(map, "");
    }

    /**
     * Flattens a nested map structure with custom prefix
     */
    public static Map<String, Object> flatten(Map<String, Object> map, String prefix) {
        Map<String, Object> result = new HashMap<>();
        if (map == null)
            return result;

        for (Map.Entry<String, Object> entry : map.entrySet()) {
            String key = prefix.isEmpty() ? entry.getKey() : prefix + "." + entry.getKey();
            Object value = entry.getValue();

            if (value instanceof Map) {
                @SuppressWarnings("unchecked")
                Map<String, Object> nestedMap = (Map<String, Object>) value;
                result.putAll(flatten(nestedMap, key));
            } else {
                result.put(key, value);
            }
        }

        return result;
    }

    /**
     * Converts a map to a properties-like string format
     */
    public static String toPropertiesString(Map<String, Object> map) {
        if (map == null)
            return "";

        return map.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .map(entry -> entry.getKey() + "=" +
                        (entry.getValue() == null ? "" : entry.getValue().toString()))
                .collect(Collectors.joining("\n"));
    }

    /**
     * Converts a map to URL query string format
     */
    public static String toQueryString(Map<String, Object> map) {
        if (map == null || map.isEmpty())
            return "";

        return map.entrySet().stream()
                .filter(entry -> entry.getValue() != null)
                .map(entry -> entry.getKey() + "=" + entry.getValue().toString())
                .collect(Collectors.joining("&"));
    }

    // Override methods
    @Override
    public String toString() {
        return toSimpleString();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        MapStringObject that = (MapStringObject) obj;
        return Objects.equals(map, that.map);
    }

    @Override
    public int hashCode() {
        return Objects.hash(map);
    }
}