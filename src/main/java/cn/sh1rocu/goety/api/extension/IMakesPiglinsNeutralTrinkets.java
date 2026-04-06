package cn.sh1rocu.goety.api.extension;

import dev.emi.trinkets.api.SlotReference;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public interface IMakesPiglinsNeutralTrinkets {

    boolean makesPiglinsNeutral(SlotReference slotContext, ItemStack stack, LivingEntity entity);
}
