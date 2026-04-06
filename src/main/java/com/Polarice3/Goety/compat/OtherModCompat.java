package com.Polarice3.Goety.compat;

import com.Polarice3.Goety.Goety;
import com.Polarice3.Goety.compat.trinkets.TrinketsIntegration;
import com.Polarice3.Goety.compat.patchouli.PatchouliIntegration;
import com.google.common.collect.ImmutableMap;
import net.fabricmc.loader.api.FabricLoader;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;
import java.util.function.Supplier;

public final class OtherModCompat {
    private static final Map<String, Supplier<ICompatable>> MODULE_TYPES = ImmutableMap.<String, Supplier<ICompatable>>builder()
            .put("trinkets", TrinketsIntegration::new)
            .put("patchouli", PatchouliIntegration::new)
            .build();
    private static final Map<String, ICompatable> MODULES = new HashMap<>();

    public static void setup() {
        populateModules(FabricLoader.getInstance()::isModLoaded);
        MODULES.values().forEach(ICompatable::setup);
    }

    private static void populateModules(Predicate<String> isLoaded) {
        for (Map.Entry<String, Supplier<ICompatable>> entry : MODULE_TYPES.entrySet()) {
            String id = entry.getKey();
            if (isLoaded.test(id)) {
                MODULES.put(id, entry.getValue().get());
                Goety.LOGGER.info("Loading compat module for mod " + id);
            }
        }
    }
}
