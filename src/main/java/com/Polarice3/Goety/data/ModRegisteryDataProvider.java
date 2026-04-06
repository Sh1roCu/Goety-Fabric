package com.Polarice3.Goety.data;

import com.Polarice3.Goety.common.world.features.ConfiguredFeatures;
import com.Polarice3.Goety.common.world.features.PlacedFeatures;
import com.Polarice3.Goety.init.ModTrimMaterials;
import com.Polarice3.Goety.utils.ModDamageSource;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;

import java.util.concurrent.CompletableFuture;

public class ModRegisteryDataProvider extends FabricDynamicRegistryProvider {

    public ModRegisteryDataProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    public static void generate(RegistrySetBuilder builder) {
        builder.add(Registries.CONFIGURED_FEATURE, ConfiguredFeatures::bootstrap);
        builder.add(Registries.PLACED_FEATURE, PlacedFeatures::bootstrap);
        builder.add(Registries.DAMAGE_TYPE, ModDamageSource::bootstrap);
        builder.add(Registries.TRIM_MATERIAL, ModTrimMaterials::bootstrap);
    }

    @Override
    protected void configure(HolderLookup.Provider provider, Entries entries) {
        entries.addAll(provider.lookupOrThrow(Registries.CONFIGURED_FEATURE));
        entries.addAll(provider.lookupOrThrow(Registries.PLACED_FEATURE));
        entries.addAll(provider.lookupOrThrow(Registries.DAMAGE_TYPE));
        entries.addAll(provider.lookupOrThrow(Registries.TRIM_MATERIAL));
    }

    @Override
    public String getName() {
        return "Goety Dynamic";
    }
}