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

    private Tracers() {
    }

    public static boolean isEnabled() {
        return enabled;
    }

    public static void toggle() {
        enabled = !enabled;
        System.out.println("Tracers is now " + (enabled ? "ENABLED" : "DISABLED"));
    }

    public static void render(Matrix4f matrix, float partialTicks) {
        if (!enabled) return;
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null || mc.level == null) return;

        RenderSystem.enableBlend();
        RenderSystem.disableDepthTest();
        RenderSystem.setShader(GameRenderer::getPositionColorShader);

        Tesselator tesselator = Tesselator.getInstance();
        // 1.21.1 uses this new build process
        BufferBuilder buffer = tesselator.begin(VertexFormat.Mode.DEBUG_LINES, DefaultVertexFormat.POSITION_COLOR);

        Vec3 cameraPos = mc.gameRenderer.getMainCamera().getPosition();
        Vec3 start = mc.player.getEyePosition(partialTicks).subtract(cameraPos);

        for (Entity entity : mc.level.entitiesForRendering()) {
            if (entity == mc.player || !(entity instanceof Player)) continue;

            Vec3 pos = entity.getPosition(partialTicks).add(0, entity.getBbHeight() / 2, 0).subtract(cameraPos);

            // 1.21.1 syntax: addVertex(matrix, x, y, z)
            buffer.addVertex(matrix, (float) start.x, (float) start.y, (float) start.z).setColor(0f, 1f, 0f, 1f);
            buffer.addVertex(matrix, (float) pos.x, (float) pos.y, (float) pos.z).setColor(0f, 1f, 0f, 1f);
        }

        MeshData meshData = buffer.build();
        if (meshData != null) {
            BufferUploader.drawWithShader(meshData);
        }
        RenderSystem.enableDepthTest();
    }
}