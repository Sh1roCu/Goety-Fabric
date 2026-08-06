package com.Polarice3.Goety.client.particles;

import com.Polarice3.Goety.Goety;
import com.mojang.serialization.Codec;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;

import java.util.function.Supplier;

public class ModParticleTypes {

    public static final SimpleParticleType NONE = register("none",
            () -> FabricParticleTypes.simple(false));

    public static final SimpleParticleType TOTEM_EFFECT = register("totem_effect",
            () -> FabricParticleTypes.simple(false));

    public static final SimpleParticleType PLAGUE_EFFECT = register("plague_effect",
            () -> FabricParticleTypes.simple(false));

    public static final SimpleParticleType DOOM = register("doom",
            () -> FabricParticleTypes.simple(false));

    public static final SimpleParticleType DOOM_DEATH = register("doom_death",
            () -> FabricParticleTypes.simple(false));

    public static final SimpleParticleType HYSTERIA = register("hysteria",
            () -> FabricParticleTypes.simple(false));

    public static final SimpleParticleType HEAL_EFFECT = register("heal",
            () -> FabricParticleTypes.simple(false));

    public static final SimpleParticleType HEAL_EFFECT_2 = register("heal2",
            () -> FabricParticleTypes.simple(false));

    public static final SimpleParticleType BULLET_EFFECT = register("bullet_effect",
            () -> FabricParticleTypes.simple(false));

    public static final SimpleParticleType NECRO_EFFECT = register("necro_effect",
            () -> FabricParticleTypes.simple(false));

    public static final SimpleParticleType NECRO_BOLT = register("necro_bolt",
            () -> FabricParticleTypes.simple(false));

    public static final SimpleParticleType STUN = register("stun",
            () -> FabricParticleTypes.simple(false));

    public static final SimpleParticleType SOUL_LIGHT_EFFECT = register("soul_light",
            () -> FabricParticleTypes.simple(false));

    public static final SimpleParticleType GLOW_EFFECT = register("glow_trail",
            () -> FabricParticleTypes.simple(false));

    public static final SimpleParticleType GLOW_LIGHT_EFFECT = register("glow_light",
            () -> FabricParticleTypes.simple(false));

    public static final SimpleParticleType SOUL_EXPLODE_BITS = register("soul_explode_bits",
            () -> FabricParticleTypes.simple(false));

    public static final SimpleParticleType XP_TAKE = register("xp_take",
            () -> FabricParticleTypes.simple(false));

    public static final SimpleParticleType LASER_GATHER = register("laser",
            () -> FabricParticleTypes.simple(true));

    public static final SimpleParticleType RESONANCE_GATHER = register("resonance",
            () -> FabricParticleTypes.simple(true));

    public static final SimpleParticleType BURNING = register("burning",
            () -> FabricParticleTypes.simple(true));

    public static final SimpleParticleType FIERY_PILLAR = register("fiery_pillar",
            () -> FabricParticleTypes.simple(true));

    public static final SimpleParticleType CULT_SPELL = register("cult_spell",
            () -> FabricParticleTypes.simple(true));

    public static final SimpleParticleType BIG_CULT_SPELL = register("big_cult_spell",
            () -> FabricParticleTypes.simple(true));

    public static final SimpleParticleType SMALL_STATION_CULT_SPELL = register("small_station_cult_spell",
            () -> FabricParticleTypes.simple(true));

    public static final SimpleParticleType STATION_CULT_SPELL = register("station_cult_spell",
            () -> FabricParticleTypes.simple(true));

    public static final SimpleParticleType MUD_GAS = register("mud_gas",
            () -> FabricParticleTypes.simple(true));

    public static final SimpleParticleType LICH = register("lich",
            () -> FabricParticleTypes.simple(true));

    public static final SimpleParticleType CONFUSED = register("confused",
            () -> FabricParticleTypes.simple(true));

    public static final SimpleParticleType WHITE_EFFECT = register("white_effect",
            () -> FabricParticleTypes.simple(false));

    public static final SimpleParticleType WRAITH = register("wraith",
            () -> FabricParticleTypes.simple(false));

    public static final SimpleParticleType WRAITH_BURST = register("wraith_burst",
            () -> FabricParticleTypes.simple(false));

    public static final SimpleParticleType WRAITH_FIRE = register("wraith_fire",
            () -> FabricParticleTypes.simple(false));

    public static final SimpleParticleType SMALL_FIRE = register("small_fire",
            () -> FabricParticleTypes.simple(false));

    public static final SimpleParticleType SMALL_FIRE_DROP = register("small_fire_drop",
            () -> FabricParticleTypes.simple(false));

    public static final SimpleParticleType SMALL_FIRE_GROUND = register("small_fire_ground",
            () -> FabricParticleTypes.simple(false));

    public static final SimpleParticleType SMALL_FIRE_REVERSED = register("small_fire_reversed",
            () -> FabricParticleTypes.simple(false));

    public static final SimpleParticleType BIG_FIRE = register("big_fire",
            () -> FabricParticleTypes.simple(false));

    public static final SimpleParticleType BIG_FIRE_DROP = register("big_fire_drop",
            () -> FabricParticleTypes.simple(false));

    public static final SimpleParticleType BIG_FIRE_GROUND = register("big_fire_ground",
            () -> FabricParticleTypes.simple(false));

    public static final SimpleParticleType BIG_SOUL_FIRE = register("big_soul_fire",
            () -> FabricParticleTypes.simple(false));

    public static final SimpleParticleType BIG_SOUL_FIRE_DROP = register("big_soul_fire_drop",
            () -> FabricParticleTypes.simple(false));

    public static final SimpleParticleType BIG_SOUL_FIRE_GROUND = register("big_soul_fire_ground",
            () -> FabricParticleTypes.simple(false));

    public static final SimpleParticleType NECRO_FIRE = register("necro_fire",
            () -> FabricParticleTypes.simple(false));

    public static final SimpleParticleType NECRO_FIRE_DROP = register("necro_fire_drop",
            () -> FabricParticleTypes.simple(false));

    public static final SimpleParticleType SMALL_NECRO_FIRE = register("small_necro_fire",
            () -> FabricParticleTypes.simple(false));

    public static final SimpleParticleType SMALL_NECRO_FIRE_DROP = register("small_necro_fire_drop",
            () -> FabricParticleTypes.simple(false));

    public static final SimpleParticleType NECRO_FLAME = register("necro_flame",
            () -> FabricParticleTypes.simple(false));

    public static final SimpleParticleType DRAGON_FLAME = register("dragon_flame",
            () -> FabricParticleTypes.simple(false));

    public static final SimpleParticleType DRAGON_FLAME_DROP = register("dragon_flame_drop",
            () -> FabricParticleTypes.simple(false));

    public static final SimpleParticleType SMALL_DRAGON_FLAME = register("small_dragon_flame",
            () -> FabricParticleTypes.simple(false));

    public static final SimpleParticleType SMALL_DRAGON_FLAME_GROUND = register("small_dragon_flame_ground",
            () -> FabricParticleTypes.simple(false));

    public static final SimpleParticleType END_FIRE = register("end_fire",
            () -> FabricParticleTypes.simple(false));

    public static final SimpleParticleType END_FIRE_DROP = register("end_fire_drop",
            () -> FabricParticleTypes.simple(false));

    public static final SimpleParticleType SMALL_END_FIRE = register("small_end_fire",
            () -> FabricParticleTypes.simple(false));

    public static final SimpleParticleType FROST = register("frost",
            () -> FabricParticleTypes.simple(false));

    public static final SimpleParticleType FROST_NOVA = register("frost_nova",
            () -> FabricParticleTypes.simple(false));

    public static final SimpleParticleType FLY = register("fly",
            () -> FabricParticleTypes.simple(false));

    public static final SimpleParticleType BONE = register("bone",
            () -> FabricParticleTypes.simple(false));

    public static final SimpleParticleType LEECH = register("leech",
            () -> FabricParticleTypes.simple(false));

    public static final SimpleParticleType CHANT = register("chant",
            () -> FabricParticleTypes.simple(false));

    public static final SimpleParticleType ELECTRIC = register("electric",
            () -> FabricParticleTypes.simple(false));

    public static final SimpleParticleType BIG_ELECTRIC = register("big_electric",
            () -> FabricParticleTypes.simple(false));

    public static final SimpleParticleType SPELL_ELECTRIC = register("spell_electric",
            () -> FabricParticleTypes.simple(false));

    public static final SimpleParticleType BUBBLE_STREAM = register("bubble_stream",
            () -> FabricParticleTypes.simple(false));

    public static final SimpleParticleType BREW_BUBBLE = register("brew_bubble",
            () -> FabricParticleTypes.simple(false));

    public static final SimpleParticleType WIND_BLAST = register("wind_blast",
            () -> FabricParticleTypes.simple(false));

    public static final SimpleParticleType WARLOCK = register("warlock",
            () -> FabricParticleTypes.simple(false));

    public static final SimpleParticleType FUNGUS_EXPLOSION = register("fungus_explosion",
            () -> FabricParticleTypes.simple(true));

    public static final SimpleParticleType FUNGUS_EXPLOSION_EMITTER = register("fungus_explosion_emitter",
            () -> FabricParticleTypes.simple(true));

    public static final SimpleParticleType SOUL_EXPLODE = register("soul_explode",
            () -> FabricParticleTypes.simple(true));

    public static final SimpleParticleType SUMMON = register("summon",
            () -> FabricParticleTypes.simple(true));

    public static final SimpleParticleType VOID_SPAWNER_DETECTION = register("void_spawner_detection",
            () -> FabricParticleTypes.simple(true));

    public static final SimpleParticleType VOID_VAULT_CONNECT = register("void_vault_connect",
            () -> FabricParticleTypes.simple(true));

    public static final SimpleParticleType SPELL_SQUARE = register("spell_square",
            () -> FabricParticleTypes.simple(true));

    public static final SimpleParticleType SMALL_SPELL_SQUARE = register("small_spell_square",
            () -> FabricParticleTypes.simple(true));

    public static final SimpleParticleType TRAIL = register("trail",
            () -> FabricParticleTypes.simple(true));

    public static final SimpleParticleType SUMMON_TRAIL = register("summon_trail",
            () -> FabricParticleTypes.simple(true));

    public static final SimpleParticleType SPELL_CLOUD = register("spell_cloud",
            () -> FabricParticleTypes.simple(true));

    public static final SimpleParticleType DROPLET = register("droplet",
            () -> FabricParticleTypes.simple(true));

    public static final SimpleParticleType GO = register("go",
            () -> FabricParticleTypes.simple(true));

    public static final SimpleParticleType STOP = register("stop",
            () -> FabricParticleTypes.simple(true));

    public static final SimpleParticleType FANG_RAIN = register("fang_rain",
            () -> FabricParticleTypes.simple(false));

    public static final SimpleParticleType MAGIC_BOLT = register("magic_bolt",
            () -> FabricParticleTypes.simple(false));

    public static final SimpleParticleType RISING_SPIRAL = register("rising_spiral",
            () -> FabricParticleTypes.simple(false));

    public static final SimpleParticleType RISING_ENCHANT = register("rising_enchant",
            () -> FabricParticleTypes.simple(false));

    public static final SimpleParticleType ROLLING_SPIRAL = register("rolling_spiral",
            () -> FabricParticleTypes.simple(false));

    public static final SimpleParticleType ROLLING_ENCHANT = register("rolling_enchant",
            () -> FabricParticleTypes.simple(false));

    public static final SimpleParticleType ROLLING_TARGET = register("rolling_target",
            () -> FabricParticleTypes.simple(false));

    public static final SimpleParticleType REDSTONE_EXPLODE = register("redstone_explode",
            () -> FabricParticleTypes.simple(false));

    public static final SimpleParticleType ELECTRIC_EXPLODE = register("electric_explode",
            () -> FabricParticleTypes.simple(false));

    public static final SimpleParticleType FAN_CLOUD = register("fan_cloud",
            () -> FabricParticleTypes.simple(false));

    public static final SimpleParticleType REDSTONE_DEBRIS = register("redstone_debris",
            () -> FabricParticleTypes.simple(false));

    public static final SimpleParticleType GOO_STAIN = register("goo_stain",
            () -> FabricParticleTypes.simple(true));

    public static final SimpleParticleType CHORUS_LEAVES = register("chorus_leaves",
            () -> FabricParticleTypes.simple(true));

    public static final SimpleParticleType CHORUS_BLOSSOM_LEAVES = register("chorus_blossom_leaves",
            () -> FabricParticleTypes.simple(true));

    public static final SimpleParticleType GOD_RAY = register("god_ray",
            () -> FabricParticleTypes.simple(true));

    public static final SimpleParticleType STRETCHED_GOD_RAY = register("stretched_god_ray",
            () -> FabricParticleTypes.simple(true));

    public static final SimpleParticleType WATER_STREAM = register("water_stream",
            () -> FabricParticleTypes.simple(true));

    public static final SimpleParticleType WATER_JET = register("water_jet",
            () -> FabricParticleTypes.simple(true));

    public static final SimpleParticleType WATER_TRAIL = register("water_trail",
            () -> FabricParticleTypes.simple(true));

    public static final SimpleParticleType BLOSSOM_THORN_INDICATOR = register("blossom_thorn_indicator",
            () -> FabricParticleTypes.simple(true));

    public static final ParticleType<SparkleParticleOption> SPARKLE = register("sparkle",
            () -> new ParticleType<>(false, SparkleParticleOption.DESERIALIZER) {
                @Override
                public Codec<SparkleParticleOption> codec() {
                    return SparkleParticleOption.CODEC;
                }
            });

    public static final ParticleType<DustCloudParticleOption> DUST_CLOUD = register("dust_cloud",
            () -> new ParticleType<>(false, DustCloudParticleOption.DESERIALIZER) {
                @Override
                public Codec<DustCloudParticleOption> codec() {
                    return DustCloudParticleOption.CODEC;
                }
            });

    public static final ParticleType<BlockParticleOption> FAST_DUST = register("fast_dust",
            () -> new ParticleType<>(false, BlockParticleOption.DESERIALIZER) {
                @Override
                public Codec<BlockParticleOption> codec() {
                    return BlockParticleOption.codec(this);
                }
            });

    public static final ParticleType<ShockwaveParticleOption> SHOCKWAVE = register("shockwave",
            () -> new ParticleType<>(false, ShockwaveParticleOption.DESERIALIZER) {
                @Override
                public Codec<ShockwaveParticleOption> codec() {
                    return ShockwaveParticleOption.CODEC;
                }
            });

    public static final ParticleType<ShockwaveParticleOption> REVERSE_SHOCKWAVE = register("reverse_shockwave",
            () -> new ParticleType<>(false, ShockwaveParticleOption.DESERIALIZER) {
                @Override
                public Codec<ShockwaveParticleOption> codec() {
                    return ShockwaveParticleOption.CODEC;
                }
            });

    public static final ParticleType<ShockwaveParticleOption> LICH_DEATH = register("lich_death",
            () -> new ParticleType<>(false, ShockwaveParticleOption.DESERIALIZER) {
                @Override
                public Codec<ShockwaveParticleOption> codec() {
                    return ShockwaveParticleOption.CODEC;
                }
            });

    public static final ParticleType<CircleExplodeParticleOption> CIRCLE_EXPLODE = register("circle_explode",
            () -> new ParticleType<>(false, CircleExplodeParticleOption.DESERIALIZER) {
                @Override
                public Codec<CircleExplodeParticleOption> codec() {
                    return CircleExplodeParticleOption.CODEC;
                }
            });

    public static final ParticleType<AoEParticleOption> AOE_INDICATOR = register("aoe_indicator",
            () -> new ParticleType<>(false, AoEParticleOption.DESERIALIZER) {
                @Override
                public Codec<AoEParticleOption> codec() {
                    return AoEParticleOption.CODEC;
                }
            });

    public static final ParticleType<FoggyCloudParticleOption> FOG_CLOUD = register("fog_cloud",
            () -> new ParticleType<>(false, FoggyCloudParticleOption.DESERIALIZER) {
                @Override
                public Codec<FoggyCloudParticleOption> codec() {
                    return FoggyCloudParticleOption.CODEC;
                }
            });

    public static final ParticleType<PulsatingCircleParticleOption> MINE_PULSE = register("mine_pulse",
            () -> new ParticleType<>(false, PulsatingCircleParticleOption.DESERIALIZER) {
                @Override
                public Codec<PulsatingCircleParticleOption> codec() {
                    return PulsatingCircleParticleOption.CODEC;
                }
            });

    public static final ParticleType<RisingCircleParticleOption> SOUL_HEAL = register("soul_heal",
            () -> new ParticleType<>(false, RisingCircleParticleOption.DESERIALIZER) {
                @Override
                public Codec<RisingCircleParticleOption> codec() {
                    return RisingCircleParticleOption.CODEC;
                }
            });

    public static final ParticleType<ModShriekParticleOption> MOD_SHRIEK = register("mod_shriek",
            () -> new ParticleType<>(false, ModShriekParticleOption.DESERIALIZER) {
                @Override
                public Codec<ModShriekParticleOption> codec() {
                    return ModShriekParticleOption.CODEC;
                }
            });

    public static final ParticleType<SculkBubbleParticleOption> SCULK_BUBBLE = register("sculk_bubble",
            () -> new ParticleType<>(false, SculkBubbleParticleOption.DESERIALIZER) {
                @Override
                public Codec<SculkBubbleParticleOption> codec() {
                    return SculkBubbleParticleOption.CODEC;
                }
            });

    public static final ParticleType<WindParticleOption> WIND = register("wind",
            () -> new ParticleType<>(false, WindParticleOption.DESERIALIZER) {
                @Override
                public Codec<WindParticleOption> codec() {
                    return WindParticleOption.CODEC;
                }
            });

    public static final ParticleType<WindBlowParticleOption> WIND_BLOW = register("wind_blow",
            () -> new ParticleType<>(false, WindBlowParticleOption.DESERIALIZER) {
                @Override
                public Codec<WindBlowParticleOption> codec() {
                    return WindBlowParticleOption.CODEC;
                }
            });

    public static final ParticleType<WindShockwaveParticleOption> WIND_SHOCKWAVE = register("wind_shockwave",
            () -> new ParticleType<>(false, WindShockwaveParticleOption.DESERIALIZER) {
                @Override
                public Codec<WindShockwaveParticleOption> codec() {
                    return WindShockwaveParticleOption.CODEC;
                }
            });

    public static final ParticleType<WindGatherParticleOption> WIND_GATHER = register("wind_gather",
            () -> new ParticleType<>(false, WindGatherParticleOption.DESERIALIZER) {
                @Override
                public Codec<WindGatherParticleOption> codec() {
                    return WindGatherParticleOption.CODEC;
                }
            });

    public static final ParticleType<GatherTrailParticleOption> GATHER_TRAIL = register("gather_trail",
            () -> new ParticleType<>(false, GatherTrailParticleOption.DESERIALIZER) {
                @Override
                public Codec<GatherTrailParticleOption> codec() {
                    return GatherTrailParticleOption.CODEC;
                }
            });

    public static final ParticleType<GatherFrostParticleOption> FROST_GATHER = register("frost_gather",
            () -> new ParticleType<>(false, GatherFrostParticleOption.DESERIALIZER) {
                @Override
                public Codec<GatherFrostParticleOption> codec() {
                    return GatherFrostParticleOption.CODEC;
                }
            });

    public static final ParticleType<AuraParticleOption> AURA = register("aura",
            () -> new ParticleType<>(false, AuraParticleOption.DESERIALIZER) {
                @Override
                public Codec<AuraParticleOption> codec() {
                    return AuraParticleOption.CODEC;
                }
            });

    public static final ParticleType<GroundAuraParticleOption> GROUND_AURA = register("ground_aura",
            () -> new ParticleType<>(false, GroundAuraParticleOption.DESERIALIZER) {
                @Override
                public Codec<GroundAuraParticleOption> codec() {
                    return GroundAuraParticleOption.CODEC;
                }
            });

    public static final ParticleType<VerticalCircleExplodeParticleOption> VERTICAL_CIRCLE_EXPLODE = register("vertical_circle_explode",
            () -> new ParticleType<>(false, VerticalCircleExplodeParticleOption.DESERIALIZER) {
                @Override
                public Codec<VerticalCircleExplodeParticleOption> codec() {
                    return VerticalCircleExplodeParticleOption.CODEC;
                }
            });

    public static final ParticleType<AbsorbTrailParticleOption> ABSORB_TRAIL = register("absorb_trail",
            () -> new ParticleType<>(false, AbsorbTrailParticleOption.DESERIALIZER) {
                @Override
                public Codec<AbsorbTrailParticleOption> codec() {
                    return AbsorbTrailParticleOption.CODEC;
                }
            });

    public static final ParticleType<MagicSmokeParticleOption> MAGIC_SMOKE = register("magic_smoke",
            () -> new ParticleType<>(false, MagicSmokeParticleOption.DESERIALIZER) {
                @Override
                public Codec<MagicSmokeParticleOption> codec() {
                    return MagicSmokeParticleOption.CODEC;
                }
            });

    public static final ParticleType<MagicAshSmokeParticleOption> MAGIC_ASH_SMOKE = register("magic_ash_smoke",
            () -> new ParticleType<>(false, MagicAshSmokeParticleOption.DESERIALIZER) {
                @Override
                public Codec<MagicAshSmokeParticleOption> codec() {
                    return MagicAshSmokeParticleOption.CODEC;
                }
            });

    public static final ParticleType<FollowFireParticleOption> FOLLOW_CULT_SPELL = register("follow_cult_spell",
            () -> new ParticleType<>(false, FollowFireParticleOption.DESERIALIZER) {
                @Override
                public Codec<FollowFireParticleOption> codec() {
                    return FollowFireParticleOption.CODEC;
                }
            });

    public static final ParticleType<SpirallingParticleOption> SPIRALLING = register("spiralling",
            () -> new ParticleType<>(false, SpirallingParticleOption.DESERIALIZER) {
                @Override
                public Codec<SpirallingParticleOption> codec() {
                    return SpirallingParticleOption.CODEC;
                }
            });

    public static final ParticleType<ShootIndicatorParticleOption> SHOOT_INDICATOR = register("shoot_indicator",
            () -> new ParticleType<>(false, ShootIndicatorParticleOption.DESERIALIZER) {
                @Override
                public Codec<ShootIndicatorParticleOption> codec() {
                    return ShootIndicatorParticleOption.CODEC;
                }
            });

    public static final ParticleType<SlamParticleOption> SLAM = register("slam",
            () -> new ParticleType<>(false, SlamParticleOption.DESERIALIZER) {
                @Override
                public Codec<SlamParticleOption> codec() {
                    return SlamParticleOption.CODEC;
                }
            });

    public static final ParticleType<SmashParticleOption> SMASH = register("smash",
            () -> new ParticleType<>(false, SmashParticleOption.DESERIALIZER) {
                @Override
                public Codec<SmashParticleOption> codec() {
                    return SmashParticleOption.CODEC;
                }
            });

    public static final ParticleType<SphereExplodeParticleOption> SPHERE_EXPLODE = register("sphere_explode",
            () -> new ParticleType<>(false, SphereExplodeParticleOption.DESERIALIZER) {
                @Override
                public Codec<SphereExplodeParticleOption> codec() {
                    return SphereExplodeParticleOption.CODEC;
                }
            });

    public static void init() {

    }

    private static <T extends ParticleOptions, P extends ParticleType<T>> P register(String name, Supplier<P> supplier) {
        return Registry.register(BuiltInRegistries.PARTICLE_TYPE, Goety.location(name), supplier.get());
    }
}
