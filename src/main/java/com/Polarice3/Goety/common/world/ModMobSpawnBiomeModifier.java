package com.Polarice3.Goety.common.world;

import com.Polarice3.Goety.Goety;
import net.fabricmc.fabric.api.biome.v1.BiomeModification;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;

public class ModMobSpawnBiomeModifier {

    public static void init() {
        BiomeModification mobSpawnsModification = BiomeModifications.create(Goety.location("mob_spawns"));
        ModLevelRegistry.addBiomeSpawns(mobSpawnsModification);
    }
}
