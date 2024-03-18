package com.github.prplrose.petalhud.client;

import com.github.prplrose.petalhud.performanceindicators.PerformanceHUD;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;

public class PetalHUDClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {

        HudRenderCallback.EVENT.register(new PerformanceHUD());
    }
}
