package com.github.prplrose.petalhud.settings.screen.widget;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.systems.VertexSorter;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.CubeMapRenderer;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.RotationAxis;
import org.joml.Matrix4f;

public class CustomCubeMapRenderer extends CubeMapRenderer {
    private final static Identifier face_textures = new Identifier("textures/gui/title/background/panorama");
    private static float time = 0f;
    private final Identifier[] faces = new Identifier[6];

    public CustomCubeMapRenderer() {
        super(face_textures);

        for (int i = 0; i < 6; ++i) {
            this.faces[i] = face_textures.withPath(face_textures.getPath() + "_" + i + ".png");
        }

    }

    @Override
    public void draw(MinecraftClient client, float xRot, float yRot, float delta) {
        time += delta;
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuffer();
        Matrix4f matrix4f = new Matrix4f().setPerspective(1.4835298f, (float) client.getWindow().getFramebufferWidth() / (float) client.getWindow().getFramebufferHeight(), 0.05f, 10.0f);
        RenderSystem.backupProjectionMatrix();
        RenderSystem.setProjectionMatrix(matrix4f, VertexSorter.BY_DISTANCE);
        MatrixStack matrixStack = RenderSystem.getModelViewStack();
        matrixStack.push();
        matrixStack.loadIdentity();
        matrixStack.multiply(RotationAxis.POSITIVE_X.rotationDegrees(180.0f));
        RenderSystem.applyModelViewMatrix();
        RenderSystem.setShader(GameRenderer::getPositionTexColorProgram);
        RenderSystem.enableBlend();
        RenderSystem.disableCull();
        RenderSystem.depthMask(false);
        float u = 3.0f;
        for (int i = 0; i < 4; ++i) {
            matrixStack.push();
            float blurX = ((float) (i % 2) / 2.0f - 0.5f) / 256.0f;
            float blurY = ((float) (i / 2) / 2.0f - 0.5f) / 256.0f;
            float h = 0.0f;
            matrixStack.translate(2, 2, 0.0f);
            matrixStack.multiply(RotationAxis.POSITIVE_X.rotationDegrees(xRot));
            matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(yRot + time / 1f));
            RenderSystem.applyModelViewMatrix();
            for (int k = 0; k < 6; ++k) {
                RenderSystem.setShaderTexture(0, this.faces[k]);
                bufferBuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE_COLOR);
                int l = Math.round(255.0f) / (i + 1);
                if (k == 0) {
                    bufferBuilder.vertex(-u, -u, u).texture(0.0f, 0.0f).color(255, 255, 255, l).next();
                    bufferBuilder.vertex(-u, u, u).texture(0.0f, 1.0f).color(255, 255, 255, l).next();
                    bufferBuilder.vertex(u, u, u).texture(1.0f, 1.0f).color(255, 255, 255, l).next();
                    bufferBuilder.vertex(u, -u, u).texture(1.0f, 0.0f).color(255, 255, 255, l).next();
                }
                if (k == 1) {
                    bufferBuilder.vertex(u, -u, u).texture(0.0f, 0.0f).color(255, 255, 255, l).next();
                    bufferBuilder.vertex(u, u, u).texture(0.0f, 1.0f).color(255, 255, 255, l).next();
                    bufferBuilder.vertex(u, u, -u).texture(1.0f, 1.0f).color(255, 255, 255, l).next();
                    bufferBuilder.vertex(u, -u, -u).texture(1.0f, 0.0f).color(255, 255, 255, l).next();
                }
                if (k == 2) {
                    bufferBuilder.vertex(u, -u, -u).texture(0.0f, 0.0f).color(255, 255, 255, l).next();
                    bufferBuilder.vertex(u, u, -u).texture(0.0f, 1.0f).color(255, 255, 255, l).next();
                    bufferBuilder.vertex(-u, u, -u).texture(1.0f, 1.0f).color(255, 255, 255, l).next();
                    bufferBuilder.vertex(-u, -u, -u).texture(1.0f, 0.0f).color(255, 255, 255, l).next();
                }
                if (k == 3) {
                    bufferBuilder.vertex(-u, -u, -u).texture(0.0f, 0.0f).color(255, 255, 255, l).next();
                    bufferBuilder.vertex(-u, u, -u).texture(0.0f, 1.0f).color(255, 255, 255, l).next();
                    bufferBuilder.vertex(-u, u, u).texture(1.0f, 1.0f).color(255, 255, 255, l).next();
                    bufferBuilder.vertex(-u, -u, u).texture(1.0f, 0.0f).color(255, 255, 255, l).next();
                }
                if (k == 4) {
                    bufferBuilder.vertex(-u, -u, -u).texture(0.0f, 0.0f).color(255, 255, 255, l).next();
                    bufferBuilder.vertex(-u, -u, u).texture(0.0f, 1.0f).color(255, 255, 255, l).next();
                    bufferBuilder.vertex(u, -u, u).texture(1.0f, 1.0f).color(255, 255, 255, l).next();
                    bufferBuilder.vertex(u, -u, -u).texture(1.0f, 0.0f).color(255, 255, 255, l).next();
                }
                if (k == 5) {
                    bufferBuilder.vertex(-u, u, u).texture(0.0f, 0.0f).color(255, 255, 255, l).next();
                    bufferBuilder.vertex(-u, u, -u).texture(0.0f, 1.0f).color(255, 255, 255, l).next();
                    bufferBuilder.vertex(u, u, -u).texture(1.0f, 1.0f).color(255, 255, 255, l).next();
                    bufferBuilder.vertex(u, u, u).texture(1.0f, 0.0f).color(255, 255, 255, l).next();
                }

                tessellator.draw();
            }
            matrixStack.pop();
            RenderSystem.applyModelViewMatrix();
            RenderSystem.colorMask(true, true, true, false);
        }
        RenderSystem.colorMask(true, true, true, true);
        RenderSystem.restoreProjectionMatrix();
        matrixStack.pop();
        RenderSystem.applyModelViewMatrix();
        RenderSystem.depthMask(true);
        RenderSystem.enableCull();
        RenderSystem.enableDepthTest();

        //RenderSystem.
    }
}
