package com.Polarice3.Goety.common.items.block;

import cn.sh1rocu.goety.api.extension.ICustomMaxStackSize;
import cn.sh1rocu.goety.api.extension.IEnchantment;
import cn.sh1rocu.goety.api.extension.client.ICustomRenderer;
import com.Polarice3.Goety.client.render.block.ModISTER;
import com.Polarice3.Goety.common.blocks.ModBlocks;
import com.Polarice3.Goety.common.enchantments.ModEnchantments;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;

public class BlackCrystalItem extends BlockItemBase implements IEnchantment, ICustomRenderer, ICustomMaxStackSize {

    public BlackCrystalItem() {
        super(ModBlocks.BLACK_CRYSTAL);
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        return stack.getCount() == 1
                && (enchantment == ModEnchantments.SOUL_EATER
                || enchantment == ModEnchantments.RADIUS);
    }

    @Override
    public int getMaxStackSize(ItemStack itemStack) {
        return itemStack.isEnchanted() ? 1 : super.getMaxStackSize();
    }

    @Override
    public boolean isEnchantable(ItemStack stack) {
        return stack.getCount() == 1;
    }

    @Override
    public int getEnchantmentValue() {
        return 25;
    }

    @Environment(EnvType.CLIENT)
    @Override
    public BlockEntityWithoutLevelRenderer getCustomRenderer() {
        return ModISTER.INSTANCE.get();
    }
}
