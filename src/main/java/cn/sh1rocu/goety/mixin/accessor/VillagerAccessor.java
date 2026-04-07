package cn.sh1rocu.goety.mixin.accessor;

import net.minecraft.world.entity.npc.Villager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Villager.class)
public interface VillagerAccessor {
    @Accessor("foodLevel")
    int goety$getFoodLevel();

    @Accessor("foodLevel")
    void goety$setFoodLevel(int foodLevel);
}
