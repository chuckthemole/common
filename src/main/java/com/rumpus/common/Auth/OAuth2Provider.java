package com.rumpus.common.Auth;

import java.util.Map;

/**
 * Enum representing common OAuth2 providers with their configuration details
 */
public enum OAuth2Provider {

    GOOGLE(
            "google",
            "https://accounts.google.com/o/oauth2/v2/auth",
            "https://oauth2.googleapis.com/token",
            "https://www.googleapis.com/oauth2/v2/userinfo",
            "openid email profile"),

    GITHUB(
            "github",
            "https://github.com/login/oauth/authorize",
            "https://github.com/login/oauth/access_token",
            "https://api.github.com/user",
            "user:email"),

    MICROSOFT(
            "microsoft",
            "https://login.microsoftonline.com/common/oauth2/v2.0/authorize",
            "https://login.microsoftonline.com/common/oauth2/v2.0/token",
            "https://graph.microsoft.com/v1.0/me",
            "openid email profile"),

    FACEBOOK(
            "facebook",
            "https://www.facebook.com/v18.0/dialog/oauth",
            "https://graph.facebook.com/v18.0/oauth/access_token",
            "https://graph.facebook.com/me?fields=id,name,email",
            "email public_profile"),

    DISCORD(
            "discord",
            "https://discord.com/api/oauth2/authorize",
            "https://discord.com/api/oauth2/token",
            "https://discord.com/api/users/@me",
            "identify email"),

    TWITTER(
            "twitter",
            "https://twitter.com/i/oauth2/authorize",
            "https://api.twitter.com/2/oauth2/token",
            "https://api.twitter.com/2/users/me",
            "tweet.read users.read"),

    LINKEDIN(
            "linkedin",
            "https://www.linkedin.com/oauth/v2/authorization",
            "https://www.linkedin.com/oauth/v2/accessToken",
            "https://api.linkedin.com/v2/people/~",
            "r_liteprofile r_emailaddress"),

    APPLE(
            "apple",
            "https://appleid.apple.com/auth/authorize",
            "https://appleid.apple.com/auth/token",
            null, // Apple doesn't have a separate user info endpoint
            "name email"),

    GITLAB(
            "gitlab",
            "https://gitlab.com/oauth/authorize",
            "https://gitlab.com/oauth/token",
            "https://gitlab.com/api/v4/user",
            "read_user"),

    SLACK(
            "slack",
            "https://slack.com/oauth/v2/authorize",
            "https://slack.com/api/oauth.v2.access",
            "https://slack.com/api/users.identity",
            "identity.basic identity.email");

    private final String providerId;
    private final String authorizationUrl;
    private final String tokenUrl;
    private final String userInfoUrl;
    private final String defaultScopes;

    private OAuth2Provider(
            String providerId,
            String authorizationUrl,
            String tokenUrl,
            String userInfoUrl,
            String defaultScopes) {
        this.providerId = providerId;
        this.authorizationUrl = authorizationUrl;
        this.tokenUrl = tokenUrl;
        this.userInfoUrl = userInfoUrl;
        this.defaultScopes = defaultScopes;
    }

    public String getProviderId() {
        return providerId;
    }

    public String getAuthorizationUrl() {
        return authorizationUrl;
    }

    public String getTokenUrl() {
        return tokenUrl;
    }

    public String getUserInfoUrl() {
        return userInfoUrl;
    }

    public String getDefaultScopes() {
        return defaultScopes;
    }

    /**
     * Get provider by string identifier (case-insensitive)
     */
    public static OAuth2Provider fromString(String provider) {
        for (OAuth2Provider p : OAuth2Provider.values()) {
            if (p.getProviderId().equalsIgnoreCase(provider)) {
                return p;
            }
        }
        throw new IllegalArgumentException("Unknown OAuth2 provider: " + provider);
    }

    /**
     * Check if provider is supported
     */
    public static boolean isSupported(String provider) {
        try {
            fromString(provider);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    /**
     * Generate callback URL path for this provider
     */
    public String getCallbackPath() {
        return "/auth/" + providerId + "/callback";
    }

    /**
     * Generate authorization URL path for this provider
     */
    public String getAuthPath() {
        return "/auth/" + providerId + "/authorize";
    }

    /**
     * Extract email from user info based on provider-specific response format
     */
    public String extractEmail(Map<String, Object> userInfo) {
        switch (this) {
            case GOOGLE:
            case GITHUB:
            case MICROSOFT:
            case FACEBOOK:
            case DISCORD:
            case GITLAB:
                return (String) userInfo.get("email");
            case TWITTER:
                return (String) userInfo.get("email"); // Twitter v2 API
            case LINKEDIN:
                // LinkedIn has a different structure for email
                return extractLinkedInEmail(userInfo);
            case APPLE:
                return (String) userInfo.get("email");
            case SLACK:
                Map<String, Object> user = (Map<String, Object>) userInfo.get("user");
                return user != null ? (String) user.get("email") : null;
            default:
                return (String) userInfo.get("email");
        }
    }

    /**
     * Extract name from user info based on provider-specific response format
     */
    public String extractName(Map<String, Object> userInfo) {
        switch (this) {
            case GOOGLE:
            case FACEBOOK:
            case MICROSOFT:
            case APPLE:
                return (String) userInfo.get("name");
            case GITHUB:
            case GITLAB:
                String name = (String) userInfo.get("name");
                return name != null ? name : (String) userInfo.get("login"); // fallback to username
            case DISCORD:
                return (String) userInfo.get("username");
            case TWITTER:
                return (String) userInfo.get("name");
            case LINKEDIN:
                return extractLinkedInName(userInfo);
            case SLACK:
                Map<String, Object> user = (Map<String, Object>) userInfo.get("user");
                return user != null ? (String) user.get("name") : null;
            default:
                return (String) userInfo.get("name");
        }
    }

    /**
     * Extract profile picture URL from user info based on provider-specific
     * response format
     */
    public String extractPicture(Map<String, Object> userInfo) {
        switch (this) {
            case GOOGLE:
                return (String) userInfo.get("picture");
            case GITHUB:
                return (String) userInfo.get("avatar_url");
            case MICROSOFT:
                return (String) userInfo.get("picture");
            case FACEBOOK:
                Map<String, Object> picture = (Map<String, Object>) userInfo.get("picture");
                if (picture != null) {
                    Map<String, Object> data = (Map<String, Object>) picture.get("data");
                    return data != null ? (String) data.get("url") : null;
                }
                return null;
            case DISCORD:
                String avatar = (String) userInfo.get("avatar");
                String id = (String) userInfo.get("id");
                return avatar != null ? "https://cdn.discordapp.com/avatars/" + id + "/" + avatar + ".png" : null;
            case TWITTER:
                return (String) userInfo.get("profile_image_url");
            case LINKEDIN:
                return extractLinkedInPicture(userInfo);
            case APPLE:
                return null; // Apple doesn't provide profile pictures
            case GITLAB:
                return (String) userInfo.get("avatar_url");
            case SLACK:
                Map<String, Object> user = (Map<String, Object>) userInfo.get("user");
                return user != null ? (String) user.get("image_192") : null;
            default:
                return (String) userInfo.get("picture");
        }
    }

    private String extractLinkedInEmail(Map<String, Object> userInfo) {
        // LinkedIn email is usually in a separate API call, but if present:
        return (String) userInfo.get("emailAddress");
    }

    private String extractLinkedInName(Map<String, Object> userInfo) {
        Map<String, Object> localizedFirstName = (Map<String, Object>) userInfo.get("localizedFirstName");
        Map<String, Object> localizedLastName = (Map<String, Object>) userInfo.get("localizedLastName");

        String firstName = localizedFirstName != null ? (String) localizedFirstName.get("en_US") : "";
        String lastName = localizedLastName != null ? (String) localizedLastName.get("en_US") : "";

        return (firstName + " " + lastName).trim();
    }

    private String extractLinkedInPicture(Map<String, Object> userInfo) {
        Map<String, Object> profilePicture = (Map<String, Object>) userInfo.get("profilePicture");
        if (profilePicture != null) {
            Map<String, Object> displayImage = (Map<String, Object>) profilePicture.get("displayImage");
            if (displayImage != null) {
                Object[] elements = (Object[]) displayImage.get("elements");
                if (elements != null && elements.length > 0) {
                    Map<String, Object> element = (Map<String, Object>) elements[0];
                    Map<String, Object> identifiers = (Map<String, Object>) element.get("identifiers");
                    if (identifiers != null) {
                        return (String) identifiers.get("identifier");
                    }
                }
            }
        }
        return null;
    }
}