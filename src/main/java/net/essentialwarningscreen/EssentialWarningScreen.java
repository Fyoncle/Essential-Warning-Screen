package net.essentialwarningscreen;

import net.essentialwarningscreen.warningscreen.WarningScreen;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.screen.v1.ScreenEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.text.Text;

public class EssentialWarningScreen implements ClientModInitializer {

    public static final String MOD_ID = "essentialwarningscreen";
    private boolean hasShownWarning = false;

    @Override
    public void onInitializeClient() {
        EssentialWarningScreenConfig.init();
        ScreenEvents.AFTER_INIT.register((mc, screen, width, height) -> {
            if (screen instanceof TitleScreen && !hasShownWarning && shouldShowWarning()) {
                hasShownWarning = true;
                mc.setScreen(new WarningScreen(Text.empty()));
            }
        });
    }

    private boolean shouldShowWarning() {
        return FabricLoader.getInstance().isModLoaded("essential")
                && !EssentialWarningScreenConfig.config.essentialWarningDisabled;
    }
}