package com.Polarice3.Goety.common.network.client;

import com.Polarice3.Goety.Goety;
import com.Polarice3.Goety.common.entities.ally.illager.raider.AllyTrampler;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.world.entity.Entity;

public class CTramplerPacket {

    public static final ResourceLocation ID = Goety.location("c2s_trampler");

    public int mob;
    public int mode;

    public static FriendlyByteBuf encode(int mob, int mode) {
        FriendlyByteBuf buffer = PacketByteBufs.create();
        buffer.writeInt(mob);
        buffer.writeInt(mode);
        return buffer;
    }

    public static void consume(MinecraftServer server, ServerPlayer playerEntity, ServerGamePacketListenerImpl handler, FriendlyByteBuf buf, PacketSender responseSender) {
        int mob = buf.readInt();
        int mode = buf.readInt();
        server.execute(() -> {
            if (playerEntity != null) {
                Entity entity = playerEntity.level.getEntity(mob);
                if (entity instanceof AllyTrampler trampler) {
                    if (mode == 0) {
                        ++trampler.running;
                    } else if (mode == 1) {
                        trampler.setDashing(true);
                    } else if (mode == 2) {
                        trampler.setDashing(false);
                    }
                }
            }
        });
    }
}
