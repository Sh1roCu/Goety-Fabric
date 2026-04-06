package cn.sh1rocu.goety.mixin.common;

import cn.sh1rocu.goety.util.forge.EventHooks;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// From Kilt
@Mixin(EnderDragon.class)
public abstract class EnderDragonMixin extends Mob {
    @Nullable
    @Unique
    private Player goety$unlimitedLastHurtByPlayer = null;

    protected EnderDragonMixin(EntityType<? extends Mob> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(method = "aiStep", at = @At("HEAD"))
    private void goety$storeLastHurtByPlayer(CallbackInfo ci) {
        if (this.lastHurtByPlayer != null)
            this.goety$unlimitedLastHurtByPlayer = this.lastHurtByPlayer;

        if (this.goety$unlimitedLastHurtByPlayer != null && this.goety$unlimitedLastHurtByPlayer.isRemoved())
            this.goety$unlimitedLastHurtByPlayer = null;
    }

    @ModifyArg(method = "tickDeath", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/ExperienceOrb;award(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/phys/Vec3;I)V"))
    private int goety$useForgeModifiedExperienceDrop(int experience) {
        return EventHooks.getExperienceDrop(this, this.goety$unlimitedLastHurtByPlayer, experience);
    }
}