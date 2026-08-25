package com.Polarice3.Goety.init;

import cn.sh1rocu.goety.api.event.EntityAddedLayerCallback;
import cn.sh1rocu.goety.mixin.accessor.LivingEntityRendererAccessor;
import cn.sh1rocu.goety.mixin.accessor.SheetsAccessor;
import com.Polarice3.Goety.Goety;
import com.Polarice3.Goety.api.items.magic.ITotem;
import com.Polarice3.Goety.client.gui.overlay.*;
import com.Polarice3.Goety.client.gui.screen.inventory.*;
import com.Polarice3.Goety.client.inventory.container.ModContainerType;
import com.Polarice3.Goety.client.render.*;
import com.Polarice3.Goety.client.render.block.*;
import com.Polarice3.Goety.client.render.layer.*;
import com.Polarice3.Goety.client.render.model.*;
import com.Polarice3.Goety.common.blocks.ModBlocks;
import com.Polarice3.Goety.common.blocks.ModWoodType;
import com.Polarice3.Goety.common.blocks.entities.BrewCauldronBlockEntity;
import com.Polarice3.Goety.common.blocks.entities.ModBlockEntities;
import com.Polarice3.Goety.common.entities.ModEntityType;
import com.Polarice3.Goety.common.entities.vehicle.ModBoat;
import com.Polarice3.Goety.common.items.ArcaCompassItem;
import com.Polarice3.Goety.common.items.FlameCaptureItem;
import com.Polarice3.Goety.common.items.ModItems;
import com.Polarice3.Goety.common.items.WaystoneItem;
import com.Polarice3.Goety.common.items.curios.EternalCauldronItem;
import com.Polarice3.Goety.common.items.curios.OminousCharmItem;
import com.Polarice3.Goety.common.items.magic.*;
import com.Polarice3.Goety.common.items.revive.SoulJar;
import com.google.common.base.Suppliers;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.fabricmc.fabric.api.client.model.loading.v1.ModelLoadingPlugin;
import net.fabricmc.fabric.api.client.model.loading.v1.ModelModifier;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.gui.screens.inventory.CraftingScreen;
import net.minecraft.client.model.BoatModel;
import net.minecraft.client.model.ChestBoatModel;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.blockentity.HangingSignRenderer;
import net.minecraft.client.renderer.blockentity.SignRenderer;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.client.renderer.item.CompassItemPropertyFunction;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.level.FoliageColor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.WoodType;

import java.util.Map;
import java.util.function.Supplier;

@Environment(EnvType.CLIENT)
public class ClientInitEvents {

    public static void init() {
        clientInit();
        EntityAddedLayerCallback.EVENT.register(ClientInitEvents::addLayers);
        registerGUI();
        onRegisterLayers();
        ClientLifecycleEvents.CLIENT_STARTED.register(client -> {
            TrinketsRenderer.register();
        });
        onRegisterRenders();
        colorBlock();
        colorItem();

    }

    private static void addWoodType(WoodType woodType) {
        Sheets.SIGN_MATERIALS.put(woodType, SheetsAccessor.goety$createSignMaterial(woodType));
        Sheets.HANGING_SIGN_MATERIALS.put(woodType, SheetsAccessor.goety$createHangingSignMaterial(woodType));
    }

    private static void clientInit() {
        MenuScreens.register(ModContainerType.WAND, SoulItemScreen::new);
        MenuScreens.register(ModContainerType.FOCUS_BAG, FocusBagScreen::new);
        MenuScreens.register(ModContainerType.FOCUS_PACK, FocusPackScreen::new);
        MenuScreens.register(ModContainerType.BREW_BAG, BrewBagScreen::new);
        MenuScreens.register(ModContainerType.ETERNAL_CAULDRON, EternalCauldronScreen::new);
        MenuScreens.register(ModContainerType.DARK_ANVIL, DarkAnvilScreen::new);
        MenuScreens.register(ModContainerType.CRAFTING_FOCUS, CraftingScreen::new);
        ModKeybindings.init();

        addWoodType(ModWoodType.HAUNTED);
        addWoodType(ModWoodType.ROTTEN);
        addWoodType(ModWoodType.WINDSWEPT);
        addWoodType(ModWoodType.PINE);

        ItemProperties.register(ModItems.TOTEM_OF_SOULS, new ResourceLocation("souls"),
                (stack, world, living, seed) -> ((float) ITotem.currentSouls(stack)) / ITotem.maximumSouls(stack));
        ItemProperties.register(ModItems.TOTEM_OF_SOULS, new ResourceLocation("activated"),
                (stack, world, living, seed) -> TotemOfSouls.isActivated(stack) ? 1.0F : 0.0F);
        ItemProperties.register(ModItems.FLAME_CAPTURE, new ResourceLocation("capture"),
                (stack, world, living, seed) -> FlameCaptureItem.hasEntity(stack) ? 1.0F : 0.0F);
        ItemProperties.register(ModItems.SOUL_JAR, new ResourceLocation("type"),
                (stack, world, living, seed) -> SoulJar.isDrowned(stack) ? 1.0F : SoulJar.isWither(stack) ? 2.0F : SoulJar.isCairn(stack) ? 3.0F : SoulJar.isMossy(stack) ? 4.0F : 0.0F);
        ItemProperties.register(ModItems.TAGLOCK_KIT, new ResourceLocation("tagged"),
                (stack, world, living, seed) -> TaglockKit.hasEntity(stack) ? 1.0F : 0.0F);
        ItemProperties.register(ModItems.WAYSTONE, new ResourceLocation("store"),
                (stack, world, living, seed) -> WaystoneItem.hasBlock(stack) ? 1.0F : 0.0F);
        ItemProperties.register(ModItems.TRANSFER_SCROLL, new ResourceLocation("signed"),
                (stack, world, living, seed) -> TransferScroll.hasSummon(stack) ? 1.0F : 0.0F);
        ItemProperties.register(ModItems.ARCA_COMPASS, new ResourceLocation("angle")
                , new CompassItemPropertyFunction((p_234992_, p_234993_, p_234994_) -> {
                    return ArcaCompassItem.getArcaPosition(p_234993_.getOrCreateTag());
                }));
        ItemProperties.register(ModItems.HUNTERS_BOW, new ResourceLocation("pull"),
                (stack, world, living, seed) -> {
                    if (living == null) {
                        return 0.0F;
                    } else {
                        return living.getUseItem() != stack ? 0.0F : (float) (stack.getUseDuration() - living.getUseItemRemainingTicks()) / 20;
                    }
                });
        ItemProperties.register(ModItems.HUNTERS_BOW, new ResourceLocation("pulling")
                , (stack, world, living, seed) -> living != null && living.isUsingItem() && living.getUseItem() == stack ? 1.0F : 0.0F);
            /*ItemProperties.register(ModItems.REVOLVER_CROSSBOW, new ResourceLocation("pull")
                    , (stack, world, living, seed) -> {
                if (living == null) {
                    return 0.0F;
                } else {
                    return RevolverCrossbowItem.isCharged(stack) ? 0.0F : (float)(stack.getUseDuration() - living.getUseItemRemainingTicks()) / (float)RevolverCrossbowItem.getChargeDuration(stack);
                }
            });
            ItemProperties.register(ModItems.REVOLVER_CROSSBOW, new ResourceLocation("pulling")
                    , (stack, world, living, seed) -> living != null && living.isUsingItem() && living.getUseItem() == stack && !RevolverCrossbowItem.isCharged(stack) ? 1.0F : 0.0F);
            ItemProperties.register(ModItems.REVOLVER_CROSSBOW, new ResourceLocation("charged")
                    , (stack, world, living, seed) -> RevolverCrossbowItem.isCharged(stack) ? 1.0F : 0.0F);
            ItemProperties.register(ModItems.REVOLVER_CROSSBOW, new ResourceLocation("firework")
                    , (stack, world, living, seed) -> RevolverCrossbowItem.isCharged(stack) && RevolverCrossbowItem.containsChargedProjectile(stack, Items.FIREWORK_ROCKET) ? 1.0F : 0.0F);*/
        ItemProperties.register(ModItems.CALL_FOCUS, new ResourceLocation("active")
                , (stack, world, living, seed) -> CallFocus.hasSummon(stack) ? 1.0F : 0.0F);
        ItemProperties.register(ModItems.TROOP_FOCUS, new ResourceLocation("active")
                , (stack, world, living, seed) -> TroopFocus.hasSummonType(stack) ? 1.0F : 0.0F);
        ItemProperties.register(ModItems.RECALL_FOCUS, new ResourceLocation("active")
                , (stack, world, living, seed) -> RecallFocus.hasRecall(stack) ? 1.0F : 0.0F);
        ItemProperties.register(ModItems.INFERNAL_TOME, new ResourceLocation("active")
                , (stack, world, living, seed) -> living != null && living.isUsingItem() && (living.getUseItem() == stack || InfernalTome.isChanting(stack)) ? 1.0F : 0.0F);
        ItemProperties.register(ModItems.OMINOUS_CHARM, new ResourceLocation("active")
                , (stack, world, living, seed) -> OminousCharmItem.hasOmen(stack) ? 1.0F : 0.0F);
        ItemProperties.register(ModItems.COMMAND_HORN, new ResourceLocation("mode")
                , (stack, world, living, seed) -> {
                    if (CommandHorn.isWander(stack)) {
                        return 1.0F;
                    } else if (CommandHorn.isStandBy(stack)) {
                        return 2.0F;
                    } else if (CommandHorn.isGuard(stack)) {
                        return 3.0F;
                    } else if (CommandHorn.isFollow(stack)) {
                        return 4.0F;
                    }
                    return 0.0F;
                });
        ItemProperties.register(ModItems.ESOTERIC_TESSERACT, new ResourceLocation("active")
                , (stack, world, living, seed) -> EsotericTesseract.getServantsInTesseract(stack) > 0 ? 1.0F : 0.0F);
        ItemProperties.register(ModItems.ETERNAL_CAULDRON, new ResourceLocation("filled")
                , (stack, world, living, seed) -> !EternalCauldronItem.getBottle(stack).isEmpty() ? 1.0F : 0.0F);

    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private static void addLayers(
            Map<EntityType<?>, EntityRenderer<?>> renderers,
            Map<String, EntityRenderer<? extends Player>> skinMap,
            EntityRendererProvider.Context context,
            EntityModelSet entityModelSet
    ) {
        skinMap.forEach((skin, renderer) -> {
            if (renderer instanceof LivingEntityRenderer livingRenderer) {
                addPlayerLayers(livingRenderer, entityModelSet);
            }
        });
        renderers.values().stream().filter(LivingEntityRenderer.class::isInstance).map(LivingEntityRenderer.class::cast).forEach(ClientInitEvents::addLivingLayer);
    }

    private static <T extends LivingEntity, M extends EntityModel<T>> void addLivingLayer(LivingEntityRenderer<T, M> renderer) {
        var livingRenderer = (LivingEntityRendererAccessor) renderer;
        livingRenderer.goety$addLayer(new FreezeLayer<>(renderer));
        livingRenderer.goety$addLayer(new MagicShieldLayer<>(renderer));
    }

    private static void addPlayerLayers(LivingEntityRenderer<Player, EntityModel<Player>> renderer, EntityModelSet entityModelSet) {
        var livingRenderer = (LivingEntityRendererAccessor) renderer;
        livingRenderer.goety$addLayer(new FreezeLayer<>(renderer));
        livingRenderer.goety$addLayer(new MagicShieldLayer<>(renderer));
        livingRenderer.goety$addLayer(new PlayerSoulArmorLayer<>(renderer, entityModelSet));
        livingRenderer.goety$addLayer(new PlayerSoulShieldLayer<>(renderer, entityModelSet));
        livingRenderer.goety$addLayer(new PlayerSpellShieldLayer<>(renderer, entityModelSet));
    }

    private static void registerGUI() {
        HudRenderCallback.EVENT.register(OminousCharmGui.OVERLAY);
        HudRenderCallback.EVENT.register(EternalCauldronGui.OVERLAY);
        HudRenderCallback.EVENT.register(DreadOverlay.OVERLAY);
        HudRenderCallback.EVENT.register(SoulEnergyGui.OVERLAY);
        HudRenderCallback.EVENT.register(RavagerRoarGui.OVERLAY);
        HudRenderCallback.EVENT.register(CurrentFocusGui.OVERLAY);
    }

    private static void onRegisterLayers() {
        EntityModelLayerRegistry.registerModelLayer(ModBlockLayer.ARCA, ArcaRenderer::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(ModBlockLayer.TALL_SKULL, TallSkullModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(ModBlockLayer.REDSTONE_GOLEM_SKULL, RedstoneGolemSkullModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(ModBlockLayer.GRAVE_GOLEM_SKULL, GraveGolemSkullModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(ModBlockLayer.REDSTONE_MONSTROSITY_HEAD, RedstoneMonstrosityHeadModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(ModBlockLayer.LOFTY_CHEST, LoftyChestRenderer::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(ModBlockLayer.BLACK_CRYSTAL, BlackCrystalRenderer::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(ModBlockLayer.OMINOUS_STATUE, OminousStatueRenderer::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(ModBlockLayer.BRAZIER_STATUE, OminousBrazierStatueRenderer::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(ModBlockLayer.SCULPTURED_STATUE, SculpturedStatueRenderer::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(ModBlockLayer.SCULPTURED_STATUE_SLIM, SculpturedStatueRenderer::createSlimLayer);
        EntityModelLayerRegistry.registerModelLayer(ModBlockLayer.PLUSHIE, PlushieModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayer.SPIKE, SpikeModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayer.HARPOON, HarpoonModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayer.POISON_QUILL, PoisonQuillModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayer.ICE_BOUQUET, IceBouquetModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayer.ICE_CHUNK, IceChunkModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayer.VICIOUS_TOOTH, ViciousToothModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayer.VICIOUS_PIKE, ViciousPikeModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayer.GULF_TENTACLE, GulfTentacleModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayer.EARTH_FIST, EarthFistModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayer.SOUL_BOLT, SoulBoltModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayer.SHIELD_DEBRIS, ShieldDebrisModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayer.HELL_BLAST, HellBlastModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayer.SCREAM, HellChantModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayer.POTION_CASE, ReprobateCarryModel::createCaseLayer);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayer.POTION_BARREL, ReprobateCarryModel::createBarrelLayer);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayer.VOID_SHOCK, VoidShockModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayer.VOID_SHOCK_BOMB, VoidShockBombModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayer.SCATTER_MINE, ScatterMineModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayer.SMACK_STONE, SmackStoneModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayer.BLAST_FUNGUS, BlastFungusModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayer.WEB_SHOT, WebShotModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayer.SOUL_BOMB, SoulBombModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayer.SUMMON_CIRCLE, SummonCircleModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayer.SUMMON_CIRCLE_BOSS, SummonCircleBossModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayer.ENTANGLE_VINES, EntangleVinesModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayer.FIRE_TORNADO, CycloneModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayer.TIDAL_SURGE, TidalSurgeModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayer.MONOLITH, MonolithModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayer.QUICK_GROWING_VINE, QuickGrowingVineModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayer.POISON_QUILL_VINE, PoisonQuillVineModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayer.BIOMINE, BioMineModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayer.SPIDER_EGG, SpiderEggModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayer.VOLCANO, VolcanoModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayer.TRIDENT_STORM, TridentStormModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayer.BLOCK, BlockModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayer.WARLOCK, WarlockModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayer.HERETIC, HereticModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayer.MAVERICK, MaverickModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayer.REPROBATE, ReprobateModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayer.CRONE, CroneModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayer.MOD_WITCH, ModWitchModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayer.HERESIARCH, HeresiarchModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayer.HERESIARCH_SHADOW, HeresiarchModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayer.APOSTLE, ApostleModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayer.APOSTLE_SHADE, ApostleShadeRenderer.ApostleShadeModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayer.ZOMBIE_VILLAGER_SERVANT, VillagerServantModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayer.SKELETON_VILLAGER_SERVANT, SkeletonVillagerModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayer.BOUND_ILLAGER, BoundIllagerModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayer.BOUND_ILLAGER_ANIMATED, BoundIllagerAnimatedModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayer.DAMNED, DamnedModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayer.DAMNED_HUMAN, DamnedModel::createHumanLayer);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayer.ILLAGER_SERVANT, IllagerServantModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayer.VINDICATOR_CHEF, VindicatorChefModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayer.MOUNTAINEER, MountaineerModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayer.GEOMANCER, GeomancerModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayer.ICEOLOGER, IceologerModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayer.WIND_CALLER, WindCallerModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayer.PRISONER, PrisonerModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayer.RAVAGED, RavagedModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayer.RAVAGER, ModRavagerModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayer.RAVAGER_ARMOR, ModRavagerModel::createArmorLayer);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayer.BLACK_WOLF, BlackWolfModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayer.BEAR, BearServantModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayer.SNAPPER, SnapperModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayer.GNASHER, GnasherModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayer.BLACK_BEAST, BlackBeastModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayer.BEAST_HEAD, BeastHeadModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayer.WHISPERER, WhispererModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayer.LEAPLEAF, LeapleafModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayer.MINISTROSITY, MinistrosityModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayer.ICE_GOLEM, IceGolemModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayer.SQUALL_GOLEM, SquallGolemModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayer.REDSTONE_GOLEM, RedstoneGolemModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayer.GRAVE_GOLEM, GraveGolemModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayer.HAUNT, HauntModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayer.REDSTONE_MONSTROSITY, RedstoneMonstrosityModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayer.REDSTONE_CUBE, RedstoneCubeModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayer.WATCHLING, WatchlingModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayer.BLASTLING, BlastlingModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayer.SNARELING, SnarelingModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayer.ENDERSENT, EndersentModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayer.ENDER_KEEPER, EnderKeeperModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayer.ENDER_KEEPER_SHADOW, EnderKeeperModel::createShadowLayer);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayer.ZPIGLIN_SERVANT, ZPiglinModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayer.MALGHAST, ModGhastModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayer.INFERNO, InfernoModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayer.WILDFIRE, WildfireModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayer.MINI_GHAST, MiniGhastModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayer.MAGMA_CUBE, MagmaCubeServantModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayer.TROPICAL_SLIME_OUTER, TropicalSlimeModel::createOuterBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayer.TROPICAL_SLIME_INNER, TropicalSlimeModel::createInnerBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayer.MOD_SPIDER, ModSpiderModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayer.ICY_SPIDER, ModSpiderModel::createIcyBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayer.WEB_SPIDER, WebSpiderModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayer.BROOD_MOTHER, BroodMotherModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayer.SPECTER, SpecterModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayer.REAPER, ReaperModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayer.WRAITH, WraithModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayer.PLAYER_ZOMBIE, PlayerZombieModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayer.SUNKEN_SKELETON, SunkenSkeletonModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayer.NECROMANCER, NecromancerModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayer.DROWNED_NECROMANCER, DrownedNecromancerModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayer.WITHER_NECROMANCER, WitherNecromancerModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayer.VANGUARD, VanguardModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayer.BLACKGUARD, BlackguardModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayer.WIGHT, WightModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayer.MAGGOT, CarrionMaggotModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayer.FLY, CarrionFlyModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayer.SORCERER, SorcererModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayer.ENVIOKER, EnviokerModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayer.TORMENTOR, TormentorModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayer.INQUILLAGER, InquillagerModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayer.CONQUILLAGER, ConquillagerModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayer.PIKER, PikerModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayer.RIPPER, RipperModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayer.TRAMPLER, TramplerModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayer.CRUSHER, CrusherModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayer.STORM_CASTER, StormCasterModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayer.CRYOLOGER, CryologerModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayer.PREACHER, PreacherModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayer.MINISTER, MinisterModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayer.VIZIER, VizierModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayer.VIZIER_CLONE, VizierCloneModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayer.IRK, IrkModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayer.MINION, MinionModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayer.SPRITE, SpriteMobModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayer.HAUNTED_SKULL, HauntedSkullModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayer.HAUNTED_SKULL_FIRELESS, HauntedSkullModel::createFirelessLayer);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayer.SKULL_LORD, SkullLordModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayer.VIZIER_ARMOR, VizierModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayer.DARK_HAT, DarkHatModel::createDarkHatLayer);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayer.GRAND_TURBAN, DarkHatModel::createGrandTurbanLayer);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayer.WITCH_HAT, WitchHatModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayer.CRONE_HAT, WitchHatModel::createCroneLayer);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayer.UNHOLY_HAT, UnholyHatModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayer.IRON_CROWN, DarkHatModel::createIronCrownLayer);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayer.DARK_ROBE, DarkRobeModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayer.GRAND_ROBE, DarkRobeModel::createGrandRobeLayer);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayer.NECRO_CROWN, NecroCapeModel::createHeadLayer);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayer.NECRO_CAPE, NecroCapeModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayer.NECRO_SET, NecroCapeModel::createNecromancerLayer);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayer.NAMELESS_CROWN, NecroCapeModel::createBigHeadLayer);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayer.NAMELESS_SET, NecroCapeModel::createNamelessLayer);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayer.LICH, () -> LayerDefinition.create(LichModeModel.createMesh(CubeDeformation.NONE), 64, 64));
        EntityModelLayerRegistry.registerModelLayer(ModModelLayer.GLOVE, GloveModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayer.FOCUS_BAG, MiscCuriosModel::createFocusBagLayer);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayer.BREW_BAG, MiscCuriosModel::createBrewBagLayer);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayer.AMULET, MiscCuriosModel::createAmuletLayer);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayer.AMETHYST_NECKLACE, MiscCuriosModel::createAmethystNecklaceLayer);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayer.ETERNAL_CAULDRON, EternalCauldronModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayer.BELT, MiscCuriosModel::createBeltLayer);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayer.MONOCLE, MiscCuriosModel::createMonocleLayer);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayer.VILLAGER_ARMOR_INNER, VillagerArmorModel::createInnerArmorLayer);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayer.VILLAGER_ARMOR_OUTER, VillagerArmorModel::createOuterArmorLayer);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayer.CURSED_KNIGHT_ARMOR_INNER, CursedKnightArmorModel::createInnerLayer);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayer.CURSED_KNIGHT_ARMOR_OUTER, CursedKnightArmorModel::createOuterLayer);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayer.CURSED_PALADIN_ARMOR_INNER, CursedPaladinArmorModel::createInnerLayer);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayer.CURSED_PALADIN_ARMOR_OUTER, CursedPaladinArmorModel::createOuterLayer);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayer.BLACK_IRON_ARMOR_INNER, BlackIronArmorModel::createInnerLayer);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayer.BLACK_IRON_ARMOR_OUTER, BlackIronArmorModel::createOuterLayer);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayer.DARK_ARMOR_INNER, DarkArmorModel::createInnerLayer);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayer.DARK_ARMOR_OUTER, DarkArmorModel::createOuterLayer);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayer.MALEFIC_HELM, MaleficHelmModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayer.SOUL_SHIELD, () -> LayerDefinition.create(PlayerModel.createMesh(new CubeDeformation(0.5F), false), 64, 64));
        EntityModelLayerRegistry.registerModelLayer(ModModelLayer.SOUL_ARMOR, () -> LayerDefinition.create(PlayerModel.createMesh(new CubeDeformation(0.3F), false), 64, 64));
        EntityModelLayerRegistry.registerModelLayer(ModModelLayer.NAMELESS_STAFF, NamelessStaffModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayer.BROOM, HauntedBroomModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayer.HAUNTED_ARMOR_STAND, HauntedArmorStandModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayer.HAS_INNER, () -> HauntedArmorStandArmorModel.createBodyLayer(new CubeDeformation(0.5F)));
        EntityModelLayerRegistry.registerModelLayer(ModModelLayer.HAS_OUTER, () -> HauntedArmorStandArmorModel.createBodyLayer(new CubeDeformation(1.0F)));
        EntityModelLayerRegistry.registerModelLayer(ModModelLayer.SARCOPHAGUS, SarcophagusModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayer.SMALL_PAINTING, HauntedPaintingModel::createSmallFrameLayer);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayer.MEDIUM_PAINTING, HauntedPaintingModel::createMediumFrameLayer);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayer.LARGE_PAINTING, HauntedPaintingModel::createLargeFrameLayer);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayer.TALL_PAINTING, HauntedPaintingModel::createTallFrameLayer);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayer.WIDE_PAINTING, HauntedPaintingModel::createWideFrameLayer);

        for (ModBoat.Type boatType : ModBoat.Type.values()) {
            EntityModelLayerRegistry.registerModelLayer(ModBoatRenderer.createBoatModelName(boatType), BoatModel::createBodyModel);
            EntityModelLayerRegistry.registerModelLayer(ModBoatRenderer.createChestBoatModelName(boatType), ChestBoatModel::createBodyModel);
        }
    }

    private static void onRegisterRenders() {
        Supplier<ItemRenderer> itemRenderer = Suppliers.memoize(() -> Minecraft.getInstance().getItemRenderer());
        BlockEntityRenderers.register(ModBlockEntities.ARCA, ArcaRenderer::new);
        BlockEntityRenderers.register(ModBlockEntities.CURSED_INFUSER, CursedInfuserRenderer::new);
        BlockEntityRenderers.register(ModBlockEntities.GRIM_INFUSER, GrimInfuserRenderer::new);
        BlockEntityRenderers.register(ModBlockEntities.CURSED_CAGE, CursedCageRenderer::new);
        BlockEntityRenderers.register(ModBlockEntities.DARK_ALTAR, DarkAltarRenderer::new);
        BlockEntityRenderers.register(ModBlockEntities.PEDESTAL, PedestalRenderer::new);
        BlockEntityRenderers.register(ModBlockEntities.SOUL_ABSORBER, SoulAbsorberRenderer::new);
        BlockEntityRenderers.register(ModBlockEntities.SOUL_MENDER, SoulMenderRenderer::new);
        BlockEntityRenderers.register(ModBlockEntities.ICE_BOUQUET_TRAP, ModBlockEntityRenderer::new);
        BlockEntityRenderers.register(ModBlockEntities.WIND_BLOWER, ModBlockEntityRenderer::new);
        BlockEntityRenderers.register(ModBlockEntities.RESONANCE_CRYSTAL, ResonanceCrystalRenderer::new);
        BlockEntityRenderers.register(ModBlockEntities.SCULK_DEVOURER, ModBlockEntityRenderer::new);
        BlockEntityRenderers.register(ModBlockEntities.FORBIDDEN_GRASS, ModBlockEntityRenderer::new);
        BlockEntityRenderers.register(ModBlockEntities.MAGIC_LIGHT, ModBlockEntityRenderer::new);
        BlockEntityRenderers.register(ModBlockEntities.HOOK_BELL, HookBellRenderer::new);
        BlockEntityRenderers.register(ModBlockEntities.SHRIEKING_OBELISK, ModBlockEntityRenderer::new);
        BlockEntityRenderers.register(ModBlockEntities.NECRO_BRAZIER, NecroBrazierRenderer::new);
        BlockEntityRenderers.register(ModBlockEntities.ANIMATOR, AnimatorRenderer::new);
        BlockEntityRenderers.register(ModBlockEntities.BLACK_CRYSTAL, BlackCrystalRenderer::new);
        BlockEntityRenderers.register(ModBlockEntities.BREWING_CAULDRON, BrewCauldronRenderer::new);
        BlockEntityRenderers.register(ModBlockEntities.HAUNTED_MIRROR, ModBlockEntityRenderer::new);
        BlockEntityRenderers.register(ModBlockEntities.HAUNTED_JUG, ModBlockEntityRenderer::new);
        BlockEntityRenderers.register(ModBlockEntities.SPIDER_NEST, TrainingBlockRenderer::new);
        BlockEntityRenderers.register(ModBlockEntities.SHADE_GRAVESTONE, TrainingBlockRenderer::new);
        BlockEntityRenderers.register(ModBlockEntities.SHADE_OSSUARY, TrainingBlockRenderer::new);
        BlockEntityRenderers.register(ModBlockEntities.BLAZING_CAGE, TrainingBlockRenderer::new);
        BlockEntityRenderers.register(ModBlockEntities.OMINOUS_PYRE, BarracksBlockRenderer::new);
        BlockEntityRenderers.register(ModBlockEntities.OMINOUS_IDOL, ModBlockEntityRenderer::new);
        BlockEntityRenderers.register(ModBlockEntities.SPIDER_MOTHER_DEN, ModBlockEntityRenderer::new);
        BlockEntityRenderers.register(ModBlockEntities.VOID_SPAWNER, VoidSpawnerRenderer::new);
        BlockEntityRenderers.register(ModBlockEntities.VOID_VAULT, VoidVaultRenderer::new);
        BlockEntityRenderers.register(ModBlockEntities.VOID_FRAME, ModBlockEntityRenderer::new);
        BlockEntityRenderers.register(ModBlockEntities.VOID_SHRINE, VoidShrineRenderer::new);
        BlockEntityRenderers.register(ModBlockEntities.HOLE, HoleBlockEntityRenderer::new);
        BlockEntityRenderers.register(ModBlockEntities.PART_LIQUID, ModBlockEntityRenderer::new);
        BlockEntityRenderers.register(ModBlockEntities.NIGHT_BEACON, NightBeaconRenderer::new);
        BlockEntityRenderers.register(ModBlockEntities.VOID_BARREL, ModBlockEntityRenderer::new);
        BlockEntityRenderers.register(ModBlockEntities.MANDALA, MandalaRenderer::new);
        BlockEntityRenderers.register(ModBlockEntities.OMINOUS_STATUE, OminousStatueRenderer::new);
        BlockEntityRenderers.register(ModBlockEntities.OMINOUS_BRAZIER_STATUE, OminousBrazierStatueRenderer::new);
        BlockEntityRenderers.register(ModBlockEntities.THRONE, ModBlockEntityRenderer::new);
        BlockEntityRenderers.register(ModBlockEntities.TALL_SKULL, TallSkullBlockEntityRenderer::new);
        BlockEntityRenderers.register(ModBlockEntities.REDSTONE_GOLEM_SKULL, RedstoneGolemSkullBlockEntityRenderer::new);
        BlockEntityRenderers.register(ModBlockEntities.GRAVE_GOLEM_SKULL, GraveGolemSkullBlockEntityRenderer::new);
        BlockEntityRenderers.register(ModBlockEntities.REDSTONE_MONSTROSITY_HEAD, RedstoneMonstrosityHeadBlockEntityRenderer::new);
        BlockEntityRenderers.register(ModBlockEntities.MOD_CHEST, ModChestRenderer::new);
        BlockEntityRenderers.register(ModBlockEntities.MOD_TRAPPED_CHEST, ModChestRenderer::new);
        BlockEntityRenderers.register(ModBlockEntities.CRYPT_CHEST, CryptChestRenderer::new);
        BlockEntityRenderers.register(ModBlockEntities.LOFTY_CHEST, LoftyChestRenderer::new);
        BlockEntityRenderers.register(ModBlockEntities.SIGN_BLOCK_ENTITIES, SignRenderer::new);
        BlockEntityRenderers.register(ModBlockEntities.HANGING_SIGN_BLOCK_ENTITIES, HangingSignRenderer::new);
        BlockEntityRenderers.register(ModBlockEntities.SARCOPHAGUS, SarcophagusRenderer::new);
        BlockEntityRenderers.register(ModBlockEntities.PLUSHIE, PlushieBlockEntityRenderer::new);
        BlockEntityRenderers.register(ModBlockEntities.SCULPTURED_STATUE, SculpturedStatueRenderer::new);

        EntityRendererRegistry.register(ModEntityType.NETHER_METEOR, NetherMeteorRenderer::new);
        EntityRendererRegistry.register(ModEntityType.MOD_FIREBALL, (rendererManager) -> new ModFireballRenderer<>(rendererManager, 0.75F, true));
        EntityRendererRegistry.register(ModEntityType.LAVABALL, (rendererManager) -> new ModFireballRenderer<>(rendererManager, 3.0F, true));
        EntityRendererRegistry.register(ModEntityType.HELL_BOLT, HellBoltRenderer::new);
        EntityRendererRegistry.register(ModEntityType.HELL_BLAST, HellBlastRenderer::new);
        EntityRendererRegistry.register(ModEntityType.HELL_CHANT, HellChantRenderer::new);
        EntityRendererRegistry.register(ModEntityType.SWORD, (rendererManager) -> new SwordProjectileRenderer<>(rendererManager, itemRenderer, 1.25F, true));
        EntityRendererRegistry.register(ModEntityType.ICE_SPIKE, IceSpikeRenderer::new);
        EntityRendererRegistry.register(ModEntityType.ICE_SPEAR, IceSpearRenderer::new);
        EntityRendererRegistry.register(ModEntityType.ICE_STORM, IceStormRenderer::new);
        EntityRendererRegistry.register(ModEntityType.GHOST_ARROW, TippableArrowRenderer::new);
        EntityRendererRegistry.register(ModEntityType.RAIN_ARROW, RainArrowRenderer::new);
        EntityRendererRegistry.register(ModEntityType.DEATH_ARROW, DeathArrowRenderer::new);
        EntityRendererRegistry.register(ModEntityType.HARPOON, HarpoonRenderer::new);
        EntityRendererRegistry.register(ModEntityType.POISON_QUILL, PoisonQuillRenderer::new);
        EntityRendererRegistry.register(ModEntityType.BONE_SHARD, (rendererManager) -> new BoneShardRenderer<>(rendererManager, itemRenderer));
        EntityRendererRegistry.register(ModEntityType.BREW, ThrownItemRenderer::new);
        EntityRendererRegistry.register(ModEntityType.REPROBATE_CARRY, ReprobateCarryRenderer::new);
        EntityRendererRegistry.register(ModEntityType.SCYTHE, ScytheSlashRenderer::new);
        EntityRendererRegistry.register(ModEntityType.MOD_DRAGON_FIREBALL, ModDragonFireballRenderer::new);
        EntityRendererRegistry.register(ModEntityType.HAUNTED_SKULL_SHOT, HauntedSkullProjectileRenderer::new);
        EntityRendererRegistry.register(ModEntityType.MOD_WITHER_SKULL, ModWitherSkullRenderer::new);
        EntityRendererRegistry.register(ModEntityType.SOUL_LIGHT, SoulBulletRenderer::new);
        EntityRendererRegistry.register(ModEntityType.GLOW_LIGHT, SoulBulletRenderer::new);
        EntityRendererRegistry.register(ModEntityType.SOUL_BULLET, SoulBulletRenderer::new);
        EntityRendererRegistry.register(ModEntityType.SOUL_BOLT, SoulBoltRenderer::new);
        EntityRendererRegistry.register(ModEntityType.POISON_BOLT, PoisonBoltRenderer::new);
        EntityRendererRegistry.register(ModEntityType.STEAM_MISSILE, SteamMissileRenderer::new);
        EntityRendererRegistry.register(ModEntityType.WITHER_BOLT, WitherBoltRenderer::new);
        EntityRendererRegistry.register(ModEntityType.NECRO_BOLT, NecroBoltRenderer::new);
        EntityRendererRegistry.register(ModEntityType.MAGIC_BOLT, SoulBulletRenderer::new);
        EntityRendererRegistry.register(ModEntityType.SHIELD_DEBRIS, ShieldDebrisRenderer::new);
        EntityRendererRegistry.register(ModEntityType.VOID_SHOCK, VoidShockRenderer::new);
        EntityRendererRegistry.register(ModEntityType.VOID_SHOCK_BOMB, VoidShockBombRenderer::new);
        EntityRendererRegistry.register(ModEntityType.FANG, FangsRenderer::new);
        EntityRendererRegistry.register(ModEntityType.SPIKE, SpikeRenderer::new);
        EntityRendererRegistry.register(ModEntityType.ILL_BOMB, IllBombRenderer::new);
        EntityRendererRegistry.register(ModEntityType.CRYPTIC_EYE, (rendererManager) -> new ThrownItemRenderer<>(rendererManager, 1.0F, true));
        EntityRendererRegistry.register(ModEntityType.VOID_EYE, (rendererManager) -> new ThrownItemRenderer<>(rendererManager, 1.0F, true));
        EntityRendererRegistry.register(ModEntityType.FLYING_ITEM, (rendererManager) -> new ThrownItemRenderer<>(rendererManager, 1.0F, true));
        EntityRendererRegistry.register(ModEntityType.ELECTRO_ORB, ElectroOrbRenderer::new);
        EntityRendererRegistry.register(ModEntityType.SURGING_ORB, SurgingOrbRenderer::new);
        EntityRendererRegistry.register(ModEntityType.BOUNCY_BUBBLE, BouncyBubbleRenderer::new);
        EntityRendererRegistry.register(ModEntityType.ICE_BOUQUET, IceBouquetRenderer::new);
        EntityRendererRegistry.register(ModEntityType.MAGIC_FIRE, MagicFireRenderer::new);
        EntityRendererRegistry.register(ModEntityType.HELLFIRE, HellfireRenderer::new);
        EntityRendererRegistry.register(ModEntityType.ICE_CHUNK, IceChunkRenderer::new);
        EntityRendererRegistry.register(ModEntityType.VICIOUS_TOOTH, ViciousToothRenderer::new);
        EntityRendererRegistry.register(ModEntityType.VICIOUS_PIKE, ViciousPikeRenderer::new);
        EntityRendererRegistry.register(ModEntityType.EARTH_FIST, EarthFistRenderer::new);
        EntityRendererRegistry.register(ModEntityType.BLOSSOM_THORN, BlossomThornRenderer::new);
        EntityRendererRegistry.register(ModEntityType.CORRUPTED_BEAM, CorruptedBeamRenderer::new);
        EntityRendererRegistry.register(ModEntityType.SCATTER_MINE, ScatterMineRenderer::new);
        EntityRendererRegistry.register(ModEntityType.SCATTER_BOMB, ScatterBombRenderer::new);
        EntityRendererRegistry.register(ModEntityType.SOUL_BOMB, SoulBombRenderer::new);
        EntityRendererRegistry.register(ModEntityType.SNAP_FUNGUS, SnapFungusRenderer::new);
        EntityRendererRegistry.register(ModEntityType.BLAST_FUNGUS, BlastFungusRenderer::new);
        EntityRendererRegistry.register(ModEntityType.BERSERK_FUNGUS, BerserkFungusRenderer::new);
        EntityRendererRegistry.register(ModEntityType.PYROCLAST, PyroclastRenderer::new);
        EntityRendererRegistry.register(ModEntityType.SMACK_STONE, SmackStoneRenderer::new);
        EntityRendererRegistry.register(ModEntityType.MAGMA_BOMB, MagmaBombRenderer::new);
        EntityRendererRegistry.register(ModEntityType.BLOSSOM_BALL, BlossomBallRenderer::new);
        EntityRendererRegistry.register(ModEntityType.WEB_SHOT, WebShotRenderer::new);
        EntityRendererRegistry.register(ModEntityType.SNARELING_SHOT, SnarelingShotRenderer::new);
        EntityRendererRegistry.register(ModEntityType.ENDER_GOO, EnderGooRenderer::new);
        EntityRendererRegistry.register(ModEntityType.TRIDENT_STORM, TridentStormRenderer::new);
        EntityRendererRegistry.register(ModEntityType.DELAYED_SUMMON, TrapRenderer::new);
        EntityRendererRegistry.register(ModEntityType.SUMMON_CIRCLE, SummonCircleRenderer::new);
        EntityRendererRegistry.register(ModEntityType.SUMMON_CIRCLE_BOSS, SummonCircleBossRenderer::new);
        EntityRendererRegistry.register(ModEntityType.SUMMON_FIERY, SummonCircleVariantRenderer::new);
        EntityRendererRegistry.register(ModEntityType.RAID_BOSS_SUMMON, TrapRenderer::new);
        EntityRendererRegistry.register(ModEntityType.ENTANGLE_VINES, EntangleVinesRenderer::new);
        EntityRendererRegistry.register(ModEntityType.SPIDER_WEB, SpiderWebRenderer::new);
        EntityRendererRegistry.register(ModEntityType.SNARELING_GOOP, SnarelingGoopRenderer::new);
        EntityRendererRegistry.register(ModEntityType.OBSIDIAN_MONOLITH, ObsidianMonolithRenderer::new);
        EntityRendererRegistry.register(ModEntityType.TOTEMIC_WALL, TotemicWallRenderer::new);
        EntityRendererRegistry.register(ModEntityType.TOTEMIC_BOMB, TotemicBombRenderer::new);
        EntityRendererRegistry.register(ModEntityType.GLACIAL_WALL, GlacialWallRenderer::new);
        EntityRendererRegistry.register(ModEntityType.QUICK_GROWING_VINE, QuickGrowingVineRenderer::new);
        EntityRendererRegistry.register(ModEntityType.QUICK_GROWING_KELP, QuickGrowingVineRenderer::new);
        EntityRendererRegistry.register(ModEntityType.POISON_QUILL_VINE, PoisonQuillVineRenderer::new);
        EntityRendererRegistry.register(ModEntityType.POISON_ANEMONE, PoisonQuillVineRenderer::new);
        EntityRendererRegistry.register(ModEntityType.BIOMINE, BioMineRenderer::new);
        EntityRendererRegistry.register(ModEntityType.SPIDER_EGG, SpiderEggRenderer::new);
        EntityRendererRegistry.register(ModEntityType.INSECT_SWARM, TrapRenderer::new);
        EntityRendererRegistry.register(ModEntityType.BEAST_HEAD, BeastHeadRenderer::new);
        EntityRendererRegistry.register(ModEntityType.GULF_TENTACLE, GulfTentacleRenderer::new);
        EntityRendererRegistry.register(ModEntityType.VOLCANO, VolcanoRenderer::new);
        EntityRendererRegistry.register(ModEntityType.FIRE_TORNADO, FireTornadoRenderer::new);
        EntityRendererRegistry.register(ModEntityType.CYCLONE, CycloneRenderer::new);
        EntityRendererRegistry.register(ModEntityType.TIDAL_SURGE, TidalSurgeRenderer::new);
        EntityRendererRegistry.register(ModEntityType.RAZOR_WIND, RazorWindRenderer::new);
        EntityRendererRegistry.register(ModEntityType.VOID_SLASH, VoidSlashRenderer::new);
        EntityRendererRegistry.register(ModEntityType.FALLING_BLOCK, ModFallingBlockRenderer::new);
        EntityRendererRegistry.register(ModEntityType.BREW_EFFECT_GAS, BrewGasRenderer::new);
        EntityRendererRegistry.register(ModEntityType.MOD_BOAT, (render) -> new ModBoatRenderer(render, false));
        EntityRendererRegistry.register(ModEntityType.MOD_CHEST_BOAT, (render) -> new ModBoatRenderer(render, true));
        EntityRendererRegistry.register(ModEntityType.HAUNTED_BROOM, HauntedBroomRenderer::new);
        EntityRendererRegistry.register(ModEntityType.MOD_PAINTING, HauntedPaintingRenderer::new);
        EntityRendererRegistry.register(ModEntityType.HAUNTED_ARMOR_STAND, HauntedArmorStandRenderer::new);
        EntityRendererRegistry.register(ModEntityType.WARLOCK, WarlockRenderer::new);
        EntityRendererRegistry.register(ModEntityType.WARTLING, WartlingRenderer::new);
        EntityRendererRegistry.register(ModEntityType.HERETIC, HereticRenderer::new);
        EntityRendererRegistry.register(ModEntityType.MAVERICK, MaverickRenderer::new);
        EntityRendererRegistry.register(ModEntityType.REPROBATE, ReprobateRenderer::new);
        EntityRendererRegistry.register(ModEntityType.CRONE, CroneRenderer::new);
        EntityRendererRegistry.register(ModEntityType.HERESIARCH, HeresiarchRenderer::new);
        EntityRendererRegistry.register(ModEntityType.APOSTLE, ApostleRenderer::new);
        EntityRendererRegistry.register(ModEntityType.SKELETON_VILLAGER_SERVANT, SkeletonVillagerServantRenderer::new);
        EntityRendererRegistry.register(ModEntityType.ZPIGLIN_SERVANT, ZPiglinRenderer::new);
        EntityRendererRegistry.register(ModEntityType.ZPIGLIN_BRUTE_SERVANT, ZPiglinRenderer::new);
        EntityRendererRegistry.register(ModEntityType.MALGHAST, MalghastRenderer::new);
        EntityRendererRegistry.register(ModEntityType.INFERNO, InfernoRenderer::new);
        EntityRendererRegistry.register(ModEntityType.DAMNED, DamnedRenderer::new);
        EntityRendererRegistry.register(ModEntityType.VAMPIRE_BAT, VampireBatRenderer::new);
        EntityRendererRegistry.register(ModEntityType.HOSTILE_BLACK_WOLF, BlackWolfRenderer::new);
        EntityRendererRegistry.register(ModEntityType.FRAYED, FrayedRenderer::new);
        EntityRendererRegistry.register(ModEntityType.RATTLED, RattledRenderer::new);
        EntityRendererRegistry.register(ModEntityType.REAPER, ReaperRenderer::new);
        EntityRendererRegistry.register(ModEntityType.WRAITH, WraithRenderer::new);
        EntityRendererRegistry.register(ModEntityType.BORDER_WRAITH, BorderWraithRenderer::new);
        EntityRendererRegistry.register(ModEntityType.MUCK_WRAITH, MuckWraithRenderer::new);
        EntityRendererRegistry.register(ModEntityType.CRYPT_SLIME, CryptSlimeRenderer::new);
        EntityRendererRegistry.register(ModEntityType.WEB_SPIDER, WebSpiderRenderer::new);
        EntityRendererRegistry.register(ModEntityType.ICY_SPIDER, IcySpiderRenderer::new);
        EntityRendererRegistry.register(ModEntityType.BONE_SPIDER, BoneSpiderRenderer::new);
        EntityRendererRegistry.register(ModEntityType.BROOD_MOTHER, BroodMotherRenderer::new);
        EntityRendererRegistry.register(ModEntityType.NECROMANCER, NecromancerRenderer::new);
        EntityRendererRegistry.register(ModEntityType.CAIRN_NECROMANCER, AbstractCairnNecromancerRenderer::new);
        EntityRendererRegistry.register(ModEntityType.MOSSY_NECROMANCER, MossyNecromancerRenderer::new);
        EntityRendererRegistry.register(ModEntityType.HAUNTED_ARMOR, HauntedArmorRenderer::new);
        EntityRendererRegistry.register(ModEntityType.WATCHLING, WatchlingRenderer::new);
        EntityRendererRegistry.register(ModEntityType.BLASTLING, BlastlingRenderer::new);
        EntityRendererRegistry.register(ModEntityType.SNARELING, SnarelingRenderer::new);
        EntityRendererRegistry.register(ModEntityType.ENDERSENT, EndersentRenderer::new);
        EntityRendererRegistry.register(ModEntityType.ENDER_KEEPER, EnderKeeperRenderer::new);
        EntityRendererRegistry.register(ModEntityType.VEX_SERVANT, AllyVexRenderer::new);
        EntityRendererRegistry.register(ModEntityType.IRK_SERVANT, IrkRenderer::new);
        EntityRendererRegistry.register(ModEntityType.ZOMBIE_SERVANT, ZombieServantRenderer::new);
        EntityRendererRegistry.register(ModEntityType.ZOMBIE_VILLAGER_SERVANT, ZombieVillagerServantRenderer::new);
        EntityRendererRegistry.register(ModEntityType.HUSK_SERVANT, HuskServantRenderer::new);
        EntityRendererRegistry.register(ModEntityType.DROWNED_SERVANT, DrownedServantRenderer::new);
        EntityRendererRegistry.register(ModEntityType.FROZEN_ZOMBIE_SERVANT, FrozenZombieRenderer::new);
        EntityRendererRegistry.register(ModEntityType.JUNGLE_ZOMBIE_SERVANT, JungleZombieRenderer::new);
        EntityRendererRegistry.register(ModEntityType.FRAYED_SERVANT, FrayedServantRenderer::new);
        EntityRendererRegistry.register(ModEntityType.BLACKGUARD_SERVANT, BlackguardRenderer::new);
        EntityRendererRegistry.register(ModEntityType.SKELETON_SERVANT, SkeletonServantRenderer::new);
        EntityRendererRegistry.register(ModEntityType.STRAY_SERVANT, SkeletonServantRenderer::new);
        EntityRendererRegistry.register(ModEntityType.WITHER_SKELETON_SERVANT, WitherSkeletonServantRenderer::new);
        EntityRendererRegistry.register(ModEntityType.MOSSY_SKELETON_SERVANT, SkeletonServantRenderer::new);
        EntityRendererRegistry.register(ModEntityType.SUNKEN_SKELETON_SERVANT, SunkenSkeletonServantRenderer::new);
        EntityRendererRegistry.register(ModEntityType.RATTLED_SERVANT, SkeletonServantRenderer::new);
        EntityRendererRegistry.register(ModEntityType.NECROMANCER_SERVANT, NecromancerRenderer::new);
        EntityRendererRegistry.register(ModEntityType.CAIRN_NECROMANCER_SERVANT, AbstractCairnNecromancerRenderer::new);
        EntityRendererRegistry.register(ModEntityType.MOSSY_NECROMANCER_SERVANT, MossyNecromancerRenderer::new);
        EntityRendererRegistry.register(ModEntityType.DROWNED_NECROMANCER_SERVANT, DrownedNecromancerRenderer::new);
        EntityRendererRegistry.register(ModEntityType.WITHER_NECROMANCER_SERVANT, WitherNecromancerRenderer::new);
        EntityRendererRegistry.register(ModEntityType.REAPER_SERVANT, ReaperRenderer::new);
        EntityRendererRegistry.register(ModEntityType.WRAITH_SERVANT, WraithServantRenderer::new);
        EntityRendererRegistry.register(ModEntityType.BORDER_WRAITH_SERVANT, BorderWraithServantRenderer::new);
        EntityRendererRegistry.register(ModEntityType.MUCK_WRAITH_SERVANT, MuckWraithServantRenderer::new);
        EntityRendererRegistry.register(ModEntityType.PHANTOM_SERVANT, PhantomServantRenderer::new);
        EntityRendererRegistry.register(ModEntityType.VANGUARD_SERVANT, VanguardRenderer::new);
        EntityRendererRegistry.register(ModEntityType.SKELETON_PILLAGER_SERVANT, SkeletonPillagerRenderer::new);
        EntityRendererRegistry.register(ModEntityType.ZOMBIE_VINDICATOR_SERVANT, ZombieVindicatorRenderer::new);
        EntityRendererRegistry.register(ModEntityType.BOUND_EVOKER, BoundEvokerRenderer::new);
        EntityRendererRegistry.register(ModEntityType.BOUND_GEOMANCER, BoundGeomancerRenderer::new);
        EntityRendererRegistry.register(ModEntityType.BOUND_ICEOLOGER, BoundIceologerRenderer::new);
        EntityRendererRegistry.register(ModEntityType.BOUND_CRYOLOGER, BoundCryologerRenderer::new);
        EntityRendererRegistry.register(ModEntityType.BOUND_WIND_CALLER, BoundWindCallerRenderer::new);
        EntityRendererRegistry.register(ModEntityType.BOUND_STORM_CASTER, BoundStormCasterRenderer::new);
        EntityRendererRegistry.register(ModEntityType.HAUNTED_ARMOR_SERVANT, HauntedArmorRenderer::new);
        EntityRendererRegistry.register(ModEntityType.HAUNTED_SKULL, HauntedSkullRenderer::new);
        EntityRendererRegistry.register(ModEntityType.SPRITE, SpriteMobRenderer::new);
        EntityRendererRegistry.register(ModEntityType.BURNING_HOGLIN, BurningHoglinRenderer::new);
        EntityRendererRegistry.register(ModEntityType.DOPPELGANGER, (render) -> new DoppelgangerRenderer(render, false));
        EntityRendererRegistry.register(ModEntityType.MINI_GHAST, MiniGhastRenderer::new);
        EntityRendererRegistry.register(ModEntityType.GHAST_SERVANT, GhastServantRenderer::new);
        EntityRendererRegistry.register(ModEntityType.BLAZE_SERVANT, BlazeServantRenderer::new);
        EntityRendererRegistry.register(ModEntityType.WILDFIRE, WildfireRenderer::new);
        EntityRendererRegistry.register(ModEntityType.SLIME_SERVANT, SlimeServantRenderer::new);
        EntityRendererRegistry.register(ModEntityType.MAGMA_CUBE_SERVANT, MagmaCubeServantRenderer::new);
        EntityRendererRegistry.register(ModEntityType.CRYPT_SLIME_SERVANT, CryptSlimeServantRenderer::new);
        EntityRendererRegistry.register(ModEntityType.TROPICAL_SLIME_SERVANT, TropicalSlimeServantRenderer::new);
        EntityRendererRegistry.register(ModEntityType.SPIDER_SERVANT, SpiderServantRenderer::new);
        EntityRendererRegistry.register(ModEntityType.CAVE_SPIDER_SERVANT, CaveSpiderServantRenderer::new);
        EntityRendererRegistry.register(ModEntityType.WEB_SPIDER_SERVANT, WebSpiderServantRenderer::new);
        EntityRendererRegistry.register(ModEntityType.ICY_SPIDER_SERVANT, IcySpiderServantRenderer::new);
        EntityRendererRegistry.register(ModEntityType.BONE_SPIDER_SERVANT, BoneSpiderServantRenderer::new);
        EntityRendererRegistry.register(ModEntityType.BROOD_MOTHER_SERVANT, BroodMotherRenderer::new);
        EntityRendererRegistry.register(ModEntityType.PRISONER, PrisonerRenderer::new);
        EntityRendererRegistry.register(ModEntityType.NEOLLAGER, NeollagerRenderer::new);
        EntityRendererRegistry.register(ModEntityType.PILLAGER_SERVANT, PillagerServantRenderer::new);
        EntityRendererRegistry.register(ModEntityType.PIKER_SERVANT, PikerServantRenderer::new);
        EntityRendererRegistry.register(ModEntityType.SIGNALER_SERVANT, SignalerServantRenderer::new);
        EntityRendererRegistry.register(ModEntityType.VINDICATOR_SERVANT, VindicatorServantRenderer::new);
        EntityRendererRegistry.register(ModEntityType.VINDICATOR_CHEF_SERVANT, VindicatorChefServantRenderer::new);
        EntityRendererRegistry.register(ModEntityType.MOUNTAINEER_SERVANT, MountaineerServantRenderer::new);
        EntityRendererRegistry.register(ModEntityType.CRUSHER_SERVANT, CrusherServantRenderer::new);
        EntityRendererRegistry.register(ModEntityType.EVOKER_SERVANT, EvokerServantRenderer::new);
        EntityRendererRegistry.register(ModEntityType.GEOMANCER_SERVANT, GeomancerServantRenderer::new);
        EntityRendererRegistry.register(ModEntityType.ICEOLOGER_SERVANT, IceologerServantRenderer::new);
        EntityRendererRegistry.register(ModEntityType.CRYOLOGER_SERVANT, CryologerServantRenderer::new);
        EntityRendererRegistry.register(ModEntityType.WIND_CALLER_SERVANT, WindCallerServantRenderer::new);
        EntityRendererRegistry.register(ModEntityType.STORM_CASTER_SERVANT, StormCasterServantRenderer::new);
        EntityRendererRegistry.register(ModEntityType.RIPPER_SERVANT, RipperServantRenderer::new);
        EntityRendererRegistry.register(ModEntityType.TRAMPLER_SERVANT, AllyTramplerRenderer::new);
        EntityRendererRegistry.register(ModEntityType.RAVAGED, RavagedRenderer::new);
        EntityRendererRegistry.register(ModEntityType.MOD_RAVAGER, ModRavagerRenderer::new);
        EntityRendererRegistry.register(ModEntityType.ARMORED_RAVAGER, ModRavagerRenderer::new);
        EntityRendererRegistry.register(ModEntityType.ZOMBIE_RAVAGER, ZombieRavagerRenderer::new);
        EntityRendererRegistry.register(ModEntityType.WITCH_SERVANT, WitchServantRenderer::new);
        EntityRendererRegistry.register(ModEntityType.WARLOCK_SERVANT, WarlockServantRenderer::new);
        EntityRendererRegistry.register(ModEntityType.HERETIC_SERVANT, HereticServantRenderer::new);
        EntityRendererRegistry.register(ModEntityType.MAVERICK_SERVANT, MaverickServantRenderer::new);
        EntityRendererRegistry.register(ModEntityType.REPROBATE_SERVANT, ReprobateServantRenderer::new);
        EntityRendererRegistry.register(ModEntityType.BLACK_WOLF, BlackWolfRenderer::new);
        EntityRendererRegistry.register(ModEntityType.SKELETON_WOLF, SkeletonWolfRenderer::new);
        EntityRendererRegistry.register(ModEntityType.WINTER_WOLF, WinterWolfRenderer::new);
        EntityRendererRegistry.register(ModEntityType.STORMHOUND, StormhoundRenderer::new);
        EntityRendererRegistry.register(ModEntityType.HELLHOUND, HellhoundRenderer::new);
        EntityRendererRegistry.register(ModEntityType.TWILIGHT_GOAT, TwilightGoatRenderer::new);
        EntityRendererRegistry.register(ModEntityType.SNAPPER, SnapperRenderer::new);
        EntityRendererRegistry.register(ModEntityType.GNASHER, GnasherRenderer::new);
        EntityRendererRegistry.register(ModEntityType.GUARDIAN_SERVANT, GuardianServantRenderer::new);
        EntityRendererRegistry.register(ModEntityType.ELDER_GUARDIAN_SERVANT, ElderGuardianServantRenderer::new);
        EntityRendererRegistry.register(ModEntityType.BEAR_SERVANT, BearServantRenderer::new);
        EntityRendererRegistry.register(ModEntityType.POLAR_BEAR_SERVANT, BearServantRenderer::new);
        EntityRendererRegistry.register(ModEntityType.HOGLIN_SERVANT, HoglinServantRenderer::new);
        EntityRendererRegistry.register(ModEntityType.BLACK_BEAST, BlackBeastRenderer::new);
        EntityRendererRegistry.register(ModEntityType.WHISPERER, WhispererRenderer::new);
        EntityRendererRegistry.register(ModEntityType.WAVEWHISPERER, WhispererRenderer::new);
        EntityRendererRegistry.register(ModEntityType.LEAPLEAF, LeapleafRenderer::new);
        EntityRendererRegistry.register(ModEntityType.STONE_MINISTROSITY, StoneMinistrosityRenderer::new);
        EntityRendererRegistry.register(ModEntityType.REDSTONE_MINISTROSITY, RedstoneMinistrosityRenderer::new);
        EntityRendererRegistry.register(ModEntityType.ICE_GOLEM, IceGolemRenderer::new);
        EntityRendererRegistry.register(ModEntityType.SQUALL_GOLEM, SquallGolemRenderer::new);
        EntityRendererRegistry.register(ModEntityType.REDSTONE_GOLEM, RedstoneGolemRenderer::new);
        EntityRendererRegistry.register(ModEntityType.GRAVE_GOLEM, GraveGolemRenderer::new);
        EntityRendererRegistry.register(ModEntityType.HAUNT, HauntRenderer::new);
        EntityRendererRegistry.register(ModEntityType.REDSTONE_MONSTROSITY, RedstoneMonstrosityRenderer::new);
        EntityRendererRegistry.register(ModEntityType.REDSTONE_CUBE, RedstoneCubeRenderer::new);
        EntityRendererRegistry.register(ModEntityType.WATCHLING_SERVANT, WatchlingRenderer::new);
        EntityRendererRegistry.register(ModEntityType.BLASTLING_SERVANT, BlastlingRenderer::new);
        EntityRendererRegistry.register(ModEntityType.SNARELING_SERVANT, SnarelingRenderer::new);
        EntityRendererRegistry.register(ModEntityType.SORCERER, SorcererRenderer::new);
        EntityRendererRegistry.register(ModEntityType.ENVIOKER, EnviokerRenderer::new);
        EntityRendererRegistry.register(ModEntityType.TORMENTOR, TormentorRenderer::new);
        EntityRendererRegistry.register(ModEntityType.INQUILLAGER, InquillagerRenderer::new);
        EntityRendererRegistry.register(ModEntityType.CONQUILLAGER, ConquillagerRenderer::new);
        EntityRendererRegistry.register(ModEntityType.PIKER, PikerRenderer::new);
        EntityRendererRegistry.register(ModEntityType.RIPPER, RipperRenderer::new);
        EntityRendererRegistry.register(ModEntityType.TRAMPLER, TramplerRenderer::new);
        EntityRendererRegistry.register(ModEntityType.CRUSHER, CrusherRenderer::new);
        EntityRendererRegistry.register(ModEntityType.STORM_CASTER, StormCasterRenderer::new);
        EntityRendererRegistry.register(ModEntityType.CRYOLOGER, CryologerRenderer::new);
        EntityRendererRegistry.register(ModEntityType.PREACHER, PreacherRenderer::new);
        EntityRendererRegistry.register(ModEntityType.MINISTER, MinisterRenderer::new);
        EntityRendererRegistry.register(ModEntityType.HOSTILE_REDSTONE_GOLEM, HostileRedstoneGolemRenderer::new);
        EntityRendererRegistry.register(ModEntityType.HOSTILE_REDSTONE_MONSTROSITY, RedstoneMonstrosityRenderer::new);
        EntityRendererRegistry.register(ModEntityType.VIZIER, VizierRenderer::new);
        EntityRendererRegistry.register(ModEntityType.VIZIER_CLONE, VizierCloneRenderer::new);
        EntityRendererRegistry.register(ModEntityType.IRK, IrkRenderer::new);
        EntityRendererRegistry.register(ModEntityType.WIGHT, WightRenderer::new);
        EntityRendererRegistry.register(ModEntityType.CARRION_MAGGOT, CarrionMaggotRenderer::new);
        EntityRendererRegistry.register(ModEntityType.CARRION_FLY, CarrionFlyRenderer::new);
        EntityRendererRegistry.register(ModEntityType.SKULL_LORD, SkullLordRenderer::new);
        EntityRendererRegistry.register(ModEntityType.BONE_LORD, BoneLordRenderer::new);
        EntityRendererRegistry.register(ModEntityType.WITHER_NECROMANCER, WitherNecromancerRenderer::new);
        EntityRendererRegistry.register(ModEntityType.ARROW_RAIN_TRAP, TrapRenderer::new);
        EntityRendererRegistry.register(ModEntityType.FIRE_BLAST_TRAP, TrapRenderer::new);
        EntityRendererRegistry.register(ModEntityType.FIRE_RAIN_TRAP, TrapRenderer::new);
        EntityRendererRegistry.register(ModEntityType.FIRE_TORNADO_TRAP, TrapRenderer::new);
        EntityRendererRegistry.register(ModEntityType.LIGHTNING_TRAP, TrapRenderer::new);
        EntityRendererRegistry.register(ModEntityType.MAGIC_LIGHTNING_TRAP, TrapRenderer::new);
        EntityRendererRegistry.register(ModEntityType.VOID_LIGHTNING_TRAP, TrapRenderer::new);
        EntityRendererRegistry.register(ModEntityType.UPDRAFT_BLAST, TrapRenderer::new);
        EntityRendererRegistry.register(ModEntityType.EFFECT_BLAST_TRAP, TrapRenderer::new);
        EntityRendererRegistry.register(ModEntityType.CUSHION, TrapRenderer::new);
        EntityRendererRegistry.register(ModEntityType.MAGIC_GROUND, TrapRenderer::new);
        EntityRendererRegistry.register(ModEntityType.ACID_POOL, TrapRenderer::new);
        EntityRendererRegistry.register(ModEntityType.FIRE_PILLAR, TrapRenderer::new);
        EntityRendererRegistry.register(ModEntityType.VOID_RIFT, VoidRiftRenderer::new);
        EntityRendererRegistry.register(ModEntityType.STORM_UTIL, TrapRenderer::new);
        EntityRendererRegistry.register(ModEntityType.SUMMON_APOSTLE, SummonApostleRenderer::new);
        EntityRendererRegistry.register(ModEntityType.HAIL_CLOUD, TrapRenderer::new);
        EntityRendererRegistry.register(ModEntityType.MONSOON_CLOUD, TrapRenderer::new);
        EntityRendererRegistry.register(ModEntityType.HELL_CLOUD, TrapRenderer::new);
        EntityRendererRegistry.register(ModEntityType.SPELL_LIGHTNING_BOLT, SpellLightningBoltRenderer::new);
        EntityRendererRegistry.register(ModEntityType.ALLIED_EFFECT_CLOUD, TrapRenderer::new);
        EntityRendererRegistry.register(ModEntityType.BREW_EFFECT_CLOUD, TrapRenderer::new);
        EntityRendererRegistry.register(ModEntityType.DRAGON_BREATH_CLOUD, TrapRenderer::new);
        EntityRendererRegistry.register(ModEntityType.VINE_HOOK, VineHookRenderer::new);
        EntityRendererRegistry.register(ModEntityType.SURVEY_EYE, TrapRenderer::new);
        EntityRendererRegistry.register(ModEntityType.CAMERA_SHAKE, TrapRenderer::new);
        EntityRendererRegistry.register(ModEntityType.SEAT, TrapRenderer::new);
    }

    private static void colorBlock() {
        ColorProviderRegistry.BLOCK.register(
                (state, lightReader, pos, color) ->
                        lightReader != null && pos != null ?
                                Minecraft.getInstance().level != null
                                        && Minecraft.getInstance().level.getBlockEntity(pos) instanceof BrewCauldronBlockEntity cauldronBlock
                                ? cauldronBlock.getColor() :
                                        BiomeColors.getAverageWaterColor(lightReader, pos) : -1, ModBlocks.BREWING_CAULDRON);

        ColorProviderRegistry.BLOCK.register(
                (state, lightReader, pos, color) ->
                        lightReader != null && pos != null ?
                                BiomeColors.getAverageWaterColor(lightReader, pos) :
                                -1, ModBlocks.HAUNTED_JUG);

        ColorProviderRegistry.BLOCK.register(
                (state, lightReader, pos, color) ->
                        lightReader != null && pos != null ?
                                BiomeColors.getAverageFoliageColor(lightReader, pos) :
                                FoliageColor.getDefaultColor(), ModBlocks.HARDENED_LEAVES, ModBlocks.ROTTEN_LEAVES);
    }

    private static void colorItem() {
        ColorProviderRegistry.ITEM.register((itemStack, i) -> i > 0 ? -1 : PotionUtils.getColor(itemStack),
                ModItems.BREW, ModItems.SPLASH_BREW, ModItems.LINGERING_BREW, ModItems.GAS_BREW);

        ColorProviderRegistry.ITEM.register((itemStack, i) -> 3694022, ModBlocks.HAUNTED_JUG);

        ColorProviderRegistry.ITEM.register((itemStack, i) -> {
            BlockState blockstate = ((BlockItem) itemStack.getItem()).getBlock().defaultBlockState();
            return ColorProviderRegistry.BLOCK.get(blockstate.getBlock()).getColor(blockstate, null, null, i);
        }, ModBlocks.HARDENED_LEAVES, ModBlocks.ROTTEN_LEAVES);
    }

    public static BakedModel modelBake(BakedModel bakedModel, ModelModifier.AfterBake.Context context) {
        var id = context.id();
        if (id.getNamespace().equals(Goety.MOD_ID)
                && id.getPath().contains("leaves")
                && !id.getPath().contains("mcd")
                && !id.getPath().contains("chorus")) {
            return new BakedLeavesModel(bakedModel);
        }

        if (id.getNamespace().equals(Goety.MOD_ID)
                && id.getPath().contains("leaves")
                && (id.getPath().contains("mcd") || id.getPath().contains("chorus"))) {
            return new FullLeavesModel(bakedModel);
        }

        return bakedModel;
    }

    public static void registerModels(ModelLoadingPlugin.Context plugin) {
        plugin.addModels(MagicShieldLayer.SHIELD);
    }

//    @SubscribeEvent
//    public static void registerRecipeBookCategory(RegisterRecipeBookCategoriesEvent event) {
//        event.registerRecipeCategoryFinder(ModRecipeSerializer.CURSED_INFUSER, recipe -> RecipeBookCategories.UNKNOWN);
//        event.registerRecipeCategoryFinder(ModRecipeSerializer.SOUL_ABSORBER, recipe -> RecipeBookCategories.UNKNOWN);
//        event.registerRecipeCategoryFinder(ModRecipeSerializer.RITUAL_TYPE, recipe -> RecipeBookCategories.UNKNOWN);
//        event.registerRecipeCategoryFinder(ModRecipeSerializer.BRAZIER_TYPE, recipe -> RecipeBookCategories.UNKNOWN);
//        event.registerRecipeCategoryFinder(ModRecipeSerializer.CAULDRON_TYPE, recipe -> RecipeBookCategories.UNKNOWN);
//        event.registerRecipeCategoryFinder(ModRecipeSerializer.BREWING_TYPE, recipe -> RecipeBookCategories.UNKNOWN);
//        event.registerRecipeCategoryFinder(ModRecipeSerializer.PULVERIZE_TYPE, recipe -> RecipeBookCategories.UNKNOWN);
//    }

//    // impl in GuiGraphicsMixin
//    @SubscribeEvent
//    public static void registerItemDecorators(RegisterItemDecorationsEvent event) {
//        for (Item item : ForgeRegistries.ITEMS.getValues()) {
//            if (item instanceof IPersist) {
//                event.register(item, new IPersistDecorator());
//            }
//        }
//    }
}
