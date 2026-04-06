package cn.sh1rocu.goety.mixin.common;

import cn.sh1rocu.goety.api.extension.IEntityListener;
import net.minecraft.world.level.entity.EntityAccess;
import net.minecraft.world.level.entity.PersistentEntitySectionManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PersistentEntitySectionManager.class)
public class PersistentEntitySectionManagerMixin<T extends EntityAccess> {

    // method_31857: processPendingLoads.11
    // method_31863: addWorldGenChunkEntities.1
    // method_31864: addLegacyChunkEntities.0
    @Inject(method = {"method_31857", "method_31863", "method_31864"}, at = @At("TAIL"))
    private static void goety$addedToWorld(EntityAccess entityAccess, CallbackInfo ci) {
        if (entityAccess instanceof IEntityListener entity)
            entity.onAddedToWorld();
    }
}