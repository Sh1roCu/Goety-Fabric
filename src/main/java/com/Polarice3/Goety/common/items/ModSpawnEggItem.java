package com.Polarice3.Goety.common.items;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.SpawnEggItem;

public class ModSpawnEggItem extends SpawnEggItem {

    public ModSpawnEggItem(final EntityType<? extends Mob> entityTypeSupplier, int primaryColorIn, int secondaryColorIn, Properties builder) {
        super(entityTypeSupplier, primaryColorIn, secondaryColorIn, builder);
    }
}
