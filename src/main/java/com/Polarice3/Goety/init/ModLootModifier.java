package com.Polarice3.Goety.init;

import cn.sh1rocu.goety.loot.AddItemLootFunction;
import cn.sh1rocu.goety.loot.InstantPickFunction;
import com.Polarice3.Goety.Goety;
import com.Polarice3.Goety.common.items.ModItems;
import com.google.common.collect.Sets;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.predicates.MatchTool;

import java.util.Set;
import java.util.function.Supplier;

public class ModLootModifier {

    public static final LootItemCondition[] INSTANT_PICK_CONDITIONS = new LootItemCondition[]{
            MatchTool.toolMatches(ItemPredicate.Builder.item().of(ModItems.EERIE_PICKAXE)).build()
    };

    public static final LootItemFunctionType INSTANT_PICK_TYPE = register("instant_pick", () -> new LootItemFunctionType(new InstantPickFunction.Serializer()));

    public static final LootItemFunctionType ADD_LOOT_TYPE = register("add_loot", () -> new LootItemFunctionType(new AddItemLootFunction.Serializer()));

    public static final Set<ResourceLocation> CROSS_POTTERY_SHERD_DIG = Sets.newHashSet(BuiltInLootTables.OCEAN_RUIN_COLD_ARCHAEOLOGY, BuiltInLootTables.OCEAN_RUIN_WARM_ARCHAEOLOGY);
    public static final Set<ResourceLocation> DEAD_POTTERY_SHERD_DIG = Sets.newHashSet(BuiltInLootTables.DESERT_PYRAMID_ARCHAEOLOGY, BuiltInLootTables.DESERT_WELL_ARCHAEOLOGY);
    public static final Set<ResourceLocation> EMPTY_FOCUS_DIG = Sets.newHashSet(
            BuiltInLootTables.DESERT_PYRAMID_ARCHAEOLOGY, BuiltInLootTables.DESERT_WELL_ARCHAEOLOGY, new ResourceLocation("minecraft:archaeology/ancient_city")
    );
    public static final Set<ResourceLocation> FORBIDDEN_PIECE_DIG = Sets.newHashSet(
            BuiltInLootTables.DESERT_PYRAMID_ARCHAEOLOGY, BuiltInLootTables.TRAIL_RUINS_ARCHAEOLOGY_RARE, new ResourceLocation("minecraft:archaeology/ancient_city")
    );
    public static final Set<ResourceLocation> HAUNT_POTTERY_SHERD_DIG = Sets.newHashSet(BuiltInLootTables.DESERT_PYRAMID_ARCHAEOLOGY, BuiltInLootTables.DESERT_WELL_ARCHAEOLOGY);
    public static final Set<ResourceLocation> NIGHT_POTTERY_SHERD_DIG = Sets.newHashSet(BuiltInLootTables.DESERT_PYRAMID_ARCHAEOLOGY, BuiltInLootTables.DESERT_WELL_ARCHAEOLOGY);
    public static final Set<ResourceLocation> OCCULT_FABRIC_DIG = Sets.newHashSet(BuiltInLootTables.DESERT_PYRAMID_ARCHAEOLOGY, BuiltInLootTables.DESERT_WELL_ARCHAEOLOGY);
    public static final Set<ResourceLocation> SOUL_POTTERY_SHERD_DIG = Sets.newHashSet(BuiltInLootTables.DESERT_PYRAMID_ARCHAEOLOGY, BuiltInLootTables.DESERT_WELL_ARCHAEOLOGY);
    public static final Set<ResourceLocation> SPIRIT_FABRIC_DIG = Sets.newHashSet(
            BuiltInLootTables.DESERT_PYRAMID_ARCHAEOLOGY, BuiltInLootTables.DESERT_WELL_ARCHAEOLOGY, new ResourceLocation("minecraft:archaeology/ancient_city")
    );

    public static void init() {
        LootTableEvents.MODIFY.register((resourceManager, lootManager, id, builder, source) -> {
            builder.modifyPools(b -> b.apply(new InstantPickFunction(INSTANT_PICK_CONDITIONS)));

            if (CROSS_POTTERY_SHERD_DIG.contains(id)) {
                builder.modifyPools(b -> b.apply(AddItemLootFunction
                        .builder(ModItems.CROSS_POTTERY_SHERD, 1)
                        .when(LootItemRandomChanceCondition.randomChance(0.15F))));
            }
            if (DEAD_POTTERY_SHERD_DIG.contains(id)) {
                builder.modifyPools(b -> b.apply(AddItemLootFunction
                        .builder(ModItems.DEAD_POTTERY_SHERD, 1)
                        .when(LootItemRandomChanceCondition.randomChance(0.15F))));
            }
            if (EMPTY_FOCUS_DIG.contains(id)) {
                builder.modifyPools(b -> b.apply(AddItemLootFunction
                        .builder(ModItems.EMPTY_FOCUS, 1)
                        .when(LootItemRandomChanceCondition.randomChance(0.15F))));
            }
            if (FORBIDDEN_PIECE_DIG.contains(id)) {
                builder.modifyPools(b -> b.apply(AddItemLootFunction
                        .builder(ModItems.FORBIDDEN_PIECE, 1)
                        .when(LootItemRandomChanceCondition.randomChance(0.025F))));
            }
            if (HAUNT_POTTERY_SHERD_DIG.contains(id)) {
                builder.modifyPools(b -> b.apply(AddItemLootFunction
                        .builder(ModItems.HAUNT_POTTERY_SHERD, 1)
                        .when(LootItemRandomChanceCondition.randomChance(0.15F))));
            }
            if (NIGHT_POTTERY_SHERD_DIG.contains(id)) {
                builder.modifyPools(b -> b.apply(AddItemLootFunction
                        .builder(ModItems.NIGHT_POTTERY_SHERD, 1)
                        .when(LootItemRandomChanceCondition.randomChance(0.15F))));
            }
            if (OCCULT_FABRIC_DIG.contains(id)) {
                builder.modifyPools(b -> b.apply(AddItemLootFunction
                        .builder(ModItems.OCCULT_FABRIC, 1)
                        .when(LootItemRandomChanceCondition.randomChance(0.15F))));
            }
            if (SOUL_POTTERY_SHERD_DIG.contains(id)) {
                builder.modifyPools(b -> b.apply(AddItemLootFunction
                        .builder(ModItems.SOUL_POTTERY_SHERD, 1)
                        .when(LootItemRandomChanceCondition.randomChance(0.15F))));
            }
            if (SPIRIT_FABRIC_DIG.contains(id)) {
                builder.modifyPools(b -> b.apply(AddItemLootFunction
                        .builder(ModItems.SPIRIT_FABRIC, 1)
                        .when(LootItemRandomChanceCondition.randomChance(0.15F))));
            }
        });
    }

    private static LootItemFunctionType register(String name, Supplier<LootItemFunctionType> supplier) {
        return Registry.register(BuiltInRegistries.LOOT_FUNCTION_TYPE, Goety.location(name), supplier.get());
    }
}
