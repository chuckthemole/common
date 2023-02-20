package com.rumpus.common.Session;

import org.springframework.http.HttpMethod;
import org.springframework.web.reactive.function.client.WebClient;

import ch.qos.logback.core.net.server.Client;

public class CommonWebClient implements WebClient {

    // TODO this needs a lot of work...
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
    @Override
    public RequestHeadersUriSpec<?> get() {
        // TODO Auto-generated method stub
        return null;
    }
    @Override
    public RequestHeadersUriSpec<?> head() {
        // TODO Auto-generated method stub
        return null;
    }
    @Override
    public RequestBodyUriSpec post() {
        // TODO Auto-generated method stub
        return null;
    }
    @Override
    public RequestBodyUriSpec put() {
        // TODO Auto-generated method stub
        return null;
    }
    @Override
    public RequestBodyUriSpec patch() {
        // TODO Auto-generated method stub
        return null;
    }
    @Override
    public RequestHeadersUriSpec<?> delete() {
        // TODO Auto-generated method stub
        return null;
    }
    @Override
    public RequestHeadersUriSpec<?> options() {
        // TODO Auto-generated method stub
        return null;
    }
    @Override
    public RequestBodyUriSpec method(HttpMethod method) {
        // TODO Auto-generated method stub
        return null;
    }
    @Override
    public Builder mutate() {
        // TODO Auto-generated method stub
        return null;
    }
}
