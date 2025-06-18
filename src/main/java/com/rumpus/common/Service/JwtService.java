package com.rumpus.common.Service;

import org.springframework.stereotype.Service;

import com.rumpus.common.Auth.OAuth2Provider;
import com.rumpus.common.Config.AbstractCommonConfig;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.core.user.OAuth2User;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;

/**
 * Service class responsible for JWT (JSON Web Token) operations including token
 * generation,
 * validation, and claims extraction. Supports multiple OAuth2 providers and
 * authentication
 * methods for secure user session management.
 */
@Service
public class JwtService {

    @Value(AbstractCommonConfig.JWT_SECRET_VALUE_ANNOTATION)
    private String jwtSecret;

    @Value(AbstractCommonConfig.JWT_SECRET_EXPIRATION_ANNOTATION)
    private long jwtExpiration;

    /**
     * Creates a cryptographic signing key from the JWT secret for token signing and
     * verification.
     * Uses HMAC-SHA algorithm for secure token generation.
     * 
     * @return SecretKey object for JWT operations
     */
    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes());
    }

    /**
     * Generates a JWT token for Spring Security OAuth2 authenticated users.
     * Extracts user information from OAuth2User object and creates a signed token
     * with standard claims (email, name, picture).
     * 
     * @param oAuth2User Spring Security OAuth2User containing user attributes
     * @return Signed JWT token as a string
     */
    public String generateToken(OAuth2User oAuth2User) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpiration);

        return Jwts.builder()
                .subject(oAuth2User.getAttribute("email")) // setSubject() → subject()
                .claim("name", oAuth2User.getAttribute("name"))
                .claim("email", oAuth2User.getAttribute("email"))
                .claim("picture", oAuth2User.getAttribute("picture"))
                .issuedAt(now) // setIssuedAt() → issuedAt()
                .expiration(expiryDate) // setExpiration() → expiration()
                .signWith(getSigningKey()) // Remove SignatureAlgorithm parameter
                .compact();
    }

    /**
     * Generates a JWT token using a custom OAuth2Provider and raw user information.
     * Uses the provider's extraction methods to normalize user data from different
     * OAuth2 services (Google, Facebook, etc.) into a standardized token format.
     * 
     * @param provider OAuth2Provider instance with extraction logic for specific
     *                 OAuth2 service
     * @param userInfo Raw user information map from OAuth2 provider
     * @return Signed JWT token as a string
     */
    public String generateToken(OAuth2Provider provider, Map<String, Object> userInfo) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpiration);

        String email = provider.extractEmail(userInfo);
        String name = provider.extractName(userInfo);
        String picture = provider.extractPicture(userInfo);

        return Jwts.builder()
                .subject(email) // setSubject() → subject()
                .claim("name", name)
                .claim("email", email)
                .claim("picture", picture)
                .claim("provider", provider.getProviderId())
                .issuedAt(now) // setIssuedAt() → issuedAt()
                .expiration(expiryDate) // setExpiration() → expiration()
                .signWith(getSigningKey()) // Remove SignatureAlgorithm parameter
                .compact();
    }

    /**
     * Generates a JWT token from individual user attributes passed as parameters.
     * Useful for creating tokens when user information is already extracted and
     * available as separate variables rather than objects or maps.
     * 
     * @param email    User's email address (used as token subject)
     * @param name     User's display name
     * @param picture  URL to user's profile picture
     * @param provider Name/ID of the OAuth2 provider used for authentication
     * @return Signed JWT token as a string
     */
    public String generateToken(String email, String name, String picture, String provider) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpiration);

        return Jwts.builder()
                .subject(email) // setSubject() → subject()
                .claim("name", name) // claim() stays the same
                .claim("email", email)
                .claim("picture", picture)
                .claim("provider", provider)
                .issuedAt(now) // setIssuedAt() → issuedAt()
                .expiration(expiryDate) // setExpiration() → expiration()
                .signWith(getSigningKey()) // Remove SignatureAlgorithm parameter
                .compact();
    }

    /**
     * Extracts and returns all claims from a JWT token after verification.
     * Verifies the token signature and expiration before returning the payload.
     * 
     * @param token JWT token string to parse
     * @return Claims object containing all token data
     * @throws io.jsonwebtoken.JwtException if token is invalid, expired, or
     *                                      malformed
     */
    public Claims extractClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    /**
     * Validates a JWT token by attempting to parse and verify it.
     * Returns true if token is valid (properly signed and not expired),
     * false if token is invalid, expired, or malformed.
     * 
     * @param token JWT token string to validate
     * @return true if token is valid, false otherwise
     */
    public boolean isTokenValid(String token) {
        try {
            extractClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Extracts the email address from a JWT token.
     * Email is stored as the token's subject claim.
     * 
     * @param token JWT token string
     * @return User's email address
     * @throws io.jsonwebtoken.JwtException if token is invalid
     */
    public String extractEmail(String token) {
        return extractClaims(token).getSubject();
    }

    /**
     * Extracts the user's display name from a JWT token.
     * 
     * @param token JWT token string
     * @return User's display name
     * @throws io.jsonwebtoken.JwtException if token is invalid
     */
    public String extractName(String token) {
        return extractClaims(token).get("name", String.class);
    }

    /**
     * Extracts the OAuth2 provider identifier from a JWT token.
     * Indicates which OAuth2 service was used for authentication (e.g., "google",
     * "facebook").
     * 
     * @param token JWT token string
     * @return OAuth2 provider identifier
     * @throws io.jsonwebtoken.JwtException if token is invalid
     */
    public String extractProvider(String token) {
        return extractClaims(token).get("provider", String.class);
    }

    /**
     * Extracts the user's profile picture URL from a JWT token.
     * 
     * @param token JWT token string
     * @return URL to user's profile picture
     * @throws io.jsonwebtoken.JwtException if token is invalid
     */
    public String extractPicture(String token) {
        return extractClaims(token).get("picture", String.class);
    }
}