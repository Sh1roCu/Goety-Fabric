package cn.sh1rocu.goety.mixin.common;

import cn.sh1rocu.goety.api.extension.ICustomExplosionResistance;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.ExplosionDamageCalculator;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ExplosionDamageCalculator.class)
public class ExplosionDamageCalculatorMixin {

    @WrapOperation(method = "getBlockExplosionResistance", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/Block;getExplosionResistance()F"))
    private float goety$getExplosionResistance(
            Block instance, Operation<Float> original,
            @Local(argsOnly = true) BlockState state,
            @Local(argsOnly = true) BlockGetter level,
            @Local(argsOnly = true) BlockPos pos,
            @Local(argsOnly = true) Explosion explosion) {
        if (state.getBlock() instanceof ICustomExplosionResistance custom) {
            return custom.getExplosionResistance(state, level, pos, explosion);
        }

        return original.call(instance);
    }
}
