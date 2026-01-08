package com.wurstclient_v7.feature;

import com.wurstclient_v7.config.ConfigManager;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

public final class AirPlace {

	private static boolean enabled = false;
	private static double RANGE = 5.0;

	public static void toggle() {
		enabled = !enabled;
		ConfigManager.setBoolean("airplace.enabled", enabled);
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

	public static void onRightClick(PlayerInteractEvent.RightClickItem event) {
		if (!event.getLevel().isClientSide()) return;
		AirPlace.onRightClick();
	}
}
