package com.Polarice3.Goety.common.blocks;

import com.Polarice3.Goety.Goety;
import com.Polarice3.Goety.init.ModSoundTypes;
import net.fabricmc.fabric.api.object.builder.v1.block.type.BlockSetTypeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.block.state.properties.BlockSetType;

public class ModBlockSetType {
    public static final BlockSetType HAUNTED = register(Goety.location("haunted"), new BlockSetTypeBuilder());
    public static final BlockSetType ROTTEN = register(Goety.location("rotten"), new BlockSetTypeBuilder());
    public static final BlockSetType WINDSWEPT = register(Goety.location("windswept"), new BlockSetTypeBuilder());
    public static final BlockSetType PINE = register(Goety.location("pine"), new BlockSetTypeBuilder());
    public static final BlockSetType CHORUS = register(Goety.location("chorus"), new BlockSetTypeBuilder());
    public static final BlockSetType CORRUPT_CHORUS = register(Goety.location("corrupt_chorus"), new BlockSetTypeBuilder());
    public static final BlockSetType MOD_METAL = register(Goety.location("metal"), new BlockSetTypeBuilder()
            .openableByHand(false)
            .soundGroup(ModSoundTypes.MOD_METAL)
            .doorCloseSound(SoundEvents.IRON_DOOR_CLOSE)
            .doorOpenSound(SoundEvents.IRON_DOOR_OPEN)
            .trapdoorCloseSound(SoundEvents.IRON_DOOR_CLOSE)
            .trapdoorOpenSound(SoundEvents.IRON_DOOR_OPEN)
            .pressurePlateClickOffSound(SoundEvents.METAL_PRESSURE_PLATE_CLICK_OFF)
            .pressurePlateClickOnSound(SoundEvents.METAL_PRESSURE_PLATE_CLICK_ON)
            .buttonClickOffSound(SoundEvents.STONE_BUTTON_CLICK_OFF)
            .buttonClickOnSound(SoundEvents.STONE_BUTTON_CLICK_ON)
    );

    private static BlockSetType register(ResourceLocation id, BlockSetTypeBuilder builder) {
        return builder.register(id);
    }
}
