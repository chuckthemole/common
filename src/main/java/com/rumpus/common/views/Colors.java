package com.rumpus.common.views;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;

/**
 * Stores key-value pairs of site color settings.
 * Uses a JSON column in MySQL for easy serialization/deserialization.
 */
public class Colors {

    /**
     * Map of color keys to values.
     * Example: "primary" -> "#8A4D76"
     */
    private Map<String, String> colors = new HashMap<>();

    public Colors() {
    }

    public Colors(Map<String, String> colors) {
        this.colors = colors;
    }

    // ----- JSON-friendly getters/setters -----

    @JsonAnyGetter
    public Map<String, String> getColors() {
        return colors;
    }

    @JsonAnySetter
    public void setColor(String key, String value) {
        this.colors.put(key, value);
    }

    public void setColors(Map<String, String> colors) {
        this.colors = colors;
    }
}
