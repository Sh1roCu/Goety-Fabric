package com.Polarice3.Goety.common.enchantments;

import com.Polarice3.Goety.Goety;
import com.chocohead.mm.api.ClassTinkerers;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

import java.util.function.Supplier;

public class ModEnchantments {

    public static final EnchantmentCategory RINGS = ClassTinkerers.getEnum(EnchantmentCategory.class, "RINGS");
    public static final EnchantmentCategory FOCUS = ClassTinkerers.getEnum(EnchantmentCategory.class, "FOCUS");

    public static final Enchantment SOUL_EATER = register("soul_eater",
            () -> new SoulEaterEnchantment(Enchantment.Rarity.UNCOMMON, EnchantmentCategory.WEAPON, EquipmentSlot.MAINHAND));

    public static final Enchantment WANTING = register("wanting",
            () -> new LootingEnchantment(Enchantment.Rarity.RARE, RINGS, EquipmentSlot.MAINHAND));

    public static final Enchantment POTENCY = register("potency",
            () -> new PotencyEnchantment(Enchantment.Rarity.RARE, EquipmentSlot.MAINHAND));

    public static final Enchantment RADIUS = register("radius",
            () -> new RadiusEnchantment(Enchantment.Rarity.RARE, EquipmentSlot.MAINHAND));

    public static final Enchantment BURNING = register("burning",
            () -> new BurningEnchantment(Enchantment.Rarity.VERY_RARE, EquipmentSlot.MAINHAND));

    public static final Enchantment RANGE = register("range",
            () -> new RangeEnchantment(Enchantment.Rarity.COMMON, EquipmentSlot.MAINHAND));

    public static final Enchantment ABSORB = register("absorb",
            () -> new AbsorbEnchantment(Enchantment.Rarity.VERY_RARE, EquipmentSlot.MAINHAND));

    public static final Enchantment MAGNET = register("magnet",
            () -> new MagnetEnchantment(Enchantment.Rarity.VERY_RARE, EquipmentSlot.MAINHAND));

    public static final Enchantment DURATION = register("duration",
            () -> new DurationEnchantment(Enchantment.Rarity.UNCOMMON, EquipmentSlot.MAINHAND));

    public static final Enchantment VELOCITY = register("velocity",
            () -> new VelocityEnchantment(Enchantment.Rarity.UNCOMMON, EquipmentSlot.MAINHAND));

    public static final Enchantment ROYALTY = register("royalty",
            () -> new RoyaltyEnchantment(Enchantment.Rarity.VERY_RARE, EquipmentSlot.MAINHAND));
    public static void init() {

    }

    private static Enchantment register(String name, Supplier<Enchantment> supplier) {
        return Registry.register(BuiltInRegistries.ENCHANTMENT, Goety.location(name), supplier.get());
    }
}
