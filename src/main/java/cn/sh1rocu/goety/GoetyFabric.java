package cn.sh1rocu.goety;

import cn.sh1rocu.goety.api.event.*;
import cn.sh1rocu.goety.api.extension.IBedBlock;
import cn.sh1rocu.goety.api.extension.ICustomBlockPathType;
import cn.sh1rocu.goety.util.forge.FluidInteractionRegistry;
import cn.sh1rocu.goety.util.forge.UsernameCache;
import cn.sh1rocu.goety.util.transfer.ItemItemStorage;
import com.Polarice3.Goety.Goety;
import com.Polarice3.Goety.api.items.magic.IWand;
import com.Polarice3.Goety.common.blocks.ModBlocks;
import com.Polarice3.Goety.common.events.*;
import com.Polarice3.Goety.common.events.spell.CastMagicEvent;
import com.Polarice3.Goety.common.events.spell.CastingMagicEvent;
import com.Polarice3.Goety.common.items.brew.BrewBag;
import com.Polarice3.Goety.common.items.brew.WitchStaff;
import com.Polarice3.Goety.common.items.curios.EternalCauldronItem;
import com.Polarice3.Goety.common.items.magic.FocusBag;
import com.Polarice3.Goety.common.items.magic.FocusPack;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.entity.event.v1.EntitySleepEvents;
import net.fabricmc.fabric.api.entity.event.v1.ServerEntityWorldChangeEvents;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerWorldEvents;
import net.fabricmc.fabric.api.event.player.*;
import net.fabricmc.fabric.api.networking.v1.EntityTrackingEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.fabric.api.registry.LandPathNodeTypesRegistry;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import org.jetbrains.annotations.Nullable;

import java.lang.ref.WeakReference;

public class GoetyFabric implements ModInitializer {

    @Nullable
    private static WeakReference<MinecraftServer> server;

    @Nullable
    public static MinecraftServer getServer() {
        if (server == null) {
            return null;
        }
        return server.get();
    }

    @Override
    public void onInitialize() {
        UsernameCache.load();

        FluidInteractionRegistry.init();

        Goety.init();

        ModEvents.addVillagerTrade();
        ModEvents.addWanderTrade();
        ModEvents.furnaceBurnItems();

        ItemItemStorage.ITEM.registerFallback((itemStack, context) -> {
            Item item = itemStack.getItem();
            if (item instanceof IWand wand) {
                return wand.initCapabilities(itemStack);
            }
            if (item instanceof BrewBag brewBag) {
                return brewBag.initCapabilities(itemStack);
            }
            if (item instanceof FocusBag focusBag) {
                if (item instanceof FocusPack focusPack) {
                    return focusPack.initCapabilities(itemStack);
                } else {
                    return focusBag.initCapabilities(itemStack);
                }
            }
            if (item instanceof WitchStaff witchStaff) {
                return witchStaff.initCapabilities(itemStack);
            }
            if (item instanceof EternalCauldronItem eternalCauldron) {
                return eternalCauldron.initCapabilities(itemStack);
            }
            return null;
        });

        ModBlocks.BLOCKS.forEach(block -> {
            if (block instanceof ICustomBlockPathType custom) {
                LandPathNodeTypesRegistry.registerDynamic(block, (state, world, pos, neighbor) ->
                        custom.getBlockPathType(state, world, pos, null));
            }
        });

        ServerLifecycleEvents.SERVER_STARTING.register((server) -> GoetyFabric.server = new WeakReference<>(server));

        EntitySleepEvents.SET_BED_OCCUPATION_STATE.register((entity, sleepingPos, bedState, occupied) -> {
            if (bedState.getBlock() instanceof IBedBlock bedBlock && bedBlock.isBed(bedState, entity.level(), sleepingPos, entity)) {
                bedBlock.setBedOccupied(bedState, entity.level(), sleepingPos, entity, occupied);
                return true;
            }
            return false;
        });
        EntitySleepEvents.MODIFY_SLEEPING_DIRECTION.register((entity, sleepingPos, direction) -> {
            var bedState = entity.level().getBlockState(sleepingPos);
            if (bedState.getBlock() instanceof IBedBlock bedBlock && bedBlock.isBed(bedState, entity.level(), sleepingPos, entity)) {
                return bedState.getValue(HorizontalDirectionalBlock.FACING);
            }
            return direction;
        });
        EntitySleepEvents.ALLOW_BED.register((entity, sleepingPos, bedState, vanillaResult) -> {
            if (bedState.getBlock() instanceof IBedBlock bedBlock && bedBlock.isBed(bedState, entity.level(), sleepingPos, entity)) {
                return InteractionResult.SUCCESS;
            }
            return InteractionResult.PASS;
        });

        ServerLifecycleEvents.SERVER_STARTED.register(Goety::onServerStarting);
        ServerLifecycleEvents.SERVER_STOPPED.register(Goety::onServerStopped);
        PlayerTickEvent.START.register(ItemEvents::playerTick);
        PlayerTickEvent.END.register(ItemEvents::playerTick);
        LivingTickEvent.EVENT.register(ItemEvents::livingEffects);
        LivingHurtEvent.EVENT.register(ItemEvents::hurtEvent);
        LivingDamageEvent.EVENT.register(BaseEvent.LOWEST, ItemEvents::onLivingDamage);
        LivingJumpEvent.EVENT.register(ItemEvents::onLivingJump);
        LivingFallEvent.EVENT.register(ItemEvents::onLivingFall);
        LivingEntityUseItemEvent.TICK.register(ItemEvents::usingItemEvents);
        PlayerBlockBreakEvents.BEFORE.register(ItemEvents::onBreakingBlock);
        UseBlockCallback.EVENT.register(ItemEvents::playerInteractBlockEvents);
        LivingEntityUseItemEvent.FINISH.register(ItemEvents::useItemEvent);
        ServerLivingEntityEvents.AFTER_DEATH.register(ItemEvents::axeDeath);
        LootingLevelEvent.EVENT.register(ItemEvents::hunterLoot);
        PlayerInteractEvent.LEFT_CLICK_EMPTY.register(ItemEvents::emptyClickEvents);
        AttackEntityCallback.EVENT.register(ItemEvents::playerAttackEvents);
        UseEntityCallback.EVENT.register(ItemEvents::interactEntityEvents);
        UseItemCallback.EVENT.register(ItemEvents::generalInteractEvents);
        // LivingDropsEvent.EVENT.register(ItemEvents::dropEvents);

        PlayerTickEvent.START.register(LichEvents::onPlayerLichdom);
        PlayerTickEvent.END.register(LichEvents::onPlayerLichdom);
        MobEffectEvent.APPLICABLE.register(LichEvents::specialPotionEffects);
        LivingChangeTargetEvent.EVENT.register(LichEvents::undeadFriendly);
        LivingHurtEvent.EVENT.register(LichEvents::hurtEvent);
        ServerLivingEntityEvents.AFTER_DEATH.register(LichEvents::onLivingDeathEvent);

        ServerPlayerEvents.COPY_FROM.register(ModEvents::onPlayerClone);
        ServerEntityEvents.ENTITY_LOAD.register(ModEvents::onEntityJoinWorld);
        ServerPlayConnectionEvents.JOIN.register(ModEvents::onPlayerEntersWorld);
        ServerWorldEvents.LOAD.register(ModEvents::worldLoad);
        ServerWorldEvents.UNLOAD.register(ModEvents::worldUnload);
        ServerTickEvents.START_WORLD_TICK.register(ModEvents::onServerTick);
        ServerTickEvents.END_WORLD_TICK.register(ModEvents::onServerTick);
        MobSpawnEvent.FINALIZE_SPAWN.register(ModEvents::checkSpawnEvents);
        PlayerTickEvent.START.register(ModEvents::playerTick);
        PlayerTickEvent.END.register(ModEvents::playerTick);
        LivingTickEvent.EVENT.register(ModEvents::livingEffects);
        LivingChangeTargetEvent.EVENT.register(ModEvents::targetEvents);
        ServerLivingEntityEvents.ALLOW_DAMAGE.register(ModEvents::attackEvent);
        LivingHurtEvent.EVENT.register(ModEvents::hurtEvent);
        LivingDamageEvent.EVENT.register(ModEvents::damageEvent);
        LivingHealEvent.EVENT.register(ModEvents::onLivingHeal);
        ServerLivingEntityEvents.AFTER_DEATH.register(ModEvents::specialDeath);
        LivingExperienceDropEvent.EVENT.register(ModEvents::experienceEvents);
        LootingLevelEvent.EVENT.register(ModEvents::spellLoot);
        LivingDropsEvent.EVENT.register(ModEvents::dropEvents);
        LivingKnockBackEvent.EVENT.register(ModEvents::knockBackEvents);
        EntityStruckByLightningEvent.EVENT.register(ModEvents::lightningStruckEvent);
        ExplosionEvent.START.register(ModEvents::explosionStartEvent);
        ExplosionEvent.DETONATE.register(ModEvents::explosionDetonateEvent);
        ProjectileImpactEvent.EVENT.register(ModEvents::projectileImpactEvent);
        EntitySleepEvents.ALLOW_SLEEPING.register(ModEvents::sleepEvents);
        EntitySleepEvents.ALLOW_SLEEP_TIME.register(ModEvents::onCanSleep);
        EntitySleepEvents.ALLOW_SLEEPING.register(ModEvents::canStartSleeping);
        EntityTeleportEvent.ENDER_PEARL.register(ModEvents::onTeleport);
        EntityTeleportEvent.ENDER_ENTITY.register(ModEvents::onTeleport);
        EntityTeleportEvent.CHORUS.register(ModEvents::onTeleport);

        LivingTickEvent.EVENT.register(PotionEvents::livingEffects);
        LivingHurtEvent.EVENT.register(PotionEvents::hurtEvent);
        LivingDamageEvent.EVENT.register(PotionEvents::damageEvents);
        LivingExperienceDropEvent.EVENT.register(PotionEvents::experienceEvents);
        ServerLivingEntityEvents.AFTER_DEATH.register(PotionEvents::deathEvents);
        ServerPlayerEvents.AFTER_RESPAWN.register(PotionEvents::respawnEvents);
        LivingTickEvent.EVENT.register(PotionEvents::chargeEffect);
        LivingVisibilityEvent.EVENT.register(PotionEvents::effectVisibilityEvents);
        LivingChangeTargetEvent.EVENT.register(PotionEvents::changeTarget);
        EntityTeleportEvent.CHORUS.register(PotionEvents::enderTeleport);
        EntityTeleportEvent.ENDER_ENTITY.register(PotionEvents::enderTeleport);
        EntityTeleportEvent.ENDER_PEARL.register(PotionEvents::enderTeleport);
        LivingEntityUseItemEvent.FINISH.register(PotionEvents::finishItemEvents);
        CastingMagicEvent.EVENT.register(PotionEvents::onCastingSpell);
        CastMagicEvent.EVENT.register(PotionEvents::onCastSpell);
        ServerEntityEvents.ENTITY_LOAD.register(PotionEvents::projectileAddEvents);
        ProjectileImpactEvent.EVENT.register(PotionEvents::onDeflectImpact);
        UseItemCallback.EVENT.register(PotionEvents::playerInteractItemEvents);
        UseEntityCallback.EVENT.register(PotionEvents::playerInteractEntityEvents);
        UseBlockCallback.EVENT.register(PotionEvents::playerInteractBlockEvents);
        PlayerBlockBreakEvents.BEFORE.register(PotionEvents::breakingBlockEvents);
        UseBlockCallback.EVENT.register(PotionEvents::placingBlockEvents);
        ServerEntityWorldChangeEvents.AFTER_ENTITY_CHANGE_WORLD.register((oldEntity, newEntity, origin, destination) ->
                PotionEvents.dimensionChangeEvents(newEntity, origin, destination));
        ServerEntityWorldChangeEvents.AFTER_PLAYER_CHANGE_WORLD.register(PotionEvents::dimensionChangeEvents);
        MobEffectEvent.APPLICABLE.register(PotionEvents::potionApplicationEvents);
        MobEffectEvent.ADDED.register(PotionEvents::potionAddedEvents);
        MobEffectEvent.REMOVE.register(PotionEvents::potionRemoveEvents);
        MobEffectEvent.EXPIRED.register(PotionEvents::potionExpiredEvents);

        LivingTickEvent.EVENT.register(RobeEvents::livingEffects);
        PlayerBlockBreakEvents.AFTER.register(RobeEvents::onBreakingBlock);
        LivingHurtEvent.EVENT.register(BaseEvent.LOWEST, RobeEvents::hurtEvent);
        ServerLivingEntityEvents.ALLOW_DAMAGE.register(RobeEvents::attackEvent);
        LivingChangeTargetEvent.EVENT.register(RobeEvents::targetEvents);
        LivingVisibilityEvent.EVENT.register(RobeEvents::visibilityEvent);
        LivingFallEvent.EVENT.register(RobeEvents::onLivingFall);
        MobEffectEvent.APPLICABLE.register(RobeEvents::potionApplicationEvents);

        LivingTickEvent.EVENT.register(ServantEvents::livingEffects);
        LivingChangeTargetEvent.EVENT.register(ServantEvents::targetEvents);
        UseEntityCallback.EVENT.register(ServantEvents::interactEntityEvent);
        ServerLivingEntityEvents.ALLOW_DAMAGE.register(ServantEvents::attackEvent);
        AttackEntityCallback.EVENT.addPhaseOrdering(BaseEvent.HIGHEST, BaseEvent.HIGH);
        AttackEntityCallback.EVENT.addPhaseOrdering(BaseEvent.HIGH, Event.DEFAULT_PHASE);
        AttackEntityCallback.EVENT.addPhaseOrdering(Event.DEFAULT_PHASE, BaseEvent.LOW);
        AttackEntityCallback.EVENT.addPhaseOrdering(BaseEvent.LOW, BaseEvent.LOWEST);
        AttackEntityCallback.EVENT.register(BaseEvent.HIGHEST, ServantEvents::playerAttackEvent);
        LivingHurtEvent.EVENT.register(ServantEvents::hurtEvent);
        LivingDamageEvent.EVENT.register(ServantEvents::damageEvent);
        ServerLivingEntityEvents.ALLOW_DEATH.register(ServantEvents::deathEvent);
        ServerEntityWorldChangeEvents.AFTER_ENTITY_CHANGE_WORLD.register((oldEntity, newEntity, origin, destination) ->
                ServantEvents.changeDimensions(newEntity, origin, destination));
        ServerEntityWorldChangeEvents.AFTER_PLAYER_CHANGE_WORLD.register(ServantEvents::changeDimensions);
        ProjectileImpactEvent.EVENT.register(ServantEvents::servantProjectileImpact);
        ExplosionEvent.DETONATE.register(ServantEvents::explosionDetonateEvent);
        LivingDropsEvent.EVENT.register(ServantEvents::dropEvents);
        LivingUseTotemEvent.EVENT.register(ServantEvents::onTotemUse);

        ServerPlayConnectionEvents.DISCONNECT.register(SoulEnergyEvents::onPlayerLogOff);
        PlayerTickEvent.START.register(SoulEnergyEvents::onPlayerTick);
        PlayerTickEvent.END.register(SoulEnergyEvents::onPlayerTick);
        ServerLivingEntityEvents.ALLOW_DAMAGE.register(SoulEnergyEvents::onLivingAttack);
        LivingHurtEvent.EVENT.register(SoulEnergyEvents::onLivingHurt);
        ServerPlayConnectionEvents.JOIN.register(SoulEnergyEvents::onPlayerEntersWorld);
        ServerPlayConnectionEvents.DISCONNECT.register(SoulEnergyEvents::onPlayerLeavesWorld);
        ServerEntityWorldChangeEvents.AFTER_PLAYER_CHANGE_WORLD.register(SoulEnergyEvents::onPlayerChangeDimensions);
        EntityTrackingEvents.STOP_TRACKING.register(SoulEnergyEvents::onPlayerStopTracking);
        UseEntityCallback.EVENT.register(SoulEnergyEvents::onLivingInteract);
        ServerLivingEntityEvents.ALLOW_DEATH.register(SoulEnergyEvents::onLivingDeathEvent);

        AttackEntityCallback.EVENT.register(StunnedEvents::cancelPlayerAttack);
        AttackBlockCallback.EVENT.register(StunnedEvents::cancelBreakSpeed);
        UseBlockCallback.EVENT.register(StunnedEvents::cancelActivateBlock);
        UseEntityCallback.EVENT.register(StunnedEvents::cancelInteract);
        LivingEntityUseItemEvent.START.register(StunnedEvents::cancelUsingItem);
        LivingEntityUseItemEvent.TICK.register(StunnedEvents::cancelTickUsingItem);
        UseItemCallback.EVENT.register(StunnedEvents::cancelPlayerUseItem);
        LivingChangeTargetEvent.EVENT.register(StunnedEvents::onLivingTarget);
        LivingKnockBackEvent.EVENT.register(StunnedEvents::onKnockback);
        MobEffectEvent.APPLICABLE.register(StunnedEvents::potionApplicationEvents);
        ServerLivingEntityEvents.AFTER_DEATH.register(StunnedEvents::onLivingDeath);

        ServerTickEvents.END_SERVER_TICK.register(TimedEvents::serverTickEvents);
        ServerLifecycleEvents.SERVER_STOPPED.register(TimedEvents::stopped);
        ServerLifecycleEvents.SERVER_STARTED.register(TimedEvents::started);

        LivingTickEvent.EVENT.register(WitchBarterEvents::livingEffects);
        UseEntityCallback.EVENT.register(WitchBarterEvents::interactEntityEvent);

    }
}
