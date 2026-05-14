package com.Polarice3.Goety.utils;

import com.Polarice3.Goety.common.effects.GoetyEffects;
import com.Polarice3.Goety.common.items.ModItems;
import com.Polarice3.Goety.common.items.ModTiers;
import com.Polarice3.Goety.common.items.armor.MaleficHelm;
import com.Polarice3.Goety.common.items.equipment.PhilosophersMaceItem;
import com.Polarice3.Goety.config.ItemConfig;
import com.jamieswhiteshirt.reachentityattributes.ReachEntityAttributes;
import net.fabricmc.fabric.api.transfer.v1.context.ContainerItemContext;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorage;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.StorageUtil;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BannerPattern;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

public class ItemHelper {

    public static <T extends LivingEntity> void hurtAndRemove(ItemStack stack, int pAmount, T pEntity) {
        if (!pEntity.level.isClientSide && (!(pEntity instanceof Player) || !((Player) pEntity).getAbilities().instabuild)) {
            if (stack.isDamageableItem()) {
                if (stack.hurt(pAmount, pEntity.getRandom(), pEntity instanceof ServerPlayer ? (ServerPlayer) pEntity : null)) {
                    stack.shrink(1);
                    stack.setDamageValue(0);
                }
            }
        }
    }

    public static <T extends LivingEntity> void hurtAndBreak(ItemStack itemStack, int pAmount, T pEntity) {
        itemStack.hurtAndBreak(pAmount, pEntity, (p_220045_0_) -> p_220045_0_.broadcastBreakEvent(EquipmentSlot.MAINHAND));
    }

    public static void hurtNoEntity(ItemStack itemStack, int pAmount, Level level) {
        if (!level.isClientSide) {
            if (itemStack.isDamageableItem()) {
                if (itemStack.hurt(pAmount, level.getRandom(), null)) {
                    itemStack.shrink(1);
                    itemStack.setDamageValue(0);
                }
            }
        }
    }

    public static ItemEntity itemEntityDrop(LivingEntity livingEntity, ItemStack itemStack) {
        return new ItemEntity(livingEntity.level, livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(), itemStack);
    }

    public static void addItemEntity(Level level, BlockPos blockPos, ItemStack itemStack) {
        double d0 = (double) (level.random.nextFloat() * 0.5F) + 0.25D;
        double d1 = (double) (level.random.nextFloat() * 0.5F) + 0.25D;
        double d2 = (double) (level.random.nextFloat() * 0.5F) + 0.25D;
        ItemEntity itementity = new ItemEntity(level, (double) blockPos.getX() + d0, (double) blockPos.getY() + d1, (double) blockPos.getZ() + d2, itemStack);
        itementity.setDefaultPickUpDelay();
        level.addFreshEntity(itementity);
    }

    public static void addAndConsumeItem(Player player, InteractionHand hand, ItemStack toAdd) {
        addAndConsumeItem(player, hand, toAdd, true);
    }

    public static void addAndConsumeItem(Player player, InteractionHand hand, ItemStack toAdd, boolean addToInventory) {
        ItemStack stack = player.getItemInHand(hand);
        if (stack.getCount() == 1) {
            if (!player.isCreative()) {
                player.setItemInHand(hand, toAdd);
            } else if (addToInventory) {
                if (!player.getInventory().add(toAdd)) {
                    player.drop(toAdd, false, true);
                }
            }
        } else {
            if (!player.isCreative()) {
                stack.shrink(1);
            }
            if (!player.getInventory().add(toAdd)) {
                player.drop(toAdd, false, true);
            }
        }
    }

    public static boolean hasItem(Player player, Item item) {
        return !findItem(player, item).isEmpty();
    }

    public static ItemStack findItem(Player playerEntity, Item item) {
        ItemStack foundStack = ItemStack.EMPTY;
        for (int i = 0; i < playerEntity.getInventory().getContainerSize(); i++) {
            ItemStack itemStack = playerEntity.getInventory().getItem(i);
            if (!itemStack.isEmpty() && itemStack.getItem() == item) {
                foundStack = itemStack;
                break;
            }
        }
        return foundStack;
    }

    public static ItemStack findItem(Player playerEntity, Predicate<ItemStack> item) {
        ItemStack foundStack = ItemStack.EMPTY;
        for (int i = 0; i < playerEntity.getInventory().getContainerSize(); i++) {
            ItemStack itemStack = playerEntity.getInventory().getItem(i);
            if (!itemStack.isEmpty() && item.test(itemStack)) {
                foundStack = itemStack;
                break;
            }
        }
        return foundStack;
    }

    public static boolean findHelmet(Player player, Item item) {
        return player.getItemBySlot(EquipmentSlot.HEAD).getItem() == item;
    }

    public static boolean armorSet(LivingEntity living, ArmorMaterial material) {
        int i = 0;
        if (living.getItemBySlot(EquipmentSlot.HEAD).getItem() instanceof ArmorItem helmet && helmet.getMaterial() == material) {
            for (EquipmentSlot equipmentSlot : EquipmentSlot.values()) {
                if (equipmentSlot.getType() == EquipmentSlot.Type.ARMOR) {
                    if (living.getItemBySlot(equipmentSlot).getItem() instanceof ArmorItem armorItem) {
                        if (armorItem.getMaterial() == material) {
                            ++i;
                        }
                    }
                }
            }
        }
        return i >= 4;
    }

    public static boolean isFullEquipped(LivingEntity living) {
        int i = 0;
        for (EquipmentSlot equipmentSlot : EquipmentSlot.values()) {
            if (equipmentSlot.getType() == EquipmentSlot.Type.ARMOR) {
                if (!living.getItemBySlot(equipmentSlot).isEmpty()) {
                    ++i;
                }
            }
        }
        return i >= 4;
    }

    public static boolean isFullArmored(LivingEntity living) {
        int i = 0;
        for (EquipmentSlot equipmentSlot : EquipmentSlot.values()) {
            if (equipmentSlot.getType() == EquipmentSlot.Type.ARMOR) {
                if (living.getItemBySlot(equipmentSlot).getItem() instanceof ArmorItem) {
                    ++i;
                }
            }
        }
        return i >= 4;
    }

    public static boolean noArmor(LivingEntity living) {
        int i = 0;
        for (EquipmentSlot equipmentSlot : EquipmentSlot.values()) {
            if (equipmentSlot.getType() == EquipmentSlot.Type.ARMOR) {
                if (living.getItemBySlot(equipmentSlot).isEmpty()) {
                    ++i;
                }
            }
        }
        return i >= 4;
    }

    public static void repairTick(ItemStack stack, Entity entityIn, boolean isSelected) {
        if (ItemConfig.SoulRepair.get()) {
            if (entityIn instanceof Player player) {
                if (!(player.swinging && isSelected)) {
                    if (stack.isDamaged()) {
                        if (SEHelper.getSoulsContainer(player)) {
                            int i = 1;
                            var enchants = EnchantmentHelper.getEnchantments(stack);
                            if (!enchants.isEmpty() && ItemConfig.SoulRepairEnchant.get()) {
                                i += enchants.size();
                            }
                            if (SEHelper.getSoulsAmount(player, ItemConfig.ItemsRepairAmount.get() * i)) {
                                if (player.tickCount % 20 == 0) {
                                    stack.setDamageValue(stack.getDamageValue() - 1);
                                    SEHelper.decreaseSouls(player, ItemConfig.ItemsRepairAmount.get() * i);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public static boolean isValidFluidContainerToDrain(ItemStack stack, Fluid fluid) {
        if (stack.isEmpty() || stack.getCount() != 1) {
            return false;
        }

        var storage = FluidStorage.ITEM.find(stack, ContainerItemContext.withConstant(stack));
        if (storage == null) {
            return false;
        } else {
            long drained = StorageUtil.simulateExtract(storage, FluidVariant.of(fluid), FluidConstants.BUCKET, null);
            return drained == FluidConstants.BUCKET;
        }
    }

    public static ItemStack drain(Fluid fluid, ItemStack stack, ContainerItemContext context) {
        var storage = FluidStorage.ITEM.find(stack, context);

        if (storage != null) {
            try (Transaction tx = Transaction.openOuter()) {
                storage.extract(FluidVariant.of(fluid), FluidConstants.BUCKET, tx);
                tx.commit();
            }
        }

        return context.getItemVariant().toStack(stack.getCount());
    }

    public static boolean isValidFluidContainerToFill(ItemStack stack, Fluid fluid) {
        if (stack.isEmpty()) {
            return false;
        }

        ItemStack container = stack;
        if (stack.getCount() > 1) {
            container = new ItemStack(stack.getItem());
        }

        var storage = FluidStorage.ITEM.find(container, ContainerItemContext.withConstant(container));
        if (storage == null) {
            return false;
        } else {
            long filled = StorageUtil.simulateInsert(storage, FluidVariant.of(fluid), FluidConstants.BUCKET, null);
            return filled == FluidConstants.BUCKET;
        }
    }

    public static ItemStack fill(Fluid fluid, ItemStack stack, ContainerItemContext context) {
        var storage = FluidStorage.ITEM.find(stack, context);
        if (storage != null) {
            try (Transaction tx = Transaction.openOuter()) {
                storage.insert(FluidVariant.of(fluid), FluidConstants.BUCKET, tx);
                tx.commit();
            }
        }

        return context.getItemVariant().toStack(stack.getCount());
    }

    public static void setItemEffect(ItemStack stack, LivingEntity victim) {
        if (stack.getItem() instanceof TieredItem weapon) {
            if (weapon.getTier() == ModTiers.DARK) {
                victim.addEffect(new MobEffectInstance(GoetyEffects.WANE, 60));
            }
            if (weapon == ModItems.FELL_BLADE && victim.getRandom().nextBoolean()) {
                victim.addEffect(new MobEffectInstance(GoetyEffects.BUSTED, MathHelper.secondsToTicks(5)));
            }
            if (weapon == ModItems.FROZEN_BLADE) {
                victim.addEffect(new MobEffectInstance(GoetyEffects.FREEZING, MathHelper.secondsToTicks(2)));
            }
        } else if (stack.getItem() instanceof PhilosophersMaceItem) {
            int i2 = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.MOB_LOOTING, stack);
            victim.addEffect(new MobEffectInstance(GoetyEffects.GOLD_TOUCHED, 300, i2));
        }
    }

    //Stolen from @Vazkii: https://github.com/VazkiiMods/Botania/blob/1.20.x/Xplat/src/main/java/vazkii/botania/client/gui/TooltipHandler.java
    public static Component getShiftInfoTooltip() {
        Component shift = Component.literal("SHIFT").withStyle(ChatFormatting.AQUA);
        return Component.translatable("info.goety.item_info", shift).withStyle(ChatFormatting.GRAY);
    }

    public static void addOnShift(List<Component> tooltip, Runnable lambda) {
        if (Screen.hasShiftDown()) {
            lambda.run();
        } else {
            tooltip.add(getShiftInfoTooltip());
        }
    }

    public static boolean sameBanner(ItemStack banner1, ItemStack banner2) {
        if (banner1.getItem() instanceof BannerItem && banner2.getItem() instanceof BannerItem) {
            CompoundTag compoundtag1 = BlockItem.getBlockEntityData(banner1);
            CompoundTag compoundtag2 = BlockItem.getBlockEntityData(banner2);
            if (compoundtag1 != null && compoundtag2 != null) {
                if (compoundtag1.contains("Patterns") && compoundtag2.contains("Patterns")) {
                    ListTag listtag1 = compoundtag1.getList("Patterns", 10);
                    ListTag listtag2 = compoundtag2.getList("Patterns", 10);
                    if (listtag1.size() == listtag2.size()) {
                        int i = 0;
                        for (int j = 0; j < listtag1.size(); ++j) {
                            CompoundTag compoundtag3 = listtag1.getCompound(i);
                            CompoundTag compoundtag4 = listtag2.getCompound(i);
                            Holder<BannerPattern> holder1 = BannerPattern.byHash(compoundtag3.getString("Pattern"));
                            Holder<BannerPattern> holder2 = BannerPattern.byHash(compoundtag4.getString("Pattern"));
                            if (holder1 != null && holder2 != null) {
                                if (holder1.value().getHashname().equals(holder2.value().getHashname())) {
                                    ++i;
                                }
                            }
                        }
                        return i == listtag1.size();
                    }
                }
            }
        }
        return false;
    }

    public static int repairPlayerItems(Player p_147093_, int experience) {
        Map.Entry<EquipmentSlot, ItemStack> entry = EnchantmentHelper.getRandomItemWith(Enchantments.MENDING, p_147093_, ItemStack::isDamaged);
        if (entry != null) {
            ItemStack itemstack = entry.getValue();
            // Fabric: use vanilla's repair ratio
            /*itemstack.getXpRepairRatio()*/
            int i = Math.min(experience * 2, itemstack.getDamageValue());
            itemstack.setDamageValue(itemstack.getDamageValue() - i);
            int j = experience - durabilityToXp(i);
            return j > 0 ? repairPlayerItems(p_147093_, j) : 0;
        } else {
            return experience;
        }
    }

    private static int durabilityToXp(int p_20794_) {
        return p_20794_ / 2;
    }

    public static InteractionResultHolder<ItemStack> getVoidBottle(Player player, Level world, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        if (stack.isEmpty() || !stack.is(Items.GLASS_BOTTLE) || world.dimension().location().toString().contains("aether")) {
            return InteractionResultHolder.pass(stack);
        }

        BlockHitResult result = getPlayerPOVHitResult(world, player, ClipContext.Fluid.ANY);

        if (result.getLocation().y <= world.getMinBuildHeight()) {
            if (!world.isClientSide) {
                ItemStack enderAir = new ItemStack(ModItems.VOID_BOTTLE);
                player.getInventory().placeItemBackInInventory(enderAir);
                stack.shrink(1);
                world.playSound(null, player.blockPosition(), SoundEvents.ITEM_PICKUP, SoundSource.NEUTRAL, 0.5F, 1.0F);
                world.gameEvent(player, GameEvent.FLUID_PICKUP, player.position());
            }

            return InteractionResultHolder.sidedSuccess(stack, world.isClientSide());
        }

        return InteractionResultHolder.pass(stack);
    }

    protected static BlockHitResult getPlayerPOVHitResult(Level p_41436_, Player player, ClipContext.Fluid p_41438_) {
        float f = player.getXRot();
        float f1 = player.getYRot();
        Vec3 vec3 = player.getEyePosition();
        float f2 = Mth.cos(-f1 * ((float) Math.PI / 180F) - (float) Math.PI);
        float f3 = Mth.sin(-f1 * ((float) Math.PI / 180F) - (float) Math.PI);
        float f4 = -Mth.cos(-f * ((float) Math.PI / 180F));
        float f5 = Mth.sin(-f * ((float) Math.PI / 180F));
        float f6 = f3 * f4;
        float f7 = f2 * f4;
        double d0 = ReachEntityAttributes.getReachDistance(player, player.isCreative() ? 5 : 4.5);
        Vec3 vec31 = vec3.add((double) f6 * d0, (double) f5 * d0, (double) f7 * d0);
        return p_41436_.clip(new ClipContext(vec3, vec31, ClipContext.Block.OUTLINE, p_41438_, player));
    }

    public static boolean hasMaleficHelm(LivingEntity wearer) {
        ItemStack itemStack = wearer.getItemBySlot(EquipmentSlot.HEAD);
        if (itemStack.getItem() instanceof MaleficHelm helm) {
            return !helm.isBroken(itemStack);
        }
        return false;
    }
}
