package com.Polarice3.Goety.common.network.server;

import com.Polarice3.Goety.Goety;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

public class SPlayerRotationPacket {

    public static final ResourceLocation ID = Goety.location("s2c_player_rotation");

    public static FriendlyByteBuf encode(float yRot, float xRot) {
        FriendlyByteBuf buffer = PacketByteBufs.create();
        buffer.writeFloat(yRot);
        buffer.writeFloat(xRot);
        return buffer;
    }

    @Environment(EnvType.CLIENT)
    public static void consume(Minecraft client, ClientPacketListener handler, FriendlyByteBuf buf, PacketSender responseSender) {
        float yRot = buf.readFloat();
        float xRot = buf.readFloat();
        client.execute(() -> {
            Player player = Goety.PROXY.getPlayer();
            if (player != null) {
                player.setYRot(yRot);
                player.setXRot(xRot);
            }
        });
    }
}
