package com.Polarice3.Goety.compat.serene_seasons;

import net.fabricmc.loader.api.FabricLoader;

public enum SSeasonsLoaded {
    SERENE_SEASONS("sereneseasons");
    private final boolean loaded;

    SSeasonsLoaded(String modid) {
        this.loaded = FabricLoader.getInstance().isModLoaded(modid);
    }

    public boolean isLoaded() {
        return this.loaded;
    }

}
