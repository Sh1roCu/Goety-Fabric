package cn.sh1rocu.goety.mixin.common;

import cn.sh1rocu.goety.api.event.MobEffectEvent;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Spider;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Spider.class)
public class SpiderMixin {
    @Inject(method = "canBeAffected", at = @At("HEAD"), cancellable = true)
    private void goety$isEffectApplicable(MobEffectInstance effectInstance, CallbackInfoReturnable<Boolean> cir) {
        if (effectInstance.getEffect() == MobEffects.POISON) {
            MobEffectEvent.Applicable event = new MobEffectEvent.Applicable((LivingEntity) (Object) this, effectInstance);
            MobEffectEvent.APPLICABLE.invoker().post(event);
            if (event.getResult() != MobEffectEvent.Result.DEFAULT)
                cir.setReturnValue(event.getResult() == MobEffectEvent.Result.ALLOW);
        }
    }
}