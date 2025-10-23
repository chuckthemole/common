package com.rumpus.common.Config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public abstract class AbstractOpenApiConfig {

    /**
     * Returns the OpenAPI bean with shared configuration.
     */
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI().info(new Info()
                .title(getTitle())
                .description(getDescription())
                .version(getVersion())
                .license(new License().name(getLicenseName()).url(getLicenseUrl()))
                .contact(new Contact().name(getContactName()).email(getContactEmail())));
    }

    /**
     * Abstract method for consumer apps to define API groups.
     * Should return a list of ApiGroup objects.
     */
    protected abstract List<ApiGroup> initApiGroups();

    /**
     * Creates GroupedOpenApi beans for each API group provided
     * by the consumer app via initApiGroups().
     * If none are provided, a default group matching all paths is created.
     */
    @Bean
    public List<GroupedOpenApi> apiGroups() {
        List<ApiGroup> groups = initApiGroups();
        if (groups == null || groups.isEmpty()) {
            return List.of(GroupedOpenApi.builder()
                    .group("default")
                    .pathsToMatch("/**")
                    .build());
        }
        return groups.stream()
                .map(group -> GroupedOpenApi.builder()
                        .group(group.getName())
                        .pathsToMatch(group.getPaths())
                        .build())
                .collect(Collectors.toList());
    }

    // ---------------------------------------------------------------------
    // Metadata methods (can be overridden)
    // ---------------------------------------------------------------------

    protected String getTitle() {
        return "RumpusHub API";
    }

    protected String getDescription() {
        return "Common OpenAPI documentation for RumpusHub services.";
    }

    protected String getVersion() {
        return "v1.0";
    }

    protected String getLicenseName() {
        return "Apache 2.0";
    }

    protected String getLicenseUrl() {
        return "https://www.apache.org/licenses/LICENSE-2.0.html";
    }

    protected String getContactName() {
        return "RumpusHub Engineering";
    }

    protected String getContactEmail() {
        return "support@rumpushub.com";
    }

    // ---------------------------------------------------------------------
    // Inner class representing an API group
    // ---------------------------------------------------------------------
    public static class ApiGroup {
        private final String name;
        private final String[] paths;

        public ApiGroup(String name, String... paths) {
            this.name = name;
            this.paths = paths;
        }

        public String getName() {
            return name;
        }

        public String[] getPaths() {
            return paths;
        }
    }
}
