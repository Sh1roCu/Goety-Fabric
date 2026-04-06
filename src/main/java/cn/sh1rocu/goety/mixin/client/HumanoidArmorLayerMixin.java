package cn.sh1rocu.goety.mixin.client;

import cn.sh1rocu.goety.api.extension.client.IArmorModelHook;
import cn.sh1rocu.goety.util.forge.ClientHooks;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import javax.annotation.Nullable;
import java.util.Locale;
import java.util.Map;

// Based on https://github.com/BluSpring/Citadel-Fabric/blob/1.19.2/src/main/java/com/github/alexthe666/citadel/mixin/fabric/HumanoidArmorLayerMixin.java
@Mixin(HumanoidArmorLayer.class)
public abstract class HumanoidArmorLayerMixin<T extends LivingEntity, M extends HumanoidModel<T>, A extends HumanoidModel<T>> {

    @Shadow
    protected abstract boolean usesInnerModel(EquipmentSlot equipmentSlot);

    @Shadow
    @Final
    private static Map<String, ResourceLocation> ARMOR_LOCATION_CACHE;

    @Unique
    protected Model goety$getArmorModelHook(T entity, ItemStack itemStack, EquipmentSlot slot, A model) {
        return ClientHooks.getArmorModel(entity, itemStack, slot, model);
    }

    @Unique
    public ResourceLocation goety$getArmorResource(Entity entity, ItemStack stack, EquipmentSlot slot, @Nullable String type) {
        var item = (ArmorItem) stack.getItem();
        var texture = item.getMaterial().getName();
        var domain = "minecraft";

        var idx = texture.indexOf(':');

        if (idx != -1) {
            domain = texture.substring(0, idx);
            texture = texture.substring(idx + 1);
        }

        var path = String.format(Locale.ROOT, "%s:textures/models/armor/%s_layer_%d%s.png",
                domain, texture,
                (this.usesInnerModel(slot) ? 2 : 1),
                type == null ? "" : String.format(Locale.ROOT, "_%s", type)
        );

        path = ClientHooks.getArmorTexture(entity, stack, path, slot, type);
        var loc = ARMOR_LOCATION_CACHE.get(path);

        if (loc == null) {
            loc = new ResourceLocation(path);
            ARMOR_LOCATION_CACHE.put(path, loc);
        }

        return loc;
    }

    @Unique
    private void goety$renderModel(PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, ArmorItem pArmorItem, net.minecraft.client.model.Model pModel, boolean pWithGlint, float pRed, float pGreen, float pBlue, ResourceLocation armorResource) {
        VertexConsumer vertexconsumer = pBuffer.getBuffer(RenderType.armorCutoutNoCull(armorResource));
        pModel.renderToBuffer(pPoseStack, vertexconsumer, pPackedLight, OverlayTexture.NO_OVERLAY, pRed, pGreen, pBlue, 1.0F);
    }

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/layers/HumanoidArmorLayer;setPartVisibility(Lnet/minecraft/client/model/HumanoidModel;Lnet/minecraft/world/entity/EquipmentSlot;)V"), method = "renderArmorPiece", locals = LocalCapture.CAPTURE_FAILHARD)
    private void goety$getModelHook(PoseStack poseStack, MultiBufferSource multiBufferSource, T livingEntity, EquipmentSlot equipmentSlot, int i, A humanoidModel, CallbackInfo ci, ItemStack itemStack, ArmorItem armorItem, @Share("goety$model") LocalRef<Model> modelLocalRef) {
        if (this instanceof IArmorModelHook hook) {
            modelLocalRef.set(hook.getArmorModelHook(livingEntity, itemStack, equipmentSlot, humanoidModel));
        } else {
            var model = goety$getArmorModelHook(livingEntity, itemStack, equipmentSlot, humanoidModel);
            modelLocalRef.set(model == humanoidModel ? null : model);
        }
    }

    @WrapOperation(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/layers/HumanoidArmorLayer;renderModel(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/world/item/ArmorItem;Lnet/minecraft/client/model/HumanoidModel;ZFFFLjava/lang/String;)V"), method = "renderArmorPiece")
    private void goety$useForgeRenderModel(HumanoidArmorLayer instance, PoseStack poseStack, MultiBufferSource multiBufferSource, int light, ArmorItem armorItem, A humanoidModel, boolean withGlint, float r, float g, float b, String type, Operation<Void> original, @Local(ordinal = 0) ItemStack itemStack, @Share("goety$model") LocalRef<Model> modelLocalRef, @Local(argsOnly = true) EquipmentSlot slot, @Local(argsOnly = true) T livingEntity) {
        if (modelLocalRef.get() != null) {
            this.goety$renderModel(poseStack, multiBufferSource, light, armorItem, modelLocalRef.get(), withGlint, r, g, b, this.goety$getArmorResource(livingEntity, itemStack, slot, type));
        } else {
            original.call(instance, poseStack, multiBufferSource, light, armorItem, humanoidModel, withGlint, r, g, b, type);
        }
    }
}