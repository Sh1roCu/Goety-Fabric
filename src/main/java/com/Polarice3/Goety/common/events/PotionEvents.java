package com.Polarice3.Goety.common.events;

import cn.sh1rocu.goety.api.event.*;
import cn.sh1rocu.goety.api.extension.IEntityPersistentData;
import cn.sh1rocu.goety.mixin.accessor.PlayerAccessor;
import com.Polarice3.Goety.api.items.magic.IWand;
import com.Polarice3.Goety.client.particles.FollowFireParticleOption;
import com.Polarice3.Goety.client.particles.ModParticleTypes;
import com.Polarice3.Goety.client.particles.RisingCircleParticleOption;
import com.Polarice3.Goety.client.particles.ShockwaveParticleOption;
import com.Polarice3.Goety.common.effects.GoetyEffects;
import com.Polarice3.Goety.common.effects.brew.BrewEffectInstance;
import com.Polarice3.Goety.common.entities.util.DragonBreathCloud;
import com.Polarice3.Goety.common.events.spell.CastMagicEvent;
import com.Polarice3.Goety.common.events.spell.CastingMagicEvent;
import com.Polarice3.Goety.common.items.ModItems;
import com.Polarice3.Goety.common.magic.spells.void_spells.EndWalkSpell;
import com.Polarice3.Goety.common.network.ModNetwork;
import com.Polarice3.Goety.common.network.server.SPlayEntitySoundPacket;
import com.Polarice3.Goety.common.network.server.SPlayWorldSoundPacket;
import com.Polarice3.Goety.config.ItemConfig;
import com.Polarice3.Goety.init.ModSounds;
import com.Polarice3.Goety.init.ModTags;
import com.Polarice3.Goety.utils.*;
import net.fabricmc.fabric.api.tag.convention.v1.ConventionalEntityTypeTags;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ambient.Bat;
import net.minecraft.world.entity.animal.Bee;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.monster.Endermite;
import net.minecraft.world.entity.monster.PatrollingMonster;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.monster.ZombieVillager;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.*;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.trading.Merchant;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

import static cn.sh1rocu.goety.api.event.LivingChangeTargetEvent.LivingTargetType.MOB_TARGET;

public class PotionEvents {

    public static AttributeModifier SOUL_ARMOR_MOD = new AttributeModifier(UUID.fromString("3e4b414b-466c-4b90-8a92-a878e2542bb8"), "Increase Armor", 2.0D, AttributeModifier.Operation.MULTIPLY_TOTAL);

    public static ColorUtil VOID_TOUCHED = new ColorUtil(0x7f0075);

    public static void livingEffects(LivingTickEvent event) {
        LivingEntity livingEntity = event.getEntity();
        if (livingEntity != null) {
            if (livingEntity.level instanceof ServerLevel serverLevel) {
                if (livingEntity.hasEffect(GoetyEffects.ILLAGUE)) {
                    EffectsUtil.Illague(serverLevel, livingEntity);
                }
                if (livingEntity.hasEffect(GoetyEffects.VOID_TOUCHED)) {
                    if (livingEntity.tickCount % 10 == 0) {
                        ColorUtil colorUtil = VOID_TOUCHED;
                        serverLevel.sendParticles(new FollowFireParticleOption(livingEntity.getId()), livingEntity.getX(), livingEntity.getY() + (livingEntity.getBbHeight() / 2.0F), livingEntity.getZ(), 0, colorUtil.red(), colorUtil.green(), colorUtil.blue(), 1.0F);
                    }
                }
            }
            AttributeInstance armor = livingEntity.getAttribute(Attributes.ARMOR);
            if (armor != null) {
                if (livingEntity.hasEffect(GoetyEffects.SOUL_ARMOR)) {
                    if (ItemHelper.noArmor(livingEntity)) {
                        if (!armor.hasModifier(SOUL_ARMOR_MOD)) {
                            armor.addPermanentModifier(SOUL_ARMOR_MOD);
                        }
                    } else {
                        if (armor.hasModifier(SOUL_ARMOR_MOD)) {
                            armor.removeModifier(SOUL_ARMOR_MOD);
                        }
                    }
                } else {
                    if (armor.hasModifier(SOUL_ARMOR_MOD)) {
                        armor.removeModifier(SOUL_ARMOR_MOD);
                    }
                }
            }
            if (livingEntity.getTags().contains(ConstantPaths.gassed())) {
                if (livingEntity.tickCount % 20 == 0) {
                    livingEntity.getTags().remove(ConstantPaths.gassed());
                }
            }
            if (livingEntity.hasEffect(GoetyEffects.BURN_HEX)) {
                if (livingEntity.hasEffect(MobEffects.FIRE_RESISTANCE)) {
                    livingEntity.removeEffectNoUpdate(MobEffects.FIRE_RESISTANCE);
                }
            }
            if (livingEntity.hasEffect(GoetyEffects.CLIMBING)) {
                MobUtil.ClimbAnyWall(livingEntity);
            }
            if (livingEntity instanceof Bee bee) {
                if (!bee.level.isClientSide) {
                    if (bee.getTags().contains(ConstantPaths.conjuredBee())) {
                        if ((bee.getTarget() == null && bee.getPersistentAngerTarget() == null) || bee.hasStung()) {
                            if (bee.tickCount % MathHelper.secondsToTicks(10) == 0) {
                                bee.spawnAnim();
                                bee.discard();
                            }
                        }
                    }
                }
            }
            if (livingEntity instanceof Bat bat) {
                if (!bat.level.isClientSide) {
                    if (bat.getTags().contains(ConstantPaths.conjuredBat())) {
                        if (bat.tickCount % MathHelper.secondsToTicks(20) == 0) {
                            bat.spawnAnim();
                            bat.discard();
                        }
                    }
                }
            }
            if (livingEntity.hasEffect(GoetyEffects.FREEZING)){
                if (!livingEntity.level.isClientSide){
                    MobEffectInstance instance = livingEntity.getEffect(GoetyEffects.FREEZING);
                    if (instance != null) {
                        livingEntity.setIsInPowderSnow(true);
                        if (livingEntity.canFreeze()) {
                            int h = instance.getAmplifier() + 1;
                            MiscCapHelper.setFreezing(livingEntity, h);
                            if (livingEntity.level instanceof ServerLevel serverLevel) {
                                float chance = 0.005F * (h * h);
                                if (serverLevel.getRandom().nextFloat() <= chance) {
                                    ServerParticleUtil.addParticlesAroundMiddleSelf(serverLevel, ParticleTypes.SNOWFLAKE, livingEntity);
                                }
                            }
                            int i = livingEntity.getTicksFrozen();
                            int j = h * 4;
                            livingEntity.setTicksFrozen(Math.min(livingEntity.getTicksRequiredToFreeze() + 5, i + j));
                        }
                    }
                }
            } else {
                if (!livingEntity.level.isClientSide) {
                    if (MiscCapHelper.isFreezing(livingEntity)) {
                        MiscCapHelper.setFreezing(livingEntity, 0);
                    }
                }
            }
            if (livingEntity instanceof Player && livingEntity.hasEffect(GoetyEffects.SENSE_LOSS)) {
                MobEffectInstance mobEffectInstance = livingEntity.getEffect(GoetyEffects.SENSE_LOSS);
                if (mobEffectInstance != null) {
                    if (livingEntity.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, mobEffectInstance.getDuration(), mobEffectInstance.getAmplifier(), mobEffectInstance.isAmbient(), mobEffectInstance.isVisible()))) {
                        livingEntity.removeEffect(GoetyEffects.SENSE_LOSS);
                    }
                }
            }
            if (livingEntity.hasEffect(GoetyEffects.EXPLOSIVE)) {
                MobEffectInstance mobEffectInstance = livingEntity.getEffect(GoetyEffects.EXPLOSIVE);
                if (mobEffectInstance != null) {
                    int a = mobEffectInstance.getAmplifier() + 1;
                    if (MobUtil.isPushed(livingEntity)) {
                        int max = Math.max(1, 100 - (a * 10));
                        if (livingEntity.getRandom().nextInt(max) == 0) {
                            if (!livingEntity.level.isClientSide) {
                                livingEntity.level.explode(livingEntity, livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(), 3.0F + (a / 2.0F), Level.ExplosionInteraction.BLOCK);
                                livingEntity.removeEffect(GoetyEffects.EXPLOSIVE);
                            }
                        }
                    }
                }
            }
            if (livingEntity.hasEffect(GoetyEffects.SNOW_SKIN)) {
                MobEffectInstance mobEffectInstance = livingEntity.getEffect(GoetyEffects.SNOW_SKIN);
                if (mobEffectInstance != null) {
                    if (!livingEntity.level.isClientSide) {
                        int i = Mth.floor(livingEntity.getX());
                        int j = Mth.floor(livingEntity.getY());
                        int k = Mth.floor(livingEntity.getZ());
                        BlockPos blockpos = new BlockPos(i, j, k);
                        Holder<Biome> biome = livingEntity.level.getBiome(blockpos);
                        if (biome.is(BiomeTags.SNOW_GOLEM_MELTS)) {
                            livingEntity.hurt(livingEntity.damageSources().onFire(), 1.0F);
                        }

                        livingEntity.setIsInPowderSnow(false);
                        livingEntity.setTicksFrozen(0);

                        BlockState blockstate = Blocks.SNOW.defaultBlockState();

                        for (int l = 0; l < 4; ++l) {
                            i = Mth.floor(livingEntity.getX() + (double) ((float) (l % 2 * 2 - 1) * 0.25F));
                            j = Mth.floor(livingEntity.getY());
                            k = Mth.floor(livingEntity.getZ() + (double) ((float) (l / 2 % 2 * 2 - 1) * 0.25F));
                            BlockPos blockpos1 = new BlockPos(i, j, k);
                            if (livingEntity.level.isEmptyBlock(blockpos1) && blockstate.canSurvive(livingEntity.level, blockpos1)) {
                                livingEntity.level.setBlockAndUpdate(blockpos1, blockstate);
                                livingEntity.level.gameEvent(GameEvent.BLOCK_PLACE, blockpos1, GameEvent.Context.of(livingEntity, blockstate));
                            }
                        }
                    }
                }
            }
        }
    }

    public static void hurtEvent(LivingHurtEvent event) {
        LivingEntity victim = event.getEntity();
        Entity attacker = event.getSource().getEntity();

        if (attacker instanceof LivingEntity living) {
            if (ModDamageSource.physicalAttacks(event.getSource())) {
                if (living.hasEffect(GoetyEffects.FLAME_HANDS)) {
                    MobEffectInstance mobEffectInstance = living.getEffect(GoetyEffects.FLAME_HANDS);
                    if (mobEffectInstance != null) {
                        int a = mobEffectInstance.getAmplifier() + 1;
                        victim.setSecondsOnFire(a * 4);
                    }
                }
                if (living.hasEffect(GoetyEffects.VENOMOUS_HANDS)) {
                    MobEffect effect = MobEffects.POISON;
                    if (CuriosFinder.hasWildRobe(living)) {
                        effect = GoetyEffects.ACID_VENOM;
                    }
                    MobEffectInstance mobEffectInstance = living.getEffect(GoetyEffects.VENOMOUS_HANDS);
                    if (mobEffectInstance != null) {
                        int a = mobEffectInstance.getAmplifier();
                        victim.addEffect(new MobEffectInstance(effect, 200, a), living);
                    }
                }
            }
            if (victim.hasEffect(GoetyEffects.REPULSIVE)) {
                MobEffectInstance mobEffectInstance = victim.getEffect(GoetyEffects.REPULSIVE);
                if (mobEffectInstance != null) {
                    int a = mobEffectInstance.getAmplifier();
                    living.playSound(SoundEvents.IRON_GOLEM_ATTACK);
                    if (!living.level.isClientSide) {
                        ModNetwork.sendToALL(living.level().getServer(), SPlayWorldSoundPacket.ID, SPlayWorldSoundPacket.encode(living.blockPosition(), SoundEvents.IRON_GOLEM_ATTACK, 1.0F, 1.0F));
                        MobUtil.knockBack(living, victim, 1.0D + (a / 2.0D), 0.4D + (a * 0.2D), 1.0D + (a / 2.0D));
                    }
                }
            }
        }
        if (victim.hasEffect(GoetyEffects.SOUL_ARMOR)) {
            MobEffectInstance mobEffectInstance = victim.getEffect(GoetyEffects.SOUL_ARMOR);
            if (mobEffectInstance != null) {
                if (mobEffectInstance.getDuration() > MathHelper.secondsToTicks(event.getAmount())) {
                    EffectsUtil.decreaseDuration(victim, GoetyEffects.SOUL_ARMOR, MathHelper.secondsToTicks(event.getAmount()), mobEffectInstance.isAmbient(), mobEffectInstance.isVisible());
                }
                if (victim instanceof Player player) {
                    SEHelper.decreaseSouls(player, (int) event.getAmount());
                }
            }
        }
        if (victim.hasEffect(GoetyEffects.EXPLOSIVE)) {
            MobEffectInstance mobEffectInstance = victim.getEffect(GoetyEffects.EXPLOSIVE);
            if (mobEffectInstance != null) {
                int a = mobEffectInstance.getAmplifier() + 1;
                int max = Math.max(1, 5 - a);
                if (victim.getRandom().nextInt(max) == 0) {
                    if (!victim.level.isClientSide) {
                        victim.level.explode(victim, victim.getX(), victim.getY(), victim.getZ(), 3.0F + (a / 2.0F), Level.ExplosionInteraction.BLOCK);
                        victim.removeEffect(GoetyEffects.EXPLOSIVE);
                    }
                }
            }
        }
        if (victim.hasEffect(GoetyEffects.FLAMMABLE)) {
            MobEffectInstance mobEffectInstance = victim.getEffect(GoetyEffects.FLAMMABLE);
            if (mobEffectInstance != null) {
                int a = mobEffectInstance.getAmplifier() + 2;
                if (event.getSource().is(DamageTypeTags.IS_FIRE)) {
                    event.setAmount(event.getAmount() * a);
                }
            }
        }
        if (victim.hasEffect(GoetyEffects.ENDER_FLUX)) {
            MobEffectInstance mobEffectInstance = victim.getEffect(GoetyEffects.ENDER_FLUX);
            if (mobEffectInstance != null) {
                int a = mobEffectInstance.getAmplifier();
                for (int i = 0; i < 64; ++i) {
                    if (MobUtil.teleport(victim, 16, a)) {
                        if (victim.getRandom().nextFloat() < 0.05F && victim.level.getGameRules().getBoolean(GameRules.RULE_DOMOBSPAWNING)) {
                            Endermite endermite = EntityType.ENDERMITE.create(victim.level);
                            if (endermite != null) {
                                endermite.moveTo(victim.getX(), victim.getY(), victim.getZ(), victim.getYRot(), victim.getXRot());
                                victim.level.addFreshEntity(endermite);
                            }
                        }
                        break;
                    }
                }
            }
        }
        if (attacker instanceof LivingEntity attackerL && ModDamageSource.physicalAttacks(event.getSource())) {
            float bonus = 0.0F;

            if (attackerL.hasEffect(GoetyEffects.SMITING)) {
                MobEffectInstance inst = attackerL.getEffect(GoetyEffects.SMITING);
                if (inst != null) {
                    int amp = inst.getAmplifier() + 1;
                    ItemStack weapon = attackerL.getMainHandItem();
                    if (EnchantmentHelper.getItemEnchantmentLevel(Enchantments.SMITE, weapon) < amp) {
                        bonus += Enchantments.SMITE.getDamageBonus(amp, victim.getMobType());
                    }
                }
            }

            if (attackerL.hasEffect(GoetyEffects.INSECT_BANE)) {
                MobEffectInstance inst = attackerL.getEffect(GoetyEffects.INSECT_BANE);
                if (inst != null) {
                    int amp = inst.getAmplifier() + 1;
                    ItemStack weapon = attackerL.getMainHandItem();
                    if (EnchantmentHelper.getItemEnchantmentLevel(Enchantments.BANE_OF_ARTHROPODS, weapon) < amp) {
                        bonus += Enchantments.BANE_OF_ARTHROPODS.getDamageBonus(amp, victim.getMobType());
                    }
                }
            }

            if (bonus > 0.0F) {
                event.setAmount(event.getAmount() + bonus);
            }
        }
    }

    public static void damageEvents(LivingDamageEvent event) {
        LivingEntity target = event.getEntity();
        Entity attacker = event.getSource().getEntity();
        float finalDamage = event.getAmount();

        if (!target.level.isClientSide) {
            if (target.hasEffect(GoetyEffects.SAPPED)) {
                MobEffectInstance effectInstance = target.getEffect(GoetyEffects.SAPPED);
                if (effectInstance != null) {
                    int i = effectInstance.getAmplifier() + 1;
                    finalDamage += finalDamage * (0.2F * i);
                }
            }

            if (target.hasEffect(GoetyEffects.VOID_TOUCHED)) {
                if (!event.getSource().is(ModDamageSource.VOIDED)) {
                    MobEffectInstance effectInstance = target.getEffect(GoetyEffects.VOID_TOUCHED);
                    if (effectInstance != null) {
                        int i = effectInstance.getAmplifier() + 2;
                        finalDamage *= i;
                        target.removeEffect(GoetyEffects.VOID_TOUCHED);
                    }
                }
            }

            if (target.hasEffect(GoetyEffects.SHIELDING) || target.hasEffect(GoetyEffects.SHIELDED)) {
                if (!event.getSource().is(DamageTypeTags.BYPASSES_EFFECTS) && !event.getSource().is(DamageTypeTags.BYPASSES_RESISTANCE)) {
                    MobEffectInstance effectInstance = target.getEffect(GoetyEffects.SHIELDING);
                    if (effectInstance == null && target.hasEffect(GoetyEffects.SHIELDED)) {
                        effectInstance = target.getEffect(GoetyEffects.SHIELDED);
                    }
                    if (effectInstance != null) {
                        int i = effectInstance.getAmplifier() + 1;
                        finalDamage -= finalDamage * (0.05F * i);
                    }
                }
            }

            if (attacker instanceof LivingEntity attackerL) {
                if (attackerL.hasEffect(GoetyEffects.SHADOW_WALK)) {
                    float multiply = 1.25F + (EffectsUtil.getAmplifier(attackerL, GoetyEffects.SHADOW_WALK) / 4.0F);
                    finalDamage *= multiply;
                    attackerL.removeEffect(GoetyEffects.SHADOW_WALK);
                }
                if (target.hasEffect(GoetyEffects.CHILL_HIDE) && ModDamageSource.physicalAttacks(event.getSource())) {
                    MobEffectInstance effectInstance = target.getEffect(GoetyEffects.CHILL_HIDE);
                    if (effectInstance != null) {
                        int i = effectInstance.getAmplifier() * 2;
                        attackerL.addEffect(new MobEffectInstance(GoetyEffects.FREEZING, MathHelper.secondsToTicks(3 + i), 1));
                    }
                }
                if (attackerL.hasEffect(GoetyEffects.RADIANCE)) {
                    MobEffectInstance effectInstance = attackerL.getEffect(GoetyEffects.RADIANCE);
                    if (effectInstance != null) {
                        int amp = effectInstance.getAmplifier() + 1;
                        float heal = 6.0F * ((amp / 2.0F) + 1);
                        if (attackerL.level instanceof ServerLevel serverLevel) {
                            float chance = event.getSource().is(DamageTypeTags.IS_PROJECTILE) ? 0.5F : 0.2F;
                            if (attackerL.level.getRandom().nextFloat() <= chance) {
                                attackerL.level.playSound(null, target, ModSounds.RADIANCE_WAVE, attacker.getSoundSource(), 0.9F, 1.0F);
                                serverLevel.sendParticles(new ShockwaveParticleOption(4, 1), target.getX(), target.getY() + 0.25F, target.getZ(), 0, 0, 0, 0, 0.5F);
                                for (LivingEntity living2 : attackerL.level.getEntitiesOfClass(LivingEntity.class, target.getBoundingBox().inflate(4.0D), ally -> MobUtil.areAllies(attackerL, ally) || ally == attackerL)) {
                                    living2.heal(heal);
                                    for (int i = 0; i < serverLevel.getRandom().nextInt(10) + 10; ++i) {
                                        serverLevel.sendParticles(ModParticleTypes.HEAL_EFFECT_2, living2.getRandomX(1.5D), living2.getRandomY(), living2.getRandomZ(1.5D), 0, 0.0F, 1.0F, 0.0F, 1.0F);
                                    }
                                    ColorUtil colorUtil = new ColorUtil(0xfffcc5);
                                    serverLevel.sendParticles(new RisingCircleParticleOption(0), living2.getX(), living2.getY(), living2.getZ(), 0, colorUtil.red(), colorUtil.green(), colorUtil.blue(), 1.0F);
                                    living2.level.playSound(null, living2, ModSounds.HEAL_SPELL, living2.getSoundSource(), 1.0F, 1.0F);
                                }
                            }
                        }
                    }
                }
                if (attackerL.hasEffect(GoetyEffects.LEECHING)) {
                    MobEffectInstance effectInstance = attackerL.getEffect(GoetyEffects.LEECHING);
                    if (effectInstance != null) {
                        if (ModDamageSource.physicalAttacks(event.getSource())) {
                            int amp = effectInstance.getAmplifier();
                            float increase = 0.02F * amp;
                            float heal = target.getMaxHealth() * (0.05F + increase);
                            if (heal > 0.0F) {
                                attackerL.level.playSound(null, attackerL, ModSounds.LEECHING, attackerL.getSoundSource(), 1.0F, 1.0F);
                                attackerL.heal(heal);
                            }
                        }
                    }
                }
                if (attackerL.hasEffect(GoetyEffects.SWIRLING)) {
                    MobEffectInstance effectInstance = attackerL.getEffect(GoetyEffects.SWIRLING);
                    if (effectInstance != null) {
                        if (ModDamageSource.physicalAttacks(event.getSource()) && !event.getSource().is(ModDamageSource.SWORD)) {
                            int amp = effectInstance.getAmplifier();
                            float damage = 5.0F * ((amp / 2.0F) + 1);
                            if (attackerL.level instanceof ServerLevel serverLevel) {
                                ServerParticleUtil.windShockwaveParticle(serverLevel, ColorUtil.WHITE, 4.0F, 0, -1, attackerL.position().add(0.0D, 1.0D, 0.0D));
                                for (LivingEntity livingEntity : attackerL.level.getEntitiesOfClass(LivingEntity.class, attackerL.getBoundingBox().inflate(4.0D, 1.0D, 4.0D))) {
                                    if (attackerL != livingEntity && livingEntity != target && !MobUtil.areAllies(attackerL, livingEntity)) {
                                        livingEntity.hurt(ModDamageSource.sword(attackerL, attackerL), damage);
                                    }
                                }
                            }
                        }
                    }
                }
            }

            if (event.getAmount() > 0.0F) {
                if (target.hasEffect(GoetyEffects.ALTRUISTIC)) {
                    MobEffectInstance effectInstance = target.getEffect(GoetyEffects.ALTRUISTIC);
                    if (effectInstance != null) {
                        int amp = effectInstance.getAmplifier() + 1;
                        float heal = Math.min(0.25F * amp, 1.0F);
                        for (LivingEntity living : target.level.getEntitiesOfClass(LivingEntity.class, target.getBoundingBox().inflate(60.0D), livingEntity -> MobUtil.areAllies(target, livingEntity) && livingEntity != target)) {
                            living.heal(event.getAmount() * heal);
                        }
                    }
                }
                if (target.hasEffect(GoetyEffects.MANDATE)) {
                    MobEffectInstance effectInstance = target.getEffect(GoetyEffects.MANDATE);
                    if (effectInstance != null) {
                        int amp = effectInstance.getAmplifier() + 1;
                        double radius = 8.0D * amp;
                        int size = 4 * amp;
                        List<LivingEntity> allies = target.level.getEntitiesOfClass(LivingEntity.class, target.getBoundingBox().inflate(radius), livingEntity -> MobUtil.areAllies(target, livingEntity) && livingEntity != target && !livingEntity.hasEffect(GoetyEffects.MANDATE));
                        if (target instanceof Mob mob) {
                            allies.removeIf(livingEntity -> MobUtil.getOwner(livingEntity) != mob);
                        }
                        if (!allies.isEmpty()) {
                            allies.sort(Comparator.comparingDouble(target::distanceToSqr));
                            if (allies.size() > size) {
                                allies.subList(size, allies.size()).clear();
                            }
                            finalDamage /= (allies.size() + 1);
                            float distribute = finalDamage;
                            for (LivingEntity living : allies) {
                                if (living != target) {
                                    living.hurt(event.getSource(), distribute);
                                }
                            }
                        }
                    }
                }
            }
            if (finalDamage != event.getAmount()) {
                event.setAmount(finalDamage);
            }
        }
    }

    public static void experienceEvents(LivingExperienceDropEvent event) {
        Player player = event.getAttackingPlayer();
        LivingEntity living = event.getEntity();
        if (player != null && living != null) {
            if (player.hasEffect(GoetyEffects.INSIGHT)) {
                MobEffectInstance mobEffectInstance = player.getEffect(GoetyEffects.INSIGHT);
                if (mobEffectInstance != null) {
                    int a = (mobEffectInstance.getAmplifier() + 1) * 2;
                    event.setDroppedExperience(event.getOriginalExperience() * a);
                }
            }
        }
    }

    public static void deathEvents(LivingEntity effected, DamageSource damageSource) {
        if (effected instanceof Player player) {
            if (player.hasEffect(GoetyEffects.SAVE_EFFECTS)) {
                if (!player.getActiveEffects().isEmpty()) {
                    List<MobEffectInstance> instanceList = new ArrayList<>(player.getActiveEffects());
                    if (!instanceList.isEmpty()) {
                        ListTag listtag = new ListTag();
                        CompoundTag playerData = ((IEntityPersistentData) player).goety$getPersistentData();
                        CompoundTag data;

                        if (!playerData.contains(IEntityPersistentData.PLAYER_PERSISTED_NBT_TAG)) {
                            data = new CompoundTag();
                        } else {
                            data = playerData.getCompound(IEntityPersistentData.PLAYER_PERSISTED_NBT_TAG);
                        }
                        for (MobEffectInstance mobeffectinstance : instanceList) {
                            listtag.add(mobeffectinstance.save(new CompoundTag()));
                        }
                        data.put(ConstantPaths.keepEffects(), listtag);
                        playerData.put(IEntityPersistentData.PLAYER_PERSISTED_NBT_TAG, data);
                    }
                }
            }
            if (SEHelper.hasEndWalk(player)) {
                SEHelper.removeEndWalk(player);
            }
        }
        if (damageSource.getEntity() instanceof LivingEntity livingEntity) {
            if (livingEntity.hasEffect(GoetyEffects.CORPSE_EATER)) {
                MobEffectInstance mobEffectInstance = livingEntity.getEffect(GoetyEffects.CORPSE_EATER);
                if (mobEffectInstance != null) {
                    int amp = mobEffectInstance.getAmplifier() + 1;
                    int amount = Mth.floor(effected.getMaxHealth() / 5);
                    amount = (livingEntity.level.getRandom().nextInt(amount + 1) + 2) * amp;
                    if (amount > 0) {
                        if (livingEntity instanceof Player player) {
                            player.getFoodData().eat(amount, 0.1F);
                        } else {
                            livingEntity.heal(amount);
                        }
                    }
                }
            }
        }
        if (damageSource.is(ModDamageSource.DOOM)) {
            if (effected.level instanceof ServerLevel serverLevel) {
                serverLevel.sendParticles(ModParticleTypes.DOOM_DEATH, effected.getX(), effected.getNameTagOffsetY(), effected.getZ(), 0, 0.0D, 0.07D, 0.0D, 0.5D);
                effected.playSound(ModSounds.DOOM, 1.0F, 1.0F);
                ModNetwork.sendToALL(serverLevel.getServer(), SPlayWorldSoundPacket.ID, SPlayWorldSoundPacket.encode(effected.blockPosition(), ModSounds.DOOM, 1.0F, 1.0F));
            }
        }
        if (effected instanceof Villager villager) {
            if (villager.hasEffect(GoetyEffects.ILLAGUE) || villager.hasEffect(GoetyEffects.NECROSIS)) {
                if (villager.level instanceof ServerLevel serverLevel) {
                    ZombieVillager zombievillager = villager.convertTo(EntityType.ZOMBIE_VILLAGER, false);
                    if (zombievillager != null) {
                        zombievillager.finalizeSpawn(serverLevel, serverLevel.getCurrentDifficultyAt(zombievillager.blockPosition()), MobSpawnType.CONVERSION, new Zombie.ZombieGroupData(false, true), (CompoundTag) null);
                        zombievillager.setVillagerData(villager.getVillagerData());
                        zombievillager.setGossips(villager.getGossips().store(NbtOps.INSTANCE));
                        zombievillager.setTradeOffers(villager.getOffers().createTag());
                        zombievillager.setVillagerXp(villager.getVillagerXp());
                        if (!zombievillager.isSilent()) {
                            serverLevel.levelEvent(null, 1026, zombievillager.blockPosition(), 0);
                        }
                    }
                }
            }
        }
    }

    public static void respawnEvents(ServerPlayer oldPlayer, ServerPlayer newPlayer, boolean alive) {
        CompoundTag playerData = ((IEntityPersistentData) newPlayer).goety$getPersistentData();
        if (playerData.contains(IEntityPersistentData.PLAYER_PERSISTED_NBT_TAG)) {
            CompoundTag data = playerData.getCompound(IEntityPersistentData.PLAYER_PERSISTED_NBT_TAG);
            if (data.contains(ConstantPaths.keepEffects(), 9)) {
                ListTag listtag = data.getList(ConstantPaths.keepEffects(), 10);

                for (int i = 0; i < listtag.size(); ++i) {
                    MobEffectInstance mobeffectinstance = MobEffectInstance.load(listtag.getCompound(i));
                    if (mobeffectinstance != null
                            && !newPlayer.hasEffect(mobeffectinstance.getEffect())
                            && mobeffectinstance.getEffect() != GoetyEffects.SAVE_EFFECTS) {
                        newPlayer.addEffect(mobeffectinstance);
                    }
                }

                data.remove(ConstantPaths.keepEffects());
            }
        }
    }

    public static AttributeModifier CHARGE_SPEED_MOD = new AttributeModifier(UUID.fromString("d4818bbc-54ed-4ecf-95a3-a15fbf71b31d"), "Charged Speed I", 0.1, AttributeModifier.Operation.MULTIPLY_TOTAL);
    public static AttributeModifier CHARGE_ATTACK_MOD = new AttributeModifier(UUID.fromString("4bf0a8e3-a8f8-4bf6-95d2-f0ddbadd793e"), "Charged Attack I", 0.1, AttributeModifier.Operation.MULTIPLY_TOTAL);

    public static AttributeModifier CHARGE_MOAR_SPEED_MOD = new AttributeModifier(UUID.fromString("e8ea9f21-c671-4a61-a297-db8fa50f3d13"), "Charged Speed II", 0.25, AttributeModifier.Operation.MULTIPLY_TOTAL);
    public static AttributeModifier CHARGE_BAD_ATTACK_MOD = new AttributeModifier(UUID.fromString("a55e53d6-dd6a-41e8-8c1f-8f548887ed30"), "Charged Attack II", -0.15, AttributeModifier.Operation.MULTIPLY_TOTAL);

    public static void chargeEffect(LivingTickEvent event) {
        LivingEntity livingEntity = event.getEntity();
        if (livingEntity != null) {
            AttributeInstance speed = livingEntity.getAttribute(Attributes.MOVEMENT_SPEED);
            AttributeInstance attack = livingEntity.getAttribute(Attributes.ATTACK_DAMAGE);

            MobEffectInstance chargeInstance = livingEntity.getEffect(GoetyEffects.CHARGED);
            boolean notNull = chargeInstance != null;
            boolean flag = notNull && chargeInstance.getAmplifier() < 1;
            boolean flag2 = notNull && chargeInstance.getAmplifier() >= 1;
            if (attack != null && speed != null) {
                if (notNull) {
                    if (flag) {
                        if (speed.hasModifier(CHARGE_MOAR_SPEED_MOD)) {
                            speed.removeModifier(CHARGE_MOAR_SPEED_MOD);
                        }
                        if (attack.hasModifier(CHARGE_BAD_ATTACK_MOD)) {
                            attack.removeModifier(CHARGE_BAD_ATTACK_MOD);
                        }
                        if (!speed.hasModifier(CHARGE_SPEED_MOD)) {
                            speed.addPermanentModifier(CHARGE_SPEED_MOD);
                        }
                        if (!attack.hasModifier(CHARGE_ATTACK_MOD)) {
                            attack.addPermanentModifier(CHARGE_ATTACK_MOD);
                        }
                    } else if (flag2) {
                        if (speed.hasModifier(CHARGE_SPEED_MOD)) {
                            speed.removeModifier(CHARGE_SPEED_MOD);
                        }
                        if (attack.hasModifier(CHARGE_ATTACK_MOD)) {
                            attack.removeModifier(CHARGE_ATTACK_MOD);
                        }
                        if (!speed.hasModifier(CHARGE_MOAR_SPEED_MOD)) {
                            speed.addPermanentModifier(CHARGE_MOAR_SPEED_MOD);
                        }
                        if (!attack.hasModifier(CHARGE_BAD_ATTACK_MOD)) {
                            attack.addPermanentModifier(CHARGE_BAD_ATTACK_MOD);
                        }
                    }
                } else {
                    if (speed.hasModifier(CHARGE_SPEED_MOD)) {
                        speed.removeModifier(CHARGE_SPEED_MOD);
                    }
                    if (attack.hasModifier(CHARGE_ATTACK_MOD)) {
                        attack.removeModifier(CHARGE_ATTACK_MOD);
                    }
                    if (speed.hasModifier(CHARGE_MOAR_SPEED_MOD)) {
                        speed.removeModifier(CHARGE_MOAR_SPEED_MOD);
                    }
                    if (attack.hasModifier(CHARGE_BAD_ATTACK_MOD)) {
                        attack.removeModifier(CHARGE_BAD_ATTACK_MOD);
                    }
                }
            }
            if (notNull) {
                if (chargeInstance.getAmplifier() >= 2 && livingEntity.hurtTime > 0) {
                    livingEntity.removeEffect(chargeInstance.getEffect());
                } else {
                    if (livingEntity.tickCount % 20 == 0) {
                        if (livingEntity.level instanceof ServerLevel serverLevel) {
                            ServerParticleUtil.addParticlesAroundSelf(serverLevel, ModParticleTypes.ELECTRIC, livingEntity);
                        }
                    }
                }
            }
        }
    }

    public static void effectVisibilityEvents(LivingVisibilityEvent event) {
        if (event.getLookingEntity() instanceof LivingEntity living) {
            if (living.hasEffect(GoetyEffects.SENSE_LOSS)) {
                MobEffectInstance mobEffectInstance = living.getEffect(GoetyEffects.SENSE_LOSS);
                if (mobEffectInstance != null) {
                    int a = mobEffectInstance.getAmplifier();
                    event.modifyVisibility(0.5D - (a / 10.0D));
                }
            }
            if (event.getEntity().hasEffect(GoetyEffects.SHADOW_WALK)) {
                if (event.getLookingEntity().getType().is(ConventionalEntityTypeTags.BOSSES)) {
                    event.modifyVisibility(0.5D);
                } else {
                    event.modifyVisibility(0.0D);
                }
            }
        }
    }

    public static void changeTarget(LivingChangeTargetEvent event) {
        LivingEntity target = event.getOriginalTarget();
        if (target != null) {
            LivingEntity owner = MobUtil.getOwner(event.getEntity());
            if (target.hasEffect(GoetyEffects.SHADOW_WALK)
                    && !event.getEntity().getType().is(ConventionalEntityTypeTags.BOSSES)
                    && !(owner != null && owner.getType().is(ConventionalEntityTypeTags.BOSSES))) {
                if (event.getTargetType() == MOB_TARGET) {
                    event.setNewTarget(null);
                } else {
                    event.setCanceled(true);
                }
            }
        }
    }

    public static void enderTeleport(EntityTeleportEvent event) {
        if (event.getEntity() instanceof LivingEntity living) {
            if (living.hasEffect(GoetyEffects.ENDER_GROUND)) {
                event.setCanceled(true);
            }
        }
    }

    public static void finishItemEvents(LivingEntityUseItemEvent.Finish event) {
        if (event.getItem().getItem() == Items.MILK_BUCKET) {
            if (event.getEntity().hasEffect(GoetyEffects.SOUL_ARMOR)) {
                event.getEntity().removeEffect(GoetyEffects.SOUL_ARMOR);
            }
        }
        if (event.getItem().is(ModTags.Items.BREWABLE_FOOD)) {
            for (MobEffectInstance mobeffectinstance : PotionUtils.getMobEffects(event.getItem())) {
                if (mobeffectinstance.getEffect().isInstantenous()) {
                    mobeffectinstance.getEffect().applyInstantenousEffect(event.getEntity(), event.getEntity(), event.getEntity(), mobeffectinstance.getAmplifier(), 1.0D);
                } else {
                    event.getEntity().addEffect(new MobEffectInstance(mobeffectinstance));
                }
            }
            for (BrewEffectInstance brewEffectInstance : BrewUtils.getBrewEffects(event.getItem())) {
                brewEffectInstance.getEffect().drinkBlockEffect(event.getEntity(), event.getEntity(), event.getEntity(), brewEffectInstance.getAmplifier(), BrewUtils.getAreaOfEffect(event.getItem()));
            }
        }
        if (!(event.getItem().getItem() instanceof IWand)) {
            if (event.getEntity().hasEffect(GoetyEffects.SHADOW_WALK)) {
                event.getEntity().removeEffect(GoetyEffects.SHADOW_WALK);
            }
        }
    }

    public static void onCastingSpell(CastingMagicEvent event) {
        if (!(event.getSpell() instanceof EndWalkSpell)) {
            if (event.castingTime() > 20 || event.castingTime() >= event.getSpell().castDuration(event.getEntity(), event.getUseItem())) {
                if (event.getEntity().hasEffect(GoetyEffects.SHADOW_WALK)) {
                    event.getEntity().removeEffect(GoetyEffects.SHADOW_WALK);
                    event.setCanceled(true);
                }
            }
        }
    }

    public static void onCastSpell(CastMagicEvent event) {
        if (!(event.getSpell() instanceof EndWalkSpell)) {
            if (event.getEntity().hasEffect(GoetyEffects.SHADOW_WALK)) {
                event.getEntity().removeEffect(GoetyEffects.SHADOW_WALK);
            }
        }
    }

    public static void projectileAddEvents(Entity entity, ServerLevel world) {
        if (!world.isClientSide) {
            if (entity instanceof Projectile projectile) {
                if (projectile.getOwner() instanceof LivingEntity livingEntity) {
                    if (livingEntity.hasEffect(GoetyEffects.SHADOW_WALK)) {
                        livingEntity.removeEffect(GoetyEffects.SHADOW_WALK);
                    }
                }
            }
        }
    }

    public static void onDeflectImpact(ProjectileImpactEvent event) {
        Entity entity = event.getEntity();
        if (entity instanceof Projectile arrow) {
            HitResult rayTraceResult = event.getRayTraceResult();
            if (rayTraceResult instanceof EntityHitResult result) {
                if (result.getEntity() instanceof LivingEntity victim && victim != arrow.getOwner()) {
                    if (victim.hasEffect(GoetyEffects.DEFLECTIVE)) {
                        MobEffectInstance instance = victim.getEffect(GoetyEffects.DEFLECTIVE);
                        if (instance != null) {
                            int amp = instance.getAmplifier();
                            float chance = 0.25F + (amp / 10.0F);
                            if (victim.level.getRandom().nextFloat() <= chance) {
                                MobUtil.deflectProjectile(arrow, arrow.getOwner(), victim);
                                event.setCanceled(true);
                            }
                        }
                    }
                }
            }
        }
    }

    public static InteractionResultHolder<ItemStack> playerInteractItemEvents(Player player, Level level, InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);
        if (itemStack.getItem() instanceof BottleItem bottleItem) {
            List<DragonBreathCloud> list = level.getEntitiesOfClass(DragonBreathCloud.class, player.getBoundingBox().inflate(2.0D), (p_289499_) -> {
                return p_289499_ != null && p_289499_.isAlive() && p_289499_.getOwner() instanceof EnderDragon;
            });
            if (!list.isEmpty()) {
                DragonBreathCloud breathCloud = list.get(0);
                breathCloud.setRadius(breathCloud.getRadius() - 0.5F);
                level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.BOTTLE_FILL_DRAGONBREATH, SoundSource.NEUTRAL, 1.0F, 1.0F);
                level.gameEvent(player, GameEvent.FLUID_PICKUP, player.position());
                if (player instanceof ServerPlayer serverplayer) {
                    CriteriaTriggers.PLAYER_INTERACTED_WITH_ENTITY.trigger(serverplayer, itemStack, breathCloud);
                }
                player.awardStat(Stats.ITEM_USED.get(bottleItem));
                ItemUtils.createFilledResult(itemStack, player, new ItemStack(Items.DRAGON_BREATH));
                return InteractionResultHolder.sidedSuccess(itemStack, level.isClientSide);
            }
        }

        return InteractionResultHolder.pass(itemStack);
    }

    public static InteractionResult playerInteractEntityEvents(Player player, Level world, InteractionHand hand, Entity target, @Nullable EntityHitResult hitResult) {
        if (player.hasEffect(GoetyEffects.SHADOW_WALK)) {
            if (SEHelper.hasEndWalk(player)) {
                if (target instanceof Merchant merchant) {
                    merchant.setTradingPlayer(null);
                }
            }
            player.removeEffect(GoetyEffects.SHADOW_WALK);
            return InteractionResult.FAIL;
        }

        return InteractionResult.PASS;
    }

    public static InteractionResult playerInteractBlockEvents(Player player, Level level, InteractionHand hand, BlockHitResult blockHitResult) {
        BlockPos blockPos = blockHitResult.getBlockPos();
        BlockState blockState = level.getBlockState(blockPos);
        if (player.hasEffect(GoetyEffects.SHADOW_WALK)) {
            if (blockState.use(level, player, player.getUsedItemHand(), blockHitResult).consumesAction()) {
                player.removeEffect(GoetyEffects.SHADOW_WALK);
                return InteractionResult.FAIL;
            }
        }
        return InteractionResult.PASS;
    }

    public static boolean breakingBlockEvents(Level level, Player player, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity) {
        if (!state.isAir()) {
            if (player.hasEffect(GoetyEffects.SHADOW_WALK)) {
                player.removeEffect(GoetyEffects.SHADOW_WALK);
            }
        }
        if (!player.isCreative()) {
            return !player.hasEffect(GoetyEffects.IMPAIRED);
        }

        return true;
    }

    public static InteractionResult placingBlockEvents(Player player, Level level, InteractionHand hand, BlockHitResult blockHitResult) {
        // Entity entity = event.getEntity();
        // if (entity instanceof LivingEntity living) {

        // Fabric: check if the item is Block
        if (player.getItemInHand(hand).getItem() instanceof BlockItem blockItem) {
            BlockState state = blockItem.getBlock().defaultBlockState();
            if (!/*event.getState()*/state.isAir()) {
                if (/*living*/player.hasEffect(GoetyEffects.SHADOW_WALK)) {
                    // living.removeEffect(GoetyEffects.SHADOW_WALK);
                    player.removeEffect(GoetyEffects.SHADOW_WALK);
                }
            }
            // }
            // if (entity instanceof LivingEntity livingEntity) {
            if (EntitySelector.NO_CREATIVE_OR_SPECTATOR.test(/*livingEntity*/player)) {
                if (/*livingEntity*/player.hasEffect(GoetyEffects.IMPAIRED)) {
                    // event.setCanceled(true);
                    return InteractionResult.FAIL;
                }
            }
            // }
        }

        return InteractionResult.PASS;
    }

    // TODO
//    public static void mobGriefingEvents(EntityMobGriefingEvent event) {
//        if (event.getEntity() instanceof LivingEntity livingEntity) {
//            if (EntitySelector.NO_CREATIVE_OR_SPECTATOR.test(livingEntity)) {
//                if (livingEntity.hasEffect(GoetyEffects.IMPAIRED)) {
//                    event.setResult(Event.Result.DENY);
//                }
//            }
//        }
//    }

    public static void dimensionChangeEvents(Entity newEntity, ServerLevel origin, ServerLevel destination) {
        if (newEntity instanceof LivingEntity living) {
            if (living.hasEffect(GoetyEffects.SHADOW_WALK)) {
                if (living instanceof Player player) {
                    if (SEHelper.hasEndWalk(player)) {
                        SEHelper.removeEndWalk(player);
                    }
                }
                living.removeEffect(GoetyEffects.SHADOW_WALK);
            }
        }
    }

    public static void potionApplicationEvents(MobEffectEvent.Applicable event) {
        if (event.getEffectInstance() == null) return;

        if (event.getEffectInstance().getEffect() == MobEffects.FIRE_RESISTANCE) {
            if (event.getEntity().hasEffect(GoetyEffects.BURN_HEX)) {
                event.setResult(MobEffectEvent.Result.DENY);
            }
        }
        if (event.getEffectInstance().getEffect() == MobEffects.BLINDNESS) {
            if (event.getEntity() instanceof Player player) {
                if (ItemConfig.DarkHelmetBlindness.get()) {
                    if (ItemHelper.findHelmet(player, ModItems.DARK_HELMET)) {
                        event.setResult(MobEffectEvent.Result.DENY);
                    }
                }
            }
        }
        if (event.getEffectInstance().getEffect() == MobEffects.DARKNESS) {
            if (event.getEntity() instanceof Player player) {
                if (ItemConfig.DarkHelmetDarkness.get()) {
                    if (ItemHelper.findHelmet(player, ModItems.DARK_HELMET)) {
                        event.setResult(MobEffectEvent.Result.DENY);
                    }
                }
            }
        }
        if (event.getEffectInstance().getEffect() == MobEffects.SLOW_FALLING) {
            if (CuriosFinder.hasWindyRobes(event.getEntity())) {
                event.setResult(MobEffectEvent.Result.DENY);
            }
        }
        if (event.getEffectInstance().getEffect() == MobEffects.POISON) {
            if (event.getEntity().hasEffect(GoetyEffects.ACID_VENOM)) {
                event.setResult(MobEffectEvent.Result.DENY);
            }
        }
        if (event.getEffectInstance().getEffect() == GoetyEffects.HYSTERIA) {
            if (event.getEntity().getType().is(ConventionalEntityTypeTags.BOSSES)) {
                event.setResult(MobEffectEvent.Result.DENY);
            }
        }
        if (event.getEffectInstance().getEffect() == GoetyEffects.ILLAGUE) {
            if (event.getEntity().getType().is(EntityTypeTags.RAIDERS) || event.getEntity() instanceof PatrollingMonster) {
                event.setResult(MobEffectEvent.Result.DENY);
            }
        }
        if (event.getEffectInstance().getEffect() == GoetyEffects.FREEZING) {
            if (event.getEntity().hasEffect(GoetyEffects.SNOW_SKIN) || event.getEntity().getType().is(EntityTypeTags.FREEZE_IMMUNE_ENTITY_TYPES)) {
                event.setResult(MobEffectEvent.Result.DENY);
            }
        }
        if (event.getEffectInstance().getEffect() == GoetyEffects.BUSTED) {
            if (event.getEntity().getAttribute(Attributes.ARMOR) == null || event.getEntity().getAttributeValue(Attributes.ARMOR) <= 0.0D) {
                event.setResult(MobEffectEvent.Result.DENY);
            }
        }
        if (event.getEffectInstance().getEffect() == GoetyEffects.VOID_TOUCHED) {
            if (event.getEntity().getType().is(ModTags.EntityTypes.VOID_TOUCHED_IMMUNE)) {
                event.setResult(MobEffectEvent.Result.DENY);
            }
        }
        if (event.getEffectInstance().getEffect() == GoetyEffects.DOOM) {
            if (!event.getEntity().canChangeDimensions() || event.getEntity().getType().is(ConventionalEntityTypeTags.BOSSES)) {
                event.setResult(MobEffectEvent.Result.DENY);
            }
        }
    }

    public static void potionAddedEvents(MobEffectEvent.Added event) {
        LivingEntity effected = event.getEntity();
        MobEffectInstance instance = event.getEffectInstance();
        if (instance == null) return;

        MobEffect effect = instance.getEffect();
        if (effect == GoetyEffects.BURN_HEX) {
            if (effected.hasEffect(MobEffects.FIRE_RESISTANCE)) {
                effected.removeEffect(MobEffects.FIRE_RESISTANCE);
            }
        }
        if (effect == GoetyEffects.SNOW_SKIN) {
            if (effected.hasEffect(GoetyEffects.FREEZING)) {
                effected.removeEffect(GoetyEffects.FREEZING);
            }
        }
        if (effect == GoetyEffects.ENDER_GROUND) {
            if (effected.hasEffect(GoetyEffects.SHADOW_WALK)) {
                effected.removeEffect(GoetyEffects.SHADOW_WALK);
            }
        }
        if (effect == GoetyEffects.VOID_TOUCHED) {
            if (!effected.hasEffect(GoetyEffects.VOID_TOUCHED)) {
                if (effected.level instanceof ServerLevel) {
                    ModNetwork.sentToTrackingEntityAndPlayer(effected, SPlayWorldSoundPacket.ID, SPlayWorldSoundPacket.encode(effected.blockPosition(), ModSounds.VOID_TOUCHED_ACTIVATE, 1.0F, 1.0F));
                }
            }
        }
        if (effect == GoetyEffects.SENSE_LOSS) {
            if (effected instanceof Mob mob) {
                mob.setTarget(null);
            }
        }
    }

    public static void potionRemoveEvents(MobEffectEvent.Remove event) {
        LivingEntity effected = event.getEntity();
        if (effected != null) {
            if (event.getEffect() != null) {
                if (effected.hasEffect(GoetyEffects.SAVE_EFFECTS)) {
                    event.setCanceled(event.getEffect() != GoetyEffects.SAVE_EFFECTS
                            && (effected instanceof Player player
                            && SEHelper.hasEndWalk(player)
                            && event.getEffect() != GoetyEffects.SHADOW_WALK));
                }
                if (event.getEffect() != null) {
                    if (event.getEffect() == GoetyEffects.SHADOW_WALK) {
                        if (effected instanceof Player player) {
                            teleportShadowWalk(player);
                        }
                    }
                }
                if (event.getEffect() == GoetyEffects.WILD_RAGE) {
                    if (effected instanceof Mob mob) {
                        mob.setTarget(null);
                        mob.setLastHurtByMob(null);
                        mob.getBrain().eraseMemory(MemoryModuleType.ANGRY_AT);
                        mob.getBrain().eraseMemory(MemoryModuleType.ATTACK_TARGET);
                    }
                }
                if (event.getEffect() == GoetyEffects.VOID_TOUCHED) {
                    if (effected.level instanceof ServerLevel) {
                        ModNetwork.sentToTrackingEntityAndPlayer(effected, SPlayWorldSoundPacket.ID, SPlayWorldSoundPacket.encode(effected.blockPosition(), ModSounds.VOID_TOUCHED_DEACTIVATE, 1.0F, 1.0F));
                    }
                }
            }
        }
    }

    public static void potionExpiredEvents(MobEffectEvent.Expired event) {
        LivingEntity effected = event.getEntity();
        MobEffectInstance mobEffectInstance = event.getEffectInstance();
        if (mobEffectInstance != null) {
            if (mobEffectInstance.getEffect() == GoetyEffects.SHADOW_WALK) {
                if (effected instanceof Player player) {
                    teleportShadowWalk(player);
                }
            }

            if (mobEffectInstance.getEffect() == GoetyEffects.DOOM) {
                if (effected.canChangeDimensions() && !effected.getType().is(ConventionalEntityTypeTags.BOSSES)) {
                    int a = mobEffectInstance.getAmplifier() + 1;
                    float doom = 0.05F * a;
                    if (effected.getHealth() <= effected.getMaxHealth() * doom) {
                        effected.hurt(ModDamageSource.getDamageSource(effected.level, ModDamageSource.DOOM), effected.getMaxHealth() * 20);
                    }
                }
            }

            if (mobEffectInstance.getEffect() == GoetyEffects.VOID_TOUCHED) {
                if (effected.level instanceof ServerLevel) {
                    ModNetwork.sentToTrackingEntityAndPlayer(effected, SPlayWorldSoundPacket.ID, SPlayWorldSoundPacket.encode(effected.blockPosition(), ModSounds.VOID_TOUCHED_DEACTIVATE, 1.0F, 1.0F));
                }
            }
        }
    }

    public static void teleportShadowWalk(Player player) {
        BlockPos blockPos = SEHelper.getEndWalkPos(player);
        if (blockPos != null) {
            if (SEHelper.getEndWalkDimension(player) != null
                    && player.level.dimension() == SEHelper.getEndWalkDimension(player)) {
                ((PlayerAccessor) player).goety$closeContainer();
                player.teleportTo(blockPos.getX() + 0.5D, blockPos.getY(), blockPos.getZ() + 0.5D);
                if (!player.level.isClientSide) {
                    player.level.broadcastEntityEvent(player, (byte) 46);
                    ModNetwork.sendToALL(player.level.getServer(), SPlayWorldSoundPacket.ID, SPlayWorldSoundPacket.encode(player.blockPosition(), SoundEvents.ENDERMAN_TELEPORT, 1.0F, 1.0F));
                    ModNetwork.sendToALL(player.level.getServer(), SPlayEntitySoundPacket.ID, SPlayEntitySoundPacket.encode(player.getUUID(), SoundEvents.ENDERMAN_TELEPORT, 1.0F, 1.0F));
                }
                Vec3 vec3 = player.position();
                player.level.gameEvent(GameEvent.TELEPORT, vec3, GameEvent.Context.of(player));
            }
            SEHelper.removeEndWalk(player);
        }
    }
}
