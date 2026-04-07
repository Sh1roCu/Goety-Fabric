package com.Polarice3.Goety.common.items.equipment;

import cn.sh1rocu.goety.api.extension.IEnchantment;
import com.Polarice3.Goety.client.particles.SmashParticleOption;
import com.Polarice3.Goety.common.enchantments.ModEnchantments;
import com.Polarice3.Goety.config.ItemConfig;
import com.Polarice3.Goety.init.ModSounds;
import com.Polarice3.Goety.utils.BlockFinder;
import com.Polarice3.Goety.utils.ColorUtil;
import com.Polarice3.Goety.utils.MobUtil;
import com.Polarice3.Goety.utils.ServerParticleUtil;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.SweepingEdgeEnchantment;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Objects;

public class HammerItem extends TieredItem implements Vanishable, IEnchantment {
    private static float initialDamage = ItemConfig.HammerBaseDamage.get().floatValue();
    private final Multimap<Attribute, AttributeModifier> hammerAttributes;
    protected final float speed;

    public HammerItem(Tier itemTier) {
        super(itemTier, new Properties().rarity(Rarity.UNCOMMON).durability(ItemConfig.HammerDurability.get()));
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        initialDamage = ItemConfig.HammerBaseDamage.get().floatValue() + itemTier.getAttackDamageBonus();
        double attackSpeed = 4.0D - ItemConfig.HammerAttackSpeed.get();
        this.speed = itemTier.getSpeed() - 2.0F;
        builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Tool modifier", initialDamage - 1.0D, AttributeModifier.Operation.ADDITION));
        builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Tool modifier", -attackSpeed, AttributeModifier.Operation.ADDITION));
        this.hammerAttributes = builder.build();
    }

    public HammerItem() {
        this(Tiers.IRON);
    }

    public static float getInitialDamage() {
        return initialDamage;
    }

    @Override
    public boolean hurtEnemy(ItemStack pStack, LivingEntity pTarget, LivingEntity pAttacker) {
        pStack.hurtAndBreak(1, pAttacker, (p_220045_0_) ->
                p_220045_0_.broadcastBreakEvent(EquipmentSlot.MAINHAND));
        if (pAttacker instanceof Player player) {
            float f2 = player.getAttackStrengthScale(0.5F);
            if (f2 > 0.9F) {
                this.attackMobs(pTarget, player, pStack);
                this.smash(pStack, pTarget, player);
            }
        }
        return true;
    }

    public void smash(ItemStack pStack, LivingEntity pTarget, Player player) {
        player.level.playSound(null, player.getX(), player.getY(), player.getZ(), ModSounds.HAMMER_SWING, player.getSoundSource(), 1.0F, 1.0F);
        if (pTarget.onGround()) {
            player.level.playSound(null, pTarget.getX(), pTarget.getY(), pTarget.getZ(), ModSounds.DIRT_DEBRIS, player.getSoundSource(), 1.0F, 1.0F);
        }
        if (player.level instanceof ServerLevel serverLevel) {
            BlockPos blockPos = BlockPos.containing(pTarget.getX(), pTarget.getY() - 1.0F, pTarget.getZ());
            BlockParticleOption option = new BlockParticleOption(ParticleTypes.BLOCK, serverLevel.getBlockState(blockPos));
            float area = 1.75F;
            area += EnchantmentHelper.getItemEnchantmentLevel(ModEnchantments.RADIUS, pStack);
            for (int i = 0; i < 8; ++i) {
                ServerParticleUtil.circularParticles(serverLevel, option, pTarget.getX(), pTarget.getY() + 0.25D, pTarget.getZ(), area);
            }
            int color = serverLevel.getBlockState(blockPos).getMapColor(serverLevel, blockPos).col;
            ColorUtil colorUtil = color == 0 ? ColorUtil.WHITE : new ColorUtil(color);
            serverLevel.sendParticles(new SmashParticleOption(colorUtil, area * 2, 5), pTarget.getX(), pTarget.getY() + 0.25D, pTarget.getZ(), 1, 0, 0, 0, 0);
        }
    }

    @Override
    public InteractionResult useOn(UseOnContext p_41427_) {
        Level level = p_41427_.getLevel();
        BlockPos blockpos = p_41427_.getClickedPos();
        Player player = p_41427_.getPlayer();
        BlockState blockstate = level.getBlockState(blockpos);
        if (player != null) {
            ItemStack itemStack = p_41427_.getItemInHand();
            if (blockstate.is(Blocks.IRON_BLOCK/*Tags.Blocks.STORAGE_BLOCKS_IRON*/)) {
                itemStack.hurtAndBreak(5, player, (p_220045_0_) ->
                        p_220045_0_.broadcastBreakEvent(EquipmentSlot.MAINHAND));
                level.setBlockAndUpdate(blockpos, Blocks.DAMAGED_ANVIL.defaultBlockState());
                level.scheduleTick(blockpos, Blocks.DAMAGED_ANVIL, 2);
                return InteractionResult.SUCCESS;
            }
        }
        return super.useOn(p_41427_);
    }

    public boolean getMineBlocks(Level pLevel, BlockState pState, BlockPos pPos) {
        return pState.is(BlockTags.MINEABLE_WITH_PICKAXE)
                && pState.getDestroySpeed(pLevel, pPos) > -1.0F;
    }

    @Override
    public float getDestroySpeed(ItemStack pStack, BlockState pState) {
        return pState.is(BlockTags.MINEABLE_WITH_PICKAXE) ? this.speed : 1.0F;
    }

    @Override
    public boolean mineBlock(ItemStack pStack, Level pLevel, BlockState pState, BlockPos pPos, LivingEntity pEntityLiving) {
        if (pState.getDestroySpeed(pLevel, pPos) != 0.0F) {
            pStack.hurtAndBreak(this.getMineBlocks(pLevel, pState, pPos) ? 1 : 2, pEntityLiving, (p_220044_0_) ->
                    p_220044_0_.broadcastBreakEvent(EquipmentSlot.MAINHAND));
        }
        if (this.getMineBlocks(pLevel, pState, pPos)) {
            pLevel.playSound(null, pPos.getX(), pPos.getY(), pPos.getZ(), ModSounds.DIRT_DEBRIS, pEntityLiving.getSoundSource(), 1.0F, 1.0F);
            for (BlockPos blockPos : BlockFinder.multiBlockBreak(pEntityLiving, pPos, 1, 1, 1)) {
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

    public void attackMobs(LivingEntity pTarget, Player pPlayer, ItemStack pStack) {
        float f = (float) pPlayer.getAttributeValue(Attributes.ATTACK_DAMAGE);
        float f1 = EnchantmentHelper.getDamageBonus(pPlayer.getMainHandItem(), pTarget.getMobType());
        int j = EnchantmentHelper.getFireAspect(pPlayer);
        double area = 1.75D;
        area += EnchantmentHelper.getItemEnchantmentLevel(ModEnchantments.RADIUS, pStack);
        for (LivingEntity livingentity : pPlayer.level.getEntitiesOfClass(LivingEntity.class, pTarget.getBoundingBox().inflate(area, 0.25D, area))) {
            if (livingentity != pPlayer && livingentity != pTarget && !MobUtil.areAllies(pPlayer, livingentity) && (!(livingentity instanceof ArmorStand) || !((ArmorStand) livingentity).isMarker()) && livingentity != pPlayer.getVehicle()) {
                livingentity.knockback(0.4F, Mth.sin(pPlayer.getYRot() * ((float) Math.PI / 180F)), -Mth.cos(pPlayer.getYRot() * ((float) Math.PI / 180F)));
                if (livingentity.hurt(pPlayer.damageSources().playerAttack(pPlayer), f + f1)) {
                    if (j > 0) {
                        livingentity.setSecondsOnFire(j * 4);
                    }
                    EnchantmentHelper.doPostHurtEffects(livingentity, pPlayer);
                    EnchantmentHelper.doPostDamageEffects(pPlayer, livingentity);
                }
            }
        }

        pPlayer.level.playSound(null, pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(), ModSounds.HAMMER_IMPACT, pPlayer.getSoundSource(), 1.0F, 1.0F);
    }

    @Override
    public boolean isCorrectToolForDrops(BlockState pBlock) {
        return pBlock.is(BlockTags.MINEABLE_WITH_PICKAXE);
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        ResourceLocation enchantmentId = BuiltInRegistries.ENCHANTMENT.getKey(enchantment);
        return (enchantment.category == EnchantmentCategory.WEAPON
                || enchantment.category == EnchantmentCategory.DIGGER
                || enchantment == ModEnchantments.RADIUS
                || Objects.equals(enchantmentId, new ResourceLocation("vanillatweaks", "siphon"))
                || enchantment.category.canEnchant(stack.getItem())
                && !(enchantment instanceof SweepingEdgeEnchantment));
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot equipmentSlot) {
        return equipmentSlot == EquipmentSlot.MAINHAND ? this.hammerAttributes : super.getDefaultAttributeModifiers(equipmentSlot);
    }
}
