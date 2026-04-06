package cn.sh1rocu.goety.api.extension.client;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.Model;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public interface IArmorModelHook<T extends LivingEntity, M extends HumanoidModel<T>, A extends HumanoidModel<T>> {

    Model getArmorModelHook(T entity, ItemStack itemStack, EquipmentSlot slot, A model);
}
