package cn.sh1rocu.goety.mixin.common;

import cn.sh1rocu.goety.api.event.LivingChangeTargetEvent;
import cn.sh1rocu.goety.api.event.MobSpawnEvent;
import cn.sh1rocu.goety.util.forge.EventHooks;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.level.ServerLevelAccessor;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Mob.class)
public class MobMixin {

    @Shadow
    @Nullable
    private LivingEntity target;

    @Inject(method = "finalizeSpawn", at = @At("HEAD"), cancellable = true)
    private void goety$onFinalizeSpawn(ServerLevelAccessor serverLevelAccessor, DifficultyInstance difficultyInstance, MobSpawnType mobSpawnType, SpawnGroupData spawnGroupData, CompoundTag compoundTag, CallbackInfoReturnable<SpawnGroupData> cir) {
        MobSpawnEvent.FinalizeSpawn event = new MobSpawnEvent.FinalizeSpawn(
                (Mob) (Object) this, serverLevelAccessor, difficultyInstance, mobSpawnType, spawnGroupData, compoundTag
        );
        MobSpawnEvent.FINALIZE_SPAWN.invoker().onFinalizeSpawn(event);
        if (event.isCanceled()) {
            cir.setReturnValue(null);
        } else {
            cir.setReturnValue(event.getSpawnData());
        }
    }

    @ModifyVariable(method = "setTarget", at = @At("HEAD"), argsOnly = true)
    private LivingEntity goety$onChangeTarget(LivingEntity target) {
        LivingChangeTargetEvent changeTargetEvent = EventHooks.onLivingChangeTarget((Mob) (Object) this, target, LivingChangeTargetEvent.LivingTargetType.MOB_TARGET);
        if (!changeTargetEvent.isCanceled()) {
            return changeTargetEvent.getNewTarget();
        }
        return this.target;
    }
}
