package com.github.prplrose.petalhud.settings.screen;

import com.github.prplrose.petalhud.Color;
import com.github.prplrose.petalhud.settings.screen.widget.ColorWidget;
import com.github.prplrose.petalhud.settings.screen.widget.CrosshairWidget;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

public class CrosshairSettingsScreen extends AbstractSettingsScreen {
    public static final Text TITLE = Text.translatable("gui.petalhud.settings.crosshair.title");

    public CrosshairSettingsScreen(Screen previous) {
        super(previous, TITLE);
        this.previous = previous;
    }

    @Override
    protected void init() {
        int left = this.width / 2 - 155;
        int right = left + 160;
        int y = this.height / 6 - 12;


        this.addDrawableChild(
                ColorWidget.builder(Text.translatable("gui.petalhud.settings.crosshair.x_color"))
                        .color(new Color(0xff0000))
                        .dimensions(left, y, 150, 20)
                        .build());
        this.addDrawableChild(
                ColorWidget.builder(Text.translatable("gui.petalhud.settings.crosshair.y_color"))
                        .color(new Color(0x00ff00))
                        .dimensions(left, y + 30, 150, 20)
                        .build());
        this.addDrawableChild(
                ColorWidget.builder(Text.translatable("gui.petalhud.settings.crosshair.z_color"))
                        .color(new Color(0x7f7fff))
                        .dimensions(left, y + 60, 150, 20)
                        .build());
        this.addDrawableChild(
                CrosshairWidget.builder()
                        .dimensions(right, y, 150, 80)
                        .build()
        );

        /*addDrawableChild(
                options = new OptionListWidget(this.client, this.width, this.height - 64, 32, 25)
        );*/


        super.init();
    }
}
