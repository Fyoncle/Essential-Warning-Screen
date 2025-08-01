package net.essentialwarningscreen;

import net.essentialwarningscreen.config.ConfigReader;
import net.essentialwarningscreen.config.ConfigSaver;
import net.essentialwarningscreen.constants.Constants;
import net.essentialwarningscreen.warningscreen.WarningScreen;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.text.Text;

public class EssentialWarningScreen implements ClientModInitializer {
    public ConfigSaver configSaver;
    public ConfigReader configReader;
    private WarningScreen essentialWarningScreen;

    @Override
    public void onInitializeClient() {
        initConfigs();
        ClientLifecycleEvents.CLIENT_STARTED.register(this::onFullLoad);
    }

    private void initConfigs() {
        String configDir = FabricLoader.getInstance().getConfigDir().toString() + "/";
        configReader = new ConfigReader(configDir, Constants.CONFIG_FILE_NAME);
        configSaver = new ConfigSaver(configDir, Constants.CONFIG_FILE_NAME);

        if (configReader.readData().isEmpty()) {
            configSaver.saveData("false", Constants.ESSENTIAL_SCREEN_DISABLED);
        }
        essentialWarningScreen = new WarningScreen(Text.empty(), configSaver);
    }

    private void onFullLoad(MinecraftClient client) {
        if (shouldShowWarning() && client.currentScreen instanceof TitleScreen) {
            client.setScreen(essentialWarningScreen);
        }
    }

    private boolean shouldShowWarning() {
        return FabricLoader.getInstance().isModLoaded("essential")
                && "false".equals(configReader.readData().get("disableEssentialScreen"));
    }
}