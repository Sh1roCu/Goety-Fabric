package com.Polarice3.Goety.common.world.structures;

import com.Polarice3.Goety.Goety;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;

import java.util.function.Supplier;

public class ModStructureTypes {

    public static StructureType<BiggerJigsawStructure> BIGGER_JIGSAW_STRUCTURE = register("bigger_jigsaw", () -> () -> BiggerJigsawStructure.CODEC);
    public static StructureType<CryptStructure> CRYPT_STRUCTURE = register("crypt", () -> () -> CryptStructure.CODEC);
    public static StructureType<FinalTerminalStructure> FINAL_TERMINAL_STRUCTURE = register("final_terminal", () -> () -> FinalTerminalStructure.CODEC);

    public static void init() {

    }

    private static <T extends Structure> StructureType<T> register(String name, Supplier<StructureType<T>> supplier) {
        return Registry.register(BuiltInRegistries.STRUCTURE_TYPE, Goety.location(name), supplier.get());
    }
}
