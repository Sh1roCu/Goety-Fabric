package com.Polarice3.Goety.init;

import com.Polarice3.Goety.Goety;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
import net.minecraft.world.level.storage.loot.entries.LootTableReference;

import java.util.List;

public class ModLootInject {

    private static final List<String> CHEST_TABLES = List.of("abandoned_mineshaft", "ancient_city", "ancient_city_ice_box", "desert_pyramid", "jungle_temple", "nether_bridge", "pillager_outpost", "simple_dungeon", "stronghold_crossing", "stronghold_library", "woodland_mansion");

    private static final List<String> ENTITY_TABLES = List.of("cave_spider", "frog", "ravager", "spider", "witch", "zoglin");

    public static void injectLootTables() {
        String chestsPrefix = "minecraft:chests/";
        String entitiesPrefix = "minecraft:entities/";

        LootTableEvents.MODIFY.register((resourceManager, lootManager, id, tableBuilder, source) -> {
            String name = id.toString();

            if ((name.startsWith(chestsPrefix) && CHEST_TABLES.contains(name.substring(chestsPrefix.length())))
                    || (name.startsWith(entitiesPrefix) && ENTITY_TABLES.contains(name.substring(entitiesPrefix.length())))) {
                String file = name.substring("minecraft:".length());
                tableBuilder.withPool(getInjectPool(file));
            }
        });
    }

    private static LootPool.Builder getInjectPool(String entryName) {
        return LootPool.lootPool().add(getInjectEntry(entryName));
    }

    private static LootPoolEntryContainer.Builder<?> getInjectEntry(String name) {
        return LootTableReference.lootTableReference(Goety.location("inject/" + name));
    }
}
