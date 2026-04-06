package com.Polarice3.Goety.data;

import com.Polarice3.Goety.Goety;
import com.Polarice3.Goety.common.blocks.ModBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;

import java.util.function.Consumer;

public class ModCraftingProvider extends FabricRecipeProvider {

    public ModCraftingProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void buildRecipes(Consumer<FinishedRecipe> consumer) {
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.CRYPT_PILLAR_BLOCK, 2)
                .pattern("#")
                .pattern("#")
                .define('#', ModBlocks.CRYPT_STONE_POLISHED_BLOCK)
                .unlockedBy("has_item", has(ModBlocks.CRYPT_STONE_POLISHED_BLOCK))
                .save(consumer, loc("crypt/crypt_pillar"));
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.CRYPT_STONE_CHISELED_BLOCK)
                .pattern("#")
                .pattern("#")
                .define('#', ModBlocks.CRYPT_STONE_POLISHED_SLAB_BLOCK)
                .unlockedBy("has_item", has(ModBlocks.CRYPT_STONE_POLISHED_SLAB_BLOCK))
                .save(consumer, loc("crypt/crypt_stone_chiseled"));
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.CRYPT_PLINTH_BLOCK)
                .pattern("#")
                .pattern("#")
                .define('#', ModBlocks.CRYPT_STONE_SLAB_BLOCK)
                .unlockedBy("has_item", has(ModBlocks.CRYPT_STONE_SLAB_BLOCK))
                .save(consumer, loc("crypt/crypt_plinth"));

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.SILTSTONE_PILLAR_BLOCK)
                .pattern("#")
                .pattern("#")
                .define('#', ModBlocks.SILTSTONE_TILE_SLAB)
                .unlockedBy("has_item", has(ModBlocks.SILTSTONE_TILE_SLAB))
                .save(consumer, loc("silt/siltstone_pillar"));
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.CHISELED_SILTSTONE_BLOCK)
                .pattern("#")
                .pattern("#")
                .define('#', ModBlocks.SILTSTONE_PAVEMENT_SLAB)
                .unlockedBy("has_item", has(ModBlocks.SILTSTONE_PAVEMENT_SLAB))
                .save(consumer, loc("silt/chiseled_siltstone"));
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.CHISELED_SILTSTONE_BRICKS_BLOCK)
                .pattern("#")
                .pattern("#")
                .define('#', ModBlocks.SILTSTONE_BRICK_SLAB)
                .unlockedBy("has_item", has(ModBlocks.SILTSTONE_BRICK_SLAB))
                .save(consumer, loc("silt/chiseled_siltstone_bricks"));

        smelting(consumer, "silt/smooth_siltstone", ModBlocks.COBBLED_SILTSTONE_BLOCK, ModBlocks.SMOOTH_SILTSTONE_BLOCK);
        smelting(consumer, "crypt/crypt_stone_polished", ModBlocks.CRYPT_STONE_BLOCK, ModBlocks.CRYPT_STONE_POLISHED_BLOCK);

        twoByTwo(consumer, "crypt/crypt_bricks", ModBlocks.CRYPT_STONE_POLISHED_BLOCK, ModBlocks.CRYPT_BRICKS_BLOCK);
        twoByTwo(consumer, "crypt/crypt_tiles", ModBlocks.CRYPT_STONE_BLOCK, ModBlocks.CRYPT_TILES_BLOCK);

        twoByTwo(consumer, "silt/smooth_siltstone", ModBlocks.SILTSTONE_BLOCK, ModBlocks.SMOOTH_SILTSTONE_BLOCK);
        twoByTwo(consumer, "silt/siltstone_bricks", ModBlocks.SMOOTH_SILTSTONE_BLOCK, ModBlocks.SILTSTONE_BRICKS_BLOCK);
        twoByTwo(consumer, "silt/siltstone_tiles", ModBlocks.SILTSTONE_BRICKS_BLOCK, ModBlocks.SILTSTONE_TILES_BLOCK);
        twoByTwo(consumer, "silt/siltstone_pavement", ModBlocks.SILTSTONE_TILES_BLOCK, ModBlocks.SILTSTONE_PAVEMENT_BLOCK);

        slabBlock(consumer, "crypt/crypt_stone", ModBlocks.CRYPT_STONE_BLOCK, ModBlocks.CRYPT_STONE_SLAB_BLOCK);
        slabBlock(consumer, "crypt/crypt_stone_polished", ModBlocks.CRYPT_STONE_POLISHED_BLOCK, ModBlocks.CRYPT_STONE_POLISHED_SLAB_BLOCK);
        slabBlock(consumer, "crypt/crypt_bricks", ModBlocks.CRYPT_BRICKS_BLOCK, ModBlocks.CRYPT_BRICKS_SLAB_BLOCK);
        slabBlock(consumer, "crypt/crypt_tiles", ModBlocks.CRYPT_TILES_BLOCK, ModBlocks.CRYPT_TILES_SLAB_BLOCK);

        slabBlock(consumer, "silt/siltstone", ModBlocks.SILTSTONE_BLOCK, ModBlocks.SILTSTONE_SLAB);
        slabBlock(consumer, "silt/smooth_siltstone", ModBlocks.SMOOTH_SILTSTONE_BLOCK, ModBlocks.SMOOTH_SILTSTONE_SLAB);
        slabBlock(consumer, "silt/cobbled_siltstone", ModBlocks.COBBLED_SILTSTONE_BLOCK, ModBlocks.COBBLED_SILTSTONE_SLAB);
        slabBlock(consumer, "silt/snowy_cobbled_siltstone", ModBlocks.SNOWY_COBBLED_SILTSTONE_BLOCK, ModBlocks.SNOWY_COBBLED_SILTSTONE_SLAB);
        slabBlock(consumer, "silt/siltstone_bricks", ModBlocks.SILTSTONE_BRICKS_BLOCK, ModBlocks.SILTSTONE_BRICK_SLAB);
        slabBlock(consumer, "silt/snowy_siltstone_bricks_slight", ModBlocks.SNOWY_SILTSTONE_BRICKS_SLIGHT_BLOCK, ModBlocks.SNOWY_SILTSTONE_BRICK_SLIGHT_SLAB);
        slabBlock(consumer, "silt/snowy_siltstone_bricks", ModBlocks.SNOWY_SILTSTONE_BRICKS_BLOCK, ModBlocks.SNOWY_SILTSTONE_BRICK_SLAB);
        slabBlock(consumer, "silt/siltstone_tiles", ModBlocks.SILTSTONE_TILES_BLOCK, ModBlocks.SILTSTONE_TILE_SLAB);
        slabBlock(consumer, "silt/siltstone_pavement", ModBlocks.SILTSTONE_PAVEMENT_BLOCK, ModBlocks.SILTSTONE_PAVEMENT_SLAB);

        stairsBlock(consumer, "crypt/crypt_stone", ModBlocks.CRYPT_STONE_BLOCK, ModBlocks.CRYPT_STONE_STAIRS_BLOCK);
        stairsBlock(consumer, "crypt/crypt_stone_polished", ModBlocks.CRYPT_STONE_POLISHED_BLOCK, ModBlocks.CRYPT_STONE_POLISHED_STAIRS_BLOCK);
        stairsBlock(consumer, "crypt/crypt_bricks", ModBlocks.CRYPT_BRICKS_BLOCK, ModBlocks.CRYPT_BRICKS_STAIRS_BLOCK);
        stairsBlock(consumer, "crypt/crypt_tiles", ModBlocks.CRYPT_TILES_BLOCK, ModBlocks.CRYPT_TILES_STAIRS_BLOCK);

        stairsBlock(consumer, "silt/siltstone", ModBlocks.SILTSTONE_BLOCK, ModBlocks.SILTSTONE_STAIRS);
        stairsBlock(consumer, "silt/smooth_siltstone", ModBlocks.SMOOTH_SILTSTONE_BLOCK, ModBlocks.SMOOTH_SILTSTONE_STAIRS);
        stairsBlock(consumer, "silt/cobbled_siltstone", ModBlocks.COBBLED_SILTSTONE_BLOCK, ModBlocks.COBBLED_SILTSTONE_STAIRS);
        stairsBlock(consumer, "silt/snowy_cobbled_siltstone", ModBlocks.SNOWY_COBBLED_SILTSTONE_BLOCK, ModBlocks.SNOWY_COBBLED_SILTSTONE_STAIRS);
        stairsBlock(consumer, "silt/siltstone_bricks", ModBlocks.SILTSTONE_BRICKS_BLOCK, ModBlocks.SILTSTONE_BRICK_STAIRS);
        stairsBlock(consumer, "silt/snowy_siltstone_bricks_slight", ModBlocks.SNOWY_SILTSTONE_BRICKS_SLIGHT_BLOCK, ModBlocks.SNOWY_SILTSTONE_BRICK_SLIGHT_STAIRS);
        stairsBlock(consumer, "silt/snowy_siltstone_bricks", ModBlocks.SNOWY_SILTSTONE_BRICKS_BLOCK, ModBlocks.SNOWY_SILTSTONE_BRICK_STAIRS);
        stairsBlock(consumer, "silt/siltstone_tiles", ModBlocks.SILTSTONE_TILES_BLOCK, ModBlocks.SILTSTONE_TILE_STAIRS);
        stairsBlock(consumer, "silt/siltstone_pavement", ModBlocks.SILTSTONE_PAVEMENT_BLOCK, ModBlocks.SILTSTONE_PAVEMENT_STAIRS);

        wallBlock(consumer, "crypt/crypt_stone_polished", ModBlocks.CRYPT_STONE_POLISHED_BLOCK, ModBlocks.CRYPT_STONE_POLISHED_WALL_BLOCK);
        wallBlock(consumer, "crypt/crypt_bricks", ModBlocks.CRYPT_BRICKS_BLOCK, ModBlocks.CRYPT_BRICKS_WALL_BLOCK);
        wallBlock(consumer, "crypt/crypt_tiles", ModBlocks.CRYPT_TILES_BLOCK, ModBlocks.CRYPT_TILES_WALL_BLOCK);

        wallBlock(consumer, "silt/siltstone", ModBlocks.SILTSTONE_BLOCK, ModBlocks.SILTSTONE_WALL_BLOCK);
        wallBlock(consumer, "silt/smooth_siltstone", ModBlocks.SMOOTH_SILTSTONE_BLOCK, ModBlocks.SMOOTH_SILTSTONE_WALL_BLOCK);
        wallBlock(consumer, "silt/cobbled_siltstone", ModBlocks.COBBLED_SILTSTONE_BLOCK, ModBlocks.COBBLED_SILTSTONE_WALL_BLOCK);
        wallBlock(consumer, "silt/snowy_cobbled_siltstone", ModBlocks.SNOWY_COBBLED_SILTSTONE_BLOCK, ModBlocks.SNOWY_COBBLED_SILTSTONE_WALL_BLOCK);
        wallBlock(consumer, "silt/siltstone_bricks", ModBlocks.SILTSTONE_BRICKS_BLOCK, ModBlocks.SILTSTONE_BRICK_WALL_BLOCK);
        wallBlock(consumer, "silt/snowy_siltstone_bricks_slight", ModBlocks.SNOWY_SILTSTONE_BRICKS_SLIGHT_BLOCK, ModBlocks.SNOWY_SILTSTONE_BRICK_SLIGHT_WALL_BLOCK);
        wallBlock(consumer, "silt/snowy_siltstone_bricks", ModBlocks.SNOWY_SILTSTONE_BRICKS_BLOCK, ModBlocks.SNOWY_SILTSTONE_BRICK_WALL_BLOCK);
        wallBlock(consumer, "silt/siltstone_tiles", ModBlocks.SILTSTONE_TILES_BLOCK, ModBlocks.SILTSTONE_TILE_WALL_BLOCK);
        wallBlock(consumer, "silt/siltstone_pavement", ModBlocks.SILTSTONE_PAVEMENT_BLOCK, ModBlocks.SILTSTONE_PAVEMENT_WALL_BLOCK);

        stoneCutting(consumer, "crypt", ModBlocks.CRYPT_STONE_BLOCK, ModBlocks.CRYPT_STONE_SLAB_BLOCK, 2);
        stoneCutting(consumer, "crypt", ModBlocks.CRYPT_STONE_BLOCK, ModBlocks.CRYPT_TILES_SLAB_BLOCK, 2);
        stoneCutting(consumer, "crypt", ModBlocks.CRYPT_STONE_POLISHED_BLOCK, ModBlocks.CRYPT_STONE_POLISHED_SLAB_BLOCK, 2);
        stoneCutting(consumer, "crypt", ModBlocks.CRYPT_STONE_POLISHED_BLOCK, ModBlocks.CRYPT_BRICKS_SLAB_BLOCK, 2);
        stoneCutting(consumer, "crypt", ModBlocks.CRYPT_BRICKS_BLOCK, ModBlocks.CRYPT_BRICKS_SLAB_BLOCK, 2);
        stoneCutting(consumer, "crypt", ModBlocks.CRYPT_TILES_BLOCK, ModBlocks.CRYPT_TILES_SLAB_BLOCK, 2);

        stoneCutting(consumer, "crypt", ModBlocks.CRYPT_STONE_BLOCK, ModBlocks.CRYPT_STONE_STAIRS_BLOCK);
        stoneCutting(consumer, "crypt", ModBlocks.CRYPT_STONE_BLOCK, ModBlocks.CRYPT_TILES_STAIRS_BLOCK);
        stoneCutting(consumer, "crypt", ModBlocks.CRYPT_STONE_POLISHED_BLOCK, ModBlocks.CRYPT_STONE_POLISHED_STAIRS_BLOCK);
        stoneCutting(consumer, "crypt", ModBlocks.CRYPT_STONE_POLISHED_BLOCK, ModBlocks.CRYPT_BRICKS_STAIRS_BLOCK);
        stoneCutting(consumer, "crypt", ModBlocks.CRYPT_BRICKS_BLOCK, ModBlocks.CRYPT_BRICKS_STAIRS_BLOCK);
        stoneCutting(consumer, "crypt", ModBlocks.CRYPT_TILES_BLOCK, ModBlocks.CRYPT_TILES_STAIRS_BLOCK);

        stoneCutting(consumer, "crypt", ModBlocks.CRYPT_STONE_BLOCK, ModBlocks.CRYPT_TILES_WALL_BLOCK);
        stoneCutting(consumer, "crypt", ModBlocks.CRYPT_STONE_POLISHED_BLOCK, ModBlocks.CRYPT_STONE_POLISHED_WALL_BLOCK);
        stoneCutting(consumer, "crypt", ModBlocks.CRYPT_STONE_POLISHED_BLOCK, ModBlocks.CRYPT_BRICKS_WALL_BLOCK);
        stoneCutting(consumer, "crypt", ModBlocks.CRYPT_BRICKS_BLOCK, ModBlocks.CRYPT_BRICKS_WALL_BLOCK);
        stoneCutting(consumer, "crypt", ModBlocks.CRYPT_TILES_BLOCK, ModBlocks.CRYPT_TILES_WALL_BLOCK);

        stoneCutting(consumer, "crypt", ModBlocks.CRYPT_STONE_BLOCK, ModBlocks.CRYPT_TILES_BLOCK);
        stoneCutting(consumer, "crypt", ModBlocks.CRYPT_STONE_BLOCK, ModBlocks.CRYPT_PLINTH_BLOCK);

        stoneCutting(consumer, "crypt", ModBlocks.CRYPT_STONE_POLISHED_BLOCK, ModBlocks.CRYPT_BRICKS_BLOCK);
        stoneCutting(consumer, "crypt", ModBlocks.CRYPT_STONE_POLISHED_BLOCK, ModBlocks.CRYPT_PILLAR_BLOCK);
        stoneCutting(consumer, "crypt", ModBlocks.CRYPT_STONE_POLISHED_BLOCK, ModBlocks.CRYPT_STONE_CHISELED_BLOCK);

        stoneCutting(consumer, "silt", ModBlocks.SILTSTONE_BLOCK, ModBlocks.SILTSTONE_SLAB, 2);
        stoneCutting(consumer, "silt", ModBlocks.SILTSTONE_BLOCK, ModBlocks.SMOOTH_SILTSTONE_SLAB, 2);
        stoneCutting(consumer, "silt", ModBlocks.SILTSTONE_BLOCK, ModBlocks.SILTSTONE_BRICK_SLAB, 2);
        stoneCutting(consumer, "silt", ModBlocks.SILTSTONE_BLOCK, ModBlocks.SILTSTONE_TILE_SLAB, 2);
        stoneCutting(consumer, "silt", ModBlocks.SILTSTONE_BLOCK, ModBlocks.SILTSTONE_PAVEMENT_SLAB, 2);
        stoneCutting(consumer, "silt", ModBlocks.SMOOTH_SILTSTONE_BLOCK, ModBlocks.SMOOTH_SILTSTONE_SLAB, 2);
        stoneCutting(consumer, "silt", ModBlocks.SMOOTH_SILTSTONE_BLOCK, ModBlocks.SILTSTONE_BRICK_SLAB, 2);
        stoneCutting(consumer, "silt", ModBlocks.SMOOTH_SILTSTONE_BLOCK, ModBlocks.SILTSTONE_TILE_SLAB, 2);
        stoneCutting(consumer, "silt", ModBlocks.SMOOTH_SILTSTONE_BLOCK, ModBlocks.SILTSTONE_PAVEMENT_SLAB, 2);
        stoneCutting(consumer, "silt", ModBlocks.SILTSTONE_BRICKS_BLOCK, ModBlocks.SILTSTONE_BRICK_SLAB, 2);
        stoneCutting(consumer, "silt", ModBlocks.SILTSTONE_BRICKS_BLOCK, ModBlocks.SILTSTONE_TILE_SLAB, 2);
        stoneCutting(consumer, "silt", ModBlocks.SILTSTONE_BRICKS_BLOCK, ModBlocks.SILTSTONE_PAVEMENT_SLAB, 2);
        stoneCutting(consumer, "silt", ModBlocks.SILTSTONE_TILES_BLOCK, ModBlocks.SILTSTONE_TILE_SLAB, 2);
        stoneCutting(consumer, "silt", ModBlocks.SILTSTONE_TILES_BLOCK, ModBlocks.SILTSTONE_PAVEMENT_SLAB, 2);
        stoneCutting(consumer, "silt", ModBlocks.SILTSTONE_PAVEMENT_BLOCK, ModBlocks.SILTSTONE_PAVEMENT_SLAB, 2);

        stoneCutting(consumer, "silt", ModBlocks.COBBLED_SILTSTONE_SLAB, ModBlocks.COBBLED_SILTSTONE_SLAB, 2);

        stoneCutting(consumer, "silt", ModBlocks.SNOWY_COBBLED_SILTSTONE_BLOCK, ModBlocks.SNOWY_COBBLED_SILTSTONE_SLAB, 2);
        stoneCutting(consumer, "silt", ModBlocks.SNOWY_SILTSTONE_BRICKS_SLIGHT_BLOCK, ModBlocks.SNOWY_SILTSTONE_BRICK_SLIGHT_SLAB, 2);
        stoneCutting(consumer, "silt", ModBlocks.SNOWY_SILTSTONE_BRICKS_BLOCK, ModBlocks.SNOWY_SILTSTONE_BRICK_SLAB, 2);

        stoneCutting(consumer, "silt", ModBlocks.SILTSTONE_BLOCK, ModBlocks.SILTSTONE_STAIRS);
        stoneCutting(consumer, "silt", ModBlocks.SILTSTONE_BLOCK, ModBlocks.SMOOTH_SILTSTONE_STAIRS);
        stoneCutting(consumer, "silt", ModBlocks.SILTSTONE_BLOCK, ModBlocks.SILTSTONE_BRICK_STAIRS);
        stoneCutting(consumer, "silt", ModBlocks.SILTSTONE_BLOCK, ModBlocks.SILTSTONE_TILE_STAIRS);
        stoneCutting(consumer, "silt", ModBlocks.SILTSTONE_BLOCK, ModBlocks.SILTSTONE_PAVEMENT_STAIRS);
        stoneCutting(consumer, "silt", ModBlocks.SMOOTH_SILTSTONE_BLOCK, ModBlocks.SMOOTH_SILTSTONE_STAIRS);
        stoneCutting(consumer, "silt", ModBlocks.SMOOTH_SILTSTONE_BLOCK, ModBlocks.SILTSTONE_BRICK_STAIRS);
        stoneCutting(consumer, "silt", ModBlocks.SMOOTH_SILTSTONE_BLOCK, ModBlocks.SILTSTONE_TILE_STAIRS);
        stoneCutting(consumer, "silt", ModBlocks.SMOOTH_SILTSTONE_BLOCK, ModBlocks.SILTSTONE_PAVEMENT_STAIRS);
        stoneCutting(consumer, "silt", ModBlocks.SILTSTONE_BRICKS_BLOCK, ModBlocks.SILTSTONE_BRICK_STAIRS);
        stoneCutting(consumer, "silt", ModBlocks.SILTSTONE_BRICKS_BLOCK, ModBlocks.SILTSTONE_TILE_STAIRS);
        stoneCutting(consumer, "silt", ModBlocks.SILTSTONE_BRICKS_BLOCK, ModBlocks.SILTSTONE_PAVEMENT_STAIRS);
        stoneCutting(consumer, "silt", ModBlocks.SILTSTONE_TILES_BLOCK, ModBlocks.SILTSTONE_TILE_STAIRS);
        stoneCutting(consumer, "silt", ModBlocks.SILTSTONE_TILES_BLOCK, ModBlocks.SILTSTONE_PAVEMENT_STAIRS);
        stoneCutting(consumer, "silt", ModBlocks.SILTSTONE_PAVEMENT_BLOCK, ModBlocks.SILTSTONE_PAVEMENT_STAIRS);

        stoneCutting(consumer, "silt", ModBlocks.COBBLED_SILTSTONE_BLOCK, ModBlocks.COBBLED_SILTSTONE_STAIRS);

        stoneCutting(consumer, "silt", ModBlocks.SNOWY_COBBLED_SILTSTONE_BLOCK, ModBlocks.SNOWY_COBBLED_SILTSTONE_STAIRS);
        stoneCutting(consumer, "silt", ModBlocks.SNOWY_SILTSTONE_BRICKS_SLIGHT_BLOCK, ModBlocks.SNOWY_SILTSTONE_BRICK_SLIGHT_STAIRS);
        stoneCutting(consumer, "silt", ModBlocks.SNOWY_SILTSTONE_BRICKS_BLOCK, ModBlocks.SNOWY_SILTSTONE_BRICK_STAIRS);

        stoneCutting(consumer, "silt", ModBlocks.SILTSTONE_BLOCK, ModBlocks.SILTSTONE_WALL_BLOCK);
        stoneCutting(consumer, "silt", ModBlocks.SILTSTONE_BLOCK, ModBlocks.SMOOTH_SILTSTONE_WALL_BLOCK);
        stoneCutting(consumer, "silt", ModBlocks.SILTSTONE_BLOCK, ModBlocks.SILTSTONE_BRICK_WALL_BLOCK);
        stoneCutting(consumer, "silt", ModBlocks.SILTSTONE_BLOCK, ModBlocks.SILTSTONE_TILE_WALL_BLOCK);
        stoneCutting(consumer, "silt", ModBlocks.SILTSTONE_BLOCK, ModBlocks.SILTSTONE_PAVEMENT_WALL_BLOCK);
        stoneCutting(consumer, "silt", ModBlocks.SMOOTH_SILTSTONE_BLOCK, ModBlocks.SMOOTH_SILTSTONE_WALL_BLOCK);
        stoneCutting(consumer, "silt", ModBlocks.SMOOTH_SILTSTONE_BLOCK, ModBlocks.SILTSTONE_BRICK_WALL_BLOCK);
        stoneCutting(consumer, "silt", ModBlocks.SMOOTH_SILTSTONE_BLOCK, ModBlocks.SILTSTONE_TILE_WALL_BLOCK);
        stoneCutting(consumer, "silt", ModBlocks.SMOOTH_SILTSTONE_BLOCK, ModBlocks.SILTSTONE_PAVEMENT_WALL_BLOCK);
        stoneCutting(consumer, "silt", ModBlocks.SILTSTONE_BRICKS_BLOCK, ModBlocks.SILTSTONE_BRICK_WALL_BLOCK);
        stoneCutting(consumer, "silt", ModBlocks.SILTSTONE_BRICKS_BLOCK, ModBlocks.SILTSTONE_TILE_WALL_BLOCK);
        stoneCutting(consumer, "silt", ModBlocks.SILTSTONE_BRICKS_BLOCK, ModBlocks.SILTSTONE_PAVEMENT_WALL_BLOCK);
        stoneCutting(consumer, "silt", ModBlocks.SILTSTONE_TILES_BLOCK, ModBlocks.SILTSTONE_TILE_WALL_BLOCK);
        stoneCutting(consumer, "silt", ModBlocks.SILTSTONE_TILES_BLOCK, ModBlocks.SILTSTONE_PAVEMENT_WALL_BLOCK);
        stoneCutting(consumer, "silt", ModBlocks.SILTSTONE_PAVEMENT_BLOCK, ModBlocks.SILTSTONE_PAVEMENT_WALL_BLOCK);

        stoneCutting(consumer, "silt", ModBlocks.COBBLED_SILTSTONE_BLOCK, ModBlocks.COBBLED_SILTSTONE_WALL_BLOCK);

        stoneCutting(consumer, "silt", ModBlocks.SNOWY_COBBLED_SILTSTONE_BLOCK, ModBlocks.SNOWY_COBBLED_SILTSTONE_WALL_BLOCK);
        stoneCutting(consumer, "silt", ModBlocks.SNOWY_SILTSTONE_BRICKS_SLIGHT_BLOCK, ModBlocks.SNOWY_SILTSTONE_BRICK_SLIGHT_WALL_BLOCK);
        stoneCutting(consumer, "silt", ModBlocks.SNOWY_SILTSTONE_BRICKS_BLOCK, ModBlocks.SNOWY_SILTSTONE_BRICK_WALL_BLOCK);

        stoneCutting(consumer, "silt", ModBlocks.SILTSTONE_BLOCK, ModBlocks.CHISELED_SILTSTONE_BLOCK);
        stoneCutting(consumer, "silt", ModBlocks.SMOOTH_SILTSTONE_BLOCK, ModBlocks.CHISELED_SILTSTONE_BLOCK);
        stoneCutting(consumer, "silt", ModBlocks.SILTSTONE_BRICKS_BLOCK, ModBlocks.CHISELED_SILTSTONE_BLOCK);
        stoneCutting(consumer, "silt", ModBlocks.SILTSTONE_TILES_BLOCK, ModBlocks.CHISELED_SILTSTONE_BLOCK);

        stoneCutting(consumer, "silt", ModBlocks.SILTSTONE_BLOCK, ModBlocks.CHISELED_SILTSTONE_BRICKS_BLOCK);
        stoneCutting(consumer, "silt", ModBlocks.SMOOTH_SILTSTONE_BLOCK, ModBlocks.CHISELED_SILTSTONE_BRICKS_BLOCK);
        stoneCutting(consumer, "silt", ModBlocks.SILTSTONE_BRICKS_BLOCK, ModBlocks.CHISELED_SILTSTONE_BRICKS_BLOCK);
        stoneCutting(consumer, "silt", ModBlocks.SILTSTONE_TILES_BLOCK, ModBlocks.CHISELED_SILTSTONE_BRICKS_BLOCK);
        stoneCutting(consumer, "silt", ModBlocks.SILTSTONE_PAVEMENT_BLOCK, ModBlocks.CHISELED_SILTSTONE_BRICKS_BLOCK);

        stoneCutting(consumer, "silt", ModBlocks.SILTSTONE_BLOCK, ModBlocks.SILTSTONE_PILLAR_BLOCK);
        stoneCutting(consumer, "silt", ModBlocks.SMOOTH_SILTSTONE_BLOCK, ModBlocks.SILTSTONE_PILLAR_BLOCK);
        stoneCutting(consumer, "silt", ModBlocks.SILTSTONE_BRICKS_BLOCK, ModBlocks.SILTSTONE_PILLAR_BLOCK);
        stoneCutting(consumer, "silt", ModBlocks.SILTSTONE_TILES_BLOCK, ModBlocks.SILTSTONE_PILLAR_BLOCK);
    }

    protected final void twoByTwo(Consumer<FinishedRecipe> consumer, String name, Block input, Block output) {
        twoByTwo(consumer, name, input, output, 4);
    }

    protected final void twoByTwo(Consumer<FinishedRecipe> consumer, String name, Block input, Block output, int amount) {
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, output, amount)
                .pattern("##")
                .pattern("##")
                .define('#', input)
                .unlockedBy("has_item", has(input))
                .save(consumer, loc(name));
    }

    protected final void stairsBlock(Consumer<FinishedRecipe> consumer, String name, Block input, Block output) {
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, output, 4)
                .pattern("#  ")
                .pattern("## ")
                .pattern("###")
                .define('#', input)
                .unlockedBy("has_item", has(input))
                .save(consumer, loc(name + "_stairs"));
    }

    protected final void slabBlock(Consumer<FinishedRecipe> consumer, String name, Block input, Block output) {
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, output, 6)
                .pattern("###")
                .define('#', input)
                .unlockedBy("has_item", has(input))
                .save(consumer, loc(name + "_slab"));
    }

    protected final void wallBlock(Consumer<FinishedRecipe> consumer, String name, Block input, Block output) {
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, output, 6)
                .pattern("###")
                .pattern("###")
                .define('#', input)
                .unlockedBy("has_item", has(input))
                .save(consumer, loc(name + "_wall"));
    }

    protected final ResourceLocation loc(String name) {
        return Goety.location(name);
    }

    public final void stoneCutting(Consumer<FinishedRecipe> consumer, String folder, Block input, Block output) {
        stoneCutting(consumer, folder, input, output, 1);
    }

    public final void stoneCutting(Consumer<FinishedRecipe> consumer, String folder, Block input, Block output, int amount) {
        SingleItemRecipeBuilder
                .stonecutting(Ingredient.of(input), RecipeCategory.BUILDING_BLOCKS, output, amount)
                .unlockedBy("has_item", has(input))
                .save(consumer, loc(folder + "/" + getConversionRecipeName(input, output) + "_stonecutting"));
    }

    public final void smelting(Consumer<FinishedRecipe> consumer, String name, ItemLike input, ItemLike output) {
        SimpleCookingRecipeBuilder
                .smelting(Ingredient.of(input), RecipeCategory.BUILDING_BLOCKS, output, 0.35F, 200)
                .unlockedBy("has_item", has(input))
                .save(consumer, loc(name + "_smelting"));
    }
}
