package com.Polarice3.Goety.common.events;

import cn.sh1rocu.goety.api.event.*;
import com.Polarice3.Goety.api.blocks.IEnchanteableBlock;
import com.Polarice3.Goety.api.items.IPersist;
import com.Polarice3.Goety.api.items.ISoulRepair;
import com.Polarice3.Goety.api.items.magic.IWand;
import com.Polarice3.Goety.client.particles.ShockwaveParticleOption;
import com.Polarice3.Goety.common.blocks.ModBlocks;
import com.Polarice3.Goety.common.effects.GoetyEffects;
import com.Polarice3.Goety.common.enchantments.ModEnchantments;
import com.Polarice3.Goety.common.items.ModItems;
import com.Polarice3.Goety.common.items.ModTiers;
import com.Polarice3.Goety.common.items.armor.ModArmorMaterials;
import com.Polarice3.Goety.common.items.brew.BrewItem;
import com.Polarice3.Goety.common.items.curios.WitchHatItem;
import com.Polarice3.Goety.common.items.equipment.*;
import com.Polarice3.Goety.common.items.magic.DarkStaff;
import com.Polarice3.Goety.common.items.revive.ReviveServantItem;
import com.Polarice3.Goety.config.ItemConfig;
import com.Polarice3.Goety.config.MainConfig;
import com.Polarice3.Goety.init.ModSounds;
import com.Polarice3.Goety.init.ModTags;
import com.Polarice3.Goety.utils.*;
import com.google.common.collect.ImmutableList;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.*;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.TallGrassBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ItemEvents {

    public static AttributeModifier TWO_HAND_SCYTHE_SPEED_MOD = new AttributeModifier(UUID.fromString("0c091f42-8c6d-4fde-96e9-148115731cbf"), "Two Handed Scythe", 0.25F, AttributeModifier.Operation.MULTIPLY_TOTAL);
    public static AttributeModifier GLOVE_SCYTHE_SPEED_MOD = new AttributeModifier(UUID.fromString("d4818bbc-54ed-4ecf-95a3-a15fbf71b31d"), "Scythe Proficiency", 0.5F, AttributeModifier.Operation.MULTIPLY_TOTAL);
    public static AttributeModifier TWO_HAND_HAMMER_SPEED_MOD = new AttributeModifier(UUID.fromString("3f0d53a8-f075-4d27-a0b7-a4d923542d4f"), "Two Handed Hammer", 0.25F, AttributeModifier.Operation.MULTIPLY_TOTAL);
    public static AttributeModifier GLOVE_HAMMER_SPEED_MOD = new AttributeModifier(UUID.fromString("39c01496-8161-4fde-ac2c-0bea379ceb37"), "Hammer Proficiency", 0.5F, AttributeModifier.Operation.MULTIPLY_TOTAL);
    public static AttributeModifier STAFF_ATTACK_BOOST_MOD = new AttributeModifier(UUID.fromString("6dc7952d-11a6-4bf4-954b-b527b35787c6"), "Dark Staff Proficiency", 0.25D, AttributeModifier.Operation.MULTIPLY_TOTAL);

    public static void playerTick(PlayerTickEvent event) {
        Player player = event.getEntity();
        if (event instanceof PlayerTickEvent.Post) {
            if (ItemHelper.findHelmet(player, ModItems.DARK_HELMET)) {
                if (ItemConfig.DarkHelmetDarkness.get()) {
                    if (player.getEffect(MobEffects.DARKNESS) != null) {
                        player.removeEffect(MobEffects.DARKNESS);
                    }
                }
                if (ItemConfig.DarkHelmetBlindness.get()) {
                    if (player.getEffect(MobEffects.BLINDNESS) != null) {
                        player.removeEffect(MobEffects.BLINDNESS);
                    }
                }
            }

            Inventory inventory = player.getInventory();

            List<NonNullList<ItemStack>> compartments = ImmutableList.of(inventory.items, inventory.armor, inventory.offhand);

            for (NonNullList<ItemStack> nonnulllist : compartments) {
                for (int i = 0; i < nonnulllist.size(); ++i) {
                    if (!nonnulllist.get(i).isEmpty()) {
                        ItemStack itemStack = nonnulllist.get(i);
                        Item item = itemStack.getItem();
                        if (!(item instanceof IPersist persist) || !persist.isBroken(itemStack)) {
                            if (itemStack.getItem() instanceof ISoulRepair soulRepair) {
                                soulRepair.repairTick(nonnulllist.get(i), player, inventory.selected == i);
                            } else if (itemStack.getItem() instanceof TieredItem tieredItem && tieredItem.getTier() == ModTiers.DARK) {
                                ItemHelper.repairTick(itemStack, player, inventory.selected == i);
                            }
                        }
                    }
                }
            }

            if (ItemHelper.armorSet(player, ModArmorMaterials.DARK)) {
                if (player.getFoodData().needsFood()) {
                    if (player.tickCount % 40 == 0) {
                        player.heal(1.0F);
                    }
                }
            }
        }

        AttributeInstance attackSpeed = player.getAttribute(Attributes.ATTACK_SPEED);
        boolean scythe = player.getMainHandItem().getItem() instanceof DarkScytheItem;

        boolean flag0 = scythe && player.getOffhandItem().isEmpty();
        if (attackSpeed != null) {
            if (flag0) {
                if (!attackSpeed.hasModifier(TWO_HAND_SCYTHE_SPEED_MOD)) {
                    attackSpeed.addPermanentModifier(TWO_HAND_SCYTHE_SPEED_MOD);
                }
            } else {
                if (attackSpeed.hasModifier(TWO_HAND_SCYTHE_SPEED_MOD)) {
                    attackSpeed.removeModifier(TWO_HAND_SCYTHE_SPEED_MOD);
                }
            }
        }

        boolean flag = CuriosFinder.hasCurio(player, ModItems.GRAVE_GLOVE) && (scythe || player.getMainHandItem().is(ModTags.Items.GRAVE_GLOVE_BOOST));
        if (attackSpeed != null) {
            if (flag) {
                if (!attackSpeed.hasModifier(GLOVE_SCYTHE_SPEED_MOD)) {
                    attackSpeed.addPermanentModifier(GLOVE_SCYTHE_SPEED_MOD);
                }
            } else {
                if (attackSpeed.hasModifier(GLOVE_SCYTHE_SPEED_MOD)) {
                    attackSpeed.removeModifier(GLOVE_SCYTHE_SPEED_MOD);
                }
            }
        }

        boolean hammer = player.getMainHandItem().getItem() instanceof HammerItem;

        boolean flag1 = hammer && player.getOffhandItem().isEmpty();
        if (attackSpeed != null) {
            if (flag1) {
                if (!attackSpeed.hasModifier(TWO_HAND_HAMMER_SPEED_MOD)) {
                    attackSpeed.addPermanentModifier(TWO_HAND_HAMMER_SPEED_MOD);
                }
            } else {
                if (attackSpeed.hasModifier(TWO_HAND_HAMMER_SPEED_MOD)) {
                    attackSpeed.removeModifier(TWO_HAND_HAMMER_SPEED_MOD);
                }
            }
        }

        boolean flag2 = CuriosFinder.hasCurio(player, ModItems.THRASH_GLOVE) && (hammer || player.getMainHandItem().is(ModTags.Items.THRASH_GLOVE_BOOST));
        if (attackSpeed != null) {
            if (flag2) {
                if (!attackSpeed.hasModifier(GLOVE_HAMMER_SPEED_MOD)) {
                    attackSpeed.addPermanentModifier(GLOVE_HAMMER_SPEED_MOD);
                }
            } else {
                if (attackSpeed.hasModifier(GLOVE_HAMMER_SPEED_MOD)) {
                    attackSpeed.removeModifier(GLOVE_HAMMER_SPEED_MOD);
                }
            }
        }

        boolean staff = player.getOffhandItem().getItem() instanceof DarkStaff && ItemConfig.StaffOffhandBuff.get();

        AttributeInstance attackDamage = player.getAttribute(Attributes.ATTACK_DAMAGE);

        if (attackDamage != null) {
            if (staff) {
                if (!attackDamage.hasModifier(STAFF_ATTACK_BOOST_MOD)) {
                    attackDamage.addPermanentModifier(STAFF_ATTACK_BOOST_MOD);
                }
            } else {
                if (attackDamage.hasModifier(STAFF_ATTACK_BOOST_MOD)) {
                    attackDamage.removeModifier(STAFF_ATTACK_BOOST_MOD);
                }
            }
        }
        if (MobUtil.starAmuletActive(player)) {
            player.getAbilities().flying &= player.isCreative();
        }
    }

    public static AttributeModifier ARMOR_INCREASE_MOD = new AttributeModifier(UUID.fromString("17cb060f-0465-412e-abe7-a9c397b2e548"), "Increase Armor", 4.0D, AttributeModifier.Operation.ADDITION);
    public static AttributeModifier TOUGHNESS_INCREASE_MOD = new AttributeModifier(UUID.fromString("c3c510ca-76eb-4eb5-9f69-6763b7e40be2"), "Increase Toughness", 4.0D, AttributeModifier.Operation.ADDITION);

    public static void livingEffects(LivingTickEvent event) {
        LivingEntity livingEntity = event.getEntity();
        if (livingEntity != null && livingEntity.isAlive()) {
            AttributeInstance armor = livingEntity.getAttribute(Attributes.ARMOR);
            AttributeInstance toughness = livingEntity.getAttribute(Attributes.ARMOR_TOUGHNESS);
            if (armor != null) {
                if (ItemHelper.armorSet(livingEntity, ModArmorMaterials.CURSED_KNIGHT) || ItemHelper.armorSet(livingEntity, ModArmorMaterials.CURSED_PALADIN)) {
                    if (!armor.hasModifier(ARMOR_INCREASE_MOD)) {
                        armor.addPermanentModifier(ARMOR_INCREASE_MOD);
                    }
                } else {
                    if (armor.hasModifier(ARMOR_INCREASE_MOD)) {
                        armor.removeModifier(ARMOR_INCREASE_MOD);
                    }
                }
            }
            if (toughness != null) {
                if (ItemHelper.armorSet(livingEntity, ModArmorMaterials.CURSED_PALADIN)) {
                    if (!toughness.hasModifier(TOUGHNESS_INCREASE_MOD)) {
                        toughness.addPermanentModifier(TOUGHNESS_INCREASE_MOD);
                    }
                } else {
                    if (toughness.hasModifier(TOUGHNESS_INCREASE_MOD)) {
                        toughness.removeModifier(TOUGHNESS_INCREASE_MOD);
                    }
                }
            }
        }
    }

    public static void hurtEvent(LivingHurtEvent event) {
        LivingEntity victim = event.getEntity();
        Entity directEntity = event.getSource().getDirectEntity();
        if (event.getAmount() > 0.0F) {
            if (directEntity instanceof LivingEntity livingAttacker) {
                if (ModDamageSource.physicalAttacks(event.getSource())) {
                    ItemHelper.setItemEffect(livingAttacker.getMainHandItem(), victim);
                    if (livingAttacker.getMainHandItem().getItem() instanceof TieredItem weapon) {
                        if (weapon == ModItems.FANGED_DAGGER) {
                            MobEffect effect = MobEffects.POISON;
                            if (CuriosFinder.hasWildRobe(livingAttacker)) {
                                effect = GoetyEffects.ACID_VENOM;
                            }
                            if (livingAttacker.hasEffect(GoetyEffects.VENOMOUS_HANDS)) {
                                EffectsUtil.increaseDuration(victim, effect, 600);
                            } else {
                                victim.addEffect(new MobEffectInstance(effect, 200));
                            }
                        }
                        if (weapon == ModItems.FROZEN_BLADE) {
                            if (victim.getType().is(EntityTypeTags.FREEZE_HURTS_EXTRA_TYPES)) {
                                event.setAmount(event.getAmount() * 2);
                            }
                        }
                        if (weapon == ModItems.HUNGRY_DAGGER) {
                            int soulEat = EnchantmentHelper.getEnchantmentLevel(ModEnchantments.SOUL_EATER, livingAttacker) + 1;
                            livingAttacker.heal(event.getAmount() * (0.05F * soulEat));
                        }
                        if (weapon instanceof DarkScytheItem) {
                            victim.playSound(ModSounds.SCYTHE_HIT_MEATY);
                        }
                        if (weapon instanceof DeathScytheItem) {
                            if (ItemConfig.DeathScytheSappedDuration.get() > 0) {
                                int seconds = MathHelper.secondsToTicks(ItemConfig.DeathScytheSappedDuration.get());
                                if (!victim.hasEffect(GoetyEffects.SAPPED)) {
                                    victim.addEffect(new MobEffectInstance(GoetyEffects.SAPPED, seconds));
                                    victim.playSound(SoundEvents.SHIELD_BREAK, 2.0F, 1.0F);
                                } else {
                                    if (ItemConfig.DeathScytheSappedChance.get() > 0 && victim.level.random.nextFloat() <= (ItemConfig.DeathScytheSappedChance.get() / 100.0F)) {
                                        EffectsUtil.amplifyEffect(victim, GoetyEffects.SAPPED, seconds);
                                        victim.playSound(SoundEvents.SHIELD_BREAK, 2.0F, 1.0F);
                                    } else {
                                        EffectsUtil.resetDuration(victim, GoetyEffects.SAPPED, seconds);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        if (victim instanceof Player player) {
            if (CuriosFinder.hasCurio(victim, ModItems.SPITEFUL_BELT)) {
                int a = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.THORNS, CuriosFinder.findCurio(victim, ModItems.SPITEFUL_BELT));
                if (SEHelper.getSoulsAmount(player, ItemConfig.SpitefulBeltUseAmount.get() * (a + 1))) {
                    if (!event.getSource().is(DamageTypeTags.AVOIDS_GUARDIAN_THORNS) && !event.getSource().is(DamageTypes.THORNS) && event.getSource().getEntity() instanceof LivingEntity livingentity && livingentity != victim) {
                        livingentity.hurt(livingentity.damageSources().thorns(victim), 2.0F + a);
                        SEHelper.decreaseSouls(player, ItemConfig.SpitefulBeltUseAmount.get() * (a + 1));
                    }
                }
            }
        }
    }

    public static void onLivingDamage(LivingDamageEvent event) {
        LivingEntity victim = event.getEntity();
        Entity directEntity = event.getSource().getDirectEntity();
        if (event.getAmount() > 0.0F) {
            if (directEntity instanceof LivingEntity livingAttacker) {
                if (ModDamageSource.physicalAttacks(event.getSource())) {
                    if (livingAttacker.getMainHandItem().getItem() instanceof TieredItem weapon) {
                        if (weapon == ModItems.BLADE_OF_ENDER) {
                            MobEffect effect = GoetyEffects.VOID_TOUCHED;
                            int amp = 0;
                            if (!livingAttacker.hasEffect(effect)) {
                                victim.addEffect(new MobEffectInstance(effect, MathHelper.secondsToTicks(5), amp, false, true));
                            }
                        }
                    }
                }
            }
        }
    }

    public static void onLivingJump(LivingJumpEvent event) {
        if (event.getEntity() instanceof Player player) {
            if (CuriosFinder.hasCurio(player, ModItems.WAYFARERS_BELT)) {
                float f = 0.625F;
                if (player.hasEffect(MobEffects.JUMP)) {
                    f += 0.1F * (float) (player.getEffect(MobEffects.JUMP).getAmplifier() + 1);
                }
                Vec3 vector3d = player.getDeltaMovement();
                player.setDeltaMovement(vector3d.x, f, vector3d.z);
            }
        }

    }

    public static void onLivingFall(LivingFallEvent event) {
        if (event.getEntity() instanceof Player player) {
            if (CuriosFinder.hasCurio(player, ModItems.WAYFARERS_BELT)) {
                event.setDistance(event.getDistance() / 2);
            }
        }
    }

    public static boolean onBreakingBlock(Level world, Player player, BlockPos pos, BlockState blockState, @Nullable BlockEntity blockEntity) {
        Block block = blockState.getBlock();
        ItemStack tool = player.getMainHandItem();
        boolean canceled = false;
        if (tool.getItem() instanceof PhilosophersMaceItem) {
            if (block.getDescriptionId().contains("nether_gold")) {
                if (!player.level.isClientSide) {
                    Block.dropResources(Blocks.GOLD_ORE.defaultBlockState(), player.level, pos, null, player, player.getMainHandItem());
                    block.playerWillDestroy(player.level, pos, blockState, player);
                    player.level.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
                    ItemHelper.hurtAndBreak(tool, 1, player);
                    // event.setCanceled(true);
                    canceled = true;
                }
            }
        }
        if (tool.getItem() instanceof DarkScytheItem) {
            if (block.getDescriptionId().contains("sculk") && blockState.is(BlockTags.MINEABLE_WITH_HOE)) {
                if (!player.level.isClientSide) {
                    ItemStack fakeItem = new ItemStack(Items.DIAMOND_HOE);
                    fakeItem.enchant(Enchantments.SILK_TOUCH, 1);
                    Map<Enchantment, Integer> map1 = EnchantmentHelper.getEnchantments(tool);
                    if (!map1.isEmpty()) {
                        for (Enchantment enchantment : EnchantmentHelper.getEnchantments(tool).keySet()) {
                            if (enchantment != Enchantments.SILK_TOUCH) {
                                fakeItem.enchant(enchantment, map1.get(enchantment));
                            }
                        }
                    }
                    if (block instanceof IEnchanteableBlock) {
                        block.playerDestroy(player.level, player, pos, blockState, blockEntity, fakeItem);
                    } else {
                        Block.dropResources(blockState, player.level, pos, null, player, fakeItem);
                    }
                    player.level.levelEvent(player, 2001, pos, Block.getId(blockState));
                    player.level.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
                    ItemHelper.hurtAndBreak(player.getMainHandItem(), 1, player);
                    // event.setCanceled(true);
                    canceled = true;
                }
            }
        }
        if (tool.getItem() instanceof IceAxeItem) {
            if (blockState.is(BlockTags.ICE)) {
                if (!player.level.isClientSide) {
                    ItemStack fakeItem = new ItemStack(Items.IRON_PICKAXE);
                    fakeItem.enchant(Enchantments.SILK_TOUCH, 1);
                    Map<Enchantment, Integer> map1 = EnchantmentHelper.getEnchantments(tool);
                    if (!map1.isEmpty()) {
                        for (Enchantment enchantment : EnchantmentHelper.getEnchantments(tool).keySet()) {
                            if (enchantment != Enchantments.SILK_TOUCH) {
                                fakeItem.enchant(enchantment, map1.get(enchantment));
                            }
                        }
                    }
                    if (block instanceof IEnchanteableBlock) {
                        block.playerDestroy(player.level, player, pos, blockState, blockEntity, fakeItem);
                    } else {
                        Block.dropResources(blockState, player.level, pos, null, player, fakeItem);
                    }
                    block.playerWillDestroy(player.level, pos, blockState, player);
                    player.level.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
                    ItemHelper.hurtAndBreak(player.getMainHandItem(), 1, player);
                    // event.setCanceled(true);
                    canceled = true;
                }
            }
        }
        if (tool.getItem() instanceof SickleItem) {
            if (!player.isCreative()) {
                if (!EnchantmentHelper.hasSilkTouch(tool)) {
                    if (player.level.getGameRules().getBoolean(GameRules.RULE_DOBLOCKDROPS)) {
                        if (block instanceof TallGrassBlock || blockState.is(Blocks.TALL_GRASS) || blockState.is(Blocks.LARGE_FERN)) {
                            if (!player.level.isClientSide) {
                                boolean damage = false;
                                if (player.level.getRandom().nextFloat() < 0.125F) {
                                    int i = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.BLOCK_FORTUNE, tool);
                                    int count = 1 + RandomUtil.nextInt(player.level.getRandom(), i);
                                    Block.popResource(player.level, pos, new ItemStack(ModBlocks.HENBANE_SEEDS, count));
                                    damage = true;
                                }
                                if (player.level.getRandom().nextFloat() < 0.1F) {
                                    int i = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.BLOCK_FORTUNE, tool);
                                    int count = 1 + RandomUtil.nextInt(player.level.getRandom(), i);
                                    Block.popResource(player.level, pos, new ItemStack(ModBlocks.NIGHTSHADE_SEEDS, count));
                                    damage = true;
                                }
                                if (damage) {
                                    ItemHelper.hurtAndBreak(player.getMainHandItem(), 1, player);
                                }
                            }
                        }
                    }
                }
            }
        }
        return !canceled;
    }

    public static InteractionResult playerInteractBlockEvents(Player player, Level level, InteractionHand hand, BlockHitResult blockHitResult) {
        BlockPos blockPos = blockHitResult.getBlockPos();
        BlockState blockState = level.getBlockState(blockPos);
        ItemStack itemStack = player.getItemInHand(hand);
        if (PotionUtils.getPotion(itemStack) == Potions.WATER) {
            if (blockHitResult.getDirection() != Direction.DOWN && blockState.is(ModBlocks.END_SOIL)) {
                level.playSound(null, blockPos, SoundEvents.GENERIC_SPLASH, SoundSource.BLOCKS, 1.0F, 1.0F);
                player.setItemInHand(hand, ItemUtils.createFilledResult(itemStack, player, new ItemStack(Items.GLASS_BOTTLE)));
                player.awardStat(Stats.ITEM_USED.get(itemStack.getItem()));
                if (!level.isClientSide) {
                    ServerLevel serverlevel = (ServerLevel) level;

                    for (int i = 0; i < 5; ++i) {
                        serverlevel.sendParticles(ParticleTypes.SPLASH, (double) blockPos.getX() + level.random.nextDouble(), blockPos.getY() + 1, (double) blockPos.getZ() + level.random.nextDouble(), 1, 0.0D, 0.0D, 0.0D, 1.0D);
                    }
                }

                level.playSound(null, blockPos, SoundEvents.BOTTLE_EMPTY, SoundSource.BLOCKS, 1.0F, 1.0F);
                level.gameEvent(null, GameEvent.FLUID_PLACE, blockPos);
                level.setBlockAndUpdate(blockPos, ModBlocks.END_MUD.defaultBlockState());
                return InteractionResult.sidedSuccess(level.isClientSide);
            }
        } else if (itemStack.is(Items.GLASS_BOTTLE)) {
            if (blockHitResult.getDirection() != Direction.DOWN && blockState.is(ModBlocks.END_MUD)) {
                level.playSound(null, blockPos, SoundEvents.GENERIC_SPLASH, SoundSource.BLOCKS, 1.0F, 1.0F);
                player.setItemInHand(hand, ItemUtils.createFilledResult(itemStack, player, new ItemStack(ModItems.END_MUD_BOTTLE)));
                player.awardStat(Stats.ITEM_USED.get(itemStack.getItem()));
                if (!level.isClientSide) {
                    ServerLevel serverlevel = (ServerLevel) level;

                    for (int i = 0; i < 5; ++i) {
                        serverlevel.sendParticles(ParticleTypes.SPLASH, (double) blockPos.getX() + level.random.nextDouble(), blockPos.getY() + 1, (double) blockPos.getZ() + level.random.nextDouble(), 1, 0.0D, 0.0D, 0.0D, 1.0D);
                    }
                }

                level.playSound(null, blockPos, SoundEvents.BOTTLE_FILL, SoundSource.BLOCKS, 1.0F, 1.0F);
                level.gameEvent(null, GameEvent.FLUID_PLACE, blockPos);
                level.setBlockAndUpdate(blockPos, ModBlocks.END_SOIL.defaultBlockState());
                return InteractionResult.sidedSuccess(level.isClientSide);
            }
        } else if (/*itemStack.canPerformAction(ToolActions.PICKAXE_DIG)*/ itemStack.is(ItemTags.PICKAXES) || itemStack.getItem() instanceof PickaxeItem) {
            if (blockState.is(ModBlocks.COBBLED_OMINOUS_STONE_BLOCK)) {
                if (blockHitResult.getDirection() != Direction.DOWN) {
                    if (level.isEmptyBlock(blockPos.above())) {
                        BlockState blockstate2 = ModBlocks.COBBLED_OMINOUS_STONE_PATH_BLOCK.defaultBlockState();
                        level.playSound(player, blockPos, SoundEvents.UI_STONECUTTER_TAKE_RESULT, SoundSource.BLOCKS, 1.0F, 1.0F);
                        if (!level.isClientSide) {
                            level.setBlock(blockPos, blockstate2, 11);
                            level.gameEvent(GameEvent.BLOCK_CHANGE, blockPos, GameEvent.Context.of(player, blockstate2));
                            if (player != null) {
                                ItemHelper.hurtAndBreak(itemStack, 1, player);
                                player.swing(hand);
                            }
                        }
                        return InteractionResult.sidedSuccess(level.isClientSide);
                    }
                }
            }
        }
        return InteractionResult.PASS;
    }

    public static void useItemEvent(LivingEntityUseItemEvent.Finish event) {
        if (CuriosFinder.hasCurio(event.getEntity(), ModItems.CRONE_HAT)) {
            if (event.getEntity().level.random.nextFloat() <= 0.25F) {
                if (event.getItem().getItem() instanceof PotionItem) {
                    event.setResultStack(event.getItem());
                }
            }
            if (event.getEntity().level.random.nextFloat() <= 0.1F) {
                if (event.getItem().getItem() instanceof BrewItem) {
                    event.setResultStack(event.getItem());
                }
            }
        } else if (CuriosFinder.hasCurio(event.getEntity(), itemStack -> itemStack.getItem() instanceof WitchHatItem)) {
            if (event.getEntity().level.random.nextFloat() <= 0.1F) {
                if (event.getItem().getItem() instanceof PotionItem) {
                    event.setResultStack(event.getItem());
                }
            }
        }
        if (event.getEntity() instanceof Player player) {
            if (MainConfig.WandCoolItemUse.get()) {
                if (!(event.getItem().getItem() instanceof IWand)) {
                    Item main = event.getEntity().getMainHandItem().getItem();
                    Item off = event.getEntity().getOffhandItem().getItem();
                    if (main instanceof IWand) {
                        player.getCooldowns().addCooldown(main, 10);
                    } else if (off instanceof IWand) {
                        player.getCooldowns().addCooldown(off, 10);
                    }
                }
            }
        }
    }

    public static void axeDeath(LivingEntity killed, DamageSource damageSource) {
        Entity killer = damageSource.getEntity();
        Level world = killed.getCommandSenderWorld();
        if (killer instanceof LivingEntity livingEntity) {
            if (ModDamageSource.physicalAttacks(damageSource) && livingEntity.getMainHandItem().getItem() instanceof RampagingAxeItem) {
                MobEffectInstance effectinstance1 = livingEntity.getEffect(GoetyEffects.RAMPAGE);
                if (!livingEntity.hasEffect(GoetyEffects.RAMPAGE)) {
                    livingEntity.addEffect(new MobEffectInstance(GoetyEffects.RAMPAGE, MathHelper.secondsToTicks(ItemConfig.RampagingAxeDuration.get())));
                } else if (effectinstance1 != null) {
                    int random = killed.getMaxHealth() >= 20 ? 0 : world.random.nextInt(4);
                    if (effectinstance1.getAmplifier() < 4) {
                        if (random == 0) {
                            EffectsUtil.amplifyEffect(livingEntity, GoetyEffects.RAMPAGE, MathHelper.secondsToTicks(ItemConfig.RampagingAxeDuration.get()));
                        }
                    } else {
                        livingEntity.removeEffect(GoetyEffects.RAMPAGE);
                        if (world instanceof ServerLevel serverLevel) {
                            serverLevel.sendParticles(new ShockwaveParticleOption(), livingEntity.getX(), livingEntity.getY() + 0.5F, livingEntity.getZ(), 0, 0.0D, 0.0D, 0.0D, 0);
                            serverLevel.sendParticles(ParticleTypes.EXPLOSION_EMITTER, livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(), 0, 1.0D, 0.0D, 0.0D, 0.5F);
                        }
                        LootingExplosion.Mode lootMode = CuriosFinder.hasWanting(livingEntity) ? LootingExplosion.Mode.LOOT : LootingExplosion.Mode.REGULAR;
                        ExplosionUtil.lootExplode(world, livingEntity, livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(), 3.0F, false, Explosion.BlockInteraction.KEEP, lootMode);
                    }
                }
            }
        }
    }

    public static void hunterLoot(LootingLevelEvent event) {
        if (event.getDamageSource() != null) {
            if (event.getEntity() != null) {
                if (!event.getEntity().level.isClientSide) {
                    if (event.getDamageSource().getEntity() != null && event.getDamageSource().getEntity() instanceof LivingEntity livingEntity) {
                        if (livingEntity.getMainHandItem().getItem() instanceof HuntersBowItem) {
                            if (event.getDamageSource().getDirectEntity() instanceof AbstractArrow) {
                                if (event.getEntity() instanceof Animal) {
                                    event.setLootingLevel(event.getLootingLevel() + 4);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public static void emptyClickEvents(PlayerInteractEvent.LeftClickEmpty event) {
        Player player = event.getEntity();
        ItemStack itemStack = event.getItemStack();
        if (itemStack.getItem() instanceof DeathScytheItem) {
            DeathScytheItem.emptyClick(itemStack);
        } else if (itemStack.getItem() instanceof BladeOfEnderItem && !player.getCooldowns().isOnCooldown(itemStack.getItem())) {
            BladeOfEnderItem.emptyClick(itemStack);
        }
    }

    public static InteractionResult playerAttackEvents(Player player, Level level, InteractionHand hand, Entity entity, @Nullable EntityHitResult hitResult) {
        ItemStack itemStack = player.getMainHandItem();
        if (itemStack.getItem() instanceof DeathScytheItem) {
            DeathScytheItem.entityClick(player, player.level);
        } else if (itemStack.getItem() instanceof BladeOfEnderItem) {
            BladeOfEnderItem.entityClick(player, player.level);
        }
        return InteractionResult.PASS;
    }

    public static InteractionResult interactEntityEvents(Player player, Level level, InteractionHand hand, Entity entity, @Nullable EntityHitResult hitResult) {
        ItemStack itemStack = player.getItemInHand(hand);
        Item item = itemStack.getItem();
        InteractionResult result = InteractionResult.PASS;
        if (item instanceof ReviveServantItem) {
            if (SEHelper.isOnCooldown(player, itemStack)) {
                result = InteractionResult.FAIL;
            }
        }

        return result;
    }

    public static InteractionResultHolder<ItemStack> generalInteractEvents(Player player, Level level, InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);
        if (itemStack.is(Items.GLASS_BOTTLE)) {
            InteractionResultHolder<ItemStack> result = ItemHelper.getVoidBottle(player, level, hand);
            if (result.getResult().consumesAction()) {
                return result;
            }
        }
        return InteractionResultHolder.pass(itemStack);
    }

    public static void dropEvents(LivingDropsEvent event){
        if (event.getEntity() != null) {
            Entity attacker = event.getSource().getEntity();
            LivingEntity victim = event.getEntity();
            if (!event.getSource().is(ModDamageSource.DISMISSED)) {
                Player player = MobUtil.getPlayerFromEntity(attacker);
                if (player == null) {
                    if (victim.lastHurtByPlayer != null) {
                        player = victim.lastHurtByPlayer;
                    }
                }
                if (player != null) {
                    if (!victim.level.isClientSide) {
                        if (victim instanceof Mob && !victim.getType().is(ModTags.EntityTypes.UNWRECKABLE)) {
                            if (CuriosFinder.hasCurio(player, ModItems.RING_OF_WRECKING)) {
                                boolean flag = event.getDrops().stream().anyMatch(itemEntity -> ItemHelper.isWreckable(itemEntity.getItem()));
                                if (flag) {
                                    if (!victim.isSilent()) {
                                        victim.playSound(SoundEvents.ITEM_BREAK, 1.0F, 0.8F + victim.getRandom().nextFloat() * 0.4F);
                                    }
                                }
                                event.getDrops().removeIf(itemEntity -> ItemHelper.isWreckable(itemEntity.getItem()));
                            }
                        }
                    }
                }
            }
        }
    }
}
