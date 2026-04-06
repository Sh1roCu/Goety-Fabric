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
import net.minecraft.world.entity.Mob;

public class CTargetPlayerPacket {

    public static final ResourceLocation ID = Goety.location("c2s_target_player");

    public static FriendlyByteBuf encode(Mob aggressor) {
        return encode(aggressor.getId());
    }

    public static FriendlyByteBuf encode(int target) {
        FriendlyByteBuf buffer = PacketByteBufs.create();
        buffer.writeInt(target);
        return buffer;
    }

    public static void consume(MinecraftServer server, ServerPlayer player, ServerGamePacketListenerImpl handler, FriendlyByteBuf buf, PacketSender responseSender) {
        int aggressor = buf.readInt();
        server.execute(() -> {
            if (player != null) {
                Entity entity = player.level.getEntity(aggressor);
                if (entity instanceof Mob mob) {
                    mob.setTarget(player);
                }
            }
        });
    }
}
