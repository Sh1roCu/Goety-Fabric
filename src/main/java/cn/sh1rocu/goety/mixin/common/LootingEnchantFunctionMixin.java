package cn.sh1rocu.goety.mixin.common;

import cn.sh1rocu.goety.api.event.LootingLevelEvent;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.functions.LootingEnchantFunction;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LootingEnchantFunction.class)
public class LootingEnchantFunctionMixin {

    @ModifyExpressionValue(method = "run", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/enchantment/EnchantmentHelper;getMobLooting(Lnet/minecraft/world/entity/LivingEntity;)I"))
    private int goety$modifyLootingLevel(int original, @Local Entity entity, @Local(argsOnly = true) LootContext context) {
        if (entity instanceof LivingEntity livingEntity) {
            var event = new LootingLevelEvent(livingEntity, context.getParamOrNull(LootContextParams.DAMAGE_SOURCE), original);
            LootingLevelEvent.EVENT.invoker().post(event);
            return event.getLootingLevel();
        }

        return original;
    }
}
