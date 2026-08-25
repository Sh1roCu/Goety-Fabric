package cn.sh1rocu.goety.api.extension;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;

public interface IBedBlock {
    boolean isBed(BlockState state, BlockGetter world, BlockPos pos, @Nullable Entity entity);

    void setBedOccupied(BlockState state, Level level, BlockPos pos, LivingEntity sleeper, boolean occupied);
}