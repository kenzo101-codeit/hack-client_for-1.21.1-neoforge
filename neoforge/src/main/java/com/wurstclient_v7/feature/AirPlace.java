package com.wurstclient_v7.feature;

import com.wurstclient_v7.config.NeoForgeConfigManager;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

@EventBusSubscriber(modid = "wurst_client_on_neoforge")
public final class AirPlace {

	private static double RANGE = 5.0;

	// === Public API for Click GUI ===
	private static boolean enabled = false;

	public static void toggle() {
		enabled = !enabled;
		NeoForgeConfigManager.setBoolean("airplace.enabled", enabled);
	}

	public static boolean isEnabled() {
		return enabled;
	}

	public static void onRightClick() {
		if (!enabled) return;

		Minecraft mc = Minecraft.getInstance();
		if (mc.player == null || mc.level == null) return;

		HitResult hit = mc.player.pick(RANGE, 0.0F, false);
		if (hit.getType() != HitResult.Type.MISS) return;

		Vec3 hitVec = hit.getLocation();
		BlockPos pos = BlockPos.containing(hitVec);

		BlockHitResult fakeHit = new BlockHitResult(hitVec, Direction.UP, pos, false);

		mc.gameMode.useItemOn(mc.player, InteractionHand.MAIN_HAND, fakeHit);
	}

	@SubscribeEvent
	public static void onRightClick(PlayerInteractEvent.RightClickItem event) {
		if (!event.getLevel().isClientSide()) return;
		AirPlace.onRightClick();
	}
}
