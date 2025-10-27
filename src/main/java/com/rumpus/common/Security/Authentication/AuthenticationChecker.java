package com.rumpus.common.Security.Authentication;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.authentication.AnonymousAuthenticationToken;

/**
 * AuthenticationChecker is a reusable component that provides utility
 * methods related to user authentication. It is intended to be injected
 * into controllers or services that need to verify authentication state.
 * 
 * This component does **not** handle HTTP requests directly but returns
 * ResponseEntity<Boolean> for convenience in controllers.
 * 
 * Usage example in a controller:
 * 
 * <pre>
 * {@code
 * @RestController
 * @RequestMapping("/api/auth")
 * public class AuthController {
 *     private final AuthenticationChecker authChecker;
 * 
 *     public AuthController(AuthenticationChecker authChecker) {
 *         this.authChecker = authChecker;
 *     }
 * 
 *     @GetMapping("/is_authenticated")
 *     public ResponseEntity<Boolean> isAuthenticated(Authentication authentication) {
 *         return authChecker.isAuthenticated(authentication);
 *     }
 * }
 * }
 * </pre>
 */
public class AuthenticationChecker {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationChecker.class);

    public AuthenticationChecker() {
    }

    /**
     * Checks if the given Authentication object represents an authenticated user.
     *
     * @param authentication the Spring Security Authentication object, injected by
     *                       the framework
     * @return ResponseEntity<Boolean> with HTTP 202 ACCEPTED and body true if
     *         authenticated, false otherwise
     */
    public ResponseEntity<Boolean> isAuthenticated(Authentication authentication) {
        boolean isAuth = false;

        if (authentication != null) {
            isAuth = authentication.isAuthenticated();
            LOGGER.debug("AuthenticationChecker: User isAuthenticated={}", isAuth);
        } else {
            LOGGER.debug("AuthenticationChecker: Authentication object is null");
        }

        // Return a standard HTTP response
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(isAuth);
    }

    public ResponseEntity<?> isAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null &&
                authentication.isAuthenticated() &&
                !(authentication instanceof AnonymousAuthenticationToken)) {
            // Optionally: return username or roles
            return ResponseEntity.ok(Map.of(
                    "authenticated", true,
                    "username", authentication.getName(),
                    "roles", authentication.getAuthorities()));
        } else {
            return ResponseEntity.ok(Map.of("authenticated", false));
        }
    }
}
