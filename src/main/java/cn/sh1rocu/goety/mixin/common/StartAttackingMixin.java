package cn.sh1rocu.goety.mixin.common;

import cn.sh1rocu.goety.api.event.LivingChangeTargetEvent;
import cn.sh1rocu.goety.util.forge.EventHooks;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.behavior.StartAttacking;
import net.minecraft.world.entity.ai.behavior.declarative.MemoryAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.function.Function;
import java.util.function.Predicate;

// PortingLib
@Mixin(StartAttacking.class)
public class StartAttackingMixin {

    @Inject(method = "method_47123", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/ai/behavior/declarative/MemoryAccessor;set(Ljava/lang/Object;)V"), cancellable = true)
    private static void goety$onLivingChangeTarget(
            Predicate predicate, Function function, MemoryAccessor memoryAccessor, MemoryAccessor memoryAccessor2, ServerLevel level, Mob mob, long l, CallbackInfoReturnable<Boolean> cir,
            @Local(index = 9) LivingEntity target, @Share("event") LocalRef<LivingChangeTargetEvent> eventRef
    ) {
        LivingChangeTargetEvent changeTargetEvent = EventHooks.onLivingChangeTarget(mob, target, LivingChangeTargetEvent.LivingTargetType.BEHAVIOR_TARGET);
        eventRef.set(changeTargetEvent);
        if (changeTargetEvent.isCanceled())
            cir.setReturnValue(false);
    }

    @ModifyArg(method = "method_47123", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/ai/behavior/declarative/MemoryAccessor;set(Ljava/lang/Object;)V"))
    private static Object goety$changeTarget(Object object, @Share("event") LocalRef<LivingChangeTargetEvent> eventRef) {
        LivingChangeTargetEvent event = eventRef.get();
        // Only pass the new target if we know the target was changed.
        if (event.changedTarget())
            return event.getNewTarget();
        return object;
    }
}