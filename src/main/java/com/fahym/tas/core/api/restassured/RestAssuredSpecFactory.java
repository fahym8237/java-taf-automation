package com.fahym.tas.core.api.restassured;

import com.fahym.tas.core.config.Config;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;

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