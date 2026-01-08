package com.wurstclient_v7.feature;

import com.wurstclient_v7.config.ConfigManager;
import com.wurstclient_v7.mixin.GameRendererAccessor;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;

public final class LsdHack {
	private static final Minecraft MC = Minecraft.getInstance();
	private static final ResourceLocation LSD_SHADER =
			ResourceLocation.fromNamespaceAndPath("wurst_client_on_neoforge", "post/lsd.json");
	GameRendererAccessor accessor = (GameRendererAccessor) MC.gameRenderer;

	private static boolean enabled = false;

	public static void toggle() {
		enabled = !enabled;
		ConfigManager.setBoolean("lsd.enabled", enabled);
	}

	public static boolean isEnabled() {
		return enabled;
	}

	public void enable() {
		if (enabled)
			return;
		MC.execute(() -> {


			if (MC.level == null || MC.player == null)
				return;

			if (MC.gameRenderer.currentEffect() != null)
				MC.gameRenderer.shutdownEffect();

			accessor.wurst$loadEffectPublic(LSD_SHADER);
			enabled = true;
		});
	}
	public void disable() {
		if (!enabled)
			return;
		MC.execute(() -> {
			if (MC.gameRenderer.currentEffect() != null)
				MC.gameRenderer.shutdownEffect();

			enabled = false;
		});
	}
}