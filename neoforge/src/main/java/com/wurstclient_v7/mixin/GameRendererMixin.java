package com.wurstclient_v7.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(GameRenderer.class)
public abstract class GameRendererMixin implements GameRendererAccessor {

	@Shadow
	@Final
	Minecraft minecraft;

	// Access the private field
	@Shadow
	private boolean effectActive;

	// INVOKER for the private method
	@Invoker(value = "loadEffect", remap = false)
	protected abstract void wurst$invokeLoadEffect(ResourceLocation resourceLocation);

	@Override
	public void wurst$loadEffectPublic(ResourceLocation resourceLocation) {
		wurst$invokeLoadEffect(resourceLocation);
	}

	@Override
	public boolean wurst$isEffectActive() {
		return effectActive;
	}

	@Override
	public void wurst$disableEffect() {
		if (effectActive) {
			wurst$invokeLoadEffect(null);
		}
	}
}
