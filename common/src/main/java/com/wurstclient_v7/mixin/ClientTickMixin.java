package com.wurstclient_v7.mixin;

import com.wurstclient_v7.feature.*;
import com.wurstclient_v7.gui.ModuleScreen;
import com.wurstclient_v7.input.KeybindManager;
import net.minecraft.client.Minecraft;
import com.mojang.blaze3d.platform.InputConstants;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = Minecraft.class, remap = false)
public final class ClientTickMixin {
    private static boolean prevTogglePressed = false;
    private static boolean prevMenuPressed = false;
    private static int traceCounter = 0;
    private static boolean prevLeftPressed = false;
    private static boolean prevAutoPressed = false;
    private static boolean prevSpeedPressed = false;
    private static boolean prevFBPressed = false;
    private static boolean prevFlightPressed = false;
    private static boolean prevNoFallPressed = false;
    private static boolean prevXRayPressed = false;
    private static boolean prevJetpackPressed = false;
    private static boolean prevNukerPressed = false;
    private static boolean prevSpiderPressed = false;
    private static boolean prevESPPressed = false;
    private static boolean prevTracersPressed = false;
    private static boolean prevAndromedaPressed = false;
    private static boolean prevSafeWalkPressed = false;
    private static boolean prevGodModePressed = false;
    private static final boolean DEBUG_KEYS = false;

    @Inject(method = "tick", at = @At("TAIL"))
    private void onTick(CallbackInfo ci) {
        Minecraft mc = Minecraft.getInstance();
        if (mc == null) return;

        // periodic trace to help detect missing key events (every ~20 ticks)
        traceCounter++;
        if (DEBUG_KEYS && traceCounter % 20 == 0) {
            long win = mc.getWindow().getWindow();
            int rc = GLFW.GLFW_KEY_RIGHT_CONTROL;

            boolean glfwRc =
                    GLFW.glfwGetKey(win, rc)
                            == GLFW.GLFW_PRESS;

            boolean icRc =
                    InputConstants.isKeyDown(win, rc);

            System.out.println(
                    "[KEYTRACE] RCTRL GLFW=" + glfwRc +
                            " IC=" + icRc +
                            " bind=" + KeybindManager.getLabel("open_menu")
            );
        }
        long window = mc.getWindow().getWindow();
        boolean pressed = KeybindManager.isPressed(window, "kill_aura_toggle");
        if (pressed && !prevTogglePressed) {
            KillAura.toggle();
            System.out.println("KillAura toggled: " + (KillAura.isEnabled() ? "ON" : "OFF"));
        }
        prevTogglePressed = pressed;

        // Menu key handling
        boolean menuPressed = KeybindManager.isPressed(window, "open_menu");
        // Debug: print label and pressed state for troubleshooting
        if (menuPressed && !prevMenuPressed) {
            System.out.println("[KEYDEBUG] open_menu pressed (label=" + KeybindManager.getLabel("open_menu") + ")");
            Minecraft.getInstance().setScreen(new ModuleScreen());
        } else if (menuPressed) {
            // occasional debug when continuously pressed (helps when user reports no effect)
            System.out.println("[KEYDEBUG] open_menu held (label=" + KeybindManager.getLabel("open_menu") + ")");
        }
        prevMenuPressed = menuPressed;

        // Autoattack toggle key
        boolean aaPressed = KeybindManager.isPressed(window, "autoattack_toggle");
        if (aaPressed && !prevAutoPressed) {
            AutoAttack.toggle();
            System.out.println("AutoAttack toggled: " + (AutoAttack.isEnabled() ? "ON" : "OFF"));
        }
        prevAutoPressed = aaPressed;

        // Speedhack toggle key
        boolean shPressed = KeybindManager.isPressed(window, "speedhack_toggle");
        if (shPressed && !prevSpeedPressed) {
            SpeedHack.toggle();
        }
        prevSpeedPressed = shPressed;

        // FullBright toggle key
        boolean fbPressed = KeybindManager.isPressed(window, "fullbright_toggle");
        if (fbPressed && !prevFBPressed) {
            FullBright.toggle();
            System.out.println("FullBright toggled: " + (FullBright.isEnabled() ? "ON" : "OFF"));
        }
        prevFBPressed = fbPressed;

        // Flight toggle key
        boolean flightPressed = KeybindManager.isPressed(window, "flight_toggle");
        if (flightPressed && !prevFlightPressed) {
            Flight.toggle();
            System.out.println("Flight toggled: " + (Flight.isEnabled() ? "ON" : "OFF"));
        }
        prevFlightPressed = flightPressed;

        // NoFall toggle key
        boolean noFallPressed = KeybindManager.isPressed(window, "nofall_toggle");
        if (noFallPressed && !prevNoFallPressed) {
            NoFall.toggle();
            System.out.println("NoFall toggled: " + (NoFall.isEnabled() ? "ON" : "OFF"));
        }
        prevNoFallPressed = noFallPressed;

        // XRay toggle key
        boolean xrayPressed = KeybindManager.isPressed(window, "xray_toggle");
        if (xrayPressed && !prevXRayPressed) {
            XRay.toggle();
            System.out.println("XRay toggled: " + (XRay.isEnabled() ? "ON" : "OFF"));
        }
        prevXRayPressed = xrayPressed;

        // Jetpack toggle key
        boolean jetpackPressed = KeybindManager.isPressed(window, "jetpack_toggle");
        if (jetpackPressed && !prevJetpackPressed) {
            Jetpack.toggle();
            System.out.println("Jetpack toggled: " + (Jetpack.isEnabled() ? "ON" : "OFF"));
        }
        prevJetpackPressed = jetpackPressed;

        // Nuker toggle key
        boolean nukerPressed = KeybindManager.isPressed(window, "nuker_toggle");
        if (nukerPressed && !prevNukerPressed) {
            Nuker.toggle();
            System.out.println("Nuker toggled: " + (Nuker.isEnabled() ? "ON" : "OFF"));
        }
        prevNukerPressed = nukerPressed;

        // Spider toggle key
        boolean spiderPressed = KeybindManager.isPressed(window, "spider_toggle");
        if (spiderPressed && !prevSpiderPressed) {
            Spider.toggle();
            System.out.println("Spider toggled: " + (Spider.isEnabled() ? "ON" : "OFF"));
        }
        prevSpiderPressed = spiderPressed;

        // ESP toggle key
        boolean espPressed = KeybindManager.isPressed(window, "esp_toggle");
        if (espPressed && !prevESPPressed) {
            ESP.toggle();
            System.out.println("ESP toggled: " + (ESP.isEnabled() ? "ON" : "OFF"));
        }
        prevESPPressed = espPressed;

        // Tracers toggle key
        boolean tracersPressed = KeybindManager.isPressed(window, "tracers_toggle");
        if (tracersPressed && !prevTracersPressed) {
            Tracers.toggle();
            System.out.println("Tracers toggled: " + (Tracers.isEnabled() ? "ON" : "OFF"));
        }
        prevTracersPressed = tracersPressed;

        // Andromeda Bridge toggle key
        boolean andromedaPressed = KeybindManager.isPressed(window, "andromeda_toggle");
        if (andromedaPressed && !prevAndromedaPressed) {
            AndromedaBridge.toggle();
            System.out.println("Andromeda Bridge toggled: " + (AndromedaBridge.isEnabled() ? "ON" : "OFF"));
        }
        prevAndromedaPressed = andromedaPressed;

        boolean swPressed = KeybindManager.isPressed(window, "safewalk_toggle");
        if (swPressed && !prevSafeWalkPressed) {
            SafeWalk.toggle();
        }
        prevSafeWalkPressed = swPressed;

        // Mouse left click handling (for autoattack)
        boolean leftPressed = InputConstants.isKeyDown(window, GLFW.GLFW_MOUSE_BUTTON_LEFT);
        if (leftPressed && !prevLeftPressed) {
            AutoAttack.onLeftClick();
        }
        prevLeftPressed = leftPressed;

        // SafeWalk Execution Logic
        if (SafeWalk.isEnabled()) {
            if (mc.player != null && mc.player.onGround()) {
                mc.options.keyShift.setDown(true);
            }
        } else {
            mc.options.keyShift.setDown(false);
        }


        // Call feature tick handler
        KillAura.onClientTick();
        // SpeedHack tick handler (applies boosts when movement begins)
        SpeedHack.onClientTick();
        FullBright.onClientTick();
        Flight.onClientTick();
        NoFall.onClientTick();
        Jetpack.onClientTick();
        Nuker.onClientTick();
        Spider.onClientTick();
        AndromedaBridge.onClientTick();
    }
}