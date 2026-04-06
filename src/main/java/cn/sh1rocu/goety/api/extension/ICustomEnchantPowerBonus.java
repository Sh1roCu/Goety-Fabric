package cn.sh1rocu.goety.api.extension;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;

public interface ICustomEnchantPowerBonus {

    float getEnchantPowerBonus(BlockState state, LevelReader level, BlockPos pos);
}
