package com.github.prplrose.petalhud.settings.screen;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

@Environment(EnvType.CLIENT)
public abstract class AbstractSettingsScreen extends Screen {

    Screen previous;
    private final ButtonWidget doneButton = ButtonWidget.builder(Text.translatable("gui.done"), button -> {
                this.close();
            })
            .size(200, 20)
            .build();
    Text title;

    public AbstractSettingsScreen(Screen previous, Text title) {
        super(title);
        this.title = title;
        this.previous = previous;
    }

    @Override
    protected void init() {
        doneButton.setPosition(this.width / 2 - 100, this.height - 27);
        addDrawableChild(doneButton);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
        context.drawCenteredTextWithShadow(textRenderer, title, width / 2, 15, 0xffffffff);
    }

    @Override
    public void close() {
        client.setScreen(previous);
    }

}
