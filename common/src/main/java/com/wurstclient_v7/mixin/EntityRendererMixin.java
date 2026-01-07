package com.wurstclient_v7.mixin;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityRenderer.class)
public abstract class EntityRendererMixin<T extends LivingEntity, M extends EntityModel<T>> {

	@Inject(
			method = "render(Lnet/minecraft/world/entity/LivingEntity;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V",
			at = @At("TAIL")
	)
	private void addHealthTag(LivingEntity entity, float yaw, float partialTicks,
	                          PoseStack poseStack, MultiBufferSource buffer, int packedLight,
	                          CallbackInfo ci) {
		if (!(entity instanceof LivingEntity living))
			return;

		// Use your helper class
		Component newComponent = HealthTagsHack.addHealth(living, component);

		((EntityRenderer<T>) (Object) this).renderNameTag(entity, newComponent,
				poseStack, buffer, packedLight, partialTicks);

		ci.cancel();
	}
}
