package cn.sh1rocu.goety.api.extension;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public interface ILeftClickEntity {

    boolean onLeftClickEntity(ItemStack stack, Player player, Entity entity);
}
