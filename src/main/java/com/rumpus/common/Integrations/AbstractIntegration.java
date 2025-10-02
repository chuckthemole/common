package com.rumpus.common.Integrations;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * Abstract base class for making HTTP requests to external integrations.
 * Provides common methods for GET, POST, PUT, and DELETE.
 * 
 * Subclasses can extend this to handle authentication, logging,
 * retries, or response parsing in a consistent way.
 */
public abstract class AbstractIntegration {

    // Default timeouts (in ms) — can be overridden by subclasses
    private static final int CONNECT_TIMEOUT = 10_000; // 10 seconds
    private static final int READ_TIMEOUT = 15_000; // 15 seconds

    /**
     * Perform an HTTP GET request.
     *
     * @param url     The target URL
     * @param headers Optional request headers
     * @return Response body as a String
     * @throws IOException if the request fails
     */
    protected String get(String url, Map<String, String> headers) throws IOException {
        HttpURLConnection conn = createConnection(url, "GET", headers);
        return readResponse(conn);
    }

    /**
     * Perform an HTTP POST request.
     *
     * @param url     The target URL
     * @param headers Optional request headers
     * @param body    Request body (JSON, XML, etc.)
     * @return Response body as a String
     * @throws IOException if the request fails
     */
    protected String post(String url, Map<String, String> headers, String body) throws IOException {
        HttpURLConnection conn = createConnection(url, "POST", headers);
        conn.setDoOutput(true);

        if (body != null && !body.isEmpty()) {
            try (OutputStream os = conn.getOutputStream()) {
                os.write(body.getBytes(StandardCharsets.UTF_8));
            }
        }

        return readResponse(conn);
    }

    /**
     * Perform an HTTP PUT request.
     */
    protected String put(String url, Map<String, String> headers, String body) throws IOException {
        HttpURLConnection conn = createConnection(url, "PUT", headers);
        conn.setDoOutput(true);

        if (body != null && !body.isEmpty()) {
            try (OutputStream os = conn.getOutputStream()) {
                os.write(body.getBytes(StandardCharsets.UTF_8));
            }
        }

        return readResponse(conn);
    }

    /**
     * Perform an HTTP DELETE request.
     */
    protected String delete(String url, Map<String, String> headers) throws IOException {
        HttpURLConnection conn = createConnection(url, "DELETE", headers);
        return readResponse(conn);
    }

    /**
     * Creates and configures an HttpURLConnection.
     */
    private HttpURLConnection createConnection(String url, String method, Map<String, String> headers)
            throws IOException {
        HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
        conn.setRequestMethod(method);
        conn.setConnectTimeout(CONNECT_TIMEOUT);
        conn.setReadTimeout(READ_TIMEOUT);
        setHeaders(conn, headers);
        return conn;
    }

    /**
     * Sets headers on the connection.
     */
    private void setHeaders(HttpURLConnection conn, Map<String, String> headers) {
        if (headers != null) {
            headers.forEach(conn::setRequestProperty);
        }
    }

    /**
     * Reads the response body from the connection.
     *
     * @return Response body as a String
     * @throws IOException if reading fails
     */
    private String readResponse(HttpURLConnection conn) throws IOException {
        int status = conn.getResponseCode();
        InputStream is = (status < HttpURLConnection.HTTP_BAD_REQUEST)
                ? conn.getInputStream()
                : conn.getErrorStream();

        if (is == null) {
            return ""; // Some servers return no body for certain statuses
        }

        try (BufferedReader in = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = in.readLine()) != null) {
                response.append(line).append("\n");
            }
            return response.toString().trim();
        }
    }

    /**
     * Hook method for subclasses to log requests and responses if needed.
     * (No-op by default)
     */
    protected void logRequest(String method, String url, Map<String, String> headers, String body) {
        // Subclasses may override
    }

    protected void logResponse(int statusCode, String response) {
        // Subclasses may override
    }
}
