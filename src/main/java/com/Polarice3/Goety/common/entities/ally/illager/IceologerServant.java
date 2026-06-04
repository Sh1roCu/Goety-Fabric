package com.Polarice3.Goety.common.entities.ally.illager;

import com.Polarice3.Goety.common.entities.ModEntityType;
import com.Polarice3.Goety.common.entities.ai.AvoidTargetGoal;
import com.Polarice3.Goety.common.entities.ally.undead.bound.BoundIceologer;
import com.Polarice3.Goety.common.entities.neutral.Owned;
import com.Polarice3.Goety.common.items.ModItems;
import com.Polarice3.Goety.common.magic.spells.frost.IceChunkSpell;
import com.Polarice3.Goety.config.AttributesConfig;
import com.Polarice3.Goety.init.ModSounds;
import com.Polarice3.Goety.utils.CuriosFinder;
import com.Polarice3.Goety.utils.MathHelper;
import com.Polarice3.Goety.utils.MobUtil;
import com.Polarice3.Goety.utils.ModLootTables;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Objects;

public class IceologerServant extends SpellcasterIllagerServant {
    private static final EntityDataAccessor<Integer> ANIM_STATE = SynchedEntityData.defineId(IceologerServant.class, EntityDataSerializers.INT);
    public int spellCool;
    public AnimationState idleAnimationState = new AnimationState();
    public AnimationState walkAnimationState = new AnimationState();
    public AnimationState attackAnimationState = new AnimationState();

    public IceologerServant(EntityType<? extends Owned> type, Level worldIn) {
        super(type, worldIn);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(2, new AvoidTargetGoal<>(this, LivingEntity.class, 6.0F, 1.0D, 1.0D));
        this.goalSelector.addGoal(4, new ChunkSpellGoal(this));
    }

    @Override
    public void miscGoal() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(8, new RaiderWanderGoal<>(this, 0.6D));
        this.goalSelector.addGoal(9, new LookAtPlayerGoal(this, Player.class, 3.0F, 1.0F));
        this.goalSelector.addGoal(10, new LookAtPlayerGoal(this, Mob.class, 8.0F));
    }

    public static AttributeSupplier.Builder setCustomAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.35D)
                .add(Attributes.FOLLOW_RANGE, AttributesConfig.IceologerServantFollowRange.get())
                .add(Attributes.ARMOR, AttributesConfig.IceologerServantArmor.get())
                .add(Attributes.MAX_HEALTH, AttributesConfig.IceologerServantHealth.get());
    }

    @Override
    public void setConfigurableAttributes() {
        MobUtil.setBaseAttributes(this.getAttribute(Attributes.MAX_HEALTH), AttributesConfig.IceologerServantHealth.get());
        MobUtil.setBaseAttributes(this.getAttribute(Attributes.ARMOR), AttributesConfig.IceologerServantArmor.get());
        MobUtil.setBaseAttributes(this.getAttribute(Attributes.FOLLOW_RANGE), AttributesConfig.IceologerServantFollowRange.get());
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(ANIM_STATE, 0);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("SpellCool", this.spellCool);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        if (compound.contains("SpellCool")) {
            this.spellCool = compound.getInt("SpellCool");
        }
    }

    public void setAnimationState(String input) {
        this.setAnimationState(this.getAnimationState(input));
    }

    public void setAnimationState(int id) {
        this.entityData.set(ANIM_STATE, id);
    }

    public int getAnimationState(String animation) {
        if (Objects.equals(animation, "idle")) {
            return 1;
        } else if (Objects.equals(animation, "attack")) {
            return 2;
        } else {
            return 0;
        }
    }

    public List<AnimationState> getAllAnimations() {
        List<AnimationState> list = new ArrayList<>();
        list.add(this.idleAnimationState);
        list.add(this.attackAnimationState);
        return list;
    }

    public void stopMostAnimation(AnimationState exception) {
        for (AnimationState state : this.getAllAnimations()) {
            if (state != exception) {
                state.stop();
            }
        }
    }

    public int getCurrentAnimation() {
        return this.entityData.get(ANIM_STATE);
    }

    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> accessor) {
        if (ANIM_STATE.equals(accessor)) {
            if (this.level.isClientSide) {
                switch (this.entityData.get(ANIM_STATE)) {
                    case 0:
                        break;
                    case 1:
                        this.idleAnimationState.startIfStopped(this.tickCount);
                        this.stopMostAnimation(this.idleAnimationState);
                        break;
                    case 2:
                        this.attackAnimationState.start(this.tickCount);
                        this.stopMostAnimation(this.attackAnimationState);
                        break;
                }
            }
        }
    }

    @Override
    public void die(DamageSource pCause) {
        if (!this.level.isClientSide) {
            if (this.getIdol() == null) {
                if (this.getTrueOwner() != null) {
                    if (CuriosFinder.hasNamelessSet(this.getTrueOwner())) {
                        BoundIceologer servant = this.convertTo(ModEntityType.BOUND_ICEOLOGER, true);
                        if (servant != null) {
                            servant.setTrueOwner(this.getTrueOwner());
                            if (!this.isSilent()) {
                                this.level.levelEvent(null, 1026, this.blockPosition(), 0);
                            }
                        }
                    }
                }
            }
        }
        super.die(pCause);
    }

    @Override
    public int xpReward() {
        return 10;
    }

    @Override
    protected ResourceLocation getDefaultLootTable() {
        if (this.isNatural()){
            return ModLootTables.NATURAL_ICEOLOGER;
        } else {
            return super.getDefaultLootTable();
        }
    }

    @Override
    public SoundEvent getCelebrateSound() {
        return ModSounds.ICEOLOGER_AMBIENT;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return ModSounds.ICEOLOGER_AMBIENT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return ModSounds.ICEOLOGER_DEATH;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource p_32654_) {
        return ModSounds.ICEOLOGER_HURT;
    }

    @Override
    protected SoundEvent getCastingSoundEvent() {
        return null;
    }

    @Override
    public void tick() {
        if (this.level.isClientSide()) {
            this.idleAnimationState.animateWhen(this.getCurrentAnimation() != this.getAnimationState("attack") && !this.walkAnimation.isMoving(), this.tickCount);
        }
        super.tick();
        if (!this.level.isClientSide) {
            if (this.spellCool > 0) {
                --this.spellCool;
            }
        }
    }

    public static class ChunkSpellGoal extends Goal {
        public IceologerServant iceologer;
        public boolean attack = false;
        public int spellTick;

        public ChunkSpellGoal(IceologerServant iceologer) {
            this.iceologer = iceologer;
            this.setFlags(EnumSet.of(Flag.TARGET, Flag.LOOK));
        }

        @Override
        public boolean canUse() {
            if (this.iceologer.getTarget() != null) {
                return this.iceologer.spellCool <= 0;
            }
            return false;
        }

        @Override
        public boolean canContinueToUse() {
            return this.iceologer.getTarget() != null && this.spellTick < 66;
        }

        @Override
        public void start() {
            super.start();
            this.attack = false;
            this.spellTick = 0;
        }

        @Override
        public void stop() {
            super.stop();
            this.attack = false;
            this.spellTick = 0;
            this.iceologer.spellCool = MathHelper.secondsToTicks(6);
            this.iceologer.setAnimationState("idle");
        }

        @Override
        public boolean requiresUpdateEveryTick() {
            return true;
        }

        @Override
        public void tick() {
            if (this.iceologer.getTarget() == null) {
                this.stop();
                return;
            }
            if (this.iceologer.distanceTo(this.iceologer.getTarget()) <= 7.0D || this.attack) {
                if (!this.attack) {
                    this.iceologer.playSound(ModSounds.ICEOLOGER_ATTACK);
                    if (this.iceologer.getTarget() != null) {
                        MobUtil.instaLook(this.iceologer, this.iceologer.getTarget());
                        new IceChunkSpell().mobSpellResult(this.iceologer, new ItemStack(ModItems.FROST_STAFF));
                    }
                    this.iceologer.setAnimationState("attack");
                    this.attack = true;
                }

                MobUtil.instaLook(this.iceologer, this.iceologer.getTarget());
                this.iceologer.getNavigation().stop();
                ++this.spellTick;
            } else {
                this.iceologer.getNavigation().moveTo(this.iceologer.getTarget(), 0.8D);
            }

        }
    }
}
