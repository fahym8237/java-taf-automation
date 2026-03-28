package com.fahym.tas.observability.api;

import java.util.Map;

public final class ApiExchange {

    private final String method;
    private final String uri;
    private final Map<String, String> requestHeaders;
    private final String requestBody;

    private final int statusCode;
    private final Map<String, String> responseHeaders;
    private final String responseBody;

    public ApiExchange(
            String method,
            String uri,
            Map<String, String> requestHeaders,
            String requestBody,
            int statusCode,
            Map<String, String> responseHeaders,
            String responseBody
    ) {
        this.method = method;
        this.uri = uri;
        this.requestHeaders = requestHeaders;
        this.requestBody = requestBody;

        this.statusCode = statusCode;
        this.responseHeaders = responseHeaders;
        this.responseBody = responseBody;
    }

    public String method() { return method; }
    public String uri() { return uri; }
    public Map<String, String> requestHeaders() { return requestHeaders; }
    public String requestBody() { return requestBody; }

    public int statusCode() { return statusCode; }
    public Map<String, String> responseHeaders() { return responseHeaders; }
    public String responseBody() { return responseBody; }
}