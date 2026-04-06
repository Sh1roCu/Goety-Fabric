package com.Polarice3.Goety.common.network.server;

import com.Polarice3.Goety.Goety;
import com.Polarice3.Goety.utils.FungusExplosion;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;

public class SFungusExplosionPacket {

    public static final ResourceLocation ID = Goety.location("s2c_fungus_explosion");

    public static FriendlyByteBuf encode(double x, double y, double z, float power, @Nullable Vec3 vec3) {
        FriendlyByteBuf buffer = PacketByteBufs.create();
        buffer.writeFloat((float) x);
        buffer.writeFloat((float) y);
        buffer.writeFloat((float) z);
        buffer.writeFloat(power);
        float knockbackX;
        float knockbackY;
        float knockbackZ;
        if (vec3 != null) {
            knockbackX = (float) vec3.x;
            knockbackY = (float) vec3.y;
            knockbackZ = (float) vec3.z;
        } else {
            knockbackX = 0.0F;
            knockbackY = 0.0F;
            knockbackZ = 0.0F;
        }
        buffer.writeFloat(knockbackX);
        buffer.writeFloat(knockbackY);
        buffer.writeFloat(knockbackZ);

        return buffer;
    }

    @Environment(EnvType.CLIENT)
    public static void consume(Minecraft client, ClientPacketListener handler, FriendlyByteBuf buf, PacketSender responseSender) {
        float x = buf.readFloat();
        float y = buf.readFloat();
        float z = buf.readFloat();
        float power = buf.readFloat();
        float knockbackX = buf.readFloat();
        float knockbackY = buf.readFloat();
        float knockbackZ = buf.readFloat();
        client.execute(() -> {
            Player player = Goety.PROXY.getPlayer();
            if (player != null) {
                FungusExplosion explosion = new FungusExplosion(player.level, null, x, y, z, power, false);
                explosion.finalizeExplosion(true);
                player.setDeltaMovement(player.getDeltaMovement().add(knockbackX, knockbackY, knockbackZ));
            }
        });
    }
}
