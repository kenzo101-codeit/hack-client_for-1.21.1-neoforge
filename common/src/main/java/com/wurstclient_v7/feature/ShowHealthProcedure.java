package com.wurstclient_v7.feature;

import com.wurstclient_v7.config.ConfigManager;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.event.tick.EntityTickEvent;

public final class ShowHealthProcedure {

	public static void onEntityTick(EntityTickEvent.Pre EntityEvent) {
		Entity entity = EntityEvent.getEntity();
		if (!(entity instanceof LivingEntity living)) return;

		float health = living.getHealth();
		float max = living.getMaxHealth();

		// Format with one decimal place
		String text = String.format("%s : %.1f / %.1f",
				BuiltInRegistries.ENTITY_TYPE.getKey(entity.getType()).toString(),
				health, max);

		living.setCustomName(Component.literal(text));
		living.setCustomNameVisible(true);
	}
}
