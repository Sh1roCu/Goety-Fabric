package com.Polarice3.Goety.common.world.placements;

import com.Polarice3.Goety.Goety;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.structure.placement.StructurePlacement;
import net.minecraft.world.level.levelgen.structure.placement.StructurePlacementType;

import java.util.function.Supplier;

public class ModPlacementType {

    public static final StructurePlacementType<ModRandomSpread> MOD_RANDOM_SPREAD = register("random_spread", () -> () -> ModRandomSpread.CODEC);
    public static final StructurePlacementType<ModMinorRandomSpread> MOD_MINOR_RANDOM_SPREAD = register("minor_random_spread", () -> () -> ModMinorRandomSpread.CODEC);

    public static void init() {

    }

    private static <T extends StructurePlacement> StructurePlacementType<T> register(String name, Supplier<StructurePlacementType<T>> supplier) {
        return Registry.register(BuiltInRegistries.STRUCTURE_PLACEMENT, Goety.location(name), supplier.get());
    }

}
