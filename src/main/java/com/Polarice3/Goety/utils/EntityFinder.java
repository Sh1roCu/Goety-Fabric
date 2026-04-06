package com.Polarice3.Goety.utils;

import cn.sh1rocu.goety.GoetyFabric;
import cn.sh1rocu.goety.api.extension.IEntityPersistentData;
import com.Polarice3.Goety.common.network.EntityUpdatePacket;
import com.Polarice3.Goety.common.network.ModNetwork;
import net.minecraft.core.BlockPos;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.UUID;

public class EntityFinder {
    public static Optional<ServerPlayer> getPlayerByUuiDGlobal(UUID uuid) {
        if (GoetyFabric.getServer() == null) return Optional.empty();

        for (ServerLevel world : GoetyFabric.getServer().getAllLevels()) {
            ServerPlayer player = (ServerPlayer) world.getPlayerByUUID(uuid);
            if (player != null)
                return Optional.of(player);
        }
        return Optional.empty();
    }

    public static Optional<? extends Entity> getEntityByUuiDGlobal(UUID uuid) {
        var server = GoetyFabric.getServer();
        if (server == null) return Optional.empty();

        return getEntityByUuiDGlobal(server, uuid);
    }

    public static Optional<? extends Entity> getEntityByUuiDGlobal(MinecraftServer server, UUID uuid) {
        if (uuid != null && server != null) {
            for (ServerLevel world : server.getAllLevels()) {
                Entity entity = world.getEntity(uuid);
                if (entity != null)
                    return Optional.of(entity);
            }
        }
        return Optional.empty();
    }

    public static Player getServerPlayersByUuiD(UUID uuid) {
        return getServerPlayersByUuiD(GoetyFabric.getServer(), uuid);
    }

    public static Player getServerPlayersByUuiD(MinecraftServer server, UUID uuid) {
        if (uuid != null && server != null) {
            for (ServerLevel world : server.getAllLevels()) {
                return world.getPlayerByUUID(uuid);
            }
        }
        return null;
    }

    @Nullable
    public static Entity getEntityByUuiD(Level level, UUID uuid) {
        return getEntityByUuiD(level.getServer(), uuid);
    }

    @Nullable
    public static Entity getEntityByUuiD(UUID uuid) {
        return getEntityByUuiD(GoetyFabric.getServer(), uuid);
    }

    @Nullable
    public static Entity getEntityByUuiD(MinecraftServer server, UUID uuid) {
        if (uuid != null && server != null) {
            for (ServerLevel world : server.getAllLevels()) {
                Entity entity = world.getEntity(uuid);
                if (entity != null) {
                    return world.getEntity(uuid);
                }
            }
        }
        return null;
    }

    public static LivingEntity getLivingEntityByUuiD(Level level, UUID uuid) {
        return getLivingEntityByUuiD(level.getServer(), uuid);
    }

    public static LivingEntity getLivingEntityByUuiD(UUID uuid) {
        return getLivingEntityByUuiD(GoetyFabric.getServer(), uuid);
    }

    public static LivingEntity getLivingEntityByUuiD(MinecraftServer server, UUID uuid) {
        if (uuid != null && server != null) {
            for (ServerLevel world : server.getAllLevels()) {
                Entity entity = world.getEntity(uuid);
                if (entity instanceof LivingEntity) {
                    return (LivingEntity) entity;
                }
            }
        }
        return null;
    }

    public static Player getNearbyPlayer(Level world, BlockPos blockPos) {
        for (Player player : world.getEntitiesOfClass(Player.class, new AABB(blockPos).inflate(256))) {
            return player;
        }
        return null;
    }

    public static void sendEntityUpdatePacket(Player player, LivingEntity livingEntity) {
        ModNetwork.sendTo(player, EntityUpdatePacket.ID, EntityUpdatePacket.encode(livingEntity.getUUID(), ((IEntityPersistentData) livingEntity).goety$getPersistentData()));
    }

    public static void sendEntityUpdatePacket(LivingEntity livingEntity) {
        var server = livingEntity.getServer();
        if (server == null) return;

        ModNetwork.sendToALL(server, EntityUpdatePacket.ID, EntityUpdatePacket.encode(livingEntity.getUUID(), ((IEntityPersistentData) livingEntity).goety$getPersistentData()));
    }
}
