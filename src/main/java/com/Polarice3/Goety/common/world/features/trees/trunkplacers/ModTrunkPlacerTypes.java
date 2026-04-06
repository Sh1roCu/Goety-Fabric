package com.Polarice3.Goety.common.world.features.trees.trunkplacers;

import com.Polarice3.Goety.Goety;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;

import java.util.function.Supplier;

public class ModTrunkPlacerTypes {

    public static final TrunkPlacerType<ChorusTrunkPlacer> CHORUS_TRUNK_PLACER = register("chorus_trunk_placer", () -> new TrunkPlacerType<>(ChorusTrunkPlacer.CODEC));

    public static void init() {

    }

    private static <T extends TrunkPlacer> TrunkPlacerType<T> register(String name, Supplier<TrunkPlacerType<T>> supplier) {
        return Registry.register(BuiltInRegistries.TRUNK_PLACER_TYPE, Goety.location(name), supplier.get());
    }
}
