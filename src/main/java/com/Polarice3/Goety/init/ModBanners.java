package com.Polarice3.Goety.init;

import com.Polarice3.Goety.Goety;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BannerPattern;

import java.util.function.Supplier;

public class ModBanners {

    public static final BannerPattern CROSS = create("cross");
    public static final BannerPattern GALE = create("gale");
    public static final BannerPattern MOON = create("moon");

    private static BannerPattern create(String name) {
        return register(name, () -> new BannerPattern(name));
    }


    public static void init() {

    }

    private static BannerPattern register(String name, Supplier<BannerPattern> supplier) {
        return Registry.register(BuiltInRegistries.BANNER_PATTERN, Goety.location(name), supplier.get());
    }
}