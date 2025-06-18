package com.rumpus.common.Config.SuccessFailureHandler.OAuth2;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;

import com.rumpus.common.Config.SuccessFailureHandler.AbstractSuccessHandler;
import com.rumpus.common.Service.JwtService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

abstract public class AbstractOAuth2SuccesHandler extends AbstractSuccessHandler {

    private JwtService jwtService;

    abstract public String getBaseRedirectUrl();

    public AbstractOAuth2SuccesHandler(JwtService jwtService) {
        this.jwtService = jwtService;
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
        final String baseRedirectUrl = this.getBaseRedirectUrl();
        String redirectUrl = baseRedirectUrl + token;
        response.sendRedirect(redirectUrl);
    }
}
