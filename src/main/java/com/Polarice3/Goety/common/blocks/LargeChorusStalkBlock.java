package com.Polarice3.Goety.common.blocks;

import com.Polarice3.Goety.init.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

public class LargeChorusStalkBlock extends DoublePlantBlock {

    public LargeChorusStalkBlock() {
        super(Properties.of()
                .mapColor(MapColor.COLOR_PURPLE)
                .replaceable()
                .noCollission()
                .instabreak()
                .lightLevel(l -> 4)
                .emissiveRendering((i, d, k) -> true)
                .sound(SoundType.STEM)
                .offsetType(OffsetType.XYZ)
                .pushReaction(PushReaction.DESTROY));
    }

    @Override
    protected boolean mayPlaceOn(BlockState state, BlockGetter world, BlockPos pos) {
        return state.is(ModTags.Blocks.CHORUS_GROW) || state.isSolidRender(world, pos);
    }
}
