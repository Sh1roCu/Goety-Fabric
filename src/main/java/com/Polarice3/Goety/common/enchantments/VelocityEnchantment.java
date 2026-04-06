package com.Polarice3.Goety.common.enchantments;

import com.Polarice3.Goety.config.SpellConfig;
import net.minecraft.world.entity.EquipmentSlot;

public class VelocityEnchantment extends FocusEnchantments {
    public VelocityEnchantment(Rarity pRarity, EquipmentSlot... pApplicableSlots) {
        super(pRarity, pApplicableSlots);
    }

    @Override
    public int getMinCost(int pEnchantmentLevel) {
        return 15 + (pEnchantmentLevel - 1) * 9;
    }

    @Override
    public int getMaxCost(int pEnchantmentLevel) {
        return super.getMinCost(pEnchantmentLevel) + 50;
    }

    @Override
    public int getMaxLevel() {
        return SpellConfig.MaxVelocityLevel.get();
    }
}
