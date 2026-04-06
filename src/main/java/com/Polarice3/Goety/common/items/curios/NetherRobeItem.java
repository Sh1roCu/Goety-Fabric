package com.Polarice3.Goety.common.items.curios;

import cn.sh1rocu.goety.api.extension.IMakesPiglinsNeutralTrinkets;
import com.Polarice3.Goety.compat.iron.IronAttributes;
import com.Polarice3.Goety.compat.iron.IronLoaded;
import com.Polarice3.Goety.config.MainConfig;
import com.Polarice3.Goety.utils.CuriosFinder;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import dev.emi.trinkets.api.SlotReference;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.UUID;

public class NetherRobeItem extends SingleStackItem implements IMakesPiglinsNeutralTrinkets {

    public NetherRobeItem() {
        super(new Properties().fireResistant().stacksTo(1));
    }

    @Override
    public void inventoryTick(ItemStack stack, Level worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        if (entityIn instanceof LivingEntity livingEntity) {
            if (CuriosFinder.hasCurio(livingEntity, this)) {
                if (livingEntity.isOnFire()) {
                    livingEntity.setRemainingFireTicks(livingEntity.getRemainingFireTicks() - 2);
                }
            }
        }

        super.inventoryTick(stack, worldIn, entityIn, itemSlot, isSelected);
    }

    @Override
    public boolean makesPiglinsNeutral(SlotReference slotContext, ItemStack stack, LivingEntity entity) {
        return true;
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getModifiers(ItemStack stack, SlotReference slotContext, LivingEntity livingEntity,
                                                               UUID uuid) {
        Multimap<Attribute, AttributeModifier> map = HashMultimap.create();
        if (IronLoaded.IRON_SPELLBOOKS.isLoaded()) {
            if (MainConfig.RobesIronResist.get()) {
                map.put(IronAttributes.FIRE_MAGIC_RESIST, new AttributeModifier(UUID.fromString("c4bc990e-a8b4-4e6e-8055-e8ba88d90e55"), "Robes Iron Spell Resist", 0.5F, AttributeModifier.Operation.ADDITION));
            }
        }
        return map;
    }
}
