package net.essentialwarningscreen;

import me.fzzyhmstrs.fzzy_config.api.ConfigApiJava;
import me.fzzyhmstrs.fzzy_config.api.RegisterType;
import me.fzzyhmstrs.fzzy_config.config.Config;
import net.minecraft.util.Identifier;

public class EssentialWarningScreenConfig extends Config {
    public static EssentialWarningScreenConfig config = ConfigApiJava.registerAndLoadConfig(EssentialWarningScreenConfig::new, RegisterType.CLIENT);
    public boolean essentialWarningDisabled = false;

    public EssentialWarningScreenConfig() {
        super(new Identifier(EssentialWarningScreen.MOD_ID, "config"));
    }

    public static void init() {
    }
}