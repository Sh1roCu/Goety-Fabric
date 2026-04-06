package com.Polarice3.Goety.common.network.client;

import com.Polarice3.Goety.Goety;
import com.Polarice3.Goety.api.entities.IOwned;
import com.Polarice3.Goety.init.ModSounds;
import com.Polarice3.Goety.utils.ModDamageSource;
import com.Polarice3.Goety.utils.SEHelper;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

public class CDismissServantsPacket {

    public static final ResourceLocation ID = Goety.location("c2s_dismiss_curio_key");

    public static final FriendlyByteBuf DUMMY = PacketByteBufs.empty();

    public static void consume(MinecraftServer server, ServerPlayer playerEntity, ServerGamePacketListenerImpl handler, FriendlyByteBuf buf, PacketSender responseSender) {
        server.execute(() -> {
            if (playerEntity != null) {
                if (playerEntity.level instanceof ServerLevel serverLevel) {
                    for (Entity entity : serverLevel.getAllEntities()) {
                        if (entity instanceof IOwned owned && owned instanceof LivingEntity livingEntity && owned.getTrueOwner() == playerEntity) {
                            if (owned.isLimitedLife() && !SEHelper.getGroundedEntities(playerEntity).contains(livingEntity) && !SEHelper.getGroundedEntityTypes(playerEntity).contains(entity.getType())) {
                                entity.hurt(ModDamageSource.getDamageSource(serverLevel, ModDamageSource.DISMISSED), Float.MAX_VALUE);
                                entity.playSound(ModSounds.ROAR_SPELL, 0.5F, 2.0F);
                            }
                        }
                    }
                }
            }
        });
    }
}
