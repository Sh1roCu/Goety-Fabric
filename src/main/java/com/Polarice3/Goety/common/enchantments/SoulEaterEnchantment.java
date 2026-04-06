package com.Polarice3.Goety.common.enchantments;

import com.Polarice3.Goety.config.SpellConfig;
import net.fabricmc.fabric.api.tag.convention.v1.ConventionalItemTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class SoulEaterEnchantment extends Enchantment {
    public SoulEaterEnchantment(Rarity rarityIn, EnchantmentCategory enchantmentType, EquipmentSlot... slots) {
        super(rarityIn, enchantmentType, slots);
    }

    @Override
    public int getMinCost(int enchantmentLevel) {
        return 5 + (enchantmentLevel - 1) * 9;
    }

    @Override
    public int getMaxCost(int enchantmentLevel) {
        return this.getMinCost(enchantmentLevel) + 15;
    }

    @Override
    public int getMaxLevel() {
        return SpellConfig.MaxSoulEaterLevel.get();
    }

    @Override
    public boolean canEnchant(ItemStack stack) {
        return super.canEnchant(stack)
                || stack.getItem() instanceof AxeItem
                || (stack.getItem() instanceof TieredItem
                && !(stack.getItem() instanceof DiggerItem))
                || stack.getItem() instanceof TridentItem
                || stack.getItem() instanceof ProjectileWeaponItem
                || stack.is(ItemTags.SWORDS)
                || stack.is(ItemTags.AXES)
                || stack.is(holder -> holder.value() instanceof TridentItem) // ConventionalItemTags.TRIDENTS
                || stack.is(ConventionalItemTags.BOWS)
                || stack.is(Items.CROSSBOW) || stack.is(holder -> holder.value() instanceof CrossbowItem); // ConventionalItemTags.CROSS_BOWS
    }
}
