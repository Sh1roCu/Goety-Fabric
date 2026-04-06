package com.Polarice3.Goety.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.PlayerModelPart;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class ClientUtils {
    public static boolean noLoadingExceptions() {
//        System.out.println();
//        LoadingFailedException error = ClientModLoaderAccessor.getError();
//        return error == null || error.getErrors().isEmpty();
        return true;
    }

    public static PlayerInfo getPlayerInfo(UUID playerId) {
        if (playerId != null) {
            ClientPacketListener listener = Minecraft.getInstance().getConnection();
            if (listener != null) {
                return listener.getPlayerInfo(playerId);
            }
        }
        return null;
    }

    public static ResourceLocation getSkinTextureLocation(UUID uuid, @Nullable PlayerInfo playerInfo) {
        return playerInfo == null ? DefaultPlayerSkin.getDefaultSkin(uuid) : playerInfo.getSkinLocation();
    }

    public static String getModelName(UUID uuid, @Nullable PlayerInfo playerInfo) {
        return playerInfo == null ? DefaultPlayerSkin.getSkinModelName(uuid) : playerInfo.getModelName();
    }

    @Nullable
    public static ResourceLocation getCloakTextureLocation(@Nullable PlayerInfo playerInfo) {
        return playerInfo == null ? null : playerInfo.getCapeLocation();
    }

    public static boolean isModelPartShown(byte customization, PlayerModelPart part) {
        return (customization & part.getMask()) == part.getMask();
    }
}
