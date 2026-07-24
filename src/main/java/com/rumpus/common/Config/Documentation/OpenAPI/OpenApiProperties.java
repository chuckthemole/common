package com.rumpus.common.Config.Documentation.OpenAPI;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Configuration properties for the OpenAPI/Swagger documentation.
 *
 * <p>
 * These properties provide sensible defaults for all Rumpus applications while
 * allowing each application to override them via application.yml.
 *
 * <pre>
 * rumpus:
 *   openapi:
 *     title: Admin API
 *     description: REST API for administration
 *     version: v2
 *     contact:
 *       name: Admin Team
 *       email: admin@example.com
 *     license:
 *       name: Apache 2.0
 *       url: https://www.apache.org/licenses/LICENSE-2.0.html
 * </pre>
 */
@ConfigurationProperties(prefix = "properties.documentation.openapi")
public class OpenApiProperties {

    /**
     * API title displayed in Swagger UI.
     */
    private String title = "RumpusHub API";

    /**
     * Description shown on the Swagger landing page.
     */
    private String description = "Common OpenAPI documentation for RumpusHub services.";

    /**
     * API version.
     */
    private String version = "v1.0";

    /**
     * Contact information.
     */
    private final Contact contact = new Contact();

    /**
     * License information.
     */
    private final License license = new License();

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Contact getContact() {
        return contact;
    }

    public License getLicense() {
        return license;
    }

    // TODO: Consider moving these to a separate class or file if they become more
    // complex or need to be reused elsewhere.
    /**
     * Contact information displayed in Swagger UI.
     */
    public static class Contact {

        private String name = "RumpusHub Engineering";

        private String email = "support@rumpushub.com";

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }
    }

    /**
     * License information displayed in Swagger UI.
     */
    public static class License {

        private String name = "Apache 2.0";

        private String url = "https://www.apache.org/licenses/LICENSE-2.0.html";

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
