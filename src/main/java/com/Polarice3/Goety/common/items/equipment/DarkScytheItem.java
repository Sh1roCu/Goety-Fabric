package com.Polarice3.Goety.common.items.equipment;

import cn.sh1rocu.goety.api.extension.IEnchantment;
import com.Polarice3.Goety.common.enchantments.ModEnchantments;
import com.Polarice3.Goety.common.items.ModItems;
import com.Polarice3.Goety.common.items.ModTiers;
import com.Polarice3.Goety.config.ItemConfig;
import com.Polarice3.Goety.init.ModSounds;
import com.Polarice3.Goety.utils.BlockFinder;
import com.Polarice3.Goety.utils.ModUUIDUtil;
import com.Polarice3.Goety.utils.SEHelper;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.jamieswhiteshirt.reachentityattributes.ReachEntityAttributes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.OwnableEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Objects;

public class DarkScytheItem extends TieredItem implements Vanishable, IEnchantment {
    private static float initialDamage = ItemConfig.ScytheBaseDamage.get().floatValue();
    private final Multimap<Attribute, AttributeModifier> scytheAttributes;

    public DarkScytheItem(Tier itemTier, Properties properties) {
        super(itemTier, properties.rarity(Rarity.UNCOMMON).durability(itemTier.getUses()));
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        initialDamage = ItemConfig.ScytheBaseDamage.get().floatValue() + itemTier.getAttackDamageBonus();
        double attackSpeed = 4.0D - ItemConfig.ScytheAttackSpeed.get();
        builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Tool modifier", initialDamage - 1.0D, AttributeModifier.Operation.ADDITION));
        builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Tool modifier", -attackSpeed, AttributeModifier.Operation.ADDITION));
        builder.put(ReachEntityAttributes.ATTACK_RANGE, new AttributeModifier(ModUUIDUtil.createUUID("item.goety.scythe.reach"), "Tool modifier", 1.0F, AttributeModifier.Operation.ADDITION));
        this.scytheAttributes = builder.build();
    }

    public DarkScytheItem() {
        this(ModTiers.SPECIAL, new Properties());
    }

    public static float getInitialDamage() {
        return initialDamage;
    }

    public boolean getMineBlocks(Level pLevel, BlockState pState, BlockPos pPos) {
        if (pState.getDestroySpeed(pLevel, pPos) <= -1.0F) {
            return false;
        }
        return pState.is(BlockTags.MINEABLE_WITH_HOE) || pState.is(Blocks.COBWEB) || BlockFinder.isScytheBreak(pState);
    }

    @Override
    public float getDestroySpeed(ItemStack pStack, BlockState pState) {
        if (pState.is(Blocks.COBWEB)) {
            return 15.0F;
        }
        return pState.is(BlockTags.MINEABLE_WITH_HOE) ? 8.0F : 1.0F;
    }

    @Override
    public boolean hurtEnemy(ItemStack pStack, LivingEntity pTarget, LivingEntity pAttacker) {
        pStack.hurtAndBreak(1, pAttacker, (p_220045_0_) ->
                p_220045_0_.broadcastBreakEvent(EquipmentSlot.MAINHAND));
        if (pAttacker instanceof Player player) {
            this.attackMobs(pStack, pTarget, player);
        }
        return true;
    }

    @Override
    public boolean mineBlock(ItemStack pStack, Level pLevel, BlockState pState, BlockPos pPos, LivingEntity pEntityLiving) {
        if (pState.getDestroySpeed(pLevel, pPos) != 0.0F) {
            pStack.hurtAndBreak(this.getMineBlocks(pLevel, pState, pPos) ? 1 : 2, pEntityLiving, (p_220044_0_) ->
                    p_220044_0_.broadcastBreakEvent(EquipmentSlot.MAINHAND));
        }
        if (this.getMineBlocks(pLevel, pState, pPos)) {
            pLevel.playSound(null, pPos.getX(), pPos.getY(), pPos.getZ(), ModSounds.SCYTHE_HIT, pEntityLiving.getSoundSource(), 1.0F, 1.0F);
            for (BlockPos blockPos : BlockFinder.multiBlockBreak(pEntityLiving, pPos, 2, 2, 2)) {
                BlockState blockstate = pLevel.getBlockState(blockPos);
                if (this.getMineBlocks(pLevel, blockstate, blockPos)) {
                    if (BlockFinder.breakBlock(pLevel, blockPos, pStack, pEntityLiving)) {
                        if (blockstate.getDestroySpeed(pLevel, blockPos) != 0) {
                            pStack.hurtAndBreak(1, pEntityLiving, (p_220044_0_)
                                    -> p_220044_0_.broadcastBreakEvent(EquipmentSlot.MAINHAND));
                        }
                    }
                }
            }
        }

        return true;
    }

    public void attackMobs(ItemStack pStack, LivingEntity pTarget, Player pPlayer) {
        int enchantment = EnchantmentHelper.getItemEnchantmentLevel(ModEnchantments.SOUL_EATER, pStack);
        int soulEater = Mth.clamp(enchantment + 1, 1, 10);
        if (SEHelper.getSoulGiven(pTarget) > 0) {
            SEHelper.increaseSouls(pPlayer, ItemConfig.DarkScytheSouls.get() * soulEater);
        }

        float f = (float) pPlayer.getAttributeValue(Attributes.ATTACK_DAMAGE);
        float f1 = EnchantmentHelper.getDamageBonus(pPlayer.getMainHandItem(), pTarget.getMobType());
        float f2 = pPlayer.getAttackStrengthScale(0.5F);
        f = f * (0.2F + f2 * f2 * 0.8F);
        f1 = f1 * f2;
        f = f + f1;

        if (f > 0.5F || f1 > 0.5F) {
            float f3 = 1.0F + EnchantmentHelper.getSweepingDamageRatio(pPlayer) * f;
            int j = EnchantmentHelper.getFireAspect(pPlayer);
            double area = 1.0D;
            if (f2 > 0.9F) {
                area = 2.0D;
            }
            for (LivingEntity livingentity : pPlayer.level.getEntitiesOfClass(LivingEntity.class, pTarget.getBoundingBox().inflate(area, 0.25D, area))) {
                if (livingentity != pPlayer && livingentity != pTarget && !pPlayer.isAlliedTo(livingentity) && (!(livingentity instanceof ArmorStand) || !((ArmorStand) livingentity).isMarker()) && pPlayer.distanceToSqr(livingentity) < 16.0D && livingentity != pPlayer.getVehicle()) {
                    livingentity.knockback(0.4F, Mth.sin(pPlayer.getYRot() * ((float) Math.PI / 180F)), -Mth.cos(pPlayer.getYRot() * ((float) Math.PI / 180F)));
                    if (livingentity.hurt(livingentity.damageSources().playerAttack(pPlayer), f3)) {
                        if (j > 0) {
                            livingentity.setSecondsOnFire(j * 4);
                        }
                        pStack.hurtAndBreak(1, pPlayer, (p_220045_0_) ->
                                p_220045_0_.broadcastBreakEvent(EquipmentSlot.MAINHAND));
                        boolean giveSoul = false;
                        if (livingentity instanceof OwnableEntity owned) {
                            if (owned.getOwner() != pPlayer) {
                                giveSoul = true;
                            }
                        } else {
                            giveSoul = true;
                        }
                        if (giveSoul) {
                            if (SEHelper.getSoulGiven(livingentity) > 0) {
                                SEHelper.increaseSouls(pPlayer, ItemConfig.DarkScytheSouls.get() * soulEater);
                            }
                        }
                        EnchantmentHelper.doPostHurtEffects(livingentity, pPlayer);
                        EnchantmentHelper.doPostDamageEffects(pPlayer, livingentity);
                    }
                }
            }
        }

        pPlayer.level.playSound(null, pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(), ModSounds.SCYTHE_SWING, pPlayer.getSoundSource(), 1.0F, 1.0F);
        pPlayer.sweepAttack();
    }

    @Override
    public boolean allowNbtUpdateAnimation(Player player, InteractionHand hand, ItemStack oldStack, ItemStack newStack) {
        return super.allowNbtUpdateAnimation(player, hand, oldStack, newStack) && !oldStack.equals(newStack);
    }

    @Override
    public boolean isCorrectToolForDrops(BlockState pBlock) {
        return pBlock.is(BlockTags.MINEABLE_WITH_HOE) || pBlock.is(Blocks.COBWEB);
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        ResourceLocation enchantmentId = BuiltInRegistries.ENCHANTMENT.getKey(enchantment);
        return (enchantment.category == EnchantmentCategory.VANISHABLE
                || enchantment.category == EnchantmentCategory.WEAPON
                || enchantment.category == EnchantmentCategory.BREAKABLE
                || enchantment.category == EnchantmentCategory.DIGGER
                || enchantment == Enchantments.MOB_LOOTING
                || enchantment == Enchantments.BLOCK_FORTUNE
                || Objects.equals(enchantmentId, new ResourceLocation("vanillatweaks", "siphon")));
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot equipmentSlot) {
        return equipmentSlot == EquipmentSlot.MAINHAND ? this.scytheAttributes : super.getDefaultAttributeModifiers(equipmentSlot);
    }

    @Override
    public boolean isValidRepairItem(ItemStack pToRepair, ItemStack pRepair) {
        return pRepair.getItem() == ModItems.PALE_STEEL_INGOT || super.isValidRepairItem(pToRepair, pRepair);
    }
}
