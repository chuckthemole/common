package com.rumpus.common.Config;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;

/**
 * 
 */
public class AbstractRedirectToIndexFilter implements Filter {

    private List<String> startOfURIsNotFiltered;

    public AbstractRedirectToIndexFilter(List<String> startOfURIsNotFiltered) {
        this.startOfURIsNotFiltered = startOfURIsNotFiltered;
    }

    @Override
    public void doFilter(
            ServletRequest request,
            ServletResponse response,
            FilterChain chain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        String requestURI = req.getRequestURI();

        for (String startOfURINotFiltered : startOfURIsNotFiltered) {
            if (requestURI.startsWith(startOfURINotFiltered)) {
                chain.doFilter(request, response);
                return;
            }
        }

        // all requests not api or static will be forwarded to index page.
        request.getRequestDispatcher("/").forward(request, response);
    }

}
