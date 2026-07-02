package com.Polarice3.Goety.common.network.server;

import com.Polarice3.Goety.Goety;
import com.Polarice3.Goety.utils.MobUtil;
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
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class SInstaLookAtPacket {
    public static final ResourceLocation ID = Goety.location("s2c_insta_look_at");

    public static FriendlyByteBuf encode(Mob looker, Vec3 vec3) {
        return encode(looker.getId(), vec3);
    }

    public static FriendlyByteBuf encode(int lookerId, Vec3 vec3) {
        FriendlyByteBuf buffer = PacketByteBufs.create();
        buffer.writeInt(lookerId);
        buffer.writeVector3f(vec3.toVector3f());
        return buffer;
    }

    @Environment(EnvType.CLIENT)
    public static void consume(Minecraft client, ClientPacketListener handler, FriendlyByteBuf buf, PacketSender responseSender) {
        int lookerId = buf.readInt();
        Vec3 target = new Vec3(buf.readVector3f());
        client.execute(() -> {
            Level level = Goety.PROXY.getLevel();
            if (level instanceof ClientLevel clientWorld) {
                Entity looker = clientWorld.getEntity(lookerId);
                if (looker instanceof Mob mob && target != null) {
                    MobUtil.instaLook(mob, target);
                }
            }
        });
    }
}
