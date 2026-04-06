package com.Polarice3.Goety.init;

import com.Polarice3.Goety.Goety;
import com.Polarice3.Goety.common.items.ModItems;
import com.google.common.collect.Maps;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;

import java.util.Map;
import java.util.function.Supplier;

public class ModPotPatterns {

    public static final ResourceKey<String> CROSS = create("cross_pottery_pattern");
    public static final ResourceKey<String> DEAD = create("dead_pottery_pattern");
    public static final ResourceKey<String> HAUNT = create("haunt_pottery_pattern");
    public static final ResourceKey<String> NIGHT = create("night_pottery_pattern");
    public static final ResourceKey<String> SOUL = create("soul_pottery_pattern");

    public static final Map<Item, ResourceKey<String>> ITEM_TO_POT_TEXTURE = Maps.newHashMap();

    private static ResourceKey<String> create(String name) {
        return ResourceKey.create(Registries.DECORATED_POT_PATTERNS, Goety.location(register(name, () -> name)));
    }

    public static void addPatterns() {
        ITEM_TO_POT_TEXTURE.put(ModItems.CROSS_POTTERY_SHERD, CROSS);
        ITEM_TO_POT_TEXTURE.put(ModItems.DEAD_POTTERY_SHERD, DEAD);
        ITEM_TO_POT_TEXTURE.put(ModItems.HAUNT_POTTERY_SHERD, HAUNT);
        ITEM_TO_POT_TEXTURE.put(ModItems.NIGHT_POTTERY_SHERD, NIGHT);
        ITEM_TO_POT_TEXTURE.put(ModItems.SOUL_POTTERY_SHERD, SOUL);
    }

    public static void init() {

    }

    private static String register(String name, Supplier<String> supplier) {
        return Registry.register(BuiltInRegistries.DECORATED_POT_PATTERNS, Goety.location(name), supplier.get());
    }
}
