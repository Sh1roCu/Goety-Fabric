package cn.sh1rocu.goety.mixin.client;

import com.Polarice3.Goety.client.events.ClientEvents;
import com.Polarice3.Goety.client.gui.screen.inventory.BrewRadialMenuScreen;
import com.Polarice3.Goety.client.gui.screen.inventory.FocusRadialMenuScreen;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalIntRef;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.concurrent.atomic.AtomicBoolean;

@Mixin(Gui.class)
public abstract class GuiMixin {

    @Inject(method = "renderHearts", at = @At("HEAD"), cancellable = true)
    private void goety$renderHeartsPre(GuiGraphics guiGraphics, Player player, int i, int j, int k, int l, float f, int m, int n, int o, boolean bl, CallbackInfo ci) {
        AtomicBoolean cancelled = new AtomicBoolean(false);
        ClientEvents.renderHealthBarPre(guiGraphics, cancelled);
        if (cancelled.get()) {
            ci.cancel();
        }
    }

    @WrapOperation(method = "renderPlayerHealth", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/Gui;getVehicleMaxHearts(Lnet/minecraft/world/entity/LivingEntity;)I"))
    private int goety$renderFoodPre(Gui instance, LivingEntity livingEntity, Operation<Integer> original, @Share("vehicle_max_hearts") LocalIntRef ref) {
        Integer result = original.call(instance, livingEntity);
        ref.set(result);
        AtomicBoolean cancelled = new AtomicBoolean(false);
        ClientEvents.renderLichHUD(cancelled);
        if (cancelled.get()) {
            return -1;
        }
        return result;
    }

    @ModifyArg(method = "renderPlayerHealth", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/Gui;getVisibleVehicleHeartRows(I)I"))
    private int goety$recoverVehicleMaxHeart(int i, @Share("vehicle_max_hearts") LocalIntRef ref) {
        return i == -1 ? ref.get() : i;
    }

    @Inject(method = "renderCrosshair", at = @At("HEAD"), cancellable = true)
    private void goety$renderCrosshairPre(GuiGraphics guiGraphics, CallbackInfo ci) {
        AtomicBoolean cancelled = new AtomicBoolean(false);
        BrewRadialMenuScreen.overlayEvent(cancelled);
        FocusRadialMenuScreen.overlayEvent(cancelled);
        if (cancelled.get()) {
            ci.cancel();
        }
    }

    @Inject(method = "renderCrosshair", at = @At("TAIL"))
    private void goety$renderCrosshairPost(GuiGraphics guiGraphics, CallbackInfo ci) {
        ClientEvents.renderArcaAmount(guiGraphics);
    }
}
