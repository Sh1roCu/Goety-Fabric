package com.Polarice3.Goety.common.items;

import cn.sh1rocu.goety.api.extension.IEnchantment;
import com.Polarice3.Goety.Goety;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class TramplerArmorItem extends Item implements IEnchantment {
    private final int protection;
    private final ResourceLocation texture;

    public TramplerArmorItem(int protection, String material) {
        this(protection, Goety.location("textures/entity/illagers/trampler/armor/" + material + ".png"), new Properties().stacksTo(1));
    }

    public TramplerArmorItem(int protection, String material, Properties properties) {
        this(protection, Goety.location("textures/entity/illagers/trampler/armor/" + material + ".png"), properties);
    }

    public TramplerArmorItem(int protection, ResourceLocation texture, Properties properties) {
        super(properties);
        this.protection = protection;
        this.texture = texture;
    }

    public ResourceLocation getTexture() {
        return texture;
    }

    public int getProtection() {
        return this.protection;
    }

    @Override
    public int getEnchantmentValue() {
        return 1;
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        return enchantment.category.canEnchant(stack.getItem()) && enchantment.category == EnchantmentCategory.ARMOR;
    }
}
