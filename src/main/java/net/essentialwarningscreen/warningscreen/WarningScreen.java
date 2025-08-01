package net.essentialwarningscreen.warningscreen;

import net.essentialwarningscreen.config.ConfigSaver;
import net.essentialwarningscreen.constants.Constants;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.MultilineText;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import net.minecraft.util.Colors;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import org.lwjgl.glfw.GLFW;

public class WarningScreen extends Screen {

    private static final Identifier LOGO_TEXTURE = new Identifier("essential-warning-screen", "textures/gui/notessential.png");

    private final ConfigSaver configSaver;

    public WarningScreen(Text title, ConfigSaver configSaver) {
        super(title);
        this.configSaver = configSaver;
    }

    @Override
    protected void init() {
        super.init();
        addNotEssentialButton();
        addIgnoreButton();
        addDontShowAgainButton();
        addQuitGameButton();
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == GLFW.GLFW_KEY_ESCAPE) {
            return true;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        renderBackground(context);

        context.drawTexture(
                LOGO_TEXTURE,
                this.width / 2 - 375 / 2,
                this.height / 2 - 130,
                0, 0,
                375, 25,
                375, 25
        );

        Text essentialText = Text.literal("Essential").styled(style -> style.withColor(0x1d6aff));

        super.render(context, mouseX, mouseY, delta);
        MultilineText.create(
                MinecraftClient.getInstance().textRenderer,
                Text.translatable("essentialwarningscreen.info_1", essentialText),
                Text.translatable("essentialwarningscreen.info_2"),
                Text.translatable("essentialwarningscreen.info_3")
        ).drawCenterWithShadow(context, this.width / 2, this.height / 2 - 80, 20, Colors.WHITE);
    }

    private void addNotEssentialButton() {
        this.addDrawableChild(ButtonWidget.builder(
                                Text.translatable("essentialwarningscreen.why_not_essential"),
                                button -> {
                                    Util.getOperatingSystem().open(Constants.NOTESSENTIAL_GUIDE_LINK);
                                }
                        )
                        .dimensions(this.width / 2 - 200 / 2, this.height / 2 - 20, 200, 20)
                        .build()
        );
    }

    private void addIgnoreButton() {
        this.addDrawableChild(ButtonWidget.builder(
                                Text.translatable("essentialwarningscreen.ignore"),
                                button -> this.close()
                        )
                        .dimensions(this.width / 2 - 200 / 2, this.height / 2 + 25, 200, 20)
                        .build()
        );
    }

    private void addDontShowAgainButton() {
        this.addDrawableChild(ButtonWidget.builder(
                                Text.translatable("essentialwarningscreen.dont_show_again"),
                                button -> {
                                    configSaver.saveData("true", Constants.ESSENTIAL_SCREEN_DISABLED);
                                    this.close();
                                }
                        )
                        .dimensions(this.width / 2 - 200 / 2, this.height / 2 + 50, 200, 20)
                        .build()
        );
    }

    private void addQuitGameButton() {
        this.addDrawableChild(ButtonWidget.builder(
                                Text.translatable("menu.quit"),
                                button -> MinecraftClient.getInstance().scheduleStop()
                        )
                        .dimensions(this.width / 2 - 200 / 2, this.height / 2 + 75, 200, 20)
                        .build()
        );
    }

    @Override
    public void close() {
        super.close();
    }
}
