package com.Polarice3.Goety.data;

import com.Polarice3.Goety.common.world.structures.ModStructures;
import com.Polarice3.Goety.init.ModTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.StructureTags;
import net.minecraft.world.level.levelgen.structure.BuiltinStructures;
import net.minecraft.world.level.levelgen.structure.Structure;

import java.util.concurrent.CompletableFuture;

public class ModStructureTagsProvider extends FabricTagProvider<Structure> {

    public ModStructureTagsProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, Registries.STRUCTURE, registriesFuture);
    }

    @Override
    protected void addTags(HolderLookup.Provider p_255894_) {
        this.getOrCreateTagBuilder(ModTags.Structures.WITHER_NECROMANCER_SPAWNS)
                .add(BuiltinStructures.FORTRESS)
                .addOptional(new ResourceLocation("betterfortresses", "better_fortresses"))
                .addOptionalTag(new ResourceLocation("morevillagers", "on_fortress_explorer_maps"));
        this.getOrCreateTagBuilder(ModTags.Structures.VIZIER_SPAWNS)
                .forceAddTag(StructureTags.ON_WOODLAND_EXPLORER_MAPS)
                .addOptionalTag(new ResourceLocation("repurposed_structures", "collections/mansions"));
        this.getOrCreateTagBuilder(ModTags.Structures.CRONE_SPAWNS).addOptionalTag(ModStructures.BLIGHTED_SHACK_KEY.location());
        this.getOrCreateTagBuilder(ModTags.Structures.SKULL_LORD_SPAWNS).addOptionalTag(ModStructures.CRYPT_KEY.location());
        this.getOrCreateTagBuilder(ModTags.Structures.CRYPT).addOptionalTag(ModStructures.CRYPT_KEY.location());
        this.getOrCreateTagBuilder(ModTags.Structures.NECROMANCER_POWER).forceAddTag(ModTags.Structures.CRYPT).addOptionalTag(ModStructures.GRAVEYARD_KEY.location());
        this.getOrCreateTagBuilder(ModTags.Structures.CAN_SUMMON_BRUTES).add(BuiltinStructures.BASTION_REMNANT);
        this.getOrCreateTagBuilder(ModTags.Structures.CAN_SUMMON_WITHER_SKELETONS)
                .add(BuiltinStructures.FORTRESS)
                .addOptional(new ResourceLocation("betterfortresses", "better_fortresses"))
                .addOptionalTag(new ResourceLocation("morevillagers", "on_fortress_explorer_maps"));
        this.getOrCreateTagBuilder(ModTags.Structures.CAN_SUMMON_BORDER_WRAITHS).forceAddTag(ModTags.Structures.CRYPT);
    }
}
