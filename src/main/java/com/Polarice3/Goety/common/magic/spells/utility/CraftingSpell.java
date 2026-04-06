package com.Polarice3.Goety.common.magic.spells.utility;

import com.Polarice3.Goety.client.inventory.container.CraftingFocusMenu;
import com.Polarice3.Goety.common.magic.Spell;
import com.Polarice3.Goety.common.magic.SpellStat;
import com.Polarice3.Goety.config.SpellConfig;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class CraftingSpell extends Spell {
    private static final Component CONTAINER_TITLE = Component.translatable("container.crafting");

    @Override
    public int defaultSoulCost() {
        return SpellConfig.CraftingCost.get();
    }

    @Override
    public int defaultCastDuration() {
        return SpellConfig.CraftingDuration.get();
    }

    @Override
    public SoundEvent CastingSound() {
        return null;
    }

    @Override
    public int defaultSpellCooldown() {
        return SpellConfig.CraftingCoolDown.get();
    }

    @Override
    public void SpellResult(ServerLevel worldIn, LivingEntity caster, ItemStack staff, SpellStat spellStat) {
        if (caster instanceof Player player) {
            player.openMenu(new ExtendedScreenHandlerFactory() {
                @Override
                public void writeScreenOpeningData(ServerPlayer serverPlayer, FriendlyByteBuf friendlyByteBuf) {

                }

                @Override
                public Component getDisplayName() {
                    return CONTAINER_TITLE;
                }

                @Override
                public @Nullable AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
                    return new CraftingFocusMenu(i, inventory, ContainerLevelAccess.create(worldIn, caster.blockPosition()));
                }
            });
        }
    }
}
