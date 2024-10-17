package com.rumpus.common.Security.Authentication;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Logger;

/**
 * TODO: Work and fill out this class more.
 */
public abstract class AbstractAuthenticationEntryPoint implements AuthenticationEntryPoint {

    protected static final Logger LOG = Logger.getLogger(AbstractAuthenticationEntryPoint.class.getName());

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        // Delegates to handle specific exception types
        if (authException != null) {
            handleAuthException(request, response, authException);
        } else {
            handleGenericException(request, response);
        }
    }

    /**
     * Handle specific authentication exceptions (e.g., unauthorized access).
     */
    protected abstract void handleAuthException(HttpServletRequest request, HttpServletResponse response,
                                                AuthenticationException authException) throws IOException;

    /**
     * Handle generic authentication issues.
     */
    protected void handleGenericException(HttpServletRequest request, HttpServletResponse response) throws IOException {
        LOG.warning("Unauthorized access attempt detected: " + request.getRequestURI());
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized: Access is denied.");
    }
}

