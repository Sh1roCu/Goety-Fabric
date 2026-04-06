package com.Polarice3.Goety.common.items.handler;

import cn.sh1rocu.goety.util.transfer.ItemItemStorage;
import cn.sh1rocu.goety.util.transfer.ItemStackHandler;
import cn.sh1rocu.goety.util.transfer.ItemStackStorage;
import com.Polarice3.Goety.api.items.magic.IFocus;
import net.fabricmc.fabric.api.transfer.v1.context.ContainerItemContext;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.transaction.TransactionContext;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nonnull;

public class SoulUsingItemHandler extends ItemStackHandler {
    private final ItemStack itemStack;

    public SoulUsingItemHandler(ItemStack itemStack) {
        this.itemStack = itemStack;
        CompoundTag tag = itemStack.getTagElement("Container");
        if (tag != null) {
            deserializeNBT(tag);
        }
    }

    public long extractItem(TransactionContext tx) {
        var resource = getSlot(0).getResource();
        if (resource.isBlank()) return 0;
        return extract(resource, 1, tx);
    }

    public long insertItem(ItemStack insert, TransactionContext tx) {
        if (insert.isEmpty()) return 0;
        return insert(ItemVariant.of(insert), insert.getCount(), tx);
    }

    public ItemStack getSlot() {
        return getStackInSlot(0);
    }

    @Override
    public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
        return stack.getItem() instanceof IFocus;
    }

    @Override
    public int getSlotLimit(int slot) {
        return 1;
    }

    @Override
    public void onContentsChanged(int slot) {
        CompoundTag nbt = itemStack.getOrCreateTag();
        nbt.put("Container", serializeNBT());
    }

    public static SoulUsingItemHandler get(ItemStack stack) {
        return (SoulUsingItemHandler) ItemItemStorage.ITEM.find(stack, ContainerItemContext.ofSingleSlot(new ItemStackStorage(stack)));
    }
}