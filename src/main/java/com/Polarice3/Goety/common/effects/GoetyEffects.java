package com.Polarice3.Goety.common.effects;

import com.Polarice3.Goety.Goety;
import com.Polarice3.Goety.config.BrewConfig;
import com.Polarice3.Goety.init.ModAttributes;
import com.Polarice3.Goety.utils.ModUUIDUtil;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

import java.util.function.Supplier;

public class GoetyEffects {

    public static final MobEffect ILLAGUE = register("illague",
            IllagueEffect::new);

    public static final MobEffect SUMMON_DOWN = register("summon_down",
            () -> new GoetyBaseEffect(MobEffectCategory.HARMFUL, 0));

    public static final MobEffect GOLD_TOUCHED = register("gold_touched",
            () -> new GoetyBaseEffect(MobEffectCategory.HARMFUL, 4866583));

    public static final MobEffect BURN_HEX = register("burn_hex",
            () -> new GoetyBaseEffect(MobEffectCategory.HARMFUL, 2236962));

    public static final MobEffect SAPPED = register("sapped",
            () -> new GoetyBaseEffect(MobEffectCategory.HARMFUL, 0x3f395f));

    public static final MobEffect CLIMBING = register("climbing",
            () -> new GoetyBaseEffect(MobEffectCategory.BENEFICIAL, 0xf5e895));

    public static final MobEffect CHARGED = register("charged",
            () -> new GoetyBaseEffect(MobEffectCategory.NEUTRAL, 0xd67b5b));

    public static final MobEffect BUFF = register("buff",
            () -> new GoetyBaseEffect(MobEffectCategory.BENEFICIAL, 0)
                    .addAttributeModifier(Attributes.ATTACK_DAMAGE, "f033b086-8a5e-44f2-8655-888dd700691c",
                            1.0D, AttributeModifier.Operation.ADDITION));

    public static final MobEffect RAMPAGE = register("rampage",
            () -> new GoetyBaseEffect(MobEffectCategory.BENEFICIAL, 0x6a0000)
                    .addAttributeModifier(Attributes.ATTACK_DAMAGE, "f51b56c4-bab1-43f4-8607-4db1b50061ef",
                            1.0D, AttributeModifier.Operation.ADDITION)
                    .addAttributeModifier(Attributes.ATTACK_SPEED, "da7d3d70-88a2-43c7-8924-4b4fbaf3b6aa",
                            0.1F, AttributeModifier.Operation.MULTIPLY_TOTAL));

    public static final MobEffect WANE = register("wane",
            () -> new GoetyBaseEffect(MobEffectCategory.HARMFUL, 0x425b64)
                    .addAttributeModifier(Attributes.ATTACK_DAMAGE, "85c0b45f-d88b-4752-9aa0-0460a5125860",
                            -4.0D, AttributeModifier.Operation.ADDITION)
                    .addAttributeModifier(Attributes.MOVEMENT_SPEED, "9a4d6273-711a-4f6d-87d2-23e91e190ab8",
                            -0.15D, AttributeModifier.Operation.MULTIPLY_TOTAL));

    public static final MobEffect BUSTED = register("busted",
            () -> new GoetyBaseEffect(MobEffectCategory.HARMFUL, 0x232f58)
                    .addAttributeModifier(Attributes.ARMOR, "bf1df32b-2ee2-4fb9-9b96-e1765202bca3",
                            -0.5D, AttributeModifier.Operation.MULTIPLY_TOTAL));

    public static final MobEffect SOUL_ARMOR = register("soul_armor",
            () -> new BrewMobEffect(MobEffectCategory.BENEFICIAL, 0x668785, false)
                    .addAttributeModifier(Attributes.ARMOR, "e4b8d878-0c5b-43b2-bd94-8c021d231f1e",
                            2.0D, AttributeModifier.Operation.ADDITION));

    public static final MobEffect IRON_HIDE = register("iron_hide",
            () -> new BrewMobEffect(MobEffectCategory.BENEFICIAL, 0x585858, false)
                    .addAttributeModifier(Attributes.ARMOR, "7487ebfe-56fb-4e83-b804-3f337b2a7814",
                            4.0D, AttributeModifier.Operation.ADDITION));

    public static final MobEffect CHILL_HIDE = register("chill_hide",
            () -> new GoetyBaseEffect(MobEffectCategory.BENEFICIAL, 0)
                    .addAttributeModifier(Attributes.ARMOR, "2767ec5c-c2dd-4c0c-b98b-d947b1b7f07f",
                            3.0D, AttributeModifier.Operation.ADDITION));

    public static final MobEffect SHADOW_WALK = register("shadow_walk",
            () -> new GoetyBaseEffect(MobEffectCategory.BENEFICIAL, 0));

    public static final MobEffect SOUL_HUNGER = register("soul_hunger",
            SoulHungerEffect::new);

    public static final MobEffect CURSED = register("cursed",
            () -> new GoetyBaseEffect(MobEffectCategory.HARMFUL, 0x1e1f24));

    public static final MobEffect FREEZING = register("freezing",
            () -> new GoetyBaseEffect(MobEffectCategory.HARMFUL, 0xf4fcfc));

    public static final MobEffect DOOM = register("doom",
            DoomEffect::new);

    public static final MobEffect ACID_VENOM = register("acid_venom",
            VenomEffect::new);

    public static final MobEffect SPASMS = register("spasms",
            SpasmEffect::new);

    public static final MobEffect ELECTRIFIED = register("electrified",
            ElectrifiedEffect::new);

    public static final MobEffect VOID_TOUCHED = register("void_touched",
            () -> new GoetyBaseEffect(MobEffectCategory.HARMFUL, 0));

    public static final MobEffect IMPAIRED = register("impaired",
            () -> new GoetyBaseEffect(MobEffectCategory.HARMFUL, 0));

    public static final MobEffect TREMOR_SENSE = register("tremor_sense",
            () -> new GoetyBaseEffect(MobEffectCategory.BENEFICIAL, 0));

    public static final MobEffect WOUNDED = register("wounded",
            () -> new BrewMobEffect(MobEffectCategory.HARMFUL, 0, false));

    public static final MobEffect CRIPPLED = register("crippled",
            () -> new BrewMobEffect(MobEffectCategory.HARMFUL, 0, false)
                    .addAttributeModifier(Attributes.MOVEMENT_SPEED, ModUUIDUtil.uuidString("effect.goety.crippled.movement_speed"),
                            -0.75D, AttributeModifier.Operation.MULTIPLY_TOTAL)
                    .addAttributeModifier(Attributes.ATTACK_SPEED, ModUUIDUtil.uuidString("effect.goety.crippled.attack_speed"),
                            -0.5D, AttributeModifier.Operation.MULTIPLY_TOTAL)
                    .addAttributeModifier(Attributes.ATTACK_DAMAGE, ModUUIDUtil.uuidString("effect.goety.crippled.attack_damage"),
                            -0.5D, AttributeModifier.Operation.MULTIPLY_TOTAL));

    public static final MobEffect STUNNED = register("stunned",
            () -> new GoetyBaseEffect(MobEffectCategory.HARMFUL, 0xffbc2e)
                    .addAttributeModifier(ModAttributes.SWIM_SPEED, "e4669259-9b6f-40d2-b253-46e65b1f3363",
                            -1.0D, AttributeModifier.Operation.MULTIPLY_TOTAL)
                    .addAttributeModifier(Attributes.MOVEMENT_SPEED, "963d8748-941f-4f75-b4a6-a9c85013f27f",
                            -1.0D, AttributeModifier.Operation.MULTIPLY_TOTAL));

    public static final MobEffect TANGLED = register("tangled",
            () -> new GoetyBaseEffect(MobEffectCategory.HARMFUL, 0)
                    .addAttributeModifier(ModAttributes.SWIM_SPEED, "862219f1-18f4-483a-94db-0d4c6c4fdef2",
                            -1.0D, AttributeModifier.Operation.MULTIPLY_TOTAL)
                    .addAttributeModifier(Attributes.MOVEMENT_SPEED, "8246d3de-e765-487d-adda-18a1deb3e4a9",
                            -1.0D, AttributeModifier.Operation.MULTIPLY_TOTAL));

    //Brew Exclusive
    public static final MobEffect PRESSURE = register("pressure",
            () -> new BrewMobEffect(MobEffectCategory.HARMFUL, 0x007200, BrewConfig.PressureCurable.get()));

    public static final MobEffect ENDER_GROUND = register("ender_ground",
            () -> new GoetyBaseEffect(MobEffectCategory.HARMFUL, 0x258474));

    public static final MobEffect ENDER_FLUX = register("ender_flux",
            () -> new GoetyBaseEffect(MobEffectCategory.HARMFUL, 0x441d5a));

    public static final MobEffect NYCTOPHOBIA = register("nyctophobia",
            () -> new BrewMobEffect(MobEffectCategory.HARMFUL, 0x0d1305, BrewConfig.NyctophobiaCurable.get()));

    public static final MobEffect SUN_ALLERGY = register("sun_allergy",
            () -> new BrewMobEffect(MobEffectCategory.HARMFUL, 0x1f1421, BrewConfig.SunAllergyCurable.get()));

    public static final MobEffect SNOW_SKIN = register("snow_skin",
            () -> new BrewMobEffect(MobEffectCategory.HARMFUL, 0xe3f3f3, BrewConfig.SnowSkinCurable.get()));

    public static final MobEffect EVIL_EYE = register("evil_eye",
            () -> new EvilEyeEffect(MobEffectCategory.HARMFUL, 0x560269, BrewConfig.EvilEyeCurable.get()));

    public static final MobEffect TRIPPING = register("tripping",
            () -> new GoetyBaseEffect(MobEffectCategory.HARMFUL, 0x101636));

    public static final MobEffect ARROWMANTIC = register("arrowmantic",
            () -> new GoetyBaseEffect(MobEffectCategory.HARMFUL, 0x969696));

    public static final MobEffect PLUNGE = register("plunge",
            () -> new GoetyBaseEffect(MobEffectCategory.HARMFUL, 0x8d989a));

    public static final MobEffect FLIMSY = register("flimsy",
            () -> new GoetyBaseEffect(MobEffectCategory.HARMFUL, 0xf5f5f5)
                    .addAttributeModifier(Attributes.KNOCKBACK_RESISTANCE, ModUUIDUtil.uuidString("effect.goety.flimsy.knockback_resistance"),
                            -1.0D, AttributeModifier.Operation.MULTIPLY_TOTAL));

    public static final MobEffect SENSE_LOSS = register("sense_loss",
            () -> new GoetyBaseEffect(MobEffectCategory.HARMFUL, 2039587));

    public static final MobEffect FLAMMABLE = register("flammable",
            () -> new GoetyBaseEffect(MobEffectCategory.HARMFUL, 0x1e0f07));

    public static final MobEffect STORMS_WRATH = register("storms_wrath",
            () -> new GoetyBaseEffect(MobEffectCategory.HARMFUL, 0xe77c56));

    public static final MobEffect EXPLOSIVE = register("explosive",
            () -> new GoetyBaseEffect(MobEffectCategory.BENEFICIAL, 0x912d11));

    public static final MobEffect SWIFT_SWIM = register("swift_swim",
            () -> new GoetyBaseEffect(MobEffectCategory.BENEFICIAL, 0xbead6a)
                    .addAttributeModifier(ModAttributes.SWIM_SPEED, "15c1a19c-b4f8-4d84-ab37-a9036ac1885f",
                            1.0D, AttributeModifier.Operation.ADDITION));

    public static final MobEffect FROG_LEG = register("frog_leg",
            () -> new GoetyBaseEffect(MobEffectCategory.BENEFICIAL, 0x6abe30));

    public static final MobEffect FLAME_HANDS = register("flame_hands",
            () -> new GoetyBaseEffect(MobEffectCategory.BENEFICIAL, 0xff3d29));

    public static final MobEffect VENOMOUS_HANDS = register("venomous_hands",
            () -> new GoetyBaseEffect(MobEffectCategory.BENEFICIAL, 0x122620));

    public static final MobEffect REPULSIVE = register("repulsive",
            () -> new GoetyBaseEffect(MobEffectCategory.BENEFICIAL, 0x67502c));

    public static final MobEffect FIRE_TRAIL = register("fire_trail",
            () -> new GoetyBaseEffect(MobEffectCategory.BENEFICIAL, 0xffc800));

    public static final MobEffect FIERY_AURA = register("fiery_aura",
            () -> new GoetyBaseEffect(MobEffectCategory.BENEFICIAL, 0xff0000));

    public static final MobEffect FROSTY_AURA = register("frosty_aura",
            () -> new GoetyBaseEffect(MobEffectCategory.BENEFICIAL, 0x212d5f));

    public static final MobEffect PHOTOSYNTHESIS = register("photosynthesis",
            () -> new GoetyBaseEffect(MobEffectCategory.BENEFICIAL, 0xffec4f));

    public static final MobEffect INSIGHT = register("insight",
            () -> new GoetyBaseEffect(MobEffectCategory.BENEFICIAL, 0x59b057));

    public static final MobEffect BOTTLING = register("bottling",
            () -> new GoetyBaseEffect(MobEffectCategory.BENEFICIAL, 0x3e250f));

    public static final MobEffect CORPSE_EATER = register("corpse_eater",
            () -> new GoetyBaseEffect(MobEffectCategory.BENEFICIAL, 0x48565e));

    public static final MobEffect FORTUNATE = register("fortunate",
            () -> new GoetyBaseEffect(MobEffectCategory.BENEFICIAL, 0x4aedd9));

    public static final MobEffect ALTRUISTIC = register("altruistic",
            () -> new GoetyBaseEffect(MobEffectCategory.BENEFICIAL, 0xa10000));

    public static final MobEffect RADIANCE = register("radiance",
            () -> new GoetyBaseEffect(MobEffectCategory.BENEFICIAL, 0xffbc2e));

    public static final MobEffect LEECHING = register("leeching",
            () -> new GoetyBaseEffect(MobEffectCategory.BENEFICIAL, 0x690000));

    public static final MobEffect SHIELDING = register("shielding",
            () -> new AuraEffect(MobEffectCategory.BENEFICIAL, 0xb3bec0));

    public static final MobEffect SHIELDED = register("shielded",
            () -> new GoetyBaseEffect(MobEffectCategory.BENEFICIAL, 0x939d9e));

    public static final MobEffect RALLYING = register("rallying",
            () -> new AuraEffect(MobEffectCategory.BENEFICIAL, 0xff8609)
                    .addAttributeModifier(Attributes.ATTACK_DAMAGE, ModUUIDUtil.uuidString("effect.goety.rallying.attack"),
                            0.1D, AttributeModifier.Operation.MULTIPLY_TOTAL));

    public static final MobEffect RALLIED = register("rallied",
            () -> new GoetyBaseEffect(MobEffectCategory.BENEFICIAL, 0xf65500)
                    .addAttributeModifier(Attributes.ATTACK_DAMAGE, ModUUIDUtil.uuidString("effect.goety.rallied.attack"),
                            0.1D, AttributeModifier.Operation.MULTIPLY_TOTAL));

    public static final MobEffect DEFLECTIVE = register("deflective",
            () -> new GoetyBaseEffect(MobEffectCategory.BENEFICIAL, 0xa575a5));

    public static final MobEffect SWIRLING = register("swirling",
            () -> new GoetyBaseEffect(MobEffectCategory.BENEFICIAL, 0xffffff));

    public static final MobEffect SAVE_EFFECTS = register("save_effects",
            () -> new GoetyBaseEffect(MobEffectCategory.BENEFICIAL, 0x4f446b));

    public static final MobEffect GRAVITY_PULSE = register("gravity_pulse",
            () -> new GoetyBaseEffect(MobEffectCategory.NEUTRAL, 0x580c56));

    public static final MobEffect WILD_RAGE = register("wild_rage",
            () -> new GoetyBaseEffect(MobEffectCategory.NEUTRAL, 0xa8311c));

    public static void init() {

    }

    private static MobEffect register(String name, Supplier<MobEffect> supplier) {
        return Registry.register(BuiltInRegistries.MOB_EFFECT, Goety.location(name), supplier.get());
    }
}
