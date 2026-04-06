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
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.player.Player;

public class SPlayPlayerSoundPacket {

    public static final ResourceLocation ID = Goety.location("s2c_play_player_sound");

    public static FriendlyByteBuf encode(SoundEvent soundEvent, float volume, float pitch) {
        FriendlyByteBuf buffer = PacketByteBufs.create();
        buffer.writeResourceLocation(soundEvent.getLocation());
        buffer.writeFloat(volume);
        buffer.writeFloat(pitch);
        return buffer;
    }

    @Environment(EnvType.CLIENT)
    public static void consume(Minecraft client, ClientPacketListener handler, FriendlyByteBuf buf, PacketSender responseSender) {
        SoundEvent soundEvent = SoundEvent.createVariableRangeEvent(buf.readResourceLocation());
        float volume = buf.readFloat();
        float pitch = buf.readFloat();
        client.execute(() -> {
            Player player = Goety.PROXY.getPlayer();
            if (player != null) {
                player.playSound(soundEvent, volume, pitch);
            }
        });
    }
}
