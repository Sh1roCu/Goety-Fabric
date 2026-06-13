package cn.sh1rocu.goety.client;

import cn.sh1rocu.goety.api.event.*;
import cn.sh1rocu.goety.api.extension.client.ICustomArmorRenderer;
import cn.sh1rocu.goety.api.extension.client.ICustomRenderer;
import com.Polarice3.Goety.client.events.ClientEvents;
import com.Polarice3.Goety.common.blocks.BrazierBlock;
import com.Polarice3.Goety.common.blocks.DarkAltarBlock;
import com.Polarice3.Goety.common.blocks.ModBlocks;
import com.Polarice3.Goety.common.blocks.fluids.EndMudFluid;
import com.Polarice3.Goety.common.blocks.fluids.ModFluids;
import com.Polarice3.Goety.common.blocks.fluids.VoidFluid;
import com.Polarice3.Goety.common.items.ModItems;
import com.Polarice3.Goety.common.network.ModNetwork;
import com.Polarice3.Goety.init.ClientInitEvents;
import com.Polarice3.Goety.init.ModShaders;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientEntityEvents;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.model.loading.v1.ModelLoadingPlugin;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry;
import net.fabricmc.fabric.api.client.render.fluid.v1.SimpleFluidRenderHandler;
import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.CoreShaderRegistrationCallback;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;

public class GoetyFabricClient implements ClientModInitializer, ModelLoadingPlugin {

    // Vanilla
    private static void setPartVisibility(HumanoidModel<?> model, EquipmentSlot slot) {
        model.setAllVisible(false);
        switch (slot) {
            case HEAD -> {
                model.head.visible = true;
                model.hat.visible = true;
            }
            case CHEST -> {
                model.body.visible = true;
                model.rightArm.visible = true;
                model.leftArm.visible = true;
            }
            case LEGS -> {
                model.body.visible = true;
                model.rightLeg.visible = true;
                model.leftLeg.visible = true;
            }
            case FEET -> {
                model.rightLeg.visible = true;
                model.leftLeg.visible = true;
            }
        }
    }

    @Override
    public void onInitializeClient() {
        ModelLoadingPlugin.register(this);

        ModNetwork.registerS2CPackets();

        ModItems.ITEMS.stream()
                .filter(item -> item instanceof ICustomRenderer)
                .forEach(item ->
                        BuiltinItemRendererRegistry.INSTANCE.register(item,
                                (stack, mode, matrices, vertexConsumers, light, overlay) ->
                                        ((ICustomRenderer) item).getCustomRenderer().renderByItem(stack, mode, matrices, vertexConsumers, light, overlay)));

        var armorRenderers = ModItems.ITEMS.stream().filter(item -> item instanceof ArmorItem && item instanceof ICustomArmorRenderer).toArray(Item[]::new);

        ArmorRenderer.register((matrices, vertexConsumers, stack, entity, slot, light, contextModel) -> {
            var custom = (ICustomArmorRenderer) stack.getItem();
            var model = custom.getHumanoidArmorModel(entity, stack, slot, contextModel);
            setPartVisibility(model, slot);
            ArmorRenderer.renderPart(matrices, vertexConsumers, light, stack, model, new ResourceLocation(custom.getArmorTexture(stack, entity, slot, "")));
        }, armorRenderers);

        addRenderLayers();

        FluidRenderHandlerRegistry.INSTANCE.register(ModFluids.VOID_FLUID_SOURCE, ModFluids.VOID_FLUID_FLOWING, new SimpleFluidRenderHandler(VoidFluid.FLUID_STILL, VoidFluid.FLUID_FLOWING, VoidFluid.OVERLAY));
        FluidRenderHandlerRegistry.INSTANCE.register(ModFluids.END_MUD_FLUID_SOURCE, ModFluids.END_MUD_FLUID_FLOWING, new SimpleFluidRenderHandler(EndMudFluid.FLUID_STILL, EndMudFluid.FLUID_FLOWING, EndMudFluid.OVERLAY));

        ClientInitEvents.init();
        CoreShaderRegistrationCallback.EVENT.register(ModShaders::registerShaders);
        ClientEntityEvents.ENTITY_LOAD.register(ClientEvents::onEntityJoinWorld);
        ViewportEvent.CAMERA.register(ClientEvents::onSetupCamera);
        RenderTickEvent.EVENT.register(ClientEvents::renderTick);
        PlayerTickEvent.START.register(ClientEvents::onPlayerTick);
        PlayerTickEvent.END.register(ClientEvents::onPlayerTick);
        InputEvent.InteractionKeyMappingTriggered.EVENT.register(ClientEvents::onInputInteract);
        LivingEntityUseItemEvent.Start.START.register(ClientEvents::onItemUse);
        LivingTickEvent.EVENT.register(ClientEvents::onEntityTick);
        RenderArmEvent.EVENT.register(ClientEvents::renderGlove);
        RenderArmEvent.EVENT.register(ClientEvents::renderArm);
        RenderHandEvent.EVENT.register(ClientEvents::renderHand);
        RenderPlayerEvent.PRE.register(ClientEvents::onPlayerRenderPre);
        WorldRenderEvents.AFTER_TRANSLUCENT.register(ClientEvents::renderWorldLast);
        ClientTickEvents.START_CLIENT_TICK.register(client -> ClientEvents.tickEvents(true));
        ClientTickEvents.END_CLIENT_TICK.register(client -> ClientEvents.tickEvents(false));
        ViewportEvent.RENDER_FOG.register(ClientEvents::fogEvents);
        ClientTickEvents.START_CLIENT_TICK.register(ClientEvents::handleKeys);
        MovementInputUpdateEvent.EVENT.register(ClientEvents::updateInputEvent);
        InputEvent.Key.EVENT.register(ClientEvents::keyInputs);
        InputEvent.InteractionKeyMappingTriggered.EVENT.register(BaseEvent.HIGHEST, ClientEvents::interactionKeyEvent);
        ClientTickEvents.START_CLIENT_TICK.register(ClientEvents::targetMonocleEvents);
        ClientPlayConnectionEvents.DISCONNECT.register(ClientEvents::logOff);
        ServerLivingEntityEvents.AFTER_DEATH.register(ClientEvents::onDying);
        ServerLifecycleEvents.END_DATA_PACK_RELOAD.register(ClientEvents::onRecipesUpdated);
    }

    @Override
    public void onInitializeModelLoader(Context plugin) {
        ClientInitEvents.registerModels(plugin);
        plugin.modifyModelAfterBake().register(ClientInitEvents::modelBake);
    }

    private static void addRenderLayers() {
        RenderType cutout = RenderType.cutout();
        RenderType cutoutMipped = RenderType.cutoutMipped();
        RenderType translucent = RenderType.translucent();

        ModBlocks.BLOCKS.stream().filter(
                b -> b instanceof DarkAltarBlock || b instanceof BrazierBlock
        ).forEach(block ->
                BlockRenderLayerMap.INSTANCE.putBlock(block, cutout)
        );

        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.ANIMATOR, cutout);
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.APPARITION_DOOR, cutout);
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.ARCA_BLOCK, cutout);
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.BLAZING_CAGE, cutout);
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.CHORUS_BLOSSOM_VINES, cutout);
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.CHORUS_BLOSSOM_VINES_PRUNED, cutout);
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.CHORUS_DOOR, cutout);
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.CHORUS_FERN_SPROUT, cutout);
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.CHORUS_SAPLING, cutout);
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.CHORUS_SPROUT, cutout);
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.CHORUS_STALK, cutout);
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.CHORUS_TALL_GRASS, cutout);
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.CHORUS_TRAPDOOR, cutout);
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.CHORUS_VINE, cutout);
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.CORRUPT_CHORUS_TRAPDOOR, cutout);
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.CRYSTAL_BALL, translucent);
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.CURSED_BARS_BLOCK, translucent);
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.CURSED_CAGE_BLOCK, cutout);
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.END_GRASS, cutout);
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.END_GRASS_SPROUT, cutout);
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.END_GROWTH_VINES, cutout);
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.END_GROWTH_VINES_PLANT, cutout);
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.FIRETHORN, cutout);
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.GLOW_LIGHT_BLOCK, translucent);
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.GOLD_TRAPDOOR, cutout);
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.HAUNTED_DOOR, cutout);
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.HAUNTED_GLASS, cutout);
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.HAUNTED_GLASS_MOB, cutout);
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.HAUNTED_SAPLING, cutout);
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.JADE_LIGHT, cutout);
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.LARGE_CHORUS_STALK, cutout);
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.LARGE_SIENNA_FERN, cutout);
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.MAGIC_THORN, cutout);
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.NIGHT_BEACON, cutout);
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.OMINOUS_PYRE, cutout);
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.PINE_DOOR, cutout);
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.PINE_LANTERN, cutout);
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.PINE_SAPLING, cutout);
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.PINE_TRAPDOOR, cutout);
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.POTTED_CHORUS_FERN, cutout);
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.POTTED_CHORUS_SAPLING, cutout);
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.POTTED_CHORUS_STALK, cutout);
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.POTTED_HAUNTED_SAPLING, cutout);
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.POTTED_PINE_SAPLING, cutout);
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.POTTED_ROTTEN_SAPLING, cutout);
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.POTTED_SIENNA_FERN, cutout);
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.POTTED_SIENNA_GRASS, cutout);
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.POTTED_WINDSWEPT_DEAD_BUSH, cutout);
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.POTTED_WINDSWEPT_SAPLING, cutout);
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.ROTTEN_DOOR, cutout);
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.ROTTEN_SAPLING, cutout);
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.ROTTEN_TRAPDOOR, cutout);
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.RUSTY_IRON_BARS_BLOCK, cutoutMipped);
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.RUSTY_IRON_GRATE, translucent);
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.SHADE_GLASS_BLOCK, cutoutMipped);
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.SHADE_GLASS_PANE, cutoutMipped);
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.SIENNA_FERN, cutout);
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.SIENNA_GRASS, cutout);
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.SIENNA_VINE, cutout);
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.SNAP_WARTS, translucent);
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.SOUL_LIGHT_BLOCK, translucent);
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.STEEP_SCONCE, cutout);
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.TALL_SIENNA_GRASS, cutout);
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.GOLD_CHANDELIER, cutout);
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.GOLD_DUNGEON_CHAIN, cutout);
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.IRON_DUNGEON_CHAIN, cutout);
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.GOLD_DUNGEON_TORCH, cutout);
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.IRON_DUNGEON_TORCH, cutout);
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.WALL_GOLD_DUNGEON_TORCH, cutout);
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.WALL_IRON_DUNGEON_TORCH, cutout);
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.SHADE_GRAVESTONE, cutout);
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.OMINOUS_IDOL, cutout);
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.END_LAMP_BLOCK, cutout);
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.PURPUR_LAMP_BLOCK, cutout);
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.HAUNTED_LAMP, cutout);
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.STEEP_LAMP, cutout);
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.WINDSWEPT_LAMP, cutout);
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.STASH_URN, cutout);
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.CHORUS_BLOSSOM_LEAVES, cutout);
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.CHORUS_LEAVES, cutout);
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.HAUNTED_MIRROR, cutout);
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.NECRO_BRAZIER, cutout);
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.ROYAL_THRONE, cutout);
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.STONE_THRONE, cutout);
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.VOID_VAULT, cutout);
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.VOID_SHRINE, cutout);
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.VOID_BARREL, translucent);
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.VOID_BLOCK, cutout);
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.VOID_FLAME, cutout);
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.VOID_MAJOR_SPREAD, cutout);
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.VOID_MINOR_SPREAD, cutout);
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.VOID_SPAWNER, cutout);
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.WINDSWEPT_DEAD_BUSH, cutout);
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.WINDSWEPT_DOOR, cutout);
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.WINDSWEPT_SAPLING, cutout);
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.WINDSWEPT_TRAPDOOR, cutout);
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.WITCH_POLE, cutout);
    }
}
