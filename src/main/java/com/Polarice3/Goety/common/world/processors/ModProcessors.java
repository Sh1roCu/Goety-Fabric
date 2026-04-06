package com.Polarice3.Goety.common.world.processors;

import com.Polarice3.Goety.Goety;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;

import java.util.function.Supplier;

public class ModProcessors {

    public static final StructureProcessorType<WaterloggingStopProcessor> WATERLOGGING_STOP_PROCESSOR = register("waterlogging_stop_processor", () -> () -> WaterloggingStopProcessor.CODEC);

    public static void init() {

    }

    private static <T extends StructureProcessor> StructureProcessorType<T> register(String name, Supplier<StructureProcessorType<T>> supplier) {
        return Registry.register(BuiltInRegistries.STRUCTURE_PROCESSOR, Goety.location(name), supplier.get());
    }
}
