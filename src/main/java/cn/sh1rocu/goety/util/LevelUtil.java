package cn.sh1rocu.goety.util;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;

public class LevelUtil {

    public static boolean isAreaLoaded(ServerLevel serverLevel, BlockPos center, int range) {
        return serverLevel.hasChunksAt(center.offset(-range, -range, -range), center.offset(range, range, range));
    }

}
