package com.Polarice3.Goety.compat.patchouli;

import net.fabricmc.loader.api.FabricLoader;

public enum PatchouliLoaded {
    PATCHOULI("patchouli");
    private final boolean loaded;

    PatchouliLoaded(String modid) {
        this.loaded = FabricLoader.getInstance().isModLoaded(modid);
    }

    public boolean isLoaded() {
        return this.loaded;
    }

}
