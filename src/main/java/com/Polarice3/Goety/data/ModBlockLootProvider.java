package com.Polarice3.Goety.data;

import com.Polarice3.Goety.common.blocks.*;
import com.Polarice3.Goety.common.items.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.advancements.critereon.EnchantmentPredicate;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.state.properties.BedPart;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.AlternativesEntry;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.*;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.BiConsumer;

/**
 * Based on @klikli-dev's Block Loot Generator
 */
public class ModBlockLootProvider extends FabricBlockLootTableProvider {
    private static final LootItemCondition.Builder HAS_SILK_TOUCH = MatchTool.toolMatches(ItemPredicate.Builder.item().hasEnchantment(new EnchantmentPredicate(Enchantments.SILK_TOUCH, MinMaxBounds.Ints.atLeast(1))));
    private static final LootItemCondition.Builder HAS_PICKAXE = MatchTool.toolMatches(ItemPredicate.Builder.item().of(ItemTags.PICKAXES));
    private static final LootItemCondition.Builder HAS_SHOVEL = MatchTool.toolMatches(ItemPredicate.Builder.item().of(ItemTags.SHOVELS));
    private static final LootItemCondition.Builder HAS_SHEARS = MatchTool.toolMatches(ItemPredicate.Builder.item().of(Items.SHEARS));
    private static final LootItemCondition.Builder HAS_SHEARS_OR_SILK_TOUCH = HAS_SHEARS.or(HAS_SILK_TOUCH);
    private static final LootItemCondition.Builder HAS_NO_SHEARS_OR_SILK_TOUCH = HAS_SHEARS_OR_SILK_TOUCH.invert();
    private static final float[] NORMAL_LEAVES_SAPLING_CHANCES = new float[]{0.05F, 0.0625F, 0.083333336F, 0.1F};
    private static final float[] NORMAL_LEAVES_STICK_CHANCES = new float[]{0.02F, 0.022222223F, 0.025F, 0.033333335F, 0.1F};

    protected ModBlockLootProvider(FabricDataOutput dataOutput) {
        super(dataOutput);
    }

    @Override
    public void generate(BiConsumer<ResourceLocation, LootTable.Builder> consumer) {
        this.generate();
        this.map.forEach(consumer);
    }

    @Override
    public void generate() {
        Collection<Block> blocks = new ArrayList<>();
        ModBlocks.BLOCKS.forEach(block ->
        {
            ModBlocks.BlockLootSetting setting = ModBlocks.BLOCK_LOOT.get(BuiltInRegistries.BLOCK.getKey(block));
            if (setting.lootTableType == ModBlocks.LootTableType.DROP) {
                blocks.add(block);
            }
        });
        for (Block block : blocks) {
            if (block instanceof DoorBlock) {
                this.add(block, createDoorTable(block));
            } else if (block instanceof SlabBlock) {
                this.add(block, createSlabItemTable(block));
            } else if (block.defaultBlockState().hasProperty(BlockStateProperties.BED_PART)){
                this.add(block, this.createSinglePropConditionTable(block, BlockStateProperties.BED_PART, BedPart.HEAD));
            } else if (block.defaultBlockState().hasProperty(BlockStateProperties.DOUBLE_BLOCK_HALF)) {
                this.add(block, bl -> createSinglePropConditionTable(bl, BlockStateProperties.DOUBLE_BLOCK_HALF, DoubleBlockHalf.LOWER));
            } else {
                this.dropSelf(block);
            }
        }
        LootItemCondition.Builder lootbuilder = LootItemBlockStatePropertyCondition.hasBlockStateProperties(ModBlocks.SNAP_WARTS).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(SnapWartsBlock.AGE, 2));
        LootItemCondition.Builder lootbuilder1 = LootItemBlockStatePropertyCondition.hasBlockStateProperties(ModBlocks.SNAP_WARTS).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(SnapWartsBlock.AGE, 1));
        this.add(ModBlocks.SNAP_WARTS, createSnapWartDrops(ModBlocks.SNAP_WARTS, ModItems.SNAP_FUNGUS, ModBlocks.SNAP_WARTS_ITEM, lootbuilder, lootbuilder1));
        LootItemCondition.Builder lootitemcondition$builder1 = LootItemBlockStatePropertyCondition.hasBlockStateProperties(ModBlocks.HENBANE).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(HenbaneBlock.AGE, 3));
        this.add(ModBlocks.HENBANE, this.createCropDrops(ModBlocks.HENBANE, ModItems.HENBANE_FLOWER, ModBlocks.HENBANE_SEEDS, lootitemcondition$builder1));
        LootItemCondition.Builder lootitemcondition$builder2 = LootItemBlockStatePropertyCondition.hasBlockStateProperties(ModBlocks.DEADLY_NIGHTSHADE).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(NightshadeBlock.AGE, 3));
        this.add(ModBlocks.DEADLY_NIGHTSHADE, this.createCropDrops(ModBlocks.DEADLY_NIGHTSHADE, ModItems.NIGHTSHADE_BLOSSOM, ModBlocks.NIGHTSHADE_SEEDS, lootitemcondition$builder2));
        this.add(ModBlocks.FIRETHORN, (p_249159_) -> {
            return this.applyExplosionDecay(p_249159_, LootTable.lootTable().withPool(LootPool.lootPool().when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(ModBlocks.FIRETHORN).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(BlockStateProperties.AGE_3, 3))).add(LootItem.lootTableItem(ModBlocks.FIRETHORN_BERRIES)).apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 3.0F))).apply(ApplyBonusCount.addUniformBonusCount(Enchantments.BLOCK_FORTUNE))).withPool(LootPool.lootPool().when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(ModBlocks.FIRETHORN).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(BlockStateProperties.AGE_3, 2))).add(LootItem.lootTableItem(ModBlocks.FIRETHORN_BERRIES)).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))).apply(ApplyBonusCount.addUniformBonusCount(Enchantments.BLOCK_FORTUNE))));
        });
        this.add(ModBlocks.COBBLED_DIRT, (block) -> {
            return LootTable.lootTable().withPool(LootPool.lootPool().add(AlternativesEntry.alternatives(
                    LootItem.lootTableItem(ModBlocks.COBBLED_DIRT).when(HAS_SILK_TOUCH),
                    LootItem.lootTableItem(Items.COBBLESTONE).when(HAS_PICKAXE),
                    LootItem.lootTableItem(Items.DIRT).when(HAS_SHOVEL),
                    applyExplosionCondition(ModBlocks.COBBLED_DIRT, LootItem.lootTableItem(ModBlocks.COBBLED_DIRT)))));
        });
        this.add(ModBlocks.SNOWY_DIRT, (p_124183_) -> createSingleItemTableWithSilkTouch(p_124183_, Blocks.DIRT));
        this.add(ModBlocks.COBBLED_DARK_DIRT, (block) -> {
            return LootTable.lootTable().withPool(LootPool.lootPool().add(AlternativesEntry.alternatives(
                    LootItem.lootTableItem(ModBlocks.COBBLED_DARK_DIRT).when(HAS_SILK_TOUCH),
                    LootItem.lootTableItem(Items.COBBLESTONE).when(HAS_PICKAXE),
                    LootItem.lootTableItem(ModBlocks.DARK_DIRT).when(HAS_SHOVEL),
                    applyExplosionCondition(ModBlocks.COBBLED_DARK_DIRT, LootItem.lootTableItem(ModBlocks.COBBLED_DARK_DIRT)))));
        });
        this.add(ModBlocks.SNOWY_DARK_DIRT, (p_124183_) -> createSingleItemTableWithSilkTouch(p_124183_, ModBlocks.DARK_DIRT));
        this.add(ModBlocks.FORBIDDEN_GRASS, (p_124183_) -> createSingleItemTableWithSilkTouch(p_124183_, Blocks.DIRT));
        this.add(ModBlocks.END_GROWTH_BLOCK, (p_124183_) -> createSingleItemTableWithSilkTouch(p_124183_, ModBlocks.END_ROCK));
        this.add(ModBlocks.CHORUS_GRASS_BLOCK, (p_124183_) -> createSingleItemTableWithSilkTouch(p_124183_, ModBlocks.COBBLED_END_STONE_BLOCK));
        this.add(ModBlocks.CHORUS_GRASS_DIRT, (p_124183_) -> createSingleItemTableWithSilkTouch(p_124183_, ModBlocks.END_DIRT));
        this.add(ModBlocks.RED_MOSS_SILTSTONE, (p_124183_) -> createSingleItemTableWithSilkTouch(p_124183_, ModBlocks.COBBLED_SILTSTONE_BLOCK));
        this.add(ModBlocks.RED_MOSS_HIGHROCK, (p_124183_) -> createSingleItemTableWithSilkTouch(p_124183_, ModBlocks.HIGHROCK_BLOCK));
        this.add(ModBlocks.RED_MOSS_DIRT, (p_124183_) -> createSingleItemTableWithSilkTouch(p_124183_, Blocks.DIRT));
        this.add(ModBlocks.SPIDER_NEST, (p_124183_) -> createSingleItemTableWithSilkTouch(p_124183_, Items.STRING, UniformGenerator.between(4.0F, 8.0F)));
        this.add(ModBlocks.SMOOTH_SILTSTONE_BLOCK, (p_124183_) -> createSingleItemTableWithSilkTouch(p_124183_, ModBlocks.COBBLED_SILTSTONE_BLOCK));
        this.add(ModBlocks.OMINOUS_STONE_BLOCK, (p_124183_) -> createSingleItemTableWithSilkTouch(p_124183_, ModBlocks.COBBLED_OMINOUS_STONE_BLOCK));
        this.add(ModBlocks.END_STONE_SLATE_BLOCK, (p_124183_) -> createSingleItemTableWithSilkTouch(p_124183_, ModBlocks.COBBLED_END_STONE_BLOCK));
        this.dropOther(ModBlocks.COBBLED_OMINOUS_STONE_PATH_BLOCK, ModBlocks.COBBLED_OMINOUS_STONE_BLOCK);
        this.dropWhenSilkTouch(ModBlocks.SCULK_RELAY);
        this.dropPottedContents(ModBlocks.POTTED_HENBANE);
        this.dropPottedContents(ModBlocks.POTTED_DEADLY_NIGHTSHADE);
        this.dropPottedContents(ModBlocks.POTTED_SIENNA_GRASS);
        this.dropPottedContents(ModBlocks.POTTED_SIENNA_FERN);
        this.dropPottedContents(ModBlocks.POTTED_WINDSWEPT_DEAD_BUSH);
        this.dropPottedContents(ModBlocks.POTTED_CHORUS_STALK);
        this.dropPottedContents(ModBlocks.POTTED_CHORUS_FERN);
        this.dropPottedContents(ModBlocks.POTTED_HAUNTED_SAPLING);
        this.dropPottedContents(ModBlocks.POTTED_ROTTEN_SAPLING);
        this.dropPottedContents(ModBlocks.POTTED_WINDSWEPT_SAPLING);
        this.dropPottedContents(ModBlocks.POTTED_PINE_SAPLING);
        this.dropPottedContents(ModBlocks.POTTED_CHORUS_SAPLING);
        this.add(ModBlocks.JADE_ORE, (p_124076_) -> {
            return createOreDrop(p_124076_, ModItems.JADE);
        });
        this.add(ModBlocks.ROTTEN_LEAVES, (p_124094_) -> {
            return createRottenLeavesDrops(p_124094_, ModBlocks.ROTTEN_SAPLING, NORMAL_LEAVES_SAPLING_CHANCES);
        });
        this.add(ModBlocks.ROTTEN_BOOKSHELF, (p_124233_) -> {
            return createSingleItemTableWithSilkTouch(p_124233_, Items.BOOK, ConstantValue.exactly(3.0F));
        });
        this.add(ModBlocks.WINDSWEPT_LEAVES, (p_124094_) -> {
            return createLeavesDrops(p_124094_, ModBlocks.WINDSWEPT_SAPLING, NORMAL_LEAVES_SAPLING_CHANCES);
        });
        this.add(ModBlocks.WINDSWEPT_BOOKSHELF, (p_124233_) -> {
            return createSingleItemTableWithSilkTouch(p_124233_, Items.BOOK, ConstantValue.exactly(3.0F));
        });
        this.add(ModBlocks.PINE_LEAVES, (p_124094_) -> {
            return createLeavesDrops(p_124094_, ModBlocks.PINE_SAPLING, NORMAL_LEAVES_SAPLING_CHANCES);
        });
        this.add(ModBlocks.PINE_BOOKSHELF, (p_124233_) -> {
            return createSingleItemTableWithSilkTouch(p_124233_, Items.BOOK, ConstantValue.exactly(3.0F));
        });
        this.add(ModBlocks.CHORUS_LEAVES, (p_124094_) -> {
            return createChorusLeavesDrops(p_124094_, ModBlocks.CHORUS_SAPLING, NORMAL_LEAVES_SAPLING_CHANCES);
        });
        this.add(ModBlocks.CHORUS_BLOSSOM_LEAVES, (p_124094_) -> {
            return createLeavesDrops(p_124094_, ModBlocks.CHORUS_BLOSSOM_VINES, NORMAL_LEAVES_STICK_CHANCES);
        });
        this.add(ModBlocks.CHORUS_BOOKSHELF, (p_124233_) -> {
            return createSingleItemTableWithSilkTouch(p_124233_, Items.BOOK, ConstantValue.exactly(3.0F));
        });
        this.add(ModBlocks.CORRUPT_CHORUS_BOOKSHELF, (p_124233_) -> {
            return createSingleItemTableWithSilkTouch(p_124233_, Items.BOOK, ConstantValue.exactly(3.0F));
        });
        this.add(ModBlocks.CRYPT_BOOKSHELF, (p_124233_) -> {
            return createSingleItemTableWithSilkTouch(p_124233_, Items.BOOK, ConstantValue.exactly(3.0F));
        });
        this.add(ModBlocks.SKULL_PILE, (p_124233_) -> {
            return createSingleItemTableWithSilkTouch(p_124233_, Items.BONE, UniformGenerator.between(3.0F, 6.0F));
        });
        this.add(ModBlocks.CRYPT_URN, createSilkTouchOnlyTable(ModBlocks.CRYPT_URN));
        this.add(ModBlocks.CRYPT_CHEST, (p_124233_) -> {
            return createSingleItemTableWithSilkTouch(p_124233_, ModBlocks.CRYPT_STONE_BLOCK, UniformGenerator.between(2.0F, 4.0F));
        });
        this.add(ModBlocks.LOFTY_CHEST, (p_124233_) -> {
            return createSingleItemTableWithSilkTouch(p_124233_, Items.OBSIDIAN, UniformGenerator.between(2.0F, 4.0F));
        });
        this.add(ModBlocks.SPIDER_SAC, (p_124233_) -> {
            return createSingleItemTableWithSilkTouch(p_124233_, Items.STRING, UniformGenerator.between(2.0F, 4.0F));
        });
        this.add(ModBlocks.STASH_URN, createSilkTouchOnlyTable(ModBlocks.STASH_URN));
        this.add(ModBlocks.SIENNA_GRASS, this::createGrassDrops);
        this.add(ModBlocks.TALL_SIENNA_GRASS, (p_124233_) -> {
            return createDoublePlantWithSeedDrops(p_124233_, ModBlocks.SIENNA_GRASS);
        });
        this.add(ModBlocks.SIENNA_FERN, this::createGrassDrops);
        this.add(ModBlocks.LARGE_SIENNA_FERN, (p_124233_) -> {
            return createDoublePlantWithSeedDrops(p_124233_, ModBlocks.SIENNA_FERN);
        });
        this.add(ModBlocks.WINDSWEPT_DEAD_BUSH, (p_249226_) -> {
            return createShearsDispatchTable(p_249226_, this.applyExplosionDecay(p_249226_, LootItem.lootTableItem(Items.STICK).apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 2.0F)))));
        });
        this.add(ModBlocks.SIENNA_VINE, (p_124233_) -> {
            return createShearsOnlyDrop(ModBlocks.SIENNA_VINE);
        });
        this.add(ModBlocks.CHORUS_VINE, (p_124233_) -> {
            return createShearsOnlyDrop(ModBlocks.CHORUS_VINE);
        });
        this.add(ModBlocks.END_GRASS_SPROUT, (p_124233_) -> {
            return createShearsOnlyDrop(ModBlocks.END_GRASS_SPROUT);
        });
        this.add(ModBlocks.END_GRASS, (p_124233_) -> {
            return createShearsOnlyDrop(ModBlocks.END_GRASS);
        });
        this.add(ModBlocks.TALL_END_GRASS, (p_124233_) -> {
            return createShearsOnlyDrop(ModBlocks.TALL_END_GRASS);
        });
        this.add(ModBlocks.CHORUS_TALL_GRASS, (p_124233_) -> {
            return createShearsOnlyDrop(ModBlocks.CHORUS_TALL_GRASS);
        });
        this.add(ModBlocks.CHORUS_FERN_SPROUT, (p_124233_) -> {
            return createShearsOnlyDrop(ModBlocks.CHORUS_FERN_SPROUT);
        });
        this.add(ModBlocks.CHORUS_FERN, (p_124233_) -> {
            return createShearsOnlyDrop(ModBlocks.CHORUS_FERN);
        });
        this.add(ModBlocks.LARGE_CHORUS_FERN, (p_124233_) -> {
            return createDoublePlantShearsDrop(ModBlocks.LARGE_CHORUS_FERN);
        });
        this.addNetherVinesDropTable(ModBlocks.END_GROWTH_VINES, ModBlocks.END_GROWTH_VINES_PLANT);
        this.add(ModBlocks.VOID_BARREL, this::createVoidBarrelConditionTable);
        this.dropOther(ModBlocks.VOID_CAULDRON, Blocks.CAULDRON.asItem());
        this.dropOther(ModBlocks.END_MUD_CAULDRON, Blocks.CAULDRON.asItem());
        this.add(ModBlocks.DETRITUS_DUST, (p_251108_) -> {
            return LootTable.lootTable().withPool(LootPool.lootPool().when(LootItemEntityPropertyCondition.entityPresent(LootContext.EntityTarget.THIS)).add(AlternativesEntry.alternatives(AlternativesEntry.alternatives(LayerBlock.LAYERS.getPossibleValues(), (p_252097_) -> {
                return LootItem.lootTableItem(ModBlocks.DETRITUS_DUST).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(p_251108_).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(LayerBlock.LAYERS, p_252097_))).apply(SetItemCountFunction.setCount(ConstantValue.exactly((float) p_252097_.intValue())));
            }).when(HAS_NO_SILK_TOUCH), AlternativesEntry.alternatives(LayerBlock.LAYERS.getPossibleValues(), (p_251216_) -> {
                return p_251216_ == 8 ? LootItem.lootTableItem(ModBlocks.DETRITUS) : LootItem.lootTableItem(ModBlocks.DETRITUS_DUST).apply(SetItemCountFunction.setCount(ConstantValue.exactly((float) p_251216_.intValue()))).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(p_251108_).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(LayerBlock.LAYERS, p_251216_)));
            }))));
        });
        this.add(ModBlocks.END_SOIL_DEBRIS, (p_251108_) -> {
            return LootTable.lootTable().withPool(LootPool.lootPool().when(LootItemEntityPropertyCondition.entityPresent(LootContext.EntityTarget.THIS)).add(AlternativesEntry.alternatives(AlternativesEntry.alternatives(LayerBlock.LAYERS.getPossibleValues(), (p_252097_) -> {
                return LootItem.lootTableItem(ModBlocks.END_SOIL_DEBRIS).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(p_251108_).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(LayerBlock.LAYERS, p_252097_))).apply(SetItemCountFunction.setCount(ConstantValue.exactly((float) p_252097_.intValue())));
            }).when(HAS_NO_SILK_TOUCH), AlternativesEntry.alternatives(LayerBlock.LAYERS.getPossibleValues(), (p_251216_) -> {
                return p_251216_ == 8 ? LootItem.lootTableItem(ModBlocks.END_SOIL) : LootItem.lootTableItem(ModBlocks.END_SOIL_DEBRIS).apply(SetItemCountFunction.setCount(ConstantValue.exactly((float) p_251216_.intValue()))).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(p_251108_).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(LayerBlock.LAYERS, p_251216_)));
            }))));
        });
    }

    protected LootTable.Builder createRottenLeavesDrops(Block p_124264_, Block p_124265_, float... p_124266_) {
        return createLeavesDrops(p_124264_, p_124265_, p_124266_).withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).when(HAS_NO_SHEARS_OR_SILK_TOUCH).add(applyExplosionCondition(p_124264_, LootItem.lootTableItem(Items.ROTTEN_FLESH)).when(BonusLevelTableCondition.bonusLevelFlatChance(Enchantments.BLOCK_FORTUNE, 0.005F, 0.0055555557F, 0.00625F, 0.008333334F, 0.025F))));
    }

    protected LootTable.Builder createChorusLeavesDrops(Block p_250088_, Block p_250731_, float... p_248949_) {
        return createSilkTouchOrShearsDispatchTable(p_250088_, this.applyExplosionCondition(p_250088_, LootItem.lootTableItem(p_250731_)).when(BonusLevelTableCondition.bonusLevelFlatChance(Enchantments.BLOCK_FORTUNE, p_248949_))).withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).when(HAS_NO_SHEARS_OR_SILK_TOUCH).add(this.applyExplosionDecay(p_250088_, LootItem.lootTableItem(ModBlocks.CHORUS_VINE.asItem()).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F)))).when(BonusLevelTableCondition.bonusLevelFlatChance(Enchantments.BLOCK_FORTUNE, NORMAL_LEAVES_STICK_CHANCES))));
    }

    protected LootTable.Builder createSnapWartDrops(Block block, Item drop, Item drop1, LootItemCondition.Builder builder, LootItemCondition.Builder builder1) {
        return applyExplosionDecay(block, LootTable.lootTable().withPool(LootPool.lootPool().add(LootItem.lootTableItem(drop).when(builder)).add(LootItem.lootTableItem(drop1).when(builder1))));
    }

    @Override
    public LootTable.Builder createOreDrop(Block p_124140_, Item p_124141_) {
        return createSilkTouchDispatchTable(p_124140_, applyExplosionDecay(p_124140_, LootItem.lootTableItem(p_124141_).apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE))));
    }

    protected <T extends Comparable<T> & StringRepresentable> LootTable.Builder createVoidBarrelConditionTable(Block p_252154_) {
        return LootTable.lootTable().withPool(this.applyExplosionCondition(p_252154_, LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(p_252154_).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(p_252154_).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(BlockStateProperties.DOUBLE_BLOCK_HALF, DoubleBlockHalf.LOWER).hasProperty(BlockStateProperties.LIT, false))))));
    }
}
