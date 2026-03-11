package net.essentialwarningscreen;

import net.essentialwarningscreen.warningscreen.WarningScreen;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.fabricmc.fabric.api.client.screen.v1.ScreenEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.text.Text;

public class EssentialWarningScreen implements ClientModInitializer {

    public static final String MOD_ID = "essentialwarningscreen";

    @Override
    public void onInitializeClient() {
        EssentialWarningScreenConfig.init();
        ClientLifecycleEvents.CLIENT_STARTED.register(this::onFullLoad);
    }

    private void onFullLoad(MinecraftClient client) {
        ScreenEvents.AFTER_INIT.register((mc, screen, width, height) -> {
            if (screen instanceof TitleScreen && shouldShowWarning()) {
                mc.setScreen(new WarningScreen(Text.empty()));
            }
        });
    }

    private boolean shouldShowWarning() {
        return FabricLoader.getInstance().isModLoaded("essential")
                && !EssentialWarningScreenConfig.config.essentialWarningDisabled;
    }
}