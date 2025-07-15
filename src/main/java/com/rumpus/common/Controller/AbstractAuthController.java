package com.rumpus.common.Controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import com.rumpus.common.Auth.LoginRequest;
import com.rumpus.common.Auth.LoginResponse;
import com.rumpus.common.Auth.OAuth2Provider;
import com.rumpus.common.Manager.AbstractServiceManager;
import com.rumpus.common.Service.IUserService;
import com.rumpus.common.User.AbstractCommonUser;
import com.rumpus.common.User.AbstractCommonUserMetaData;
import com.rumpus.common.views.Template.IUserTemplate;

import jakarta.servlet.http.HttpServletRequest;

@RequestMapping("/auth")
abstract public class AbstractAuthController<

        /////////////////////////
        // Define generics here//
        /////////////////////////
        SERVICES extends AbstractServiceManager<?>, USER extends AbstractCommonUser<USER, USER_META>, USER_META extends AbstractCommonUserMetaData<USER_META>, USER_SERVICE extends IUserService<USER, USER_META>, USER_TEMPLATE extends IUserTemplate<USER, USER_META>>

        extends AbstractCommonController<SERVICES, USER, USER_META, USER_SERVICE, USER_TEMPLATE> {

    private static final Logger logger = LoggerFactory.getLogger(AbstractAuthController.class);

    public static final String COMMON_REST_API_PATH = "/auth";

    private AuthenticationManager authenticationManager;

    public AbstractAuthController(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    // ===================================================================================
    // OAUTH2 LOGIN FLOW
    // ===================================================================================
    //
    // This controller handles OAuth2-based login for external providers like
    // Google.
    // It is mapped under the `/auth` path and exposes endpoints to:
    // - Initiate login with `/auth/{provider}/authorize`
    // - Handle provider callback via `/auth/{provider}/callback`
    // - Expose supported providers via `/auth/providers`
    //
    // All logic related to token exchange, user info retrieval, and app-specific
    // user handling must be implemented by the subclass through the abstract
    // methods.
    //
    // ===================================================================================

    /**
     * Initiate OAuth2 authorization for any supported provider.
     * Example: /auth/google/authorize
     */
    @GetMapping("/{provider}/authorize")
    public RedirectView authorize(@PathVariable String provider, HttpServletRequest request) {

        if (!OAuth2Provider.isSupported(provider)) {
            throw new IllegalArgumentException("Unsupported OAuth2 provider: " + provider);
        }

        OAuth2Provider oauthProvider = OAuth2Provider.fromString(provider);
        String authUrl = buildAuthorizationUrl(oauthProvider, request);
        LOG(AbstractAuthController.class, "Built authorization URL: ", authUrl);

        return new RedirectView(authUrl);
    }

    /**
     * Handle OAuth2 callback for any supported provider.
     * Exchanges code for token and handles user login/registration.
     */
    @GetMapping("/{provider}/callback")
    public ResponseEntity<?> handleCallback(
            @PathVariable String provider,
            @RequestParam("code") String code,
            @RequestParam(value = "state", required = false) String state,
            @RequestParam Map<String, String> allParams) {

        logger.info("handleCallback");

        if (!OAuth2Provider.isSupported(provider)) {
            return ResponseEntity.badRequest().body("Unsupported OAuth2 provider: " + provider);
        }

        OAuth2Provider oauthProvider = OAuth2Provider.fromString(provider);
        logger.info(provider);

        try {
            // Validate state parameter if present (optional)
            if (state != null && !validateState(state)) {
                final String msg = "Invalid state parameter";
                logger.error(msg);
                return ResponseEntity.badRequest().body(msg);
            }

            // Step 1: Exchange code for access token
            String accessToken = exchangeCodeForToken(oauthProvider, code);

            // Step 2: Retrieve user information
            Map<String, Object> userInfo = getUserInfo(oauthProvider, accessToken);

            // Step 3: App-specific login handling (e.g., JWT, session, DB entry)
            return handleUserAuthentication(oauthProvider, userInfo, accessToken);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Authentication failed: " + e.getMessage());
        }
    }

    /**
     * Get all supported providers (useful for frontend auto-discovery)
     */
    @GetMapping("/providers")
    public ResponseEntity<OAuth2Provider[]> getSupportedProviders() {
        return ResponseEntity.ok(OAuth2Provider.values());
    }

    /**
     * ============================================================================
     * Username/Password Login Endpoint
     * --------------------------------------------------------------------------
     * This endpoint handles traditional form-based authentication using a
     * username and password. It leverages Spring Security's
     * AuthenticationManager to authenticate the user.
     *
     * Endpoint: POST /auth/login
     * Request Body: JSON with "username" and "password" fields
     * Response: 200 OK with LoginResponse on success,
     * 401 Unauthorized on failure
     *
     * Typical use case: Non-OAuth2 clients or fallback login for local users.
     * ============================================================================
     */
    // @PostMapping("/login")
    // public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
    //     final String username = loginRequest.getUsername();
    //     final String password = loginRequest.getPassword();

    //     try {
    //         Authentication authentication = authenticationManager.authenticate(
    //                 new UsernamePasswordAuthenticationToken(username, password));

    //         // store session or generate token here
    //         SecurityContextHolder.getContext().setAuthentication(authentication);

    //         final LoginResponse response = new LoginResponse("Login successful", username);
    //         return ResponseEntity.ok(response);

    //     } catch (AuthenticationException ex) {
    //         return ResponseEntity.status(401).body("Invalid username or password");
    //     }
    // }

    /**
     * ============================================================================
     * Logout Endpoint
     * --------------------------------------------------------------------------
     * This endpoint handles user logout by clearing the security context and
     * invalidating the session. It ensures that subsequent requests are not
     * associated with the current authenticated user.
     *
     * Endpoint: POST /auth/logout
     * Response: 200 OK with confirmation message
     * ============================================================================
     */
    // @PostMapping("/logout")
    // public ResponseEntity<?> logout(HttpServletRequest request) {
    //     // Clear the Spring Security context
    //     SecurityContextHolder.clearContext();

    //     // Invalidate the HTTP session
    //     request.getSession(false); // don't create one if it doesn't exist
    //     if (request.getSession(false) != null) {
    //         request.getSession(false).invalidate();
    //     }

    //     logger.info("User logged out");

    //     Map<String, Object> response = new HashMap<>();
    //     response.put("message", "Logout successful");
    //     return ResponseEntity.ok(response);
    // }

    @GetMapping("/is_authenticated")
    public ResponseEntity<?> isAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()
                && !(authentication instanceof AnonymousAuthenticationToken)) {
            // Optionally: return username or roles
            return ResponseEntity.ok(Map.of(
                    "authenticated", true,
                    "username", authentication.getName(),
                    "roles", authentication.getAuthorities()));
        } else {
            return ResponseEntity.ok(Map.of("authenticated", false));
        }
    }

    // ===================================================================================
    // ABSTRACT METHODS (to be implemented by concrete AuthController)
    // ===================================================================================

    /**
     * Build the complete authorization URL for the given provider.
     * Should include client ID, redirect URI, scopes, and state.
     */
    protected abstract String buildAuthorizationUrl(OAuth2Provider provider, HttpServletRequest request);

    /**
     * Exchange the authorization code for an access token.
     */
    protected abstract String exchangeCodeForToken(OAuth2Provider provider, String code) throws Exception;

    /**
     * Fetch user information from the provider using the access token.
     */
    protected abstract Map<String, Object> getUserInfo(OAuth2Provider provider, String accessToken) throws Exception;

    /**
     * Handle application-specific logic after successful login.
     * This might involve session setup, JWT generation, user creation, etc.
     */
    protected abstract ResponseEntity<?> handleUserAuthentication(
            OAuth2Provider provider,
            Map<String, Object> userInfo,
            String accessToken);

    /**
     * Optionally validate the state parameter for CSRF protection.
     */
    protected boolean validateState(String state) {
        // Default: always valid — override for custom CSRF logic
        return true;
    }
}