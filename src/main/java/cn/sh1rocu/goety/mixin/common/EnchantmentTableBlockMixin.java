package cn.sh1rocu.goety.mixin.common;

import cn.sh1rocu.goety.api.extension.ICustomEnchantPowerBonus;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.EnchantmentTableBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(EnchantmentTableBlock.class)
public abstract class EnchantmentTableBlockMixin {

    @WrapOperation(method = "isValidBookShelf", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockState;is(Lnet/minecraft/tags/TagKey;)Z", ordinal = 0))
    private static boolean goety$isValid(BlockState state, TagKey<BlockState> tagKey, Operation<Boolean> original, Level level, BlockPos enchantingTablePos, BlockPos bookshelfPos) {
        if (state.getBlock() instanceof ICustomEnchantPowerBonus custom) {
            return custom.getEnchantPowerBonus(state, level, enchantingTablePos.offset(bookshelfPos)) != 0;
        }
        return original.call(state, tagKey);
    }
}