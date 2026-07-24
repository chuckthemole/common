package com.rumpus.common.Config.Documentation.OpenAPI;

/**
 * Represents a named Swagger/OpenAPI group.
 *
 * Example:
 *
 * <pre>
 * new ApiGroup("Admin", "/api/admin/**");
 * new ApiGroup("Public", "/api/public/**");
 * </pre>
 */
public class ApiGroup {

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
