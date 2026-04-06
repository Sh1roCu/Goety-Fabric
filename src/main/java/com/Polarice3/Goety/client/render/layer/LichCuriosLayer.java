package com.Polarice3.Goety.client.render.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.emi.trinkets.api.TrinketsApi;
import dev.emi.trinkets.api.client.TrinketRendererRegistry;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nonnull;

public class LichCuriosLayer<T extends LivingEntity, M extends EntityModel<T>> extends
        RenderLayer<T, M> {
    private final RenderLayerParent<T, M> renderLayerParent;

    public LichCuriosLayer(RenderLayerParent<T, M> renderer) {
        super(renderer);
        this.renderLayerParent = renderer;
    }

    @Override
    public void render(@Nonnull PoseStack matrixStack, @Nonnull MultiBufferSource renderTypeBuffer,
                       int light, @Nonnull T livingEntity, float limbSwing, float limbSwingAmount,
                       float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        matrixStack.pushPose();
        TrinketsApi.getTrinketComponent(livingEntity)
                .ifPresent(handler -> handler.getAllEquipped().forEach(equipped -> {
                    ItemStack stack = equipped.getB();
                    TrinketRendererRegistry.getRenderer(stack.getItem()).ifPresent(
                            renderer -> renderer
                                    .render(stack, equipped.getA(), renderLayerParent.getModel(), matrixStack,
                                            renderTypeBuffer, light, livingEntity, limbSwing, limbSwingAmount, partialTicks,
                                            ageInTicks, netHeadYaw, headPitch));
                }));
        matrixStack.popPose();
    }
}
