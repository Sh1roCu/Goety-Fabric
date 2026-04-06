package com.Polarice3.Goety.common.items.magic;

import com.Polarice3.Goety.client.inventory.container.FocusBagContainer;
import com.Polarice3.Goety.common.items.handler.FocusBagItemHandler;
import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.TrinketItem;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class FocusBag extends TrinketItem {
    public FocusBag() {
        super(new Properties()
                .rarity(Rarity.UNCOMMON)
                //.setNoRepair()
                .stacksTo(1)
        );
    }

    // Fabric: Trinkets can't call inventoryTick, so i need to do this like Curios :)
    @Override
    public void tick(ItemStack stack, SlotReference slot, LivingEntity entity) {
        super.tick(stack, slot, entity);
        stack.inventoryTick(entity.level, entity, -1, false);
    }

    @Override
    public boolean canEquip(ItemStack stack, SlotReference slotContext, LivingEntity entity) {
        return entity.isCrouching();
    }

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
                        return new FocusBagContainer(i, inventory, FocusBagItemHandler.get(itemstack), itemstack);
                    }

                    @Override
                    public Component getDisplayName() {
                        return getName(itemstack);
                    }

                    @Override
                    public void writeScreenOpeningData(ServerPlayer player, FriendlyByteBuf buf) {

                    }
                };
                playerIn.openMenu(provider);
            }
            return InteractionResultHolder.success(itemstack);
        }
    }

    @Override
    public boolean allowNbtUpdateAnimation(Player player, InteractionHand hand, ItemStack oldStack, ItemStack newStack) {
        return super.allowNbtUpdateAnimation(player, hand, oldStack, newStack) && !oldStack.equals(newStack);
    }

    @Nullable
    public FocusBagItemHandler initCapabilities(@Nonnull ItemStack stack) {
        return new FocusBagItemHandler(stack, 11);
    }
}
