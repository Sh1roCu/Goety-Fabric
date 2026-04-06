package cn.sh1rocu.goety.util.forge;

import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.block.state.BlockState;

public class TierSorting {

    public static boolean isCorrectTierVanilla(Tier tier, BlockState state) {
        int i = tier.getLevel();
        if (i < 3 && state.is(BlockTags.NEEDS_DIAMOND_TOOL)) {
            return false;
        } else if (i < 2 && state.is(BlockTags.NEEDS_IRON_TOOL)) {
            return false;
        } else return i >= 1 || !state.is(BlockTags.NEEDS_STONE_TOOL);
    }
}
