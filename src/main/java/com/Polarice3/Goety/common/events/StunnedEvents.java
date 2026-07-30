package com.Polarice3.Goety.common.events;

import cn.sh1rocu.goety.api.event.*;
import com.Polarice3.Goety.common.effects.GoetyEffects;
import com.Polarice3.Goety.common.network.ModNetwork;
import com.Polarice3.Goety.common.network.server.SRemoveEffectPacket;
import com.Polarice3.Goety.init.ModTags;
import com.Polarice3.Goety.utils.SEHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;

import javax.annotation.Nullable;

public class StunnedEvents {

    public static boolean isStunned(@Nullable LivingEntity entity) {
        return entity != null && entity.isAlive() && (entity.hasEffect(GoetyEffects.STUNNED)
                || (entity instanceof Player player && SEHelper.hasCamera(player)));
    }

    private static void cancelEvent(LivingEvent event) {
        if (event instanceof ICancellableEvent cancellableEvent && isStunned(event.getEntity())) {
            cancellableEvent.setCanceled(true);
        }
    }

    private static InteractionResult cancelFabricInteractEvent(LivingEntity entity) {
        if (isStunned(entity)) {
            return InteractionResult.FAIL;
        }

        return InteractionResult.PASS;
    }

    public static InteractionResult cancelPlayerAttack(Player player, Level world, InteractionHand hand, Entity entity, @org.jetbrains.annotations.Nullable EntityHitResult hitResult) {
        return cancelFabricInteractEvent(player);
    }

    public static InteractionResult cancelBreakSpeed(Player player, Level world, InteractionHand hand, BlockPos pos, Direction direction) {
        return cancelFabricInteractEvent(player);
    }

    public static InteractionResult cancelActivateBlock(Player player, Level world, InteractionHand hand, BlockHitResult hitResult) {
        return cancelFabricInteractEvent(player);
    }

    public static InteractionResult cancelInteract(Player player, Level world, InteractionHand hand, Entity entity, @org.jetbrains.annotations.Nullable EntityHitResult hitResult) {
        return cancelFabricInteractEvent(player);
    }

    public static void cancelUsingItem(LivingEntityUseItemEvent.Start event) {
        cancelEvent(event);
    }

    public static void cancelTickUsingItem(LivingEntityUseItemEvent.Tick event) {
        cancelEvent(event);
    }

    public static InteractionResultHolder<ItemStack> cancelPlayerUseItem(Player player, Level world, InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);
        if (isStunned(player)) {
            return InteractionResultHolder.fail(itemStack);
        }

        return InteractionResultHolder.pass(itemStack);
    }

    public static void onLivingTarget(LivingChangeTargetEvent event) {
        if (event.getEntity() instanceof Mob mob && isStunned(mob)) {
            if (event.getTargetType() == LivingChangeTargetEvent.LivingTargetType.MOB_TARGET) {
                event.setNewTarget(null);
            } else {
                event.setCanceled(true);
            }
        }
    }

    public static void onKnockback(LivingKnockBackEvent event) {
        if (event.getEntity().hasEffect(GoetyEffects.TANGLED)) {
            event.setCanceled(true);
        }
    }

    public static void potionApplicationEvents(MobEffectEvent.Applicable event) {
        if (event.getEffectInstance() == null) return;
        if (event.getEffectInstance().getEffect() == GoetyEffects.STUNNED
                || event.getEffectInstance().getEffect().getDescriptionId().contains("born_in_chaos_v1:stun")) {
            if (event.getEntity().getType().is(ModTags.EntityTypes.UNSTUNNABLE)) {
                event.setResult(MobEffectEvent.Result.DENY);
            }
        }
    }

    public static void onLivingDeath(LivingEntity entity, DamageSource damageSource) {
        if (!entity.level.isClientSide) {
            if (isStunned(entity)) {
                entity.removeEffect(GoetyEffects.STUNNED);
                entity.removeEffect(GoetyEffects.TANGLED);
                ModNetwork.sendToALL(entity.level.getServer(), SRemoveEffectPacket.ID, SRemoveEffectPacket.encode(entity.getId(), MobEffect.getId(GoetyEffects.STUNNED)));
                ModNetwork.sendToALL(entity.level.getServer(), SRemoveEffectPacket.ID, SRemoveEffectPacket.encode(entity.getId(), MobEffect.getId(GoetyEffects.TANGLED)));
            }
        }
    }
}
