package cn.sh1rocu.goety.asm.enchantment;

import cn.sh1rocu.goety.mixin.asm.EnchantmentCategoryASM;
import com.Polarice3.Goety.common.items.curios.RingItem;
import net.minecraft.world.item.Item;

@SuppressWarnings("unused")
public class RingsCategory extends EnchantmentCategoryASM {

    @Override
    public boolean canEnchant(Item item) {
        return item instanceof RingItem;
    }
}
