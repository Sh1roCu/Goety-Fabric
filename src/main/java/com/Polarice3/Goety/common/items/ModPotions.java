package com.Polarice3.Goety.common.items;

import com.Polarice3.Goety.Goety;
import com.Polarice3.Goety.common.effects.GoetyEffects;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.alchemy.Potion;

import java.util.function.Supplier;

public class ModPotions {

    public static final Potion CLIMBING = register("climbing", () -> new Potion(new MobEffectInstance(GoetyEffects.CLIMBING, 3600)));
    public static final Potion LONG_CLIMBING = register("long_climbing", () -> new Potion("climbing", new MobEffectInstance(GoetyEffects.CLIMBING, 9600)));

    public static final Potion SPASMS = register("spasms", () -> new Potion(new MobEffectInstance(GoetyEffects.SPASMS, 900)));
    public static final Potion LONG_SPASMS = register("long_spasms", () -> new Potion("spasms", new MobEffectInstance(GoetyEffects.SPASMS, 1800)));

    public static void init() {

    }

    private static Potion register(String name, Supplier<Potion> supplier) {
        return Registry.register(BuiltInRegistries.POTION, Goety.location(name), supplier.get());
    }
}
