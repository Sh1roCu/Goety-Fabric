package cn.sh1rocu.goety.mixin.common;

import cn.sh1rocu.goety.util.forge.EventHooks;
import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import net.minecraft.world.entity.projectile.ShulkerBullet;
import net.minecraft.world.phys.HitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ShulkerBullet.class)
public class ShulkerBulletMixin {
    @WrapWithCondition(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/projectile/ShulkerBullet;onHit(Lnet/minecraft/world/phys/HitResult;)V"))
    private boolean goety$onImpact(ShulkerBullet projectile, HitResult result) {
        if (result.getType() == HitResult.Type.MISS)
            return true;
        return !EventHooks.onProjectileImpact(projectile, result);
    }
}
