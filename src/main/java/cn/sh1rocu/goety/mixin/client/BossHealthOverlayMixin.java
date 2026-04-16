package cn.sh1rocu.goety.mixin.client;

import com.Polarice3.Goety.client.events.BossBarEvent;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.BossHealthOverlay;
import net.minecraft.world.BossEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.concurrent.atomic.AtomicBoolean;

@Mixin(BossHealthOverlay.class)
public class BossHealthOverlayMixin {
    @Inject(at = @At("HEAD"), method = "drawBar(Lnet/minecraft/client/gui/GuiGraphics;IILnet/minecraft/world/BossEvent;)V", cancellable = true)
    private void drawBar(GuiGraphics guiGraphics, int x, int y, BossEvent bossEvent, CallbackInfo ci) {
        var cancelled = new AtomicBoolean(false);
        BossBarEvent.renderBossBar(guiGraphics, bossEvent, x, y, cancelled);
        if (cancelled.get()) {
            ci.cancel();
        }
    }
}