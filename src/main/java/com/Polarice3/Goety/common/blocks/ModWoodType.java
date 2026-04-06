package com.Polarice3.Goety.common.blocks;

import com.Polarice3.Goety.Goety;
import net.fabricmc.fabric.api.object.builder.v1.block.type.WoodTypeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.WoodType;

public class ModWoodType {
    public static final WoodType HAUNTED = register(Goety.location("haunted"), ModBlockSetType.HAUNTED);
    public static final WoodType ROTTEN = register(Goety.location("rotten"), ModBlockSetType.ROTTEN);
    public static final WoodType WINDSWEPT = register(Goety.location("windswept"), ModBlockSetType.WINDSWEPT);
    public static final WoodType PINE = register(Goety.location("pine"), ModBlockSetType.PINE);
    public static final WoodType CHORUS = register(Goety.location("chorus"), ModBlockSetType.CHORUS);
    public static final WoodType CORRUPT_CHORUS = register(Goety.location("corrupt_chorus"), ModBlockSetType.CORRUPT_CHORUS);

    public static void init() {

    }

    private static WoodType register(ResourceLocation id, BlockSetType setType) {
        return new WoodTypeBuilder().register(id, setType);
    }
}
