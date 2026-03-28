package com.fahym.tas.domain.api.booker.models;

public final class AuthResponse {
    private String token;

    public AuthResponse() {}

    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }
}