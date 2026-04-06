package com.Polarice3.Goety.compat.jade;

import net.fabricmc.loader.api.FabricLoader;

public enum JadeLoaded {
    JADE("jade");
    private final boolean loaded;

    JadeLoaded(String modid) {
        this.loaded = FabricLoader.getInstance().isModLoaded(modid);
    }

    public boolean isLoaded() {
        return this.loaded;
    }

}
