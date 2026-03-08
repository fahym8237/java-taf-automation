package com.fahym.tas.observability.attachments;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

// write files safely
public final class AttachmentManager {
    private AttachmentManager() {}

    public static Path ensureDir(Path dir) {
        try {
            Files.createDirectories(dir);
            return dir;
        } catch (IOException e) {
            throw new IllegalStateException("Failed to create directory: " + dir, e);
        }
    }

    public static Path saveBytes(Path dir, String filename, byte[] bytes) {
        ensureDir(dir);
        Path file = dir.resolve(filename);
        try {
            Files.write(file, bytes);
            return file;
        } catch (IOException e) {
            throw new IllegalStateException("Failed to write bytes: " + file, e);
        }
    }

    public static Path saveText(Path dir, String filename, String text) {
        ensureDir(dir);
        Path file = dir.resolve(filename);
        try {
            Files.writeString(file, text, StandardCharsets.UTF_8);
            return file;
        } catch (IOException e) {
            throw new IllegalStateException("Failed to write text: " + file, e);
        }
    }
}
