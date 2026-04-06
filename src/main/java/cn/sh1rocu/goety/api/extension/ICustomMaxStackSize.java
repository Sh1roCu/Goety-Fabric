package cn.sh1rocu.goety.api.extension;

import net.minecraft.world.item.ItemStack;

public interface ICustomMaxStackSize {

    int getMaxStackSize(ItemStack stack);
}
