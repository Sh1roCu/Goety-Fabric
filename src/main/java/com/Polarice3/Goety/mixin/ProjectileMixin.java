package com.Polarice3.Goety.mixin;

import com.Polarice3.Goety.utils.MobUtil;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Projectile.class)
public abstract class ProjectileMixin {
    @Inject(
            method = {"canHitEntity(Lnet/minecraft/world/entity/Entity;)Z"},
            at = @At(value = "HEAD"),
            cancellable = true
    )
    private void canHitEntity(Entity pEntity, CallbackInfoReturnable<Boolean> callback) {
        Projectile projectile = (Projectile) (Object) this;
        if ((!(projectile instanceof AbstractArrow))) {
            if (!MobUtil.canHitEntity(projectile, pEntity)) {
                callback.setReturnValue(false);
            }
        }
    }
}
