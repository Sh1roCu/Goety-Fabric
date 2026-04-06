package com.Polarice3.Goety.compat.cataclysm;

import net.fabricmc.loader.api.FabricLoader;

public enum CataclysmLoaded {
    CATACLYSM("cataclysm");
    private final boolean loaded;

    CataclysmLoaded(String modid) {
        this.loaded = FabricLoader.getInstance().isModLoaded(modid);
    }

    public boolean isLoaded() {
        return this.loaded;
    }

}
