package cn.sh1rocu.goety.mixin.asm;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(EnchantmentCategory.class)
public abstract class EnchantmentCategoryASM {

    @Shadow
    public abstract boolean canEnchant(Item item);
}
