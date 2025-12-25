package com.wurstclient_v7.feature;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;

public final class Tracers {
    private static volatile boolean enabled = false;

    private Tracers() {}

    public static boolean isEnabled() { return enabled; }
    public static void toggle() { enabled = !enabled; }

    public static void render(Matrix4f matrix, float partialTicks) {
        if (!enabled) return;
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null || mc.level == null) return;

        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.disableDepthTest();
        RenderSystem.setShader(GameRenderer::getPositionColorShader);

        Tesselator tesselator = Tesselator.getInstance();
        BufferBuilder buffer = tesselator.begin(VertexFormat.Mode.DEBUG_LINES, DefaultVertexFormat.POSITION_COLOR);

        Vec3 cameraPos = mc.gameRenderer.getMainCamera().getPosition();
        Vec3 start = mc.player.getEyePosition(partialTicks).subtract(cameraPos);

        for (Entity entity : mc.level.entitiesForRendering()) {
            if (entity == mc.player || !(entity instanceof Player)) continue;

            // 1. Calculate Distance
            float distance = mc.player.distanceTo(entity);

            // 2. Determine Color (Green to Red)
            // 20 blocks = Red, 60+ blocks = Green
            float red = Math.min(1.0f, 1.0f - (distance - 20) / 40.0f);
            float green = Math.min(1.0f, (distance - 20) / 40.0f);

            // If they are super close (under 20 blocks), force pure Red
            if (distance < 20) {
                red = 1.0f; green = 0.0f;
            }

            Vec3 pos = entity.getPosition(partialTicks)
                    .add(0, entity.getBbHeight() / 2, 0)
                    .subtract(cameraPos);

            // First Vertex (at player)
            buffer.addVertex(matrix, (float) start.x, (float) start.y, (float) start.z)
                    .setColor(red, green, 0.0f, 1.0f);

            // Second Vertex (at target)
            buffer.addVertex(matrix, (float) pos.x, (float) pos.y, (float) pos.z)
                    .setColor(red, green, 0.0f, 1.0f);
        }

        MeshData meshData = buffer.build();
        if (meshData != null) {
            BufferUploader.drawWithShader(meshData);
        }

        RenderSystem.enableDepthTest();
        RenderSystem.disableBlend();
    }
}