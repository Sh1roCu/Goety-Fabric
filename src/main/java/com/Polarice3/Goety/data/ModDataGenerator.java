package com.Polarice3.Goety.data;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.data.DataProvider;

public class ModDataGenerator implements DataGeneratorEntrypoint {

    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        FabricDataGenerator.Pack generator = fabricDataGenerator.createPack();
        generator.addProvider(ModBlockLootProvider::new);
        generator.addProvider(ModCraftingProvider::new);
        generator.addProvider((DataProvider.Factory<DataProvider>) ModWeaponAttributesProvider::new);
        // generator.addProvider(ModAtlasProvider::new);
        generator.addProvider(ModDamageTypeTagsProvider::new);
        generator.addProvider(ModEntityTypeTagsProvider::new);
        generator.addProvider(ModItemTagsProvider::new);
        generator.addProvider(ModBlockTagsProvider::new);
        generator.addProvider(ModStructureTagsProvider::new);
        generator.addProvider(ModAdvancementProvider::new);
        generator.addProvider(ModRegisteryDataProvider::new);
    }

    @Override
    public void buildRegistry(RegistrySetBuilder registryBuilder) {
        ModRegisteryDataProvider.generate(registryBuilder);
    }
}
