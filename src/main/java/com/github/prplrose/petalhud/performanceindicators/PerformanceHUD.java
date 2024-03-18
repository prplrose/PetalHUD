package com.github.prplrose.petalhud.performanceindicators;


import com.github.prplrose.petalhud.PetalHUD;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.Identifier;

public class PerformanceHUD implements HudRenderCallback {

    public static final Identifier LOW_TPS = new Identifier(PetalHUD.MODID, "textures/performance_icons/low_tps.png");

    @Override
    public void onHudRender(DrawContext context, float tickDelta) {
        renderTickrate(context);
    }

    private void setOrange(DrawContext context) {
        context.setShaderColor(0.90f, 0.51f, 0f, 1f);
    }

    private void setRed(DrawContext context) {
        context.setShaderColor(0.87f, 0.07f, 0f, 1f);
    }

    private void clearColor(DrawContext context) {
        context.setShaderColor(1f, 1f, 1f, 1f);
    }

    private void renderTickrate(DrawContext context) {
        float mspt = Tickrate.get();
        ClientWorld clientWorld = MinecraftClient.getInstance().world;
        if (clientWorld == null) {
            PetalHUD.LOGGER.error("NO CLIENT WORLD");
            return;
        }
        float goalMSPT = clientWorld.getTickManager().getMillisPerTick();
        float ratio = mspt / goalMSPT;
        if (ratio < 0.5f)
            return;
        setOrange(context);
        if (ratio > 1f)
            setRed(context);
        context.drawTexture(PerformanceHUD.LOW_TPS, 0, 0, 0, 0, 0, 24, 24, 24, 24);
        clearColor(context);

    }

}
