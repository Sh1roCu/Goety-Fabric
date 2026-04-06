package com.Polarice3.Goety.common.items.curios;

import com.Polarice3.Goety.compat.iron.IronAttributes;
import com.Polarice3.Goety.compat.iron.IronLoaded;
import com.Polarice3.Goety.config.MainConfig;
import com.Polarice3.Goety.init.ModAttributes;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import dev.emi.trinkets.api.SlotReference;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;

import java.util.UUID;

public class AbyssRobeItem extends SingleStackItem {

    @Override
    public Multimap<Attribute, AttributeModifier> getModifiers(ItemStack stack,
                                                               SlotReference slot, LivingEntity entity, UUID uuid) {
        Multimap<Attribute, AttributeModifier> map = HashMultimap.create();
        map.put(ModAttributes.SWIM_SPEED, new AttributeModifier(UUID.fromString("7f56e408-afa5-4ea3-a0f4-6f27513f207c"), "Abyss Robe Buff", 0.5F, AttributeModifier.Operation.ADDITION));
        if (IronLoaded.IRON_SPELLBOOKS.isLoaded()) {
            if (MainConfig.RobesIronResist.get()) {
                map.put(IronAttributes.LIGHTNING_MAGIC_RESIST, new AttributeModifier(UUID.fromString("284fb930-08a1-483e-94d9-2258f7be1e2f"), "Robes Iron Spell Resist", -0.25F, AttributeModifier.Operation.ADDITION));
            }
        }
        return map;
    }
}
