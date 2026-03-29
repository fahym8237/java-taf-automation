package com.fahym.tas.core.api.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents an immutable API request model used by the TAF framework.
 *
 * This class encapsulates all the information required to perform an HTTP request,
 * including the endpoint path, headers, query parameters, and optional request body.
 * It uses the Builder pattern to construct requests in a flexible and safe way,
 * ensuring the request object remains immutable after creation.
 *
 * ApiRequest is consumed by the RestAssuredClient, which converts this framework-level
 * request representation into an executable RestAssured request.
 *
 * Typical flow:
 *   Test / Domain Layer -> ApiRequest -> RestAssuredClient -> HTTP call
 *
 * This abstraction allows the framework to remain decoupled from the underlying
 * HTTP client implementation (RestAssured).
 */

public final class ApiRequest {

    private final String path;
    private final Map<String, String> headers;
    private final Map<String, Object> queryParams;
    private final Object body;

    private ApiRequest(String path, Map<String, String> headers, Map<String, Object> queryParams, Object body) {
        this.path = path;
        this.headers = headers;
        this.queryParams = queryParams;
        this.body = body;
    }

    public String path() { return path; }
    public Map<String, String> headers() { return headers; }
    public Map<String, Object> queryParams() { return queryParams; }
    public Object body() { return body; }

    public static Builder builder(String path) {
        return new Builder(path);
    }

    public static final class Builder {
        private final String path;
        private final Map<String, String> headers = new HashMap<>();
        private final Map<String, Object> queryParams = new HashMap<>();
        private Object body;

        private Builder(String path) {
            if (path == null || path.isBlank()) throw new IllegalArgumentException("path is required");
            this.path = path;
        }

        public Builder header(String k, String v) {
            if (k != null && v != null) headers.put(k, v);
            return this;
        }

        public Builder headers(Map<String, String> map) {
            if (map != null) headers.putAll(map);
            return this;
        }

        public Builder queryParam(String k, Object v) {
            if (k != null && v != null) queryParams.put(k, v);
            return this;
        }

        public Builder queryParams(Map<String, Object> map) {
            if (map != null) queryParams.putAll(map);
            return this;
        }

        public Builder body(Object body) {
            this.body = body;
            return this;
        }

        public ApiRequest build() {
            return new ApiRequest(path, Map.copyOf(headers), Map.copyOf(queryParams), body);
        }
    }
}