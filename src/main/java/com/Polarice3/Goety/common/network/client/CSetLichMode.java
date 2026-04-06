package com.Polarice3.Goety.common.network.client;

import com.Polarice3.Goety.Goety;
import com.Polarice3.Goety.utils.LichdomHelper;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;

public class CSetLichMode {

    public static final ResourceLocation ID = Goety.location("c2s_set_lich_mode");

    public static final FriendlyByteBuf DUMMY = PacketByteBufs.empty();

    public static void consume(MinecraftServer server, ServerPlayer playerEntity, ServerGamePacketListenerImpl handler, FriendlyByteBuf buf, PacketSender responseSender) {
        server.execute(() -> {
            if (playerEntity != null && LichdomHelper.isLich(playerEntity)) {
                LichdomHelper.setLichMode(playerEntity, !LichdomHelper.isInLichMode(playerEntity));
            }
        });
    }
}
