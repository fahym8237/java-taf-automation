package com.fahym.tas.domain.api.booker.clients;

import com.fahym.tas.core.api.model.ApiRequest;
import com.fahym.tas.core.api.model.ApiResponse;
import com.fahym.tas.core.api.restassured.RestAssuredClient;
import com.fahym.tas.domain.api.booker.config.BookerPaths;
import com.fahym.tas.domain.api.booker.models.AuthRequest;
import com.fahym.tas.domain.api.booker.models.AuthResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

public final class AuthClient {

    private final RestAssuredClient client;
    private final ObjectMapper mapper = new ObjectMapper();

    public AuthClient(RestAssuredClient client) {
        this.client = client;
    }

    public ApiResponse createTokenRaw(String username, String password) {
        AuthRequest body = new AuthRequest(username, password);

        ApiRequest req = ApiRequest.builder(BookerPaths.AUTH)
                .header("Content-Type", "application/json")
                .body(body)
                .build();

        return client.post(req);
    }

    public String createToken(String username, String password) {
        ApiResponse res = createTokenRaw(username, password);
        if (res.statusCode() != 200) {
            throw new IllegalStateException("Auth failed. Status=" + res.statusCode() + " Body=" + res.body());
        }

        try {
            AuthResponse ar = mapper.readValue(res.body(), AuthResponse.class);
            if (ar.getToken() == null || ar.getToken().isBlank()) {
                throw new IllegalStateException("Auth response missing token. Body=" + res.body());
            }
            return ar.getToken();
        } catch (Exception e) {
            throw new IllegalStateException("Failed to parse auth response. Body=" + res.body(), e);
        }
    }
}
