package com.Polarice3.Goety.data;

import com.Polarice3.Goety.init.ModTags;
import com.Polarice3.Goety.utils.ModDamageSource;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.damagesource.DamageTypes;

import java.util.concurrent.CompletableFuture;

public class ModDamageTypeTagsProvider extends FabricTagProvider<DamageType> {


    public ModDamageTypeTagsProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, Registries.DAMAGE_TYPE, registriesFuture);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        this.getOrCreateTagBuilder(DamageTypeTags.BYPASSES_INVULNERABILITY)
                .add(ModDamageSource.DISMISSED);
        this.getOrCreateTagBuilder(DamageTypeTags.BYPASSES_ARMOR)
                .add(ModDamageSource.PHOBIA,
                        ModDamageSource.DOOM,
                        ModDamageSource.HELLFIRE,
                        ModDamageSource.INDIRECT_HELLFIRE,
                        ModDamageSource.ACID,
                        ModDamageSource.SPIKE,
                        ModDamageSource.MAGIC_BOLT,
                        ModDamageSource.CHOKE,
                        ModDamageSource.VOIDED,
                        ModDamageSource.DISMISSED,
                        ModDamageSource.DEATH);
        this.getOrCreateTagBuilder(DamageTypeTags.BYPASSES_SHIELD)
                .add(ModDamageSource.ICE_BOUQUET,
                        ModDamageSource.SPIKE,
                        ModDamageSource.HELLFIRE,
                        ModDamageSource.INDIRECT_HELLFIRE,
                        ModDamageSource.SOUL_LEECH,
                        ModDamageSource.LIFE_LEECH,
                        ModDamageSource.CHOKE,
                        ModDamageSource.VOIDED,
                        ModDamageSource.DISMISSED,
                        ModDamageSource.DEATH);
        this.getOrCreateTagBuilder(DamageTypeTags.BYPASSES_ENCHANTMENTS)
                .add(ModDamageSource.DOOM,
                        ModDamageSource.DISMISSED,
                        ModDamageSource.VOIDED);
        this.getOrCreateTagBuilder(DamageTypeTags.BYPASSES_RESISTANCE)
                .add(ModDamageSource.DOOM,
                        ModDamageSource.DISMISSED,
                        ModDamageSource.VOIDED);
        this.getOrCreateTagBuilder(DamageTypeTags.BYPASSES_EFFECTS)
                .add(ModDamageSource.DOOM,
                        ModDamageSource.DISMISSED,
                        ModDamageSource.VOIDED);
        this.getOrCreateTagBuilder(DamageTypeTags.IS_PROJECTILE)
                .add(ModDamageSource.ICE_SPIKE,
                        ModDamageSource.NO_OWNER_MAGIC_FIREBALL,
                        ModDamageSource.MAGIC_FIREBALL);
        this.getOrCreateTagBuilder(DamageTypeTags.IS_FIRE)
                .add(ModDamageSource.BOILING,
                        ModDamageSource.FIRE_BREATH);
        this.getOrCreateTagBuilder(DamageTypeTags.IS_FREEZING)
                .add(ModDamageSource.DIRECT_FREEZE,
                        ModDamageSource.INDIRECT_FREEZE,
                        ModDamageSource.FROST_BREATH,
                        ModDamageSource.ICE_SPIKE,
                        ModDamageSource.ICE_BOUQUET);
        this.getOrCreateTagBuilder(DamageTypeTags.IS_EXPLOSION)
                .add(ModDamageSource.LOOT_EXPLODE,
                        ModDamageSource.LOOT_EXPLODE_OWNED);
        this.getOrCreateTagBuilder(DamageTypeTags.WITCH_RESISTANT_TO)
                .add(ModDamageSource.PHOBIA,
                        ModDamageSource.ICE_BOUQUET,
                        ModDamageSource.ACID,
                        ModDamageSource.SPIKE,
                        ModDamageSource.MAGIC_BOLT,
                        ModDamageSource.WIND_BLAST,
                        ModDamageSource.SOUL_LEECH,
                        ModDamageSource.LIFE_LEECH);
        this.getOrCreateTagBuilder(ModTags.DamageTypes.NO_KNOCKBACK)
                .add(ModDamageSource.ICE_BOUQUET,
                        ModDamageSource.ACID,
                        ModDamageSource.SPIKE,
                        ModDamageSource.HELLFIRE,
                        ModDamageSource.INDIRECT_HELLFIRE,
                        ModDamageSource.FIRE_BREATH,
                        ModDamageSource.MAGIC_FIRE,
                        ModDamageSource.FROST_BREATH,
                        ModDamageSource.MAGIC_BOLT,
                        ModDamageSource.SOUL_LEECH,
                        ModDamageSource.LIFE_LEECH,
                        ModDamageSource.CHOKE,
                        ModDamageSource.SWARM,
                        ModDamageSource.VOIDED,
                        ModDamageSource.DISMISSED,
                        ModDamageSource.DEATH);
        this.getOrCreateTagBuilder(ModTags.DamageTypes.PHYSICAL)
                .add(DamageTypes.PLAYER_ATTACK,
                        DamageTypes.MOB_ATTACK,
                        DamageTypes.MOB_ATTACK_NO_AGGRO,
                        DamageTypes.STING,
                        ModDamageSource.SUMMON);
        this.getOrCreateTagBuilder(ModTags.DamageTypes.FIRE_ATTACKS)
                .addTag(ModTags.DamageTypes.MAGIC_FIRE)
                .addTag(ModTags.DamageTypes.HELLFIRE)
                .add(DamageTypes.FIREBALL,
                        DamageTypes.UNATTRIBUTED_FIREBALL,
                        ModDamageSource.FIRE_BREATH);
        this.getOrCreateTagBuilder(ModTags.DamageTypes.FROST_ATTACKS)
                .add(ModDamageSource.FROST_BREATH,
                        ModDamageSource.ICE_BOUQUET,
                        ModDamageSource.ICE_SPIKE,
                        ModDamageSource.DIRECT_FREEZE,
                        ModDamageSource.INDIRECT_FREEZE);
        this.getOrCreateTagBuilder(ModTags.DamageTypes.SHOCK_ATTACKS)
                .add(DamageTypes.LIGHTNING_BOLT,
                        ModDamageSource.SHOCK,
                        ModDamageSource.DIRECT_SHOCK,
                        ModDamageSource.INDIRECT_SHOCK,
                        ModDamageSource.LIGHTNING);
        this.getOrCreateTagBuilder(ModTags.DamageTypes.WATER_ATTACKS)
                .add(ModDamageSource.BUBBLE_STREAM,
                        ModDamageSource.DRENCH,
                        ModDamageSource.DIRECT_DRENCH,
                        ModDamageSource.INDIRECT_DRENCH);
        this.getOrCreateTagBuilder(ModTags.DamageTypes.MAGIC_FIRE)
                .add(ModDamageSource.MAGIC_FIRE,
                        ModDamageSource.MAGIC_FIREBALL,
                        ModDamageSource.NO_OWNER_MAGIC_FIREBALL);
        this.getOrCreateTagBuilder(ModTags.DamageTypes.HELLFIRE)
                .add(ModDamageSource.HELLFIRE,
                        ModDamageSource.INDIRECT_HELLFIRE);
        this.getOrCreateTagBuilder(ModTags.DamageTypes.WANTING_DAMAGE)
                .addTag(DamageTypeTags.WITCH_RESISTANT_TO)
                .addTag(ModTags.DamageTypes.NO_KNOCKBACK)
                .addTag(ModTags.DamageTypes.FIRE_ATTACKS)
                .addTag(ModTags.DamageTypes.FROST_ATTACKS)
                .addTag(ModTags.DamageTypes.SHOCK_ATTACKS)
                .addTag(ModTags.DamageTypes.WATER_ATTACKS)
                .add(DamageTypes.WITHER_SKULL)
                .add(ModDamageSource.LOOT_EXPLODE,
                        ModDamageSource.LOOT_EXPLODE_OWNED);
    }
}
