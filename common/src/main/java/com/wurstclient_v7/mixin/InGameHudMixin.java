package com.wurstclient_v7.mixin;

import com.wurstclient_v7.config.ConfigManager;
import com.wurstclient_v7.feature.ModuleRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.DeltaTracker;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

import static org.lwjgl.glfw.GLFW.*;

@Mixin(value = Gui.class, remap = false)
public class InGameHudMixin {

    @Inject(method = "render", at = @At("TAIL"))
    private void onRender(GuiGraphics guiGraphics, DeltaTracker deltaTracker, CallbackInfo ci) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.options.hideGui || mc.player == null) return;

        int x = 5;
        int y = 5;
        int color = 0xFF00FF00;

        // Draw Watermark
        guiGraphics.drawString(mc.font, "My Hack Client For 1.21.1 NeoForge v1.0", x, y, 0xFFFFFFFF, true);
        y += 12;

        // Input HUD
        long window = mc.getWindow().getWindow();
        int pressedColor = 0x59FF0000; // translucent red
        int idleColor = 0x59808080;    // translucent gray

        int baseX = 10;
        int baseY = 80;
        int boxW = 40;
        int boxH = 20;
        int spacing = 5;

// W on top
        drawKeyBox(guiGraphics, mc.font, window, "W", GLFW_KEY_W,
                baseX + boxW + spacing, baseY, false, pressedColor, idleColor);

// A S D row
        drawKeyBox(guiGraphics, mc.font, window, "A", GLFW_KEY_A,
                baseX, baseY + boxH + spacing, false, pressedColor, idleColor);

        drawKeyBox(guiGraphics, mc.font, window, "S", GLFW_KEY_S,
                baseX + boxW + spacing, baseY + boxH + spacing, false, pressedColor, idleColor);

        drawKeyBox(guiGraphics, mc.font, window, "D", GLFW_KEY_D,
                baseX + (boxW + spacing) * 2, baseY + boxH + spacing, false, pressedColor, idleColor);

// SPACE below
        drawKeyBox(guiGraphics, mc.font, window, "SPACE", GLFW_KEY_SPACE,
                baseX, baseY + (boxH + spacing) * 2, false, pressedColor, idleColor);

        //MOUSE BUTTONS
        drawKeyBox(guiGraphics, mc.font, window, "MOUSE_L", GLFW_MOUSE_BUTTON_LEFT,
                baseX + (boxW + spacing) * 3, baseY, true, pressedColor, idleColor);

        drawKeyBox(guiGraphics, mc.font, window, "MOUSE_R", GLFW_MOUSE_BUTTON_RIGHT,
                baseX + (boxW + spacing) * 3, baseY + boxH + spacing, true, pressedColor, idleColor);

        String[] modules = {
                "AndromedaBridge", "AutoAttack", "ESP", "Flight",
                "FullBright", "Jetpack", "KillAura",
                "NoFall", "Nuker", "SpeedHack", "Spider", "Tracers", "XRay", "SafeWalk", "GodMode", "Glide", "Freecam", "LSD", "Jesus", "AirPlace", "BoatFly"
        };
        for (Map.Entry<String, ModuleRegistry.ModuleToggle> entry : ModuleRegistry.MODULES.entrySet()) {
            if (entry.getValue().isEnabled()) {
                guiGraphics.drawString(mc.font, "[+] " + entry.getKey(), x, y, color, true);
                y += 10;
            }
        }
    }
    private void drawKeyBox(GuiGraphics guiGraphics, Font font, long window,
                            String label, int key, int x, int y, boolean isMouse,
                            int pressedColor, int idleColor) {
        boolean pressed;
        if (isMouse) {
            pressed = glfwGetMouseButton(window, key) == GLFW_PRESS;
        } else {
            pressed = glfwGetKey(window, key) == GLFW_PRESS;
        }

        int boxColor = pressed ? pressedColor : idleColor;

        // Draw rectangle (width=40, height=20 for example)
        guiGraphics.fill(x, y, x + 40, y + 20, boxColor);

        // Draw label centered inside
        int textWidth = font.width(label);
        int textX = x + (40 - textWidth) / 2;
        int textY = y + (20 - font.lineHeight) / 2;
        guiGraphics.drawString(font, label, textX, textY, 0xFFFFFFFF, true);
    }
}