package com.Polarice3.Goety.common.network.server;

import com.Polarice3.Goety.Goety;
import com.Polarice3.Goety.client.audio.LoopSoundPlayer;
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

public class SPlayLoopSoundPacket {

    public static final ResourceLocation ID = Goety.location("s2c_play_loop_sound");

    public static FriendlyByteBuf encode(Entity entity, SoundEvent soundEvent, float volume, float pitch) {
        return encode(entity.getId(), soundEvent, volume, pitch);
    }

    public static FriendlyByteBuf encode(int entityId, SoundEvent soundEvent, float volume, float pitch) {
        FriendlyByteBuf buffer = PacketByteBufs.create();
        buffer.writeInt(entityId);
        buffer.writeResourceLocation(soundEvent.getLocation());
        buffer.writeFloat(volume);
        buffer.writeFloat(pitch);
        return buffer;
    }

    @Environment(EnvType.CLIENT)
    public static void consume(Minecraft client, ClientPacketListener handler, FriendlyByteBuf buf, PacketSender responseSender) {
        int entityId = buf.readInt();
        SoundEvent soundEvent = SoundEvent.createVariableRangeEvent(buf.readResourceLocation());
        float volume = buf.readFloat();
        float pitch = buf.readFloat();
        client.execute(() -> {
            Level level = Goety.PROXY.getLevel();
            if (level instanceof ClientLevel clientWorld) {
                if (entityId >= 0) {
                    Entity entity = clientWorld.getEntity(entityId);
                    if (entity != null) {
                        LoopSoundPlayer.playSound(entity, soundEvent, volume, pitch);
                    }
                }
            }
        });
    }
}
