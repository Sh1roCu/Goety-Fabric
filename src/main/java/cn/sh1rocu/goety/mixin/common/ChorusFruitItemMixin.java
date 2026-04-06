package cn.sh1rocu.goety.mixin.common;

import cn.sh1rocu.goety.api.event.EntityTeleportEvent;
import cn.sh1rocu.goety.util.forge.EventHooks;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ChorusFruitItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ChorusFruitItem.class)
public abstract class ChorusFruitItemMixin {
    @WrapOperation(method = "finishUsingItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;randomTeleport(DDDZ)Z"))
    private boolean goety$chorusTeleportEvent(LivingEntity instance, double x, double y, double z, boolean fireEvent, Operation<Boolean> original, @Share("event") LocalRef<EntityTeleportEvent.ChorusFruit> eventRef) {
        var event = EventHooks.onChorusFruitTeleport(instance, x, y, z);
        eventRef.set(event);
        if (!event.isCanceled())
            return original.call(instance, event.getTargetX(), event.getTargetY(), event.getTargetZ(), fireEvent);
        return true;
    }

    @Inject(method = "finishUsingItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;gameEvent(Lnet/minecraft/world/level/gameevent/GameEvent;Lnet/minecraft/world/phys/Vec3;Lnet/minecraft/world/level/gameevent/GameEvent$Context;)V"), cancellable = true)
    private void goety$eventCancelled(ItemStack itemStack, Level level, LivingEntity livingEntity, CallbackInfoReturnable<ItemStack> cir, @Local(ordinal = 1) ItemStack result, @Share("event") LocalRef<EntityTeleportEvent.ChorusFruit> eventRef) {
        if (eventRef.get().isCanceled())
            cir.setReturnValue(result);
    }
}