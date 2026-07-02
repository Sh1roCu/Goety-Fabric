package com.Polarice3.Goety.common.network.client;

import com.Polarice3.Goety.Goety;
import com.Polarice3.Goety.common.entities.vehicle.HauntedBroom;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.world.entity.Entity;

public class CBroomCollisionPacket {
    public static final ResourceLocation ID = Goety.location("c2s_broom_collision");

    public static FriendlyByteBuf encode(int entityId, double speed) {
        FriendlyByteBuf buffer = PacketByteBufs.create();
        buffer.writeVarInt(entityId);
        buffer.writeDouble(speed);
        return buffer;
    }

    public static void consume(MinecraftServer server, ServerPlayer playerEntity, ServerGamePacketListenerImpl handler, FriendlyByteBuf buf, PacketSender responseSender) {
        int entityId = buf.readVarInt();
        double speed = buf.readDouble();
        server.execute(() -> {
            if (playerEntity != null) {
                Entity entity = playerEntity.level.getEntity(entityId);
                if (entity instanceof HauntedBroom broom) {
                    if (broom.getControllingPassenger() == playerEntity) {
                        broom.applyCollisionDamage(speed);
                    }
                }
            }
        });
    }
}
