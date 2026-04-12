package cn.sh1rocu.goety.api.extension.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public interface ICustomArmorRenderer {

    @Environment(EnvType.CLIENT)
    HumanoidModel<LivingEntity> getHumanoidArmorModel(LivingEntity livingEntity, ItemStack itemStack, EquipmentSlot equipmentSlot, HumanoidModel<?> parent);

    String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type);
}
