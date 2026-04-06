package com.Polarice3.Goety.common.world.features;

import com.Polarice3.Goety.Goety;
import com.Polarice3.Goety.common.world.features.configs.ModTreeFeatureConfig;
import com.Polarice3.Goety.common.world.features.trees.features.ChorusTreeFeature;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NetherForestVegetationConfig;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;

import java.util.function.Supplier;

public class ModFeatures {

    public static final Feature<ModTreeFeatureConfig> CHORUS_TREE = register("chorus_tree", () -> new ChorusTreeFeature(ModTreeFeatureConfig.CODEC, false));
    public static final Feature<ModTreeFeatureConfig> CHORUS_VOID_TREE = register("chorus_void_tree", () -> new ChorusTreeFeature(ModTreeFeatureConfig.CODEC, true));
    public static final Feature<NetherForestVegetationConfig> END_VEGETATION = register("end_vegetation", () -> new EndVegetationFeature(NetherForestVegetationConfig.CODEC));
    public static final Feature<NetherForestVegetationConfig> END_GROWTH = register("end_growth", () -> new EndGrowthFeature(NetherForestVegetationConfig.CODEC));
    public static final Feature<NetherForestVegetationConfig> END_CHORUS = register("end_chorus", () -> new EndChorusFeature(NetherForestVegetationConfig.CODEC));
    public static final Feature<RandomPatchConfiguration> SINGLE_PATCH = register("single_patch", () -> new SinglePatchFeature(RandomPatchConfiguration.CODEC));

    public static void init() {

    }

    private static <T extends FeatureConfiguration> Feature<T> register(String name, Supplier<Feature<T>> supplier) {
        return Registry.register(BuiltInRegistries.FEATURE, Goety.location(name), supplier.get());
    }
}
