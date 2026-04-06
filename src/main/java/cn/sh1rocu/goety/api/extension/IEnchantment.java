package cn.sh1rocu.goety.api.extension;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;

public interface IEnchantment {

    boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment);
}