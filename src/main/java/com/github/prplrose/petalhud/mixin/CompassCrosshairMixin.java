package com.github.prplrose.petalhud.mixin;

import com.github.prplrose.petalhud.PetalHUD;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public class CompassCrosshairMixin {

    @Shadow @Final private MinecraftClient client;

    @Redirect(method = "renderCrosshair", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/systems/RenderSystem;blendFuncSeparate(Lcom/mojang/blaze3d/platform/GlStateManager$SrcFactor;Lcom/mojang/blaze3d/platform/GlStateManager$DstFactor;Lcom/mojang/blaze3d/platform/GlStateManager$SrcFactor;Lcom/mojang/blaze3d/platform/GlStateManager$DstFactor;)V"))
    void addColor(GlStateManager.SrcFactor srcFactor, GlStateManager.DstFactor dstFactor, GlStateManager.SrcFactor srcAlpha, GlStateManager.DstFactor dstAlpha){

        Entity camera = client.cameraEntity;
        if (camera==null){
            PetalHUD.LOGGER.error("camera is null");
            return;
        }
        float pitch = camera.getPitch();
        float yaw = camera.getYaw();

        Vec3d x = new Vec3d(0,1,1);
        Vec3d z = new Vec3d(0.5,0.5,1);
        Vec3d y = new Vec3d(0,1,0);

        double yawCos =  -MathHelper.cos(yaw * MathHelper.RADIANS_PER_DEGREE);
        double yawSin =  -MathHelper.sin(yaw * MathHelper.RADIANS_PER_DEGREE);
        double pSin =  MathHelper.sin(pitch * MathHelper.RADIANS_PER_DEGREE);
        float pACos = MathHelper.abs(MathHelper.cos(pitch*MathHelper.RADIANS_PER_DEGREE));

        RenderSystem.setShaderColor(
                getColor(x.x, yawSin)/* *pACos*/ + /*getColor(y.x, pSin) + */getColor(z.x, yawCos)/* *pACos*/,
                getColor(x.y, yawSin)/* *pACos*/ + /*getColor(y.y, pSin) + */getColor(z.y, yawCos)/* *pACos*/,
                getColor(x.z, yawSin)/* *pACos*/ + /*getColor(y.z, pSin) + */getColor(z.z, yawCos)/* *pACos*/,
                1f
        );
    }

    @Inject(method = "renderCrosshair", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/systems/RenderSystem;defaultBlendFunc()V", shift = At.Shift.AFTER))
    void resetColor(DrawContext context, CallbackInfo ci){

        RenderSystem.setShaderColor(1f,1f,1f,1f);
    }

    @Unique
    float getColor(double color, double coefficient){
        return MathHelper.abs((float)((color-(coefficient+1.0)/2.0)*coefficient));
    }

}
