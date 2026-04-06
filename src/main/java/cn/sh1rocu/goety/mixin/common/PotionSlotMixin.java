package cn.sh1rocu.goety.mixin.common;

import cn.sh1rocu.goety.util.brewing.FabricCustomBrewingRegistry;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

// From https://github.com/TeamGalena/Nirvana/blob/main/1.20.x/fabric/src/main/java/galena/nirvana/fabric/mixins/PotionSlotMixin.java
@Mixin(targets = "net.minecraft.world.inventory.BrewingStandMenu$PotionSlot")
public class PotionSlotMixin {

    @Inject(cancellable = true, at = @At("HEAD"), method = "mayPlaceItem(Lnet/minecraft/world/item/ItemStack;)Z")
    private static void isIngredient(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
        if (FabricCustomBrewingRegistry.isCustomInput(stack)) cir.setReturnValue(true);
    }

}