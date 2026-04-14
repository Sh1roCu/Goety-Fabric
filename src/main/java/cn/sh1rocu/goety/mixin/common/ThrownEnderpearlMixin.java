package cn.sh1rocu.goety.mixin.common;

import cn.sh1rocu.goety.api.event.EntityTeleportEvent;
import cn.sh1rocu.goety.util.forge.EventHooks;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.entity.projectile.ThrownEnderpearl;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// Based on PortingLib1.21
@Mixin(ThrownEnderpearl.class)
public abstract class ThrownEnderpearlMixin extends ThrowableItemProjectile {
    public ThrownEnderpearlMixin(EntityType<? extends ThrowableItemProjectile> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(method = "onHit", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/RandomSource;nextFloat()F"), cancellable = true)
    private void goety$onEnderPearlLand(HitResult result, CallbackInfo ci, @Share("teleport") LocalRef<EntityTeleportEvent.EnderPearl> eventRef) {
        var event = EventHooks.onEnderPearlLand((ServerPlayer) getOwner(), this.getX(), this.getY(), this.getZ(), (ThrownEnderpearl) (Object) this, 5.0F, result);
        eventRef.set(event);
        if (event.isCanceled()) {
            discard();
            ci.cancel();
        }
    }

//    // used for 1.21
//    @ModifyArg(method = "onHit", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/portal/DimensionTransition;<init>(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/phys/Vec3;Lnet/minecraft/world/phys/Vec3;FFLnet/minecraft/world/level/portal/DimensionTransition$PostDimensionTransition;)V", ordinal = 0), index = 1)
//    private Vec3 goety$modifyTarget(Vec3 vec3, @Share("teleport") LocalRef<EntityTeleportEvent.EnderPearl> eventRef) {
//        return eventRef.get().getTarget();
//    }

    // used for 1.20
    @Inject(method = "onHit", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;resetFallDistance()V", ordinal = 0))
    private void goety$teleport(HitResult hitResult, CallbackInfo ci, @Local Entity entity, @Share("teleport") LocalRef<EntityTeleportEvent.EnderPearl> eventRef) {
        Vec3 target = eventRef.get().getTarget();
        entity.teleportTo(target.x, target.y, target.z);
    }

    @ModifyArg(method = "onHit", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;hurt(Lnet/minecraft/world/damagesource/DamageSource;F)Z"), index = 1)
    private float goety$modifyAttackDamage(float damage, @Share("teleport") LocalRef<EntityTeleportEvent.EnderPearl> eventRef) {
        // If damage isn't 5 then another mod has changed it (this isn't a great workaround the only proper solution for this is to use asm to check if another mod has modified this and check those modified conditions).
        if (damage != 5.0F)
            return damage;
        return eventRef.get().getAttackDamage();
    }
}