package com.rumpus.common.Config.Documentation.OpenAPI;

import java.util.List;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

/**
 * Common OpenAPI configuration.
 *
 * <p>
 * Responsibilities:
 * <ul>
 * <li>Creates the application's {@link OpenAPI} definition.</li>
 * <li>Creates Swagger/OpenAPI groups from any {@link ApiGroup} beans supplied
 * by the consuming application.</li>
 * </ul>
 *
 * <p>
 * Applications typically customize this configuration by:
 * <ol>
 * <li>Overriding metadata through {@code rumpus.openapi.*} properties.</li>
 * <li>Declaring one or more {@link ApiGroup} beans.</li>
 * </ol>
 *
 * <p>
 * If no {@link ApiGroup} beans are provided, a single "default" group matching
 * all endpoints is created automatically.
 */
@Configuration
@EnableConfigurationProperties(OpenApiProperties.class)
public class OpenApiConfig {

    /**
     * Creates the application's OpenAPI definition.
     */
    @Bean
    public OpenAPI openApi(OpenApiProperties properties) {

        return new OpenAPI()
                .info(new Info()
                        .title(properties.getTitle())
                        .description(properties.getDescription())
                        .version(properties.getVersion())
                        .contact(new Contact()
                                .name(properties.getContact().getName())
                                .email(properties.getContact().getEmail()))
                        .license(new License()
                                .name(properties.getLicense().getName())
                                .url(properties.getLicense().getUrl())));
    }

    /**
     * Creates Swagger API groups.
     *
     * <p>
     * Any {@link ApiGroup} beans declared by the consuming application are
     * automatically converted into {@link GroupedOpenApi} instances.
     *
     * <p>
     * If no groups are supplied, a single group matching every endpoint is created.
     */
    @Bean
    public List<GroupedOpenApi> groupedOpenApis(
            ObjectProvider<List<ApiGroup>> apiGroupsProvider) {

        List<ApiGroup> apiGroups = apiGroupsProvider.getIfAvailable(List::of);

        if (apiGroups.isEmpty()) {
            return List.of(
                    GroupedOpenApi.builder()
                            .group("default")
                            .pathsToMatch("/**")
                            .build());
        }

        return apiGroups.stream()
                .map(apiGroup -> GroupedOpenApi.builder()
                        .group(apiGroup.getName())
                        .pathsToMatch(apiGroup.getPaths())
                        .build())
                .toList();
    }
}
