package cn.sh1rocu.goety.api.event;

import com.Polarice3.Goety.Goety;
import net.minecraft.resources.ResourceLocation;

public class BaseEvent {
    protected boolean isCanceled = false;

    public static final ResourceLocation LOWEST = Goety.location("lowest");
    public static final ResourceLocation LOW = Goety.location("low");
    public static final ResourceLocation HIGH = Goety.location("high");
    public static final ResourceLocation HIGHEST = Goety.location("highest");
}