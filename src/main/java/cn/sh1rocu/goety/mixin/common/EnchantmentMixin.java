package cn.sh1rocu.goety.mixin.common;

import cn.sh1rocu.goety.api.extension.IEnchantment;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Enchantment.class)
public class EnchantmentMixin {

    @ModifyReturnValue(method = "canEnchant", at = @At("RETURN"))
    private boolean goety$canEnchant(boolean original, @Local(argsOnly = true) ItemStack itemStack) {
        Enchantment enchantment = (Enchantment) (Object) this;
        if (itemStack.getItem() instanceof IEnchantment ex) {
            return ex.canApplyAtEnchantingTable(itemStack, enchantment);
        }

        return original;
    }
}
