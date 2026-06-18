package com.fahym.tas.integrations.xray;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.time.Duration;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public final class XrayFeatureUploader {

    private static final String XRAY_BASE = "https://eu.xray.cloud.getxray.app";
    private static final String AUTH_URL = XRAY_BASE + "/api/v2/authenticate";

    public static void main(String[] args) throws Exception {
        if (args.length != 2) {
            System.out.println("""
                    Usage:
                      java ... XrayFeatureUploader <projectKey> <feature-file-or-features-dir>

                    Examples:
                      java ... XrayFeatureUploader TAS src/test/resources/features
                      java ... XrayFeatureUploader TAS src/test/resources/features/smoke/ui/opencart_auth.feature
                    """);
            System.exit(2);
        }

        String projectKey = args[0];
        Path input = Path.of(args[1]);

        if (!Files.exists(input)) {
            throw new IllegalArgumentException("Input path does not exist: " + input);
        }

        String clientId = requireEnv("XRAY_CLIENT_ID");
        String clientSecret = requireEnv("XRAY_CLIENT_SECRET");

        HttpClient http = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(30))
                .build();

        String token = authenticate(http, clientId, clientSecret);

        Path uploadFile;
        boolean tempZip = false;

        if (Files.isDirectory(input)) {
            uploadFile = zipFeatureFiles(input);
            tempZip = true;
        } else {
            uploadFile = input;
            String lower = input.getFileName().toString().toLowerCase();
            if (!lower.endsWith(".feature") && !lower.endsWith(".zip")) {
                throw new IllegalArgumentException("Input must be a .feature file, a .zip file, or a directory of features: " + input);
            }
        }

        try {
            String response = uploadFeatures(http, token, projectKey, uploadFile);
            System.out.println("[XRAY] Feature import successful");
            System.out.println(response);
        } finally {
            if (tempZip) {
                Files.deleteIfExists(uploadFile);
            }
        }
    }

    private static String requireEnv(String name) {
        String value = System.getenv(name);
        if (value == null || value.isBlank()) {
            throw new IllegalStateException("Missing required environment variable: " + name);
        }
        return value;
    }

    private static String authenticate(HttpClient http, String clientId, String clientSecret)
            throws IOException, InterruptedException {

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

        HttpResponse<String> response = http.send(request, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));

        if (response.statusCode() < 200 || response.statusCode() >= 300) {
            throw new IllegalStateException("XRAY auth failed. Status=" + response.statusCode() + " Body=" + response.body());
        }

        // Xray returns the token as a JSON string literal, e.g. "eyJ..."
        return response.body().replace("\"", "").trim();
    }

    private static String uploadFeatures(HttpClient http, String token, String projectKey, Path file)
            throws IOException, InterruptedException {

        String boundary = "----TasBoundary" + UUID.randomUUID().toString().replace("-", "");
        String endpoint = XRAY_BASE + "/api/v2/import/feature?projectKey=" + projectKey;

        String filename = file.getFileName().toString();
        byte[] fileBytes = Files.readAllBytes(file);

        byte[] prefix = (
                "--" + boundary + "\r\n" +
                "Content-Disposition: form-data; name=\"file\"; filename=\"" + filename + "\"\r\n" +
                "Content-Type: " + detectContentType(filename) + "\r\n\r\n"
        ).getBytes(StandardCharsets.UTF_8);

        byte[] suffix = ("\r\n--" + boundary + "--\r\n").getBytes(StandardCharsets.UTF_8);

        byte[] multipartBody = concat(prefix, fileBytes, suffix);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(endpoint))
                .timeout(Duration.ofMinutes(2))
                .header("Authorization", "Bearer " + token)
                .header("Content-Type", "multipart/form-data; boundary=" + boundary)
                .POST(HttpRequest.BodyPublishers.ofByteArray(multipartBody))
                .build();

        HttpResponse<String> response = http.send(request, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));

        if (response.statusCode() < 200 || response.statusCode() >= 300) {
            throw new IllegalStateException("XRAY feature import failed. Status=" + response.statusCode() + " Body=" + response.body());
        }

        return response.body();
    }

    private static Path zipFeatureFiles(Path featuresRoot) throws IOException {
        Path zip = Files.createTempFile("tas-features-", ".zip");

        try (ZipOutputStream zos = new ZipOutputStream(Files.newOutputStream(zip))) {
            Files.walk(featuresRoot)
                    .filter(Files::isRegularFile)
                    .filter(p -> p.getFileName().toString().toLowerCase().endsWith(".feature"))
                    .forEach(path -> {
                        try {
                            String entryName = featuresRoot.relativize(path).toString().replace("\\", "/");
                            zos.putNextEntry(new ZipEntry(entryName));
                            Files.copy(path, zos);
                            zos.closeEntry();
                        } catch (IOException e) {
                            throw new RuntimeException("Failed zipping feature file: " + path, e);
                        }
                    });
        }

        return zip;
    }

    private static String detectContentType(String filename) {
        String lower = filename.toLowerCase();
        if (lower.endsWith(".zip")) {
            return "application/zip";
        }
        return "text/plain";
    }

    private static byte[] concat(byte[] a, byte[] b, byte[] c) {
        byte[] out = new byte[a.length + b.length + c.length];
        System.arraycopy(a, 0, out, 0, a.length);
        System.arraycopy(b, 0, out, a.length, b.length);
        System.arraycopy(c, 0, out, a.length + b.length, c.length);
        return out;
    }

    private static String escapeJson(String s) {
        return s.replace("\\", "\\\\").replace("\"", "\\\"");
    }
}