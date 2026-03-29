package com.fahym.tas.domain.api.booker.models;

/**
 * Represents the authentication response returned by the booker API.
 *
 * This model holds the token received after a successful call to the /auth
 * endpoint. It is deserialized from the raw JSON response by AuthClient and
 * used by higher layers to authorize protected booking operations.
 *
 * Main interaction:
 *   API response -> AuthResponse -> AuthClient / BookingFlow
 */
public final class AuthResponse {
    private String token;

    public AuthResponse() {}

    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }
}