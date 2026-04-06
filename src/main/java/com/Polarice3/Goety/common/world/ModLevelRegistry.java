package com.Polarice3.Goety.common.world;

import cn.sh1rocu.goety.mixin.accessor.StructureAccessor;
import cn.sh1rocu.goety.mixin.accessor.StructureSettingsAccessor;
import cn.sh1rocu.goety.mixin.accessor.StructureSpawnOverrideAccessor;
import com.Polarice3.Goety.common.entities.ModEntityType;
import com.Polarice3.Goety.config.MobsConfig;
import com.Polarice3.Goety.init.ModTags;
import net.fabricmc.fabric.api.biome.v1.BiomeModification;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectionContext;
import net.fabricmc.fabric.api.biome.v1.ModificationPhase;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.random.WeightedRandomList;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureSpawnOverride;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

public class ModLevelRegistry {

    public static void addBiomeSpawns(BiomeModification modification) {
        var ADD = ModificationPhase.ADDITIONS;
        var rootPredicate = new Predicate<BiomeSelectionContext>() {
            @Override
            public boolean test(BiomeSelectionContext selectionContext) {
                return !selectionContext.hasTag(ModTags.Biomes.COMMON_BLACKLIST) &&
                        !selectionContext.getBiomeRegistryEntry().is(biomeResourceKey -> biomeResourceKey.registry().getNamespace().contains("alexscaves"));
            }
        };

        // part1
        modification
                .add(ADD, rootPredicate
                                .and(biome -> biome.getBiomeRegistryEntry().is(ModTags.Biomes.REAPER_SPAWN))
                                .and(biome -> !biome.getBiomeRegistryEntry().is(ModTags.Biomes.REAPER_EXCLUDE_SPAWN))
                                .and(biome -> MobsConfig.ReaperSpawnWeight.get() > 0),
                        builder -> builder.getSpawnSettings()
                                .addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(ModEntityType.REAPER, MobsConfig.ReaperSpawnWeight.get(), MobsConfig.ReaperSpawnMinCount.get(), MobsConfig.ReaperSpawnMaxCount.get())))
                .add(ADD, rootPredicate
                                .and(biome -> biome.getBiomeRegistryEntry().is(ModTags.Biomes.WRAITH_SPAWN))
                                .and(biome -> !biome.getBiomeRegistryEntry().is(ModTags.Biomes.WRAITH_EXCLUDE_SPAWN))
                                .and(biome -> MobsConfig.WraithSpawnWeight.get() > 0),
                        builder -> builder.getSpawnSettings()
                                .addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(ModEntityType.WRAITH, MobsConfig.WraithSpawnWeight.get(), MobsConfig.WraithSpawnMinCount.get(), MobsConfig.WraithSpawnMaxCount.get())))
                .add(ADD, rootPredicate
                                .and(biome -> biome.getBiomeRegistryEntry().is(ModTags.Biomes.MUCK_WRAITH_SPAWN))
                                .and(biome -> !biome.getBiomeRegistryEntry().is(ModTags.Biomes.MUCK_WRAITH_EXCLUDE_SPAWN))
                                .and(biome -> MobsConfig.MuckWraithSpawnWeight.get() > 0),
                        builder -> builder.getSpawnSettings()
                                .addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(ModEntityType.MUCK_WRAITH, MobsConfig.MuckWraithSpawnWeight.get(), MobsConfig.MuckWraithSpawnMinCount.get(), MobsConfig.MuckWraithSpawnMaxCount.get()))
                ).add(ADD, rootPredicate
                                .and(biome -> biome.getBiomeRegistryEntry().is(ModTags.Biomes.WEB_SPIDER_SPAWN))
                                .and(biome -> !biome.getBiomeRegistryEntry().is(ModTags.Biomes.WEB_SPIDER_EXCLUDE_SPAWN))
                                .and(biome -> MobsConfig.WebSpiderSpawnWeight.get() > 0),
                        builder -> builder.getSpawnSettings()
                                .addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(ModEntityType.WEB_SPIDER, MobsConfig.WebSpiderSpawnWeight.get(), MobsConfig.WebSpiderSpawnMinCount.get(), MobsConfig.WebSpiderSpawnMaxCount.get())))
                .add(ADD, rootPredicate
                                .and(biome -> biome.getBiomeRegistryEntry().is(ModTags.Biomes.ICY_SPIDER_SPAWN))
                                .and(biome -> !biome.getBiomeRegistryEntry().is(ModTags.Biomes.ICY_SPIDER_EXCLUDE_SPAWN))
                                .and(biome -> MobsConfig.IcySpiderSpawnWeight.get() > 0),
                        builder -> builder.getSpawnSettings()
                                .addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(ModEntityType.ICY_SPIDER, MobsConfig.IcySpiderSpawnWeight.get(), MobsConfig.IcySpiderSpawnMinCount.get(), MobsConfig.IcySpiderSpawnMaxCount.get())))
                .add(ADD, rootPredicate
                                .and(biome -> biome.getBiomeRegistryEntry().is(ModTags.Biomes.FRAYED_SPAWN))
                                .and(biome -> !biome.getBiomeRegistryEntry().is(ModTags.Biomes.FRAYED_EXCLUDE_SPAWN))
                                .and(biome -> MobsConfig.FrayedSpawnWeight.get() > 0),
                        builder -> builder.getSpawnSettings()
                                .addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(ModEntityType.FRAYED, MobsConfig.FrayedSpawnWeight.get(), MobsConfig.FrayedSpawnMinCount.get(), MobsConfig.FrayedSpawnMaxCount.get())))
                .add(ADD, rootPredicate
                                .and(biome -> biome.getBiomeRegistryEntry().is(ModTags.Biomes.RATTLED_SPAWN))
                                .and(biome -> !biome.getBiomeRegistryEntry().is(ModTags.Biomes.RATTLED_EXCLUDE_SPAWN))
                                .and(biome -> MobsConfig.RattledSpawnWeight.get() > 0),
                        builder -> builder.getSpawnSettings()
                                .addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(ModEntityType.RATTLED, MobsConfig.RattledSpawnWeight.get(), MobsConfig.RattledSpawnMinCount.get(), MobsConfig.RattledSpawnMaxCount.get())))
                .add(ADD, rootPredicate
                                .and(biome -> biome.getBiomeRegistryEntry().is(ModTags.Biomes.NECROMANCER_SPAWN))
                                .and(biome -> !biome.getBiomeRegistryEntry().is(ModTags.Biomes.NECROMANCER_EXCLUDE_SPAWN))
                                .and(biome -> MobsConfig.NecromancerSpawnWeight.get() > 0),
                        builder -> builder.getSpawnSettings()
                                .addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(ModEntityType.NECROMANCER, MobsConfig.NecromancerSpawnWeight.get(), MobsConfig.NecromancerSpawnMinCount.get(), MobsConfig.NecromancerSpawnMaxCount.get())))
                .add(ADD, rootPredicate
                                .and(biome -> biome.getBiomeRegistryEntry().is(ModTags.Biomes.WARLOCK_SPAWN))
                                .and(biome -> !biome.getBiomeRegistryEntry().is(ModTags.Biomes.WARLOCK_EXCLUDE_SPAWN))
                                .and(biome -> MobsConfig.WarlockSpawnWeight.get() > 0),
                        builder -> builder.getSpawnSettings()
                                .addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(ModEntityType.WARLOCK, MobsConfig.WarlockSpawnWeight.get(), MobsConfig.WarlockSpawnMinCount.get(), MobsConfig.WarlockSpawnMaxCount.get())))
                .add(ADD, rootPredicate
                                .and(biome -> biome.getBiomeRegistryEntry().is(ModTags.Biomes.HERETIC_SPAWN))
                                .and(biome -> !biome.getBiomeRegistryEntry().is(ModTags.Biomes.HERETIC_EXCLUDE_SPAWN))
                                .and(biome -> MobsConfig.HereticSpawnWeight.get() > 0),
                        builder -> builder.getSpawnSettings()
                                .addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(ModEntityType.HERETIC, MobsConfig.HereticSpawnWeight.get(), MobsConfig.HereticSpawnMinCount.get(), MobsConfig.HereticSpawnMaxCount.get())))
                .add(ADD, rootPredicate
                                .and(biome -> biome.getBiomeRegistryEntry().is(ModTags.Biomes.MAVERICK_SPAWN))
                                .and(biome -> !biome.getBiomeRegistryEntry().is(ModTags.Biomes.MAVERICK_EXCLUDE_SPAWN))
                                .and(biome -> MobsConfig.MaverickSpawnWeight.get() > 0),
                        builder -> builder.getSpawnSettings()
                                .addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(ModEntityType.MAVERICK, MobsConfig.MaverickSpawnWeight.get(), MobsConfig.MaverickSpawnMinCount.get(), MobsConfig.MaverickSpawnMaxCount.get())));
        // part2
        var rootPredicate2 = new Predicate<BiomeSelectionContext>() {
            @Override
            public boolean test(BiomeSelectionContext selectionContext) {
                Holder<Biome> biome = selectionContext.getBiomeRegistryEntry();
                return biome.is(Biomes.SOUL_SAND_VALLEY) || biome.is(new ResourceLocation("netherexp:black_ice_glaciers"));
            }
        };

        modification
                .add(ADD, rootPredicate2
                                .and(biome -> MobsConfig.ReaperSpawnWeight.get() > 0)
                                .and(biome -> !biome.getBiomeRegistryEntry().is(ModTags.Biomes.REAPER_EXCLUDE_SPAWN)),
                        builder -> {
                            var settings = builder.getSpawnSettings();
                            settings.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(ModEntityType.REAPER, MobsConfig.ReaperSpawnWeight.get(), MobsConfig.ReaperSpawnMinCount.get(), MobsConfig.ReaperSpawnMaxCount.get()));
                            settings.setSpawnCost(ModEntityType.REAPER, 0.7D, 0.15D);
                        })
                .add(ADD, rootPredicate2
                                .and(biome -> MobsConfig.WraithSpawnWeight.get() > 0)
                                .and(biome -> !biome.getBiomeRegistryEntry().is(ModTags.Biomes.WRAITH_EXCLUDE_SPAWN)),
                        builder -> {
                            var settings = builder.getSpawnSettings();
                            settings.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(ModEntityType.WRAITH, MobsConfig.WraithSpawnWeight.get(), MobsConfig.WraithSpawnMinCount.get(), MobsConfig.WraithSpawnMaxCount.get()));
                            settings.setSpawnCost(ModEntityType.WRAITH, 0.7D, 0.15D);
                        });

    }

    public static boolean startName(ResourceKey<Biome> biomeResourceKey, String string) {
        return biomeResourceKey.registry().getNamespace().startsWith(string);
    }

    public static boolean containsName(ResourceKey<Biome> biomeResourceKey, String string) {
        return biomeResourceKey.registry().getNamespace().contains(string);
    }

    public static void addStructureSpawns(Holder<Structure> structure) {
        if (MobsConfig.NecromancerSpawnStructure.get() && structure.is(ModTags.Structures.NECROMANCER_SPAWN) && MobsConfig.NecromancerSpawnWeight.get() > 0) {
            var newSpawns = new MobSpawnSettings.SpawnerData(ModEntityType.NECROMANCER, MobsConfig.NecromancerSpawnWeight.get(), MobsConfig.NecromancerSpawnMinCount.get(), MobsConfig.NecromancerSpawnMaxCount.get());

            Structure.StructureSettings settings = ((StructureAccessor) structure.value()).goety$getSettings();
            Map<MobCategory, StructureSpawnOverride> overrides = new HashMap<>(settings.spawnOverrides());
            WeightedRandomList<MobSpawnSettings.SpawnerData> weightedRandomList = overrides.get(MobCategory.MONSTER).spawns();
            List<MobSpawnSettings.SpawnerData> spawns = new ArrayList<>(weightedRandomList.unwrap());
            spawns.add(newSpawns);

            ((StructureSpawnOverrideAccessor) (Object) overrides.get(MobCategory.MONSTER)).goety$setSpawns(WeightedRandomList.create(spawns));
            ((StructureSettingsAccessor) (Object) settings).goety$setSpawnOverrides(overrides);
            ((StructureAccessor) structure.value()).goety$setSettings(settings);
        }
    }
}
