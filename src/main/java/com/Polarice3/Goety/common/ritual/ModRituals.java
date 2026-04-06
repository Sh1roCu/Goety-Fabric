package com.Polarice3.Goety.common.ritual;

import com.Polarice3.Goety.Goety;
import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.fabricmc.fabric.api.event.registry.RegistryAttribute;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;

import java.util.function.Supplier;

public class ModRituals {

    public static final ResourceKey<Registry<ModRitualFactory>> REGISTRY_KEY = ResourceKey.createRegistryKey(Goety.location("ritual_factory"));
    public static final Registry<ModRitualFactory> REGISTRY = FabricRegistryBuilder
            .createSimple(REGISTRY_KEY)
            .attribute(RegistryAttribute.SYNCED)
            .buildAndRegister();

    public static final ModRitualFactory CRAFT_RITUAL =
            register("craft",
                    () -> new ModRitualFactory(CraftItemRitual::new));

    public static final ModRitualFactory SUMMON_RITUAL =
            register("summon",
                    () -> new ModRitualFactory((ritual) -> new SummonRitual(ritual, false, false)));

    public static final ModRitualFactory SUMMON_TAMED_RITUAL =
            register("summon_tamed",
                    () -> new ModRitualFactory((ritual) -> new SummonRitual(ritual, true, false)));

    public static final ModRitualFactory SUMMON_TAMED_NO_VARIANT_RITUAL =
            register("summon_tamed_no_variant",
                    () -> new ModRitualFactory((ritual) -> new SummonRitual(ritual, true, true)));

    public static final ModRitualFactory CONVERT_RITUAL =
            register("convert",
                    () -> new ModRitualFactory((ritual) -> new ConvertRitual(ritual, false, false)));

    public static final ModRitualFactory CONVERT_TAMED_RITUAL =
            register("convert_tamed",
                    () -> new ModRitualFactory((ritual) -> new ConvertRitual(ritual, true, false)));

    public static final ModRitualFactory CONVERT_COMPLETE_TAMED_RITUAL =
            register("convert_complete_tamed",
                    () -> new ModRitualFactory((ritual) -> new ConvertRitual(ritual, true, true)));

    public static final ModRitualFactory ENCHANT =
            register("enchant",
                    () -> new ModRitualFactory(EnchantItemRitual::new));

    public static final ModRitualFactory TELEPORT =
            register("teleport",
                    () -> new ModRitualFactory(TeleportRitual::new));

    public static void init() {

    }

    private static ModRitualFactory register(String name, Supplier<ModRitualFactory> supplier) {
        return Registry.register(REGISTRY, Goety.location(name), supplier.get());
    }
}
/*
 * MIT License
 *
 * Copyright 2020 klikli-dev
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 * of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following
 * conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial
 * portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR
 * PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT
 * OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 * OTHER DEALINGS IN THE SOFTWARE.
 */