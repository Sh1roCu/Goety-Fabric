package com.Polarice3.Goety.common.network.server;

import com.Polarice3.Goety.Goety;
import com.Polarice3.Goety.utils.ColorUtil;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;

public class SLightningPacket {

    public static final ResourceLocation ID = Goety.location("s2c_lightning");

    public static FriendlyByteBuf encode(Vec3 start, Vec3 end, int lifespan) {
        return encode(start, end, new ColorUtil(0xb1abf1), lifespan);
    }

    public static FriendlyByteBuf encode(Vec3 start, Vec3 end, ColorUtil colorUtil, int lifespan) {
        return encode(start.x(), start.y(), start.z(), end.x(), end.y(), end.z(), colorUtil.red(), colorUtil.green(), colorUtil.blue(), lifespan);
    }

    public static FriendlyByteBuf encode(double x, double y, double z, double x2, double y2, double z2, float red, float green, float blue, int lifespan) {
        FriendlyByteBuf buffer = PacketByteBufs.create();
        buffer.writeDouble(x);
        buffer.writeDouble(y);
        buffer.writeDouble(z);
        buffer.writeDouble(x2);
        buffer.writeDouble(y2);
        buffer.writeDouble(z2);
        buffer.writeFloat(red);
        buffer.writeFloat(green);
        buffer.writeFloat(blue);
        buffer.writeInt(lifespan);
        return buffer;
    }

    @Environment(EnvType.CLIENT)
    public static void consume(Minecraft client, ClientPacketListener handler, FriendlyByteBuf buf, PacketSender responseSender) {
        double x = buf.readDouble();
        double y = buf.readDouble();
        double z = buf.readDouble();
        double x2 = buf.readDouble();
        double y2 = buf.readDouble();
        double z2 = buf.readDouble();
        float red = buf.readFloat();
        float green = buf.readFloat();
        float blue = buf.readFloat();
        int lifespan = buf.readInt();
        client.execute(() -> {
            Vec3 start = new Vec3(x, y, z);
            Vec3 end = new Vec3(x2, y2, z2);
            ColorUtil colorUtil = new ColorUtil(red, green, blue, 0.8F);
            Goety.PROXY.shock(start, end, colorUtil, lifespan);
        });
    }
}
