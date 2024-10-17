package com.rumpus.common.User;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import com.rumpus.common.ICommon;
import com.rumpus.common.Builder.LogBuilder;
import com.rumpus.common.Logger.AbstractCommonLogger.LogLevel;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class AuthenticationHandler implements AuthenticationSuccessHandler, AuthenticationFailureHandler, LogoutSuccessHandler {

    ActiveUserStore activeUserStore; // TODO: not using this rn. should look into more when using onAuthSuccess
    
    public AuthenticationHandler(ActiveUserStore activeUserStore) {
        this.activeUserStore = activeUserStore;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        LOG_THIS("onAuthenticationSuccess");
        HttpSession session = request.getSession(false);
        if (session != null) {
            LoggedUser user = new LoggedUser(authentication.getName(), activeUserStore);
            session.setAttribute("user", user);
        }
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        LOG_THIS(LogLevel.ERROR, "WebSecurityConfig::onAuthenticationFailure\\n", exception.toString());
        LogBuilder.logBuilderFromStackTraceElementArray(exception.getMessage(), exception.getStackTrace());

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write("Authentication failed: " + exception.getMessage());
    }

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        LOG_THIS("onLogoutSuccess");
        HttpSession session = request.getSession();
        if (session != null){
            session.removeAttribute("user");
        }
    }

    /**
     * Logs messages at default log level (INFO) for this class.
     *
     * @param args The message to log.
     */
    private static void LOG_THIS(String... args) {
        ICommon.LOG(AuthenticationHandler.class, args);
    }

    /**
     * Logs messages at the specified log level for this class.
     *
     * @param level The log level to use.
     * @param args  The message to log.
     */
    private static void LOG_THIS(LogLevel level, String... args) {
        ICommon.LOG(AuthenticationHandler.class, level, args);
    }
}
