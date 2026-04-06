package com.Polarice3.Goety.compat.minecolonies;

import net.fabricmc.loader.api.FabricLoader;

public enum MinecoloniesLoaded {
    MINECOLONIES("minecolonies");
    private final boolean loaded;

    MinecoloniesLoaded(String modid) {
        this.loaded = FabricLoader.getInstance().isModLoaded(modid);
    }

    public boolean isLoaded() {
        return this.loaded;
    }

}
