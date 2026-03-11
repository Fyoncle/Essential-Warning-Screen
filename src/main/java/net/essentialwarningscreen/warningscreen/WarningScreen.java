package net.essentialwarningscreen.warningscreen;

import net.essentialwarningscreen.EssentialWarningScreenConfig;
import net.minecraft.client.font.MultilineText;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.text.Text;
import net.minecraft.util.Colors;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import org.lwjgl.glfw.GLFW;

public class WarningScreen extends Screen {

    private static final Identifier LOGO_TEXTURE =
            Identifier.of("essentialwarningscreen", "notessential");

    private MultilineText infoText;

    public WarningScreen(Text title) {
        super(title);
    }

    @Override
    protected void init() {
        super.init();

        Text essentialText = Text.literal("Essential").styled(style -> style.withColor(0x1d6aff));
        this.infoText = MultilineText.create(
                this.textRenderer,
                Text.translatable("essentialwarningscreen.info_1", essentialText),
                Text.translatable("essentialwarningscreen.info_2"),
                Text.translatable("essentialwarningscreen.info_3")
        );

        addNotEssentialButton();
        addIgnoreButton();
        addDontWarnAgainButton();
        addQuitGameButton();
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == GLFW.GLFW_KEY_ESCAPE) return true;
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        renderBackground(context, mouseX, mouseY, delta);
        super.render(context, mouseX, mouseY, delta);

        context.drawGuiTexture(
                RenderLayer::getGuiTextured,
                LOGO_TEXTURE,
                this.width / 2 - 375 / 2,
                this.height / 2 - 130,
                375, 25
        );

        infoText.drawCenterWithShadow(context, this.width / 2, this.height / 2 - 80, 20, Colors.WHITE);
    }

    private void addNotEssentialButton() {
        this.addDrawableChild(ButtonWidget.builder(
                Text.translatable("essentialwarningscreen.why_not_essential"),
                button -> Util.getOperatingSystem().open("https://alternatives.microcontrollers.dev/latest/essential/")
        ).dimensions(this.width / 2 - 200 / 2, this.height / 2 - 20, 200, 20).build());
    }

    private void addIgnoreButton() {
        this.addDrawableChild(ButtonWidget.builder(
                Text.translatable("essentialwarningscreen.ignore"),
                button -> this.close()
        ).dimensions(this.width / 2 - 200 / 2, this.height / 2 + 25, 200, 20).build());
    }

    private void addDontWarnAgainButton() {
        this.addDrawableChild(ButtonWidget.builder(
                Text.translatable("essentialwarningscreen.dont_warn_again"),
                button -> {
                    EssentialWarningScreenConfig.config.essentialWarningDisabled = true;
                    EssentialWarningScreenConfig.config.save();
                    this.close();
                }
        ).dimensions(this.width / 2 - 200 / 2, this.height / 2 + 50, 200, 20).build());
    }

    private void addQuitGameButton() {
        this.addDrawableChild(ButtonWidget.builder(
                Text.translatable("menu.quit"),
                button -> this.client.scheduleStop()
        ).dimensions(this.width / 2 - 200 / 2, this.height / 2 + 75, 200, 20).build());
    }
}