package com.Polarice3.Goety.common.items.magic;

import com.Polarice3.Goety.client.inventory.container.FocusPackContainer;
import com.Polarice3.Goety.common.items.handler.FocusBagItemHandler;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class FocusPack extends FocusBag {

    @Nonnull
    @Override
    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
        ItemStack itemstack = playerIn.getItemInHand(handIn);
        if (playerIn.isCrouching() && equipItem(playerIn, itemstack)) {
            return InteractionResultHolder.success(itemstack);
        } else {
            if (!worldIn.isClientSide) {
                var provider = new ExtendedScreenHandlerFactory() {
                    @Override
                    public AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
                        return new FocusPackContainer(i, inventory, FocusBagItemHandler.get(itemstack), itemstack);
                    }

                    @Override
                    public Component getDisplayName() {
                        return getName(itemstack);
                    }

                    @Override
                    public void writeScreenOpeningData(ServerPlayer player, FriendlyByteBuf buf) {
                        buf.writeItem(itemstack);
                    }
                };
                playerIn.openMenu(provider);
            }
            return InteractionResultHolder.success(itemstack);
        }
    }

//    public CompoundTag getShareTag(ItemStack stack) {
//        IItemHandler iitemHandler = getItemHandler(stack);
//        CompoundTag nbt = stack.getTag() != null ? stack.getTag() : new CompoundTag();
//        if (iitemHandler instanceof ItemStackHandler itemHandler) {
//            nbt.put("cap", itemHandler.serializeNBT());
//        }
//        return nbt;
//    }
//
//    public void readShareTag(ItemStack stack, @Nullable CompoundTag nbt) {
//        if (nbt == null) {
//            stack.setTag(null);
//        } else {
//            IItemHandler iitemHandler = getItemHandler(stack);
//            if (iitemHandler instanceof ItemStackHandler itemHandler)
//                itemHandler.deserializeNBT(nbt.getCompound("cap"));
//            stack.setTag(nbt);
//        }
//    }

    @Override
    @Nullable
    public FocusBagItemHandler initCapabilities(@Nonnull ItemStack stack) {
        return new FocusBagItemHandler(stack, 21);
    }
}
