package com.rumpus.common.Controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

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

        extends AbstractCommonController<
                /////////////////////////
                // Define generics here//
                /////////////////////////
                SERVICES, USER, USER_META, USER_SERVICE, USER_TEMPLATE> {

    private static final Logger logger = LoggerFactory.getLogger(AbstractAuthController.class);

    public static final String COMMON_REST_API_PATH = "/auth";

    /**
     * Initiate OAuth2 authorization for any supported provider
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
     * Handle OAuth2 callback for any supported provider
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
            // Validate state parameter if present
            if (state != null && !validateState(state)) {
                final String msg = String.valueOf("Invalid state parameter");
                logger.error(msg);
                return ResponseEntity.badRequest().body(msg);
            }

            // Exchange code for access token
            String accessToken = exchangeCodeForToken(oauthProvider, code);

            // Get user info from provider
            Map<String, Object> userInfo = getUserInfo(oauthProvider, accessToken);

            // Let implementing class handle the user authentication
            return handleUserAuthentication(oauthProvider, userInfo, accessToken);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Authentication failed: " + e.getMessage());
        }
    }

    /**
     * Get all supported providers (useful for frontend)
     */
    @GetMapping("/providers")
    public ResponseEntity<OAuth2Provider[]> getSupportedProviders() {
        return ResponseEntity.ok(OAuth2Provider.values());
    }

    // Abstract methods that implementing classes must override

    /**
     * Build the complete authorization URL with client ID, redirect URI, etc.
     */
    protected abstract String buildAuthorizationUrl(OAuth2Provider provider, HttpServletRequest request);

    /**
     * Exchange authorization code for access token
     */
    protected abstract String exchangeCodeForToken(OAuth2Provider provider, String code) throws Exception;

    /**
     * Get user information from the OAuth2 provider
     */
    protected abstract Map<String, Object> getUserInfo(OAuth2Provider provider, String accessToken) throws Exception;

    /**
     * Handle user authentication after successful OAuth2 flow
     * This is where each app can implement their own logic (create user, generate
     * JWT, etc.)
     */
    protected abstract ResponseEntity<?> handleUserAuthentication(
            OAuth2Provider provider,
            Map<String, Object> userInfo,
            String accessToken);

    /**
     * Validate the state parameter for CSRF protection
     */
    protected boolean validateState(String state) {
        // Default implementation - override for custom state validation
        return true;
    }

}
