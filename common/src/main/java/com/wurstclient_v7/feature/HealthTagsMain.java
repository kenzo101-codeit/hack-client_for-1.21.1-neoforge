package com.wurstclient_v7;

import com.wurstclient_v7.config.NeoForgeConfigManager;
import com.wurstclient_v7.client.HealthTagClientCache;
import com.wurstclient_v7.net.HealthTagPayloads;
import com.wurstclient_v7.server.HealthTagBroadcaster;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;

@Mod(HealthTagsMain.MODID)
public final class HealthTagsMain {
	public static final String MODID = "wurst_client_on_neoforge";

	// Toggle state for the Click GUI
	private static boolean enabled = NeoForgeConfigManager.getBoolean("healthtags.enabled", false);

	// FIXED: Constructor name matches class name
	public HealthTagsMain(IEventBus modEventBus) {
		// Register networking
		modEventBus.addListener(this::registerNetworking);

		// Register server broadcaster
		HealthTagBroadcaster.init();
	}

	private void registerNetworking(RegisterPayloadHandlersEvent event) {
		HealthTagPayloads.register(event, HealthTagClientCache.CLIENT_HANDLER);
	}

	// === Public API for Click GUI ===
	public static void toggle() {
		enabled = !enabled;
		NeoForgeConfigManager.setBoolean("healthtags.enabled", enabled);
		System.out.println("HealthTags: " + (enabled ? "ON" : "OFF"));
	}

	public static boolean isEnabled() {
		return enabled;
	}
}