package com.Polarice3.Goety.common.network.client;

import com.Polarice3.Goety.Goety;
import com.Polarice3.Goety.common.entities.projectiles.AbstractBeam;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.world.entity.Entity;

public class CBeamPacket {

    public static final ResourceLocation ID = Goety.location("c2s_beam");

    public static FriendlyByteBuf encode(AbstractBeam corruptedBeam) {
        var position = corruptedBeam.position();
        return encode(corruptedBeam.getId(), position.x(), position.y(), position.z(), corruptedBeam.getXRot(), corruptedBeam.getYRot(), corruptedBeam.xRotO, corruptedBeam.yRotO);
    }

    public static FriendlyByteBuf encode(int beamEntityID, double positionX, double positionY, double positionZ, float xRot, float yRot, float xRotO, float yRotO) {
        FriendlyByteBuf buf = PacketByteBufs.create();
        buf.writeInt(beamEntityID);
        buf.writeDouble(positionX);
        buf.writeDouble(positionY);
        buf.writeDouble(positionZ);
        buf.writeFloat(xRot);
        buf.writeFloat(yRot);
        buf.writeFloat(xRotO);
        buf.writeFloat(yRotO);
        return buf;
    }

    public static void consume(MinecraftServer server, ServerPlayer player, ServerGamePacketListenerImpl handler, FriendlyByteBuf buf, PacketSender responseSender) {
        int beamEntityID = buf.readInt();
        double positionX = buf.readDouble();
        double positionY = buf.readDouble();
        double positionZ = buf.readDouble();
        float xRot = buf.readFloat();
        float yRot = buf.readFloat();
        float xRotO = buf.readFloat();
        float yRotO = buf.readFloat();
        server.execute(() -> {
            if (player != null) {
                Entity entity = player.level.getEntity(beamEntityID);
                if (entity instanceof AbstractBeam corruptedBeam) {
                    if (corruptedBeam.getOwner() != player) {
                        return;
                    }
                    corruptedBeam.setPos(positionX, positionY, positionZ);
                    corruptedBeam.setXRot(xRot);
                    corruptedBeam.setYRot(yRot);
                    corruptedBeam.xRotO = xRotO;
                    corruptedBeam.yRotO = yRotO;
                }
            }
        });
    }
}
