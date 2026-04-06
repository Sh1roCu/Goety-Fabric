package cn.sh1rocu.goety.mixin.common;

import com.Polarice3.Goety.init.ModPotPatterns;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.entity.DecoratedPotPatterns;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(DecoratedPotPatterns.class)
public class DecoratedPotPatternsMixin {

    @ModifyReturnValue(method = "getResourceKey", at = @At("TAIL"))
    private static ResourceKey<String> goety$customTexture(ResourceKey<String> original, @Local(argsOnly = true) Item item) {
        return ModPotPatterns.ITEM_TO_POT_TEXTURE.getOrDefault(item, original);
    }
}
