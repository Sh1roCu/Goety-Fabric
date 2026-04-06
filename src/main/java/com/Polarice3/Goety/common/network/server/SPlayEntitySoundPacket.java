package com.Polarice3.Goety.common.network.server;

import com.Polarice3.Goety.Goety;
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
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;

import java.util.Optional;
import java.util.UUID;

public class SPlayEntitySoundPacket {

    public static final ResourceLocation ID = Goety.location("s2c_play_entity_sound");

    public static FriendlyByteBuf encode(UUID uuid, SoundEvent soundEvent, float volume, float pitch) {
        FriendlyByteBuf buffer = PacketByteBufs.create();
        buffer.writeUUID(uuid);
        buffer.writeResourceLocation(soundEvent.getLocation());
        buffer.writeFloat(volume);
        buffer.writeFloat(pitch);
        return buffer;
    }

    @Environment(EnvType.CLIENT)
    public static void consume(Minecraft client, ClientPacketListener handler, FriendlyByteBuf buf, PacketSender responseSender) {
        UUID uuid = buf.readUUID();
        SoundEvent soundEvent = SoundEvent.createVariableRangeEvent(buf.readResourceLocation());
        float volume = buf.readFloat();
        float pitch = buf.readFloat();
        client.execute(() -> {
            Level level = Goety.PROXY.getLevel();
            if (level instanceof ClientLevel clientWorld) {
                Optional<? extends Entity> optionalEntity = EntityFinder.getEntityByUuiDGlobal(uuid);
                if (optionalEntity.isPresent()) {
                    Entity entity = optionalEntity.get();
                    clientWorld.playLocalSound(entity.blockPosition(), soundEvent, entity.getSoundSource(), volume, pitch, false);
                }
            }
        });
    }
}
