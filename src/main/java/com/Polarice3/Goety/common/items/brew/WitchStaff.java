package com.Polarice3.Goety.common.items.brew;

import com.Polarice3.Goety.common.entities.projectiles.ThrownBrew;
import com.Polarice3.Goety.common.items.handler.WitchStaffItemHandler;
import com.Polarice3.Goety.utils.BrewUtils;
import com.Polarice3.Goety.utils.MathHelper;
import com.Polarice3.Goety.utils.SEHelper;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class WitchStaff extends Item {
    public WitchStaff(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
        ItemStack itemstack = playerIn.getItemInHand(handIn);
        if (getThrowBrew(itemstack) != null) {
            if (!worldIn.isClientSide) {
                ThrownBrew thrownBrew = new ThrownBrew(worldIn, playerIn);
                thrownBrew.setItem(itemstack);
                float velocity = 0.5F + BrewUtils.getVelocity(itemstack);
                thrownBrew.shootFromRotation(playerIn, playerIn.getXRot(), playerIn.getYRot(), -20.0F, velocity, 1.0F);
                if (worldIn.addFreshEntity(thrownBrew)) {
                    SEHelper.addCooldown(playerIn, this, MathHelper.secondsToTicks(1));
                }
            }

            playerIn.awardStat(Stats.ITEM_USED.get(this));
            if (!playerIn.getAbilities().instabuild) {
                itemstack.shrink(1);
            }
            return InteractionResultHolder.sidedSuccess(itemstack, worldIn.isClientSide());
        } else if (getDrinkBrew(itemstack) != null) {
            playerIn.startUsingItem(handIn);
        }

        return InteractionResultHolder.consume(itemstack);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level worldIn, LivingEntity entityLiving) {
        super.finishUsingItem(stack, worldIn, entityLiving);
        BrewItem brewItem = getDrinkBrew(stack);
        if (brewItem != null) {
            brewItem.finishUsingItem(getBrew(stack), worldIn, entityLiving);
        }
        return stack;
    }

    @Override
    public int getUseDuration(@NotNull ItemStack stack) {
        BrewItem brewItem = getDrinkBrew(stack);
        if (brewItem != null) {
            return brewItem.getUseDuration(getBrew(stack));
        } else {
            return 0;
        }
    }

    public static ItemStack getBrew(ItemStack itemstack) {
        WitchStaffItemHandler handler = WitchStaffItemHandler.get(itemstack);
        return handler.getSlot();
    }

    public static BrewItem getDrinkBrew(ItemStack itemStack) {
        ItemStack brew = getBrew(itemStack);
        if (brew != null && !brew.isEmpty() && brew.getItem() instanceof BrewItem brewItem) {
            return brewItem;
        } else {
            return null;
        }
    }

    public static ThrowableBrewItem getThrowBrew(ItemStack itemStack) {
        ItemStack brew = getBrew(itemStack);
        if (brew != null && !brew.isEmpty() && brew.getItem() instanceof ThrowableBrewItem brewItem) {
            return brewItem;
        } else {
            return null;
        }
    }

    @Nullable
    public WitchStaffItemHandler initCapabilities(@Nonnull ItemStack stack) {
        return new WitchStaffItemHandler(stack);
    }

    @Override
    public boolean allowNbtUpdateAnimation(Player player, InteractionHand hand, ItemStack oldStack, ItemStack newStack) {
        return !oldStack.equals(newStack);
    }
}
