package com.rumpus.common.Config.SuccessFailureHandler.OAuth2;

import java.io.IOException;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;

import com.rumpus.common.Config.SuccessFailureHandler.AbstractSuccessHandler;
import com.rumpus.common.Service.JwtService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@EnableConfigurationProperties(OAuth2HandlerProperties.class)
public class OAuth2SuccessHandler extends AbstractSuccessHandler {

    private JwtService jwtService;

    private final String baseRedirectUrl;

    public OAuth2SuccessHandler(
            JwtService jwtService,
            OAuth2HandlerProperties oAuth2Properties) {
        this.jwtService = jwtService;
        this.baseRedirectUrl = oAuth2Properties.getSuccessRedirectUrl();
    }

    @Override
    public void onSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication)
            throws IOException, ServletException {

        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();

        // Generate JWT token
        String token = this.jwtService.generateToken(oAuth2User);

        // Redirect to React app with token
        final String baseRedirectUrl = this.baseRedirectUrl;
        String redirectUrl = baseRedirectUrl + token;
        response.sendRedirect(redirectUrl);
    }
}
