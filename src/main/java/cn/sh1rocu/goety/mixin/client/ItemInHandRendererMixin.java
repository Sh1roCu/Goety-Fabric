package cn.sh1rocu.goety.mixin.client;

import cn.sh1rocu.goety.api.event.RenderHandEvent;
import cn.sh1rocu.goety.api.extension.client.IForgeHandTransform;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemInHandRenderer.class)
public abstract class ItemInHandRendererMixin {
    @Shadow
    @Final
    private Minecraft minecraft;

    @Inject(method = "renderArmWithItem", at = @At("HEAD"), cancellable = true)
    private void goety$renderHand(AbstractClientPlayer player, float tickDelta, float pitch, InteractionHand hand, float swingProgress, ItemStack stack, float equipProgress, PoseStack matrices, MultiBufferSource vertexConsumers, int light, CallbackInfo ci) {
        RenderHandEvent event = new RenderHandEvent(player, hand, stack, matrices, vertexConsumers, tickDelta, pitch, swingProgress, equipProgress, light);
        RenderHandEvent.EVENT.invoker().post(event);
        if (event.isCanceled()) {
            ci.cancel();
        }
    }

    @WrapOperation(method = "renderArmWithItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/AbstractClientPlayer;isUsingItem()Z", ordinal = 1))
    private boolean goety$cancelIfForgeHandTransformApplied(AbstractClientPlayer instance, Operation<Boolean> original, @Local(argsOnly = true) ItemStack stack, @Local(argsOnly = true) PoseStack poseStack, @Local HumanoidArm arm, @Local(argsOnly = true, ordinal = 0) float partialTicks, @Local(argsOnly = true, ordinal = 2) float swingProgress, @Local(argsOnly = true, ordinal = 3) float equippedProgress) {
        return !(stack.getItem() instanceof IForgeHandTransform custom
                && custom.applyForgeHandTransform(poseStack, minecraft.player, arm, stack, partialTicks, equippedProgress, swingProgress)) && original.call(instance);
    }

}