package net.essentialwarningscreen.config;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class ConfigReader {
    private final Path configPath;

    public ConfigReader(String path, String filename) {
        this.configPath = Path.of(path + filename);
    }

    public Map<String, String> readData() {
        Map<String, String> config = new HashMap<>();
        try {
            if (Files.exists(configPath)) {
                for (String line : Files.readAllLines(configPath)) {
                    String[] parts = line.split(" = ", 2);
                    if (parts.length == 2) {
                        config.put(parts[0], parts[1]);
                    }
                }
            }
        } catch (IOException ignored) {
        }
        return config;
    }
}