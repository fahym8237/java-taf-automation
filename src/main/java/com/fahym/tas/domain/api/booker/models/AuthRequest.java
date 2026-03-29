package com.fahym.tas.domain.api.booker.models;

/**
 * Represents the authentication request payload for the booker API.
 *
 * This model contains the username and password sent to the /auth endpoint.
 * It is used by AuthClient as the request body and is serialized into JSON
 * when the authentication request is executed.
 *
 * Main interaction:
 *   AuthClient -> AuthRequest -> API request body
 */
public final class AuthRequest {
    private String username;
    private String password;

    public AuthRequest() {}

    public AuthRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() { return username; }
    public String getPassword() { return password; }

    public void setUsername(String username) { this.username = username; }
    public void setPassword(String password) { this.password = password; }
}