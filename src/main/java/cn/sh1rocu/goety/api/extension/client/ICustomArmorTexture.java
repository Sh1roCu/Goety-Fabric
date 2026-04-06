package cn.sh1rocu.goety.api.extension.client;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public interface ICustomArmorTexture {

    @Nullable
    String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type);

}
