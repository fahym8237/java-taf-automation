package com.fahym.tas.core.api.restassured;

import com.fahym.tas.core.api.model.ApiRequest;
import com.fahym.tas.core.api.model.ApiResponse;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;


/**
 * Executes API requests using RestAssured and converts them into framework-level responses.
 *
 * This class acts as the execution engine of the API layer. It receives ApiRequest
 * objects created by the framework and translates them into RestAssured HTTP calls.
 * After execution, the RestAssured response is wrapped into an ApiResponse object
 * that can be used by higher layers of the test automation framework.
 *
 * The client supports common HTTP methods such as GET, POST, PUT, PATCH, and DELETE.
 * All requests inherit shared configuration through a base RequestSpecification,
 * typically created by the RestAssuredSpecFactory.
 *
 * Typical flow:
 *   ApiRequest -> RestAssuredClient -> RestAssured HTTP execution -> ApiResponse
 *
 * This design centralizes all HTTP execution logic and prevents higher layers
 * of the framework from directly depending on RestAssured.
 */
public final class RestAssuredClient {

    private final RequestSpecification baseSpec;

    public RestAssuredClient(RequestSpecification baseSpec) {
        this.baseSpec = baseSpec;
    }

    public ApiResponse get(ApiRequest req)    { return execute("GET", req); }
    public ApiResponse post(ApiRequest req)   { return execute("POST", req); }
    public ApiResponse put(ApiRequest req)    { return execute("PUT", req); }
    public ApiResponse patch(ApiRequest req)  { return execute("PATCH", req); }
    public ApiResponse delete(ApiRequest req) { return execute("DELETE", req); }

    private ApiResponse execute(String method, ApiRequest req) {
        var spec = given()
                .spec(baseSpec)
                .headers(req.headers())
                .queryParams(req.queryParams());

        if (req.body() != null) {
        	spec = spec.header("Content-Type", "application/json").body(req.body());
        }

        var response = switch (method) {
            case "GET" -> spec.when().get(req.path());
            case "POST" -> spec.when().post(req.path());
            case "PUT" -> spec.when().put(req.path());
            case "PATCH" -> spec.when().patch(req.path());
            case "DELETE" -> spec.when().delete(req.path());
            default -> throw new IllegalArgumentException("Unsupported method: " + method);
        };

        return new ApiResponse(
                response.getStatusCode(),
                response.getBody().asString(),
                response.getHeaders()
        );
    }
}