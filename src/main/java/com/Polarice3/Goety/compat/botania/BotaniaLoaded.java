package com.Polarice3.Goety.compat.botania;


import net.fabricmc.loader.api.FabricLoader;

public enum BotaniaLoaded {
    BOTANIA("botania");
    private final boolean loaded;

    BotaniaLoaded(String modid) {
        this.loaded = FabricLoader.getInstance().isModLoaded(modid);
    }

    public boolean isLoaded() {
        return this.loaded;
    }

}
