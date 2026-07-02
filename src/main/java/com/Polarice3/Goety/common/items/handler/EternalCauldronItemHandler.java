package com.Polarice3.Goety.common.items.handler;

import cn.sh1rocu.goety.util.transfer.ItemItemStorage;
import cn.sh1rocu.goety.util.transfer.ItemStackHandler;
import cn.sh1rocu.goety.util.transfer.ItemStackStorage;
import com.Polarice3.Goety.common.items.brew.BrewItem;
import com.Polarice3.Goety.common.items.brew.ThrowableBrewItem;
import net.fabricmc.fabric.api.transfer.v1.context.ContainerItemContext;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.transaction.TransactionContext;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PotionItem;
import net.minecraft.world.item.ThrowablePotionItem;

import javax.annotation.Nonnull;

public class EternalCauldronItemHandler extends ItemStackHandler {
    private final ItemStack itemStack;

    public EternalCauldronItemHandler(ItemStack itemStack) {
        this.itemStack = itemStack;
        CompoundTag tag = itemStack.getTagElement("Container");
        if (tag != null) {
            deserializeNBT(tag);
        }
    }

    public long extractItem(TransactionContext tx) {
        return extract(getSlot(0).getResource(), 1, tx);
    }

    public long insertItem(ItemStack insert, TransactionContext tx) {
        return insert(ItemVariant.of(insert), insert.getCount(), tx);
    }

    public ItemStack getSlot() {
        return getStackInSlot(0);
    }

    @Override
    public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
        Item item = stack.getItem();
        return (item instanceof PotionItem && !(item instanceof ThrowablePotionItem)) || (item instanceof BrewItem && !(item instanceof ThrowableBrewItem));
    }

    @Override
    public int getSlotLimit(int slot) {
        return 1;
    }

    @Override
    protected void onContentsChanged(int slot) {
        CompoundTag nbt = itemStack.getOrCreateTag();
        nbt.put("Container", serializeNBT());
    }

    public static EternalCauldronItemHandler get(ItemStack stack) {
        return (EternalCauldronItemHandler) ItemItemStorage.ITEM.find(stack, ContainerItemContext.ofSingleSlot(new ItemStackStorage(stack)));
    }
}
