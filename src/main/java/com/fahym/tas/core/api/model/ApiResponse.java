package com.fahym.tas.core.api.model;

import io.restassured.http.Headers;

public final class ApiResponse {

    private final int statusCode;
    private final String body;
    private final Headers headers;

    public ApiResponse(int statusCode, String body, Headers headers) {
        this.statusCode = statusCode;
        this.body = body;
        this.headers = headers;
    }

    public int statusCode() { return statusCode; }
    public String body() { return body; }
    public Headers headers() { return headers; }

    public boolean is2xx() {
        return statusCode >= 200 && statusCode < 300;
    }
}