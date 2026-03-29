package com.fahym.tas.core.api.model;

import io.restassured.http.Headers;

/**
 * Represents the result of an executed API call within the TAF framework.
 *
 * This class wraps the essential information returned from an HTTP response,
 * including the status code, response body, and response headers.
 *
 * ApiResponse is created by the RestAssuredClient after executing an API request
 * using RestAssured. It serves as a framework-level response model that can be
 * safely consumed by higher layers such as domain services, step definitions,
 * or assertion utilities.
 *
 * By exposing a simplified response model, this class hides the underlying
 * RestAssured response object and keeps the framework decoupled from the
 * specific HTTP client implementation.
 *
 * Typical flow:
 *   RestAssuredClient -> ApiResponse -> Domain / Assertion Layer
 */
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