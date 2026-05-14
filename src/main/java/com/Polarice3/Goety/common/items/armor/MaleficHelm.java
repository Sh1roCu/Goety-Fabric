package com.Polarice3.Goety.common.items.armor;

import cn.sh1rocu.goety.api.extension.client.ICustomArmorRenderer;
import com.Polarice3.Goety.Goety;
import com.Polarice3.Goety.api.items.IPersist;
import com.Polarice3.Goety.api.items.armor.ISoulDiscount;
import com.Polarice3.Goety.client.render.ModModelLayer;
import com.Polarice3.Goety.client.render.model.MaleficHelmModel;
import com.Polarice3.Goety.common.items.ModItems;
import com.Polarice3.Goety.config.ItemConfig;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Consumer;

public class MaleficHelm extends ArmorItem implements ISoulDiscount, IPersist, ICustomArmorRenderer {

    public MaleficHelm() {
        super(ModArmorMaterials.MALEFIC, Type.HELMET, ModItems.baseProperties().customDamage(MaleficHelm::damageItem));
    }

    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String layer) {
        return Goety.location("textures/models/armor/malefic_helm.png").toString();
    }

    @Override
    public boolean hasCraftingRemainingItem() {
        return true;
    }

    @Nonnull
    @Override
    public ItemStack getRecipeRemainder(ItemStack itemStack) {
        return itemStack.copy();
    }

    @Override
    public boolean isBarVisible(ItemStack stack) {
        return stack.isDamaged();
    }

    @Override
    public int getBarColor(ItemStack stack) {
        if (this.isBroken(stack)) {
            return 0x800000;
        }
        return super.getBarColor(stack);
    }

    @Override
    public int getBarWidth(ItemStack stack) {
        if (this.isBroken(stack)) {
            return 13;
        }
        return super.getBarWidth(stack);
    }

    @Override
    public int getSoulDiscount(EquipmentSlot equipmentSlot, ItemStack stack) {
        if (this.isNotBroken(stack)) {
            return 5;
        } else {
            return 0;
        }
    }

    @Override
    public boolean isBroken(ItemStack stack) {
        return IPersist.super.isBroken(stack) && ItemConfig.MaleficPersist.get();
    }

    private static <T extends LivingEntity> int damageItem(ItemStack stack, int amount, T entity, Consumer<T> onBroken) {
        if (ItemConfig.MaleficPersist.get()) {
            if (stack.getDamageValue() + amount >= stack.getMaxDamage()) {
                if (stack.getDamageValue() != stack.getMaxDamage() - 1) {
                    stack.setDamageValue(stack.getMaxDamage() - 1);
                    onBroken.accept(entity);
                }
                return 0;
            }
        }
        return amount;
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(ItemStack stack, EquipmentSlot slot) {
        if ((this.isNotBroken(stack) || !ItemConfig.MaleficPersist.get()) && slot == EquipmentSlot.HEAD) {
            return super.getAttributeModifiers(stack, slot);
        } else {
            return ImmutableMultimap.of();
        }
    }

    @Environment(EnvType.CLIENT)
    @Override
    public HumanoidModel<LivingEntity> getHumanoidArmorModel(LivingEntity livingEntity, ItemStack itemStack, EquipmentSlot equipmentSlot, HumanoidModel<?> original) {
        EntityModelSet modelSet = Minecraft.getInstance().getEntityModels();
        ModelPart root = modelSet.bakeLayer(ModModelLayer.MALEFIC_HELM);
        MaleficHelmModel model = new MaleficHelmModel(root);
        model.hat.visible = equipmentSlot == EquipmentSlot.HEAD;
        model.body.visible = equipmentSlot == EquipmentSlot.CHEST;
        model.rightArm.visible = equipmentSlot == EquipmentSlot.CHEST;
        model.leftArm.visible = equipmentSlot == EquipmentSlot.CHEST;
        model.rightLeg.visible = equipmentSlot == EquipmentSlot.FEET;
        model.leftLeg.visible = equipmentSlot == EquipmentSlot.FEET;

        model.young = original.young;
        model.crouching = original.crouching;
        model.riding = original.riding;
        model.rightArmPose = original.rightArmPose;
        model.leftArmPose = original.leftArmPose;

        return model;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        int discount = this.getSoulDiscount(LivingEntity.getEquipmentSlotForItem(stack), stack);
        if (discount > 0) {
            tooltip.add(this.soulDiscountTooltip(stack));
        }
        if (ItemConfig.MaleficPersist.get() && this.isBroken(stack)) {
            tooltip.add(Component.translatable("info.goety.armor.broken").withStyle(ChatFormatting.DARK_RED));
        }
    }
}
