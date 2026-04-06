package cn.sh1rocu.goety.util.transfer;

import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

// Based Create-Fabric(https://github.com/crabdancing/Create-fabric/blob/mc1.21.1/fabric/dev/src/main/java/com/simibubi/create/infrastructure/fabric/transfer/item/SlotItemHandler.java)
public class SlotItemHandler extends Slot {

    private final SlottedStackStorage itemHandler;

    public SlotItemHandler(SlottedStackStorage storage, int slot, int x, int y) {
        super(new StorageWrapperContainer(storage), slot, x, y);
        this.itemHandler = storage;
    }

    @Override
    public boolean mayPlace(@NotNull ItemStack stack) {
        if (stack.isEmpty())
            return false;
        return itemHandler.isItemValid(index, stack);
    }
}