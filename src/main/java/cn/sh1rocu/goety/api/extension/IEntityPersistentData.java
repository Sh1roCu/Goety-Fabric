package cn.sh1rocu.goety.api.extension;

import net.minecraft.nbt.CompoundTag;

public interface IEntityPersistentData {

    String PLAYER_PERSISTED_NBT_TAG = "PlayerPersisted";
    String FORGE_TAG = "ForgeData";

    CompoundTag goety$getPersistentData();
}