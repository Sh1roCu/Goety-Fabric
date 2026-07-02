package com.Polarice3.Goety.common.items.curios;

import com.Polarice3.Goety.api.items.curios.IActivatable;
import com.Polarice3.Goety.client.inventory.container.EternalCauldronContainer;
import com.Polarice3.Goety.common.effects.brew.BrewEffect;
import com.Polarice3.Goety.common.effects.brew.BrewEffectInstance;
import com.Polarice3.Goety.common.effects.brew.BrewEffects;
import com.Polarice3.Goety.common.items.handler.EternalCauldronItemHandler;
import com.Polarice3.Goety.common.network.ModNetwork;
import com.Polarice3.Goety.common.network.server.SPlayPlayerSoundPacket;
import com.Polarice3.Goety.init.ModSounds;
import com.Polarice3.Goety.utils.BrewUtils;
import com.Polarice3.Goety.utils.MathHelper;
import com.Polarice3.Goety.utils.SEHelper;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class EternalCauldronItem extends SingleStackItem implements IActivatable {

    @Override
    public void activate(Level level, Player player, ItemStack itemStack) {
        if (itemStack.is(this)) {
            if (!level.isClientSide) {
                if (player.isShiftKeyDown()) {
                    player.openMenu(getMenuProvider(itemStack));
                } else {
                    if (!getBottle(itemStack).isEmpty() && !SEHelper.isOnCooldown(player, itemStack)) {
                        ItemStack bottle = getBottle(itemStack);
                        int duration = MathHelper.secondsToTicks(45);
                        int add = 1;
                        for (MobEffectInstance mobeffectinstance : PotionUtils.getMobEffects(bottle)) {
                            BrewEffect brewEffect = BrewEffects.INSTANCE.getBrewEffect(mobeffectinstance.getDescriptionId());
                            int amp = 1;
                            amp += mobeffectinstance.getAmplifier();
                            if (brewEffect != null) {
                                add += (brewEffect.getCapacityExtra() * amp);
                            } else if (amp > 1) {
                                add += amp - 1;
                            }
                            if (mobeffectinstance.getEffect().isInstantenous()) {
                                mobeffectinstance.getEffect().applyInstantenousEffect(player, player, player, mobeffectinstance.getAmplifier(), 1.0D);
                            } else {
                                player.addEffect(new MobEffectInstance(mobeffectinstance));
                            }
                        }
                        for (BrewEffectInstance brewEffectInstance : BrewUtils.getBrewEffects(bottle)) {
                            brewEffectInstance.getEffect().drinkBlockEffect(player, player, player, brewEffectInstance.getAmplifier(), BrewUtils.getAreaOfEffect(bottle));
                        }
                        ModNetwork.sendTo(player, SPlayPlayerSoundPacket.ID, SPlayPlayerSoundPacket.encode(ModSounds.POTION_DRINK, 1.0F, 1.0F));
                        SEHelper.addCooldown(player, this, duration * add);
                    }
                }
            }
        }
    }

    @Nonnull
    @Override
    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
        ItemStack itemstack = playerIn.getItemInHand(handIn);
        if (playerIn.isCrouching() && equipItem(playerIn, itemstack)) {
            return InteractionResultHolder.success(itemstack);
        } else {
            if (!worldIn.isClientSide) {
                playerIn.openMenu(getMenuProvider(itemstack));
            }
            return InteractionResultHolder.success(itemstack);
        }
    }

    public static ItemStack getBottle(ItemStack itemstack) {
        EternalCauldronItemHandler handler = EternalCauldronItemHandler.get(itemstack);
        return handler.getSlot();
    }

    @Nullable
    public EternalCauldronItemHandler initCapabilities(@Nonnull ItemStack stack) {
        return new EternalCauldronItemHandler(stack);
    }

    @Override
    public boolean allowNbtUpdateAnimation(Player player, InteractionHand hand, ItemStack oldStack, ItemStack newStack) {
        return !oldStack.equals(newStack);
    }

    private MenuProvider getMenuProvider(ItemStack itemStack) {
        return new ExtendedScreenHandlerFactory() {
            @Override
            public void writeScreenOpeningData(ServerPlayer player, FriendlyByteBuf buf) {
                buf.writeItem(itemStack);
            }

            @Override
            public @NotNull Component getDisplayName() {
                return getName(itemStack);
            }

            @Override
            public @NotNull AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
                return new EternalCauldronContainer(i, inventory, EternalCauldronItemHandler.get(itemStack), itemStack);
            }
        };
    }
}
