package com.Polarice3.Goety.api.items;

import com.Polarice3.Goety.Goety;
import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

@Environment(EnvType.CLIENT)
public class IPersistDecorator {
    public static final ResourceLocation BROKEN_OVERLAY = Goety.location("textures/item/broken_overlay.png");

    public static final IPersistDecorator INSTANCE = new IPersistDecorator();

    public boolean render(GuiGraphics guiGraphics, Font font, ItemStack stack, int xOffset, int yOffset) {
        if (stack.isEmpty()) {
            return false;
        }
        if (!(stack.getItem() instanceof IPersist persist)) {
            return false;
        } else if (persist.isNotBroken(stack)) {
            return false;
        }

        RenderSystem.disableDepthTest();

        guiGraphics.pose().pushPose();
        guiGraphics.pose().translate(0, 0, 390);

        guiGraphics.blit(BROKEN_OVERLAY, xOffset, yOffset, 16, 16, 0, 0, 16, 16, 16, 16);

        guiGraphics.pose().popPose();
        return true;
    }
}
