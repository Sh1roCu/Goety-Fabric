package com.Polarice3.Goety.common.network.client;

import com.Polarice3.Goety.Goety;
import com.Polarice3.Goety.common.entities.ally.illager.raider.Prisoner;
import com.Polarice3.Goety.config.MobsConfig;
import com.Polarice3.Goety.utils.ItemHelper;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.world.entity.Entity;

public class CPrisonerMinePacket {

    public static final ResourceLocation ID = Goety.location("c2s_prisoner_mine");

    public static FriendlyByteBuf encode(int mob) {
        FriendlyByteBuf buffer = PacketByteBufs.create();
        buffer.writeInt(mob);
        return buffer;
    }

    public static void consume(MinecraftServer server, ServerPlayer playerEntity, ServerGamePacketListenerImpl handler, FriendlyByteBuf buf, PacketSender responseSender) {
        int mob = buf.readInt();
        server.execute(() -> {
            if (playerEntity != null) {
                Entity entity = playerEntity.level.getEntity(mob);
                if (entity instanceof Prisoner prisoner) {
                    ItemHelper.hurtAndBreak(prisoner.getMainHandItem(), MobsConfig.PrisonerMiningDurability.get(), prisoner);
                    prisoner.mineTimes += 1;
                }
            }
        });
    }
}
