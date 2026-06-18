package com.fahym.tas.integrations.xray;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.time.Duration;
import java.util.UUID;

public final class XrayUploader {

    private static final String IMPORT_URL =
            "https://eu.xray.cloud.getxray.app/api/v2/import/execution/cucumber/multipart";

    public static void main(String[] args) throws Exception {
        XrayConfig cfg = XrayConfig.fromEnv(args);

        if (!Files.exists(cfg.resultsFile())) {
            throw new IllegalStateException("Results file not found: " + cfg.resultsFile());
        }

        System.out.println("[XRAY] Authenticating...");
        XrayAuthClient auth = new XrayAuthClient();
        String token = auth.authenticate(cfg.clientId(), cfg.clientSecret());

        System.out.println("[XRAY] Uploading multipart results from: " + cfg.resultsFile());
        String executionInfo = XrayExecutionInfoBuilder.build(cfg);
        System.out.println("[XRAY] Execution info: " + executionInfo);

        HttpClient http = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(30))
                .build();

        String response = uploadMultipart(http, token, cfg.resultsFile().toString(), executionInfo);

        System.out.println("[XRAY] Upload successful");
        System.out.println(response);
    }

    private static String uploadMultipart(HttpClient http,
                                          String token,
                                          String resultsPath,
                                          String executionInfo)
            throws IOException, InterruptedException {

        String boundary = "----TasBoundary" + UUID.randomUUID().toString().replace("-", "");
        byte[] resultsBytes = Files.readAllBytes(java.nio.file.Path.of(resultsPath));

        String resultsName = java.nio.file.Path.of(resultsPath).getFileName().toString();

        byte[] part1 = (
                "--" + boundary + "\r\n" +
                "Content-Disposition: form-data; name=\"results\"; filename=\"" + resultsName + "\"\r\n" +
                "Content-Type: application/json\r\n\r\n"
        ).getBytes(StandardCharsets.UTF_8);

        byte[] part2 = (
                "\r\n--" + boundary + "\r\n" +
                "Content-Disposition: form-data; name=\"info\"; filename=\"info.json\"\r\n" +
                "Content-Type: application/json\r\n\r\n" +
                executionInfo + "\r\n" +
                "--" + boundary + "--\r\n"
        ).getBytes(StandardCharsets.UTF_8);

        byte[] body = concat(part1, resultsBytes, part2);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(IMPORT_URL))
                .timeout(Duration.ofMinutes(2))
                .header("Authorization", "Bearer " + token)
                .header("Content-Type", "multipart/form-data; boundary=" + boundary)
                .POST(HttpRequest.BodyPublishers.ofByteArray(body))
                .build();

        HttpResponse<String> response = http.send(
                request,
                HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8)
        );

        if (response.statusCode() < 200 || response.statusCode() >= 300) {
            throw new IllegalStateException(
                    "XRAY execution import failed. Status=" + response.statusCode() + " Body=" + response.body()
            );
        }

        return response.body();
    }

    private static byte[] concat(byte[] a, byte[] b, byte[] c) {
        byte[] out = new byte[a.length + b.length + c.length];
        System.arraycopy(a, 0, out, 0, a.length);
        System.arraycopy(b, 0, out, a.length, b.length);
        System.arraycopy(c, 0, out, a.length + b.length, c.length);
        return out;
    }
}