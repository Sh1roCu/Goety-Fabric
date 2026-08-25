package cn.sh1rocu.goety.mixin.common;

import cn.sh1rocu.goety.api.event.LivingDamageEvent;
import cn.sh1rocu.goety.api.event.LivingHurtEvent;
import cn.sh1rocu.goety.api.extension.ILeftClickEntity;
import cn.sh1rocu.goety.util.forge.EventHooks;
import com.Polarice3.Goety.common.events.ModEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Player.class)
public abstract class PlayerMixin extends LivingEntity {
    protected PlayerMixin(EntityType<? extends LivingEntity> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(method = "tick", at = @At("HEAD"))
    public void goety$playerStartTickEvent(CallbackInfo ci) {
        EventHooks.firePlayerTickPre((Player) (Object) this);
    }

    @Inject(method = "tick", at = @At("TAIL"))
    public void goety$playerEndTickEvent(CallbackInfo ci) {
        EventHooks.firePlayerTickPost((Player) (Object) this);
    }

    @Inject(method = "attack", at = @At("HEAD"), cancellable = true)
    public void goety$itemAttack(Entity targetEntity, CallbackInfo ci) {
        Item item = getMainHandItem().getItem();
        if ((item instanceof ILeftClickEntity leftClickEntity)) {
            if (leftClickEntity.onLeftClickEntity(getMainHandItem(), (Player) (Object) this, targetEntity))
                ci.cancel();
        }
    }

    @ModifyVariable(method = "actuallyHurt", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;getDamageAfterArmorAbsorb(Lnet/minecraft/world/damagesource/DamageSource;F)F"), argsOnly = true)
    private float goety$hurtEvent(float original, DamageSource source) {
        LivingHurtEvent event = new LivingHurtEvent((Player) (Object) this, source, original);
        LivingHurtEvent.EVENT.invoker().post(event);
        if (event.isCanceled()) {
            return 0;
        }

        return event.getAmount();
    }

    @ModifyVariable(method = "actuallyHurt(Lnet/minecraft/world/damagesource/DamageSource;F)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;setAbsorptionAmount(F)V"), argsOnly = true)
    private float goety$damageEvent(float damageAmount, DamageSource damageSource) {
        LivingDamageEvent event = new LivingDamageEvent((Player) (Object) this, damageSource, damageAmount);
        LivingDamageEvent.EVENT.invoker().post(event);
        if (event.isCanceled()) {
            return 0;
        } else {
            return event.getAmount();
        }
    }

    @Inject(method = "stopSleepInBed", at = @At("HEAD"))
    private void goety$onPlayerWakeup(boolean wakeImmediately, boolean updateLevel, CallbackInfo ci) {
        ModEvents.onWakeUp((Player) (Object) this, wakeImmediately, updateLevel);
    }
}
