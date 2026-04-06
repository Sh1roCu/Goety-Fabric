package com.Polarice3.Goety.common.network.client;

import com.Polarice3.Goety.Goety;
import com.Polarice3.Goety.common.inventory.ModSaveInventory;
import com.Polarice3.Goety.common.inventory.WitchRobeInventory;
import com.Polarice3.Goety.common.items.curios.WitchRobeItem;
import com.Polarice3.Goety.utils.CuriosFinder;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.world.item.ItemStack;

public class CAddCatalystKeyPacket {

    public static final ResourceLocation ID = Goety.location("c2s_add_catalyst_key");

    public static final FriendlyByteBuf DUMMY = PacketByteBufs.empty();

    public static void consume(MinecraftServer server, ServerPlayer playerEntity, ServerGamePacketListenerImpl handler, FriendlyByteBuf buf, PacketSender responseSender) {
        server.execute(() -> {
            if (playerEntity != null) {
                ItemStack stack = CuriosFinder.findCurio(playerEntity, itemStack -> itemStack.getItem() instanceof WitchRobeItem);
                ItemStack mainHandItem = playerEntity.getMainHandItem();
                ItemStack offhandItem = playerEntity.getOffhandItem();

                if (!stack.isEmpty()) {
                    WitchRobeInventory inventory = ModSaveInventory.getInstance().getWitchRobeInventory(stack.getOrCreateTag().getInt(WitchRobeItem.INVENTORY), playerEntity);
                    if (!mainHandItem.isEmpty()) {
                        inventory.addBottlesOrCatalyst(mainHandItem);
                    } else if (!offhandItem.isEmpty()) {
                        inventory.addBottlesOrCatalyst(offhandItem);
                    }
                }
            }
        });
    }
}
