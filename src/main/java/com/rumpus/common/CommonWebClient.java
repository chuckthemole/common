package com.rumpus.common;

import org.springframework.web.reactive.function.client.WebClient;

import ch.qos.logback.core.net.server.Client;

public class CommonWebClient {

    protected WebClient client;

    public CommonWebClient() {
        client = WebClient.create();
    }
    public CommonWebClient(String uri) {
        client = WebClient.create(uri);
    }

    public static CommonWebClient create(String uri) {
        return new CommonWebClient(uri);
    }

    public WebClient getClient() {
        return this.client;
    }

    public void setClient(WebClient client) {
        this.client = client;
    }
}
