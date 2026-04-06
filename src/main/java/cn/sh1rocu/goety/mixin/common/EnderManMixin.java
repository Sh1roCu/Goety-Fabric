package cn.sh1rocu.goety.mixin.common;

import cn.sh1rocu.goety.api.extension.IEnderMask;
import cn.sh1rocu.goety.util.forge.EventHooks;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(EnderMan.class)
public class EnderManMixin {
    @WrapOperation(method = "teleport(DDD)Z", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/monster/EnderMan;randomTeleport(DDDZ)Z"))
    private boolean goety$onEnderTeleport(EnderMan instance, double x, double y, double z, boolean fireEvent, Operation<Boolean> original) {
        var event = EventHooks.onEnderTeleport(instance, x, y, z);
        if (!event.isCanceled())
            return original.call(instance, event.getTargetX(), event.getTargetY(), event.getTargetZ(), fireEvent);
        return false;
    }

    @WrapOperation(method = "isLookingAtMe", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;is(Lnet/minecraft/world/item/Item;)Z", ordinal = 0))
    private boolean goety$checkIsEndermanMask(ItemStack instance, Item item, Operation<Boolean> original, @Local(argsOnly = true) Player player) {
        if (instance.getItem() instanceof IEnderMask custom) {
            return custom.isEnderMask(instance, player, (EnderMan) (Object) this);
        }

        return original.call(instance, item);
    }
}