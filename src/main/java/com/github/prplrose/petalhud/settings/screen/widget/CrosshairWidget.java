package com.github.prplrose.petalhud.settings.screen.widget;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.RotatingCubeMapRenderer;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.text.Text;

import static net.minecraft.client.gui.screen.TitleScreen.PANORAMA_CUBE_MAP;

public class CrosshairWidget extends ClickableWidget {

    MinecraftClient client = MinecraftClient.getInstance();
    TextRenderer textRenderer = client.textRenderer;

    public CrosshairWidget(int x, int y, int width, int height) {
        super(x, y, width, height, Text.translatable("gui.petalhud.settings.crosshair.preview"));
    }

    public static Builder builder() {
        return new Builder();
    }

    @Override
    protected void renderWidget(DrawContext context, int mouseX, int mouseY, float delta) {
        //context.fill(this.getX(), this.getY(), this.getX()+ this.getWidth(), this.getY()+ this.getHeight(), 0xffffffff);
        RotatingCubeMapRenderer backgroundRenderer = new RotatingCubeMapRenderer(PANORAMA_CUBE_MAP);
        //new CustomCubeMapRenderer().draw(this.client, 0f, 0f, delta);

        //context.enableScissor(this.getX(), this.getY(), this.getX()+ this.getWidth(), this.getY()+ this.getHeight());
        //context.drawTexture(PANORAMA_OVERLAY, 0, 0, this.width, this.height, 0.0f, 0.0f, 16, 128, 16, 128);
        context.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        //context.disableScissor();


        backgroundRenderer.render(delta, 1f);


    }

    @Override
    protected void appendClickableNarrations(NarrationMessageBuilder builder) {

    }

    public static class Builder {
        private int x;
        private int y;
        private int width = 150;
        private int height = 20;

        Builder() {
        }

        public CrosshairWidget.Builder position(int x, int y) {
            this.x = x;
            this.y = y;
            return this;
        }

        public CrosshairWidget.Builder width(int width) {
            this.width = width;
            return this;
        }

        public CrosshairWidget.Builder size(int width, int height) {
            this.width = width;
            this.height = height;
            return this;
        }

        public CrosshairWidget.Builder dimensions(int x, int y, int width, int height) {
            return this.position(x, y).size(width, height);
        }


        public CrosshairWidget build() {
            return new CrosshairWidget(this.x, this.y, this.width, this.height);
        }
    }
}
