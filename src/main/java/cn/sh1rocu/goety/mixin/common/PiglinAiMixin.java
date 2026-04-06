package cn.sh1rocu.goety.mixin.common;

import cn.sh1rocu.goety.api.extension.IMakesPiglinsNeutralTrinkets;
import dev.emi.trinkets.api.TrinketsApi;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.piglin.PiglinAi;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PiglinAi.class)
public class PiglinAiMixin {
    @Inject(method = "isWearingGold", at = @At(value = "HEAD"), cancellable = true)
    private static void goety$makesPiglinsNeutralForTrinkets(LivingEntity entity, CallbackInfoReturnable<Boolean> cir) {
        TrinketsApi.getTrinketComponent(entity).ifPresent(trinket -> {
            trinket.getAllEquipped().forEach(equipped -> {
                if (equipped.getB().getItem() instanceof IMakesPiglinsNeutralTrinkets custom) {
                    cir.setReturnValue(custom.makesPiglinsNeutral(equipped.getA(), equipped.getB(), entity));
                }
            });
        });
    }
}