package com.wurstclient_v7.feature;

import com.wurstclient_v7.config.NeoForgeConfigManager;

public final class SafeWalk {
    // Load the initial state from your config file
    private static boolean enabled = NeoForgeConfigManager.getBoolean("safewalk.enabled", false);

    public static boolean isEnabled() { return enabled; }

    public static void toggle() {
        enabled = !enabled;
        // Save the new state immediately
        NeoForgeConfigManager.setBoolean("safewalk.enabled", enabled);
        System.out.println("SafeWalk: " + (enabled ? "ON" : "OFF"));
    }
}