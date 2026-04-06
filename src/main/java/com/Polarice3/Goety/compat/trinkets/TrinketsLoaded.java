package com.Polarice3.Goety.compat.trinkets;

import net.fabricmc.loader.api.FabricLoader;

public enum TrinketsLoaded {
    TRINKETS("trinkets");
    private final boolean loaded;

    TrinketsLoaded(String modid) {
        this.loaded = FabricLoader.getInstance().isModLoaded(modid);
    }

    public boolean isLoaded() {
        return this.loaded;
    }

}
