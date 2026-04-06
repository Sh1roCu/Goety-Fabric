package com.Polarice3.Goety.common.network.client;

import com.Polarice3.Goety.Goety;
import com.Polarice3.Goety.api.entities.IOwned;
import com.Polarice3.Goety.init.ModSounds;
import com.Polarice3.Goety.utils.MobUtil;
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
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.NeutralMob;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;

public class CStopAttackPacket {

    public static final ResourceLocation ID = Goety.location("c2s_stop_attack");

    public static final FriendlyByteBuf DUMMY = PacketByteBufs.empty();

    public static void consume(MinecraftServer server, ServerPlayer playerEntity, ServerGamePacketListenerImpl handler, FriendlyByteBuf buf, PacketSender responseSender) {
        server.execute(() -> {
            if (playerEntity != null) {
                if (playerEntity.level instanceof ServerLevel serverLevel) {
                    for (Entity entity : serverLevel.getAllEntities()) {
                        if (entity instanceof IOwned owned && entity instanceof Mob mob && owned.getTrueOwner() == playerEntity) {
                            Entity pick = MobUtil.getSingleTarget(serverLevel, playerEntity, 16, 3);
                            if (pick instanceof LivingEntity target
                                    && target != mob
                                    && target != owned.getTrueOwner()
                                    && !MobUtil.areAllies(playerEntity, target)
                                    && mob.distanceTo(playerEntity) <= 32) {
                                mob.setTarget(target);
                                if (mob.getLastHurtByMob() != target) {
                                    mob.setLastHurtByMob(target);
                                }
                                entity.playSound(ModSounds.ROAR_SPELL, 0.5F, 2.0F);
                                owned.onStopAttack();
                            } else {
                                owned.onCeaseFire(playerEntity);
                                if (mob.getTarget() != null) {
                                    mob.setTarget(null);
                                    if (mob.getLastHurtByMob() != null) {
                                        mob.setLastHurtByMob(null);
                                    }
                                    if (playerEntity.getLastHurtMob() != null) {
                                        playerEntity.setLastHurtMob(null);
                                    }
                                    if (mob.getBrain() != null) {
                                        mob.getBrain().eraseMemory(MemoryModuleType.ATTACK_TARGET);
                                        mob.getBrain().eraseMemory(MemoryModuleType.ANGRY_AT);
                                        mob.getBrain().eraseMemory(MemoryModuleType.HURT_BY);
                                    }
                                    if (mob instanceof NeutralMob neutralMob) {
                                        neutralMob.setPersistentAngerTarget(null);
                                        neutralMob.stopBeingAngry();
                                    }
                                    mob.setAggressive(false);
                                    entity.playSound(ModSounds.CAST_SPELL, 1.0F, 1.0F);
                                    owned.onStopAttack();
                                }
                            }
                        }
                    }
                }
            }
        });
    }
}
