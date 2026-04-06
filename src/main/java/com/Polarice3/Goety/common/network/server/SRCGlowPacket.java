package com.Polarice3.Goety.common.network.server;

import com.Polarice3.Goety.Goety;
import com.Polarice3.Goety.common.entities.ally.golem.RedstoneCube;
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

public class SRCGlowPacket {

    public static final ResourceLocation ID = Goety.location("s2c_rc_glow");

    public static FriendlyByteBuf encode(int redstoneCube, float bigGlow) {
        FriendlyByteBuf buffer = PacketByteBufs.create();
        buffer.writeInt(redstoneCube);
        buffer.writeFloat(bigGlow);
        return buffer;
    }

    @Environment(EnvType.CLIENT)
    public static void consume(Minecraft client, ClientPacketListener handler, FriendlyByteBuf buf, PacketSender responseSender) {
        int redstoneCube = buf.readInt();
        float bigGlow = buf.readFloat();
        client.execute(() -> {
            Level level = Goety.PROXY.getLevel();
            if (level instanceof ClientLevel clientWorld) {
                Entity entity = clientWorld.getEntity(redstoneCube);
                if (entity instanceof RedstoneCube redstoneCubeEntity) {
                    redstoneCubeEntity.bigGlow = bigGlow;
                }
            }
        });
    }

}
