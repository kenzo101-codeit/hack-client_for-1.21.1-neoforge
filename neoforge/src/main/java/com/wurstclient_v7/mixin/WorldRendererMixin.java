package com.wurstclient_v7.mixin;

import com.wurstclient_v7.feature.Tracers;
import net.minecraft.client.renderer.LevelRenderer;
import com.mojang.blaze3d.vertex.PoseStack;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LevelRenderer.class)
public class WorldRendererMixin {
    @Inject(method = "renderLevel", at = @At("HEAD"))
    private void onRenderLevel(
            net.minecraft.client.DeltaTracker deltaTracker,
            boolean renderBlockOutline,
            net.minecraft.client.Camera camera,
            net.minecraft.client.renderer.GameRenderer gameRenderer,
            net.minecraft.client.renderer.LightTexture lightTexture,
            org.joml.Matrix4f frustumMatrix,
            org.joml.Matrix4f projectionMatrix,
            org.spongepowered.asm.mixin.injection.callback.CallbackInfo ci
    ) {
        float partialTicks = deltaTracker.getGameTimeDeltaPartialTick(true);

        Tracers.render(frustumMatrix, partialTicks);
    }
}
