package com.Polarice3.Goety.common.network;

import com.Polarice3.Goety.Goety;
import com.Polarice3.Goety.utils.EntityFinder;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;

import java.util.UUID;

public class EntityUpdatePacket {

    public static final ResourceLocation ID = Goety.location("s2c_entity_update");

    public static FriendlyByteBuf encode(UUID livingEntityUUID, CompoundTag tag) {
        FriendlyByteBuf buffer = PacketByteBufs.create();
        buffer.writeUUID(livingEntityUUID);
        buffer.writeNbt(tag);
        return buffer;
    }

    @Environment(EnvType.CLIENT)
    public static void consume(Minecraft client, ClientPacketListener handler, FriendlyByteBuf buf, PacketSender responseSender) {
        var livingEntityUUID = buf.readUUID();
        var tag = buf.readNbt();
        if (tag == null) return;
        client.execute(() -> {
            EntityFinder.getEntityByUuiDGlobal(livingEntityUUID).ifPresent(entity -> {
                if (entity instanceof LivingEntity livingEntity) {
                    livingEntity.readAdditionalSaveData(tag);
                }
            });
        });
    }
}
