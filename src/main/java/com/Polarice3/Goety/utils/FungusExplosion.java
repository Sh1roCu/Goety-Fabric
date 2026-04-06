package com.Polarice3.Goety.utils;

import cn.sh1rocu.goety.api.event.ExplosionEvent;
import cn.sh1rocu.goety.mixin.accessor.ExplosionAccessor;
import com.Polarice3.Goety.client.particles.ModParticleTypes;
import com.Polarice3.Goety.init.ModSounds;
import com.google.common.collect.Sets;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.ProtectionEnchantment;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class FungusExplosion extends Explosion {

    public FungusExplosion(Level pLevel, @Nullable Entity pSource, double pToBlowX, double pToBlowY, double pToBlowZ, float pRadius, boolean pFire) {
        super(pLevel, pSource, null, null, pToBlowX, pToBlowY, pToBlowZ, pRadius, pFire, BlockInteraction.KEEP);
    }

    @Override
    public void explode() {
        Set<BlockPos> set = Sets.newHashSet();
        int i = 16;

        ExplosionAccessor accessor = (ExplosionAccessor) this;

        for (int j = 0; j < i; ++j) {
            for (int k = 0; k < i; ++k) {
                for (int l = 0; l < i; ++l) {
                    if (j == 0 || j == 15 || k == 0 || k == 15 || l == 0 || l == 15) {
                        double d0 = (float) j / 15.0F * 2.0F - 1.0F;
                        double d1 = (float) k / 15.0F * 2.0F - 1.0F;
                        double d2 = (float) l / 15.0F * 2.0F - 1.0F;
                        double d3 = Math.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
                        d0 = d0 / d3;
                        d1 = d1 / d3;
                        d2 = d2 / d3;
                        float f = accessor.goety$getRadius() * (0.7F + accessor.goety$getLevel().random.nextFloat() * 0.6F);
                        double d4 = accessor.goety$getX();
                        double d6 = accessor.goety$getY();
                        double d8 = accessor.goety$getZ();

                        for (float f1 = 0.3F; f > 0.0F; f -= 0.22500001F) {
                            BlockPos blockpos = BlockPos.containing(d4, d6, d8);
                            BlockState blockstate = accessor.goety$getLevel().getBlockState(blockpos);
                            FluidState fluidstate = accessor.goety$getLevel().getFluidState(blockpos);
                            Optional<Float> optional = accessor.goety$getDamageCalculator().getBlockExplosionResistance(this, accessor.goety$getLevel(), blockpos, blockstate, fluidstate);
                            if (optional.isPresent()) {
                                f -= (optional.get() + 0.3F) * 0.3F;
                            }

                            if (f > 0.0F && accessor.goety$getDamageCalculator().shouldBlockExplode(this, accessor.goety$getLevel(), blockpos, blockstate, f)) {
                                set.add(blockpos);
                            }

                            d4 += d0 * (double) 0.3F;
                            d6 += d1 * (double) 0.3F;
                            d8 += d2 * (double) 0.3F;
                        }
                    }
                }
            }
        }

        this.getToBlow().addAll(set);
        float f2 = accessor.goety$getRadius() * 2.0F;
        int k1 = Mth.floor(accessor.goety$getX() - (double) f2 - 1.0D);
        int l1 = Mth.floor(accessor.goety$getX() + (double) f2 + 1.0D);
        int i2 = Mth.floor(accessor.goety$getY() - (double) f2 - 1.0D);
        int i1 = Mth.floor(accessor.goety$getY() + (double) f2 + 1.0D);
        int j2 = Mth.floor(accessor.goety$getZ() - (double) f2 - 1.0D);
        int j1 = Mth.floor(accessor.goety$getZ() + (double) f2 + 1.0D);
        List<Entity> list = accessor.goety$getLevel().getEntities(this.getDirectSourceEntity(), new AABB(k1, i2, j2, l1, i1, j1));
        ExplosionEvent.DETONATE.invoker().post(accessor.goety$getLevel(), this, list, f2);
        Vec3 vector3d = new Vec3(accessor.goety$getX(), accessor.goety$getY(), accessor.goety$getZ());

        for (Entity entity : list) {
            if (entity instanceof LivingEntity) {
                if (!entity.ignoreExplosion()) {
                    double d12 = Mth.sqrt((float) entity.distanceToSqr(vector3d)) / f2;
                    if (d12 <= 1.0D) {
                        double d5 = entity.getX() - accessor.goety$getX();
                        double d7 = entity.getEyeY() - accessor.goety$getY();
                        double d9 = entity.getZ() - accessor.goety$getZ();
                        double d13 = Mth.sqrt((float) (d5 * d5 + d7 * d7 + d9 * d9));
                        if (d13 != 0.0D) {
                            d5 = d5 / d13;
                            d7 = d7 / d13;
                            d9 = d9 / d13;
                            double d14 = getSeenPercent(vector3d, entity);
                            double d10 = (1.0D - d12) * d14;
                            if (this.getIndirectSourceEntity() != null) {
                                entity.hurt(entity.damageSources().explosion(this.getDirectSourceEntity(), this.getIndirectSourceEntity()), (float) ((int) ((d10 * d10 + d10) / 2.0D * 7.0D * (double) f2 + 1.0D)));
                            } else {
                                entity.hurt(this.getDamageSource(), (float) ((int) ((d10 * d10 + d10) / 2.0D * 7.0D * (double) f2 + 1.0D)));
                            }
                            double d11;
                            d11 = ProtectionEnchantment.getExplosionKnockbackAfterDampener((LivingEntity) entity, d10);

                            entity.setDeltaMovement(entity.getDeltaMovement().add(d5 * d11, d7 * d11, d9 * d11));
                            if (entity instanceof Player player) {
                                if (!player.isSpectator() && (!player.isCreative() || !player.getAbilities().flying)) {
                                    this.getHitPlayers().put(player, new Vec3(d5 * d10, d7 * d10, d9 * d10));
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public void finalizeExplosion(boolean pSpawnParticles) {
        ExplosionAccessor accessor = (ExplosionAccessor) this;

        if (accessor.goety$getLevel().isClientSide) {
            accessor.goety$getLevel().playLocalSound(accessor.goety$getX(), accessor.goety$getY(), accessor.goety$getZ(), ModSounds.BLAST_FUNGUS_EXPLODE, SoundSource.BLOCKS, 4.0F, (1.0F + (accessor.goety$getLevel().random.nextFloat() - accessor.goety$getLevel().random.nextFloat()) * 0.2F) * 0.7F, false);
        }

        if (pSpawnParticles) {
            if (accessor.goety$getRadius() > 2.0F) {
                accessor.goety$getLevel().addParticle(ModParticleTypes.FUNGUS_EXPLOSION_EMITTER, accessor.goety$getX(), accessor.goety$getY(), accessor.goety$getZ(), 1.0D, 0.0D, 0.0D);
            } else {
                accessor.goety$getLevel().addParticle(ModParticleTypes.FUNGUS_EXPLOSION, accessor.goety$getX(), accessor.goety$getY(), accessor.goety$getZ(), 1.0D, 0.0D, 0.0D);
            }
        }

        if (accessor.goety$isFire()) {
            for (BlockPos blockpos2 : this.getToBlow()) {
                if (accessor.goety$getRandom().nextInt(3) == 0 && accessor.goety$getLevel().getBlockState(blockpos2).isAir() && accessor.goety$getLevel().getBlockState(blockpos2.below()).isSolidRender(accessor.goety$getLevel(), blockpos2.below())) {
                    accessor.goety$getLevel().setBlockAndUpdate(blockpos2, BaseFireBlock.getState(accessor.goety$getLevel(), blockpos2));
                }
            }
        }
    }
}
