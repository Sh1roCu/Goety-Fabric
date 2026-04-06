package cn.sh1rocu.goety.mixin.common;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.phys.shapes.CollisionContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ClipContext.class)
public class ClipContextMixin {
    @WrapOperation(
            method = "<init>",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/phys/shapes/CollisionContext;of(Lnet/minecraft/world/entity/Entity;)Lnet/minecraft/world/phys/shapes/CollisionContext;"
            )
    )
    // TODO： 1.21可以直接使用CollisionContext.empty()作为参数
    public CollisionContext goety$modifyCollisionContext(Entity entity, Operation<CollisionContext> operation) {
        if (entity == null) {
            return CollisionContext.empty();
        }
        return operation.call(entity);
    }
}