package com.rumpus.common.Service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.test.util.ReflectionTestUtils;

import com.rumpus.common.Auth.OAuth2Provider;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;

@ExtendWith(MockitoExtension.class)
class JwtServiceTest {

    @InjectMocks
    private JwtService jwtService;

    @Mock
    private OAuth2User mockOAuth2User;

    @Mock
    private OAuth2Provider mockOAuth2Provider;

    private final String TEST_JWT_SECRET = "mySecretKeyForTestingThatIsLongEnoughForHMACSHA512Algorithm";
    private final long TEST_JWT_EXPIRATION = 86400000L; // 24 hours in milliseconds

    // Test data
    private final String TEST_EMAIL = "test@example.com";
    private final String TEST_NAME = "Test User";
    private final String TEST_PICTURE = "https://example.com/profile.jpg";
    private final String TEST_PROVIDER = "google";

    @BeforeEach
    void setUp() {
        // Set up test properties using reflection
        ReflectionTestUtils.setField(jwtService, "jwtSecret", TEST_JWT_SECRET);
        ReflectionTestUtils.setField(jwtService, "jwtExpiration", TEST_JWT_EXPIRATION);
    }

    @Test
    void testGenerateTokenWithOAuth2User() {
        // Given
        when(mockOAuth2User.getAttribute("email")).thenReturn(TEST_EMAIL);
        when(mockOAuth2User.getAttribute("name")).thenReturn(TEST_NAME);
        when(mockOAuth2User.getAttribute("picture")).thenReturn(TEST_PICTURE);

        // When
        String token = jwtService.generateToken(mockOAuth2User);

        // Then
        assertNotNull(token);
        assertFalse(token.isEmpty());
        assertTrue(token.split("\\.").length == 3); // JWT has 3 parts separated by dots

        // Verify token contents
        Claims claims = jwtService.extractClaims(token);
        assertEquals(TEST_EMAIL, claims.getSubject());
        assertEquals(TEST_NAME, claims.get("name"));
        assertEquals(TEST_EMAIL, claims.get("email"));
        assertEquals(TEST_PICTURE, claims.get("picture"));
        assertNotNull(claims.getIssuedAt());
        assertNotNull(claims.getExpiration());
    }

    @Test
    void testGenerateTokenWithOAuth2Provider() {
        // Given
        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("email", TEST_EMAIL);
        userInfo.put("name", TEST_NAME);
        userInfo.put("picture", TEST_PICTURE);

        when(mockOAuth2Provider.extractEmail(userInfo)).thenReturn(TEST_EMAIL);
        when(mockOAuth2Provider.extractName(userInfo)).thenReturn(TEST_NAME);
        when(mockOAuth2Provider.extractPicture(userInfo)).thenReturn(TEST_PICTURE);
        when(mockOAuth2Provider.getProviderId()).thenReturn(TEST_PROVIDER);

        // When
        String token = jwtService.generateToken(mockOAuth2Provider, userInfo);

        // Then
        assertNotNull(token);
        assertFalse(token.isEmpty());
        assertTrue(token.split("\\.").length == 3);

        // Verify token contents
        Claims claims = jwtService.extractClaims(token);
        assertEquals(TEST_EMAIL, claims.getSubject());
        assertEquals(TEST_NAME, claims.get("name"));
        assertEquals(TEST_EMAIL, claims.get("email"));
        assertEquals(TEST_PICTURE, claims.get("picture"));
        assertEquals(TEST_PROVIDER, claims.get("provider"));
        assertNotNull(claims.getIssuedAt());
        assertNotNull(claims.getExpiration());
    }

    @Test
    void testGenerateTokenWithStringParameters() {
        // When
        String token = jwtService.generateToken(TEST_EMAIL, TEST_NAME, TEST_PICTURE, TEST_PROVIDER);

        // Then
        assertNotNull(token);
        assertFalse(token.isEmpty());
        assertTrue(token.split("\\.").length == 3);

        // Verify token contents
        Claims claims = jwtService.extractClaims(token);
        assertEquals(TEST_EMAIL, claims.getSubject());
        assertEquals(TEST_NAME, claims.get("name"));
        assertEquals(TEST_EMAIL, claims.get("email"));
        assertEquals(TEST_PICTURE, claims.get("picture"));
        assertEquals(TEST_PROVIDER, claims.get("provider"));
        assertNotNull(claims.getIssuedAt());
        assertNotNull(claims.getExpiration());
    }

    @Test
    void testExtractClaims() {
        // Given
        String token = jwtService.generateToken(TEST_EMAIL, TEST_NAME, TEST_PICTURE, TEST_PROVIDER);

        // When
        Claims claims = jwtService.extractClaims(token);

        // Then
        assertNotNull(claims);
        assertEquals(TEST_EMAIL, claims.getSubject());
        assertEquals(TEST_NAME, claims.get("name"));
        assertEquals(TEST_EMAIL, claims.get("email"));
        assertEquals(TEST_PICTURE, claims.get("picture"));
        assertEquals(TEST_PROVIDER, claims.get("provider"));
    }

    @Test
    void testExtractClaimsWithInvalidToken() {
        // Given
        String invalidToken = "invalid.jwt.token";

        // When & Then
        assertThrows(JwtException.class, () -> {
            jwtService.extractClaims(invalidToken);
        });
    }

    @Test
    void testIsTokenValid() {
        // Given
        String validToken = jwtService.generateToken(TEST_EMAIL, TEST_NAME, TEST_PICTURE, TEST_PROVIDER);

        // When & Then
        assertTrue(jwtService.isTokenValid(validToken));
    }

    @Test
    void testIsTokenValidWithInvalidToken() {
        // Given
        String invalidToken = "invalid.jwt.token";

        // When & Then
        assertFalse(jwtService.isTokenValid(invalidToken));
    }

    @Test
    void testIsTokenValidWithNullToken() {
        // When & Then
        assertFalse(jwtService.isTokenValid(null));
    }

    @Test
    void testIsTokenValidWithEmptyToken() {
        // When & Then
        assertFalse(jwtService.isTokenValid(""));
    }

    @Test
    void testExtractEmail() {
        // Given
        String token = jwtService.generateToken(TEST_EMAIL, TEST_NAME, TEST_PICTURE, TEST_PROVIDER);

        // When
        String extractedEmail = jwtService.extractEmail(token);

        // Then
        assertEquals(TEST_EMAIL, extractedEmail);
    }

    @Test
    void testExtractName() {
        // Given
        String token = jwtService.generateToken(TEST_EMAIL, TEST_NAME, TEST_PICTURE, TEST_PROVIDER);

        // When
        String extractedName = jwtService.extractName(token);

        // Then
        assertEquals(TEST_NAME, extractedName);
    }

    @Test
    void testExtractProvider() {
        // Given
        String token = jwtService.generateToken(TEST_EMAIL, TEST_NAME, TEST_PICTURE, TEST_PROVIDER);

        // When
        String extractedProvider = jwtService.extractProvider(token);

        // Then
        assertEquals(TEST_PROVIDER, extractedProvider);
    }

    @Test
    void testExtractPicture() {
        // Given
        String token = jwtService.generateToken(TEST_EMAIL, TEST_NAME, TEST_PICTURE, TEST_PROVIDER);

        // When
        String extractedPicture = jwtService.extractPicture(token);

        // Then
        assertEquals(TEST_PICTURE, extractedPicture);
    }

    @Test
    void testTokenExpirationIsSet() {
        // Given
        String token = jwtService.generateToken(TEST_EMAIL, TEST_NAME, TEST_PICTURE, TEST_PROVIDER);

        // When
        Claims claims = jwtService.extractClaims(token);

        // Then
        assertNotNull(claims.getExpiration());
        assertTrue(claims.getExpiration().getTime() > System.currentTimeMillis());

        // Verify expiration is approximately 24 hours from now (within 1 minute
        // tolerance)
        long expectedExpiration = System.currentTimeMillis() + TEST_JWT_EXPIRATION;
        long actualExpiration = claims.getExpiration().getTime();
        long tolerance = 60000; // 1 minute in milliseconds

        assertTrue(Math.abs(expectedExpiration - actualExpiration) < tolerance);
    }

    @Test
    void testTokenIssuedAtIsSet() {
        // Given
        long beforeGeneration = System.currentTimeMillis();
        String token = jwtService.generateToken(TEST_EMAIL, TEST_NAME, TEST_PICTURE, TEST_PROVIDER);
        long afterGeneration = System.currentTimeMillis();

        // When
        Claims claims = jwtService.extractClaims(token);

        // Then
        assertNotNull(claims.getIssuedAt());
        long issuedAt = claims.getIssuedAt().getTime();

        // Allow for 1-2 seconds of variance to account for timing and precision issues
        assertTrue(issuedAt >= beforeGeneration - 2000,
                "IssuedAt should be after or close to beforeGeneration");
        assertTrue(issuedAt <= afterGeneration + 2000,
                "IssuedAt should be before or close to afterGeneration");
    }

    @Test
    void testExtractFromInvalidTokenThrowsException() {
        // Given
        String invalidToken = "invalid.jwt.token";

        // When & Then
        assertThrows(JwtException.class, () -> jwtService.extractEmail(invalidToken));
        assertThrows(JwtException.class, () -> jwtService.extractName(invalidToken));
        assertThrows(JwtException.class, () -> jwtService.extractProvider(invalidToken));
        assertThrows(JwtException.class, () -> jwtService.extractPicture(invalidToken));
    }

    @Test
    void testTokensGeneratedWithDifferentMethodsAreConsistent() {
        // Given
        when(mockOAuth2User.getAttribute("email")).thenReturn(TEST_EMAIL);
        when(mockOAuth2User.getAttribute("name")).thenReturn(TEST_NAME);
        when(mockOAuth2User.getAttribute("picture")).thenReturn(TEST_PICTURE);

        Map<String, Object> userInfo = new HashMap<>();
        when(mockOAuth2Provider.extractEmail(userInfo)).thenReturn(TEST_EMAIL);
        when(mockOAuth2Provider.extractName(userInfo)).thenReturn(TEST_NAME);
        when(mockOAuth2Provider.extractPicture(userInfo)).thenReturn(TEST_PICTURE);
        when(mockOAuth2Provider.getProviderId()).thenReturn(TEST_PROVIDER);

        // When
        String token1 = jwtService.generateToken(mockOAuth2User);
        String token2 = jwtService.generateToken(mockOAuth2Provider, userInfo);
        String token3 = jwtService.generateToken(TEST_EMAIL, TEST_NAME, TEST_PICTURE, TEST_PROVIDER);

        // Then - All tokens should contain the same user information
        Claims claims1 = jwtService.extractClaims(token1);
        Claims claims2 = jwtService.extractClaims(token2);
        Claims claims3 = jwtService.extractClaims(token3);

        assertEquals(claims1.getSubject(), claims2.getSubject());
        assertEquals(claims2.getSubject(), claims3.getSubject());
        assertEquals(claims1.get("name"), claims2.get("name"));
        assertEquals(claims2.get("name"), claims3.get("name"));
        assertEquals(claims1.get("email"), claims2.get("email"));
        assertEquals(claims2.get("email"), claims3.get("email"));
        assertEquals(claims1.get("picture"), claims2.get("picture"));
        assertEquals(claims2.get("picture"), claims3.get("picture"));
    }
}