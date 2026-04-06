package cn.sh1rocu.goety.mixin.client;

import cn.sh1rocu.goety.api.mixin.interfaces.IBlurMipmap;
import net.minecraft.client.renderer.texture.AbstractTexture;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

@Mixin(AbstractTexture.class)
public abstract class AbstractTextureMixin implements IBlurMipmap {

    @Shadow
    protected boolean blur;
    @Shadow
    protected boolean mipmap;

    @Shadow
    public abstract void setFilter(boolean blur, boolean mipmap);

    @Unique
    private boolean goety$lastBlur;
    @Unique
    private boolean goety$lastMipmap;

    @Override
    public void goety$setBlurMipmap(boolean blur, boolean mipmap) {
        this.goety$lastBlur = this.blur;
        this.goety$lastMipmap = this.mipmap;
        this.setFilter(blur, mipmap);
    }

    @Override
    public void goety$restoreLastBlurMipmap() {
        this.setFilter(this.goety$lastBlur, this.goety$lastMipmap);
    }
}
