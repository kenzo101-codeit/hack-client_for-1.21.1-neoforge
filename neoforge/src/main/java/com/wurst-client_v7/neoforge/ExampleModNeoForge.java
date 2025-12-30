package com.wurst-client_v7.neoforge;

import net.neoforged.fml.common.Mod;

import com.wurst-client_v7.ExampleMod;

@Mod(ExampleMod.MOD_ID)
public final class ExampleModNeoForge {
    public ExampleModNeoForge() {
        // Run our common setup.
        ExampleMod.init();
    }
}
