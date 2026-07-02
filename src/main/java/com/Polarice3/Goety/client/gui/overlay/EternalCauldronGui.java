package com.Polarice3.Goety.client.gui.overlay;

import com.Polarice3.Goety.common.items.ModItems;
import com.Polarice3.Goety.common.items.curios.EternalCauldronItem;
import com.Polarice3.Goety.config.MainConfig;
import com.Polarice3.Goety.utils.CuriosFinder;
import com.Polarice3.Goety.utils.SEHelper;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class EternalCauldronGui {
    public static final HudRenderCallback OVERLAY = EternalCauldronGui::drawOverlay;
    private static final Minecraft minecraft = Minecraft.getInstance();

    public static Font getFont() {
        return minecraft.font;
    }

    public static void drawOverlay(GuiGraphics ms, float partialTicks) {
        int screenWidth = ms.guiWidth();
        int screenHeight = ms.guiHeight();
        if (!MainConfig.ECGuiShow.get()) {
            return;
        }

        if (minecraft.player != null) {
            Player player = minecraft.player;
            ItemStack itemStack = CuriosFinder.findCurio(player, ModItems.ETERNAL_CAULDRON);

            if (!itemStack.isEmpty()) {
                if (!EternalCauldronItem.getBottle(itemStack).isEmpty()) {
                    ItemStack show = itemStack;
                    if (SEHelper.isOnCooldown(player, itemStack)) {
                        show = new ItemStack(ModItems.ETERNAL_CAULDRON);
                    }
                    ms.pose().pushPose();
                    int i = screenWidth / 2;
                    int x = i - 91 - 29 - 29;
                    int y = screenHeight - 16 - 3;
                    x += MainConfig.ECGuiHorizontal.get();
                    y += MainConfig.ECGuiVertical.get();
                    ms.renderFakeItem(show, x, y);
                    ms.renderItemDecorations(minecraft.font, show, x, y);
                    ms.pose().popPose();
                }
            }
        }
    }
}
