package com.fahym.tas.core.api.restassured;

import com.fahym.tas.core.config.Config;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;


/**
 * Factory responsible for creating the base RestAssured RequestSpecification
 * used by the API client.
 *
 * This class centralizes the configuration of common API request settings such as:
 *   - Base API URI
 *   - Default headers
 *   - HTTPS configuration
 *   - RestAssured filters
 *
 * The generated RequestSpecification is injected into the RestAssuredClient
 * and reused across all API calls, ensuring consistent configuration and
 * avoiding duplicated setup code.
 *
 * It also registers the ApiCaptureFilter, which enables the framework to
 * capture and record API request/response exchanges for observability,
 * debugging, and reporting purposes.
 *
 * Typical flow:
 *   Config -> RestAssuredSpecFactory -> RequestSpecification -> RestAssuredClient
 */
public final class RestAssuredSpecFactory {
    private RestAssuredSpecFactory() {}

    public static RequestSpecification baseSpec(Config cfg) {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();

        return new RequestSpecBuilder()
                .setBaseUri(cfg.apiBaseUrl())
                .setRelaxedHTTPSValidation()
                .addHeader("Accept", "application/json")
                .addFilter(new ApiCaptureFilter()) // ✅ capture last exchange per thread
                .build();
    }
}