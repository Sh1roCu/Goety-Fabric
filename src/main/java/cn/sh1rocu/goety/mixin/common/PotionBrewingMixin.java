package cn.sh1rocu.goety.mixin.common;

import cn.sh1rocu.goety.util.brewing.FabricCustomBrewingRegistry;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.PotionBrewing;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

// From https://github.com/TeamGalena/Nirvana/blob/main/1.20.x/fabric/src/main/java/galena/nirvana/fabric/mixins/PotionBrewingMixin.java
@Mixin(PotionBrewing.class)
public class PotionBrewingMixin {

    @Inject(cancellable = true, at = @At("HEAD"), method = "isIngredient(Lnet/minecraft/world/item/ItemStack;)Z")
    private static void goety$isIngredient(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
        if (FabricCustomBrewingRegistry.isCustomIngredient(stack)) cir.setReturnValue(true);
    }

    @Inject(cancellable = true, at = @At("HEAD"), method = "hasMix(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/item/ItemStack;)Z")
    private static void goety$hasMix(ItemStack input, ItemStack ingredient, CallbackInfoReturnable<Boolean> cir) {
        if (FabricCustomBrewingRegistry.hasCustomRecipe(input, ingredient)) cir.setReturnValue(true);
    }

    @Inject(cancellable = true, at = @At("HEAD"), method = "mix(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/item/ItemStack;)Lnet/minecraft/world/item/ItemStack;")
    private static void goety$mix(ItemStack ingredient, ItemStack input, CallbackInfoReturnable<ItemStack> cir) {
        FabricCustomBrewingRegistry.getCustomRecipe(input, ingredient).ifPresent(recipe -> {
            cir.setReturnValue(recipe.output().copy());
        });
    }

}