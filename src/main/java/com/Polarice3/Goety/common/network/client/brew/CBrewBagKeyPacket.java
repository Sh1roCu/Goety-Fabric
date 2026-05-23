package com.Polarice3.Goety.common.network.client.brew;

import com.Polarice3.Goety.Goety;
import com.Polarice3.Goety.client.inventory.container.BrewBagContainer;
import com.Polarice3.Goety.common.items.handler.BrewBagItemHandler;
import com.Polarice3.Goety.utils.CuriosFinder;
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

public class CBrewBagKeyPacket {

    public static final ResourceLocation ID = Goety.location("c2s_brew_bag_key");

    public static final FriendlyByteBuf DUMMY = PacketByteBufs.empty();

    public static void consume(MinecraftServer server, ServerPlayer playerEntity, ServerGamePacketListenerImpl handler, FriendlyByteBuf buf, PacketSender responseSender) {
        server.execute(() -> {
            if (playerEntity != null) {
                ItemStack stack = CuriosFinder.findBrewBag(playerEntity);

                if (!stack.isEmpty()) {
                    MenuProvider provider = new ExtendedScreenHandlerFactory() {
                        @Override
                        public void writeScreenOpeningData(ServerPlayer player, FriendlyByteBuf buf) {
                            buf.writeItem(stack);
                        }

                        @Override
                        public Component getDisplayName() {
                            return Component.translatable(stack.getDescriptionId());
                        }

                        @Override
                        public @NotNull AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
                            return new BrewBagContainer(i, inventory,
                                    BrewBagItemHandler.get(stack), stack);
                        }
                    };
                    playerEntity.openMenu(provider);
                }
            }
        });
    }
}
