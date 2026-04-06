package com.Polarice3.Goety.mixin;

import cn.sh1rocu.goety.util.Dummy;
import org.spongepowered.asm.mixin.Mixin;

//@Mixin(ClientModLoader.class)
@Mixin(Dummy.class)
public interface ClientModLoaderAccessor {
//
//    @Accessor(value = "error", remap = false)
//    static LoadingFailedException getError() {
//        throw new IllegalStateException("Failed to inject Accessor");
//    }
}
