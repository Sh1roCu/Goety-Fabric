package com.Polarice3.Goety.init;

import com.Polarice3.Goety.Goety;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvent;

import java.util.function.Supplier;

public class ModSounds {

    public static final SoundEvent APOSTLE_AMBIENT = create("apostle_ambient");
    public static final SoundEvent APOSTLE_HURT = create("apostle_hurt");
    public static final SoundEvent APOSTLE_SHOOT = create("apostle_shoot");
    public static final SoundEvent APOSTLE_PREPARE_SPELL = create("apostle_prepare_spell");
    public static final SoundEvent APOSTLE_PREPARE_SUMMON = create("apostle_prepare_summon");
    public static final SoundEvent APOSTLE_CAST_SPELL = create("apostle_cast_spell");
    public static final SoundEvent APOSTLE_PRE_TELEPORT = create("apostle_pre_teleport");
    public static final SoundEvent APOSTLE_TELEPORT = create("apostle_teleport");
    public static final SoundEvent APOSTLE_PREDEATH = create("apostle_predeath");
    public static final SoundEvent APOSTLE_DEATH = create("apostle_death");

    public static final SoundEvent INFERNO_AMBIENT = create("inferno_ambient");
    public static final SoundEvent INFERNO_HURT = create("inferno_hurt");
    public static final SoundEvent INFERNO_PRE_ATTACK = create("inferno_pre_attack");
    public static final SoundEvent INFERNO_LOOP = create("inferno_loop");
    public static final SoundEvent INFERNO_DEATH = create("inferno_death");

    public static final SoundEvent WILDFIRE_AMBIENT = create("wildfire_ambient");
    public static final SoundEvent WILDFIRE_HURT = create("wildfire_hurt");
    public static final SoundEvent WILDFIRE_PRE_ATTACK = create("wildfire_pre_attack");
    public static final SoundEvent WILDFIRE_SHOOT = create("wildfire_shoot");
    public static final SoundEvent WILDFIRE_SHOCKWAVE = create("wildfire_shockwave");
    public static final SoundEvent WILDFIRE_SHIELD_BREAK = create("wildfire_shield_break");
    public static final SoundEvent WILDFIRE_SHIELD_BREAK_VOCAL = create("wildfire_shield_break_vocal");
    public static final SoundEvent WILDFIRE_SHIELD_REGEN = create("wildfire_shield_regen");
    public static final SoundEvent WILDFIRE_LOOP = create("wildfire_loop");
    public static final SoundEvent WILDFIRE_STEP = create("wildfire_step");
    public static final SoundEvent WILDFIRE_DEATH = create("wildfire_death");

    public static final SoundEvent WARLOCK_AMBIENT = create("warlock_ambient");
    public static final SoundEvent WARLOCK_HURT = create("warlock_hurt");
    public static final SoundEvent WARLOCK_CELEBRATE = create("warlock_celebrate");
    public static final SoundEvent WARLOCK_DEATH = create("warlock_death");

    public static final SoundEvent HERETIC_AMBIENT = create("heretic_ambient");
    public static final SoundEvent HERETIC_HURT = create("heretic_hurt");
    public static final SoundEvent HERETIC_CHANT = create("heretic_chant");
    public static final SoundEvent HERETIC_CELEBRATE = create("heretic_celebrate");
    public static final SoundEvent HERETIC_DEATH = create("heretic_death");

    public static final SoundEvent MAVERICK_AMBIENT = create("maverick_ambient");
    public static final SoundEvent MAVERICK_HURT = create("maverick_hurt");
    public static final SoundEvent MAVERICK_CELEBRATE = create("maverick_celebrate");
    public static final SoundEvent MAVERICK_DEATH = create("maverick_death");

    public static final SoundEvent CRONE_AMBIENT = create("crone_ambient");
    public static final SoundEvent CRONE_LAUGH = create("crone_laugh");
    public static final SoundEvent CRONE_DEATH = create("crone_death");

    public static final SoundEvent HERESIARCH_AMBIENT = create("heresiarch_ambient");
    public static final SoundEvent HERESIARCH_HURT = create("heresiarch_hurt");
    public static final SoundEvent HERESIARCH_DEATH = create("heresiarch_death");

    public static final SoundEvent MCD_HOGLIN_AMBIENT = create("mcd_hoglin_ambient");
    public static final SoundEvent MCD_HOGLIN_HURT = create("mcd_hoglin_hurt");
    public static final SoundEvent MCD_HOGLIN_GROWL = create("mcd_hoglin_growl");
    public static final SoundEvent MCD_HOGLIN_SQUEEL = create("mcd_hoglin_squeel");
    public static final SoundEvent MCD_HOGLIN_GALLOP = create("mcd_hoglin_gallop");
    public static final SoundEvent MCD_HOGLIN_STEP = create("mcd_hoglin_step");
    public static final SoundEvent MCD_HOGLIN_DEATH = create("mcd_hoglin_death");

    public static final SoundEvent WIGHT_AMBIENT = create("wight_ambient");
    public static final SoundEvent WIGHT_HURT = create("wight_hurt");
    public static final SoundEvent WIGHT_SCREAM = create("wight_scream");
    public static final SoundEvent WIGHT_SUMMON = create("wight_summon");
    public static final SoundEvent WIGHT_PRE_SWING = create("wight_pre_swing");
    public static final SoundEvent WIGHT_SWING = create("wight_swing");
    public static final SoundEvent WIGHT_TELEPORT_SCREAM = create("wight_teleport_scream");
    public static final SoundEvent WIGHT_TELEPORT = create("wight_teleport");
    public static final SoundEvent WIGHT_LOOP = create("wight_loop");
    public static final SoundEvent WIGHT_DEATH = create("wight_death");

    public static final SoundEvent MAGGOT_AMBIENT = create("maggot_ambient");
    public static final SoundEvent MAGGOT_HURT = create("maggot_hurt");
    public static final SoundEvent MAGGOT_ATTACK = create("maggot_attack");
    public static final SoundEvent MAGGOT_STEP = create("maggot_step");
    public static final SoundEvent MAGGOT_DEATH = create("maggot_death");

    public static final SoundEvent FLY_LOOP = create("fly_loop");
    public static final SoundEvent FLY_HURT = create("fly_hurt");
    public static final SoundEvent FLY_DEATH = create("fly_death");

    public static final SoundEvent SKULL_LORD_AMBIENT = create("skull_lord_ambient");
    public static final SoundEvent SKULL_LORD_HURT = create("skull_lord_hurt");
    public static final SoundEvent SKULL_LORD_CHARGE = create("skull_lord_charge");
    public static final SoundEvent SKULL_LORD_SHOOT = create("skull_lord_shoot");
    public static final SoundEvent SKULL_LORD_LASER_BEGIN = create("skull_lord_laser_begin");
    public static final SoundEvent SKULL_LORD_LASER_START = create("skull_lord_laser_start");
    public static final SoundEvent SKULL_LORD_FLY = create("skull_lord_fly");
    public static final SoundEvent SKULL_LORD_DEATH = create("skull_lord_death");

    public static final SoundEvent SORCERER_AMBIENT = create("sorcerer_ambient");
    public static final SoundEvent SORCERER_HURT = create("sorcerer_hurt");
    public static final SoundEvent SORCERER_DEATH = create("sorcerer_death");

    public static final SoundEvent TORMENTOR_AMBIENT = create("tormentor_ambient");
    public static final SoundEvent TORMENTOR_HURT = create("tormentor_hurt");
    public static final SoundEvent TORMENTOR_CHARGE = create("tormentor_charge");
    public static final SoundEvent TORMENTOR_CELEBRATE = create("tormentor_celebrate");
    public static final SoundEvent TORMENTOR_DEATH = create("tormentor_death");

    public static final SoundEvent INQUILLAGER_AMBIENT = create("inquillager_ambient");
    public static final SoundEvent INQUILLAGER_HURT = create("inquillager_hurt");
    public static final SoundEvent INQUILLAGER_CELEBRATE = create("inquillager_celebrate");
    public static final SoundEvent INQUILLAGER_DEATH = create("inquillager_death");

    public static final SoundEvent CONQUILLAGER_AMBIENT = create("conquillager_ambient");
    public static final SoundEvent CONQUILLAGER_HURT = create("conquillager_hurt");
    public static final SoundEvent CONQUILLAGER_CELEBRATE = create("conquillager_celebrate");
    public static final SoundEvent CONQUILLAGER_DEATH = create("conquillager_death");

    public static final SoundEvent PIKER_AMBIENT = create("piker_ambient");
    public static final SoundEvent PIKER_HURT = create("piker_hurt");
    public static final SoundEvent PIKER_SWING = create("piker_swing");
    public static final SoundEvent PIKER_PIKE = create("piker_pike");
    public static final SoundEvent PIKER_CELEBRATE = create("piker_celebrate");
    public static final SoundEvent PIKER_STEP = create("piker_step");
    public static final SoundEvent PIKER_DEATH = create("piker_death");

    public static final SoundEvent TRAMPLER_AMBIENT = create("trampler_ambient");
    public static final SoundEvent TRAMPLER_HURT = create("trampler_hurt");
    public static final SoundEvent TRAMPLER_CELEBRATE = create("trampler_celebrate");
    public static final SoundEvent TRAMPLER_DEATH = create("trampler_death");

    public static final SoundEvent STORM_CASTER_AMBIENT = create("storm_caster_ambient");
    public static final SoundEvent STORM_CASTER_HURT = create("storm_caster_hurt");
    public static final SoundEvent STORM_CASTER_MONSOON = create("storm_caster_monsoon");
    public static final SoundEvent STORM_CASTER_DISCHARGE = create("storm_caster_discharge");
    public static final SoundEvent STORM_CASTER_CELEBRATE = create("storm_caster_celebrate");
    public static final SoundEvent STORM_CASTER_DEATH = create("storm_caster_death");

    public static final SoundEvent CRYOLOGER_AMBIENT = create("cryologer_ambient");
    public static final SoundEvent CRYOLOGER_HURT = create("cryologer_hurt");
    public static final SoundEvent CRYOLOGER_HAIL = create("cryologer_hail");
    public static final SoundEvent CRYOLOGER_WALL = create("cryologer_wall");
    public static final SoundEvent CRYOLOGER_CHUNK = create("cryologer_chunk");
    public static final SoundEvent CRYOLOGER_CELEBRATE = create("cryologer_celebrate");
    public static final SoundEvent CRYOLOGER_DEATH = create("cryologer_death");

    public static final SoundEvent PREACHER_AMBIENT = create("preacher_ambient");
    public static final SoundEvent PREACHER_HURT = create("preacher_hurt");
    public static final SoundEvent PREACHER_CAST = create("preacher_cast");
    public static final SoundEvent PREACHER_DEATH = create("preacher_death");

    public static final SoundEvent REAPER_AMBIENT = create("reaper_ambient");
    public static final SoundEvent REAPER_HURT = create("reaper_hurt");
    public static final SoundEvent REAPER_FLY = create("reaper_fly");
    public static final SoundEvent REAPER_SWING = create("reaper_swing");
    public static final SoundEvent REAPER_DEATH = create("reaper_death");

    public static final SoundEvent WRAITH_AMBIENT = create("wraith_ambient");
    public static final SoundEvent WRAITH_HURT = create("wraith_hurt");
    public static final SoundEvent WRAITH_FLY = create("wraith_fly");
    public static final SoundEvent WRAITH_ATTACK = create("wraith_attack");
    public static final SoundEvent WRAITH_PUKE = create("wraith_puke");
    public static final SoundEvent WRAITH_FIRE = create("wraith_fire");
    public static final SoundEvent WRAITH_TELEPORT = create("wraith_teleport");
    public static final SoundEvent WRAITH_DEATH = create("wraith_death");

    public static final SoundEvent TOWER_WRAITH_AMBIENT = create("tower_wraith_ambient");
    public static final SoundEvent TOWER_WRAITH_HURT = create("tower_wraith_hurt");
    public static final SoundEvent TOWER_WRAITH_FLY = create("tower_wraith_fly");
    public static final SoundEvent TOWER_WRAITH_ATTACK = create("tower_wraith_attack");
    public static final SoundEvent TOWER_WRAITH_ACID = create("tower_wraith_acid");
    public static final SoundEvent TOWER_WRAITH_ACID_VOCAL = create("tower_wraith_acid_vocal");
    public static final SoundEvent TOWER_WRAITH_TELEPORT_IN = create("tower_wraith_teleport_in");
    public static final SoundEvent TOWER_WRAITH_TELEPORT_OUT = create("tower_wraith_teleport_out");
    public static final SoundEvent TOWER_WRAITH_DEATH = create("tower_wraith_death");

    public static final SoundEvent FROZEN_ZOMBIE_AMBIENT = create("frozen_zombie_ambient");
    public static final SoundEvent FROZEN_ZOMBIE_HURT = create("frozen_zombie_hurt");
    public static final SoundEvent FROZEN_ZOMBIE_SNOWBALL = create("frozen_zombie_snowball");
    public static final SoundEvent FROZEN_ZOMBIE_DEATH = create("frozen_zombie_death");

    public static final SoundEvent JUNGLE_ZOMBIE_AMBIENT = create("jungle_zombie_ambient");
    public static final SoundEvent JUNGLE_ZOMBIE_HURT = create("jungle_zombie_hurt");
    public static final SoundEvent JUNGLE_ZOMBIE_STEP = create("jungle_zombie_step");
    public static final SoundEvent JUNGLE_ZOMBIE_DEATH = create("jungle_zombie_death");

    public static final SoundEvent FRAYED_AMBIENT = create("frayed_ambient");
    public static final SoundEvent FRAYED_HURT = create("frayed_hurt");
    public static final SoundEvent FRAYED_STEP = create("frayed_step");
    public static final SoundEvent FRAYED_DEATH = create("frayed_death");

    public static final SoundEvent BLACKGUARD_PRE_ATTACK = create("blackguard_pre_attack");
    public static final SoundEvent BLACKGUARD_SMASH = create("blackguard_smash");
    public static final SoundEvent BLACKGUARD_STEP = create("blackguard_step");

    public static final SoundEvent MOSSY_SKELETON_AMBIENT = create("mossy_skeleton_ambient");
    public static final SoundEvent MOSSY_SKELETON_HURT = create("mossy_skeleton_hurt");
    public static final SoundEvent MOSSY_SKELETON_SHOOT = create("mossy_skeleton_shoot");
    public static final SoundEvent MOSSY_SKELETON_STEP = create("mossy_skeleton_step");
    public static final SoundEvent MOSSY_SKELETON_DEATH = create("mossy_skeleton_death");

    public static final SoundEvent SUNKEN_SKELETON_AMBIENT = create("sunken_skeleton_ambient");
    public static final SoundEvent SUNKEN_SKELETON_HURT = create("sunken_skeleton_hurt");
    public static final SoundEvent SUNKEN_SKELETON_SHOOT = create("sunken_skeleton_shoot");
    public static final SoundEvent SUNKEN_SKELETON_STEP = create("sunken_skeleton_step");
    public static final SoundEvent SUNKEN_SKELETON_DEATH = create("sunken_skeleton_death");

    public static final SoundEvent RATTLED_AMBIENT = create("rattled_ambient");
    public static final SoundEvent RATTLED_HURT = create("rattled_hurt");
    public static final SoundEvent RATTLED_STEP = create("rattled_step");
    public static final SoundEvent RATTLED_DEATH = create("rattled_death");

    public static final SoundEvent NECROMANCER_AMBIENT = create("necromancer_ambient");
    public static final SoundEvent NECROMANCER_HURT = create("necromancer_hurt");
    public static final SoundEvent NECROMANCER_LAUGH = create("necromancer_laugh");
    public static final SoundEvent NECROMANCER_SUMMON = create("necromancer_summon");
    public static final SoundEvent NECROMANCER_STEP = create("necromancer_step");
    public static final SoundEvent NECROMANCER_DEATH = create("necromancer_death");

    public static final SoundEvent MOSSY_NECROMANCER_AMBIENT = create("mossy_necromancer_ambient");
    public static final SoundEvent MOSSY_NECROMANCER_HURT = create("mossy_necromancer_hurt");
    public static final SoundEvent MOSSY_NECROMANCER_LAUGH = create("mossy_necromancer_laugh");
    public static final SoundEvent MOSSY_NECROMANCER_STEP = create("mossy_necromancer_step");
    public static final SoundEvent MOSSY_NECROMANCER_DEATH = create("mossy_necromancer_death");

    public static final SoundEvent DROWNED_NECROMANCER_AMBIENT = create("drowned_necromancer_ambient");
    public static final SoundEvent DROWNED_NECROMANCER_HURT = create("drowned_necromancer_hurt");
    public static final SoundEvent DROWNED_NECROMANCER_PREPARE = create("drowned_necromancer_prepare");
    public static final SoundEvent DROWNED_NECROMANCER_SUMMON = create("drowned_necromancer_summon");
    public static final SoundEvent DROWNED_NECROMANCER_SWIM = create("drowned_necromancer_swim");
    public static final SoundEvent DROWNED_NECROMANCER_DEATH = create("drowned_necromancer_death");

    public static final SoundEvent WITHER_NECROMANCER_AMBIENT = create("wither_necromancer_ambient");
    public static final SoundEvent WITHER_NECROMANCER_HURT = create("wither_necromancer_hurt");
    public static final SoundEvent WITHER_NECROMANCER_DEATH = create("wither_necromancer_death");

    public static final SoundEvent VANGUARD_AMBIENT = create("vanguard_ambient");
    public static final SoundEvent VANGUARD_HURT = create("vanguard_hurt");
    public static final SoundEvent VANGUARD_SPEAR = create("vanguard_spear");
    public static final SoundEvent VANGUARD_STEP = create("vanguard_step");
    public static final SoundEvent VANGUARD_DEATH = create("vanguard_death");

    public static final SoundEvent HAUNTED_ARMOR_AMBIENT = create("haunted_armor_ambient");
    public static final SoundEvent HAUNTED_ARMOR_HURT = create("haunted_armor_hurt");
    public static final SoundEvent HAUNTED_ARMOR_STEP = create("haunted_armor_step");
    public static final SoundEvent HAUNTED_ARMOR_DEATH = create("haunted_armor_death");

    public static final SoundEvent ZOMBIE_RAVAGER_AMBIENT = create("zombie_ravager_ambient");
    public static final SoundEvent ZOMBIE_RAVAGER_HURT = create("zombie_ravager_hurt");
    public static final SoundEvent ZOMBIE_RAVAGER_BITE = create("zombie_ravager_bite");
    public static final SoundEvent ZOMBIE_RAVAGER_STUN = create("zombie_ravager_stun");
    public static final SoundEvent ZOMBIE_RAVAGER_ROAR = create("zombie_ravager_roar");
    public static final SoundEvent ZOMBIE_RAVAGER_STEP = create("zombie_ravager_step");
    public static final SoundEvent ZOMBIE_RAVAGER_DEATH = create("zombie_ravager_death");

    public static final SoundEvent RAVAGED_AMBIENT = create("ravaged_ambient");
    public static final SoundEvent RAVAGED_HURT = create("ravaged_hurt");
    public static final SoundEvent RAVAGED_BITE = create("ravaged_bite");
    public static final SoundEvent RAVAGED_EAT = create("ravaged_eat");
    public static final SoundEvent RAVAGED_STEP = create("ravaged_step");
    public static final SoundEvent RAVAGED_BIG_STEP = create("ravaged_big_step");
    public static final SoundEvent RAVAGED_DEATH = create("ravaged_death");

    public static final SoundEvent TROPICAL_SLIME_SMALL_HURT = create("tropical_slime_small_hurt");
    public static final SoundEvent TROPICAL_SLIME_SMALL_ATTACK = create("tropical_slime_small_attack");
    public static final SoundEvent TROPICAL_SLIME_SMALL_JUMP = create("tropical_slime_small_jump");
    public static final SoundEvent TROPICAL_SLIME_SMALL_SQUISH = create("tropical_slime_small_squish");
    public static final SoundEvent TROPICAL_SLIME_SMALL_DEATH = create("tropical_slime_small_death");

    public static final SoundEvent TROPICAL_SLIME_MEDIUM_HURT = create("tropical_slime_medium_hurt");
    public static final SoundEvent TROPICAL_SLIME_MEDIUM_ATTACK = create("tropical_slime_medium_attack");
    public static final SoundEvent TROPICAL_SLIME_MEDIUM_JUMP = create("tropical_slime_medium_jump");
    public static final SoundEvent TROPICAL_SLIME_MEDIUM_SQUISH = create("tropical_slime_medium_squish");
    public static final SoundEvent TROPICAL_SLIME_MEDIUM_DEATH = create("tropical_slime_medium_death");

    public static final SoundEvent TROPICAL_SLIME_LARGE_HURT = create("tropical_slime_large_hurt");
    public static final SoundEvent TROPICAL_SLIME_LARGE_ATTACK = create("tropical_slime_large_attack");
    public static final SoundEvent TROPICAL_SLIME_LARGE_JUMP = create("tropical_slime_large_jump");
    public static final SoundEvent TROPICAL_SLIME_LARGE_SQUISH = create("tropical_slime_large_squish");
    public static final SoundEvent TROPICAL_SLIME_LARGE_DEATH = create("tropical_slime_large_death");

    public static final SoundEvent ICY_SPIDER_AMBIENT = create("icy_spider_ambient");
    public static final SoundEvent ICY_SPIDER_HURT = create("icy_spider_hurt");
    public static final SoundEvent ICY_SPIDER_DEATH = create("icy_spider_death");

    public static final SoundEvent BONE_SPIDER_AMBIENT = create("bone_spider_ambient");
    public static final SoundEvent BONE_SPIDER_HURT = create("bone_spider_hurt");
    public static final SoundEvent BONE_SPIDER_SPIT = create("bone_spider_spit");
    public static final SoundEvent BONE_SPIDER_STEP = create("bone_spider_step");
    public static final SoundEvent BONE_SPIDER_DEATH = create("bone_spider_death");

    public static final SoundEvent MOUNTAINEER_AMBIENT = create("mountaineer_ambient");
    public static final SoundEvent MOUNTAINEER_HURT = create("mountaineer_hurt");
    public static final SoundEvent MOUNTAINEER_ATTACK = create("mountaineer_attack");
    public static final SoundEvent MOUNTAINEER_CELEBRATE = create("mountaineer_celebrate");
    public static final SoundEvent MOUNTAINEER_DEATH = create("mountaineer_death");

    public static final SoundEvent GEOMANCER_AMBIENT = create("geomancer_ambient");
    public static final SoundEvent GEOMANCER_HURT = create("geomancer_hurt");
    public static final SoundEvent GEOMANCER_PRE_ATTACK = create("geomancer_pre_attack");
    public static final SoundEvent GEOMANCER_ATTACK = create("geomancer_attack");
    public static final SoundEvent GEOMANCER_DEATH = create("geomancer_death");

    public static final SoundEvent ICEOLOGER_AMBIENT = create("iceologer_ambient");
    public static final SoundEvent ICEOLOGER_HURT = create("iceologer_hurt");
    public static final SoundEvent ICEOLOGER_ATTACK = create("iceologer_attack");
    public static final SoundEvent ICEOLOGER_DEATH = create("iceologer_death");

    public static final SoundEvent WIND_CALLER_AMBIENT = create("wind_caller_ambient");
    public static final SoundEvent WIND_CALLER_HURT = create("wind_caller_hurt");
    public static final SoundEvent WIND_CALLER_BLAST = create("wind_caller_blast");
    public static final SoundEvent WIND_CALLER_UPDRAFT = create("wind_caller_updraft");
    public static final SoundEvent WIND_CALLER_DEATH = create("wind_caller_death");

    public static final SoundEvent SKELETON_WOLF_AMBIENT = create("skeleton_wolf_ambient");
    public static final SoundEvent SKELETON_WOLF_HURT = create("skeleton_wolf_hurt");
    public static final SoundEvent SKELETON_WOLF_GROWL = create("skeleton_wolf_growl");
    public static final SoundEvent SKELETON_WOLF_HOWL = create("skeleton_wolf_howl");
    public static final SoundEvent SKELETON_WOLF_PANT = create("skeleton_wolf_pant");
    public static final SoundEvent SKELETON_WOLF_SHAKE = create("skeleton_wolf_shake");
    public static final SoundEvent SKELETON_WOLF_WHINE = create("skeleton_wolf_whine");
    public static final SoundEvent SKELETON_WOLF_STEP = create("skeleton_wolf_step");
    public static final SoundEvent SKELETON_WOLF_DEATH = create("skeleton_wolf_death");

    public static final SoundEvent BLACK_BEAST_AMBIENT = create("black_beast_ambient");
    public static final SoundEvent BLACK_BEAST_HURT = create("black_beast_hurt");
    public static final SoundEvent BLACK_BEAST_ROAR = create("black_beast_roar");
    public static final SoundEvent BLACK_BEAST_CLAW = create("black_beast_claw");
    public static final SoundEvent BLACK_BEAST_STEP = create("black_beast_step");
    public static final SoundEvent BLACK_BEAST_DEATH = create("black_beast_death");

    public static final SoundEvent WHISPERER_AMBIENT = create("whisperer_ambient");
    public static final SoundEvent WHISPERER_HURT = create("whisperer_hurt");
    public static final SoundEvent WHISPERER_ATTACK = create("whisperer_attack");
    public static final SoundEvent WHISPERER_SUMMON = create("whisperer_summon");
    public static final SoundEvent WHISPERER_SUMMON_POISON = create("whisperer_summon_poison");
    public static final SoundEvent WHISPERER_CAST_THORNS = create("whisperer_cast_thorns");
    public static final SoundEvent WHISPERER_SUMMON_THORNS = create("whisperer_summon_thorns");
    public static final SoundEvent WHISPERER_STEP = create("whisperer_step");
    public static final SoundEvent WHISPERER_DEATH = create("whisperer_death");

    public static final SoundEvent WAVEWHISPERER_AMBIENT = create("wavewhisperer_ambient");
    public static final SoundEvent WAVEWHISPERER_HURT = create("wavewhisperer_hurt");
    public static final SoundEvent WAVEWHISPERER_ATTACK = create("wavewhisperer_attack");
    public static final SoundEvent WAVEWHISPERER_SUMMON = create("wavewhisperer_summon");
    public static final SoundEvent WAVEWHISPERER_SUMMON_POISON = create("wavewhisperer_summon_poison");
    public static final SoundEvent WAVEWHISPERER_CAST_THORNS = create("wavewhisperer_cast_thorns");
    public static final SoundEvent WAVEWHISPERER_SUMMON_THORNS = create("wavewhisperer_summon_thorns");
    public static final SoundEvent WAVEWHISPERER_STEP = create("wavewhisperer_step");
    public static final SoundEvent WAVEWHISPERER_DEATH = create("wavewhisperer_death");

    public static final SoundEvent LEAPLEAF_AMBIENT = create("leapleaf_ambient");
    public static final SoundEvent LEAPLEAF_HURT = create("leapleaf_hurt");
    public static final SoundEvent LEAPLEAF_SMASH = create("leapleaf_smash");
    public static final SoundEvent LEAPLEAF_CHARGE = create("leapleaf_charge");
    public static final SoundEvent LEAPLEAF_LEAP = create("leapleaf_leap");
    public static final SoundEvent LEAPLEAF_REST = create("leapleaf_rest");
    public static final SoundEvent LEAPLEAF_ALERT = create("leapleaf_alert");
    public static final SoundEvent LEAPLEAF_STEP = create("leapleaf_step");
    public static final SoundEvent LEAPLEAF_DEATH = create("leapleaf_death");

    public static final SoundEvent MINISTROSITY_STONE_IDLE = create("ministrosity_stone_idle");
    public static final SoundEvent MINISTROSITY_REDSTONE_IDLE = create("ministrosity_redstone_idle");
    public static final SoundEvent MINISTROSITY_HURT = create("ministrosity_hurt");
    public static final SoundEvent MINISTROSITY_STEP = create("ministrosity_step");
    public static final SoundEvent MINISTROSITY_DEATH = create("ministrosity_death");

    public static final SoundEvent ICE_GOLEM_HURT = create("ice_golem_hurt");
    public static final SoundEvent ICE_GOLEM_SWING = create("ice_golem_swing");
    public static final SoundEvent ICE_GOLEM_ATTACK = create("ice_golem_attack");
    public static final SoundEvent ICE_GOLEM_PRE_SMASH = create("ice_golem_pre_smash");
    public static final SoundEvent ICE_GOLEM_STEP = create("ice_golem_step");
    public static final SoundEvent ICE_GOLEM_DEATH = create("ice_golem_death");

    public static final SoundEvent SQUALL_GOLEM_HURT = create("squall_golem_hurt");
    public static final SoundEvent SQUALL_GOLEM_ACTIVATE = create("squall_golem_activate");
    public static final SoundEvent SQUALL_GOLEM_DEACTIVATE = create("squall_golem_deactivate");
    public static final SoundEvent SQUALL_GOLEM_WIND_START = create("squall_golem_wind_start");
    public static final SoundEvent SQUALL_GOLEM_WIND_SLOW = create("squall_golem_wind_slow");
    public static final SoundEvent SQUALL_GOLEM_WIND_FAST = create("squall_golem_wind_fast");
    public static final SoundEvent SQUALL_GOLEM_ALERT = create("squall_golem_alert");
    public static final SoundEvent SQUALL_GOLEM_ATTACK = create("squall_golem_attack");
    public static final SoundEvent SQUALL_GOLEM_STEP = create("squall_golem_step");
    public static final SoundEvent SQUALL_GOLEM_DEATH = create("squall_golem_death");

    public static final SoundEvent REDSTONE_GOLEM_AMBIENT = create("redstone_golem_ambient");
    public static final SoundEvent REDSTONE_GOLEM_HURT = create("redstone_golem_hurt");
    public static final SoundEvent REDSTONE_GOLEM_SUMMON = create("redstone_golem_summon");
    public static final SoundEvent REDSTONE_GOLEM_PRE_ATTACK = create("redstone_golem_pre_attack");
    public static final SoundEvent REDSTONE_GOLEM_ATTACK = create("redstone_golem_attack");
    public static final SoundEvent REDSTONE_GOLEM_CHEST = create("redstone_golem_chest");
    public static final SoundEvent REDSTONE_GOLEM_GROWL = create("redstone_golem_growl");
    public static final SoundEvent REDSTONE_GOLEM_ELECTRIC_SPARKS = create("redstone_golem_electric_sparks");
    public static final SoundEvent REDSTONE_GOLEM_STEP = create("redstone_golem_step");
    public static final SoundEvent REDSTONE_GOLEM_DEATH = create("redstone_golem_death");

    public static final SoundEvent GRAVE_GOLEM_AMBIENT = create("grave_golem_ambient");
    public static final SoundEvent GRAVE_GOLEM_HURT = create("grave_golem_hurt");
    public static final SoundEvent GRAVE_GOLEM_AWAKEN = create("grave_golem_awaken");
    public static final SoundEvent GRAVE_GOLEM_ARM = create("grave_golem_arm");
    public static final SoundEvent GRAVE_GOLEM_BLAST = create("grave_golem_blast");
    public static final SoundEvent GRAVE_GOLEM_GROWL = create("grave_golem_growl");
    public static final SoundEvent GRAVE_GOLEM_STEP = create("grave_golem_step");
    public static final SoundEvent GRAVE_GOLEM_DEATH = create("grave_golem_death");

    public static final SoundEvent HAUNT_AMBIENT = create("haunt_ambient");
    public static final SoundEvent HAUNT_HURT = create("haunt_hurt");
    public static final SoundEvent HAUNT_FLY = create("haunt_fly");

    public static final SoundEvent REDSTONE_MONSTROSITY_AMBIENT = create("redstone_monstrosity_ambient");
    public static final SoundEvent REDSTONE_MONSTROSITY_HURT = create("redstone_monstrosity_hurt");
    public static final SoundEvent REDSTONE_MONSTROSITY_AWAKEN = create("redstone_monstrosity_awaken");
    public static final SoundEvent REDSTONE_MONSTROSITY_ARM = create("redstone_monstrosity_arm");
    public static final SoundEvent REDSTONE_MONSTROSITY_CHARGE = create("redstone_monstrosity_charge");
    public static final SoundEvent REDSTONE_MONSTROSITY_SMASH = create("redstone_monstrosity_smash");
    public static final SoundEvent REDSTONE_MONSTROSITY_BELCH = create("redstone_monstrosity_belch");
    public static final SoundEvent REDSTONE_MONSTROSITY_GROWL = create("redstone_monstrosity_growl");
    public static final SoundEvent REDSTONE_MONSTROSITY_CHASE = create("redstone_monstrosity_chase");
    public static final SoundEvent REDSTONE_MONSTROSITY_STEP = create("redstone_monstrosity_step");
    public static final SoundEvent REDSTONE_MONSTROSITY_DEATH = create("redstone_monstrosity_death");

    public static final SoundEvent REDSTONE_CUBE_BURST = create("redstone_cube_burst");
    public static final SoundEvent REDSTONE_CUBE_HURT = create("redstone_cube_hurt");
    public static final SoundEvent REDSTONE_CUBE_WALK = create("redstone_cube_walk");
    public static final SoundEvent REDSTONE_CUBE_ATTACK = create("redstone_cube_attack");

    public static final SoundEvent WATCHLING_AMBIENT = create("watchling_ambient");
    public static final SoundEvent WATCHLING_HURT = create("watchling_hurt");
    public static final SoundEvent WATCHLING_ATTACK = create("watchling_attack");
    public static final SoundEvent WATCHLING_SMASH = create("watchling_smash");
    public static final SoundEvent WATCHLING_STEP = create("watchling_step");
    public static final SoundEvent WATCHLING_DEATH = create("watchling_death");

    public static final SoundEvent BLASTLING_AMBIENT = create("blastling_ambient");
    public static final SoundEvent BLASTLING_HURT = create("blastling_hurt");
    public static final SoundEvent BLASTLING_PRE_ATTACK = create("blastling_pre_attack");
    public static final SoundEvent BLASTLING_SHOOT = create("blastling_shoot");
    public static final SoundEvent BLASTLING_STEP = create("blastling_step");
    public static final SoundEvent BLASTLING_DEATH = create("blastling_death");

    public static final SoundEvent SNARELING_AMBIENT = create("snareling_ambient");
    public static final SoundEvent SNARELING_HURT = create("snareling_hurt");
    public static final SoundEvent SNARELING_AMBIENT_SWING = create("snareling_ambient_swing");
    public static final SoundEvent SNARELING_SHOOT = create("snareling_shoot");
    public static final SoundEvent SNARELING_MELEE = create("snareling_melee");
    public static final SoundEvent SNARELING_MELEE_VOCAL = create("snareling_melee_vocal");
    public static final SoundEvent SNARELING_STEP = create("snareling_step");
    public static final SoundEvent SNARELING_DEATH = create("snareling_death");

    public static final SoundEvent ENDERSENT_AMBIENT = create("endersent_ambient");
    public static final SoundEvent ENDERSENT_HURT = create("endersent_hurt");
    public static final SoundEvent ENDERSENT_AMBIENT_SMASH = create("endersent_ambient_smash");
    public static final SoundEvent ENDERSENT_ATTACK = create("endersent_attack");
    public static final SoundEvent ENDERSENT_SWING = create("endersent_swing");
    public static final SoundEvent ENDERSENT_TELEPORT_SMASH = create("endersent_teleport_smash");
    public static final SoundEvent ENDERSENT_DEADLY_ESCAPE = create("endersent_deadly_escape");
    public static final SoundEvent ENDERSENT_STEP = create("endersent_step");
    public static final SoundEvent ENDERSENT_DEATH = create("endersent_death");

    public static final SoundEvent ENDER_KEEPER_HURT = create("ender_keeper_hurt");
    public static final SoundEvent ENDER_KEEPER_DEATH = create("ender_keeper_death");

    public static final SoundEvent MINISTER_AMBIENT = create("minister_ambient");
    public static final SoundEvent MINISTER_HURT = create("minister_hurt");
    public static final SoundEvent MINISTER_LAUGH = create("minister_laugh");
    public static final SoundEvent MINISTER_COMMAND = create("minister_command");
    public static final SoundEvent MINISTER_CAST = create("minister_cast");
    public static final SoundEvent MINISTER_SPEECH = create("minister_speech");
    public static final SoundEvent MINISTER_CELEBRATE = create("minister_celebrate");
    public static final SoundEvent MINISTER_DEATH = create("minister_death");

    public static final SoundEvent VIZIER_AMBIENT = create("vizier_ambient");
    public static final SoundEvent VIZIER_HURT = create("vizier_hurt");
    public static final SoundEvent VIZIER_CONFUSE = create("vizier_confuse");
    public static final SoundEvent VIZIER_RAGE = create("vizier_rage");
    public static final SoundEvent VIZIER_CELEBRATE = create("vizier_celebrate");
    public static final SoundEvent VIZIER_SCREAM = create("vizier_scream");
    public static final SoundEvent VIZIER_DEATH = create("vizier_death");

    public static final SoundEvent ICE_CHUNK_IDLE = create("ice_chunk_idle");
    public static final SoundEvent ICE_CHUNK_SUMMON = create("ice_chunk_summon");
    public static final SoundEvent ICE_CHUNK_DROP = create("ice_chunk_drop");
    public static final SoundEvent ICE_CHUNK_HIT = create("ice_chunk_hit");

    public static final SoundEvent WALL_SPAWN = create("wall_spawn");
    public static final SoundEvent WALL_HIT = create("wall_hit");
    public static final SoundEvent WALL_ERUPT = create("wall_erupt");
    public static final SoundEvent WALL_DISAPPEAR = create("wall_disappear");

    public static final SoundEvent BOMB_SPAWN = create("bomb_spawn");
    public static final SoundEvent BOMB_PULSE = create("bomb_pulse");
    public static final SoundEvent BOMB_FUSE = create("bomb_fuse");
    public static final SoundEvent BOMB_LOAD = create("bomb_load");
    public static final SoundEvent BOMB_SPARKLE = create("bomb_sparkle");

    public static final SoundEvent QUICK_GROWING_VINE_BURST = create("quick_growing_vine_burst");
    public static final SoundEvent QUICK_GROWING_VINE_BURROW = create("quick_growing_vine_burrow");
    public static final SoundEvent QUICK_GROWING_VINE_HURT = create("quick_growing_vine_hurt");
    public static final SoundEvent QUICK_GROWING_VINE_DEATH = create("quick_growing_vine_death");

    public static final SoundEvent QUICK_GROWING_KELP_BURST = create("quick_growing_kelp_burst");
    public static final SoundEvent QUICK_GROWING_KELP_BURROW = create("quick_growing_kelp_burrow");
    public static final SoundEvent QUICK_GROWING_KELP_HURT = create("quick_growing_kelp_hurt");
    public static final SoundEvent QUICK_GROWING_KELP_DEATH = create("quick_growing_kelp_death");

    public static final SoundEvent POISON_QUILL_VINE_AMBIENT = create("poison_quill_vine_ambient");
    public static final SoundEvent POISON_QUILL_VINE_HURT = create("poison_quill_vine_hurt");
    public static final SoundEvent POISON_QUILL_VINE_OPEN = create("poison_quill_vine_open");
    public static final SoundEvent POISON_QUILL_VINE_CLOSE = create("poison_quill_vine_close");
    public static final SoundEvent POISON_QUILL_VINE_SHOOT = create("poison_quill_vine_shoot");
    public static final SoundEvent POISON_QUILL_VINE_BURST = create("poison_quill_vine_burst");
    public static final SoundEvent POISON_QUILL_VINE_DEATH = create("poison_quill_vine_death");

    public static final SoundEvent POISON_ANEMONE_AMBIENT = create("poison_anemone_ambient");
    public static final SoundEvent POISON_ANEMONE_HURT = create("poison_anemone_hurt");
    public static final SoundEvent POISON_ANEMONE_OPEN = create("poison_anemone_open");
    public static final SoundEvent POISON_ANEMONE_CLOSE = create("poison_anemone_close");
    public static final SoundEvent POISON_ANEMONE_SHOOT = create("poison_anemone_shoot");
    public static final SoundEvent POISON_ANEMONE_BURST = create("poison_anemone_burst");
    public static final SoundEvent POISON_ANEMONE_DEATH = create("poison_anemone_death");

    public static final SoundEvent POISON_QUILL_IMPACT = create("poison_quill_impact");

    public static final SoundEvent POISON_QUILL_AQUA_WHOOSH = create("poison_quill_aqua_whoosh");
    public static final SoundEvent POISON_QUILL_AQUA_IMPACT = create("poison_quill_aqua_impact");

    public static final SoundEvent BONE_SHARD_IMPACT = create("bone_shard_impact");

    public static final SoundEvent SHIELD_DEBRIS_IMPACT = create("shield_debris_impact");

    public static final SoundEvent INSECT_SWARM = create("insect_swarm");
    public static final SoundEvent INSECT_SWARM_BITE = create("insect_swarm_bite");

    public static final SoundEvent VINE_TRAP_BURST = create("vine_trap_burst");
    public static final SoundEvent VINE_TRAP_HOLD = create("vine_trap_hold");

    public static final SoundEvent SPIDER_BITE = create("spider_bite");
    public static final SoundEvent SPIDER_CALL = create("spider_call");
    public static final SoundEvent SPIDER_SPIT = create("spider_spit");
    public static final SoundEvent SPIDER_WEB = create("spider_web");

    public static final SoundEvent VILLAGER_CHOP = create("villager_chop");

    public static final SoundEvent ENDERLING_TELEPORT_IN = create("enderling_teleport_in");
    public static final SoundEvent ENDERLING_TELEPORT_OUT = create("enderling_teleport_out");

    public static final SoundEvent VHOE_CHARGE = create("vhoe_charge");
    public static final SoundEvent VHOE_PINWHEEL = create("vhoe_pinwheel");

    public static final SoundEvent VOID_RIFT_OPEN = create("void_rift_open");
    public static final SoundEvent VOID_RIFT = create("void_rift");

    public static final SoundEvent LICH_AMBIENT = create("lich_ambient");
    public static final SoundEvent LICH_HURT = create("lich_hurt");
    public static final SoundEvent LICH_LAUGH = create("lich_laugh");
    public static final SoundEvent LICH_TELEPORT_IN = create("lich_teleport_in");
    public static final SoundEvent LICH_TELEPORT_OUT = create("lich_teleport_out");
    public static final SoundEvent LICH_DEATH = create("lich_death");

    public static final SoundEvent BIOMINE_SPAWN = create("biomine_spawn");
    public static final SoundEvent BIOMINE_TRIGGER = create("biomine_trigger");

    public static final SoundEvent GHAST_DISAPPEAR = create("ghast_disappear");

    public static final SoundEvent DAMNED_SCREAM = create("damned_scream");

    public static final SoundEvent DIRT_DEBRIS = create("dirt_debris");

    public static final SoundEvent THUNDER_STRIKE_EPIC = create("thunder_strike_epic");
    public static final SoundEvent THUNDER_STRIKE_FAST = create("thunder_strike_fast");

    public static final SoundEvent TOCK = create("tock");
    public static final SoundEvent INTRUDER_ALERT = create("intruder_alert");

    public static final SoundEvent ROAR_SPELL = create("roar_spell");
    public static final SoundEvent FIRE_BREATH_START = create("fire_breath_start");
    public static final SoundEvent FIRE_BREATH = create("fire_breath");
    public static final SoundEvent FROST_BREATH = create("frost_breath");
    public static final SoundEvent WATER_JET = create("water_jet");
    public static final SoundEvent BUBBLE_STREAM = create("bubble_stream");
    public static final SoundEvent BURROW = create("burrow_spell");
    public static final SoundEvent FLIGHT = create("flight");
    public static final SoundEvent WHIRLWIND = create("whirlwind");
    public static final SoundEvent IRON_HIDE = create("soul_armor");
    public static final SoundEvent SOUL_HEAL = create("soul_heal");
    public static final SoundEvent END_WALK = create("end_walk");
    public static final SoundEvent PREPARE_SPELL = create("prepare_spell");
    public static final SoundEvent PREPARE_SUMMON = create("prepare_summon");
    public static final SoundEvent FROST_PREPARE_SPELL = create("frost_prepare_spell");
    public static final SoundEvent WILD_PREPARE_SPELL = create("wild_prepare_spell");
    public static final SoundEvent ABYSS_PREPARE_SPELL = create("abyss_prepare_spell");
    public static final SoundEvent VOID_PREPARE_SPELL = create("void_prepare_spell");
    public static final SoundEvent CAST_SPELL = create("cast_spell");
    public static final SoundEvent CAST_SPELL_TWO = create("cast_spell_two");
    public static final SoundEvent VEX_VAPOR = create("vex_vapor");
    public static final SoundEvent SOUL_BOLT_CAST = create("soul_bolt_cast");
    public static final SoundEvent NECRO_CAST = create("necro_cast");
    public static final SoundEvent SHOCK_CAST = create("shock_cast");
    public static final SoundEvent SUMMON_SPELL = create("summon_spell");
    public static final SoundEvent SUMMON_SPELL_FIERY = create("summon_spell_fiery");
    public static final SoundEvent VANGUARD_SPELL = create("vanguard_spell");
    public static final SoundEvent VANGUARD_SUMMON = create("vanguard_summon");
    public static final SoundEvent BOSS_SUMMON = create("boss_summon");
    public static final SoundEvent SWING = create("swing");
    public static final SoundEvent HEAVY_WOOSH = create("heavy_woosh");
    public static final SoundEvent PROJECTILE_SPELL = create("projectile_spell");
    public static final SoundEvent COMMAND = create("command");
    public static final SoundEvent SHIELD_UP = create("shield_up");
    public static final SoundEvent WEAKEN_CAST = create("weaken_cast");
    public static final SoundEvent WEAKEN_CURSE = create("weaken_curse");
    public static final SoundEvent BOLT_IMPACT = create("bolt_impact");
    public static final SoundEvent ENDER_GOO_IMPACT = create("ender_goo_impact");
    public static final SoundEvent SNARELING_GOO_IMPACT = create("snareling_goo_impact");
    public static final SoundEvent CAST_STEAM = create("cast_steam");
    public static final SoundEvent STEAM_IMPACT = create("steam_impact");
    public static final SoundEvent POISON_BOLT_IMPACT = create("poison_bolt_impact");
    public static final SoundEvent HELL_BOLT_SHOOT = create("hell_bolt_shoot");
    public static final SoundEvent HELL_BOLT_IMPACT = create("hell_bolt_impact");
    public static final SoundEvent HELL_BLAST_SHOOT = create("hell_blast_shoot");
    public static final SoundEvent HELL_BLAST_IMPACT = create("hell_blast_impact");
    public static final SoundEvent SOUL_EXPLODE = create("soul_explode");
    public static final SoundEvent RADIANCE_WAVE = create("radiance_wave");
    public static final SoundEvent LEECHING = create("leeching");
    public static final SoundEvent HEAL_SPELL = create("heal_spell");
    public static final SoundEvent WIND = create("wind");
    public static final SoundEvent THUNDERBOLT = create("thunderbolt");
    public static final SoundEvent UPDRAFT_BLAST = create("updraft_blast");
    public static final SoundEvent WIND_BLAST = create("wind_blast");
    public static final SoundEvent WIND_HORN = create("wind_horn");
    public static final SoundEvent ZAP = create("zap");
    public static final SoundEvent TRIDENT_STORM_PRE = create("trident_storm_pre");
    public static final SoundEvent TRIDENT_STORM_EXPLODE = create("trident_storm_explode");
    public static final SoundEvent GRAVITY = create("gravity");
    public static final SoundEvent PLATE = create("plate");
    public static final SoundEvent PLATE_DROP = create("plate_drop");
    public static final SoundEvent SCARY_RECITE = create("scary_recite");
    public static final SoundEvent RUMBLE = create("rumble");
    public static final SoundEvent TOOTH_SPAWN = create("tooth_spawn");
    public static final SoundEvent IMPALE = create("impale");
    public static final SoundEvent ICE_SPIKE_CAST = create("ice_spike_cast");
    public static final SoundEvent ICE_SPIKE_HIT = create("ice_spike_hit");
    public static final SoundEvent SPELL_FAIL = create("spell_fail");
    public static final SoundEvent DOOM = create("doom");
    public static final SoundEvent DEAD_MOAN = create("dead_moan");
    public static final SoundEvent STONE_DRAG = create("stone_drag");
    public static final SoundEvent REDSTONE_FIRE_PROJECTILE = create("redstone_fire_projectile");
    public static final SoundEvent FIRE_PROJECTILE_FLY = create("fire_projectile_fly");
    public static final SoundEvent TELEPORT_ORB_THROW = create("teleport_orb_throw");
    public static final SoundEvent SWIRLINGS = create("swirlings");
    public static final SoundEvent POTION_DRINK = create("potion_drink");
    public static final SoundEvent VOID_TOUCHED_ACTIVATE = create("void_touched_activate");
    public static final SoundEvent VOID_TOUCHED_DEACTIVATE = create("void_touched_deactivate");
    public static final SoundEvent VOID_TOUCHED_LOOP = create("void_touched_loop");
    public static final SoundEvent SOUL_EAT = create("soul_eat");
    public static final SoundEvent PLUSHIE_SQUEEZE = create("plushie_squeeze");
    public static final SoundEvent JOKE = create("joke");

    public static final SoundEvent NETHER_SPREAD = create("nether_spread");

    public static final SoundEvent SCYTHE_SWING = create("scythe_swing");
    public static final SoundEvent SCYTHE_HIT = create("scythe_hit");
    public static final SoundEvent SCYTHE_HIT_MEATY = create("scythe_hit_meaty");

    public static final SoundEvent HAMMER_SWING = create("hammer_swing");
    public static final SoundEvent HAMMER_IMPACT = create("hammer_impact");

    public static final SoundEvent OBSIDIAN_CLAYMORE_SWING = create("obsidian_claymore_swing");
    public static final SoundEvent OBSIDIAN_CLAYMORE_SMASH = create("obsidian_claymore_smash");
    public static final SoundEvent OBSIDIAN_CLAYMORE_WINDUP = create("obsidian_claymore_windup");

    public static final SoundEvent SWORD_SHING = create("sword_shing");

    public static final SoundEvent SOUL_KNIFE_NO_SOUL_SWING = create("soul_knife_no_soul_swing");

    public static final SoundEvent BONEHEAD_HAMMER_IMPACT = create("bonehead_hammer_impact");
    public static final SoundEvent BONEHEAD_HAMMER_HIT = create("bonehead_hammer_hit");

    public static final SoundEvent WHIP_SWING = create("whip_swing");
    public static final SoundEvent WHIP_HIT = create("whip_hit");

    public static final SoundEvent HARPOON_HIT = create("harpoon_impact");
    public static final SoundEvent HARPOON_HIT_WATER = create("harpoon_impact_water");

    public static final SoundEvent FLAME_CAPTURE_CATCH = create("flame_capture_catch");
    public static final SoundEvent FLAME_CAPTURE_RELEASE = create("flame_capture_release");

    public static final SoundEvent REDSTONE_EXPLODE = create("redstone_explode");

    public static final SoundEvent BLAST_FUNGUS_THROW = create("blast_fungus_throw");
    public static final SoundEvent BLAST_FUNGUS_EXPLODE = create("blast_fungus_explode");

    public static final SoundEvent FUNGUS_EXPLOSION = create("fungus_explosion");

    public static final SoundEvent BROOM_SWING = create("broom_swing");
    public static final SoundEvent BROOM_BREAK = create("broom_break");
    public static final SoundEvent BROOM_IMPACT = create("broom_impact");

    public static final SoundEvent VOID_BLAST = create("void_blast");

    public static final SoundEvent FOCUS_PICK = create("focus_pick");

    public static final SoundEvent CORRUPT_BEAM_START = create("corrupt_beam_start");
    public static final SoundEvent CORRUPT_BEAM_LOOP = create("corrupt_beam_loop");
    public static final SoundEvent CORRUPT_BEAM_SOUL = create("corrupt_beam_soul");

    public static final SoundEvent ICE_STORM_LOOP = create("ice_storm_loop");

    public static final SoundEvent BREW_GAS = create("brew_gas");
    public static final SoundEvent BREW_GAS_ALT = create("brew_gas_alt");

    public static final SoundEvent ALTAR_START = create("altar_start");
    public static final SoundEvent ALTAR_LOOP = create("altar_loop");
    public static final SoundEvent ALTAR_FINISH = create("altar_finish");

    public static final SoundEvent CAULDRON_BUBBLES = create("cauldron_bubbles");
    public static final SoundEvent CAULDRON_CHIMES = create("cauldron_chimes");

    public static final SoundEvent RESONANCE_CRYSTAL_ON = create("resonance_crystal_on");
    public static final SoundEvent RESONANCE_CRYSTAL_OFF = create("resonance_crystal_off");
    public static final SoundEvent RESONANCE_CRYSTAL_LOOP = create("resonance_crystal_loop");

    public static final SoundEvent SPIDER_NEST_START = create("spider_nest_start");
    public static final SoundEvent SPIDER_NEST_TRAIN = create("spider_nest_train");
    public static final SoundEvent SPIDER_NEST_SPAWN = create("spider_nest_spawn");

    public static final SoundEvent GRAVESTONE_START = create("gravestone_start");

    public static final SoundEvent BLAZING_CAGE_START = create("blazing_cage_start");
    public static final SoundEvent BLAZING_CAGE_TRAIN = create("blazing_cage_train");

    public static final SoundEvent URN_BREAK = create("urn_break");

    public static final SoundEvent VOID_FLAME_LOOP = create("void_flame_loop");
    public static final SoundEvent VOID_FLAME = create("void_flame");
    public static final SoundEvent VOID_BARREL_ACTIVATE = create("void_barrel_activate");

    public static final SoundEvent VOID_SPAWNER_AMBIENT = create("void_spawner_ambient");
    public static final SoundEvent VOID_SPAWNER_DETECT_PLAYER = create("void_spawner_detect_player");
    public static final SoundEvent VOID_SPAWNER_OPEN_SHUTTER = create("void_spawner_open_shutter");
    public static final SoundEvent VOID_SPAWNER_CLOSE_SHUTTER = create("void_spawner_close_shutter");
    public static final SoundEvent VOID_SPAWNER_EJECT_ITEM = create("void_spawner_eject_item");

    public static final SoundEvent VOID_VAULT_AMBIENT = create("void_vault_ambient");
    public static final SoundEvent VOID_VAULT_ACTIVATE = create("void_vault_activate");
    public static final SoundEvent VOID_VAULT_DEACTIVATE = create("void_vault_deactivate");
    public static final SoundEvent VOID_VAULT_OPEN_SHUTTER = create("void_vault_open_shutter");
    public static final SoundEvent VOID_VAULT_CLOSE_SHUTTER = create("void_vault_close_shutter");
    public static final SoundEvent VOID_VAULT_EJECT_ITEM = create("void_vault_eject_item");
    public static final SoundEvent VOID_VAULT_INSERT = create("void_vault_insert");
    public static final SoundEvent VOID_VAULT_INSERT_FAIL = create("void_vault_insert_fail");
    public static final SoundEvent VOID_VAULT_REJECT = create("void_vault_reject");

    public static final SoundEvent VOID_FRAME_UNLOCK = create("void_frame_unlock");

    public static final SoundEvent CREEPER_PILLAR_ON = create("creeper_pillar_on");
    public static final SoundEvent CREEPER_PILLAR_OFF = create("creeper_pillar_off");
    public static final SoundEvent CREEPER_PILLAR_LOOP = create("creeper_pillar_loop");

    public static final SoundEvent JUNGLE_PILLAR_BURST = create("jungle_pillar_burst");

    public static final SoundEvent FIRE_TORNADO_AMBIENT = create("fire_tornado_ambient");

    public static final SoundEvent APOSTLE_SHADE = create("apostle_shade");

    public static final SoundEvent APOSTLE_THEME = create("apostle_theme");
    public static final SoundEvent APOSTLE_THEME_POST = create("apostle_theme_post");
    public static final SoundEvent VIZIER_THEME = create("vizier_theme");
    public static final SoundEvent RG_THEME = create("rg_theme");
    public static final SoundEvent RM_THEME = create("rm_theme");
    public static final SoundEvent ENDER_KEEPER_THEME = create("ender_keeper_theme");
    public static final SoundEvent ENDER_KEEPER_THEME_POST = create("ender_keeper_theme_post");
    public static final SoundEvent ENDERMAN_THEME_PRE = create("enderman_theme_pre");
    public static final SoundEvent ENDERMAN_THEME = create("enderman_theme");
    public static final SoundEvent ENDERSENT_THEME = create("endersent_theme");

    public static final SoundEvent MUSIC_DISC_APOSTLE = create("apostle_theme_disc");
    public static final SoundEvent MUSIC_DISC_KEEPER = create("ender_keeper_theme_disc");
    public static final SoundEvent MUSIC_DISC_VIZIER = create("vizier_theme_disc");
    public static final SoundEvent MUSIC_DISC_RM = create("rm_theme_disc");
    public static final SoundEvent MUSIC_DISC_ENDERMAN = create("enderman_theme_disc");

    public static final SoundEvent BOSS_POST = create("boss_post");
    public static final SoundEvent BOSS_POST_2 = create("boss_post_second");
    public static final SoundEvent ARENA_END = create("arena_end");

    static SoundEvent create(String name) {
        SoundEvent event = SoundEvent.createVariableRangeEvent(Goety.location(name));
        return register(name, () -> event);
    }


    public static void init() {

    }

    private static SoundEvent register(String name, Supplier<SoundEvent> supplier) {
        return Registry.register(BuiltInRegistries.SOUND_EVENT, Goety.location(name), supplier.get());
    }
}
