package cn.sh1rocu.goety.util;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class BlockUtil {

    public static float @Nullable [] getBeaconColorMultiplier(Block block, BlockState state, LevelReader level, BlockPos pos, BlockPos beaconPos) {
        if (block instanceof BeaconBeamBlock)
            return ((BeaconBeamBlock) block).getColor().getTextureDiffuseColors();
        return null;
    }

    public static boolean isPlantable(BlockState state) {
        Block block = state.getBlock();
        return block instanceof CropBlock || block == Blocks.PITCHER_CROP ||
                block instanceof SaplingBlock || block instanceof FlowerBlock ||
                block == Blocks.DEAD_BUSH || block == Blocks.LILY_PAD ||
                block == Blocks.RED_MUSHROOM || block == Blocks.BROWN_MUSHROOM ||
                block == Blocks.NETHER_WART || block == Blocks.TALL_GRASS;
    }

}
