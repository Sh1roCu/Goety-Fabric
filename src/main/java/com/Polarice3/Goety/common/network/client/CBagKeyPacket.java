package com.Polarice3.Goety.common.network.client;

import com.Polarice3.Goety.Goety;
import com.Polarice3.Goety.client.inventory.container.FocusBagContainer;
import com.Polarice3.Goety.client.inventory.container.FocusPackContainer;
import com.Polarice3.Goety.common.items.handler.FocusBagItemHandler;
import com.Polarice3.Goety.common.items.magic.FocusPack;
import com.Polarice3.Goety.utils.TotemFinder;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class CBagKeyPacket {

    public static final ResourceLocation ID = Goety.location("c2s_bag_key");

    public static final FriendlyByteBuf DUMMY = PacketByteBufs.empty();

    public static void consume(MinecraftServer server, ServerPlayer playerEntity, ServerGamePacketListenerImpl handler, FriendlyByteBuf buf, PacketSender responseSender) {
        server.execute(() -> {
            if (playerEntity != null) {
                ItemStack stack = TotemFinder.findBag(playerEntity);
                if (!stack.isEmpty()) {
                    MenuProvider provider = new ExtendedScreenHandlerFactory() {
                        @Override
                        public void writeScreenOpeningData(ServerPlayer player, FriendlyByteBuf buf) {

                        }

                        @Override
                        public Component getDisplayName() {
                            return Component.translatable(stack.getDescriptionId());
                        }

                        @Override
                        public @NotNull AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
                            return new FocusBagContainer(i, inventory,
                                    FocusBagItemHandler.get(stack), stack);
                        }
                    };
                    if (stack.getItem() instanceof FocusPack) {
                        provider = new ExtendedScreenHandlerFactory() {
                            @Override
                            public void writeScreenOpeningData(ServerPlayer player, FriendlyByteBuf buf) {

                            }

                            @Override
                            public Component getDisplayName() {
                                return Component.translatable(stack.getDescriptionId());
                            }

                            @Override
                            public @NotNull AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
                                return new FocusPackContainer(i, inventory,
                                        FocusBagItemHandler.get(stack), stack);
                            }
                        };
                    }
                    playerEntity.openMenu(provider);
                }
            }
        });
    }
}
