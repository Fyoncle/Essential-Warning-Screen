package net.essentialwarningscreen.config;

import net.essentialwarningscreen.constants.Constants;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class ConfigSaver {
    private final Path configPath;

    public ConfigSaver(String path, String filename) {
        this.configPath = Paths.get(path + filename);
    }

    public void saveData(String value, int dataType) {
        if (dataType != Constants.ESSENTIAL_SCREEN_DISABLED) return;

        new Thread(() -> {
            try {
                Files.writeString(
                        configPath,
                        "disableEssentialScreen = " + value,
                        StandardOpenOption.CREATE,
                        StandardOpenOption.TRUNCATE_EXISTING
                );
            } catch (IOException ignored) {
            }
        }).start();
    }
}