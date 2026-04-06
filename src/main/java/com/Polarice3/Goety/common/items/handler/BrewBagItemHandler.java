package com.Polarice3.Goety.common.items.handler;

import cn.sh1rocu.goety.util.transfer.ItemItemStorage;
import cn.sh1rocu.goety.util.transfer.ItemStackHandler;
import cn.sh1rocu.goety.util.transfer.ItemStackStorage;
import com.Polarice3.Goety.common.items.brew.ThrowableBrewItem;
import net.fabricmc.fabric.api.transfer.v1.context.ContainerItemContext;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nonnull;

public class BrewBagItemHandler extends ItemStackHandler {
    private final ItemStack itemStack;

    public BrewBagItemHandler(ItemStack itemStack) {
        super(11);
        this.itemStack = itemStack;
        CompoundTag tag = itemStack.getTagElement("Container");
        if (tag != null) {
            deserializeNBT(tag);
        }
    }

    @Override
    public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
        return stack.getItem() instanceof ThrowableBrewItem;
    }

    @Override
    protected void onContentsChanged(int slot) {
        CompoundTag nbt = itemStack.getOrCreateTag();
        nbt.put("Container", serializeNBT());
    }

    public static BrewBagItemHandler get(ItemStack stack) {
        return (BrewBagItemHandler) ItemItemStorage.ITEM.find(stack, ContainerItemContext.ofSingleSlot(new ItemStackStorage(stack)));
    }
}
