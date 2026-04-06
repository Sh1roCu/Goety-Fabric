package com.Polarice3.Goety.common.items.block;

import cn.sh1rocu.goety.api.extension.ICustomMaxStackSize;
import cn.sh1rocu.goety.api.extension.IEnchantment;
import com.Polarice3.Goety.common.blocks.ModBlocks;
import com.Polarice3.Goety.common.enchantments.ModEnchantments;
import com.Polarice3.Goety.config.MainConfig;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.block.Block;

public class EnchantableBlockItem extends BlockItemBase implements ICustomMaxStackSize, IEnchantment {

    public EnchantableBlockItem(Block blockIn) {
        super(blockIn);
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        if (stack.getItem() == ModBlocks.SCULK_DEVOURER.asItem()) {
            return stack.getCount() == 1
                    && (enchantment == ModEnchantments.SOUL_EATER
                    || enchantment == ModEnchantments.RADIUS);
        }
        if (stack.getItem() == ModBlocks.SCULK_CONVERTER.asItem()) {
            return stack.getCount() == 1 && enchantment == ModEnchantments.POTENCY;
        }
        if (stack.getItem() == ModBlocks.SCULK_GROWER.asItem()) {
            if (MainConfig.SculkGrowerPotency.get()) {
                return stack.getCount() == 1 && (enchantment == ModEnchantments.POTENCY || enchantment == ModEnchantments.RADIUS);
            } else {
                return stack.getCount() == 1 && enchantment == ModEnchantments.RADIUS;
            }
        }
        return stack.getCount() == 1;
    }

    @Override
    public int getMaxStackSize(ItemStack itemStack) {
        return itemStack.isEnchanted() ? 1 : super.getMaxStackSize();
    }

    @Override
    public boolean isEnchantable(ItemStack stack) {
        return stack.getCount() == 1;
    }

    @Override
    public int getEnchantmentValue() {
        return 25;
    }
}
