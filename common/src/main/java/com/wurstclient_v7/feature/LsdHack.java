package com.wurstclient_v7.feature;

import com.wurstclient_v7.config.ConfigManager;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;

public final class LsdHack {

	private static final Minecraft MC = Minecraft.getInstance();
	private static final ResourceLocation LSD_SHADER =
			ResourceLocation.fromNamespaceAndPath(
					"wurst_client_on_neoforge",
					"post_effect/lsd.json"
			);

	private static boolean enabled = false;

	public static void toggle() {
		if (enabled) {
			disable();
		} else {
			enable();
		}
	}

	public static boolean isEnabled() {
		return enabled;
	}

	public static void enable() {
		if (enabled) return;
		if (MC.level == null || MC.player == null) return;

		// Ensure no other shader is active
		MC.gameRenderer.shutdownEffect();

		// ✅ Correct modern call
		MC.gameRenderer.enablePostEffect(LSD_SHADER);

		enabled = true;
		ConfigManager.setBoolean("lsd.enabled", true);
	}

	public static void disable() {
		if (!enabled) return;

		MC.gameRenderer.shutdownEffect();

		enabled = false;
		ConfigManager.setBoolean("lsd.enabled", false);
	}
}
