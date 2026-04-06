package com.Polarice3.Goety.common.items;

import com.Polarice3.Goety.api.items.ISoulRepair;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import javax.annotation.Nonnull;

public class PhilosophersStone extends Item implements ISoulRepair {
    public PhilosophersStone() {
        super(new Properties().durability(64));
    }

    @Override
    public boolean hasCraftingRemainingItem() {
        return true;
    }

    @Nonnull
    @Override
    public ItemStack getRecipeRemainder(ItemStack itemStack) {
        ItemStack container = itemStack.copy();
        if (container.getDamageValue() <= container.getMaxDamage()) {
            container.setDamageValue(itemStack.getDamageValue() + 1);
        } else {
            container = ItemStack.EMPTY;
        }
        return container;
    }

    @Override
    public boolean allowNbtUpdateAnimation(Player player, InteractionHand hand, ItemStack oldStack, ItemStack newStack) {
        return super.allowNbtUpdateAnimation(player, hand, oldStack, newStack) && !oldStack.equals(newStack);
    }

    @Override
    public boolean isValidRepairItem(ItemStack pToRepair, ItemStack pRepair) {
        return pRepair.getItem() == Items.CRYING_OBSIDIAN || super.isValidRepairItem(pToRepair, pRepair);
    }

}
