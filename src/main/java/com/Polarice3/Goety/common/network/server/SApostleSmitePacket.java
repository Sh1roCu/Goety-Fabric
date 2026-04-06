package com.Polarice3.Goety.common.network.server;

import com.Polarice3.Goety.Goety;
import com.Polarice3.Goety.common.entities.boss.Apostle;
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

public class SApostleSmitePacket {

    public static final ResourceLocation ID = Goety.location("s2c_apostle_smite");

    public static FriendlyByteBuf encode(int apostle, int antiRegen) {
        FriendlyByteBuf buffer = PacketByteBufs.create();
        buffer.writeInt(apostle);
        buffer.writeInt(antiRegen);
        return buffer;
    }

    @Environment(EnvType.CLIENT)
    public static void consume(Minecraft client, ClientPacketListener handler, FriendlyByteBuf buf, PacketSender responseSender) {
        int apostle = buf.readInt();
        int antiRegen = buf.readInt();
        client.execute(() -> {
            Level level = Goety.PROXY.getLevel();
            if (level instanceof ClientLevel clientWorld) {
                Entity entity = clientWorld.getEntity(apostle);
                if (entity instanceof Apostle apostleEntity) {
                    apostleEntity.antiRegenTotal = antiRegen;
                    apostleEntity.antiRegen = antiRegen;
                }
            }
        });
    }

}
