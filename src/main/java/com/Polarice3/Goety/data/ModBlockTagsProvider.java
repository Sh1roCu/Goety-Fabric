package com.Polarice3.Goety.data;

import com.Polarice3.Goety.common.blocks.ModBlocks;
import com.Polarice3.Goety.common.blocks.PlushieBlock;
import com.Polarice3.Goety.init.ModTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.level.block.Block;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;

public class ModBlockTagsProvider extends FabricTagProvider.BlockTagProvider {

    public ModBlockTagsProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void addTags(HolderLookup.Provider p_255894_) {
        Collection<Block> plushie = new ArrayList<>();
        Collection<Block> ominous = new ArrayList<>();
        ModBlocks.BLOCKS.forEach(block ->
        {
            if (block instanceof PlushieBlock) {
                plushie.add(block);
            }
            if (block.getDescriptionId().contains("ominous_stone")) {
                ominous.add(block);
            }
        });
        if (!plushie.isEmpty()) {
            for (Block block : plushie) {
                this.getOrCreateTagBuilder(ModTags.Blocks.PLUSHIE).add(block).setReplace(false);
            }
        }
        if (!ominous.isEmpty()) {
            ominous.add(ModBlocks.OMINOUS_PYRE);
            ominous.add(ModBlocks.OMINOUS_IDOL);
            ominous.add(ModBlocks.WALL_SHRINE);
            ominous.add(ModBlocks.MANDALA);
            ominous.add(ModBlocks.OMINOUS_STATUE);
            ominous.add(ModBlocks.OMINOUS_BRAZIER_STATUE);
            for (Block block : ominous){
                this.getOrCreateTagBuilder(ModTags.Blocks.OMINOUS_BLOCKS).add(block).setReplace(false);
            }
        }
    }
}
