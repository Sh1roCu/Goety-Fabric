package cn.sh1rocu.goety.mixin.common;

import cn.sh1rocu.goety.util.forge.EventHooks;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.server.commands.TeleportCommand;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.RelativeMovement;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Set;

@Mixin(TeleportCommand.class)
public abstract class TeleportCommandMixin {
    @Inject(method = "performTeleport", at = @At("HEAD"), cancellable = true)
    private static void goety$onEntityTeleportCommand(CommandSourceStack source, Entity target, ServerLevel world, double x, double y, double z, Set<RelativeMovement> movementFlags, float yaw, float pitch, TeleportCommand.LookAt facingLocation, CallbackInfo ci) {
        var event = EventHooks.onEntityTeleportCommand(target, x, y, z);
        if (event.isCanceled())
            ci.cancel();
    }
}