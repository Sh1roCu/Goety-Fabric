package com.Polarice3.Goety.common.world.processors;

import com.Polarice3.Goety.Goety;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;

import java.util.function.Supplier;

public class ModProcessors {

    public static final StructureProcessorType<WaterloggingStopProcessor> WATERLOGGING_STOP_PROCESSOR = register("waterlogging_stop_processor", () -> () -> WaterloggingStopProcessor.CODEC);
    public static final StructureProcessorType<CobbleAgeProcessor> COBBLE_AGE_PROCESSOR = register("cobble_age_processor", () -> () -> CobbleAgeProcessor.CODEC);
    public static final StructureProcessorType<ReplaceProcessor> REPLACE_PROCESSOR = register("replace_processor", () -> () -> ReplaceProcessor.CODEC);
    public static final StructureProcessorType<HauntProcessor> HAUNT_PROCESSOR = register("haunt_processor", () -> () -> HauntProcessor.CODEC);
    public static final StructureProcessorType<WreckProcessor> WRECK_PROCESSOR = register("wreck_processor", () -> () -> WreckProcessor.CODEC);
    public static final StructureProcessorType<RotPotProcessor> ROT_POT_PROCESSOR = register("rot_pot_processor", () -> () -> RotPotProcessor.CODEC);
    public static final StructureProcessorType<RotFarmProcessor> ROT_FARM_PROCESSOR = register("rot_farm_processor", () -> () -> RotFarmProcessor.CODEC);

    public static void init() {

    }

    private static <T extends StructureProcessor> StructureProcessorType<T> register(String name, Supplier<StructureProcessorType<T>> supplier) {
        return Registry.register(BuiltInRegistries.STRUCTURE_PROCESSOR, Goety.location(name), supplier.get());
    }
}
