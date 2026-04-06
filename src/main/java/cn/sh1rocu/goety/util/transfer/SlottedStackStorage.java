package cn.sh1rocu.goety.util.transfer;

import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.SlottedStorage;
import net.minecraft.world.item.ItemStack;

// From Create-Fabric(https://github.com/crabdancing/Create-fabric/blob/mc1.21.1/fabric/dev/src/main/java/com/simibubi/create/infrastructure/fabric/transfer/item/SlottedStackStorage.java)
public interface SlottedStackStorage extends SlottedStorage<ItemVariant> {
    ItemStack getStackInSlot(int slot);

    void setStackInSlot(int slot, ItemStack stack);

    int getSlotLimit(int slot);

    boolean isItemValid(int slot, ItemStack stack);
}