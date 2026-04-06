package cn.sh1rocu.goety.util.brewing;

import cn.sh1rocu.goety.mixin.accessor.PotionBrewingAccessor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.ArrayList;
import java.util.Optional;

// From https://github.com/TeamGalena/Nirvana/blob/main/1.20.x/fabric/src/main/java/galena/nirvana/fabric/services/FabricBrewingRegistry.java
public class FabricCustomBrewingRegistry {

    public record BrewingRecipe(Ingredient input, Ingredient ingredient, ItemStack output) {
    }

    private static final ArrayList<BrewingRecipe> CUSTOM_RECIPES = new ArrayList<>();

    public static boolean isCustomInput(ItemStack stack) {
        return CUSTOM_RECIPES.stream().anyMatch(it -> it.input.test(stack));
    }

    public static boolean isCustomIngredient(ItemStack stack) {
        return CUSTOM_RECIPES.stream().anyMatch(it -> it.ingredient.test(stack));
    }

    public static Optional<BrewingRecipe> getCustomRecipe(ItemStack input, ItemStack ingredient) {
        return CUSTOM_RECIPES.stream().filter(it -> it.ingredient.test(ingredient) && it.input.test(input)).findFirst();
    }

    public static boolean hasCustomRecipe(ItemStack input, ItemStack ingredient) {
        return getCustomRecipe(input, ingredient).isPresent();
    }

    public static void addRecipe(Ingredient input, Ingredient ingredient, ItemStack output) {
        CUSTOM_RECIPES.add(new BrewingRecipe(input, ingredient, output));
        PotionBrewingAccessor.goety$getAllowedContainers().add(input);
    }

}