package cn.sh1rocu.goety.mixin.accessor;

import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.crafting.Ingredient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;

@Mixin(PotionBrewing.class)
public interface PotionBrewingAccessor {

    @Accessor("ALLOWED_CONTAINERS")
    static List<Ingredient> goety$getAllowedContainers() {
        throw new AssertionError();
    }

}
