package com.Polarice3.Goety.common.enchantments;

import com.Polarice3.Goety.config.SpellConfig;
import net.minecraft.world.entity.EquipmentSlot;

public class DurationEnchantment extends FocusEnchantments {
    public DurationEnchantment(Rarity pRarity, EquipmentSlot... pApplicableSlots) {
        super(pRarity, pApplicableSlots);
    }

    @Override
    public int getMinCost(int pEnchantmentLevel) {
        return 20;
    }

    @Override
    public int getMaxCost(int pEnchantmentLevel) {
        return 50;
    }

    @Override
    public int getMaxLevel() {
        return SpellConfig.MaxDurationLevel.get();
    }
}
