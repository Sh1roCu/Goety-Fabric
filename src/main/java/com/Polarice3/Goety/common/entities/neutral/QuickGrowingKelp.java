package com.Polarice3.Goety.common.entities.neutral;

import com.Polarice3.Goety.init.ModSounds;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;

public class QuickGrowingKelp extends QuickGrowingVine {
    public QuickGrowingKelp(EntityType<? extends Owned> type, Level worldIn) {
        super(type, worldIn);
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource p_21239_) {
        return ModSounds.QUICK_GROWING_KELP_HURT;
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return ModSounds.QUICK_GROWING_KELP_DEATH;
    }

    @Override
    protected SoundEvent getBurstSound() {
        return ModSounds.QUICK_GROWING_KELP_BURST;
    }

    @Override
    protected SoundEvent getBurrowSound() {
        return ModSounds.QUICK_GROWING_KELP_BURROW;
    }

    @Override
    public boolean isAquatic() {
        return true;
    }
}
