package com.Polarice3.Goety.init;

import com.Polarice3.Goety.Goety;
import com.Polarice3.Goety.api.magic.ISpell;
import com.Polarice3.Goety.api.magic.SpellType;
import com.Polarice3.Goety.common.entities.ai.attributes.SpellAttribute;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;

import java.util.HashSet;
import java.util.Optional;
import java.util.function.Supplier;

public class ModAttributes {

    public static final HashSet<Attribute> ATTRIBUTES = new HashSet<>();

    public static final Attribute SPELL_POTENCY = register("spell_potency", () -> (new RangedAttribute("attribute.name.goety.spell_potency", 0.0D, 0.0D, 2048.0D).setSyncable(true)));
    public static final Attribute SPELL_DURATION = register("spell_duration", () -> (new RangedAttribute("attribute.name.goety.spell_duration", 0.0D, 0.0D, 2048.0D).setSyncable(true)));
    public static final Attribute SPELL_RANGE = register("spell_range", () -> (new RangedAttribute("attribute.name.goety.spell_range", 0.0D, 0.0D, 2048.0D).setSyncable(true)));
    public static final Attribute SPELL_RADIUS = register("spell_radius", () -> (new RangedAttribute("attribute.name.goety.spell_radius", 0.0D, 0.0D, 2048.0D).setSyncable(true)));
    public static final Attribute SPELL_BURNING = register("spell_burning", () -> (new RangedAttribute("attribute.name.goety.spell_burning", 0.0D, 0.0D, 2048.0D).setSyncable(true)));
    public static final Attribute SPELL_VELOCITY = register("spell_velocity", () -> (new RangedAttribute("attribute.name.goety.spell_velocity", 0.0D, 0.0D, 2048.0D).setSyncable(true)));
    public static final Attribute CASTING_SPEED = register("casting_speed", () -> (new RangedAttribute("attribute.name.goety.casting_speed", 0.0D, -1.0D, 1.0D).setSyncable(true)));
    public static final Attribute COOLDOWN_DISCOUNT = register("cooldown_discount", () -> (new RangedAttribute("attribute.name.goety.cooldown_discount", 0.0D, -1.0D, 1.0D).setSyncable(true)));
    public static final Attribute SOUL_DISCOUNT = register("soul_discount", () -> (new RangedAttribute("attribute.name.goety.soul_discount", 0.0D, -1.0D, 1.0D).setSyncable(true)));

    public static final Attribute ABYSS_POTENCY = register("abyss_potency", () -> (SpellAttribute.potency(SpellType.ABYSS, 0.0D, 0.0D, 2048.0D).setSyncable(true)));
    public static final Attribute FROST_POTENCY = register("frost_potency", () -> (SpellAttribute.potency(SpellType.FROST, 0.0D, 0.0D, 2048.0D).setSyncable(true)));
    public static final Attribute GEOMANCY_POTENCY = register("geomancy_potency", () -> (SpellAttribute.potency(SpellType.GEOMANCY, 0.0D, 0.0D, 2048.0D).setSyncable(true)));
    public static final Attribute NECROMANCY_POTENCY = register("necromancy_potency", () -> (SpellAttribute.potency(SpellType.NECROMANCY, 0.0D, 0.0D, 2048.0D).setSyncable(true)));
    public static final Attribute NETHER_POTENCY = register("nether_potency", () -> (SpellAttribute.potency(SpellType.NETHER, 0.0D, 0.0D, 2048.0D).setSyncable(true)));
    public static final Attribute STORM_POTENCY = register("storm_potency", () -> (SpellAttribute.potency(SpellType.STORM, 0.0D, 0.0D, 2048.0D).setSyncable(true)));
    public static final Attribute VOID_POTENCY = register("void_potency", () -> (SpellAttribute.potency(SpellType.VOID, 0.0D, 0.0D, 2048.0D).setSyncable(true)));
    public static final Attribute WILD_POTENCY = register("wild_potency", () -> (SpellAttribute.potency(SpellType.WILD, 0.0D, 0.0D, 2048.0D).setSyncable(true)));
    public static final Attribute WIND_POTENCY = register("wind_potency", () -> (SpellAttribute.potency(SpellType.WIND, 0.0D, 0.0D, 2048.0D).setSyncable(true)));

    public static final Attribute ABYSS_DISCOUNT = register("abyss_discount", () -> (SpellAttribute.discount(SpellType.ABYSS, 0.0D, -1.0D, 1.0D).setSyncable(true)));
    public static final Attribute FROST_DISCOUNT = register("frost_discount", () -> (SpellAttribute.discount(SpellType.FROST, 0.0D, -1.0D, 1.0D).setSyncable(true)));
    public static final Attribute GEOMANCY_DISCOUNT = register("geomancy_discount", () -> (SpellAttribute.discount(SpellType.GEOMANCY, 0.0D, -1.0D, 1.0D).setSyncable(true)));
    public static final Attribute NECROMANCY_DISCOUNT = register("necromancy_discount", () -> (SpellAttribute.discount(SpellType.NECROMANCY, 0.0D, -1.0D, 1.0D).setSyncable(true)));
    public static final Attribute NETHER_DISCOUNT = register("nether_discount", () -> (SpellAttribute.discount(SpellType.NETHER, 0.0D, -1.0D, 1.0D).setSyncable(true)));
    public static final Attribute STORM_DISCOUNT = register("storm_discount", () -> (SpellAttribute.discount(SpellType.STORM, 0.0D, -1.0D, 1.0D).setSyncable(true)));
    public static final Attribute VOID_DISCOUNT = register("void_discount", () -> (SpellAttribute.discount(SpellType.VOID, 0.0D, -1.0D, 1.0D).setSyncable(true)));
    public static final Attribute WILD_DISCOUNT = register("wild_discount", () -> (SpellAttribute.discount(SpellType.WILD, 0.0D, -1.0D, 1.0D).setSyncable(true)));
    public static final Attribute WIND_DISCOUNT = register("wind_discount", () -> (SpellAttribute.discount(SpellType.WIND, 0.0D, -1.0D, 1.0D).setSyncable(true)));

    // Forge Attribute
    public static final Attribute SWIM_SPEED = register("swim_speed", () -> new RangedAttribute("attribute.name.goety.swim_speed", 1.0D, 0.0D, 1024.0D).setSyncable(true));

    public static int getPotency(LivingEntity livingEntity) {
        return (int) livingEntity.getAttributeValue(ModAttributes.SPELL_POTENCY);
    }

    public static int getDuration(LivingEntity livingEntity) {
        return (int) livingEntity.getAttributeValue(ModAttributes.SPELL_DURATION);
    }

    public static int getRange(LivingEntity livingEntity) {
        return (int) livingEntity.getAttributeValue(ModAttributes.SPELL_RANGE);
    }

    public static double getRadius(LivingEntity livingEntity) {
        return livingEntity.getAttributeValue(ModAttributes.SPELL_RADIUS);
    }

    public static int getBurning(LivingEntity livingEntity) {
        return (int) livingEntity.getAttributeValue(ModAttributes.SPELL_BURNING);
    }

    public static float getVelocity(LivingEntity livingEntity) {
        return (float) livingEntity.getAttributeValue(ModAttributes.SPELL_VELOCITY);
    }

    public static int getPotency(LivingEntity livingEntity, ISpell spell) {
        Attribute attribute = ModAttributes.SPELL_POTENCY;
        Optional<Attribute> optional = ATTRIBUTES
                .stream()
                .filter(attribute1 -> attribute1 instanceof SpellAttribute spellAttribute
                        && spellAttribute.getType().equals(SpellAttribute.POTENCY)
                        && spellAttribute.getSpellType() == spell.getSpellType())
                .findFirst();
        if (optional.isPresent()) {
            attribute = optional.get();
        }
        return (int) livingEntity.getAttributeValue(attribute);
    }

    public static double getCastingSpeed(LivingEntity livingEntity) {
        return 1.0D - livingEntity.getAttributeValue(ModAttributes.CASTING_SPEED);
    }

    public static double getCooldownDiscount(LivingEntity livingEntity) {
        return 1.0D - livingEntity.getAttributeValue(ModAttributes.COOLDOWN_DISCOUNT);
    }

    public static double getSoulDiscount(LivingEntity livingEntity, ISpell spell) {
        Attribute attribute = ModAttributes.SOUL_DISCOUNT;
        Optional<Attribute> optional = ATTRIBUTES
                .stream()
                .filter(attribute1 -> attribute1 instanceof SpellAttribute spellAttribute
                        && spellAttribute.getType().equals(SpellAttribute.DISCOUNT)
                        && spellAttribute.getSpellType() == spell.getSpellType())
                .findFirst();
        if (optional.isPresent()){
            attribute = optional.get();
        }
        return 1.0D - livingEntity.getAttributeValue(attribute);
    }

    public static void init() {

    }

    private static Attribute register(String name, Supplier<Attribute> supplier) {
        Attribute attribute = supplier.get();
        ATTRIBUTES.add(attribute);
        return Registry.register(BuiltInRegistries.ATTRIBUTE, Goety.location(name), attribute);
    }
}
