package com.wurstclient_v7.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.world.entity.Entity; // Import this!
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// 1. Change T extends LivingEntity -> T extends Entity
@Mixin(EntityRenderer.class)
public abstract class EntityRendererMixin<T extends Entity> {

	@Inject(method = "render", at = @At("TAIL"), remap = false)
	private void onRender(T entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource buffer, int packedLight, CallbackInfo ci) {

		// 2. Now we cast it to LivingEntity here safely
		if (!(entity instanceof LivingEntity livingEntity)) return;

		if (!com.wurstclient_v7.feature.HealthTagsMain.isEnabled()) return;

		float health = livingEntity.getHealth();
		float max = livingEntity.getMaxHealth();
		String text = String.format("%.1f / %.1f", health, max);

		poseStack.pushPose();
		poseStack.translate(0.0, livingEntity.getBbHeight() + 0.5, 0.0);
		poseStack.mulPose(Minecraft.getInstance().getEntityRenderDispatcher().cameraOrientation());
		poseStack.scale(-0.025f, -0.025f, 0.025f);

		Font font = Minecraft.getInstance().font;
		int width = font.width(text);

		poseStack.translate(0, 0, 0.5);

		font.drawInBatch(text, -width / 2f, 0, 0xFFFFFF, false,
				poseStack.last().pose(), buffer,
				Font.DisplayMode.SEE_THROUGH, 0, packedLight);

		poseStack.popPose();
	}
}