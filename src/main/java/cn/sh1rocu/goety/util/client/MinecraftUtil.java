package cn.sh1rocu.goety.util.client;

import cn.sh1rocu.goety.mixin.accessor.MinecraftAccessor;
import net.minecraft.client.Minecraft;

public class MinecraftUtil {

    public static float getPartialTick() {
        Minecraft mc = Minecraft.getInstance();
        return mc.isPaused() ? ((MinecraftAccessor) mc).goety$getPausePartialTick() : mc.getFrameTime();
    }
}
