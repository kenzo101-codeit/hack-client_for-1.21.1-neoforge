package com.wurstclient_v7.feature;

import com.wurstclient_v7.feature.HealthTagsMain;
import com.wurstclient_v7.config.NeoForgeConfigManager;

import java.util.LinkedHashMap;
import java.util.Map;

public final class ModuleRegistry {
	public static final Map<String, ModuleToggle> MODULES = new LinkedHashMap<>();

	static {
		MODULES.put("Kill-Aura", KillAura::isEnabled);
		MODULES.put("Auto-Attack", AutoAttack::isEnabled);
		MODULES.put("Speed-Hack", SpeedHack::isEnabled);
		MODULES.put("Full-Bright", FullBright::isEnabled);
		MODULES.put("Flight", Flight::isEnabled);
		MODULES.put("NoFall", NoFall::isEnabled);
		MODULES.put("XRay", XRay::isEnabled);
		MODULES.put("Jetpack", Jetpack::isEnabled);
		MODULES.put("Nuker", Nuker::isEnabled);
		MODULES.put("Spider", Spider::isEnabled);
		MODULES.put("ESP", ESP::isEnabled);
		MODULES.put("Tracers", Tracers::isEnabled);
		MODULES.put("Andromeda Bridge", AndromedaBridge::isEnabled);
		MODULES.put("SafeWalk", SafeWalk::isEnabled);
		MODULES.put("GodMode", GodMode::isEnabled);
		MODULES.put("Freecam", Freecam::isEnabled);
		MODULES.put("LSD", LsdHack::isEnabled);      // FIXED
		MODULES.put("Jesus", JesusHack::isEnabled);  // FIXED
		MODULES.put("Glide", Glide::isEnabled);
		MODULES.put("Air-Place", AirPlace::isEnabled);
		MODULES.put("Boat-Fly", BoatFly::isEnabled);
		MODULES.put("Health Tags", HealthTagsMain::isEnabled);
	}

	public interface ModuleToggle {
		boolean isEnabled();
	}
}