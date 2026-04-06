package cn.sh1rocu.goety.asm.enchantment;

import cn.sh1rocu.goety.mixin.asm.EnchantmentCategoryASM;
import com.Polarice3.Goety.api.items.magic.IFocus;
import net.minecraft.world.item.Item;

public class FocusCategory extends EnchantmentCategoryASM {

    @Override
    public boolean canEnchant(Item item) {
        return item instanceof IFocus;
    }
}
