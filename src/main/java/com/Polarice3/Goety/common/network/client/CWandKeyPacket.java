package com.Polarice3.Goety.common.network.client;

import com.Polarice3.Goety.Goety;
import com.Polarice3.Goety.api.items.magic.IWand;
import com.Polarice3.Goety.client.inventory.container.SoulItemContainer;
import com.Polarice3.Goety.common.items.handler.SoulUsingItemHandler;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class CWandKeyPacket {

    public static final ResourceLocation ID = Goety.location("c2s_wand_key");

    public static final FriendlyByteBuf DUMMY = PacketByteBufs.empty();

    public static void consume(MinecraftServer server, ServerPlayer playerEntity, ServerGamePacketListenerImpl handler, FriendlyByteBuf buf, PacketSender responseSender) {
        server.execute(() -> {
            if (playerEntity != null) {
                ItemStack stack = playerEntity.getMainHandItem();
                ItemStack stack2 = playerEntity.getOffhandItem();

                if (!stack.isEmpty() && stack.getItem() instanceof IWand) {
                    MenuProvider provider = new ExtendedScreenHandlerFactory() {
                        @Override
                        public void writeScreenOpeningData(ServerPlayer player, FriendlyByteBuf buf) {
                            buf.writeBoolean(playerEntity.getUsedItemHand() == InteractionHand.MAIN_HAND);
                            buf.writeItem(stack);
                        }

                        @Override
                        public @NotNull Component getDisplayName() {
                            return Component.translatable(stack.getDescriptionId());
                        }

                        @Override
                        public @NotNull AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
                            return new SoulItemContainer(i, inventory,
                                    SoulUsingItemHandler.get(stack),
                                    stack, playerEntity.getUsedItemHand());
                        }
                    };
                    playerEntity.openMenu(provider);
                } else if (!stack2.isEmpty() && stack2.getItem() instanceof IWand) {
                    MenuProvider provider = new ExtendedScreenHandlerFactory() {
                        @Override
                        public void writeScreenOpeningData(ServerPlayer player, FriendlyByteBuf buf) {
                            buf.writeBoolean(playerEntity.getUsedItemHand() == InteractionHand.OFF_HAND);
                            buf.writeItem(stack2);
                        }

                        @Override
                        public @NotNull Component getDisplayName() {
                            return Component.translatable(stack2.getDescriptionId());
                        }

                        @Override
                        public @NotNull AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
                            return new SoulItemContainer(i, inventory,
                                    SoulUsingItemHandler.get(stack2),
                                    stack2, playerEntity.getUsedItemHand());
                        }
                    };
                    playerEntity.openMenu(provider);
                }
            }
        });
    }
}
