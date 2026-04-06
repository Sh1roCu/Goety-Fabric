package cn.sh1rocu.goety.mixin.client;

import cn.sh1rocu.goety.api.event.CustomizeGuiOverlayEvent;
import cn.sh1rocu.goety.util.forge.ClientHooks;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.BossHealthOverlay;
import net.minecraft.client.gui.components.LerpingBossEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.world.BossEvent;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

// From Kilt(https://github.com/KiltMC/Kilt/blob/version/1.20.1/src/main/java/xyz/bluspring/kilt/forgeinjects/client/gui/components/BossHealthOverlayInject.java)
@Mixin(BossHealthOverlay.class)
public class BossHealthOverlayMixin {
    @Shadow
    @Final
    private Minecraft minecraft;

    @WrapWithCondition(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/components/BossHealthOverlay;drawBar(Lnet/minecraft/client/gui/GuiGraphics;IILnet/minecraft/world/BossEvent;)V"))
    private boolean goety$customizeBossEventProgress(BossHealthOverlay instance, GuiGraphics guiGraphics, int x, int y, BossEvent bossEvent, @Local(ordinal = 2) int k, @Local(ordinal = 1) int j, @Share("event") LocalRef<CustomizeGuiOverlayEvent.BossEventProgress> eventRef) {
        var event = ClientHooks.onCustomizeBossEventProgress(guiGraphics, this.minecraft.getWindow(), (LerpingBossEvent) bossEvent, k, j, 10 + this.minecraft.font.lineHeight);
        eventRef.set(event);
        return !event.isCanceled();
    }

    @WrapWithCondition(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;drawString(Lnet/minecraft/client/gui/Font;Lnet/minecraft/network/chat/Component;III)I"))
    private boolean goety$cancelStringIfCancelled(GuiGraphics instance, Font font, Component text, int x, int y, int color, @Share("event") LocalRef<CustomizeGuiOverlayEvent.BossEventProgress> eventRef) {
        return !eventRef.get().isCanceled();
    }

    @Expression("10 + 9")
    @ModifyExpressionValue(method = "render", at = @At("MIXINEXTRAS:EXPRESSION"))
    private int goety$getIncrement(int original, @Share("event") LocalRef<CustomizeGuiOverlayEvent.BossEventProgress> eventRef) {
        var increment = eventRef.get().getIncrement();

        if (original != 19)
            return original;

        return increment;
    }
}