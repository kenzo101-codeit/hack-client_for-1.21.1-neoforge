package com.wurstclient_v7.feature;

import com.wurstclient_v7.config.ConfigManager;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.client.event.ClientTickEvent;

public final class JesusHack {

	private static boolean enabled = false;

	public static void toggle() {
		enabled = !enabled;
		ConfigManager.setBoolean("jesus.enabled", enabled);
	}

	public static boolean isEnabled() {
		return enabled;
	}

	public static void onClientTick(ClientTickEvent.Post ClientTickEvent) {
		if (!enabled) return;

		Minecraft mc = Minecraft.getInstance();
		Player player = mc.player;
		if (player == null) return;

		// Sneaking disables Jesus (quality-of-life)
		if (player.isShiftKeyDown()) return;

		FluidState fluidBelow = player.level()
				.getFluidState(player.blockPosition().below());

		// Check if standing over water
		if (fluidBelow.getType() == Fluids.WATER) {
			Vec3 motion = player.getDeltaMovement();

			// Stop sinking + gentle lift
			if (motion.y < 0) {
				player.setDeltaMovement(motion.x, 0.08, motion.z);
			}

			// Prevent swimming animation
			player.setSwimming(false);
		}
	}
}
