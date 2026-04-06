package com.Polarice3.Goety.data;

import com.Polarice3.Goety.common.entities.ModEntityType;
import com.Polarice3.Goety.init.ModTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.entity.EntityType;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ModEntityTypeTagsProvider extends FabricTagProvider.EntityTypeTagProvider {


    public ModEntityTypeTagsProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> completableFuture) {
        super(output, completableFuture);
    }

    @Override
    protected void addTags(HolderLookup.Provider p_255894_) {
        this.getOrCreateTagBuilder(EntityTypeTags.FREEZE_IMMUNE_ENTITY_TYPES).add(
                ModEntityType.WRAITH,
                ModEntityType.WRAITH_SERVANT,
                ModEntityType.BORDER_WRAITH,
                ModEntityType.BORDER_WRAITH_SERVANT,
                ModEntityType.MUCK_WRAITH,
                ModEntityType.MUCK_WRAITH_SERVANT,
                ModEntityType.REAPER,
                ModEntityType.REAPER_SERVANT,
                ModEntityType.FROZEN_ZOMBIE_SERVANT,
                ModEntityType.STRAY_SERVANT,
                ModEntityType.ICY_SPIDER_SERVANT,
                ModEntityType.ICEOLOGER_SERVANT,
                ModEntityType.CRYOLOGER_SERVANT,
                ModEntityType.POLAR_BEAR_SERVANT,
                ModEntityType.BOUND_ICEOLOGER,
                ModEntityType.ICY_SPIDER,
                ModEntityType.WINTER_WOLF,
                ModEntityType.CAIRN_NECROMANCER,
                ModEntityType.CAIRN_NECROMANCER_SERVANT,
                ModEntityType.WIGHT,
                ModEntityType.BONE_LORD,
                ModEntityType.SKULL_LORD,
                ModEntityType.HAUNTED_ARMOR,
                ModEntityType.HAUNTED_ARMOR_SERVANT,
                ModEntityType.ICE_GOLEM,
                ModEntityType.CRYOLOGER,
                ModEntityType.GLACIAL_WALL);
        this.getOrCreateTagBuilder(EntityTypeTags.FREEZE_HURTS_EXTRA_TYPES).add(
                ModEntityType.BLAZE_SERVANT,
                ModEntityType.WILDFIRE,
                ModEntityType.INFERNO,
                ModEntityType.MAGMA_CUBE_SERVANT);
        this.getOrCreateTagBuilder(EntityTypeTags.POWDER_SNOW_WALKABLE_MOBS).add(
                ModEntityType.WRAITH,
                ModEntityType.WRAITH_SERVANT,
                ModEntityType.BORDER_WRAITH,
                ModEntityType.BORDER_WRAITH_SERVANT,
                ModEntityType.MUCK_WRAITH,
                ModEntityType.MUCK_WRAITH_SERVANT,
                ModEntityType.REAPER,
                ModEntityType.REAPER_SERVANT,
                ModEntityType.ICY_SPIDER_SERVANT,
                ModEntityType.MOUNTAINEER_SERVANT,
                ModEntityType.ICEOLOGER_SERVANT,
                ModEntityType.CRYOLOGER_SERVANT,
                ModEntityType.BOUND_ICEOLOGER,
                ModEntityType.ICY_SPIDER,
                ModEntityType.WINTER_WOLF,
                ModEntityType.ICE_GOLEM,
                ModEntityType.CRUSHER,
                ModEntityType.STORM_CASTER,
                ModEntityType.CRYOLOGER,
                ModEntityType.GLACIAL_WALL);
        this.getOrCreateTagBuilder(EntityTypeTags.FALL_DAMAGE_IMMUNE).add(
                ModEntityType.WRAITH,
                ModEntityType.WRAITH_SERVANT,
                ModEntityType.BORDER_WRAITH,
                ModEntityType.BORDER_WRAITH_SERVANT,
                ModEntityType.MUCK_WRAITH,
                ModEntityType.MUCK_WRAITH_SERVANT,
                ModEntityType.REAPER,
                ModEntityType.REAPER_SERVANT,
                ModEntityType.INFERNO,
                ModEntityType.MAGMA_CUBE_SERVANT,
                ModEntityType.MINI_GHAST,
                ModEntityType.GHAST_SERVANT,
                ModEntityType.MALGHAST,
                ModEntityType.WILDFIRE,
                ModEntityType.ENDERSENT,
                ModEntityType.CRYPT_SLIME,
                ModEntityType.CRYPT_SLIME_SERVANT,
                ModEntityType.PHANTOM_SERVANT,
                ModEntityType.WIND_CALLER_SERVANT,
                ModEntityType.BOUND_EVOKER,
                ModEntityType.BOUND_GEOMANCER,
                ModEntityType.BOUND_ICEOLOGER,
                ModEntityType.BOUND_CRYOLOGER,
                ModEntityType.BOUND_WIND_CALLER,
                ModEntityType.BOUND_STORM_CASTER,
                ModEntityType.BLACK_BEAST,
                ModEntityType.LEAPLEAF,
                ModEntityType.WIGHT,
                ModEntityType.CARRION_MAGGOT,
                ModEntityType.CARRION_FLY,
                ModEntityType.BROOD_MOTHER,
                ModEntityType.BROOD_MOTHER_SERVANT,
                ModEntityType.STORM_CASTER,
                ModEntityType.STONE_MINISTROSITY,
                ModEntityType.REDSTONE_MINISTROSITY,
                ModEntityType.ICE_GOLEM,
                ModEntityType.SQUALL_GOLEM,
                ModEntityType.GRAVE_GOLEM,
                ModEntityType.HAUNT,
                ModEntityType.REDSTONE_GOLEM,
                ModEntityType.HOSTILE_REDSTONE_GOLEM,
                ModEntityType.REDSTONE_MONSTROSITY,
                ModEntityType.HOSTILE_REDSTONE_MONSTROSITY,
                ModEntityType.SKULL_LORD,
                ModEntityType.BONE_LORD,
                ModEntityType.APOSTLE,
                ModEntityType.ENDER_KEEPER,
                ModEntityType.VIZIER);
        this.getOrCreateTagBuilder(EntityTypeTags.RAIDERS).add(
                ModEntityType.ARMORED_RAVAGER,
                ModEntityType.WARLOCK,
                ModEntityType.MAVERICK,
                ModEntityType.HERETIC,
                ModEntityType.CRONE,
                ModEntityType.SORCERER,
                ModEntityType.ENVIOKER,
                ModEntityType.INQUILLAGER,
                ModEntityType.CONQUILLAGER,
                ModEntityType.PIKER,
                ModEntityType.RIPPER,
                ModEntityType.TRAMPLER,
                ModEntityType.CRUSHER,
                ModEntityType.STORM_CASTER,
                ModEntityType.CRYOLOGER,
                ModEntityType.PREACHER,
                ModEntityType.MINISTER,
                ModEntityType.HOSTILE_REDSTONE_GOLEM,
                ModEntityType.HOSTILE_REDSTONE_MONSTROSITY,
                ModEntityType.APOSTLE,
                ModEntityType.VIZIER);
        this.getOrCreateTagBuilder(EntityTypeTags.ARROWS).add(
                ModEntityType.GHOST_ARROW,
                ModEntityType.RAIN_ARROW,
                ModEntityType.DEATH_ARROW);
        this.getOrCreateTagBuilder(EntityTypeTags.IMPACT_PROJECTILES).add(
                ModEntityType.SOUL_BOLT,
                ModEntityType.POISON_BOLT,
                ModEntityType.STEAM_MISSILE,
                ModEntityType.WITHER_BOLT,
                ModEntityType.HELL_BOLT,
                ModEntityType.HELL_BLAST,
                ModEntityType.NECRO_BOLT,
                ModEntityType.MAGIC_BOLT,
                ModEntityType.SHIELD_DEBRIS,
                ModEntityType.ILL_BOMB,
                ModEntityType.SCATTER_BOMB,
                ModEntityType.SCYTHE,
                ModEntityType.SWORD,
                ModEntityType.ICE_SPIKE,
                ModEntityType.MOD_FIREBALL,
                ModEntityType.LAVABALL,
                ModEntityType.HAUNTED_SKULL_SHOT,
                ModEntityType.MOD_WITHER_SKULL);
        this.getOrCreateTagBuilder(EntityTypeTags.FROG_FOOD).add(
                ModEntityType.SLIME_SERVANT,
                ModEntityType.MAGMA_CUBE_SERVANT,
                ModEntityType.CRYPT_SLIME_SERVANT,
                ModEntityType.TROPICAL_SLIME_SERVANT,
                ModEntityType.CRYPT_SLIME);
        this.getOrCreateTagBuilder(ModTags.EntityTypes.WANTING_ENTITIES).add(
                EntityType.WITHER_SKULL,
                EntityType.FIREBALL,
                EntityType.SMALL_FIREBALL
        );

        this.getOrCreateTagBuilder(ModTags.EntityTypes.CREEPERS).add(EntityType.CREEPER);

        List<String> creepers = Arrays.asList(
                "jungle_creeper",
                "bamboo_creeper",
                "desert_creeper",
                "badlands_creeper",
                "hills_creeper",
                "savannah_creeper",
                "mushroom_creeper",
                "swamp_creeper",
                "dripstone_creeper",
                "cave_creeper",
                "dark_oak_creeper",
                "spruce_creeper",
                "beach_creeper",
                "snowy_creeper"
        );
        for (String creeper : creepers) {
            this.getOrCreateTagBuilder(ModTags.EntityTypes.CREEPERS).addOptional(new ResourceLocation("creeperoverhaul", creeper));
        }

        this.getOrCreateTagBuilder(ModTags.EntityTypes.ENDERMEN).add(EntityType.ENDERMAN);

        List<String> endermen = Arrays.asList(
                "badlands_enderman",
                "cave_enderman",
                "crimson_forest_enderman",
                "dark_oak_enderman",
                "desert_enderman",
                "end_enderman",
                "end_islands_enderman",
                "flower_fields_enderman",
                "ice_spikes_enderman",
                "mushroom_fields_enderman",
                "nether_wastes_enderman",
                "coral_enderman",
                "savanna_enderman",
                "snowy_enderman",
                "soulsand_valley_enderman",
                "swamp_enderman",
                "warped_forest_enderman",
                "windswept_hills_enderman",
                "pet_enderman",
                "hammerhead_pet_enderman",
                "axolotl_pet_enderman"
        );
        for (String enderman : endermen) {
            this.getOrCreateTagBuilder(ModTags.EntityTypes.ENDERMEN).addOptional(new ResourceLocation("endermanoverhaul", enderman));
        }
    }
}
