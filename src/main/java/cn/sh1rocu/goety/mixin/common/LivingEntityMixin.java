package cn.sh1rocu.goety.mixin.common;

import cn.sh1rocu.goety.api.event.*;
import cn.sh1rocu.goety.api.mixin.interfaces.ICustomDrops;
import cn.sh1rocu.goety.util.forge.EventHooks;
import com.Polarice3.Goety.init.ModAttributes;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalIntRef;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity implements ICustomDrops {

    public LivingEntityMixin(EntityType<?> entityType, Level level) {
        super(entityType, level);
    }

    @Shadow
    protected int useItemRemaining;

    @Shadow
    public abstract int getUseItemRemainingTicks();

    @Shadow
    public abstract ItemStack getUseItem();

    @Shadow
    @Nullable
    public Player lastHurtByPlayer;

    @Shadow
    public int lastHurtByPlayerTime;

    @ModifyReturnValue(method = "createLivingAttributes", at = @At("RETURN"))
    private static AttributeSupplier.Builder goety$addGoetyAttribuites(AttributeSupplier.Builder original) {
        ModAttributes.ATTRIBUTES.forEach(original::add);
        return original;
    }

    // start1: from Artifacts(https://github.com/ochotonida/artifacts/blob/1.21.x/fabric/src/main/java/artifacts/fabric/mixin/attribute/swimspeed/LivingEntityMixin.java)
    @ModifyArg(method = "jumpInLiquid", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/phys/Vec3;add(DDD)Lnet/minecraft/world/phys/Vec3;"), index = 1)
    private double goety$increaseSwimUpSpeed(double y) {
        return goety$getIncreasedSwimSpeed(y);
    }

    @ModifyArg(method = "travel", allow = 1,
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;moveRelative(FLnet/minecraft/world/phys/Vec3;)V"),
            slice = @Slice(
                    from = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;isInWater()Z"),
                    to = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;isInLava()Z")
            )
    )
    private float goety$increaseSwimSpeed(float speed) {
        return (float) goety$getIncreasedSwimSpeed(speed);
    }

    @Unique
    private double goety$getIncreasedSwimSpeed(double speed) {
        // noinspection ConstantConditions
        return speed * ((LivingEntity) (Object) this).getAttributeValue(ModAttributes.SWIM_SPEED);
    }
    // end1

    @Inject(method = "tick", at = @At("HEAD"), cancellable = true)
    private void goety$tickEvent(CallbackInfo ci) {
        LivingTickEvent event = new LivingTickEvent((LivingEntity) (Object) this);
        LivingTickEvent.EVENT.invoker().post(event);
        if (event.isCanceled())
            ci.cancel();
    }

    @WrapOperation(method = "startUsingItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;getUseDuration()I"))
    private int goety$onStartUsingItem(ItemStack instance, Operation<Integer> original, @Share("old_duration") LocalIntRef oldDuration) {
        oldDuration.set(this.useItemRemaining);
        return EventHooks.onItemUseStart((LivingEntity) (Object) this, instance, original.call(instance));
    }

    @Inject(method = "startUsingItem", at = @At(value = "FIELD", target = "Lnet/minecraft/world/level/Level;isClientSide:Z"), cancellable = true)
    private void goety$cancelIfDurationLessThenZero(InteractionHand interactionHand, CallbackInfo ci, @Share("old_duration") LocalIntRef oldDuration) {
        if (this.useItemRemaining < 0) {
            this.useItemRemaining = oldDuration.get();
            ci.cancel();
        }
    }

    @ModifyVariable(method = "actuallyHurt", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;getDamageAfterArmorAbsorb(Lnet/minecraft/world/damagesource/DamageSource;F)F"), argsOnly = true)
    private float goety$hurtEvent(float original, DamageSource source) {
        LivingHurtEvent event = new LivingHurtEvent((LivingEntity) (Object) this, source, original);
        LivingHurtEvent.EVENT.invoker().post(event);
        if (event.isCanceled()) {
            return 0;
        }

        return event.getAmount();
    }

    @ModifyVariable(method = "actuallyHurt(Lnet/minecraft/world/damagesource/DamageSource;F)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;setAbsorptionAmount(F)V"), index = 2, argsOnly = true)
    private float goety$damageEvent(float damageAmount, DamageSource damageSource) {
        LivingDamageEvent event = new LivingDamageEvent((LivingEntity) (Object) this, damageSource, damageAmount);
        LivingDamageEvent.EVENT.invoker().post(event);
        if (event.isCanceled()) {
            return 0;
        } else {
            return event.getAmount();
        }
    }

    @Inject(method = "jumpFromGround", at = @At("TAIL"))
    public void goety$jumpEvent(CallbackInfo ci) {
        LivingJumpEvent.EVENT.invoker().post(new LivingJumpEvent((LivingEntity) (Object) this));
    }

    @Inject(method = "causeFallDamage(FFLnet/minecraft/world/damagesource/DamageSource;)Z", at = @At("HEAD"), cancellable = true)
    private void goety$causeFallDamage(float distance, float damageMultiplier, DamageSource damageSource, CallbackInfoReturnable<Boolean> cir, @Share("eventRef") LocalRef<LivingFallEvent> eventRef) {
        LivingFallEvent event = new LivingFallEvent((LivingEntity) (Object) this, distance, damageMultiplier);
        LivingFallEvent.EVENT.invoker().post(event);
        eventRef.set(event);
        if (event.isCanceled()) {
            cir.setReturnValue(false);
        }
    }

    @WrapOperation(method = "causeFallDamage(FFLnet/minecraft/world/damagesource/DamageSource;)Z", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;calculateFallDamage(FF)I"))
    private int goety$calculateFallDamage(LivingEntity livingEntity, float fallDistance, float multiplier, Operation<Integer> original, @Share("eventRef") LocalRef<LivingFallEvent> eventRef) {
        LivingFallEvent event = eventRef.get();
        if (event != null) {
            return original.call(livingEntity, event.getDistance(), event.getDamageMultiplier());
        }
        return original.call(livingEntity, fallDistance, multiplier);
    }

    @WrapWithCondition(method = "updateUsingItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;onUseTick(Lnet/minecraft/world/level/Level;Lnet/minecraft/world/entity/LivingEntity;I)V"))
    private boolean goety$onItemUseTick(ItemStack instance, Level level, LivingEntity livingEntity, int useItemRemainingTicks) {
        if (!instance.isEmpty()) {
            this.useItemRemaining = EventHooks.onItemUseTick(livingEntity, instance, useItemRemainingTicks);
            return this.getUseItemRemainingTicks() > 0;
        }
        return true;
    }

    @WrapOperation(method = "completeUsingItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;finishUsingItem(Lnet/minecraft/world/level/Level;Lnet/minecraft/world/entity/LivingEntity;)Lnet/minecraft/world/item/ItemStack;"))
    public ItemStack goety$onFinishUsing(ItemStack instance, Level level, LivingEntity livingEntity, Operation<ItemStack> original) {
        return EventHooks.onItemUseFinish((LivingEntity) (Object) this, this.getUseItem().copy(), getUseItemRemainingTicks(), original.call(instance, level, livingEntity));
    }

    @ModifyArgs(
            method = "dropAllDeathLoot",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/LivingEntity;dropCustomDeathLoot(Lnet/minecraft/world/damagesource/DamageSource;IZ)V"
            )
    )
    private void goety$$modifyLootingLevel(Args args) {
        DamageSource source = args.get(0);
        int originalLevel = args.get(1);
        LootingLevelEvent event = new LootingLevelEvent((LivingEntity) (Object) this, source, originalLevel);
        LootingLevelEvent.EVENT.invoker().post(event);
        args.set(1, event.getLootingLevel());
    }

    @Inject(method = "canBeAffected", at = @At("HEAD"), cancellable = true)
    private void goety$isEffectApplicable(MobEffectInstance effectInstance, CallbackInfoReturnable<Boolean> cir) {
        var event = new MobEffectEvent.Applicable((LivingEntity) (Object) this, effectInstance);
        MobEffectEvent.APPLICABLE.invoker().post(event);
        if (event.getResult() != MobEffectEvent.Result.DEFAULT)
            cir.setReturnValue(event.getResult() == MobEffectEvent.Result.ALLOW);
    }

    @Inject(method = "addEffect(Lnet/minecraft/world/effect/MobEffectInstance;Lnet/minecraft/world/entity/Entity;)Z", at = @At(value = "JUMP", opcode = Opcodes.IFNONNULL))
    private void goety$onEffectAdded(MobEffectInstance newEffect, Entity entity, CallbackInfoReturnable<Boolean> cir, @Local(index = 3) MobEffectInstance oldEffect) {
        var event = new MobEffectEvent.Added((LivingEntity) (Object) this, oldEffect, newEffect, entity);
        MobEffectEvent.ADDED.invoker().post(event);
    }

    @ModifyExpressionValue(method = "tickEffects", at = @At(value = "FIELD", target = "Lnet/minecraft/world/level/Level;isClientSide:Z", ordinal = 0))
    private boolean goety$onEffectExpired(boolean original, @Local(index = 3) MobEffectInstance effect) {
        var event = new MobEffectEvent.Expired((LivingEntity) (Object) this, effect);
        MobEffectEvent.EXPIRED.invoker().post(event);
        return !(!original && !event.isCanceled());
    }

    @Inject(method = "removeEffect", at = @At("HEAD"), cancellable = true)
    private void goety$onRemoveEffect(MobEffect effect, CallbackInfoReturnable<Boolean> cir) {
        var event = new MobEffectEvent.Remove((LivingEntity) (Object) this, effect);
        MobEffectEvent.REMOVE.invoker().post(event);
        if (event.isCanceled()) cir.setReturnValue(false);
    }

    @WrapWithCondition(method = "removeAllEffects", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;onEffectRemoved(Lnet/minecraft/world/effect/MobEffectInstance;)V"))
    private boolean goety$onRemoveAllEffectEvent(LivingEntity instance, MobEffectInstance effect, @Share("event") LocalRef<MobEffectEvent.Remove> eventRef) {
        var event = new MobEffectEvent.Remove(instance, effect);
        eventRef.set(event);
        MobEffectEvent.REMOVE.invoker().post(event);
        return !event.isCanceled();
    }

    @WrapWithCondition(method = "removeAllEffects", at = @At(value = "INVOKE", target = "Ljava/util/Iterator;remove()V"))
    private boolean goety$skipRemove(Iterator<MobEffectInstance> iterator, @Share("event") LocalRef<MobEffectEvent.Remove> eventRef) {
        return !eventRef.get().isCanceled();
    }

    @ModifyVariable(method = "heal(F)V", at = @At("HEAD"), argsOnly = true)
    private float goety$livingHealEvent(float heal) {
        LivingEntity entity = (LivingEntity) (Object) this;
        LivingHealEvent event = new LivingHealEvent(entity, heal);
        LivingHealEvent.EVENT.invoker().post(event);
        return event.isCanceled() ? 0f : heal;
    }

    @ModifyArg(method = "dropExperience", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/ExperienceOrb;award(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/phys/Vec3;I)V"))
    private int goety$expDropEvent(int original) {
        return EventHooks.getExperienceDrop((LivingEntity) (Object) this, this.lastHurtByPlayer, original);
    }

    @Unique
    private int goety$lootingLevel;

    @ModifyVariable(
            method = "dropAllDeathLoot",
            at = @At(
                    value = "FIELD",
                    target = "Lnet/minecraft/world/entity/LivingEntity;lastHurtByPlayerTime:I"
            )
    )
    private int goety$grabLootingLevel(int lootingLevel) {
        goety$lootingLevel = lootingLevel;
        return lootingLevel;
    }

    @Inject(method = "dropAllDeathLoot", at = @At("HEAD"))
    private void goety$startCapturingDrops(DamageSource damageSource, CallbackInfo ci) {
        goety$captureDrops(new ArrayList<>());
    }

    @Inject(method = "dropAllDeathLoot", at = @At("RETURN"))
    private void goety$dropCapturedDrops(DamageSource source, CallbackInfo ci) {
        Collection<ItemEntity> drops = this.goety$captureDrops(null);
        LivingDropsEvent event = new LivingDropsEvent((LivingEntity) (Object) this, source, drops, goety$lootingLevel, this.lastHurtByPlayerTime > 0);
        LivingDropsEvent.EVENT.invoker().post(event);
        if (!event.isCanceled())
            drops.forEach(e -> level().addFreshEntity(e));
    }

    @ModifyVariable(method = "knockback", at = @At("HEAD"), ordinal = 0, argsOnly = true)
    private double goety$modifyKnockbackStrength(double strength, double ogstrength, double xRatio, double zRatio, @Share("event") LocalRef<LivingKnockBackEvent> eventRef) {
        LivingKnockBackEvent event = new LivingKnockBackEvent((LivingEntity) (Object) this, (float) strength, xRatio, zRatio);
        LivingKnockBackEvent.EVENT.invoker().post(event);
        eventRef.set(event);
        if (!event.isCanceled() && event.getOriginalStrength() != event.getStrength()) {
            return event.getStrength();
        }
        return strength;
    }

    @ModifyVariable(method = "knockback", at = @At("HEAD"), ordinal = 1, argsOnly = true)
    private double goety$modifyRatioX(double ratioX, @Share("event") LocalRef<LivingKnockBackEvent> eventRef) {
        var event = eventRef.get();
        if (event.getOriginalRatioX() != event.getRatioX())
            return event.getRatioX();
        return ratioX;
    }

    @ModifyVariable(method = "knockback", at = @At("HEAD"), ordinal = 2, argsOnly = true)
    private double goety$modifyRatioZ(double ratioZ, @Share("event") LocalRef<LivingKnockBackEvent> eventRef) {
        var event = eventRef.get();
        if (event.getOriginalRatioZ() != event.getRatioZ())
            return event.getRatioZ();
        return ratioZ;
    }

    @Inject(method = "knockback", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;getAttributeValue(Lnet/minecraft/world/entity/ai/attributes/Attribute;)D"), cancellable = true)
    private void goety$shouldCancelKnockback(double strength, double xRatio, double zRatio, CallbackInfo ci, @Share("event") LocalRef<LivingKnockBackEvent> eventRef) {
        if (eventRef.get().isCanceled())
            ci.cancel();
    }

    @ModifyReturnValue(method = "getVisibilityPercent", at = @At("RETURN"))
    private double goety$livingVisibilityEvent(double original, @Nullable Entity pLookingEntity) {
        return EventHooks.getEntityVisibilityMultiplier((LivingEntity) (Object) this, pLookingEntity, original);
    }

    @WrapOperation(method = "checkTotemDeathProtection", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;is(Lnet/minecraft/world/item/Item;)Z", ordinal = 0))
    private boolean goety$checkTotemEvent(ItemStack instance, Item item, Operation<Boolean> original, @Local(argsOnly = true) DamageSource source, @Local InteractionHand hand) {
        boolean eventResult = EventHooks.onLivingUseTotem((LivingEntity) (Object) this, source, instance, hand);
        return original.call(instance, item) && eventResult;
    }

}
