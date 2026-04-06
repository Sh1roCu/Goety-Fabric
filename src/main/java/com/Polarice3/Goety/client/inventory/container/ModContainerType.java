package com.Polarice3.Goety.client.inventory.container;

import com.Polarice3.Goety.Goety;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;

import java.util.function.Supplier;

public class ModContainerType {

    public static final ExtendedScreenHandlerType<SoulItemContainer> WAND = register("wand",
            () -> new ExtendedScreenHandlerType<>(SoulItemContainer::createContainerClientSide));

    public static final ExtendedScreenHandlerType<FocusBagContainer> FOCUS_BAG = register("focus_bag",
            () -> new ExtendedScreenHandlerType<>(FocusBagContainer::createContainerClientSide));

    public static final ExtendedScreenHandlerType<FocusPackContainer> FOCUS_PACK = register("focus_pack",
            () -> new ExtendedScreenHandlerType<>(FocusPackContainer::createContainerClientSide));

    public static final ExtendedScreenHandlerType<BrewBagContainer> BREW_BAG = register("brew_bag",
            () -> new ExtendedScreenHandlerType<>(BrewBagContainer::createContainerClientSide));

    public static final ExtendedScreenHandlerType<DarkAnvilMenu> DARK_ANVIL = register("dark_anvil",
            () -> new ExtendedScreenHandlerType<>(DarkAnvilMenu::new));

    public static final ExtendedScreenHandlerType<CraftingFocusMenu> CRAFTING_FOCUS = register("crafting_focus",
            () -> new ExtendedScreenHandlerType<>(CraftingFocusMenu::new));

    public static void init() {

    }

    private static <T extends AbstractContainerMenu, S extends MenuType<T>> S register(String name, Supplier<S> supplier) {
        return Registry.register(BuiltInRegistries.MENU, Goety.location(name), supplier.get());
    }
}
