package com.rumpus.common.Controller;

/**
 * Base class for REST controllers.
 *
 * <p>
 * Provides functionality common to all REST endpoints in the application. REST
 * controllers should extend this class rather than extending
 * {@link AbstractCommonController} directly.
 * </p>
 *
 * <p>
 * Domain-specific functionality such as authentication, user management,
 * administration, or application-specific endpoints should be implemented in
 * dedicated abstract subclasses rather than here.
 * </p>
 */
public abstract class AbstractCommonRestController
        extends
            AbstractCommonController {

    /**
     * The base path associated with this controller.
     *
     * <p>
     * Subclasses should initialize this to the appropriate path during construction
     * or initialization.
     * </p>
     */
    protected final String basePath;

    protected AbstractCommonRestController(String basePath) {
        super();
        this.basePath = basePath;
    }

    /**
     * Returns the base path served by this controller.
     *
     * @return the controller's base path
     */
    public String getBasePath() {
        return basePath;
    }

    /**
     * TODO: Consider removing. Am I only using this for debugging purposes? - chuck
     * 2026/7/30
     */
    // @GetMapping(value = "/current_base_path")
    // public final ResponseEntity<Map<String, String>> getCurrentBasePath() {
    // return ResponseEntity.ok(Map.of("path", this.getBasePath()));
    // }
}
