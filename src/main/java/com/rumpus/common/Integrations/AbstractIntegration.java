package com.rumpus.common.Integrations;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

public abstract class AbstractIntegration {

    protected String get(String url, Map<String, String> headers) throws IOException {
        HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
        conn.setRequestMethod("GET");
        setHeaders(conn, headers);
        return readResponse(conn);
    }

    protected String post(String url, Map<String, String> headers, String body) throws IOException {
        HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
        conn.setRequestMethod("POST");
        conn.setDoOutput(true);
        setHeaders(conn, headers);

        if (body != null) {
            try (OutputStream os = conn.getOutputStream()) {
                os.write(body.getBytes());
            }
        }

        return readResponse(conn);
    }

    private void setHeaders(HttpURLConnection conn, Map<String, String> headers) {
        if (headers != null) {
            headers.forEach(conn::setRequestProperty);
        }
    }

    private String readResponse(HttpURLConnection conn) throws IOException {
        int status = conn.getResponseCode();
        InputStream is = (status < HttpURLConnection.HTTP_BAD_REQUEST) ? conn.getInputStream() : conn.getErrorStream();

        try (BufferedReader in = new BufferedReader(new InputStreamReader(is))) {
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = in.readLine()) != null) {
                response.append(line);
            }
            return response.toString();
        }
    }
}
