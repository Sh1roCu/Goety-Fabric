package cn.sh1rocu.goety.mixin.accessor;

import net.minecraft.util.random.WeightedRandomList;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.structure.StructureSpawnOverride;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(StructureSpawnOverride.class)
public interface StructureSpawnOverrideAccessor {

    @Accessor("spawns")
    WeightedRandomList<MobSpawnSettings.SpawnerData> goety$getSpawns();

    @Accessor("spawns")
    void goety$setSpawns(WeightedRandomList<MobSpawnSettings.SpawnerData> weightedRandomList);
}