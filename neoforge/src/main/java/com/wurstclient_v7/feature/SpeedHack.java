package com.wurstclient_v7.feature;

import com.wurstclient_v7.config.NeoForgeConfigManager;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;

public final class SpeedHack {

    private static boolean enabled =
            NeoForgeConfigManager.getBoolean("speed.enabled", false);

    public static boolean isEnabled() {
        return enabled;
    }

    public static void toggle() {
        enabled = !enabled;
        NeoForgeConfigManager.setBoolean("speed.enabled", enabled);
    }

    public static void onClientTick() {
        if (!enabled) return;

        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null || !mc.player.onGround()) return;

        Player player = mc.player;

        if (player.xxa != 0 || player.zza != 0) {
            double multiplier =
                    NeoForgeConfigManager.getDouble("speed.multiplier", 1.5);

            player.setDeltaMovement(
                    player.getDeltaMovement().multiply(multiplier, 1.0, multiplier)
            );
        }
    }
}
