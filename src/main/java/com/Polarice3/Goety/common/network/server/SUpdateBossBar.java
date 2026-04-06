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
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;

import java.util.UUID;

public class SUpdateBossBar {

    public static final ResourceLocation ID = Goety.location("s2c_update_boss_bar");

    public static FriendlyByteBuf encode(UUID bar, Mob boss, boolean remove) {
        return encode(bar, boss.getId(), remove);
    }

    public static FriendlyByteBuf encode(UUID bar, int boss, boolean remove) {
        FriendlyByteBuf buffer = PacketByteBufs.create();
        buffer.writeUUID(bar);
        buffer.writeInt(boss);
        buffer.writeBoolean(remove);
        return buffer;
    }

    @Environment(EnvType.CLIENT)
    public static void consume(Minecraft client, ClientPacketListener handler, FriendlyByteBuf buf, PacketSender responseSender) {
        UUID bar = buf.readUUID();
        int boss = buf.readInt();
        boolean remove = buf.readBoolean();
        client.execute(() -> {
            Player player = Goety.PROXY.getPlayer();
            if (player != null) {
                Entity entity = player.level.getEntity(boss);
                if (entity instanceof Mob mob) {
                    if (remove) {
                        Goety.PROXY.removeBossBar(bar, mob);
                    } else {
                        Goety.PROXY.addBossBar(bar, mob);
                    }
                }
            }
        });
    }
}
