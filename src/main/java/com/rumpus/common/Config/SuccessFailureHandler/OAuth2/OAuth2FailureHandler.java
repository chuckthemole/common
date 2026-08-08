package com.rumpus.common.Config.SuccessFailureHandler.OAuth2;

import java.io.IOException;

import org.springframework.security.core.AuthenticationException;

import com.rumpus.common.Config.SuccessFailureHandler.AbstractFailureHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class OAuth2FailureHandler extends AbstractFailureHandler {

    private final String baseRedirectUrl;

    public OAuth2FailureHandler(String baseRedirectUrl) {
        this.baseRedirectUrl = baseRedirectUrl;
    }

    @Override
    public void onFailure(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException exception) throws IOException, ServletException {

        final String baseRedirectUrl = this.baseRedirectUrl;
        String redirectUrl = baseRedirectUrl + "?error=" +
                exception.getLocalizedMessage();
        response.sendRedirect(redirectUrl);
    }
}
