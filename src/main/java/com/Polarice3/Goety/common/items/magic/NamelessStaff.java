package com.Polarice3.Goety.common.items.magic;

import cn.sh1rocu.goety.api.extension.client.ICustomRenderer;
import com.Polarice3.Goety.api.magic.SpellType;
import com.Polarice3.Goety.client.render.item.CustomItemsRenderer;
import com.Polarice3.Goety.config.ItemConfig;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;

public class NamelessStaff extends DarkStaff implements ICustomRenderer {
    public NamelessStaff() {
        super(ItemConfig.NamelessStaffDamage.get(), SpellType.NECROMANCY);
    }

    @Override
    public BlockEntityWithoutLevelRenderer getCustomRenderer() {
        return CustomItemsRenderer.INSTANCE.get();
    }
}
