package cn.sh1rocu.goety.api.extension;

import net.minecraft.client.resources.model.Material;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.LidBlockEntity;
import net.minecraft.world.level.block.state.properties.ChestType;

public interface IChestMaterial<T extends BlockEntity & LidBlockEntity> {

    Material getMaterial(T blockEntity, ChestType chestType);

}
