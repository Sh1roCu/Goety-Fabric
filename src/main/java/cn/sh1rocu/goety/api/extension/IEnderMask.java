package cn.sh1rocu.goety.api.extension;

import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public interface IEnderMask {

    boolean isEnderMask(ItemStack stack, Player player, EnderMan enderMan);
}