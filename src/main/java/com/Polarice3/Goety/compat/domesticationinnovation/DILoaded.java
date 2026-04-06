package com.Polarice3.Goety.compat.domesticationinnovation;

import net.fabricmc.loader.api.FabricLoader;

public enum DILoaded {
    DOMESTICATION_INNOVATION("domesticationinnovation");
    private final boolean loaded;

    DILoaded(String modid) {
        this.loaded = FabricLoader.getInstance().isModLoaded(modid);
    }

    public boolean isLoaded() {
        return this.loaded;
    }
}
