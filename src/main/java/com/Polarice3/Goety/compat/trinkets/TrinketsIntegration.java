package com.Polarice3.Goety.compat.trinkets;

import com.Polarice3.Goety.common.items.ModItems;
import com.Polarice3.Goety.common.items.curios.SingleStackItem;
import com.Polarice3.Goety.compat.ICompatable;
import com.google.common.collect.ImmutableMap;
import dev.emi.trinkets.api.TrinketsApi;
import net.minecraft.world.item.Item;

import java.util.Map;

public class TrinketsIntegration implements ICompatable {

    private static final Map<Item, String> TYPES = ImmutableMap.<Item, String>builder()
            .put(ModItems.RING_OF_WANT, "ring")
            .put(ModItems.RING_OF_THIRST, "ring")
            .put(ModItems.RING_OF_FORCE, "ring")
            .put(ModItems.RING_OF_THE_FORGE, "ring")
            .put(ModItems.RING_OF_THE_DRAGON, "ring")
            .put(ModItems.DARK_HAT, "head")
            .put(ModItems.GRAND_TURBAN, "head")
            .put(ModItems.FROST_CROWN, "head")
            .put(ModItems.WIND_CROWN, "head")
            .put(ModItems.STORM_CROWN, "head")
            .put(ModItems.WILD_CROWN, "head")
            .put(ModItems.ABYSS_CROWN, "head")
            .put(ModItems.VOID_CROWN, "head")
            .put(ModItems.NETHER_CROWN, "head")
            .put(ModItems.WITCH_HAT, "head")
            .put(ModItems.WITCH_HAT_HEDGE, "head")
            .put(ModItems.CRONE_HAT, "head")
            .put(ModItems.UNHOLY_HAT, "head")
            .put(ModItems.UNHOLY_HAT_HALO, "head")
            .put(ModItems.NECRO_CROWN, "head")
            .put(ModItems.NAMELESS_CROWN, "head")
            .put(ModItems.TARGETING_MONOCLE, "head")
            .put(ModItems.AMETHYST_NECKLACE, "necklace")
            .put(ModItems.PENDANT_OF_HUNGER, "necklace")
            .put(ModItems.STAR_AMULET, "necklace")
            .put(ModItems.SEA_AMULET, "necklace")
            .put(ModItems.FELINE_AMULET, "necklace")
            .put(ModItems.DARK_ROBE, "body")
            .put(ModItems.DARK_ROBE_FANCY, "body")
            .put(ModItems.GRAND_ROBE, "body")
            .put(ModItems.GEO_ROBE, "body")
            .put(ModItems.FROST_ROBE, "body")
            .put(ModItems.FROST_ROBE_CRYO, "body")
            .put(ModItems.WIND_ROBE, "body")
            .put(ModItems.STORM_ROBE, "body")
            .put(ModItems.WILD_ROBE, "body")
            .put(ModItems.ABYSS_ROBE, "body")
            .put(ModItems.VOID_ROBE, "body")
            .put(ModItems.NETHER_ROBE, "body")
            .put(ModItems.NETHER_ROBE_WARPED, "body")
            .put(ModItems.ILLUSION_ROBE, "body")
            .put(ModItems.ILLUSION_ROBE_MIRROR, "body")
            .put(ModItems.WITCH_ROBE, "body")
            .put(ModItems.WITCH_ROBE_HEDGE, "body")
            .put(ModItems.WARLOCK_ROBE, "body")
            .put(ModItems.WARLOCK_ROBE_DARK, "body")
            .put(ModItems.UNHOLY_ROBE, "body")
            .put(ModItems.NECRO_CAPE, "back")
            .put(ModItems.NAMELESS_CAPE, "back")
            .put(ModItems.GRAVE_GLOVE, "hands")
            .put(ModItems.THRASH_GLOVE, "hands")
            .put(ModItems.TOTEM_OF_ROOTS, "charm")
            .put(ModItems.TOTEM_OF_SOULS, "charm")
            .put(ModItems.ALARMING_CHARM, "charm")
            .put(ModItems.OMINOUS_CHARM, "charm")
            .put(ModItems.FOCUS_BAG, "belt")
            .put(ModItems.FOCUS_PACK, "belt")
            .put(ModItems.BREW_BAG, "belt")
            .put(ModItems.WARLOCK_SASH, "belt")
            .put(ModItems.WAYFARERS_BELT, "belt")
            .put(ModItems.SPITEFUL_BELT, "belt")
            .build();

    public void setup() {
        registerCapabilities();
    }

    private void registerCapabilities() {
        // auto registered

//        TYPES.keySet().forEach(entry -> {
//            if (entry instanceof SingleStackItem item) {
//                TrinketsApi.registerTrinket(item, new SingleStackItem());
//            }
//        });
    }

}
