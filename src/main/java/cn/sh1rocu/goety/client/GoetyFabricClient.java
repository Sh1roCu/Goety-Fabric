package cn.sh1rocu.goety.client;

import cn.sh1rocu.goety.api.event.*;
import cn.sh1rocu.goety.api.extension.client.ICustomRenderer;
import com.Polarice3.Goety.client.events.ClientEvents;
import com.Polarice3.Goety.common.blocks.fluids.EndMudFluid;
import com.Polarice3.Goety.common.blocks.fluids.ModFluids;
import com.Polarice3.Goety.common.blocks.fluids.VoidFluid;
import com.Polarice3.Goety.common.items.ModItems;
import com.Polarice3.Goety.common.network.ModNetwork;
import com.Polarice3.Goety.init.ClientInitEvents;
import com.Polarice3.Goety.init.ModShaders;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientEntityEvents;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.model.loading.v1.ModelLoadingPlugin;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry;
import net.fabricmc.fabric.api.client.render.fluid.v1.SimpleFluidRenderHandler;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.CoreShaderRegistrationCallback;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;

public class GoetyFabricClient implements ClientModInitializer, ModelLoadingPlugin {

    @Override
    public void onInitializeClient() {
        ModelLoadingPlugin.register(this);

        ModNetwork.registerS2CPackets();

        ModItems.ITEMS.forEach(item -> {
            if (item instanceof ICustomRenderer customRenderer) {
                BuiltinItemRendererRegistry.INSTANCE.register(item,
                        (stack, mode, matrices, vertexConsumers, light, overlay) ->
                                customRenderer.getCustomRenderer().renderByItem(stack, mode, matrices, vertexConsumers, light, overlay));
            }
        });

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
    }

    @Override
    public void onInitializeModelLoader(Context plugin) {
        ClientInitEvents.registerModels(plugin);
        plugin.modifyModelAfterBake().register(ClientInitEvents::modelBake);
    }
}
