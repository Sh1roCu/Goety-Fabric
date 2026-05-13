package com.Polarice3.Goety.common.blocks.fluids;

import cn.sh1rocu.goety.util.forge.FluidInteractionRegistry;
import com.Polarice3.Goety.Goety;
import com.Polarice3.Goety.common.blocks.ModBlocks;
import com.Polarice3.Goety.config.MainConfig;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariantAttributeHandler;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariantAttributes;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.function.Supplier;

public class ModFluids {

    public static final FlowingFluid VOID_FLUID_SOURCE = register("void", VoidFluid.Source::new);
    public static final FlowingFluid VOID_FLUID_FLOWING = register("void_flowing", VoidFluid.Flowing::new);

    public static final FlowingFluid END_MUD_FLUID_SOURCE = register("end_mud", EndMudFluid.Source::new);
    public static final FlowingFluid END_MUD_FLUID_FLOWING = register("end_mud_flowing", EndMudFluid.Flowing::new);

    public static void interactionInit() {
        // Water -> Void = Void Block (Source Void) / End Soil (Flowing Void)
        FluidInteractionRegistry.addInteraction(VOID_FLUID_SOURCE, new FluidInteractionRegistry.InteractionInformation(
                Fluids.WATER,
                fluidState -> fluidState.isSource() ? ModBlocks.VOID_BLOCK.defaultBlockState() : ModBlocks.END_SOIL.defaultBlockState()
        ));

        // Void -> Water = End Rock (Source Water) / End Soil (Flowing Water)
        FluidInteractionRegistry.addInteraction(Fluids.WATER, new FluidInteractionRegistry.InteractionInformation(
                VOID_FLUID_SOURCE,
                fluidState -> fluidState.isSource() ? ModBlocks.END_ROCK.defaultBlockState() : ModBlocks.END_SOIL.defaultBlockState()
        ));

        // Mud -> Void = Void Block (Source Void) / End Soil (Flowing Void)
        FluidInteractionRegistry.addInteraction(VOID_FLUID_SOURCE, new FluidInteractionRegistry.InteractionInformation(
                END_MUD_FLUID_SOURCE,
                fluidState -> fluidState.isSource() ? ModBlocks.VOID_BLOCK.defaultBlockState() : ModBlocks.END_SOIL.defaultBlockState()
        ));

        // Void -> Mud = End Rock (Source Water) / End Soil (Flowing Water)
        FluidInteractionRegistry.addInteraction(END_MUD_FLUID_SOURCE, new FluidInteractionRegistry.InteractionInformation(
                VOID_FLUID_SOURCE,
                fluidState -> fluidState.isSource() ? ModBlocks.END_ROCK.defaultBlockState() : ModBlocks.END_SOIL.defaultBlockState()
        ));

        // Lava -> Void = End Basalt (Source Void) / End Basalt (Flowing Void)
        FluidInteractionRegistry.addInteraction(VOID_FLUID_SOURCE, new FluidInteractionRegistry.InteractionInformation(
                Fluids.LAVA,
                fluidState -> {
                    if (MainConfig.CataclysmVoidStone.get()) {
                        ResourceLocation location = new ResourceLocation("cataclysm", "void_stone");
                        Block block = BuiltInRegistries.BLOCK.get(location);
                        if (BuiltInRegistries.BLOCK.getKey(block).equals(location)) {
                            return fluidState.isSource() ? block.defaultBlockState() : ModBlocks.END_BASALT.defaultBlockState();
                        }
                    }
                    return ModBlocks.END_BASALT.defaultBlockState();
                }
        ));

        // Void -> Lava = End Basalt (Source Lava) / End Basalt (Flowing Lava)
        FluidInteractionRegistry.addInteraction(Fluids.LAVA, new FluidInteractionRegistry.InteractionInformation(
                VOID_FLUID_SOURCE,
                fluidState -> ModBlocks.END_BASALT.defaultBlockState()
        ));

        // Mud -> Water = End Mud
        FluidInteractionRegistry.addInteraction(END_MUD_FLUID_SOURCE, new FluidInteractionRegistry.InteractionInformation(
                Fluids.WATER,
                fluidState -> ModBlocks.END_MUD.defaultBlockState()
        ));

        // Water -> Mud = End Mud
        FluidInteractionRegistry.addInteraction(Fluids.WATER, new FluidInteractionRegistry.InteractionInformation(
                END_MUD_FLUID_SOURCE,
                fluidState -> ModBlocks.END_MUD.defaultBlockState()
        ));

        // Mud -> Lava = Obsidian (Source Lava) / End Stone (Flowing Lava)
        FluidInteractionRegistry.addInteraction(END_MUD_FLUID_SOURCE, new FluidInteractionRegistry.InteractionInformation(
                Fluids.LAVA,
                fluidState -> fluidState.isSource() ? Blocks.OBSIDIAN.defaultBlockState() : Blocks.END_STONE.defaultBlockState()
        ));

        // Lava -> Mud = End Stone Slate (Source Mud) / End Stone (Flowing Mud)
        FluidInteractionRegistry.addInteraction(Fluids.LAVA, new FluidInteractionRegistry.InteractionInformation(
                END_MUD_FLUID_SOURCE,
                fluidState -> fluidState.isSource() ? ModBlocks.END_STONE_SLATE_BLOCK.defaultBlockState() : Blocks.END_STONE.defaultBlockState()
        ));

        FluidInteractionRegistry.addInteraction(Fluids.LAVA, new FluidInteractionRegistry.InteractionInformation(
                (level, currentPos, relativePos, currentState) -> level.getBlockState(currentPos.below()).is(ModBlocks.END_SOIL) && level.getBlockState(relativePos).is(Blocks.BLUE_ICE),
                Blocks.OBSIDIAN.defaultBlockState()
        ));

        if (MainConfig.OminousStoneGenerator.get()) {
            // Lava + Lapis Lazuli (Below) + Water = Ominous Stone
            FluidInteractionRegistry.addInteraction(Fluids.LAVA, new FluidInteractionRegistry.InteractionInformation(
                    (level, currentPos, relativePos, currentState) -> level.getBlockState(currentPos.below()).is(Blocks.LAPIS_BLOCK) && level.getFluidState(relativePos).is(Fluids.WATER),
                    fluidState -> fluidState.isSource() ? ModBlocks.OMINOUS_STONE_BLOCK.defaultBlockState() : ModBlocks.COBBLED_OMINOUS_STONE_BLOCK.defaultBlockState()
            ));
        }
    }

    @SuppressWarnings("UnstableApiUsage")
    public static void init() {
        FluidVariantAttributes.register(VOID_FLUID_SOURCE, new FluidVariantAttributeHandler() {
            @Override
            public Component getName(FluidVariant fluidVariant) {
                return Component.translatable("block.goety.void_fluid");
            }

            @Override
            public Optional<SoundEvent> getFillSound(FluidVariant variant) {
                return Optional.of(SoundEvents.BUCKET_FILL_LAVA);
            }

            @Override
            public Optional<SoundEvent> getEmptySound(FluidVariant variant) {
                return Optional.of(SoundEvents.BUCKET_EMPTY_LAVA);
            }

            @Override
            public int getTemperature(FluidVariant variant) {
                return 1300;
            }

            @Override
            public int getViscosity(FluidVariant variant, @Nullable Level world) {
                return 6000;
            }
        });

        FluidVariantAttributes.register(END_MUD_FLUID_SOURCE, new FluidVariantAttributeHandler() {
            @Override
            public Component getName(FluidVariant fluidVariant) {
                return Component.translatable("block.goety.end_mud_fluid");
            }

            @Override
            public Optional<SoundEvent> getFillSound(FluidVariant variant) {
                return Optional.of(SoundEvents.BUCKET_FILL);
            }

            @Override
            public Optional<SoundEvent> getEmptySound(FluidVariant variant) {
                return Optional.of(SoundEvents.BUCKET_EMPTY);
            }

            @Override
            public int getViscosity(FluidVariant variant, @Nullable Level world) {
                return 1400;
            }
        });
    }

    private static <T extends Fluid> T register(String name, Supplier<T> supplier) {
        return Registry.register(BuiltInRegistries.FLUID, Goety.location(name), supplier.get());
    }
}
