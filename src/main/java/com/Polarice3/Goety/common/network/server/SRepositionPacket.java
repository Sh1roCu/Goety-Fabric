package com.Polarice3.Goety.common.network.server;

import com.Polarice3.Goety.Goety;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class SRepositionPacket {

    public static final ResourceLocation ID = Goety.location("s2c_reposition");

    public static FriendlyByteBuf encode(int id, Vec3 vec3) {
        return encode(id, vec3.x(), vec3.y(), vec3.z());
    }

    public static FriendlyByteBuf encode(int id, double x, double y, double z) {
        FriendlyByteBuf buffer = PacketByteBufs.create();
        buffer.writeInt(id);
        buffer.writeDouble(x);
        buffer.writeDouble(y);
        buffer.writeDouble(z);
        return buffer;
    }

    @Environment(EnvType.CLIENT)
    public static void consume(Minecraft client, ClientPacketListener handler, FriendlyByteBuf buf, PacketSender responseSender) {
        int mob = buf.readInt();
        double x = buf.readDouble();
        double y = buf.readDouble();
        double z = buf.readDouble();
        client.execute(() -> {
            Level level = Goety.PROXY.getLevel();
            if (level instanceof ClientLevel clientWorld) {
                Entity entity = clientWorld.getEntity(mob);
                if (entity != null) {
                    entity.setPos(x, y, z);
                    entity.xo = x;
                    entity.yo = y;
                    entity.zo = z;
                    entity.xOld = x;
                    entity.yOld = y;
                    entity.zOld = z;
                    entity.setDeltaMovement(Vec3.ZERO);
                    if (entity instanceof LocalPlayer) {
                        return;
                    }
                    if (entity instanceof LivingEntity living) {
                        living.lerpTo(x, y, z, entity.getYRot(), entity.getXRot(), 0, false);
                    }
                }
            }
        });
    }
}
