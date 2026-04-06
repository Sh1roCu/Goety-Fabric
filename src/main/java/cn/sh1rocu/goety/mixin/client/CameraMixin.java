package cn.sh1rocu.goety.mixin.client;

import cn.sh1rocu.goety.api.event.ViewportEvent;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.Camera;
import net.minecraft.util.Mth;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(Camera.class)
public class CameraMixin {
    @Unique
    private float goety$roll;

    @WrapOperation(method = "setup", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Camera;setRotation(FF)V", ordinal = 0))
    private void goety$computeCameraAngles(Camera instance, float yRot, float xRot, Operation<Void> original, @Local(argsOnly = true) float partialTick) {
        var event = new ViewportEvent.ComputeCameraAngles(instance, partialTick, yRot, xRot, 0);
        ViewportEvent.CAMERA.invoker().post(event);

        this.goety$roll = event.getRoll();
        original.call(instance, event.getYaw(), event.getPitch());
    }

    @ModifyArg(method = "setRotation", at = @At(remap = false, value = "INVOKE", target = "Lorg/joml/Quaternionf;rotationYXZ(FFF)Lorg/joml/Quaternionf;"), index = 2)
    private float goety$setRollValue(float value) {
        return value + (-goety$roll * Mth.DEG_TO_RAD);
    }
}
