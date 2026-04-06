package com.Polarice3.Goety.compat.jei;

import net.fabricmc.loader.api.FabricLoader;

public enum JeiLoaded {
    JEI("jei");
    private final boolean loaded;

    JeiLoaded(String modid) {
        this.loaded = FabricLoader.getInstance().isModLoaded(modid);
    }

    public boolean isLoaded() {
        return this.loaded;
    }
}
