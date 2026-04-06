package com.Polarice3.Goety.common.events;

import cn.sh1rocu.goety.api.event.LivingTickEvent;
import com.Polarice3.Goety.common.entities.hostile.cultists.Crone;
import com.Polarice3.Goety.common.entities.hostile.cultists.Cultist;
import com.Polarice3.Goety.common.entities.hostile.cultists.Maverick;
import com.Polarice3.Goety.init.ModSounds;
import com.Polarice3.Goety.init.ModTags;
import com.Polarice3.Goety.utils.CuriosFinder;
import com.Polarice3.Goety.utils.WitchBarterHelper;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Witch;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.raid.Raider;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import org.jetbrains.annotations.Nullable;

public class WitchBarterEvents {

    public static void livingEffects(LivingTickEvent event) {
        LivingEntity livingEntity = event.getEntity();
        if (livingEntity != null && livingEntity.isAlive()) {
            if (livingEntity instanceof Raider raider) {
                if (raider instanceof Cultist || raider instanceof Witch) {
                    if (WitchBarterHelper.getTimer(raider) > 0) {
                        WitchBarterHelper.decreaseTimer(raider);
                    }
                }
            }
        }
    }

    public static InteractionResult interactEntityEvent(Player player, Level level, InteractionHand hand, Entity target, @Nullable EntityHitResult hitResult) {
        if (hitResult == null) return InteractionResult.PASS;
        InteractionResult result = InteractionResult.PASS;
        if (!level.isClientSide) {
            if (CuriosFinder.isWitchFriendly(player)) {
                if (target instanceof Raider witch) {
                    if (witch instanceof Witch || witch instanceof Cultist cultist && cultist.isBarterable()) {
                        if (!witch.isAggressive()) {
                            if (WitchBarterHelper.getTimer(witch) <= 0) {
                                if (hand == InteractionHand.MAIN_HAND) {
                                    ItemStack itemStack = player.getItemInHand(hand);
                                    boolean maverick = witch instanceof Maverick && witch.getOffhandItem().isEmpty();
                                    if ((witch.getMainHandItem().isEmpty() || maverick) && (itemStack.is(ModTags.Items.WITCH_CURRENCY) || itemStack.is(ModTags.Items.WITCH_BETTER_CURRENCY))) {
                                        // event.setCanceled(true);
                                        // event.setCancellationResult(InteractionResult.SUCCESS);
                                        result = InteractionResult.FAIL;
                                        if (witch instanceof Crone) {
                                            witch.playSound(ModSounds.CRONE_AMBIENT);
                                        } else {
                                            witch.playSound(witch.getCelebrateSound());
                                        }
                                        ItemStack itemstack1;
                                        if (player.isCreative()) {
                                            itemstack1 = itemStack;
                                        } else {
                                            itemstack1 = itemStack.split(1);
                                        }
                                        if (witch instanceof Maverick) {
                                            witch.setItemSlot(EquipmentSlot.OFFHAND, itemstack1);
                                        } else {
                                            witch.setItemSlot(EquipmentSlot.MAINHAND, itemstack1);
                                        }
                                        WitchBarterHelper.setTrader(witch, player);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        return result;
    }
}
