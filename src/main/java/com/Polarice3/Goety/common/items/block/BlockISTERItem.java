package com.Polarice3.Goety.common.items.block;

import cn.sh1rocu.goety.api.extension.client.ICustomRenderer;
import com.Polarice3.Goety.client.render.block.ModISTER;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.world.level.block.Block;

public class BlockISTERItem extends BlockItemBase implements ICustomRenderer {

    public BlockISTERItem(Block blockIn, Properties properties) {
        super(blockIn, properties);
    }

    public BlockISTERItem(Block blockIn) {
        super(blockIn);
    }

    @Environment(EnvType.CLIENT)
    @Override
    public BlockEntityWithoutLevelRenderer getCustomRenderer() {
        return ModISTER.INSTANCE.get();
    }
}
