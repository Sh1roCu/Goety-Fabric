package com.Polarice3.Goety.common.network.server;

import com.Polarice3.Goety.Goety;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

public class SSoulExplodePacket {

    public static final ResourceLocation ID = Goety.location("s2c_soul_explode");

    public static FriendlyByteBuf encode(BlockPos blockPos, int radius) {
        FriendlyByteBuf buffer = PacketByteBufs.create();
        buffer.writeBlockPos(blockPos);
        buffer.writeInt(radius);
        return buffer;
    }

    @Environment(EnvType.CLIENT)
    public static void consume(Minecraft client, ClientPacketListener handler, FriendlyByteBuf buf, PacketSender responseSender) {
        BlockPos blockPos = buf.readBlockPos();
        int radius = buf.readInt();
        client.execute(() -> {
            Goety.PROXY.soulExplode(blockPos, radius);
        });
    }
}
