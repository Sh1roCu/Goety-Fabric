package cn.sh1rocu.goety.mixin.accessor;

import net.minecraft.data.models.model.ModelTemplate;
import net.minecraft.data.models.model.ModelTemplates;
import net.minecraft.data.models.model.TextureSlot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(ModelTemplates.class)
public interface ModelTemplatesAccessor {
    @Invoker("createItem")
    static ModelTemplate goety$createItem(String string, TextureSlot... textureSlots) {
        throw new AssertionError();
    }
}
