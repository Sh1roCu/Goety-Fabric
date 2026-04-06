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
        ModBlocks.BLOCKS.forEach(block ->
        {
            if (block instanceof PlushieBlock) {
                plushie.add(block);
            }
        });
        if (!plushie.isEmpty()) {
            for (Block block : plushie) {
                this.getOrCreateTagBuilder(ModTags.Blocks.PLUSHIE).add(block).setReplace(false);
            }
        }
    }
}
