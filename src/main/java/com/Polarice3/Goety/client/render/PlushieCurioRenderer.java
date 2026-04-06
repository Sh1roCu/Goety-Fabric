package com.Polarice3.Goety.client.render;

import com.Polarice3.Goety.client.render.block.PlushieBlockEntityRenderer;
import com.Polarice3.Goety.common.blocks.PlushieBlock;
import com.mojang.blaze3d.vertex.PoseStack;
import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.client.TrinketRenderer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HeadedModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;

public class PlushieCurioRenderer implements TrinketRenderer {

    @Override
    public void render(ItemStack stack, SlotReference slotReference, EntityModel<? extends LivingEntity> entityModel, PoseStack matrixStack, MultiBufferSource renderTypeBuffer, int light, LivingEntity livingEntity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if (entityModel instanceof HeadedModel headModel) {
            Item item = stack.getItem();
            if (!stack.isEmpty() && item instanceof BlockItem blockItem) {
                Block block = blockItem.getBlock();
                if (block instanceof PlushieBlock) {
                    matrixStack.pushPose();
                    headModel.getHead().translateAndRotate(matrixStack);
                    float size = 1.875F;
                    matrixStack.scale(size, -size, -size);
                    matrixStack.translate(-0.5D, 0.255D, -0.5D);
                    PlushieBlockEntityRenderer.renderItemPlushie(stack, block.defaultBlockState(), 180.0F, matrixStack, renderTypeBuffer, light);
                    matrixStack.popPose();

                }
            }
        }
    }

}
