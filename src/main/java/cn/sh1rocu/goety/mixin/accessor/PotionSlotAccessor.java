package cn.sh1rocu.goety.mixin.accessor;

import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(targets = "net.minecraft.world.inventory.BrewingStandMenu$PotionSlot")
public interface PotionSlotAccessor {

    @Invoker("mayPlaceItem")
    static boolean goety$mayPlaceItem(ItemStack stack) {
        throw new AssertionError();
    }
}
