package cn.sh1rocu.goety.mixin.common;

import cn.sh1rocu.goety.util.forge.UsernameCache;
import net.minecraft.network.Connection;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.PlayerList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerList.class)
public abstract class PlayerListMixin {
    @Inject(method = "placeNewPlayer", at = @At("TAIL"))
    private void goety$setPlayerUsername(Connection connection, ServerPlayer serverPlayer, CallbackInfo ci) {
        UsernameCache.setUsername(serverPlayer.getUUID(), serverPlayer.getGameProfile().getName());
    }
}