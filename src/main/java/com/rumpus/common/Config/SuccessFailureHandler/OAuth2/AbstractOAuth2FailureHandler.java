package com.rumpus.common.Config.SuccessFailureHandler.OAuth2;

import java.io.IOException;

import org.springframework.security.core.AuthenticationException;

import com.rumpus.common.Config.SuccessFailureHandler.AbstractFailureHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

abstract public class AbstractOAuth2FailureHandler extends AbstractFailureHandler {

    abstract public String getBaseRedirectUrl();

    @Override
    public void onFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception)
            throws IOException, ServletException {
        final String baseRedirectUrl = this.getBaseRedirectUrl();
        String redirectUrl = baseRedirectUrl + "?error=" +
                exception.getLocalizedMessage();
        response.sendRedirect(redirectUrl);
    }
}
