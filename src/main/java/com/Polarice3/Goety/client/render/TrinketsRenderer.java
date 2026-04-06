package com.Polarice3.Goety.client.render;

import com.Polarice3.Goety.Goety;
import com.Polarice3.Goety.client.render.model.*;
import com.Polarice3.Goety.common.blocks.PlushieBlock;
import com.Polarice3.Goety.common.items.ModItems;
import dev.emi.trinkets.api.client.TrinketRendererRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;

public class TrinketsRenderer {
    public static String folderPath = "textures/models/curios/";

    public static ResourceLocation render(String textureName) {
        return Goety.location(folderPath + textureName);
    }

    public static void register() {
        TrinketRendererRegistry.registerRenderer(ModItems.DARK_HAT, new WearRenderer(render("dark_hat.png"), () -> new DarkHatModel(bakeLayer(ModModelLayer.DARK_HAT))));
        TrinketRendererRegistry.registerRenderer(ModItems.GRAND_TURBAN, new WearRenderer(render("grand_turban.png"), () -> new DarkHatModel(bakeLayer(ModModelLayer.GRAND_TURBAN))));
        TrinketRendererRegistry.registerRenderer(ModItems.FROST_CROWN, new WearRenderer(render("frost_crown.png"), () -> new DarkHatModel(bakeLayer(ModModelLayer.IRON_CROWN))));
        TrinketRendererRegistry.registerRenderer(ModItems.WIND_CROWN, new WearRenderer(render("wind_crown.png"), () -> new DarkHatModel(bakeLayer(ModModelLayer.IRON_CROWN))));
        TrinketRendererRegistry.registerRenderer(ModItems.STORM_CROWN, new WearRenderer(render("storm_crown.png"), () -> new DarkHatModel(bakeLayer(ModModelLayer.IRON_CROWN))));
        TrinketRendererRegistry.registerRenderer(ModItems.WILD_CROWN, new WearRenderer(render("wild_crown.png"), () -> new DarkHatModel(bakeLayer(ModModelLayer.IRON_CROWN))));
        TrinketRendererRegistry.registerRenderer(ModItems.ABYSS_CROWN, new WearRenderer(render("abyss_crown.png"), () -> new DarkHatModel(bakeLayer(ModModelLayer.IRON_CROWN))));
        TrinketRendererRegistry.registerRenderer(ModItems.VOID_CROWN, new WearRenderer(render("void_crown.png"), () -> new DarkHatModel(bakeLayer(ModModelLayer.IRON_CROWN))));
        TrinketRendererRegistry.registerRenderer(ModItems.NETHER_CROWN, new WearRenderer(render("nether_crown.png"), () -> new DarkHatModel(bakeLayer(ModModelLayer.IRON_CROWN))));
        TrinketRendererRegistry.registerRenderer(ModItems.WITCH_HAT, new WearRenderer(render("witch_hat.png"), () -> new WitchHatModel(bakeLayer(ModModelLayer.WITCH_HAT))));
        TrinketRendererRegistry.registerRenderer(ModItems.WITCH_HAT_HEDGE, new WearRenderer(render("witch_hat_hedge.png"), () -> new WitchHatModel(bakeLayer(ModModelLayer.WITCH_HAT))));
        TrinketRendererRegistry.registerRenderer(ModItems.CRONE_HAT, new WearRenderer(render("crone_hat.png"), () -> new WitchHatModel(bakeLayer(ModModelLayer.CRONE_HAT))));
        TrinketRendererRegistry.registerRenderer(ModItems.UNHOLY_HAT, new WearRenderer(render("unholy_hat.png"), () -> new UnholyHatModel(bakeLayer(ModModelLayer.UNHOLY_HAT))));
        TrinketRendererRegistry.registerRenderer(ModItems.UNHOLY_HAT_HALO, new WearRenderer(render("unholy_hat_halo.png"), () -> new UnholyHatModel(bakeLayer(ModModelLayer.UNHOLY_HAT))));
        TrinketRendererRegistry.registerRenderer(ModItems.DARK_ROBE, new WearRenderer(render("dark_robe.png"), () -> new DarkRobeModel(bakeLayer(ModModelLayer.DARK_ROBE))));
        TrinketRendererRegistry.registerRenderer(ModItems.DARK_ROBE_FANCY, new WearRenderer(render("dark_robe_fancy.png"), () -> new DarkRobeModel(bakeLayer(ModModelLayer.DARK_ROBE))));
        TrinketRendererRegistry.registerRenderer(ModItems.GRAND_ROBE, new WearRenderer(render("grand_robe.png"), () -> new DarkRobeModel(bakeLayer(ModModelLayer.GRAND_ROBE))));
        TrinketRendererRegistry.registerRenderer(ModItems.ILLUSION_ROBE, new WearRenderer(render("illusion_robe.png"), () -> new DarkRobeModel(bakeLayer(ModModelLayer.DARK_ROBE))));
        TrinketRendererRegistry.registerRenderer(ModItems.ILLUSION_ROBE_MIRROR, new WearRenderer(render("illusion_robe_mirror.png"), () -> new DarkRobeModel(bakeLayer(ModModelLayer.DARK_ROBE))));
        TrinketRendererRegistry.registerRenderer(ModItems.GEO_ROBE, new WearRenderer(render("geo_robe.png"), () -> new DarkRobeModel(bakeLayer(ModModelLayer.DARK_ROBE))));
        TrinketRendererRegistry.registerRenderer(ModItems.FROST_ROBE, new WearRenderer(render("frost_robe.png"), () -> new DarkRobeModel(bakeLayer(ModModelLayer.DARK_ROBE))));
        TrinketRendererRegistry.registerRenderer(ModItems.FROST_ROBE_CRYO, new WearRenderer(render("frost_robe_cryo.png"), () -> new DarkRobeModel(bakeLayer(ModModelLayer.DARK_ROBE))));
        TrinketRendererRegistry.registerRenderer(ModItems.WIND_ROBE, new WearRenderer(render("wind_robe.png"), () -> new DarkRobeModel(bakeLayer(ModModelLayer.DARK_ROBE))));
        TrinketRendererRegistry.registerRenderer(ModItems.STORM_ROBE, new WearRenderer(render("storm_robe.png"), () -> new DarkRobeModel(bakeLayer(ModModelLayer.DARK_ROBE))));
        TrinketRendererRegistry.registerRenderer(ModItems.WILD_ROBE, new WearRenderer(render("wild_robe.png"), () -> new DarkRobeModel(bakeLayer(ModModelLayer.DARK_ROBE))));
        TrinketRendererRegistry.registerRenderer(ModItems.ABYSS_ROBE, new WearRenderer(render("abyss_robe.png"), () -> new DarkRobeModel(bakeLayer(ModModelLayer.DARK_ROBE))));
        TrinketRendererRegistry.registerRenderer(ModItems.VOID_ROBE, new WearRenderer(render("void_robe.png"), () -> new DarkRobeModel(bakeLayer(ModModelLayer.DARK_ROBE))));
        TrinketRendererRegistry.registerRenderer(ModItems.NECRO_CROWN, new WearRenderer(render("necro_cape.png"), () -> new NecroCapeModel<>(bakeLayer(ModModelLayer.NECRO_CROWN))));
        TrinketRendererRegistry.registerRenderer(ModItems.NECRO_CAPE, new WearRenderer(render("necro_cape.png"), () -> new NecroCapeModel<>(bakeLayer(ModModelLayer.NECRO_CAPE))));
        TrinketRendererRegistry.registerRenderer(ModItems.NAMELESS_CROWN, new WearRenderer(render("nameless_cape.png"), () -> new NecroCapeModel<>(bakeLayer(ModModelLayer.NAMELESS_CROWN))));
        TrinketRendererRegistry.registerRenderer(ModItems.NAMELESS_CAPE, new WearRenderer(render("nameless_cape.png"), () -> new NecroCapeModel<>(bakeLayer(ModModelLayer.NECRO_CAPE))));
        TrinketRendererRegistry.registerRenderer(ModItems.WITCH_ROBE, new WearRenderer(render("witch_robe.png"), () -> new DarkRobeModel(bakeLayer(ModModelLayer.DARK_ROBE))));
        TrinketRendererRegistry.registerRenderer(ModItems.WITCH_ROBE_HEDGE, new WearRenderer(render("witch_robe_hedge.png"), () -> new DarkRobeModel(bakeLayer(ModModelLayer.DARK_ROBE))));
        TrinketRendererRegistry.registerRenderer(ModItems.WARLOCK_ROBE, new WearRenderer(render("warlock_robe.png"), () -> new DarkRobeModel(bakeLayer(ModModelLayer.DARK_ROBE))));
        TrinketRendererRegistry.registerRenderer(ModItems.WARLOCK_ROBE_DARK, new WearRenderer(render("warlock_robe_dark.png"), () -> new DarkRobeModel(bakeLayer(ModModelLayer.DARK_ROBE))));
        TrinketRendererRegistry.registerRenderer(ModItems.WARLOCK_SASH, new WearRenderer(render("warlock_sash.png"), () -> new DarkRobeModel(bakeLayer(ModModelLayer.DARK_ROBE))));
        TrinketRendererRegistry.registerRenderer(ModItems.NETHER_ROBE, new WearRenderer(render("nether_robe.png"), () -> new DarkRobeModel(bakeLayer(ModModelLayer.DARK_ROBE))));
        TrinketRendererRegistry.registerRenderer(ModItems.NETHER_ROBE_WARPED, new WearRenderer(render("nether_robe_warped.png"), () -> new DarkRobeModel(bakeLayer(ModModelLayer.DARK_ROBE))));
        TrinketRendererRegistry.registerRenderer(ModItems.UNHOLY_ROBE, new WearRenderer(render("unholy_robe.png"), () -> new DarkRobeModel(bakeLayer(ModModelLayer.DARK_ROBE))));
        TrinketRendererRegistry.registerRenderer(ModItems.PENDANT_OF_HUNGER, new WearRenderer(render("pendant_of_hunger.png"), () -> new MiscCuriosModel(bakeLayer(ModModelLayer.AMULET))));
        TrinketRendererRegistry.registerRenderer(ModItems.SEA_AMULET, new WearRenderer(render("sea_amulet.png"), () -> new MiscCuriosModel(bakeLayer(ModModelLayer.AMULET))));
        TrinketRendererRegistry.registerRenderer(ModItems.STAR_AMULET, new WearRenderer(render("star_amulet.png"), () -> new MiscCuriosModel(bakeLayer(ModModelLayer.AMULET))));
        TrinketRendererRegistry.registerRenderer(ModItems.WAYFARERS_BELT, new WearRenderer(render("wayfarers_belt.png"), () -> new MiscCuriosModel(bakeLayer(ModModelLayer.BELT))));
        TrinketRendererRegistry.registerRenderer(ModItems.FELINE_AMULET, new WearRenderer(render("feline_amulet.png"), () -> new MiscCuriosModel(bakeLayer(ModModelLayer.AMULET))));
        TrinketRendererRegistry.registerRenderer(ModItems.SPITEFUL_BELT, new WearRenderer(render("spiteful_belt.png"), () -> new MiscCuriosModel(bakeLayer(ModModelLayer.BELT))));
        TrinketRendererRegistry.registerRenderer(ModItems.FOCUS_BAG, new WearRenderer(render("focus_bag.png"), () -> new MiscCuriosModel(bakeLayer(ModModelLayer.FOCUS_BAG))));
        TrinketRendererRegistry.registerRenderer(ModItems.BREW_BAG, new WearRenderer(render("brew_bag.png"), () -> new MiscCuriosModel(bakeLayer(ModModelLayer.BREW_BAG))));
        TrinketRendererRegistry.registerRenderer(ModItems.AMETHYST_NECKLACE, new WearRenderer(render("amethyst_necklace.png"), () -> new MiscCuriosModel(bakeLayer(ModModelLayer.AMETHYST_NECKLACE))));
        TrinketRendererRegistry.registerRenderer(ModItems.TARGETING_MONOCLE, new WearRenderer(render("targeting_monocle.png"), () -> new MiscCuriosModel(bakeLayer(ModModelLayer.MONOCLE))));
        TrinketRendererRegistry.registerRenderer(ModItems.GRAVE_GLOVE, new WearRenderer(render("grave_glove.png"), () -> new GloveModel(bakeLayer(ModModelLayer.GLOVE))));
        TrinketRendererRegistry.registerRenderer(ModItems.THRASH_GLOVE, new WearRenderer(render("thrash_glove.png"), () -> new GloveModel(bakeLayer(ModModelLayer.GLOVE))));
        ModItems.ITEMS.forEach(item -> {
            if (item instanceof BlockItem blockItem) {
                if (blockItem.getBlock() instanceof PlushieBlock) {
                    TrinketRendererRegistry.registerRenderer(item, new PlushieCurioRenderer());
                }
            }
        });
    }

    public static ModelPart bakeLayer(ModelLayerLocation layerLocation) {
        return Minecraft.getInstance().getEntityModels().bakeLayer(layerLocation);
    }
}
