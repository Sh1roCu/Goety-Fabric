package com.Polarice3.Goety.common.events.spell;

import com.Polarice3.Goety.api.magic.ISpell;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;

public class GoetyEventFactory {

    public static ISpell onStartSpell(LivingEntity livingEntity, ItemStack useItem, ISpell spell) {
        StartMagicEvent event = new StartMagicEvent(livingEntity, useItem, spell);
        StartMagicEvent.EVENT.invoker().post(event);
        if (event.isCanceled()) {
            return null;
        }
        return event.getSpell();
    }

    public static ISpell onCastingSpell(LivingEntity livingEntity, ItemStack useItem, ISpell spell, int castTime) {
        CastingMagicEvent event = new CastingMagicEvent(livingEntity, useItem, spell, castTime);
        CastingMagicEvent.EVENT.invoker().post(event);
        if (event.isCanceled()) {
            return null;
        }
        return event.getSpell();
    }

    public static ISpell onBlockBasedSpell(LevelAccessor level, BlockPos pos, BlockState state, ISpell spell, @Nullable Direction direction, LivingEntity caster) {
        BlockMagicEvent event = new BlockMagicEvent(level, pos, state, spell, direction, caster);
        BlockMagicEvent.EVENT.invoker().post(event);
        if (event.isCanceled()) {
            return null;
        }
        return event.getSpell();
    }

    public static ISpell onTouchBasedSpell(LivingEntity livingEntity, ItemStack useItem, ISpell spell) {
        TouchMagicEvent event = new TouchMagicEvent(livingEntity, useItem, spell);
        TouchMagicEvent.EVENT.invoker().post(event);
        if (event.isCanceled()) {
            return null;
        }
        return event.getSpell();
    }

    public static ISpell onCastSpell(LivingEntity livingEntity, ISpell spell) {
        CastMagicEvent event = new CastMagicEvent(livingEntity, spell);
        CastMagicEvent.EVENT.invoker().post(event);
        if (event.isCanceled()) {
            return null;
        }
        return event.getSpell();
    }

    public static ISpell onStopSpell(LivingEntity livingEntity, ItemStack useItem, ISpell spell, int castTime, int timeRemaining) {
        StopMagicEvent event = new StopMagicEvent(livingEntity, useItem, spell, castTime, timeRemaining);
        StopMagicEvent.EVENT.invoker().post(event);
        if (event.isCanceled()) {
            return null;
        }
        return event.getSpell();
    }

    public static int onSoulEnergyGain(Player player, int soulChange) {
        ChangeSoulEnergyEvent.Gain event = new ChangeSoulEnergyEvent.Gain(player, soulChange);
        ChangeSoulEnergyEvent.GAIN.invoker().post(event);
        if (event.isCanceled()) {
            return 0;
        }
        return event.getSoulChange();
    }

    public static int onSoulEnergyLoss(Player player, int soulChange) {
        ChangeSoulEnergyEvent.Loss event = new ChangeSoulEnergyEvent.Loss(player, soulChange);
        ChangeSoulEnergyEvent.LOSS.invoker().post(event);
        if (event.isCanceled()) {
            return 0;
        }
        return event.getSoulChange();
    }
}
