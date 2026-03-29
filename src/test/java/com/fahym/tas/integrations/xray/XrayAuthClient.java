package com.fahym.tas.integrations.xray;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;

public final class XrayAuthClient {

    private static final String AUTH_URL = "https://xray.cloud.getxray.app/api/v2/authenticate";

    private final HttpClient http;

    public XrayAuthClient() {
        this.http = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(30))
                .build();
    }

    public String authenticate(String clientId, String clientSecret) throws IOException, InterruptedException {
        String body = """
                {
                  "client_id": "%s",
                  "client_secret": "%s"
                }
                """.formatted(escapeJson(clientId), escapeJson(clientSecret));

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(AUTH_URL))
                .timeout(Duration.ofSeconds(30))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(body, StandardCharsets.UTF_8))
                .build();

        HttpResponse<String> response = http.send(
                request,
                HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8)
        );

        if (response.statusCode() < 200 || response.statusCode() >= 300) {
            throw new IllegalStateException(
                    "XRAY auth failed. Status=" + response.statusCode() + " Body=" + response.body()
            );
        }

        // Xray returns the token as a quoted JSON string.
        return response.body().replace("\"", "").trim();
    }

    private static String escapeJson(String s) {
        return s.replace("\\", "\\\\").replace("\"", "\\\"");
    }
}