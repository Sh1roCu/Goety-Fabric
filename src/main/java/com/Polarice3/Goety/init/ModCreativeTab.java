package com.Polarice3.Goety.init;

import com.Polarice3.Goety.Goety;
import com.Polarice3.Goety.common.blocks.ModBlocks;
import com.Polarice3.Goety.common.items.ModItems;
import com.Polarice3.Goety.compat.patchouli.PatchouliIntegration;
import com.Polarice3.Goety.compat.patchouli.PatchouliLoaded;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.decoration.Painting;
import net.minecraft.world.entity.decoration.PaintingVariant;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

import java.util.Comparator;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class ModCreativeTab {

    public static final CreativeModeTab TAB = register(Goety.MOD_ID, () -> FabricItemGroup.builder()
            .icon(ModItems.TOTEM_OF_SOULS::getDefaultInstance)
            .title(Component.translatable("itemGroup.goety"))
            .displayItems((parameters, output) -> {
                if (PatchouliLoaded.PATCHOULI.isLoaded()) {
                    output.accept(PatchouliIntegration.getBlackBook());
                    output.accept(PatchouliIntegration.getWitchesBrew());
                }
                output.accept(ModItems.TOTEM_OF_SOULS.getEmptyTotem());
                output.accept(ModItems.TOTEM_OF_SOULS.getFilledTotem());
                output.accept(ModItems.TOTEM_OF_ROOTS.getEmptyTotem());
                output.accept(ModItems.TOTEM_OF_ROOTS.getFilledTotem());
                ModItems.ITEMS.forEach(i -> {
                    if (!ModItems.shouldSkipCreativeModTab(i) && !(i instanceof BlockItem) && !ModItems.isFocus(i)) {
                        output.accept(i);
                    }
                });
                parameters.holders().lookup(Registries.PAINTING_VARIANT).ifPresent((p_270026_) -> {
                    generatePresetPaintings(output, p_270026_, (holder) -> {
                        return holder.is(ModTags.Paintings.MODDED_PAINTINGS);
                    }, CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
                });
                ModItems.SPAWN_EGGS.forEach(output::accept);
            }).build());

    public static final CreativeModeTab BLOCK_TAB = register(Goety.MOD_ID + "_block", () -> FabricItemGroup.builder()
            .icon(() -> ModBlocks.SHADE_STONE_CHISELED_BLOCK.asItem().getDefaultInstance())
            .title(Component.translatable("itemGroup.goety.block"))
            .displayItems((parameters, output) -> {
                ModItems.ITEMS.forEach(i -> {
                    if (i instanceof BlockItem) {
                        output.accept(i);
                    }
                });
            }).build());

    public static final CreativeModeTab FOCUS_TAB = register(Goety.MOD_ID + "_focus", () -> FabricItemGroup.builder()
            .icon(ModItems.FOCUS_BAG::getDefaultInstance)
            .title(Component.translatable("itemGroup.goety.focus"))
            .displayItems((parameters, output) -> {
                ModItems.ITEMS.forEach(i -> {
                    if (ModItems.isFocus(i)) {
                        output.accept(i);
                    }
                });
            }).build());

    public static final CreativeModeTab SERVANT_TAB = register(Goety.MOD_ID + "_servants", () -> FabricItemGroup.builder()
            .icon(ModItems.SOUL_JAR::getDefaultInstance)
            .title(Component.translatable("itemGroup.goety.servant"))
            .displayItems((parameters, output) -> {
                ModItems.SERVANT_SPAWN_EGGS.forEach(output::accept);
            }).build());

    private static final Comparator<Holder<PaintingVariant>> PAINTING_COMPARATOR = Comparator.comparing(Holder::value, Comparator.<PaintingVariant>comparingInt((p_270004_) -> {
        return p_270004_.getHeight() * p_270004_.getWidth();
    }).thenComparing(PaintingVariant::getWidth));

    private static void generatePresetPaintings(CreativeModeTab.Output p_271007_, HolderLookup.RegistryLookup<PaintingVariant> p_270618_, Predicate<Holder<PaintingVariant>> p_270878_, CreativeModeTab.TabVisibility p_270261_) {
        p_270618_.listElements().filter(p_270878_).sorted(PAINTING_COMPARATOR).forEach((p_269979_) -> {
            ItemStack itemstack = new ItemStack(ModItems.HAUNTED_PAINTING);
            CompoundTag compoundtag = itemstack.getOrCreateTagElement("EntityTag");
            Painting.storeVariant(compoundtag, p_269979_);
            p_271007_.accept(itemstack, p_270261_);
        });
    }

    public static void init() {

    }

    private static CreativeModeTab register(String name, Supplier<CreativeModeTab> supplier) {
        return Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, Goety.location(name), supplier.get());
    }
}
