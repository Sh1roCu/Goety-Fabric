package com.Polarice3.Goety.common.network.client;

import com.Polarice3.Goety.Goety;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.world.entity.Entity;

public class CSetDeltaMovement {

    public static final ResourceLocation ID = Goety.location("c2s_set_delta_movement");

    public static FriendlyByteBuf encode(int id, double x, double y, double z) {
        FriendlyByteBuf buffer = PacketByteBufs.create();
        buffer.writeInt(id);
        buffer.writeDouble(x);
        buffer.writeDouble(y);
        buffer.writeDouble(z);
        return buffer;
    }

    public static void consume(MinecraftServer server, ServerPlayer playerEntity, ServerGamePacketListenerImpl handler, FriendlyByteBuf buf, PacketSender responseSender) {
        int mob = buf.readInt();
        double x = buf.readDouble();
        double y = buf.readDouble();
        double z = buf.readDouble();
        server.execute(() -> {
            if (playerEntity != null) {
                Entity entity = playerEntity.level.getEntity(mob);
                if (entity != null) {
                    entity.setDeltaMovement(x, y, z);
                    entity.hasImpulse = true;
                }
            }
        });
    }
}
