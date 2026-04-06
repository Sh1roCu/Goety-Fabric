package com.Polarice3.Goety.common.network.server;

import com.Polarice3.Goety.Goety;
import com.Polarice3.Goety.common.entities.neutral.GulfTentacle;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;

public class STentacleRangePacket {

    public static final ResourceLocation ID = Goety.location("s2c_tentacle_range");

    public static FriendlyByteBuf encode(int mob, float range) {
        FriendlyByteBuf buffer = PacketByteBufs.create();
        buffer.writeInt(mob);
        buffer.writeFloat(range);
        return buffer;
    }

    @Environment(EnvType.CLIENT)
    public static void consume(Minecraft client, ClientPacketListener handler, FriendlyByteBuf buf, PacketSender responseSender) {
        int mob = buf.readInt();
        float range = buf.readFloat();
        client.execute(() -> {
            Level level = Goety.PROXY.getLevel();
            if (level instanceof ClientLevel clientWorld) {
                Entity entity = clientWorld.getEntity(mob);
                if (entity instanceof GulfTentacle tentacle) {
                    tentacle.setRange(range);
                }
            }
        });
    }
}
