package cn.sh1rocu.goety.mixin.common;

import cn.sh1rocu.goety.api.extension.ICustomEnchantPowerBonus;
import net.minecraft.core.BlockPos;
import net.minecraft.world.inventory.EnchantmentMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.EnchantmentTableBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

// From https://github.com/jahirxtrap/ironbookshelves/blob/1.20.1/fabric/src/main/java/com/jahirtrap/ironbookshelves/init/mixin/EnchantmentMenuMixin.java
@Mixin(EnchantmentMenu.class)
public abstract class EnchantmentMenuMixin {

    @ModifyVariable(method = "method_17411", at = @At(value = "STORE", ordinal = 0), ordinal = 0, remap = false)
    private int goety$powerBonus(int i, ItemStack stack, Level level, BlockPos pos) {
        float j = 0;
        for (BlockPos blockPos : EnchantmentTableBlock.BOOKSHELF_OFFSETS) {
            BlockPos actualPos = pos.offset(blockPos);
            BlockState state = level.getBlockState(actualPos);
            if (state.getBlock() instanceof ICustomEnchantPowerBonus custom)
                j += custom.getEnchantPowerBonus(state, level, actualPos) - 1;
        }
        return Math.round(j);
    }
}