package com.Polarice3.Goety.common.effects;

import com.Polarice3.Goety.client.particles.ModParticleTypes;
import net.fabricmc.fabric.api.tag.convention.v1.ConventionalEntityTypeTags;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class DoomEffect extends GoetyBaseEffect {
    public DoomEffect() {
        super(MobEffectCategory.HARMFUL, 0);
    }

    @Override
    public void applyEffectTick(LivingEntity affected, int amplifier) {
        if (affected.level instanceof ServerLevel serverLevel) {
            if (affected.canChangeDimensions()
                    && !affected.getType().is(ConventionalEntityTypeTags.BOSSES)
                    && !affected.isDeadOrDying()) {
                int i1 = affected.isInvisible() ? 15 : 4;
                int j = 1;
                if (affected.getRandom().nextInt(i1 * j) == 0) {
                    serverLevel.sendParticles(ModParticleTypes.DOOM, affected.getRandomX(0.5D), affected.getRandomY(), affected.getRandomZ(0.5D), 1, 0.0D, 0.5D, 0.0D, 0);
                }
            }
        }
    }
}
