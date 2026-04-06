package com.Polarice3.Goety.common.network.server;

import com.Polarice3.Goety.Goety;
import com.Polarice3.Goety.api.entities.IOwned;
import com.Polarice3.Goety.utils.EntityFinder;
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
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import java.util.UUID;

public class SSetPlayerOwnerPacket {

    public static final ResourceLocation ID = Goety.location("s2c_set_player_owner");

    public static FriendlyByteBuf encode(Entity summoned) {
        return encode(summoned.getUUID());
    }

    public static FriendlyByteBuf encode(UUID summoned) {
        FriendlyByteBuf buffer = PacketByteBufs.create();
        buffer.writeUUID(summoned);
        return buffer;
    }

    @Environment(EnvType.CLIENT)
    public static void consume(Minecraft client, ClientPacketListener handler, FriendlyByteBuf buf, PacketSender responseSender) {
        UUID summoned = buf.readUUID();
        client.execute(() -> {
            Level level = Goety.PROXY.getLevel();
            if (level instanceof ClientLevel clientWorld) {
                Entity entity = EntityFinder.getEntityByUuiDGlobal(summoned).isPresent() ? EntityFinder.getEntityByUuiDGlobal(summoned).get() : null;
                Player playerEntity = Goety.PROXY.getPlayer();
                if (entity != null && playerEntity != null) {
                    if (entity instanceof IOwned owned) {
                        owned.setTrueOwner(playerEntity);
                    }
                }
            }
        });
    }
}
