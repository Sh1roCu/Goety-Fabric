package com.Polarice3.Goety.common.items.handler;

import cn.sh1rocu.goety.util.transfer.ItemItemStorage;
import cn.sh1rocu.goety.util.transfer.ItemStackHandler;
import cn.sh1rocu.goety.util.transfer.ItemStackStorage;
import com.Polarice3.Goety.api.items.magic.IFocus;
import net.fabricmc.fabric.api.transfer.v1.context.ContainerItemContext;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nonnull;

public class FocusBagItemHandler extends ItemStackHandler {
    private final ItemStack itemStack;
    private final int size;

    public FocusBagItemHandler(ItemStack itemStack, int size) {
        super(size);
        this.size = size;
        this.itemStack = itemStack;
        CompoundTag tag = itemStack.getTagElement("Container");
        if (tag != null) {
            deserializeNBT(tag);
        }
    }

    @Override
    public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
        return stack.getItem() instanceof IFocus;
    }

    @Override
    public int getSlotLimit(int slot) {
        return this.size;
    }

    @Override
    protected void onContentsChanged(int slot) {
        CompoundTag nbt = itemStack.getOrCreateTag();
        nbt.put("Container", serializeNBT());
    }

    public static FocusBagItemHandler get(ItemStack stack) {
        return (FocusBagItemHandler) ItemItemStorage.ITEM.find(stack, ContainerItemContext.ofSingleSlot(new ItemStackStorage(stack)));
    }
}