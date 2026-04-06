package com.Polarice3.Goety.common.entities.projectiles;

import cn.sh1rocu.goety.api.extension.IEntityAdditionalSpawnData;
import com.Polarice3.Goety.client.particles.ModParticleTypes;
import com.Polarice3.Goety.common.entities.ModEntityType;
import com.Polarice3.Goety.init.ModSounds;
import com.Polarice3.Goety.utils.ColorUtil;
import com.Polarice3.Goety.utils.MathHelper;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class EnderGoo extends SpellHurtingProjectile {

    public EnderGoo(EntityType<? extends AbstractHurtingProjectile> p_36833_, Level p_36834_) {
        super(p_36833_, p_36834_);
    }

    public EnderGoo(double p_36818_, double p_36819_, double p_36820_, double p_36821_, double p_36822_, double p_36823_, Level p_36824_) {
        super(ModEntityType.ENDER_GOO, p_36818_, p_36819_, p_36820_, p_36821_, p_36822_, p_36823_, p_36824_);
    }

    public EnderGoo(LivingEntity p_36827_, double p_36828_, double p_36829_, double p_36830_, Level p_36831_) {
        super(ModEntityType.ENDER_GOO, p_36827_, p_36828_, p_36829_, p_36830_, p_36831_);
    }

    @Override
    protected float getInertia() {
        return 0.99F;
    }

    @Override
    public void trailParticle() {
        Entity entity = this.getOwner();
        if (this.level.isClientSide || (entity == null || !entity.isRemoved()) && this.level.hasChunkAt(this.blockPosition())) {
            Vec3 vec3 = this.getDeltaMovement();
            double d0 = this.getX() - vec3.x;
            double d1 = this.getY() - vec3.y;
            double d2 = this.getZ() - vec3.z;
            ColorUtil colorUtil = new ColorUtil(0xf169e9);
            if (this.level.random.nextFloat() <= 0.75F) {
                this.level.addParticle(ModParticleTypes.TRAIL, d0, d1 + 0.15D, d2, colorUtil.red(), colorUtil.green(), colorUtil.blue());
            }
        }
    }

    @Override
    protected void onHitEntity(EntityHitResult p_37626_) {
        super.onHitEntity(p_37626_);
        if (!this.level.isClientSide) {
            float baseDamage = 3.0F;
            Entity entity = p_37626_.getEntity();
            Entity entity1 = this.getOwner();
            boolean flag;
            if (entity1 instanceof LivingEntity livingentity) {
                if (livingentity instanceof Mob mob) {
                    if (mob.getAttribute(Attributes.ATTACK_DAMAGE) != null && mob.getAttributeValue(Attributes.ATTACK_DAMAGE) > 0) {
                        baseDamage = (float) mob.getAttributeValue(Attributes.ATTACK_DAMAGE);
                    }
                }
                baseDamage += this.getExtraDamage();
                flag = entity.hurt(entity.damageSources().mobProjectile(this, livingentity), baseDamage);
                if (flag) {
                    if (entity.isAlive()) {
                        this.doEnchantDamageEffects(livingentity, entity);
                    }
                }
            } else {
                entity.hurt(entity.damageSources().magic(), baseDamage);
            }

        }
    }

    @Override
    protected void onHit(HitResult p_37628_) {
        super.onHit(p_37628_);
        this.playSound(ModSounds.ENDER_GOO_IMPACT);
        if (this.level instanceof ServerLevel serverLevel) {
            for (int i = 0; i <= 16; ++i) {
                ColorUtil colorUtil = new ColorUtil(0xeb58e5);
                serverLevel.sendParticles(ModParticleTypes.GOO_STAIN, this.getRandomX(0.5D), this.getY(), this.getRandomZ(0.5D), 0, colorUtil.red(), colorUtil.green(), colorUtil.blue(), 1.0F);
            }
            this.discard();
        }

    }

    @Override
    public void tick() {
        super.tick();
        if (this.tickCount >= MathHelper.secondsToTicks(10)) {
            this.discard();
        }
    }

    @Override
    protected ParticleOptions getTrailParticle() {
        return ModParticleTypes.NONE;
    }

    @Override
    public boolean isPickable() {
        return false;
    }

    @Override
    public boolean hurt(DamageSource p_37616_, float p_37617_) {
        return false;
    }

    @Override
    protected boolean shouldBurn() {
        return false;
    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return IEntityAdditionalSpawnData.getEntitySpawningPacket(this);
    }
}
