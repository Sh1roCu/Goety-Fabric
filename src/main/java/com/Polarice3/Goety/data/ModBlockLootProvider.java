package com.Polarice3.Goety.data;

import com.Polarice3.Goety.common.blocks.LayerBlock;
import com.Polarice3.Goety.common.blocks.ModBlocks;
import com.Polarice3.Goety.common.blocks.SnapWartsBlock;
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
            } else if (block.defaultBlockState().hasProperty(BlockStateProperties.DOUBLE_BLOCK_HALF)) {
                this.add(block, bl -> createSinglePropConditionTable(bl, BlockStateProperties.DOUBLE_BLOCK_HALF, DoubleBlockHalf.LOWER));
            } else {
                this.dropSelf(block);
            }
        }
        LootItemCondition.Builder lootbuilder = LootItemBlockStatePropertyCondition.hasBlockStateProperties(ModBlocks.SNAP_WARTS).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(SnapWartsBlock.AGE, 2));
        LootItemCondition.Builder lootbuilder1 = LootItemBlockStatePropertyCondition.hasBlockStateProperties(ModBlocks.SNAP_WARTS).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(SnapWartsBlock.AGE, 1));
        this.add(ModBlocks.SNAP_WARTS, createSnapWartDrops(ModBlocks.SNAP_WARTS, ModItems.SNAP_FUNGUS, ModBlocks.SNAP_WARTS_ITEM, lootbuilder, lootbuilder1));
        this.add(ModBlocks.FIRETHORN, (p_249159_) -> this.applyExplosionDecay(p_249159_, LootTable.lootTable().withPool(LootPool.lootPool().when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(ModBlocks.FIRETHORN).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(BlockStateProperties.AGE_3, 3))).add(LootItem.lootTableItem(ModBlocks.FIRETHORN_BERRIES)).apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 3.0F))).apply(ApplyBonusCount.addUniformBonusCount(Enchantments.BLOCK_FORTUNE))).withPool(LootPool.lootPool().when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(ModBlocks.FIRETHORN).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(BlockStateProperties.AGE_3, 2))).add(LootItem.lootTableItem(ModBlocks.FIRETHORN_BERRIES)).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))).apply(ApplyBonusCount.addUniformBonusCount(Enchantments.BLOCK_FORTUNE)))));
        this.add(ModBlocks.COBBLED_DIRT, (block) -> LootTable.lootTable().withPool(LootPool.lootPool().add(AlternativesEntry.alternatives(
                LootItem.lootTableItem(ModBlocks.COBBLED_DIRT).when(HAS_SILK_TOUCH),
                LootItem.lootTableItem(Items.COBBLESTONE).when(HAS_PICKAXE),
                LootItem.lootTableItem(Items.DIRT).when(HAS_SHOVEL),
                applyExplosionCondition(ModBlocks.COBBLED_DIRT, LootItem.lootTableItem(ModBlocks.COBBLED_DIRT))))));
        this.add(ModBlocks.SNOWY_DIRT, (block) -> createSingleItemTableWithSilkTouch(block, Blocks.DIRT));
        this.add(ModBlocks.COBBLED_DARK_DIRT, (block) -> LootTable.lootTable().withPool(LootPool.lootPool().add(AlternativesEntry.alternatives(
                LootItem.lootTableItem(ModBlocks.COBBLED_DARK_DIRT).when(HAS_SILK_TOUCH),
                LootItem.lootTableItem(Items.COBBLESTONE).when(HAS_PICKAXE),
                LootItem.lootTableItem(ModBlocks.DARK_DIRT).when(HAS_SHOVEL),
                applyExplosionCondition(ModBlocks.COBBLED_DARK_DIRT, LootItem.lootTableItem(ModBlocks.COBBLED_DARK_DIRT))))));
        this.add(ModBlocks.SNOWY_DARK_DIRT, (block) -> createSingleItemTableWithSilkTouch(block, ModBlocks.DARK_DIRT));
        this.add(ModBlocks.FORBIDDEN_GRASS, (block) -> createSingleItemTableWithSilkTouch(block, Blocks.DIRT));
        this.add(ModBlocks.END_GROWTH_BLOCK, (block) -> createSingleItemTableWithSilkTouch(block, ModBlocks.END_ROCK));
        this.add(ModBlocks.CHORUS_GRASS_BLOCK, (block) -> createSingleItemTableWithSilkTouch(block, ModBlocks.COBBLED_END_STONE_BLOCK));
        this.add(ModBlocks.RED_MOSS_SILTSTONE, (block) -> createSingleItemTableWithSilkTouch(block, ModBlocks.COBBLED_SILTSTONE_BLOCK));
        this.add(ModBlocks.RED_MOSS_HIGHROCK, (block) -> createSingleItemTableWithSilkTouch(block, ModBlocks.HIGHROCK_BLOCK));
        this.add(ModBlocks.RED_MOSS_DIRT, (block) -> createSingleItemTableWithSilkTouch(block, Blocks.DIRT));
        this.add(ModBlocks.SPIDER_NEST, (block) -> createSingleItemTableWithSilkTouch(block, Items.STRING, UniformGenerator.between(4.0F, 8.0F)));
        this.add(ModBlocks.SMOOTH_SILTSTONE_BLOCK, (block) -> createSingleItemTableWithSilkTouch(block, ModBlocks.COBBLED_SILTSTONE_BLOCK));
        this.add(ModBlocks.OMINOUS_STONE_BLOCK, (block) -> createSingleItemTableWithSilkTouch(block, ModBlocks.COBBLED_OMINOUS_STONE_BLOCK));
        this.add(ModBlocks.END_STONE_SLATE_BLOCK, (block) -> createSingleItemTableWithSilkTouch(block, ModBlocks.COBBLED_END_STONE_BLOCK));
        this.dropOther(ModBlocks.COBBLED_OMINOUS_STONE_PATH_BLOCK, ModBlocks.COBBLED_OMINOUS_STONE_BLOCK);
        this.dropWhenSilkTouch(ModBlocks.SCULK_RELAY);
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
        this.add(ModBlocks.JADE_ORE, (block) -> createOreDrop(block, ModItems.JADE));
        this.add(ModBlocks.CRYSTAL_BALL, (block) -> createSilkTouchDispatchTable(block, LootItem.lootTableItem(Items.GOLD_INGOT).apply(SetItemCountFunction.setCount(ConstantValue.exactly(2.0F)))));
        this.add(ModBlocks.ROTTEN_LEAVES, (block) -> createRottenLeavesDrops(block, ModBlocks.ROTTEN_SAPLING, NORMAL_LEAVES_SAPLING_CHANCES));
        this.add(ModBlocks.ROTTEN_BOOKSHELF, (block) -> createSingleItemTableWithSilkTouch(block, Items.BOOK, ConstantValue.exactly(3.0F)));
        this.add(ModBlocks.WINDSWEPT_LEAVES, (block) -> createLeavesDrops(block, ModBlocks.WINDSWEPT_SAPLING, NORMAL_LEAVES_SAPLING_CHANCES));
        this.add(ModBlocks.WINDSWEPT_BOOKSHELF, (block) -> createSingleItemTableWithSilkTouch(block, Items.BOOK, ConstantValue.exactly(3.0F)));
        this.add(ModBlocks.PINE_LEAVES, (block) -> createLeavesDrops(block, ModBlocks.PINE_SAPLING, NORMAL_LEAVES_SAPLING_CHANCES));
        this.add(ModBlocks.PINE_BOOKSHELF, (block) -> createSingleItemTableWithSilkTouch(block, Items.BOOK, ConstantValue.exactly(3.0F)));
        this.add(ModBlocks.CHORUS_LEAVES, (block) -> createChorusLeavesDrops(block, ModBlocks.CHORUS_SAPLING, NORMAL_LEAVES_SAPLING_CHANCES));
        this.add(ModBlocks.CHORUS_BLOSSOM_LEAVES, (block) -> createLeavesDrops(block, ModBlocks.CHORUS_BLOSSOM_VINES, NORMAL_LEAVES_STICK_CHANCES));
        this.add(ModBlocks.CHORUS_BOOKSHELF, (block) -> createSingleItemTableWithSilkTouch(block, Items.BOOK, ConstantValue.exactly(3.0F)));
        this.add(ModBlocks.CORRUPT_CHORUS_BOOKSHELF, (block) -> createSingleItemTableWithSilkTouch(block, Items.BOOK, ConstantValue.exactly(3.0F)));
        this.add(ModBlocks.CRYPT_BOOKSHELF, (block) -> createSingleItemTableWithSilkTouch(block, Items.BOOK, ConstantValue.exactly(3.0F)));
        this.add(ModBlocks.SKULL_PILE, (block) -> createSingleItemTableWithSilkTouch(block, Items.BONE, UniformGenerator.between(3.0F, 6.0F)));
        this.add(ModBlocks.CRYPT_URN, createSilkTouchOnlyTable(ModBlocks.CRYPT_URN));
        this.add(ModBlocks.CRYPT_CHEST, (block) -> createSingleItemTableWithSilkTouch(block, ModBlocks.CRYPT_STONE_BLOCK, UniformGenerator.between(2.0F, 4.0F)));
        this.add(ModBlocks.LOFTY_CHEST, (block) -> createSingleItemTableWithSilkTouch(block, Items.OBSIDIAN, UniformGenerator.between(2.0F, 4.0F)));
        this.add(ModBlocks.SPIDER_SAC, (block) -> createSingleItemTableWithSilkTouch(block, Items.STRING, UniformGenerator.between(2.0F, 4.0F)));
        this.add(ModBlocks.STASH_URN, createSilkTouchOnlyTable(ModBlocks.STASH_URN));
        this.add(ModBlocks.SIENNA_GRASS, this::createGrassDrops);
        this.add(ModBlocks.TALL_SIENNA_GRASS, (block) -> createDoublePlantWithSeedDrops(block, ModBlocks.SIENNA_GRASS));
        this.add(ModBlocks.SIENNA_FERN, this::createGrassDrops);
        this.add(ModBlocks.LARGE_SIENNA_FERN, (block) -> createDoublePlantWithSeedDrops(block, ModBlocks.SIENNA_FERN));
        this.add(ModBlocks.WINDSWEPT_DEAD_BUSH, (block) -> createShearsDispatchTable(block, this.applyExplosionDecay(block, LootItem.lootTableItem(Items.STICK).apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 2.0F))))));
        this.add(ModBlocks.CHORUS_VINE, (block) -> createShearsOnlyDrop(ModBlocks.CHORUS_VINE));
        this.add(ModBlocks.END_GRASS_SPROUT, (block) -> createShearsOnlyDrop(ModBlocks.END_GRASS_SPROUT));
        this.add(ModBlocks.END_GRASS, (block) -> createShearsOnlyDrop(ModBlocks.END_GRASS));
        this.add(ModBlocks.TALL_END_GRASS, (block) -> createShearsOnlyDrop(ModBlocks.TALL_END_GRASS));
        this.add(ModBlocks.CHORUS_TALL_GRASS, (block) -> createShearsOnlyDrop(ModBlocks.CHORUS_TALL_GRASS));
        this.add(ModBlocks.CHORUS_FERN_SPROUT, (block) -> createShearsOnlyDrop(ModBlocks.CHORUS_FERN_SPROUT));
        this.add(ModBlocks.CHORUS_FERN, (block) -> createShearsOnlyDrop(ModBlocks.CHORUS_FERN));
        this.add(ModBlocks.LARGE_CHORUS_FERN, (block) -> createDoublePlantShearsDrop(ModBlocks.LARGE_CHORUS_FERN));
        this.addNetherVinesDropTable(ModBlocks.END_GROWTH_VINES, ModBlocks.END_GROWTH_VINES_PLANT);
        this.add(ModBlocks.VOID_BARREL, this::createVoidBarrelConditionTable);
        this.dropOther(ModBlocks.VOID_CAULDRON, Blocks.CAULDRON.asItem());
        this.dropOther(ModBlocks.END_MUD_CAULDRON, Blocks.CAULDRON.asItem());
        this.add(ModBlocks.DETRITUS_DUST, (block) -> LootTable.lootTable().withPool(LootPool.lootPool().when(LootItemEntityPropertyCondition.entityPresent(LootContext.EntityTarget.THIS)).add(AlternativesEntry.alternatives(AlternativesEntry.alternatives(LayerBlock.LAYERS.getPossibleValues(),
                (p_252097_) -> LootItem.lootTableItem(ModBlocks.DETRITUS_DUST).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(LayerBlock.LAYERS, p_252097_))).apply(SetItemCountFunction.setCount(ConstantValue.exactly((float)p_252097_.intValue())))).when(HAS_NO_SILK_TOUCH), AlternativesEntry.alternatives(LayerBlock.LAYERS.getPossibleValues(),
                (p_251216_) -> p_251216_ == 8 ? LootItem.lootTableItem(ModBlocks.DETRITUS) : LootItem.lootTableItem(ModBlocks.DETRITUS_DUST).apply(SetItemCountFunction.setCount(ConstantValue.exactly((float)p_251216_.intValue()))).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(LayerBlock.LAYERS, p_251216_))))))));
        this.add(ModBlocks.END_SOIL_DEBRIS, (block) -> 
                LootTable.lootTable().withPool(LootPool.lootPool().when(LootItemEntityPropertyCondition.entityPresent(LootContext.EntityTarget.THIS)).add(AlternativesEntry.alternatives(AlternativesEntry.alternatives(LayerBlock.LAYERS.getPossibleValues(), 
                        (p_252097_) -> LootItem.lootTableItem(ModBlocks.END_SOIL_DEBRIS).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(LayerBlock.LAYERS, p_252097_))).apply(SetItemCountFunction.setCount(ConstantValue.exactly((float) p_252097_.intValue())))).when(HAS_NO_SILK_TOUCH), AlternativesEntry.alternatives(LayerBlock.LAYERS.getPossibleValues(), 
                        (p_251216_) -> p_251216_ == 8 ? LootItem.lootTableItem(ModBlocks.END_SOIL) : LootItem.lootTableItem(ModBlocks.END_SOIL_DEBRIS).apply(SetItemCountFunction.setCount(ConstantValue.exactly((float) p_251216_.intValue()))).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(LayerBlock.LAYERS, p_251216_))))))));
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
