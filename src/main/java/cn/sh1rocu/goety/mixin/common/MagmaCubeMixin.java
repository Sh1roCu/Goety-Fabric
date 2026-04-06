package cn.sh1rocu.goety.mixin.common;

import cn.sh1rocu.goety.api.event.LivingJumpEvent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.MagmaCube;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MagmaCube.class)
public class MagmaCubeMixin {
    @Inject(method = "jumpFromGround", at = @At("TAIL"))
    public void goety$onJump(CallbackInfo ci) {
        LivingJumpEvent.EVENT.invoker().post(new LivingJumpEvent((LivingEntity) (Object) this));
    }
}