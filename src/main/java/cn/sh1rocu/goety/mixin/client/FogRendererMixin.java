package cn.sh1rocu.goety.mixin.client;

import cn.sh1rocu.goety.util.forge.ClientHooks;
import com.Polarice3.Goety.common.blocks.fluids.ModFluids;
import com.Polarice3.Goety.utils.CuriosFinder;
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.FogType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FogRenderer.class)
public class FogRendererMixin {

    @Shadow
    private static float fogRed;

    @Shadow
    private static float fogGreen;

    @Shadow
    private static float fogBlue;

    @Inject(method = "setupFog", at = @At("TAIL"))
    private static void goety$setupFog(
            Camera camera, FogRenderer.FogMode fogMode, float renderDistance, boolean bl, float partialTick, CallbackInfo ci,
            @Local FogType fogType,
            @Local FogRenderer.FogData fogData) {
        ClientHooks.onFogRender(fogMode, fogType, camera, partialTick, renderDistance, fogData.start, fogData.end, fogData.shape);
    }

    @Inject(method = "setupFog", at = @At("TAIL"))
    private static void goety$modifyFluidFog(
            Camera camera, FogRenderer.FogMode fogMode, float renderDistance, boolean bl, float partialTick, CallbackInfo ci,
            @Local FogRenderer.FogData fogData) {
        FluidState state = camera.getEntity().level().getFluidState(camera.getBlockPosition());
        if (camera.getPosition().y < (double) ((float) camera.getBlockPosition().getY() + state.getHeight(camera.getEntity().level(), camera.getBlockPosition()))) {
            float nearDistance;
            float farDistance;
            Entity entity = camera.getEntity();

            if (state.is(ModFluids.VOID_FLUID_SOURCE) || state.is(ModFluids.VOID_FLUID_FLOWING)) {
                if (entity.isSpectator()) {
                    nearDistance = -8.0F;
                    farDistance = renderDistance * 0.5F;
                } else if (entity instanceof LivingEntity livingEntity && CuriosFinder.hasVoidRobe(livingEntity)) {
                    nearDistance = 0.0F;
                    farDistance = 3.0F;
                } else {
                    nearDistance = 0.25F;
                    farDistance = 1.0F;
                }
                RenderSystem.setShaderFogStart(nearDistance);
                RenderSystem.setShaderFogEnd(farDistance);
                RenderSystem.setShaderFogShape(fogData.shape);
            } else if (state.is(ModFluids.END_MUD_FLUID_SOURCE) || state.is(ModFluids.END_MUD_FLUID_FLOWING)) {
                if (entity.isSpectator()) {
                    nearDistance = -8.0F;
                    farDistance = renderDistance * 0.5F;
                } else if (entity instanceof LivingEntity livingEntity && CuriosFinder.hasVoidRobe(livingEntity)) {
                    nearDistance = 0.0F;
                    farDistance = 3.0F;
                } else {
                    nearDistance = 0.25F;
                    farDistance = 1.0F;
                }
                RenderSystem.setShaderFogStart(nearDistance);
                RenderSystem.setShaderFogEnd(farDistance);
                RenderSystem.setShaderFogShape(fogData.shape);
            }
        }
    }

    @Inject(method = "setupColor", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/systems/RenderSystem;clearColor(FFFF)V"))
    private static void goety$modifyFluidFogColor(Camera camera, float f, ClientLevel clientLevel, int i, float g, CallbackInfo ci) {
        FluidState state = clientLevel.getFluidState(camera.getBlockPosition());
        if (state.is(ModFluids.VOID_FLUID_SOURCE) || state.is(ModFluids.VOID_FLUID_FLOWING)) {
            fogRed = 16.0F / 255.0F;
            fogGreen = 0.0F;
            fogBlue = 24.0F / 255.0F;
        } else if (state.is(ModFluids.END_MUD_FLUID_SOURCE) || state.is(ModFluids.END_MUD_FLUID_FLOWING)) {
            fogRed = 140.0F / 255.0F;
            fogGreen = 140.0F / 255.0F;
            fogBlue = 140.0F / 255.0F;
        }
    }
}
