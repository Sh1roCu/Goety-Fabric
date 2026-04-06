package cn.sh1rocu.goety.mixin.accessor;

import net.minecraft.client.renderer.blockentity.ChestRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ChestRenderer.class)
public interface ChestRendererAccessor {

    @Accessor("xmasTextures")
    boolean goety$xmasTextures();
}
