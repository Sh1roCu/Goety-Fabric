package com.Polarice3.Goety.common.items;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nonnull;

public class RepeatCraftItem extends Item {
    public RepeatCraftItem(Properties properties) {
        super(properties);
    }

    @Override
    public boolean hasCraftingRemainingItem() {
        return true;
    }

    @Nonnull
    @Override
    public ItemStack getRecipeRemainder(ItemStack itemStack) {
        return itemStack.copyWithCount(1);
    }
}
