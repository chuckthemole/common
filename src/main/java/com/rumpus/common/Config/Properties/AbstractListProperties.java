package com.rumpus.common.Config.Properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import java.util.Collections;
import java.util.List;

/**
 * Generic reusable configuration properties class for mapping a list of values from application.yml/application.properties.
 *
 * Usage:
 *   1. Extend this class with your own prefix.
 *      Example:
 *        @ConfigurationProperties(prefix = "frontend")
 *        @Validated
 *        public class FrontendProperties extends ListProperties {}
 *
 *   2. In your application.yml:
 *        frontend:
 *          origins:
 *            - "http://localhost:3000"
 *            - "http://localhost:3001"
 *
 *   3. Inject and use:
 *        @Autowired
 *        private FrontendProperties frontendProperties;
 *
 *        List<String> origins = frontendProperties.getValues();
 */
@Validated
public abstract class AbstractListProperties {

    /**
     * List of values bound from configuration.
     * The property key inside the YAML/Properties file must match the field name ("values" by default).
     */
    private List<String> values;

    /**
     * Get the configured values as an immutable list.
     * Will never return {@code null}.
     */
    public List<String> getValues() {
        return values == null ? Collections.emptyList() : Collections.unmodifiableList(values);
    }

    /**
     * Setter used by Spring Boot during property binding.
     */
    public void setValues(List<String> values) {
        this.values = values;
    }

    /**
     * Convenience method to check if list is empty.
     */
    public boolean isEmpty() {
        return getValues().isEmpty();
    }
}
