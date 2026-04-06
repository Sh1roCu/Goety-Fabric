package cn.sh1rocu.goety.util.forge;

import cn.sh1rocu.goety.api.event.*;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ThrownEnderpearl;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.HitResult;

public class EventHooks {

    public static void firePlayerTickPre(Player player) {
        PlayerTickEvent.START.invoker().onStart(new PlayerTickEvent.Pre(player));
    }

    public static void firePlayerTickPost(Player player) {
        PlayerTickEvent.END.invoker().onEnd(new PlayerTickEvent.Post(player));
    }

    public static int onItemUseStart(LivingEntity entity, ItemStack item, int duration) {
        var event = new LivingEntityUseItemEvent.Start(entity, item, duration);
        LivingEntityUseItemEvent.Start.START.invoker().post(event);
        return event.isCanceled() ? -1 : event.getDuration();
    }

    public static int onItemUseTick(LivingEntity entity, ItemStack item, int duration) {
        var event = new LivingEntityUseItemEvent.Tick(entity, item, duration);
        LivingEntityUseItemEvent.TICK.invoker().post(event);
        return event.isCanceled() ? -1 : event.getDuration();
    }

    public static ItemStack onItemUseFinish(LivingEntity entity, ItemStack item, int duration, ItemStack result) {
        var event = new LivingEntityUseItemEvent.Finish(entity, item, duration, result);
        LivingEntityUseItemEvent.FINISH.invoker().post(event);
        return event.getResultStack();
    }

    public static boolean onProjectileImpact(Projectile projectile, HitResult ray) {
        return onProjectileImpactResult(projectile, ray) != ProjectileImpactEvent.ImpactResult.DEFAULT;
    }

    public static ProjectileImpactEvent.ImpactResult onProjectileImpactResult(Projectile projectile, HitResult ray) {
        ProjectileImpactEvent event = new ProjectileImpactEvent(projectile, ray);
        ProjectileImpactEvent.EVENT.invoker().post(event);
        if (event.isCanceled())
            return ProjectileImpactEvent.ImpactResult.SKIP_ENTITY;

        return event.getImpactResult();
    }

    public static void onEmptyLeftClick(Player player) {
        var event = new PlayerInteractEvent.LeftClickEmpty(player);
        PlayerInteractEvent.LEFT_CLICK_EMPTY.invoker().post(event);
    }

    public static LivingChangeTargetEvent onLivingChangeTarget(LivingEntity entity, LivingEntity originalTarget, LivingChangeTargetEvent.ILivingTargetType targetType) {
        var event = new LivingChangeTargetEvent(entity, originalTarget, targetType);
        LivingChangeTargetEvent.EVENT.invoker().post(event);
        return event;
    }

    public static int getExperienceDrop(LivingEntity entity, Player attackingPlayer, int originalExperience) {
        var event = new LivingExperienceDropEvent(entity, attackingPlayer, originalExperience);
        LivingExperienceDropEvent.EVENT.invoker().post(event);
        if (event.isCanceled()) {
            return 0;
        }
        return event.getDroppedExperience();
    }

    public static boolean onEntityStruckByLightning(Entity entity, LightningBolt bolt) {
        var event = new EntityStruckByLightningEvent(entity, bolt);
        EntityStruckByLightningEvent.EVENT.invoker().post(event);
        return event.isCanceled();
    }

    public static double getEntityVisibilityMultiplier(LivingEntity entity, Entity lookingEntity, double originalMultiplier) {
        var event = new LivingVisibilityEvent(entity, lookingEntity, originalMultiplier);
        LivingVisibilityEvent.EVENT.invoker().post(event);
        return Math.max(0, event.getVisibilityModifier());
    }

    public static EntityTeleportEvent.TeleportCommand onEntityTeleportCommand(Entity entity, double targetX, double targetY, double targetZ) {
        var event = new EntityTeleportEvent.TeleportCommand(entity, targetX, targetY, targetZ);
        EntityTeleportEvent.TP_CMD.invoker().post(event);
        return event;
    }

    public static EntityTeleportEvent.SpreadPlayersCommand onEntityTeleportSpreadPlayersCommand(Entity entity, double targetX, double targetY, double targetZ) {
        var event = new EntityTeleportEvent.SpreadPlayersCommand(entity, targetX, targetY, targetZ);
        EntityTeleportEvent.SPREAD_PLAYERS_CMD.invoker().post(event);
        return event;
    }

    public static EntityTeleportEvent.EnderEntity onEnderTeleport(LivingEntity entity, double targetX, double targetY, double targetZ) {
        var event = new EntityTeleportEvent.EnderEntity(entity, targetX, targetY, targetZ);
        EntityTeleportEvent.ENDER_ENTITY.invoker().post(event);
        return event;
    }

    public static EntityTeleportEvent.EnderPearl onEnderPearlLand(ServerPlayer entity, double targetX, double targetY, double targetZ, ThrownEnderpearl pearlEntity, float attackDamage, HitResult hitResult) {
        var event = new EntityTeleportEvent.EnderPearl(entity, targetX, targetY, targetZ, pearlEntity, attackDamage, hitResult);
        EntityTeleportEvent.ENDER_PEARL.invoker().post(event);
        return event;
    }

    public static EntityTeleportEvent.ChorusFruit onChorusFruitTeleport(LivingEntity entity, double targetX, double targetY, double targetZ) {
        var event = new EntityTeleportEvent.ChorusFruit(entity, targetX, targetY, targetZ);
        EntityTeleportEvent.CHORUS.invoker().post(event);
        return event;
    }

    public static boolean onLivingUseTotem(LivingEntity entity, DamageSource damageSource, ItemStack totem, InteractionHand hand) {
        var event = new LivingUseTotemEvent(entity, damageSource, totem, hand);
        LivingUseTotemEvent.EVENT.invoker().post(event);
        return !event.isCanceled();
    }
}
