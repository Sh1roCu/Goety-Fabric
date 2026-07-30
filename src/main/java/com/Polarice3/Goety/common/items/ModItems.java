package com.Polarice3.Goety.common.items;

import com.Polarice3.Goety.Goety;
import com.Polarice3.Goety.api.items.magic.ITotem;
import com.Polarice3.Goety.api.magic.SpellType;
import com.Polarice3.Goety.common.blocks.fluids.ModFluids;
import com.Polarice3.Goety.common.entities.vehicle.ModBoat;
import com.Polarice3.Goety.common.items.armor.*;
import com.Polarice3.Goety.common.items.block.HauntedArmorStandItem;
import com.Polarice3.Goety.common.items.block.HauntedPaintingItem;
import com.Polarice3.Goety.common.items.brew.BrewBag;
import com.Polarice3.Goety.common.items.brew.BrewItem;
import com.Polarice3.Goety.common.items.brew.LingeringBrewItem;
import com.Polarice3.Goety.common.items.brew.SplashBrewItem;
import com.Polarice3.Goety.common.items.curios.*;
import com.Polarice3.Goety.common.items.equipment.*;
import com.Polarice3.Goety.common.items.magic.*;
import com.Polarice3.Goety.common.items.research.ExtraScroll;
import com.Polarice3.Goety.common.items.research.ForbiddenScroll;
import com.Polarice3.Goety.common.items.research.ResearchScroll;
import com.Polarice3.Goety.common.items.research.Scroll;
import com.Polarice3.Goety.common.items.revive.BlazingHelm;
import com.Polarice3.Goety.common.items.revive.HowlingSoul;
import com.Polarice3.Goety.common.items.revive.SoulJar;
import com.Polarice3.Goety.common.magic.spells.*;
import com.Polarice3.Goety.common.magic.spells.abyss.*;
import com.Polarice3.Goety.common.magic.spells.frost.*;
import com.Polarice3.Goety.common.magic.spells.geomancy.*;
import com.Polarice3.Goety.common.magic.spells.necromancy.*;
import com.Polarice3.Goety.common.magic.spells.nether.*;
import com.Polarice3.Goety.common.magic.spells.nether.FireBreathSpell;
import com.Polarice3.Goety.common.magic.spells.storm.*;
import com.Polarice3.Goety.common.magic.spells.utility.CraftingSpell;
import com.Polarice3.Goety.common.magic.spells.utility.GlowLightSpell;
import com.Polarice3.Goety.common.magic.spells.utility.IlluminateSpell;
import com.Polarice3.Goety.common.magic.spells.utility.SoulLightSpell;
import com.Polarice3.Goety.common.magic.spells.void_spells.*;
import com.Polarice3.Goety.common.magic.spells.wild.*;
import com.Polarice3.Goety.common.magic.spells.wind.*;
import com.Polarice3.Goety.common.research.ResearchList;
import com.Polarice3.Goety.config.ItemConfig;
import com.Polarice3.Goety.init.ModSounds;
import com.Polarice3.Goety.init.ModTags;
import com.Polarice3.Goety.utils.MathHelper;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.Foods;
import net.minecraft.world.item.*;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.function.Supplier;

public class ModItems {

    public static final Set<Item> ITEMS = new LinkedHashSet<>();
    public static final Set<Item> SPAWN_EGGS = new LinkedHashSet<>();
    public static final Set<Item> SERVANT_SPAWN_EGGS = new LinkedHashSet<>();

    public static final FullSpentTotem TOTEM_OF_ROOTS = register("totem_of_roots", () -> new FullSpentTotem(Math.max(ITotem.MAX_SOULS / 100, 5)));
    public static final TotemOfSouls TOTEM_OF_SOULS = register("totem_of_souls", () -> new TotemOfSouls(ITotem.MAX_SOULS));

    //Basic
    public static final Item SPENT_TOTEM = register("spent_totem", ItemBase::new);
    public static final Item CURSED_METAL_INGOT = register("cursed_ingot", ItemBase::new);
    public static final Item PALE_STEEL_INGOT = register("pale_steel_ingot", ItemBase::new);
    public static final Item DARK_ALLOY_INGOT = register("dark_ingot", ItemBase::new);
    public static final Item ECTOPLASM = register("ectoplasm", ItemBase::new);
    public static final Item SHADOW_ESSENCE = register("shadow_essence", ItemBase::new);
    public static final Item DARK_FABRIC = register("dark_fabric", ItemBase::new);
    public static final Item MAGIC_FABRIC = register("magic_fabric", ItemBase::new);
    public static final Item OCCULT_FABRIC = register("occult_fabric", ItemBase::new);
    public static final Item SPIRIT_FABRIC = register("spirit_fabric", ItemBase::new);
    public static final Item GALE_FABRIC = register("gale_fabric", ItemBase::new);
    public static final Item CHILL_FABRIC = register("chill_fabric", ItemBase::new);
    public static final Item UNHOLY_FABRIC = register("unholy_fabric", () -> new Item(new Item.Properties().fireResistant()));
    public static final Item SAVAGE_TOOTH = register("savage_tooth", ItemBase::new);
    public static final Item JADE = register("jade", ItemBase::new);
    public static final Item SPIDER_EGG = register("spider_egg", ItemBase::new);
    public static final Item WARPED_WARTFUL_EGG = register("warped_wartful_egg", ItemBase::new);
    public static final Item VENOMOUS_FANG = register("venomous_fang", ItemBase::new);
    public static final Item GRAVE_DUST = register("grave_dust", ItemBase::new);
    public static final Item RAGING_MATTER = register("raging_matter", ItemBase::new);
    public static final Item ICE_CUBE = register("ice_cube", ItemBase::new);
    public static final Item VOID_KEY = register("void_key", VoidKeyItem::new);
    public static final Item VOID_ECHO = register("void_echo", () -> new Item(new Item.Properties().fireResistant()));
    public static final Item SOUL_RUBY = register("soul_ruby", ItemBase::new);
    public static final Item EMPTY_FOCUS = register("empty_focus", ItemBase::new);
    public static final Item ANIMATION_CORE = register("animation_core", AnimationCore::new);
    public static final Item HUNGER_CORE = register("hunger_core", ItemBase::new);
    public static final Item WIND_CORE = register("wind_core", ItemBase::new);
    public static final Item MYSTIC_CORE = register("mystic_core", ItemBase::new);
    public static final Item VOID_SHARD = register("void_shard", ItemBase::new);
    public static final Item OMINOUS_SHARD = register("ominous_shard", ItemBase::new);
    public static final Item OMINOUS_ORB = register("ominous_orb", () -> new RepeatCraftItem(new Item.Properties().stacksTo(1)));
    public static final Item HEART_OF_THE_NIGHT = register("heart_of_the_night", ItemBase::new);
    public static final Item CAULDRON_LADLE = register("cauldron_ladle", SingleStackItem::new);
    public static final Item OMINOUS_SADDLE = register("ominous_saddle", ItemBase::new);
    public static final Item IRON_TRAMPLER_ARMOR = register("iron_trampler_armor", () -> new TramplerArmorItem(5, "iron"));
    public static final Item GOLD_TRAMPLER_ARMOR = register("gold_trampler_armor", () -> new TramplerArmorItem(7, "gold"));
    public static final Item DIAMOND_TRAMPLER_ARMOR = register("diamond_trampler_armor", () -> new TramplerArmorItem(11, "diamond"));
    public static final Item NETHERITE_TRAMPLER_ARMOR = register("netherite_trampler_armor", () -> new TramplerArmorItem(15, "netherite", new Item.Properties().stacksTo(1).fireResistant()));
    public static final Item IRON_RAVAGER_ARMOR = register("iron_ravager_armor", () -> new RavagerArmorItem(7, "iron"));
    public static final Item GOLD_RAVAGER_ARMOR = register("gold_ravager_armor", () -> new RavagerArmorItem(11, "gold"));
    public static final Item DIAMOND_RAVAGER_ARMOR = register("diamond_ravager_armor", () -> new RavagerArmorItem(15, "diamond"));
    public static final Item NETHERITE_RAVAGER_ARMOR = register("netherite_ravager_armor", () -> new RavagerArmorItem(20, "netherite", new Item.Properties().stacksTo(1).fireResistant()));
    public static final Item WITHERED_MANUSCRIPT = register("withered_manuscript", () -> new Item(new Item.Properties().fireResistant()));
    public static final Item SHROUDED_BLUEPRINT = register("shrouded_blueprint", () -> new Item(new Item.Properties().fireResistant()));
    public static final Item FORBIDDEN_PIECE = register("forbidden_piece", ItemBase::new);
    public static final Item FORBIDDEN_FRAGMENT = register("forbidden_fragment", ItemBase::new);

    public static final Item FEET_OF_FROG = register("feet_of_frog", () -> new Item(new Item.Properties().food(Foods.COD)));
    public static final Item COOKED_FEET_OF_FROG = register("cooked_feet_of_frog", () -> new Item(new Item.Properties().food(Foods.COOKED_COD)));

    public static final Item VOID_BOTTLE = register("void_bottle", VoidBottleItem::new);
    public static final Item VOID_BUCKET = register("void_bucket", () -> new BucketItem(ModFluids.VOID_FLUID_SOURCE, (new Item.Properties()).craftRemainder(Items.BUCKET).stacksTo(1)));

    public static final Item END_MUD_BOTTLE = register("end_mud_bottle", () -> new Item(new Item.Properties().craftRemainder(Items.GLASS_BOTTLE)));
    public static final Item END_MUD_BUCKET = register("end_mud_bucket", () -> new BucketItem(ModFluids.END_MUD_FLUID_SOURCE, (new Item.Properties()).craftRemainder(Items.BUCKET).stacksTo(1)));

    public static final Item PHILOSOPHERS_STONE = register("philosophers_stone", PhilosophersStone::new);
    public static final Item DARK_SCROLL = register("dark_scroll", DarkScrollItem::new);
    public static final Item BLAZING_HORN = register("blazing_horn", BlazingHornItem::new);
    public static final Item MAGIC_EMERALD = register("magic_emerald", () -> new SimpleFoiledItem(new Item.Properties()));
    public static final Item SOUL_EMERALD = register("soul_emerald", () -> new SimpleFoiledItem(new Item.Properties()));
    public static final Item UNHOLY_BLOOD = register("unholy_blood", UnholyBloodItem::new);
    public static final Item SOUL_TRANSFER = register("soul_transfer", SoulTransferItem::new);
    public static final Item FLAME_CAPTURE = register("flame_capture", FlameCaptureItem::new);
    public static final Item SNAP_FUNGUS = register("snap_fungus", SnapFungusItem::new);
    public static final Item BLAST_FUNGUS = register("blast_fungus", BlastFungusItem::new);
    public static final Item BERSERK_FUNGUS = register("berserk_fungus", BerserkFungusItem::new);
    public static final Item WARTFUL_EGG = register("wartful_egg", WartlingEggItem::new);
    public static final Item RED_MOSS_GROWTH = register("red_moss_growth", RedMossGrowthItem::new);
    public static final Item CHORUS_GROWTH = register("chorus_growth", ChorusGrowthItem::new);
    public static final Item HENBANE_FLOWER = register("henbane_flower", () -> new Item(new Item.Properties().food(ModFoods.HENBANE)));
    public static final Item NIGHTSHADE_BLOSSOM = register("nightshade_blossom", () -> new Item(new Item.Properties().food(ModFoods.NIGHTSHADE)));
    public static final Item QUICK_GROWING_SEED = register("quick_growing_seed", () -> new QuickGrowSeedItem(false));
    public static final Item POISON_QUILL_SEED = register("poison_quill_seed", () -> new QuickGrowSeedItem(true));
    public static final Item REFUSE_BOTTLE = register("refuse_bottle", RefuseBottleItem::new);
    public static final Item RESILIENCE_LOTION = register("resilience_lotion", () -> new UnguentItem(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, MathHelper.minutesToTicks(3))));
    public static final Item FLYING_OINTMENT = register("flying_ointment", FlyingOintmentItem::new);
    public static final Item ILL_BOMB = register("ill_bomb", IllBombItem::new);
    public static final Item OMINOUS_SHACKLES = register("ominous_shackles", OminousShacklesItem::new);
    public static final Item CRYPTIC_EYE = register("cryptic_eye", CrypticEyeItem::new);
    public static final Item VOIDED_EYE = register("void_eye", VoidEyeItem::new);
    public static final Item COMMAND_HORN = register("command_horn", CommandHorn::new);
    public static final Item RAIDING_HORN = register("raiding_horn", RaidingHorn::new);
    public static final Item ESOTERIC_TESSERACT = register("esoteric_tesseract", EsotericTesseract::new);
    public static final Item EMPTY_SOUL_JAR = register("empty_soul_jar", ItemBase::new);
    public static final Item SOUL_JAR = register("soul_jar", SoulJar::new);
    public static final Item HOWLING_SOUL = register("howling_soul", HowlingSoul::new);
    public static final Item BLAZING_HELM = register("blazing_helm", BlazingHelm::new);
    public static final Item TAGLOCK_KIT = register("taglock_kit", TaglockKit::new);
    public static final Item WAYSTONE = register("waystone", WaystoneItem::new);
    public static final Item TRANSFER_SCROLL = register("transfer_scroll", TransferScroll::new);
    public static final Item ARCA_COMPASS = register("arca_compass", ArcaCompassItem::new);
    public static final Item GRIMOIRE_OF_GRUDGES = register("grimoire_of_grudges", GrudgeGrimoire::new);
    public static final Item GRIMOIRE_OF_GOODWILL = register("grimoire_of_goodwill", GoodwillGrimoire::new);
    public static final Item GRIMOIRE_OF_GROUNDING = register("grimoire_of_grounding", GroundGrimoire::new);

    public static final Item RAVAGING_SCROLL = register("ravaging_scroll", () -> new Scroll(ResearchList.RAVAGING));
    public static final Item WARRED_SCROLL = register("warred_scroll", () -> new Scroll(ResearchList.WARRED));
    public static final Item BURIED_SCROLL = register("buried_scroll", () -> new Scroll(ResearchList.BURIED));
    public static final Item HAUNTING_SCROLL = register("haunting_scroll", () -> new Scroll(ResearchList.HAUNTING));
    public static final Item FRONT_SCROLL = register("front_scroll", () -> new Scroll(ResearchList.FRONT));
    public static final Item MISTRAL_SCROLL = register("mistral_scroll", () -> new Scroll(ResearchList.MISTRAL));
    public static final Item FLORAL_SCROLL = register("floral_scroll", () -> new Scroll(ResearchList.FLORAL));
    public static final Item BYGONE_SCROLL = register("bygone_scroll", () -> new Scroll(ResearchScroll.fireResistant(), ResearchList.BYGONE));
    public static final Item TERMINUS_SCROLL = register("terminus_scroll", () -> new ExtraScroll(ResearchScroll.fireResistant(), ResearchList.TERMINUS, ResearchList.WARRED));
    public static final Item FORBIDDEN_SCROLL = register("forbidden_scroll", ForbiddenScroll::new);

    public static final Item UNDEATH_POTION = register("undeath_potion", UndeathPotionItem::new);

    public static final Item BREW = register("brew", BrewItem::new);
    public static final Item SPLASH_BREW = register("splash_brew", SplashBrewItem::new);
    public static final Item LINGERING_BREW = register("lingering_brew", LingeringBrewItem::new);
    public static final Item GAS_BREW = register("gas_brew", SplashBrewItem::new);

    public static final Item TREASURE_POUCH = register("treasure_pouch", TreasurePouchItem::new);

    public static final Item HAUNTED_BOAT = register("haunted_boat", () -> new ModBoatItem(false, ModBoat.Type.HAUNTED, (new Item.Properties()).stacksTo(1)));
    public static final Item HAUNTED_CHEST_BOAT = register("haunted_chest_boat", () -> new ModBoatItem(true, ModBoat.Type.HAUNTED, (new Item.Properties()).stacksTo(1)));

    public static final Item ROTTEN_BOAT = register("rotten_boat", () -> new ModBoatItem(false, ModBoat.Type.ROTTEN, (new Item.Properties()).stacksTo(1)));
    public static final Item ROTTEN_CHEST_BOAT = register("rotten_chest_boat", () -> new ModBoatItem(true, ModBoat.Type.ROTTEN, (new Item.Properties()).stacksTo(1)));

    public static final Item WINDSWEPT_BOAT = register("windswept_boat", () -> new ModBoatItem(false, ModBoat.Type.WINDSWEPT, (new Item.Properties()).stacksTo(1)));
    public static final Item WINDSWEPT_CHEST_BOAT = register("windswept_chest_boat", () -> new ModBoatItem(true, ModBoat.Type.WINDSWEPT, (new Item.Properties()).stacksTo(1)));

    public static final Item PINE_BOAT = register("pine_boat", () -> new ModBoatItem(false, ModBoat.Type.PINE, (new Item.Properties()).stacksTo(1)));
    public static final Item PINE_CHEST_BOAT = register("pine_chest_boat", () -> new ModBoatItem(true, ModBoat.Type.PINE, (new Item.Properties()).stacksTo(1)));

    public static final Item CHORUS_BOAT = register("chorus_boat", () -> new ModBoatItem(false, ModBoat.Type.CHORUS, (new Item.Properties()).stacksTo(1)));
    public static final Item CHORUS_CHEST_BOAT = register("chorus_chest_boat", () -> new ModBoatItem(true, ModBoat.Type.CHORUS, (new Item.Properties()).stacksTo(1)));

    public static final Item CORRUPT_CHORUS_BOAT = register("corrupt_chorus_boat", () -> new ModBoatItem(false, ModBoat.Type.CORRUPT_CHORUS, (new Item.Properties()).stacksTo(1)));
    public static final Item CORRUPT_CHORUS_CHEST_BOAT = register("corrupt_chorus_chest_boat", () -> new ModBoatItem(true, ModBoat.Type.CORRUPT_CHORUS, (new Item.Properties()).stacksTo(1)));

    public static final Item HAUNTED_BROOM = register("haunted_broom", HauntedBroomItem::new);

    public static final Item HAUNTED_ARMOR_STAND = register("haunted_armor_stand", HauntedArmorStandItem::new);
    public static final Item HAUNTED_PAINTING = register("haunted_painting", HauntedPaintingItem::new);

    public static final Item CROSS_BANNER_PATTERN = register("cross_banner_pattern", () -> new BannerPatternItem(ModTags.BannerPatterns.PATTERN_ITEM_CROSS, (new Item.Properties()).stacksTo(1)));
    public static final Item GALE_BANNER_PATTERN = register("gale_banner_pattern", () -> new BannerPatternItem(ModTags.BannerPatterns.PATTERN_ITEM_GALE, (new Item.Properties()).stacksTo(1)));
    public static final Item MOON_BANNER_PATTERN = register("moon_banner_pattern", () -> new BannerPatternItem(ModTags.BannerPatterns.PATTERN_ITEM_MOON, (new Item.Properties()).stacksTo(1)));

    //Curios
    public static final Item FOCUS_BAG = register("focus_bag", FocusBag::new);
    public static final Item FOCUS_PACK = register("focus_pack", FocusPack::new);
    public static final Item BREW_BAG = register("brew_bag", BrewBag::new);
    public static final SingleStackItem RING_OF_WANT = register("ring_of_want", RingItem::new);
    public static final SingleStackItem RING_OF_THIRST = register("ring_of_thirst", RingItem::new);
    public static final SingleStackItem RING_OF_FORCE = register("ring_of_force", RingItem::new);
    public static final SingleStackItem RING_OF_THE_FORGE = register("ring_of_the_forge", RingItem::new);
    public static final SingleStackItem RING_OF_THE_DRAGON = register("ring_of_the_dragon", RingItem::new);
    public static final SingleStackItem PENDANT_OF_HUNGER = register("pendant_of_hunger", PendantOfHungerItem::new);
    public static final SingleStackItem TARGETING_MONOCLE = register("targeting_monocle", TargetingMonocleItem::new);
    public static final SingleStackItem DARK_HAT = register("dark_hat", MagicHatItem::new);
    public static final SingleStackItem GRAND_TURBAN = register("grand_turban", MagicHatItem::new);
    public static final SingleStackItem FROST_CROWN = register("frost_crown", () -> new MagicCrownItem(SpellType.FROST));
    public static final SingleStackItem WIND_CROWN = register("wind_crown", () -> new MagicCrownItem(SpellType.WIND));
    public static final SingleStackItem STORM_CROWN = register("storm_crown", () -> new MagicCrownItem(SpellType.STORM));
    public static final SingleStackItem WILD_CROWN = register("wild_crown", () -> new MagicCrownItem(SpellType.WILD));
    public static final SingleStackItem ABYSS_CROWN = register("abyss_crown", () -> new MagicCrownItem(SpellType.ABYSS));
    public static final SingleStackItem VOID_CROWN = register("void_crown", () -> new MagicCrownItem(SpellType.VOID));
    public static final SingleStackItem NETHER_CROWN = register("nether_crown", () -> new MagicCrownItem(new Item.Properties().fireResistant().stacksTo(1), SpellType.NETHER));
    public static final SingleStackItem NECRO_CROWN = register("necro_crown", NecroGarbs.NecroCrownItem::new);
    public static final SingleStackItem NAMELESS_CROWN = register("nameless_crown", () -> new NecroGarbs.NecroCrownItem(true));
    public static final SingleStackItem AMETHYST_NECKLACE = register("amethyst_necklace", SingleStackItem::new);
    public static final SingleStackItem WITCH_HAT = register("witch_hat", WitchHatItem::new);
    public static final SingleStackItem WITCH_HAT_HEDGE = register("witch_hat_hedge", WitchHatItem::new);
    public static final SingleStackItem CRONE_HAT = register("crone_hat", WitchHatItem::new);
    public static final SingleStackItem UNHOLY_HAT = register("unholy_hat", UnholyHatItem::new);
    public static final SingleStackItem UNHOLY_HAT_HALO = register("unholy_hat_halo", UnholyHatItem::new);
    public static final SingleStackItem DARK_ROBE = register("dark_robe", MagicRobeItem::new);
    public static final SingleStackItem DARK_ROBE_FANCY = register("dark_robe_fancy", MagicRobeItem::new);
    public static final SingleStackItem GRAND_ROBE = register("grand_robe", MagicRobeItem::new);
    public static final SingleStackItem NECRO_CAPE = register("necro_cape", () -> new NecroGarbs.NecroCapeItem(false));
    public static final SingleStackItem NAMELESS_CAPE = register("nameless_cape", () -> new NecroGarbs.NecroCapeItem(true));
    public static final SingleStackItem ILLUSION_ROBE = register("illusion_robe", IllusionRobeItem::new);
    public static final SingleStackItem ILLUSION_ROBE_MIRROR = register("illusion_robe_mirror", IllusionRobeItem::new);
    public static final SingleStackItem GEO_ROBE = register("geo_robe", SingleStackItem::new);
    public static final SingleStackItem FROST_ROBE = register("frost_robe", FrostRobeItem::new);
    public static final SingleStackItem FROST_ROBE_CRYO = register("frost_robe_cryo", FrostRobeItem::new);
    public static final SingleStackItem WIND_ROBE = register("wind_robe", WindyRobeItem::new);
    public static final SingleStackItem STORM_ROBE = register("storm_robe", WindyRobeItem::new);
    public static final SingleStackItem WILD_ROBE = register("wild_robe", WildRobeItem::new);
    public static final SingleStackItem ABYSS_ROBE = register("abyss_robe", AbyssRobeItem::new);
    public static final SingleStackItem VOID_ROBE = register("void_robe", VoidRobeItem::new);
    public static final SingleStackItem WITCH_ROBE = register("witch_robe", WitchRobeItem::new);
    public static final SingleStackItem WITCH_ROBE_HEDGE = register("witch_robe_hedge", WitchRobeItem::new);
    public static final SingleStackItem WARLOCK_ROBE = register("warlock_robe", WarlockRobeItem::new);
    public static final SingleStackItem WARLOCK_ROBE_DARK = register("warlock_robe_dark", WarlockRobeItem::new);
    public static final SingleStackItem WARLOCK_SASH = register("warlock_sash", WarlockGarmentItem::new);
    public static final SingleStackItem NETHER_ROBE = register("nether_robe", NetherRobeItem::new);
    public static final SingleStackItem NETHER_ROBE_WARPED = register("nether_robe_warped", NetherRobeItem::new);
    public static final SingleStackItem UNHOLY_ROBE = register("unholy_robe", UnholyRobeItem::new);
    public static final SingleStackItem ETERNAL_CAULDRON = register("eternal_cauldron", EternalCauldronItem::new);
    public static final SingleStackItem SEA_AMULET = register("sea_amulet", SeaAmuletItem::new);
    public static final SingleStackItem FELINE_AMULET = register("feline_amulet", SingleStackItem::new);
    public static final SingleStackItem ALARMING_CHARM = register("alarming_charm", SingleStackItem::new);
    public static final SingleStackItem OMINOUS_CHARM = register("ominous_charm", OminousCharmItem::new);
    public static final SingleStackItem WAYFARERS_BELT = register("wayfarers_belt", WayfarersBeltItem::new);
    public static final SingleStackItem SPITEFUL_BELT = register("spiteful_belt", SingleStackItem::new);
    public static final SingleStackItem STAR_AMULET = register("star_amulet", SingleFoiledStackItem::new);
    public static final SingleStackItem GRAVE_GLOVE = register("grave_glove", GloveItem::new);
    public static final SingleStackItem THRASH_GLOVE = register("thrash_glove", GloveItem::new);

    //Focus
    /// Magic
    public static final Item VEXING_FOCUS = register("vexing_focus", () -> new MagicFocus(new VexSpell()));
    public static final Item BITING_FOCUS = register("biting_focus", () -> new MagicFocus(new FangSpell()));
    public static final Item FEAST_FOCUS = register("feast_focus", () -> new MagicFocus(new FeastSpell()));
    public static final Item TEETH_FOCUS = register("teeth_focus", () -> new MagicFocus(new TeethSpell()));
    public static final Item SHREDDING_FOCUS = register("shredding_focus", () -> new MagicFocus(new SpikeSpell()));
    public static final Item ILLUSION_FOCUS = register("illusion_focus", () -> new MagicFocus(new IllusionSpell()));
    public static final Item IGNITE_FOCUS = register("ignite_focus", () -> new MagicFocus(new IgniteSpell()));
    public static final Item SOUL_BOLT_FOCUS = register("soul_bolt_focus", () -> new MagicFocus(new SoulBoltSpell()));
    public static final Item MAGIC_BOLT_FOCUS = register("magic_bolt_focus", () -> new MagicFocus(new MagicBoltSpell()));
    public static final Item SWORD_FOCUS = register("sword_focus", () -> new MagicFocus(new SwordSpell()));
    public static final Item SOUL_LIGHT_FOCUS = register("soul_light_focus", () -> new MagicFocus(new SoulLightSpell()));
    public static final Item GLOW_LIGHT_FOCUS = register("glow_light_focus", () -> new MagicFocus(new GlowLightSpell()));
    public static final Item ILLUMINATE_FOCUS = register("illuminate_focus", () -> new MagicFocus(new IlluminateSpell()));
    public static final Item CRAFTING_FOCUS = register("crafting_focus", () -> new MagicFocus(new CraftingSpell()));
    public static final Item IRON_HIDE_FOCUS = register("iron_hide_focus", () -> new MagicFocus(new IronHideSpell()));
    public static final Item BULWARK_FOCUS = register("bulwark_focus", () -> new MagicFocus(new BulwarkSpell()));
    public static final Item SOUL_HEAL_FOCUS = register("soul_heal_focus", () -> new MagicFocus(new SoulHealSpell()));
    public static final Item SHOCKWAVE_FOCUS = register("shockwave_focus", () -> new MagicFocus(new ShockwaveSpell()));
    public static final Item WEAKENING_FOCUS = register("weakening_focus", () -> new MagicFocus(new WeakeningSpell()));
    public static final Item ARROW_RAIN_FOCUS = register("arrow_rain_focus", () -> new MagicFocus(new ArrowRainSpell()));
    public static final Item TELEKINESIS_FOCUS = register("telekinesis_focus", () -> new MagicFocus(new TelekinesisSpell()));
    public static final Item COMMAND_FOCUS = register("command_focus", CommandFocus::new);
    public static final Item ORDER_FOCUS = register("order_focus", OrderFocus::new);
    public static final Item SONIC_BOOM_FOCUS = register("sonic_boom_focus", () -> new MagicFocus(new SonicBoomSpell()));
    public static final Item CORRUPTION_FOCUS = register("corruption_focus", () -> new MagicFocus(new CorruptedBeamSpell()));

    /// Necromancy
    public static final Item ROTTING_FOCUS = register("rotting_focus", () -> new MagicFocus(new ZombieSpell()));
    public static final Item OSSEOUS_FOCUS = register("osseous_focus", () -> new MagicFocus(new SkeletonSpell()));
    public static final Item GHOST_FIRE_FOCUS = register("ghost_fire_focus", () -> new MagicFocus(new IceBouquetSpell()));
    public static final Item REAPING_FOCUS = register("reaping_focus", () -> new MagicFocus(new ReaperSpell()));
    public static final Item SPOOKY_FOCUS = register("spooky_focus", () -> new MagicFocus(new WraithSpell()));
    public static final Item PHANTASM_FOCUS = register("phantasm_focus", () -> new MagicFocus(new PhantomSpell()));
    public static final Item VANGUARD_FOCUS = register("vanguard_focus", () -> new MagicFocus(new VanguardSpell()));
    public static final Item BLACKGUARD_FOCUS = register("blackguard_focus", () -> new MagicFocus(new BlackguardSpell()));
    public static final Item LEECHING_FOCUS = register("leeching_focus", () -> new MagicFocus(new LeechingSpell()));
    public static final Item KILLING_FOCUS = register("killing_focus", () -> new MagicFocus(new KillingSpell()));
    public static final Item SKULL_FOCUS = register("skull_focus", () -> new MagicFocus(new HauntedSkullSpell()));

    /// Geomancy
    public static final Item BARRICADE_FOCUS = register("barricade_focus", () -> new MagicFocus(new BarricadeSpell()));
    public static final Item QUAKING_FOCUS = register("quaking_focus", () -> new MagicFocus(new QuakingSpell()));
    public static final Item EARTH_PUNCH_FOCUS = register("earth_punch_focus", () -> new MagicFocus(new EarthFistSpell()));
    public static final Item SMACK_STONE_FOCUS = register("smack_stone_focus", () -> new MagicFocus(new SmackStoneSpell()));
    public static final Item MINISTROUS_FOCUS = register("ministrous_focus", () -> new MagicFocus(new MinistrousSpell()));
    public static final Item PULVERIZE_FOCUS = register("pulverize_focus", () -> new MagicFocus(new PulverizeSpell()));
    public static final Item ROTATION_FOCUS = register("rotation_focus", () -> new MagicFocus(new RotationSpell()));
    public static final Item BURROWING_FOCUS = register("burrowing_focus", () -> new MagicFocus(new BurrowingSpell()));
    public static final Item SENSING_FOCUS = register("sensing_focus", () -> new MagicFocus(new SensingSpell()));
    public static final Item SCATTER_FOCUS = register("scatter_focus", () -> new MagicFocus(new ScatterSpell()));
    public static final Item ERUPTION_FOCUS = register("eruption_focus", () -> new MagicFocus(new EruptionSpell()));

    /// Frost
    public static final Item FROST_BREATH_FOCUS = register("frost_breath_focus", () -> new MagicFocus(new FrostBreathSpell()));
    public static final Item ICE_SPIKE_FOCUS = register("ice_spike_focus", () -> new MagicFocus(new IceSpikeSpell()));
    public static final Item ICE_STORM_FOCUS = register("ice_storm_focus", () -> new MagicFocus(new IceStormSpell()));
    public static final Item HAIL_FOCUS = register("hail_focus", () -> new MagicFocus(new HailSpell()));
    public static final Item ICEOLOGY_FOCUS = register("iceology_focus", () -> new MagicFocus(new IceChunkSpell()));
    public static final Item BLIZZARD_FOCUS = register("blizzard_focus", () -> new MagicFocus(new BlizzardSpell()));
    public static final Item CHILLING_FOCUS = register("chilling_focus", () -> new MagicFocus(new ChillHideSpell()));
    public static final Item FROST_NOVA_FOCUS = register("frost_nova_focus", () -> new MagicFocus(new FrostNovaSpell()));
    public static final Item FROSTBORN_FOCUS = register("frostborn_focus", () -> new MagicFocus(new IceGolemSpell()));

    /// Wild
    public static final Item SWARM_FOCUS = register("swarm_focus", () -> new MagicFocus(new SwarmSpell()));
    public static final Item POISON_DART_FOCUS = register("poison_dart_focus", () -> new MagicFocus(new PoisonDartSpell()));
    public static final Item BLOSSOMING_FOCUS = register("blossoming_focus", () -> new MagicFocus(new BlossomSpell()));
    public static final Item GRAPPLE_FOCUS = register("grapple_focus", () -> new MagicFocus(new GrappleSpell()));
    public static final Item HUNTING_FOCUS = register("hunting_focus", () -> new MagicFocus(new HuntingSpell()));
    public static final Item MAULING_FOCUS = register("mauling_focus", () -> new MagicFocus(new MaulingSpell()));
    public static final Item SLIMY_FOCUS = register("slimy_focus", () -> new MagicFocus(new SlimySpell()));
    public static final Item CARRION_FOCUS = register("carrion_focus", () -> new MagicFocus(new CarrionSpell()));
    public static final Item OVERGROWTH_FOCUS = register("overgrowth_focus", () -> new MagicFocus(new OvergrowthSpell()));
    public static final Item ENTANGLING_FOCUS = register("entangling_focus", () -> new MagicFocus(new EntanglingSpell()));
    public static final Item WHISPERING_FOCUS = register("whispering_focus", () -> new MagicFocus(new WhisperSpell()));
    public static final Item LEAPING_FOCUS = register("leaping_focus", () -> new MagicFocus(new LeapingSpell()));

    /// Wind
    public static final Item LAUNCH_FOCUS = register("launch_focus", () -> new MagicFocus(new LaunchSpell()));
    public static final Item FLYING_FOCUS = register("flying_focus", () -> new MagicFocus(new FlyingSpell()));
    public static final Item CUSHION_FOCUS = register("cushion_focus", () -> new MagicFocus(new CushionSpell()));
    public static final Item WHIRLWIND_FOCUS = register("whirlwind_focus", () -> new MagicFocus(new WhirlwindSpell()));
    public static final Item CYCLONE_FOCUS = register("cyclone_focus", () -> new MagicFocus(new CycloneSpell()));
    public static final Item UPDRAFT_FOCUS = register("updraft_focus", () -> new MagicFocus(new UpdraftSpell()));
    public static final Item WIND_BLAST_FOCUS = register("wind_blast_focus", () -> new MagicFocus(new WindBlastSpell()));
    public static final Item RAZOR_WIND_FOCUS = register("razor_wind_focus", () -> new MagicFocus(new RazorWindSpell()));
    public static final Item TREMBLING_FOCUS = register("trembling_focus", () -> new MagicFocus(new WindHornSpell()));

    /// Storm
    public static final Item CHARGE_FOCUS = register("charge_focus", () -> new MagicFocus(new ChargeSpell()));
    public static final Item SHOCKING_FOCUS = register("shocking_focus", () -> new MagicFocus(new ShockingSpell()));
    public static final Item THUNDERBOLT_FOCUS = register("thunderbolt_focus", () -> new MagicFocus(new ThunderboltSpell()));
    public static final Item ELECTROCUTE_FOCUS = register("electrocute_focus", () -> new MagicFocus(new ElectroOrbSpell()));
    public static final Item SURGING_FOCUS = register("surging_focus", () -> new MagicFocus(new SurgingSpell()));
    public static final Item SPRIGHTLY_FOCUS = register("sprightly_focus", () -> new MagicFocus(new SpriteSpell()));
    public static final Item MONSOON_FOCUS = register("monsoon_focus", () -> new MagicFocus(new MonsoonSpell()));
    public static final Item DISCHARGE_FOCUS = register("discharge_focus", () -> new MagicFocus(new DischargeSpell()));
    public static final Item BOLTING_FOCUS = register("bolting_focus", () -> new MagicFocus(new BoltingSpell()));
    public static final Item LIGHTNING_FOCUS = register("lightning_focus", () -> new MagicFocus(new LightningSpell()));
    public static final Item THUNDERSTORM_FOCUS = register("thunderstorm_focus", () -> new MagicFocus(new ThunderstormSpell()));

    /// Abyss
    public static final Item WATER_JET_FOCUS = register("water_jet_focus", () -> new MagicFocus(new WaterJetSpell()));
    public static final Item BOUNCY_BUBBLE_FOCUS = register("bouncy_bubble_focus", () -> new MagicFocus(new BouncyBubbleSpell()));
    public static final Item STEAMING_FOCUS = register("steaming_focus", () -> new MagicFocus(new SteamSpell()));
    public static final Item TRIDENT_STORM_FOCUS = register("trident_storm_focus", () -> new MagicFocus(new TridentStormSpell()));
    public static final Item PRISMA_BEAM_FOCUS = register("prisma_beam_focus", () -> new MagicFocus(new PrismaBeamSpell()));
    public static final Item GUARDIAN_FOCUS = register("guardian_focus", () -> new MagicFocus(new GuardianSpell()));
    public static final Item BIOMINE_FOCUS = register("biomine_focus", () -> new MagicFocus(new BioMineSpell()));
    public static final Item WATER_WHIP_FOCUS = register("water_whip_focus", () -> new MagicFocus(new GulfTentacleSpell()));
    public static final Item TIDAL_FOCUS = register("tidal_focus", () -> new MagicFocus(new TidalSpell()));

    /// Nether
    public static final Item FIRE_BREATH_FOCUS = register("fire_breath_focus", () -> new MagicFocus(new FireBreathSpell()));
    public static final Item FIREBALL_FOCUS = register("fireball_focus", () -> new MagicFocus(new FireballSpell()));
    public static final Item LAVABALL_FOCUS = register("lavaball_focus", () -> new MagicFocus(new LavaballSpell()));
    public static final Item BOMBARDMENT_FOCUS = register("bombardment_focus", () -> new MagicFocus(new BombardmentSpell()));
    public static final Item METEOR_SHOWER_FOCUS = register("meteor_shower_focus", () -> new MagicFocus(new MeteorShowerSpell()));
    public static final Item MAGMA_BOMB_FOCUS = register("magma_bomb_focus", () -> new MagicFocus(new MagmaSpell()));
    public static final Item FIRE_BLAST_FOCUS = register("fire_blast_focus", () -> new MagicFocus(new FireBlastSpell()));
    public static final Item FLAME_STRIKE_FOCUS = register("flame_strike_focus", () -> new MagicFocus(new FlameStrikeSpell()));
    public static final Item WITHER_SKULL_FOCUS = register("wither_skull_focus", () -> new MagicFocus(new WitherSkullSpell()));
    public static final Item GHASTLY_FOCUS = register("ghastly_focus", () -> new MagicFocus(new GhastSpell()));
    public static final Item BLAZING_FOCUS = register("blazing_focus", () -> new MagicFocus(new BlazeSpell()));
    public static final Item HOGGING_FOCUS = register("hogging_focus", () -> new MagicFocus(new HoggingSpell()));

    /// Void
    public static final Item CALL_FOCUS = register("call_focus", CallFocus::new);
    public static final Item TROOP_FOCUS = register("troop_focus", TroopFocus::new);
    public static final Item RECALL_FOCUS = register("recall_focus", RecallFocus::new);
    public static final Item ENDER_CHEST_FOCUS = register("ender_chest_focus", () -> new MagicFocus(new EnderChestSpell()));
    public static final Item END_WALK_FOCUS = register("end_walk_focus", () -> new MagicFocus(new EndWalkSpell()));
    public static final Item BLINK_FOCUS = register("blink_focus", () -> new MagicFocus(new BlinkSpell()));
    public static final Item BANISH_FOCUS = register("banish_focus", () -> new MagicFocus(new BanishSpell()));
    public static final Item TUNNEL_FOCUS = register("tunnel_focus", () -> new MagicFocus(new TunnelSpell()));
    public static final Item RUPTURE_FOCUS = register("rupture_focus", () -> new MagicFocus(new VoidRiftSpell()));
    public static final Item STELLAR_FOCUS = register("stellar_focus", () -> new MagicFocus(new VoidShockSpell()));
    public static final Item VOID_FLASH_FOCUS = register("void_flash_focus", () -> new MagicFocus(new VoidBombSpell()));
    public static final Item WATCHING_FOCUS = register("watching_focus", () -> new MagicFocus(new WatchlingSpell()));
    public static final Item BLASTING_FOCUS = register("blasting_focus", () -> new MagicFocus(new BlastlingSpell()));
    public static final Item SNARING_FOCUS = register("snaring_focus", () -> new MagicFocus(new SnarelingSpell()));

    //Armors
    public static final Item CURSED_KNIGHT_HELMET = register("cursed_knight_helmet", () -> new CursedKnightArmor(ArmorItem.Type.HELMET));
    public static final Item CURSED_KNIGHT_CHESTPLATE = register("cursed_knight_chestplate", () -> new CursedKnightArmor(ArmorItem.Type.CHESTPLATE));
    public static final Item CURSED_KNIGHT_LEGGINGS = register("cursed_knight_leggings", () -> new CursedKnightArmor(ArmorItem.Type.LEGGINGS));
    public static final Item CURSED_KNIGHT_BOOTS = register("cursed_knight_boots", () -> new CursedKnightArmor(ArmorItem.Type.BOOTS));

    public static final Item CURSED_PALADIN_HELMET = register("cursed_paladin_helmet", () -> new CursedPaladinArmor(ArmorItem.Type.HELMET));
    public static final Item CURSED_PALADIN_CHESTPLATE = register("cursed_paladin_chestplate", () -> new CursedPaladinArmor(ArmorItem.Type.CHESTPLATE));
    public static final Item CURSED_PALADIN_LEGGINGS = register("cursed_paladin_leggings", () -> new CursedPaladinArmor(ArmorItem.Type.LEGGINGS));
    public static final Item CURSED_PALADIN_BOOTS = register("cursed_paladin_boots", () -> new CursedPaladinArmor(ArmorItem.Type.BOOTS));

    public static final Item BLACK_IRON_HELMET = register("black_iron_helmet", () -> new BlackIronArmor(ArmorItem.Type.HELMET));
    public static final Item BLACK_IRON_CHESTPLATE = register("black_iron_chestplate", () -> new BlackIronArmor(ArmorItem.Type.CHESTPLATE));
    public static final Item BLACK_IRON_LEGGINGS = register("black_iron_leggings", () -> new BlackIronArmor(ArmorItem.Type.LEGGINGS));
    public static final Item BLACK_IRON_BOOTS = register("black_iron_boots", () -> new BlackIronArmor(ArmorItem.Type.BOOTS));

    public static final Item DARK_HELMET = register("dark_helmet", () -> new DarkArmor(ArmorItem.Type.HELMET));
    public static final Item DARK_CHESTPLATE = register("dark_chestplate", () -> new DarkArmor(ArmorItem.Type.CHESTPLATE));
    public static final Item DARK_LEGGINGS = register("dark_leggings", () -> new DarkArmor(ArmorItem.Type.LEGGINGS));
    public static final Item DARK_BOOTS = register("dark_boots", () -> new DarkArmor(ArmorItem.Type.BOOTS));

    public static final Item MALEFIC_HELM = register("malefic_helm", MaleficHelm::new);

    //Tools & Weapons
    public static final Item DARK_WAND = register("dark_wand", DarkWand::new);
    public static final Item OMINOUS_STAFF = register("ominous_staff", () -> new DarkStaff(ItemConfig.OminousStaffDamage.get(), SpellType.ILL));
    public static final Item NECRO_STAFF = register("necro_staff", () -> new DarkStaff(ItemConfig.NecroStaffDamage.get(), SpellType.NECROMANCY));
    public static final Item GEO_STAFF = register("geo_staff", () -> new DarkStaff(ItemConfig.GeoStaffDamage.get(), SpellType.GEOMANCY));
    public static final Item WIND_STAFF = register("wind_staff", () -> new DarkStaff(ItemConfig.WindStaffDamage.get(), SpellType.WIND));
    public static final Item STORM_STAFF = register("storm_staff", () -> new DarkStaff(ItemConfig.StormStaffDamage.get(), SpellType.STORM));
    public static final Item FROST_STAFF = register("frost_staff", () -> new DarkStaff(ItemConfig.FrostStaffDamage.get(), SpellType.FROST));
    public static final Item WILD_STAFF = register("wild_staff", () -> new DarkStaff(ItemConfig.WildStaffDamage.get(), SpellType.WILD));
    public static final Item ABYSS_STAFF = register("abyss_staff", () -> new DarkStaff(ItemConfig.AbyssStaffDamage.get(), -2.9D, SpellType.ABYSS));
    public static final Item VOID_STAFF = register("void_staff", () -> new DarkStaff(ItemConfig.VoidStaffDamage.get(), SpellType.VOID));
    public static final Item NETHER_STAFF = register("nether_staff", () -> new DarkStaff(DarkWand.wandProperties().fireResistant(), ItemConfig.NetherStaffDamage.get(), SpellType.NETHER));
    public static final Item NAMELESS_STAFF = register("nameless_staff", NamelessStaff::new);
    public static final Item OMINOUS_SCYTHE = register("dark_scythe", DarkScytheItem::new);
    public static final Item DARK_SCYTHE = register("dark_metal_scythe", DarkMetalScythe::new);
    public static final Item DEATH_SCYTHE = register("death_scythe", DeathScytheItem::new);
    public static final Item GREAT_HAMMER = register("great_hammer", HammerItem::new);
    public static final Item BONEHEAD_HAMMER = register("bonehead_hammer", BoneheadHammerItem::new);
    public static final Item STORMLANDER = register("stormlander", StormlanderItem::new);
    public static final Item FANGED_DAGGER = register("fanged_dagger", FangedDaggerItem::new);
    public static final Item WICKED_BOLINE = register("wicked_boline", SickleItem::new);
    public static final Item EERIE_PICKAXE = register("eerie_pickaxe", EeriePickaxeItem::new);
    public static final Item RAMPAGING_AXE = register("rampaging_axe", RampagingAxeItem::new);
    public static final Item GRAVEROBBER_SHOVEL = register("graverobber_shovel", GraverobberShovelItem::new);
    public static final Item HUNTERS_BOW = register("hunters_bow", HuntersBowItem::new);
    //    public static final Item REVOLVER_CROSSBOW = register("revolver_crossbow", RevolverCrossbowItem::new);
    public static final Item IRON_ICE_AXE = register("iron_ice_axe", () -> new IceAxeItem(Tiers.IRON));
    public static final Item REINFORCED_ICE_AXE = register("reinforced_ice_axe", () -> new IceAxeItem(Tiers.IRON, new Item.Properties().durability(600)));
    public static final Item DIAMOND_ICE_AXE = register("diamond_ice_axe", () -> new IceAxeItem(Tiers.DIAMOND));
    public static final Item PHILOSOPHERS_MACE = register("philosophers_mace", PhilosophersMaceItem::new);
    public static final Item DARK_SWORD = register("dark_sword", ModToolItems.DarkSwordItem::new);
    public static final Item DARK_SHOVEL = register("dark_shovel", ModToolItems.DarkShovelItem::new);
    public static final Item DARK_PICKAXE = register("dark_pickaxe", ModToolItems.DarkPickaxeItem::new);
    public static final Item DARK_AXE = register("dark_axe", ModToolItems.DarkAxeItem::new);
    public static final Item DARK_HOE = register("dark_hoe", ModToolItems.DarkHoeItem::new);
    public static final Item HUNGRY_DAGGER = register("hungry_dagger", () -> new FangedDaggerItem(ModTiers.DARK));
    public static final Item FELL_BLADE = register("fell_blade", () -> new SwordItem(ModTiers.SPECIAL, 3, -2.4F, new Item.Properties().durability(256)));
    public static final Item FROZEN_BLADE = register("frozen_blade", () -> new SwordItem(ModTiers.SPECIAL, 4, -2.4F, new Item.Properties()));
    public static final Item BLADE_OF_ENDER = register("blade_of_ender", BladeOfEnderItem::new);
    public static final Item INFERNAL_TOME = register("infernal_tome", InfernalTome::new);

    //Sherds
    public static final Item CROSS_POTTERY_SHERD = register("cross_pottery_sherd", ItemBase::new);
    public static final Item DEAD_POTTERY_SHERD = register("dead_pottery_sherd", ItemBase::new);
    public static final Item HAUNT_POTTERY_SHERD = register("haunt_pottery_sherd", ItemBase::new);
    public static final Item NIGHT_POTTERY_SHERD = register("night_pottery_sherd", ItemBase::new);
    public static final Item SOUL_POTTERY_SHERD = register("soul_pottery_sherd", ItemBase::new);

    //Discs
    public static final Item MUSIC_DISC_ENDERMAN = register("music_disc_enderman", () -> new RecordItem(14, ModSounds.MUSIC_DISC_ENDERMAN, (new Item.Properties()).stacksTo(1).rarity(Rarity.RARE), 2240));
    public static final Item MUSIC_DISC_RM = register("music_disc_rm", () -> new RecordItem(14, ModSounds.MUSIC_DISC_RM, (new Item.Properties()).stacksTo(1).rarity(Rarity.RARE), 3200));
    public static final Item MUSIC_DISC_VIZIER = register("music_disc_vizier", () -> new RecordItem(14, ModSounds.MUSIC_DISC_VIZIER, (new Item.Properties()).stacksTo(1).rarity(Rarity.RARE), 1860));
    public static final Item MUSIC_DISC_KEEPER = register("music_disc_keeper", () -> new RecordItem(14, ModSounds.MUSIC_DISC_KEEPER, (new Item.Properties()).stacksTo(1).rarity(Rarity.RARE), 4640));
    public static final Item MUSIC_DISC_APOSTLE = register("music_disc_apostle", () -> new RecordItem(15, ModSounds.MUSIC_DISC_APOSTLE, (new Item.Properties()).stacksTo(1).rarity(Rarity.RARE), 3440));

    //Dummies
    public static final Item PEDESTAL_DUMMY = register("pedestal_dummy",
            () -> new Item(new Item.Properties()));
    public static final DummyItem JEI_DUMMY_NONE = register(
            "jei_dummy/none", () -> new DummyItem(new Item.Properties()));
    public static final DummyItem JEI_DUMMY_REQUIRE_SACRIFICE = register(
            "jei_dummy/sacrifice", () -> new DummyItem(new Item.Properties()));
    public static final Item BONE_SHARD = register("bone_shard",
            () -> new Item(new Item.Properties()));
    public static final Item COOKING_LADLE = register("cooking_ladle",
            () -> new Item(new Item.Properties()));

    public static FabricItemSettings baseProperties() {
        return new FabricItemSettings();
    }

    public static boolean isFocus(Item item) {
        return item instanceof MagicFocus;
    }

    public static boolean shouldSkipCreativeModTab(Item item) {
        return item == JEI_DUMMY_NONE
                || item == JEI_DUMMY_REQUIRE_SACRIFICE
                || item == PEDESTAL_DUMMY
                || item == BONE_SHARD
                || item == COOKING_LADLE
                || item instanceof TotemOfSouls
                || item instanceof BrewItem;
    }

    public static void init() {

    }

    public static <T extends Item> T register(String name, Supplier<T> supplier) {
        T item = supplier.get();
        if (item instanceof ServantSpawnEggItem) {
            SERVANT_SPAWN_EGGS.add(item);
        } else if (item instanceof SpawnEggItem) {
            SPAWN_EGGS.add(item);
        } else {
            ITEMS.add(item);
        }
        return Registry.register(BuiltInRegistries.ITEM, Goety.location(name), item);
    }
}
