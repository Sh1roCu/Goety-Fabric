package cn.sh1rocu.goety.mixin.common;

import cn.sh1rocu.goety.api.event.LivingJumpEvent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractHorse.class)
public class AbstractHorseMixin {
    @Inject(method = "executeRidersJump", at = @At(value = "FIELD", target = "Lnet/minecraft/world/entity/animal/horse/AbstractHorse;hasImpulse:Z", shift = At.Shift.AFTER))
    private void goety$onHorseJump(float f, Vec3 vec3, CallbackInfo ci) {
        LivingJumpEvent.EVENT.invoker().post(new LivingJumpEvent((LivingEntity) (Object) this));
    }
}