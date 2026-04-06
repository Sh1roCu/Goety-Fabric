package com.Polarice3.Goety.common.world;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.core.registries.Registries;

public class ModMobSpawnStructureModifier {

    public static void init() {
        ServerLifecycleEvents.SERVER_STARTED.register((server) -> {
            for (var holder : server.registryAccess().registryOrThrow(Registries.STRUCTURE).holders().toList()) {
                ModLevelRegistry.addStructureSpawns(holder);
            }
        });
    }
}
