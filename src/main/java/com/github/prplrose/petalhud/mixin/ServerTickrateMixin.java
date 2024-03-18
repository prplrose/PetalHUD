package com.github.prplrose.petalhud.mixin;

import com.github.prplrose.petalhud.performanceindicators.Tickrate;
import com.mojang.authlib.GameProfile;
import com.mojang.datafixers.DataFixer;
import net.minecraft.resource.ResourcePackManager;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.SaveLoader;
import net.minecraft.server.WorldGenerationProgressListenerFactory;
import net.minecraft.server.integrated.IntegratedServer;
import net.minecraft.util.ApiServices;
import net.minecraft.util.SystemDetails;
import net.minecraft.world.level.storage.LevelStorage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.net.Proxy;

@Mixin(IntegratedServer.class)
public class ServerTickrateMixin extends MinecraftServer {
    public ServerTickrateMixin(Thread serverThread, LevelStorage.Session session, ResourcePackManager dataPackManager, SaveLoader saveLoader, Proxy proxy, DataFixer dataFixer, ApiServices apiServices, WorldGenerationProgressListenerFactory worldGenerationProgressListenerFactory) {
        super(serverThread, session, dataPackManager, saveLoader, proxy, dataFixer, apiServices, worldGenerationProgressListenerFactory);
    }

    @Inject(method = "tickTickLog", at = @At("HEAD"))
    void logTickrate(long nanos, CallbackInfo ci) {
        if (Tickrate.getSource() == Tickrate.Source.VANILLA) {
            Tickrate.set((float) (nanos / 1000000.0));
        }
    }

    @Shadow
    public boolean setupServer() {
        return false;
    }

    @Shadow
    public int getOpPermissionLevel() {
        return 0;
    }

    @Shadow
    public int getFunctionPermissionLevel() {
        return 0;
    }

    @Shadow
    public boolean shouldBroadcastRconToOps() {
        return false;
    }

    @Shadow
    public SystemDetails addExtraSystemDetails(SystemDetails details) {
        return null;
    }

    @Shadow
    public boolean isDedicated() {
        return false;
    }

    @Shadow
    public int getRateLimit() {
        return 0;
    }

    @Shadow
    public boolean isUsingNativeTransport() {
        return false;
    }

    @Shadow
    public boolean areCommandBlocksEnabled() {
        return false;
    }

    @Shadow
    public boolean isRemote() {
        return false;
    }

    @Shadow
    public boolean shouldBroadcastConsoleToOps() {
        return false;
    }

    @Shadow
    public boolean isHost(GameProfile profile) {
        return false;
    }
}
