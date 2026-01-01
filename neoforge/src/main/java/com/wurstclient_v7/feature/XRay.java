package com.wurstclient_v7.feature;

import com.wurstclient_v7.config.NeoForgeConfigManager;
import net.minecraft.client.Minecraft;

public final class XRay {
    private static boolean enabled = NeoForgeConfigManager.getBoolean("xray.enabled", false);

    private XRay() { }

    public static boolean isEnabled() { return enabled; }

    public static void toggle() {
        enabled = !enabled;
        // Save the new state immediately
        NeoForgeConfigManager.setBoolean("xray.enabled", enabled);
        Minecraft mc = Minecraft.getInstance();
        if (mc != null) {
            // Automatically sync FullBright so you can actually see the ores
            if (enabled && !FullBright.isEnabled()) {
                FullBright.toggle();
            }
            mc.levelRenderer.allChanged();
        }
    }
}
