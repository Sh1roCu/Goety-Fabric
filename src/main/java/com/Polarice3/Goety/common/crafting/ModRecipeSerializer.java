package com.Polarice3.Goety.common.crafting;

import com.Polarice3.Goety.Goety;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;

import java.util.function.Supplier;

public class ModRecipeSerializer {

    public static final RecipeType<CursedInfuserRecipes> CURSED_INFUSER = registerRecipeType("cursed_infuser");

    public static final RecipeSerializer<CursedInfuserRecipes> CURSED_INFUSER_RECIPES = registerSerializer("cursed_infuser_recipes",
            () -> new CursedInfuserRecipeSerializer<>(CursedInfuserRecipes::new, 60));

    public static final RecipeType<SoulAbsorberRecipes> SOUL_ABSORBER = registerRecipeType("soul_absorber");

    public static final RecipeSerializer<SoulAbsorberRecipes> SOUL_ABSORBER_RECIPES = registerSerializer("soul_absorber_recipes",
            () -> new SoulAbsorberRecipeSerializer<>(SoulAbsorberRecipes::new, 25, 200));

    public static final RecipeType<RitualRecipe> RITUAL_TYPE = registerRecipeType("ritual");

    public static final RecipeSerializer<RitualRecipe> RITUAL = registerSerializer("ritual",
            () -> RitualRecipe.SERIALIZER);

    public static final RecipeType<BrazierRecipe> BRAZIER_TYPE = registerRecipeType("brazier");

    public static final RecipeSerializer<BrazierRecipe> BRAZIER = registerSerializer("brazier",
            () -> BrazierRecipe.SERIALIZER);

    public static final RecipeType<CauldronRecipe> CAULDRON_TYPE = registerRecipeType("cauldron");

    public static final RecipeSerializer<CauldronRecipe> CAULDRON = registerSerializer("cauldron",
            () -> CauldronRecipe.SERIALIZER);

    public static final RecipeSerializer<CauldronSusStewRecipe> CAULDRON_SUS = registerSerializer("cauldron_sus",
            () -> CauldronSusStewRecipe.SERIALIZER);

    public static final RecipeType<BrewingRecipe> BREWING_TYPE = registerRecipeType("brewing");

    public static final RecipeSerializer<BrewingRecipe> BREWING = registerSerializer("brewing",
            () -> BrewingRecipe.SERIALIZER);

    public static final RecipeType<PulverizeRecipe> PULVERIZE_TYPE = registerRecipeType("pulverize");

    public static final RecipeSerializer<PulverizeRecipe> PULVERIZE = registerSerializer("pulverize",
            () -> PulverizeRecipe.SERIALIZER);

    public static final RecipeSerializer<ModShapelessRecipe> MODDED_SHAPELESS = registerSerializer("crafting_shapeless",
            ModShapelessRecipe.Serializer::new);

    public static void init() {

    }

    private static <T extends Recipe<?>> RecipeType<T> registerRecipeType(String name) {
        return registerRecipeType(name, () -> new RecipeType<>() {
            public String toString() {
                return name;
            }
        });
    }

    private static <T extends Recipe<?>> RecipeType<T> registerRecipeType(String name, Supplier<RecipeType<T>> supplier) {
        return Registry.register(BuiltInRegistries.RECIPE_TYPE, Goety.location(name), supplier.get());
    }

    private static <T extends Recipe<?>> RecipeSerializer<T> registerSerializer(String name, Supplier<RecipeSerializer<T>> supplier) {
        return Registry.register(BuiltInRegistries.RECIPE_SERIALIZER, Goety.location(name), supplier.get());
    }
}
