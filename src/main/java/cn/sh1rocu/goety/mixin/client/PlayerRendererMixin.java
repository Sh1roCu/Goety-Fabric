package cn.sh1rocu.goety.mixin.client;

import cn.sh1rocu.goety.api.event.RenderArmEvent;
import cn.sh1rocu.goety.api.event.RenderPlayerEvent;
import cn.sh1rocu.goety.api.extension.client.ICustomArmPose;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerRenderer.class)
public class PlayerRendererMixin {
    @Inject(method = "render(Lnet/minecraft/client/player/AbstractClientPlayer;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/LivingEntityRenderer;render(Lnet/minecraft/world/entity/LivingEntity;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V"), cancellable = true)
    private void goety$onRenderPlayerPre(AbstractClientPlayer player, float f, float partialTick, PoseStack poseStack, MultiBufferSource buffer, int packedLight, CallbackInfo ci) {
        RenderPlayerEvent.Pre event = new RenderPlayerEvent.Pre(player, (PlayerRenderer) (Object) this, partialTick, poseStack, buffer, packedLight);
        RenderPlayerEvent.PRE.invoker().post(event);
        if (event.isCanceled())
            ci.cancel();
    }

    @Inject(method = "render(Lnet/minecraft/client/player/AbstractClientPlayer;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V", at = @At("RETURN"))
    private void goety$onRenderPlayerPost(AbstractClientPlayer player, float f, float partialTick, PoseStack poseStack, MultiBufferSource buffer, int packedLight, CallbackInfo ci) {
        RenderPlayerEvent.Post event = new RenderPlayerEvent.Post(player, (PlayerRenderer) (Object) this, partialTick, poseStack, buffer, packedLight);
        RenderPlayerEvent.POST.invoker().post(event);
    }

    @Inject(method = "getArmPose", at = @At("HEAD"), cancellable = true)
    private static void goety$getArmPose(AbstractClientPlayer player, InteractionHand hand, CallbackInfoReturnable<HumanoidModel.ArmPose> ci) {
        ItemStack itemStack = player.getItemInHand(hand);
        if (itemStack.getItem() instanceof ICustomArmPose custom) {
            ci.setReturnValue(custom.getArmPose(player, hand, itemStack));
        }
    }

    @Inject(method = "renderLeftHand", at = @At("HEAD"), cancellable = true)
    private void goety$onRenderLeftArm(PoseStack poseStack, MultiBufferSource buffer, int packedLight, AbstractClientPlayer player, CallbackInfo ci) {
        var event = new RenderArmEvent(poseStack, buffer, packedLight, player, HumanoidArm.LEFT);
        if (event.isCanceled()) ci.cancel();
    }

    @Inject(method = "renderRightHand", at = @At("HEAD"), cancellable = true)
    private void goety$onRenderRightArm(PoseStack poseStack, MultiBufferSource buffer,
                                        int packedLight, AbstractClientPlayer player, CallbackInfo ci) {
        var event = new RenderArmEvent(poseStack, buffer, packedLight, player, HumanoidArm.RIGHT);
        if (event.isCanceled()) ci.cancel();
    }
}