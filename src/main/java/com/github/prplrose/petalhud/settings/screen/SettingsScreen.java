package com.github.prplrose.petalhud.settings.screen;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

@Environment(EnvType.CLIENT)
public class SettingsScreen extends AbstractSettingsScreen {

    public SettingsScreen(Screen previous) {
        super(previous, Text.translatable("gui.petalhud.settings.title"));
    }

    @Override
    protected void init() {
        int x = this.width / 2 - 155;
        int y = this.height / 6 - 12;
        this.addDrawableChild(
                ButtonWidget.builder(
                                CrosshairSettingsScreen.TITLE,
                                button -> this.client.setScreen(new CrosshairSettingsScreen(this))
                        )
                        .dimensions(x, y, 150, 20)
                        .build());

        super.init();
    }

}
