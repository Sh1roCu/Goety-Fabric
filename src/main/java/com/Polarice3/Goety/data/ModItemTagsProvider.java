package com.Polarice3.Goety.data;

import com.Polarice3.Goety.common.blocks.PlushieBlock;
import com.Polarice3.Goety.common.items.ModItems;
import com.Polarice3.Goety.common.items.magic.MagicFocus;
import com.Polarice3.Goety.init.ModTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;

public class ModItemTagsProvider extends FabricTagProvider.ItemTagProvider {

    public ModItemTagsProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> completableFuture) {
        super(output, completableFuture);
    }

    @Override
    protected void addTags(HolderLookup.Provider p_255894_) {
        this.getOrCreateTagBuilder(ModTags.Items.WANDS).add(ModItems.DARK_WAND)
                .addTag(ModTags.Items.STAFFS).setReplace(false);
        this.getOrCreateTagBuilder(ModTags.Items.STAFFS).add(ModItems.NECRO_STAFF,
                ModItems.NAMELESS_STAFF,
                ModItems.OMINOUS_STAFF,
                ModItems.FROST_STAFF,
                ModItems.WILD_STAFF,
                ModItems.WIND_STAFF,
                ModItems.STORM_STAFF,
                ModItems.GEO_STAFF,
                ModItems.ABYSS_STAFF,
                ModItems.VOID_STAFF,
                ModItems.NETHER_STAFF).setReplace(false);
        this.getOrCreateTagBuilder(ModTags.Items.ROBES).add(ModItems.DARK_ROBE,
                ModItems.DARK_ROBE_FANCY,
                ModItems.GRAND_ROBE,
                ModItems.ILLUSION_ROBE,
                ModItems.ILLUSION_ROBE_MIRROR,
                ModItems.GEO_ROBE,
                ModItems.FROST_ROBE,
                ModItems.FROST_ROBE_CRYO,
                ModItems.WIND_ROBE,
                ModItems.STORM_ROBE,
                ModItems.WILD_ROBE,
                ModItems.ABYSS_ROBE,
                ModItems.VOID_ROBE,
                ModItems.WITCH_ROBE,
                ModItems.WITCH_ROBE_HEDGE,
                ModItems.WARLOCK_ROBE,
                ModItems.WARLOCK_ROBE_DARK,
                ModItems.NETHER_ROBE,
                ModItems.NETHER_ROBE_WARPED,
                ModItems.UNHOLY_ROBE).setReplace(false);
        this.getOrCreateTagBuilder(ModTags.Items.CAPES).add(ModItems.NECRO_CAPE,
                ModItems.NAMELESS_CAPE).setReplace(false);
        this.getOrCreateTagBuilder(ModTags.Items.CROWNS).add(ModItems.NECRO_CROWN,
                ModItems.NAMELESS_CROWN,
                ModItems.FROST_CROWN,
                ModItems.WIND_CROWN,
                ModItems.STORM_CROWN,
                ModItems.WILD_CROWN,
                ModItems.ABYSS_CROWN,
                ModItems.VOID_CROWN,
                ModItems.NETHER_CROWN,
                ModItems.DARK_HAT,
                ModItems.GRAND_TURBAN,
                ModItems.UNHOLY_HAT,
                ModItems.UNHOLY_HAT_HALO).setReplace(false);
        Collection<Item> focuses = new ArrayList<>();
        Collection<Item> plushie = new ArrayList<>();
        ModItems.ITEMS.forEach(item ->
        {
            if (item instanceof MagicFocus) {
                focuses.add(item);
            }
            if (item instanceof BlockItem item1 && item1.getBlock() instanceof PlushieBlock) {
                plushie.add(item);
            }
        });
        if (!focuses.isEmpty()) {
            for (Item item : focuses) {
                this.getOrCreateTagBuilder(ModTags.Items.FOCUSES).add(item).setReplace(false);
            }
        }
        if (!plushie.isEmpty()) {
            for (Item item : plushie) {
                this.getOrCreateTagBuilder(ModTags.Items.PLUSHIE).add(item).setReplace(false);
            }
        }
    }
}
