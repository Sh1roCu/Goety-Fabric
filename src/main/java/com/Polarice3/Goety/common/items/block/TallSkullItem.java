package com.Polarice3.Goety.common.items.block;

import cn.sh1rocu.goety.api.extension.client.ICustomRenderer;
import com.Polarice3.Goety.client.render.block.ModISTER;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.StandingAndWallBlockItem;
import net.minecraft.world.level.block.Block;

public class TallSkullItem extends StandingAndWallBlockItem implements ICustomRenderer {

    public TallSkullItem(Block pStandingBlock, Block pWallBlock, Properties pProperties) {
        super(pStandingBlock, pWallBlock, pProperties, Direction.DOWN);
    }

    public static EquipmentSlot getEquipmentSlot(ItemStack stack) {
        return EquipmentSlot.HEAD;
    }

    @Environment(EnvType.CLIENT)
    @Override
    public BlockEntityWithoutLevelRenderer getCustomRenderer() {
        return ModISTER.INSTANCE.get();
    }
}
