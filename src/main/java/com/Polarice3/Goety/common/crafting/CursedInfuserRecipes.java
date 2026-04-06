package com.Polarice3.Goety.common.crafting;

import com.Polarice3.Goety.common.blocks.ModBlocks;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;

public class CursedInfuserRecipes extends ModCookingRecipe {
    public boolean grim;

    public CursedInfuserRecipes(ResourceLocation pId, String pGroup, Ingredient pIngredient, ItemStack pResult, float pExperience, int pCookingTime, boolean grim) {
        super(ModRecipeSerializer.CURSED_INFUSER, pId, pGroup, pIngredient, pResult, pExperience, pCookingTime);
        this.grim = grim;
    }

    @Override
    public ItemStack getToastSymbol() {
        return new ItemStack(ModBlocks.CURSED_INFUSER);
    }

    public boolean isGrim() {
        return this.grim;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipeSerializer.CURSED_INFUSER_RECIPES;
    }
}
