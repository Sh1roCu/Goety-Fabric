package com.Polarice3.Goety.common.events;

import cn.sh1rocu.goety.api.event.*;
import cn.sh1rocu.goety.api.extension.IEntityPersistentData;
import cn.sh1rocu.goety.mixin.accessor.MobAccessor;
import cn.sh1rocu.goety.mixin.accessor.VillagerAccessor;
import com.Polarice3.Goety.Goety;
import com.Polarice3.Goety.api.entities.IChunkLoader;
import com.Polarice3.Goety.api.entities.IHiding;
import com.Polarice3.Goety.api.entities.IOwned;
import com.Polarice3.Goety.api.entities.ally.IServant;
import com.Polarice3.Goety.client.particles.ModParticleTypes;
import com.Polarice3.Goety.common.blocks.ModBlocks;
import com.Polarice3.Goety.common.blocks.ModChestBlock;
import com.Polarice3.Goety.common.effects.GoetyEffects;
import com.Polarice3.Goety.common.enchantments.ModEnchantments;
import com.Polarice3.Goety.common.entities.ModEntityType;
import com.Polarice3.Goety.common.entities.ai.DefendVillagerGoal;
import com.Polarice3.Goety.common.entities.ai.FreePrisonerGoal;
import com.Polarice3.Goety.common.entities.ai.TargetHostileOwnedGoal;
import com.Polarice3.Goety.common.entities.ai.WitchBarterGoal;
import com.Polarice3.Goety.common.entities.ally.golem.IceGolem;
import com.Polarice3.Goety.common.entities.ally.illager.cultist.HereticServant;
import com.Polarice3.Goety.common.entities.ally.illager.cultist.MaverickServant;
import com.Polarice3.Goety.common.entities.ally.illager.cultist.ReprobateServant;
import com.Polarice3.Goety.common.entities.ally.illager.cultist.WarlockServant;
import com.Polarice3.Goety.common.entities.ally.illager.raider.ModRavager;
import com.Polarice3.Goety.common.entities.ally.illager.raider.Prisoner;
import com.Polarice3.Goety.common.entities.ally.illager.raider.RaiderServant;
import com.Polarice3.Goety.common.entities.ally.illager.raider.Ravaged;
import com.Polarice3.Goety.common.entities.ally.undead.GraveGolem;
import com.Polarice3.Goety.common.entities.boss.Apostle;
import com.Polarice3.Goety.common.entities.boss.Vizier;
import com.Polarice3.Goety.common.entities.deco.HauntedArmorStand;
import com.Polarice3.Goety.common.entities.hostile.WitherNecromancer;
import com.Polarice3.Goety.common.entities.hostile.cultists.*;
import com.Polarice3.Goety.common.entities.hostile.illagers.*;
import com.Polarice3.Goety.common.entities.hostile.servants.Damned;
import com.Polarice3.Goety.common.entities.neutral.AbstractObsidianMonolith;
import com.Polarice3.Goety.common.entities.neutral.BlazeServant;
import com.Polarice3.Goety.common.entities.neutral.Owned;
import com.Polarice3.Goety.common.entities.projectiles.Fangs;
import com.Polarice3.Goety.common.entities.projectiles.ModDragonFireball;
import com.Polarice3.Goety.common.entities.util.DragonBreathCloud;
import com.Polarice3.Goety.common.entities.util.StormEntity;
import com.Polarice3.Goety.common.items.ModItems;
import com.Polarice3.Goety.common.items.armor.ModArmorMaterials;
import com.Polarice3.Goety.common.items.curios.WarlockGarmentItem;
import com.Polarice3.Goety.common.items.handler.FocusBagItemHandler;
import com.Polarice3.Goety.common.items.magic.FocusBag;
import com.Polarice3.Goety.common.items.magic.FocusPack;
import com.Polarice3.Goety.common.network.ModNetwork;
import com.Polarice3.Goety.common.network.server.SPlayPlayerSoundPacket;
import com.Polarice3.Goety.common.network.server.SPlayWorldSoundPacket;
import com.Polarice3.Goety.common.research.ResearchList;
import com.Polarice3.Goety.common.world.data.ChunkLoadData;
import com.Polarice3.Goety.compat.iron.IronAttributes;
import com.Polarice3.Goety.compat.iron.IronLoaded;
import com.Polarice3.Goety.compat.patchouli.PatchouliLoaded;
import com.Polarice3.Goety.config.MainConfig;
import com.Polarice3.Goety.config.MobsConfig;
import com.Polarice3.Goety.config.SpellConfig;
import com.Polarice3.Goety.init.ModMobType;
import com.Polarice3.Goety.init.ModSounds;
import com.Polarice3.Goety.init.ModTags;
import com.Polarice3.Goety.utils.*;
import com.google.common.collect.Sets;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.fabricmc.fabric.api.tag.convention.v1.ConventionalEntityTypeTags;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.world.Difficulty;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.CombatRules;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.gossip.GossipType;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.NearestVisibleLivingEntities;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.*;
import net.minecraft.world.entity.monster.warden.AngerLevel;
import net.minecraft.world.entity.monster.warden.Warden;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.WanderingTrader;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.DragonFireball;
import net.minecraft.world.entity.projectile.Snowball;
import net.minecraft.world.entity.raid.Raid;
import net.minecraft.world.entity.raid.Raider;
import net.minecraft.world.inventory.CraftingMenu;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.saveddata.maps.MapDecoration;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import vazkii.patchouli.api.PatchouliAPI;

import java.util.*;

import static cn.sh1rocu.goety.api.event.LivingChangeTargetEvent.LivingTargetType.MOB_TARGET;

public class ModEvents {

//    public static void onMissingMappings(MissingMappingsEvent event) {
//        event.getAllMappings(ForgeRegistries.Keys.ENTITY_TYPES).forEach(missingMapping -> {
//            switch (missingMapping.getKey().toString()) {
//                case "goety:ally_vex" -> missingMapping.remap(ModEntityType.VEX_SERVANT);
//                case "goety:ally_irk" -> missingMapping.remap(ModEntityType.IRK_SERVANT);
//                case "goety:ally_trampler" -> missingMapping.remap(ModEntityType.TRAMPLER_SERVANT);
//            }
//        });
//        event.getAllMappings(ForgeRegistries.Keys.ITEMS).forEach(missingMapping -> {
//            if (missingMapping.getKey().toString().equals("goety:bubble_stream_focus")) {
//                missingMapping.remap(ModItems.WATER_JET_FOCUS);
//            }
//        });
//        event.getAllMappings(ForgeRegistries.Keys.BLOCKS).forEach(missingMapping -> {
//            if (missingMapping.getKey().toString().equals("goety:soiled_oak_planks")) {
//                missingMapping.remap(ModBlocks.SOILED_SPRUCE_PLANKS);
//            }
//            if (missingMapping.getKey().toString().equals("goety:soiled_oak_planks_heavy")) {
//                missingMapping.remap(ModBlocks.SOILED_SPRUCE_PLANKS_HEAVY);
//            }
//        });
//    }

    public static void onPlayerClone(ServerPlayer original, ServerPlayer player, boolean alive) {
        // 注册CCA时选择了同步策略，会自动同步数据，应该不需要手动

//        ILichdom capability2 = LichdomHelper.getCapability(original);
//
//        ModCapabilities.LICH_DOM.maybeGet(player)
//                .ifPresent(lichdom ->
//                        lichdom.setLichdom(capability2.getLichdom()));
//
//        ModCapabilities.LICH_DOM.maybeGet(player)
//                .ifPresent(lichdom ->
//                        lichdom.setLichMode(capability2.isLichMode()));
//
//        ModCapabilities.LICH_DOM.maybeGet(player)
//                .ifPresent(lichdom ->
//                        lichdom.setNightVision(capability2.nightVision()));
//
//        ISoulEnergy capability3 = SEHelper.getCapability(original);
//
//        ModCapabilities.SOUL_ENERGY.maybeGet(player)
//                .ifPresent(soulEnergy ->
//                        soulEnergy.setSEActive(capability3.getSEActive()));
//        ModCapabilities.SOUL_ENERGY.maybeGet(player)
//                .ifPresent(soulEnergy ->
//                        soulEnergy.setSoulEnergy(capability3.getSoulEnergy()));
//        ModCapabilities.SOUL_ENERGY.maybeGet(player)
//                .ifPresent(soulEnergy ->
//                        soulEnergy.setRecoil(capability3.getRecoil()));
//        ModCapabilities.SOUL_ENERGY.maybeGet(player)
//                .ifPresent(soulEnergy ->
//                        soulEnergy.setArcaBlock(capability3.getArcaBlock()));
//        ModCapabilities.SOUL_ENERGY.maybeGet(player)
//                .ifPresent(soulEnergy ->
//                        soulEnergy.setArcaBlockDimension(capability3.getArcaBlockDimension()));
//        ModCapabilities.SOUL_ENERGY.maybeGet(player)
//                .ifPresent(soulEnergy ->
//                        soulEnergy.setRestPeriod(capability3.getRestPeriod()));
//        ModCapabilities.SOUL_ENERGY.maybeGet(player)
//                .ifPresent(soulEnergy -> {
//                    for (Research research : capability3.getResearch()) {
//                        soulEnergy.addResearch(research);
//                    }
//                });
//        ModCapabilities.SOUL_ENERGY.maybeGet(player)
//                .ifPresent(soulEnergy -> {
//                    for (UUID uuid : capability3.grudgeList()) {
//                        soulEnergy.addGrudge(uuid);
//                    }
//                });
//        ModCapabilities.SOUL_ENERGY.maybeGet(player)
//                .ifPresent(soulEnergy -> {
//                    for (UUID uuid : capability3.allyList()) {
//                        soulEnergy.addAlly(uuid);
//                    }
//                });
//        ModCapabilities.SOUL_ENERGY.maybeGet(player)
//                .ifPresent(soulEnergy -> {
//                    for (EntityType<?> entityType : capability3.grudgeTypeList()) {
//                        soulEnergy.addGrudgeType(entityType);
//                    }
//                });
//        ModCapabilities.SOUL_ENERGY.maybeGet(player)
//                .ifPresent(soulEnergy -> {
//                    for (EntityType<?> entityType : capability3.allyTypeList()) {
//                        soulEnergy.addAllyType(entityType);
//                    }
//                });
//        ModCapabilities.SOUL_ENERGY.maybeGet(player)
//                .ifPresent(soulEnergy -> {
//                    soulEnergy.setBannerBaseColor(capability3.bannerBaseColor());
//                });
//        ModCapabilities.SOUL_ENERGY.maybeGet(player)
//                .ifPresent(soulEnergy -> {
//                    if (capability3.bannerPattern() != null) {
//                        soulEnergy.setBannerPattern(capability3.bannerPattern());
//                    }
//                });
//        ModCapabilities.SOUL_ENERGY.maybeGet(player)
//                .ifPresent(soulEnergy ->
//                        soulEnergy.setApostleWarned(capability3.apostleWarned()));
//        ModCapabilities.SOUL_ENERGY.maybeGet(player)
//                .ifPresent(soulEnergy ->
//                        soulEnergy.setCooldowns(capability3.cooldowns()));
//        ModCapabilities.SOUL_ENERGY.maybeGet(player)
//                .ifPresent(soulEnergy ->
//                        soulEnergy.setBottling(capability3.bottling()));
//        ;
//        ModCapabilities.SOUL_ENERGY.maybeGet(player)
//                .ifPresent(soulEnergy ->
//                        soulEnergy.setCameraUUID(null));
//        ModCapabilities.SOUL_ENERGY.maybeGet(player)
//                .ifPresent(soulEnergy ->
//                        soulEnergy.setMiningProgress(0));
//        ModCapabilities.SOUL_ENERGY.maybeGet(player)
//                .ifPresent(soulEnergy ->
//                        soulEnergy.setMiningPos(null));
//
//        IMisc capability4 = MiscCapHelper.getCapability(original);
//
//        ModCapabilities.MISC.maybeGet(player)
//                .ifPresent(misc ->
//                        misc.setShields(capability4.shieldsLeft()));
//        ModCapabilities.MISC.maybeGet(player)
//                .ifPresent(misc ->
//                        misc.setShieldTime(capability4.shieldTime()));
//        ModCapabilities.MISC.maybeGet(player)
//                .ifPresent(misc ->
//                        misc.setShieldCool(capability4.shieldCool()));
//        ModCapabilities.MISC.maybeGet(player)
//                .ifPresent(misc ->
//                        misc.setAmbientSoundTime(0));

    }

    public static void onEntityJoinWorld(Entity entity, ServerLevel world) {
        if (entity instanceof LivingEntity && !world.isClientSide()) {
            if (entity instanceof Player player) {
                SEHelper.sendSEUpdatePacket(player);
                LichdomHelper.sendLichUpdatePacket(player);
            }
            if (entity instanceof Mob mob) {
                if (entity instanceof Witch witch) {
                    ((MobAccessor) witch).goety$goalSelector().addGoal(1, new WitchBarterGoal(witch));
                }
                if (mob.getType().is(ModTags.EntityTypes.VILLAGE_GUARDS)) {
                    ((MobAccessor) mob).goety$goalSelector().addGoal(1, new FreePrisonerGoal(mob));
                    ((MobAccessor) mob).goety$targetSelector().addGoal(3, new TargetHostileOwnedGoal<>(mob, Owned.class));
                    ((MobAccessor) mob).goety$targetSelector().addGoal(3, new DefendVillagerGoal(mob));
                }
                if (entity instanceof PathfinderMob creeper && creeper.getType().is(ModTags.EntityTypes.CREEPERS)) {
                    ((MobAccessor) creeper).goety$goalSelector().addGoal(3, new AvoidEntityGoal<>(creeper, Player.class, (target) -> target != null && CuriosFinder.hasCurio(target, ModItems.FELINE_AMULET), 6.0F, 1.0D, 1.2D, EntitySelector.NO_SPECTATORS::test));
                }
                if (entity instanceof Zombie zombie) {
                    boolean villagerHater = ((MobAccessor) zombie).goety$targetSelector()
                            .getAvailableGoals()
                            .stream()
                            .anyMatch(goal -> goal.getGoal() instanceof NearestAttackableTargetGoal<?> targetGoal && targetGoal.targetType == AbstractVillager.class);
                    if (villagerHater) {
                        ((MobAccessor) zombie).goety$targetSelector().addGoal(3, new NearestAttackableTargetGoal<>(zombie, Prisoner.class, false));
                    }
                }
            }
        }
        if (MainConfig.BetterDragonFireball.get()) {
            if (entity instanceof DragonFireball original) {
                ModDragonFireball dragonFireball;
                if (original.getOwner() instanceof LivingEntity livingEntity) {
                    dragonFireball = new ModDragonFireball(entity.level, livingEntity, original.xPower, original.yPower, original.zPower);
                } else {
                    dragonFireball = new ModDragonFireball(ModEntityType.MOD_DRAGON_FIREBALL, entity.level);
                }
                dragonFireball.moveTo(original.position());
                if (entity.level.addFreshEntity(dragonFireball)) {
                    original.discard();
                    // event.setCanceled(true);
                }
            }
            if (entity instanceof AreaEffectCloud cloud) {
                if (cloud.getOwner() instanceof EnderDragon) {
                    DragonBreathCloud breathCloud = new DragonBreathCloud(entity.level, cloud.getX(), cloud.getY(), cloud.getZ());
                    breathCloud.setOwner(cloud.getOwner());
                    breathCloud.setRadius(cloud.getRadius());
                    breathCloud.setRadiusOnUse(cloud.getRadiusOnUse());
                    breathCloud.setRadiusPerTick(cloud.getRadiusPerTick());
                    breathCloud.setDuration(cloud.getDuration());
                    breathCloud.setDurationOnUse(cloud.getDurationOnUse());
                    breathCloud.setWaitTime(cloud.getWaitTime());
                    if (entity.level.addFreshEntity(breathCloud)) {
                        cloud.discard();
                        // event.setCanceled(true);
                    }
                }
            }
        }
        if (entity instanceof StormEntity) {
            if (!entity.level.isClientSide) {
                ServerLevel serverWorld = (ServerLevel) entity.level;
                serverWorld.setWeatherParameters(0, 6000, true, true);
            }
        }
        if (entity instanceof Raider raider) {
            if (world instanceof ServerLevel) {
                if (raider.hasActiveRaid()) {
                    Raid raid = raider.getCurrentRaid();
                    if (raid != null && raid.isActive() && !raid.isBetweenWaves() && !raid.isOver() && !raid.isStopped()) {
                        Player player = EntityFinder.getNearbyPlayer(world, raid.getCenter());
                        if (player != null) {
                            if (MobsConfig.IllagerRaid.get()) {
                                if (SEHelper.getSoulAmountInt(player) < (MobsConfig.IllagerAssaultSEThreshold.get() * 2)) {
                                    if (raider instanceof HuntingIllagerEntity) {
                                        raid.removeFromRaid(raider, true);
                                        // event.setCanceled(true);
                                    }
                                }
                            } else {
                                if (raider instanceof HuntingIllagerEntity) {
                                    raid.removeFromRaid(raider, true);
                                    // event.setCanceled(true);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public static void onPlayerEntersWorld(ServerGamePacketListenerImpl handler, PacketSender sender, MinecraftServer server) {
        CompoundTag playerData = ((IEntityPersistentData) handler.getPlayer()).goety$getPersistentData();
        CompoundTag data;

        if (!playerData.contains(IEntityPersistentData.PLAYER_PERSISTED_NBT_TAG)) {
            data = new CompoundTag();
        } else {
            data = playerData.getCompound(IEntityPersistentData.PLAYER_PERSISTED_NBT_TAG);
        }
        if (!handler.getPlayer().level.isClientSide) {
            if (data.getBoolean(ConstantPaths.readScroll())) {
                SEHelper.addResearch(handler.getPlayer(), ResearchList.FORBIDDEN);
            }
            if (MainConfig.StarterTotem.get()) {
                if (!data.getBoolean("goety:gotTotem")) {
                    handler.getPlayer().addItem(new ItemStack(ModItems.TOTEM_OF_ROOTS));
                    data.putBoolean("goety:gotTotem", true);
                    playerData.put(IEntityPersistentData.PLAYER_PERSISTED_NBT_TAG, data);
                }
            }
            if (PatchouliLoaded.PATCHOULI.isLoaded()) {
                if (MainConfig.StarterBook.get()) {
                    if (!data.getBoolean("goety:starterBook")) {
                        ItemStack book = PatchouliAPI.get().getBookStack(Goety.location("black_book"));
                        handler.getPlayer().addItem(book);
                        data.putBoolean("goety:starterBook", true);
                        playerData.put(IEntityPersistentData.PLAYER_PERSISTED_NBT_TAG, data);
                    }
                }
                if (MainConfig.StarterWitchBook.get()) {
                    if (!data.getBoolean("goety:witchBook")) {
                        ItemStack book = PatchouliAPI.get().getBookStack(Goety.location("witches_brew"));
                        handler.getPlayer().addItem(book);
                        data.putBoolean("goety:witchBook", true);
                        playerData.put(IEntityPersistentData.PLAYER_PERSISTED_NBT_TAG, data);
                    }
                }
            }
        }
    }

    private static final Map<ServerLevel, IllagerSpawner> ILLAGER_SPAWN_MAP = new HashMap<>();
    private static final Map<ServerLevel, WightSpawner> WIGHT_SPAWN_MAP = new HashMap<>();

    public static void worldLoad(MinecraftServer server, ServerLevel serverWorld) {
        // RaidAdditions.addRaiders();
        ILLAGER_SPAWN_MAP.put(serverWorld, new IllagerSpawner());
        WIGHT_SPAWN_MAP.put(serverWorld, new WightSpawner());
        ChunkLoadData data = ChunkLoadData.get(serverWorld);
        List<BlockPos> toRemove = new ArrayList<>();
        data.getPositions().forEach((pos, radius) -> {
            ChunkPos chunkPos = new ChunkPos(pos);
            serverWorld.getChunkSource().addRegionTicket(
                    ModTicketTypes.BLOCK, chunkPos, radius, pos);
            BlockEntity be = serverWorld.getBlockEntity(pos);
            if (!(be instanceof IChunkLoader)) {
                toRemove.add(pos);
            }
        });
        if (!toRemove.isEmpty()) {
            for (BlockPos blockPos : toRemove) {
                data.removePosition(blockPos);
            }
        }
    }

    public static void worldUnload(MinecraftServer server, ServerLevel serverWorld) {
//        Raid.RaiderType[] members = Raid.RaiderType.values();
//        for (Raid.RaiderType member : members) {
//            if (RaidAdditions.NEW_RAID_MEMBERS.contains(member)) {
//                ArrayUtils.remove(members, member.ordinal());
//            }
//        }
        ILLAGER_SPAWN_MAP.remove(serverWorld);
        WIGHT_SPAWN_MAP.remove(serverWorld);
    }

    public static void onServerTick(ServerLevel serverWorld) {
        IllagerSpawner illagerSpawner = ILLAGER_SPAWN_MAP.get(serverWorld);
        if (illagerSpawner != null) {
            illagerSpawner.tick(serverWorld);
        }
        WightSpawner wightSpawner = WIGHT_SPAWN_MAP.get(serverWorld);
        if (wightSpawner != null) {
            wightSpawner.tick(serverWorld);
        }

    }

    public static void checkSpawnEvents(MobSpawnEvent.FinalizeSpawn event) {
        if (event.getEntity() instanceof SpellcasterIllager || event.getEntity() instanceof Witch || event.getEntity() instanceof Cultist) {
            if (event.getSpawnType() == MobSpawnType.STRUCTURE) {
                event.getEntity().addTag(ConstantPaths.structureMob());
            }
        }
        if (event.getSpawnType() == MobSpawnType.STRUCTURE) {
            if (event.getEntity().getTags().contains(ConstantPaths.giveAI())) {
                if (event.getEntity().isNoAi()) {
                    event.getEntity().setNoAi(false);
                    event.getEntity().removeTag(ConstantPaths.giveAI());
                }
            }
        }

        // for Fabric
        boolean removed = false;

        if (event.getEntity() instanceof Cultist cultist) {
            if (event.getLevel() instanceof ServerLevel serverLevel) {
                if (serverLevel.getRaidAt(cultist.blockPosition()) != null) {
                    if (event.getSpawnType() == MobSpawnType.NATURAL || event.getSpawnType() == MobSpawnType.CHUNK_GENERATION) {
                        // event.setSpawnCancelled(true);
                        removed = true;
                    }
                }
            }
        }
        Mob mob = event.getEntity();
        if (IronLoaded.IRON_SPELLBOOKS.isLoaded()) {
            if (!IronAttributes.resistances(mob).isEmpty()) {
                for (AttributeInstance attributeInstance : IronAttributes.resistances(mob)) {
                    if (attributeInstance != null) {
                        if (mob instanceof Inquillager) {
                            attributeInstance.setBaseValue(1.75D);
                        } else {
                            if (attributeInstance.getAttribute() == IronAttributes.EVOCATION_MAGIC_RESIST) {
                                if (mob instanceof Envioker || mob instanceof Minister || mob instanceof Vizier) {
                                    attributeInstance.setBaseValue(1.25D);
                                }
                            }
                            if (attributeInstance.getAttribute() == IronAttributes.NATURE_MAGIC_RESIST) {
                                if (mob instanceof Conquillager) {
                                    attributeInstance.setBaseValue(1.25D);
                                }
                                if (mob instanceof Apostle) {
                                    attributeInstance.setBaseValue(1.5D);
                                }
                                if (mob.getMobType() == ModMobType.NATURAL) {
                                    attributeInstance.setBaseValue(1.5D);
                                }
                            }
                            if (attributeInstance.getAttribute() == IronAttributes.HOLY_MAGIC_RESIST) {
                                if (mob instanceof Conquillager || mob instanceof Preacher || mob instanceof Minister || mob instanceof Vizier) {
                                    attributeInstance.setBaseValue(1.25D);
                                }
                                if (mob instanceof Apostle) {
                                    attributeInstance.setBaseValue(0.25D);
                                }
                            }
                            if (attributeInstance.getAttribute() == IronAttributes.ICE_MAGIC_RESIST) {
                                if (mob instanceof Cryologer) {
                                    attributeInstance.setBaseValue(1.5D);
                                }
                                if (mob instanceof IceGolem) {
                                    attributeInstance.setBaseValue(2.0D);
                                }
                                if (mob instanceof BlazeServant) {
                                    attributeInstance.setBaseValue(0.5D);
                                }
                            }
                            if (attributeInstance.getAttribute() == IronAttributes.LIGHTNING_MAGIC_RESIST) {
                                if (mob instanceof StormCaster) {
                                    attributeInstance.setBaseValue(1.5D);
                                }
                            }
                            if (attributeInstance.getAttribute() == IronAttributes.FIRE_MAGIC_RESIST) {
                                if (mob instanceof Apostle) {
                                    attributeInstance.setBaseValue(2.0D);
                                }
                                if (mob instanceof IceGolem) {
                                    attributeInstance.setBaseValue(0.33D);
                                }
                            }
                            if (attributeInstance.getAttribute() == IronAttributes.BLOOD_MAGIC_RESIST) {
                                if (mob instanceof Apostle) {
                                    attributeInstance.setBaseValue(1.75D);
                                }
                            }
                        }
                    }
                }
            }
        }
        if (removed) {
            mob.discard();
        }
    }

    public static void playerTick(PlayerTickEvent event) {
        Player player = event.getEntity();
        Level world = player.level;
        if (world instanceof ServerLevel) {
            if (player.tickCount % 20 == 0) {
                if (player instanceof ServerPlayer serverPlayer) {
                    if (serverPlayer.getServer() != null) {
                        Advancement advancement3 = serverPlayer.getServer().getAdvancements().getAdvancement(Goety.location("goety/read_warred_and_haunting_scroll"));
                        if (advancement3 != null) {
                            AdvancementProgress advancementProgress3 = serverPlayer.getAdvancements().getOrStartProgress(advancement3);
                            if (!advancementProgress3.isDone()) {
                                Advancement advancement1 = serverPlayer.getServer().getAdvancements().getAdvancement(Goety.location("goety/read_warred_scroll"));
                                Advancement advancement2 = serverPlayer.getServer().getAdvancements().getAdvancement(Goety.location("goety/read_haunting_scroll"));
                                if (advancement1 != null && advancement2 != null) {
                                    AdvancementProgress advancementProgress1 = serverPlayer.getAdvancements().getOrStartProgress(advancement1);
                                    AdvancementProgress advancementProgress2 = serverPlayer.getAdvancements().getOrStartProgress(advancement2);
                                    if (advancementProgress1.isDone() && advancementProgress2.isDone()) {
                                        for (String s : advancementProgress3.getRemainingCriteria()) {
                                            serverPlayer.getAdvancements().award(advancement3, s);
                                        }
                                    }
                                }
                            }
                        }
                        Advancement advancement4 = serverPlayer.getServer().getAdvancements().getAdvancement(Goety.location("goety/read_buried_and_bygone_scroll"));
                        if (advancement4 != null) {
                            AdvancementProgress advancementProgress4 = serverPlayer.getAdvancements().getOrStartProgress(advancement4);
                            if (!advancementProgress4.isDone()) {
                                Advancement advancement1 = serverPlayer.getServer().getAdvancements().getAdvancement(Goety.location("goety/unlock_necromancer"));
                                Advancement advancement2 = serverPlayer.getServer().getAdvancements().getAdvancement(Goety.location("goety/read_bygone_scroll"));
                                if (advancement1 != null && advancement2 != null) {
                                    AdvancementProgress advancementProgress1 = serverPlayer.getAdvancements().getOrStartProgress(advancement1);
                                    AdvancementProgress advancementProgress2 = serverPlayer.getAdvancements().getOrStartProgress(advancement2);
                                    if (advancementProgress1.isDone() && advancementProgress2.isDone()) {
                                        for (String s : advancementProgress4.getRemainingCriteria()) {
                                            serverPlayer.getAdvancements().award(advancement4, s);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public static void livingEffects(LivingTickEvent event) {
        LivingEntity livingEntity = event.getEntity();
        if (livingEntity != null && livingEntity.isAlive()) {
            if (!MobUtil.isSpellCasting(livingEntity)) {
                if (MiscCapHelper.getClientTargetID(livingEntity) > 0) {
                    MiscCapHelper.setClientTargetID(livingEntity, 0);
                }
            }
            if (MiscCapHelper.getShields(livingEntity) > 0) {
                if (MiscCapHelper.getShieldTime(livingEntity) > 0) {
                    MiscCapHelper.decreaseShieldTime(livingEntity);
                } else {
                    MiscCapHelper.setShields(livingEntity, 0);
                    if (!livingEntity.level.isClientSide) {
                        if (livingEntity instanceof Player player) {
                            ModNetwork.sendTo(player, SPlayPlayerSoundPacket.ID, SPlayPlayerSoundPacket.encode(ModSounds.WALL_DISAPPEAR, 1.0F, 2.0F));
                        } else {
                            livingEntity.playSound(ModSounds.WALL_DISAPPEAR, 1.0F, 2.0F);
                        }
                    }
                }
            } else {
                if (MiscCapHelper.getShieldTime(livingEntity) > 0) {
                    MiscCapHelper.setShieldTime(livingEntity, 0);
                }
            }
            if (MiscCapHelper.getShieldCool(livingEntity) > 0) {
                MiscCapHelper.decreaseShieldCool(livingEntity);
            }
            if (MiscCapHelper.getShakeTime(livingEntity) > 0) {
                MiscCapHelper.setShakeTime(livingEntity, MiscCapHelper.getShakeTime(livingEntity) - 1);
            }
            if (MiscCapHelper.getSunscreen(livingEntity) > 0) {
                MiscCapHelper.setSunscreen(livingEntity, MiscCapHelper.getSunscreen(livingEntity) - 1);
            }
            if (livingEntity instanceof Mob mob) {
                double followRange = 32.0D;
                if (mob.getAttribute(Attributes.FOLLOW_RANGE) != null) {
                    followRange = mob.getAttributeValue(Attributes.FOLLOW_RANGE) * 2;
                }
//                MiscCapHelper.updateMobTarget(mob); Commented in case it causes lag
                if (mob.getTarget() instanceof Apostle apostle) {
                    if (apostle.obsidianInvul > 5) {
                        for (AbstractObsidianMonolith obsidianMonolith : mob.level.getEntitiesOfClass(AbstractObsidianMonolith.class, mob.getBoundingBox().inflate(followRange, 8.0D, followRange))) {
                            if (obsidianMonolith.getOwner() == apostle) {
                                mob.setTarget(obsidianMonolith);
                                try {
                                    mob.getBrain().setMemoryWithExpiry(MemoryModuleType.ANGRY_AT, obsidianMonolith.getUUID(), 600L);
                                    mob.getBrain().setMemoryWithExpiry(MemoryModuleType.ATTACK_TARGET, obsidianMonolith, 600L);
                                    if (mob instanceof Warden warden) {
                                        warden.increaseAngerAt(obsidianMonolith, AngerLevel.ANGRY.getMinimumAnger() + 20, false);
                                        warden.setAttackTarget(obsidianMonolith);
                                    }
                                } catch (NullPointerException ignored) {
                                }
                            }
                        }
                    }
                }
                if (mob.getTarget() instanceof AbstractObsidianMonolith monolith) {
                    if (monolith.empowered > 5) {
                        for (Heretic heretic : mob.level.getEntitiesOfClass(Heretic.class, mob.getBoundingBox().inflate(followRange, 8.0D, followRange))) {
                            if (heretic.getMonolith() == monolith) {
                                mob.setTarget(heretic);
                            }
                        }
                    }
                }
                if (mob.getTarget() instanceof IHiding hiding) {
                    if (hiding.isHiding()) {
                        mob.setTarget(null);
                    }
                }
            }
            if (livingEntity instanceof Raider raider) {
                if (raider.getTarget() instanceof Player player) {
                    if (SEHelper.getSoulAmountInt(player) > MobsConfig.IllagerAssaultSEThreshold.get() * 2) {
                        if (!raider.isAggressive()) {
                            raider.setAggressive(true);
                        }
                    }
                }
            }
            if (livingEntity instanceof Villager villager) {
                if (!villager.level.isClientSide) {
                    Brain<?> brain = villager.getBrain();
                    Optional<LivingEntity> avoidIllager = Optional.empty();
                    NearestVisibleLivingEntities nearestvisiblelivingentities = brain.getMemory(MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES).orElse(NearestVisibleLivingEntities.empty());
                    for (LivingEntity livingentity : nearestvisiblelivingentities.findAll((p_186157_) -> true)) {
                        if (livingentity instanceof HuntingIllagerEntity || livingentity instanceof Tormentor || livingentity instanceof HostileGolem || livingentity instanceof Trampler || livingentity instanceof Vizier) {
                            avoidIllager = Optional.of(livingentity);
                        } else if (livingentity instanceof RaiderServant servant) {
                            if (servant.isRaiding() || servant.isHostile()) {
                                avoidIllager = Optional.of(livingentity);
                                if (servant.isRaiding()) {
                                    brain.setMemory(MemoryModuleType.HEARD_BELL_TIME, villager.level.getGameTime());
                                }
                            }
                        }
                    }
                    if (avoidIllager.isPresent()) {
                        brain.setMemory(MemoryModuleType.NEAREST_HOSTILE, avoidIllager);
                        if (avoidIllager.get() instanceof RaiderServant servant && servant.isRaiding()) {
                            if (!villager.isNoAi() && villager.getRandom().nextInt(100) == 0) {
                                villager.level().broadcastEntityEvent(villager, (byte) 42);
                            }
                        }
                    }
                    Player player = brain.getMemory(MemoryModuleType.NEAREST_VISIBLE_PLAYER).orElse(null);
                    if (player != null) {
                        if (MobsConfig.VillagerHate.get()) {
                            if (CuriosFinder.hasCurio(player, item -> item.is(ModTags.Items.ROBES))) {
                                if (villager.getPlayerReputation(player) > -25 && villager.getPlayerReputation(player) < 25) {
                                    villager.getGossips().add(player.getUUID(), GossipType.MINOR_NEGATIVE, 25);
                                }
                            }
                        }
                        if (MobsConfig.VillagerHateRavager.get()) {
                            for (Owned owned : player.level.getEntitiesOfClass(Owned.class, player.getBoundingBox().inflate(16.0D))) {
                                if (owned instanceof Ravaged || owned instanceof ModRavager) {
                                    if (owned.getTrueOwner() == player || owned.getMasterOwner() == player) {
                                        if (villager.getPlayerReputation(player) > -200) {
                                            villager.getGossips().add(player.getUUID(), GossipType.MAJOR_NEGATIVE, 25);
                                        }
                                    }
                                }
                            }
                        }
                    }
                    if (villager.level instanceof ServerLevel serverLevel) {
                        if (MobsConfig.VillagerConvertWarlock.get() || MobsConfig.VillagerConvertWarlockUnholy.get()) {
                            if (BlockFinder.getVerticalBlock(serverLevel, villager.blockPosition(), Blocks.CRYING_OBSIDIAN.defaultBlockState(), 16, true)) {
                                if (villager.getRandom().nextFloat() < 7.5E-4F && serverLevel.getDifficulty() != Difficulty.PEACEFUL) {
                                    if (player != null && CuriosFinder.hasUnholySet(player) && MobsConfig.VillagerConvertWarlockUnholy.get()) {
                                        // if (ForgeEventFactory.canLivingConvert(villager, ModEntityType.WARLOCK_SERVANT, (timer) -> {
                                        // })) {
                                        serverLevel.explode(villager, villager.getX(), villager.getY(), villager.getZ(), 0.1F, Level.ExplosionInteraction.NONE);
                                        WarlockServant warlock = ModEntityType.WARLOCK_SERVANT.create(serverLevel);
                                        if (warlock != null) {
                                            warlock.moveTo(villager.getX(), villager.getY(), villager.getZ(), villager.getYRot(), villager.getXRot());
                                            warlock.setTrueOwner(player);
                                            warlock.finalizeSpawn(serverLevel, serverLevel.getCurrentDifficultyAt(warlock.blockPosition()), MobSpawnType.CONVERSION, null, null);
                                            warlock.setNoAi(villager.isNoAi());
                                            if (villager.hasCustomName()) {
                                                warlock.setCustomName(villager.getCustomName());
                                                warlock.setCustomNameVisible(villager.isCustomNameVisible());
                                            }

                                            warlock.setPersistenceRequired();
                                            ServerLivingEntityEvents.MOB_CONVERSION.invoker().onConversion(villager, warlock, false);
                                            serverLevel.addFreshEntityWithPassengers(warlock);
                                            MobUtil.releaseAllPois(villager);
                                            villager.discard();
                                        }
                                        // }
                                    } else if (MobsConfig.VillagerConvertWarlock.get()) {
                                        // if (ForgeEventFactory.canLivingConvert(villager, ModEntityType.WARLOCK, (timer) -> {
                                        // })) {
                                        serverLevel.explode(villager, villager.getX(), villager.getY(), villager.getZ(), 0.1F, Level.ExplosionInteraction.NONE);
                                        Warlock warlock = ModEntityType.WARLOCK.create(serverLevel);
                                        if (warlock != null) {
                                            warlock.moveTo(villager.getX(), villager.getY(), villager.getZ(), villager.getYRot(), villager.getXRot());
                                            warlock.finalizeSpawn(serverLevel, serverLevel.getCurrentDifficultyAt(warlock.blockPosition()), MobSpawnType.CONVERSION, null, null);
                                            warlock.setNoAi(villager.isNoAi());
                                            if (villager.hasCustomName()) {
                                                warlock.setCustomName(villager.getCustomName());
                                                warlock.setCustomNameVisible(villager.isCustomNameVisible());
                                            }

                                            warlock.setPersistenceRequired();
                                            ServerLivingEntityEvents.MOB_CONVERSION.invoker().onConversion(villager, warlock, false);
                                            serverLevel.addFreshEntityWithPassengers(warlock);
                                            MobUtil.releaseAllPois(villager);
                                            villager.discard();
                                        }
                                        // }
                                    }
                                }
                            }
                        }
                        if (MobsConfig.VillagerConvertHeretic.get() || MobsConfig.VillagerConvertHereticUnholy.get()) {
                            if (villager.getRandom().nextFloat() < 7.5E-4F && villager.isSleeping()) {
                                if (BlockFinder.findNetherPortal(serverLevel, villager.blockPosition(), 8).isPresent()) {
                                    if (player != null && (CuriosFinder.hasUnholySet(player) || ItemHelper.hasMaleficHelm(player)) && MobsConfig.VillagerConvertHereticUnholy.get()) {
                                        // if (ForgeEventFactory.canLivingConvert(villager, ModEntityType.HERETIC_SERVANT, (timer) -> {
                                        // })) {
                                        serverLevel.explode(villager, villager.getX(), villager.getY(), villager.getZ(), 0.1F, Level.ExplosionInteraction.NONE);
                                        HereticServant heretic = ModEntityType.HERETIC_SERVANT.create(serverLevel);
                                        if (heretic != null) {
                                            heretic.moveTo(villager.getX(), villager.getY(), villager.getZ(), villager.getYRot(), villager.getXRot());
                                            heretic.setTrueOwner(player);
                                            heretic.finalizeSpawn(serverLevel, serverLevel.getCurrentDifficultyAt(heretic.blockPosition()), MobSpawnType.CONVERSION, null, null);
                                            heretic.setNoAi(villager.isNoAi());
                                            if (villager.hasCustomName()) {
                                                heretic.setCustomName(villager.getCustomName());
                                                heretic.setCustomNameVisible(villager.isCustomNameVisible());
                                            }

                                            heretic.setPersistenceRequired();
                                            ServerLivingEntityEvents.MOB_CONVERSION.invoker().onConversion(villager, heretic, false);
                                            serverLevel.addFreshEntityWithPassengers(heretic);
                                            MobUtil.releaseAllPois(villager);
                                            villager.discard();
                                        }
                                        // }
                                    } else if (MobsConfig.VillagerConvertHeretic.get()) {
                                        if (serverLevel.getDifficulty() != Difficulty.PEACEFUL /*&& ForgeEventFactory.canLivingConvert(villager, ModEntityType.HERETIC, (timer) -> {
                                        })*/) {
                                            serverLevel.explode(villager, villager.getX(), villager.getY(), villager.getZ(), 0.1F, Level.ExplosionInteraction.NONE);
                                            Heretic heretic = ModEntityType.HERETIC.create(serverLevel);
                                            if (heretic != null) {
                                                heretic.moveTo(villager.getX(), villager.getY(), villager.getZ(), villager.getYRot(), villager.getXRot());
                                                heretic.finalizeSpawn(serverLevel, serverLevel.getCurrentDifficultyAt(heretic.blockPosition()), MobSpawnType.CONVERSION, null, null);
                                                heretic.setNoAi(villager.isNoAi());
                                                if (villager.hasCustomName()) {
                                                    heretic.setCustomName(villager.getCustomName());
                                                    heretic.setCustomNameVisible(villager.isCustomNameVisible());
                                                }

                                                heretic.setPersistenceRequired();
                                                ServerLivingEntityEvents.MOB_CONVERSION.invoker().onConversion(villager, heretic, false);
                                                serverLevel.addFreshEntityWithPassengers(heretic);
                                                MobUtil.releaseAllPois(villager);
                                                villager.discard();
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    MiscCapHelper.setCustomFoodLevel(villager, Math.max(0, ((VillagerAccessor) villager).goety$getFoodLevel()));
                }
            }
            if (livingEntity instanceof WanderingTrader trader) {
                if (trader.level instanceof ServerLevel serverLevel) {
                    if (MobsConfig.TraderConvertMaverick.get() || MobsConfig.TraderConvertMaverickUnholy.get()) {
                        if (BlockFinder.getVerticalBlock(serverLevel, trader.blockPosition(), Blocks.CRYING_OBSIDIAN.defaultBlockState(), 16, true)) {
                            if (trader.getRandom().nextFloat() < 7.5E-4F && serverLevel.getDifficulty() != Difficulty.PEACEFUL) {
                                Player player = trader.level.getNearestPlayer(trader.getX(), trader.getY(), trader.getZ(), 16.0D, entity -> entity instanceof Player player1 && (CuriosFinder.hasUnholySet(player1) || ItemHelper.hasMaleficHelm(player1)));
                                if (player != null && (CuriosFinder.hasUnholySet(player) || ItemHelper.hasMaleficHelm(player)) && MobsConfig.TraderConvertMaverickUnholy.get()) {
                                    // if (ForgeEventFactory.canLivingConvert(trader, ModEntityType.MAVERICK_SERVANT, (timer) -> {
                                    // })) {
                                    serverLevel.explode(trader, trader.getX(), trader.getY(), trader.getZ(), 0.1F, Level.ExplosionInteraction.NONE);
                                    MaverickServant maverick = ModEntityType.MAVERICK_SERVANT.create(serverLevel);
                                    if (maverick != null) {
                                        maverick.moveTo(trader.getX(), trader.getY(), trader.getZ(), trader.getYRot(), trader.getXRot());
                                        maverick.setTrueOwner(player);
                                        maverick.finalizeSpawn(serverLevel, serverLevel.getCurrentDifficultyAt(maverick.blockPosition()), MobSpawnType.CONVERSION, (SpawnGroupData) null, (CompoundTag) null);
                                        maverick.setNoAi(trader.isNoAi());
                                        if (trader.hasCustomName()) {
                                            maverick.setCustomName(trader.getCustomName());
                                            maverick.setCustomNameVisible(trader.isCustomNameVisible());
                                        }

                                        maverick.setPersistenceRequired();
                                        ServerLivingEntityEvents.MOB_CONVERSION.invoker().onConversion(trader, maverick, false);
                                        serverLevel.addFreshEntityWithPassengers(maverick);
                                        trader.discard();
                                    }
                                    // }
                                } else if (MobsConfig.TraderConvertMaverick.get()) {
                                    // if (ForgeEventFactory.canLivingConvert(trader, ModEntityType.MAVERICK.get(), (timer) -> {
                                    // })) {
                                    serverLevel.explode(trader, trader.getX(), trader.getY(), trader.getZ(), 0.1F, Level.ExplosionInteraction.NONE);
                                    Maverick maverick = ModEntityType.MAVERICK.create(serverLevel);
                                    if (maverick != null) {
                                        maverick.moveTo(trader.getX(), trader.getY(), trader.getZ(), trader.getYRot(), trader.getXRot());
                                        maverick.finalizeSpawn(serverLevel, serverLevel.getCurrentDifficultyAt(maverick.blockPosition()), MobSpawnType.CONVERSION, (SpawnGroupData) null, (CompoundTag) null);
                                        maverick.setNoAi(trader.isNoAi());
                                        if (trader.hasCustomName()) {
                                            maverick.setCustomName(trader.getCustomName());
                                            maverick.setCustomNameVisible(trader.isCustomNameVisible());
                                        }

                                        maverick.setPersistenceRequired();
                                        ServerLivingEntityEvents.MOB_CONVERSION.invoker().onConversion(trader, maverick, false);
                                        serverLevel.addFreshEntityWithPassengers(maverick);
                                        trader.discard();
                                    }
                                    // }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public static void targetEvents(LivingChangeTargetEvent event) {
        LivingEntity attacker = event.getEntity();
        LivingEntity target = event.getOriginalTarget();
        if (attacker instanceof Mob mobAttacker) {
            if (target != null) {
                if (attacker instanceof IOwned) {
                    if (target instanceof ArmorStand || target instanceof HauntedArmorStand) {
                        if (event.getTargetType() == MOB_TARGET) {
                            event.setNewTarget(null);
                        } else {
                            event.setCanceled(true);
                        }
                    }
                }
                if ((mobAttacker.getMobType() == MobType.UNDEAD && !(mobAttacker instanceof IOwned) && mobAttacker.getMaxHealth() < 100.0F) || mobAttacker instanceof Creeper) {
                    if (event.getNewTarget() instanceof Apostle) {
                        event.setCanceled(true);
                    }
                }
                if (mobAttacker.getType().is(ModTags.EntityTypes.CREEPERS) && CuriosFinder.hasCurio(target, ModItems.FELINE_AMULET)) {
                    if (event.getTargetType() == MOB_TARGET) {
                        event.setNewTarget(null);
                    } else {
                        event.setCanceled(true);
                    }
                }
                if (mobAttacker instanceof Phantom && CuriosFinder.hasCurio(target, ModItems.FELINE_AMULET)) {
                    if (event.getTargetType() == MOB_TARGET) {
                        event.setNewTarget(null);
                    } else {
                        event.setCanceled(true);
                    }
                }
            }
        }
    }

    private static final String NO_KNOCKBACK_TAG = "goety:no_knockback";

    public static boolean attackEvent(LivingEntity victim, DamageSource damageSource, float amount) {
        Entity source = damageSource.getEntity();
        Entity direct = damageSource.getDirectEntity();
        boolean cancelled = false;
        if (!victim.level.isClientSide) {
            if (MiscCapHelper.getShields(victim) > 0
                    && !damageSource.is(DamageTypeTags.BYPASSES_EFFECTS)
                    && !damageSource.is(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
                if (MiscCapHelper.getShieldCool(victim) <= 0) {
                    MiscCapHelper.decreaseShields(victim);
                    if (SpellConfig.BulwarkShieldBreakExtra.get() > 0.0D) {
                        int extra = Mth.floor(amount / SpellConfig.BulwarkShieldBreakExtra.get());
                        if (extra >= 1) {
                            for (int i = 0; i < extra; ++i) {
                                MiscCapHelper.decreaseShields(victim);
                            }
                        }
                    }
                    MiscCapHelper.setShieldCool(victim, 10);
                    if (damageSource.getEntity() instanceof LivingEntity livingEntity) {
                        MobUtil.knockBack(livingEntity, victim, 1.0D, 0.2D, 1.0D);
                    }
                }
                cancelled = true;
            }
            if (MainConfig.GoodwillNoDamage.get()) {
                Player player = null;
                if (source instanceof Player player1) {
                    player = player1;
                } else if (MobUtil.getOwner(source) instanceof Player player1) {
                    player = player1;
                }
                if (player != null) {
                    if (SEHelper.isAlly(player, victim)) {
                        cancelled = true;
                    }
                } else if (source instanceof IOwned owned) {
                    if (owned.isAllyWith(victim)) {
                        cancelled = true;
                    }
                }
            }
            if (victim instanceof Witch witch) {
                double d0 = witch.getAttributeValue(Attributes.FOLLOW_RANGE);
                AABB axisalignedbb = AABB.unitCubeFromLowerCorner(witch.position()).inflate(d0, 10.0D, d0);
                List<Mob> list = witch.level.getEntitiesOfClass(Mob.class, axisalignedbb);

                for (Mob mob : list) {
                    if (mob.getTarget() == null && witch.getLastHurtByMob() != null && !(witch.getLastHurtByMob() instanceof Raider) && !MobUtil.areAllies(witch.getLastHurtByMob(), witch)) {
                        if (mob instanceof Cultist && !mob.getType().is(ConventionalEntityTypeTags.BOSSES) && EntitySelector.NO_CREATIVE_OR_SPECTATOR.test(witch.getLastHurtByMob())) {
                            mob.setTarget(witch.getLastHurtByMob());
                        }
                    }
                }
            }
        }

        if (damageSource instanceof NoKnockBackDamageSource) {
            if (!victim.level.isClientSide) {
                CompoundTag tag = ((IEntityPersistentData) victim).goety$getPersistentData();
                tag.putInt(NO_KNOCKBACK_TAG, victim.tickCount);
            }
        }

        if (source instanceof IOwned owned) {
            if (owned.getMasterOwner() instanceof Player player) {
                victim.lastHurtByPlayer = player;
                victim.lastHurtByPlayerTime = 100;
            }
        }

        if (direct instanceof AbstractArrow arrowEntity) {
            if (arrowEntity.getTags().contains(ConstantPaths.rainArrow()) || arrowEntity.getOwner() instanceof Apostle) {
                if (arrowEntity.getOwner() != null) {
                    if (victim instanceof IOwned ownedEntity) {
                        if (ownedEntity.getTrueOwner() != null) {
                            if (ownedEntity.getTrueOwner() == arrowEntity.getOwner()) {
                                cancelled = true;
                            }
                        }
                    }
                    if (victim == arrowEntity.getOwner()) {
                        cancelled = true;
                    }
                }
            }
            if (!(arrowEntity.getOwner() instanceof Apostle && victim.level.getDifficulty() == Difficulty.HARD)) {
                if (victim instanceof Player player) {
                    if (MobUtil.starAmuletActive(player)) {
                        cancelled = true;
                    }
                }
            }
        }
        return !cancelled;
    }

    public static void hurtEvent(LivingHurtEvent event) {
        LivingEntity victim = event.getEntity();
        if (ModDamageSource.shockAttacks(event.getSource())) {
            if (victim.level instanceof ServerLevel serverLevel) {
                ServerParticleUtil.addParticlesAroundSelf(serverLevel, ModParticleTypes.BIG_ELECTRIC, victim);
                ModNetwork.sendToALL(serverLevel.getServer(), SPlayWorldSoundPacket.ID, SPlayWorldSoundPacket.encode(victim.blockPosition(), ModSounds.ZAP, 2.0F, 1.0F));
            }
        }
        if (ModDamageSource.isMagicFire(event.getSource())) {
            float amount = event.getAmount();
            if (victim.fireImmune() && !victim.hasEffect(GoetyEffects.BURN_HEX)) {
                amount /= 2.0F;
            }
            int k = EnchantmentHelper.getDamageProtection(victim.getArmorSlots(), victim.damageSources().inFire());
            if (k > 0) {
                amount = CombatRules.getDamageAfterMagicAbsorb(amount, (float) k);
            }
            event.setAmount(amount);
        }
        if (ModDamageSource.hellfireAttacks(event.getSource())) {
            if (victim.level instanceof ServerLevel serverLevel) {
                ServerParticleUtil.addParticlesAroundSelf(serverLevel, ModParticleTypes.BIG_FIRE, victim);
                ModNetwork.sendToALL(serverLevel.getServer(), SPlayWorldSoundPacket.ID, SPlayWorldSoundPacket.encode(victim.blockPosition(), SoundEvents.PLAYER_HURT_ON_FIRE, 2.0F, 1.0F));
            }
            float amount = event.getAmount();
            if (MobsConfig.HellfireFireImmune.get()) {
                if (victim.fireImmune() && !victim.hasEffect(GoetyEffects.BURN_HEX)) {
                    amount /= 2.0F;
                }
            }
            if (MobsConfig.HellfireFireProtection.get()) {
                int k = 0;
                for (ItemStack itemStack : victim.getArmorSlots()) {
                    int i = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.FIRE_PROTECTION, itemStack);
                    if (i > 0) {
                        k += Enchantments.FIRE_PROTECTION.getDamageProtection(i, victim.damageSources().inFire());
                    }
                }
                if (k > 0) {
                    amount = CombatRules.getDamageAfterMagicAbsorb(amount, (float) k / 2.0F);
                }
            }
            event.setAmount(amount);
        }
        if (victim instanceof BlazeServant) {
            if (event.getSource().getDirectEntity() instanceof Snowball) {
                if (event.getSource().is(DamageTypes.THROWN)) {
                    if (event.getAmount() <= 0.0F) {
                        event.setAmount(3.0F);
                    }
                }
            }
        }
        if (victim instanceof Prisoner) {
            Entity entity = event.getSource().getEntity();
            if (entity instanceof Mob mob) {
                if (mob.getType().is(ModTags.EntityTypes.VILLAGE_GUARDS)) {
                    if (!event.getSource().isIndirect()) {
                        if (mob.getTarget() != victim) {
                            event.setCanceled(true);
                        }
                    }
                }
            }
        }
    }

    public static void damageEvent(LivingDamageEvent event) {
        LivingEntity target = event.getEntity();
        if (target instanceof Player player) {
            if (MobUtil.starAmuletActive(player)) {
                if (event.getSource().getDirectEntity() instanceof AbstractArrow arrow && !(arrow.getOwner() instanceof Apostle && target.level.getDifficulty() == Difficulty.HARD)) {
                    event.setCanceled(true);
                }
            }
        }

        if (event.getSource().getDirectEntity() instanceof Fangs fangEntity) {
            if (fangEntity.getOwner() instanceof Player player) {
                if (fangEntity.isAbsorbing()) {
                    player.heal(event.getAmount());
                }
            }
        }
        if (event.getAmount() > 0.0F) {
            float damageAmount = event.getAmount();
            if (event.getSource().is(ModDamageSource.LIFE_LEECH)
                    && event.getSource().getEntity() instanceof LivingEntity livingEntity) {
                float percent = SpellConfig.LeechingPercent.get() / 100.0F;
                livingEntity.heal(event.getAmount() * percent);
            }
            if (target.isInWaterOrRain()) {
                if (ModDamageSource.shockAttacks(event.getSource())) {
                    event.setAmount(damageAmount * 2.0F);
                }
            }
            if (ModDamageSource.freezeAttacks(event.getSource())) {
                if (target.getType().is(EntityTypeTags.FREEZE_IMMUNE_ENTITY_TYPES)) {
                    event.setAmount(damageAmount * 0.5F);
                }
            }
            if (ModDamageSource.waterAttacks(event.getSource())) {
                if (target.isSensitiveToWater()) {
                    event.setAmount(damageAmount * 2.0F);
                } else if (target.getMobType() == MobType.WATER) {
                    event.setAmount(damageAmount * 0.5F);
                }
            }
            float totalReduce = 0;
            for (EquipmentSlot equipmentSlot : EquipmentSlot.values()) {
                if (equipmentSlot.getType() == EquipmentSlot.Type.ARMOR) {
                    ItemStack itemStack = target.getItemBySlot(equipmentSlot);
                    if (itemStack.getItem() instanceof ArmorItem armorItem) {
                        if (armorItem.getMaterial() == ModArmorMaterials.BLACK_IRON
                                || armorItem.getMaterial() == ModArmorMaterials.DARK) {
                            float reducedDamage = getReducedDamage(event, armorItem);
                            totalReduce += reducedDamage;
                        }
                    }
                }
            }
            if (totalReduce > 0) {
                damageAmount -= totalReduce;
                damageAmount = Math.max(0, damageAmount);
                event.setAmount(damageAmount);
            }
            /*if (event.getSource().getEntity() instanceof Player attacker) {
                if (EnchantmentHelper.getEnchantmentLevel(ModEnchantments.SOUL_EATER, attacker) > 0) {
                    int level = EnchantmentHelper.getEnchantmentLevel(ModEnchantments.SOUL_EATER, attacker);
                    int percent = ((level - 1) * 5) + 15;
                    float rawPercent = (float) SEHelper.getSoulAmountInt(attacker) / MainConfig.MaxArcaSouls.get();
                    float totalPercent = rawPercent * percent;
                    if (attacker.level.getRandom().nextFloat() <= totalPercent){
                        event.setAmount(damageAmount * 2.0F);
                    }
                }
            }*/
        }
    }

    private static float getReducedDamage(LivingDamageEvent event, ArmorItem armorItem) {
        float reduction = 0;
        if (event.getSource().is(DamageTypeTags.WITCH_RESISTANT_TO)) {
            reduction = armorItem.getDefense() / 25.0F;
        } else if (event.getSource().is(DamageTypeTags.IS_FIRE) || event.getSource().is(DamageTypeTags.IS_EXPLOSION)) {
            reduction = armorItem.getDefense() / 10.0F;
        }
        return event.getAmount() * reduction;
    }

    public static void onLivingHeal(LivingHealEvent event) {
        if ((event.getEntity().hasEffect(GoetyEffects.CURSED) || ModDamageSource.hellfireAttacks(event.getEntity().getLastDamageSource())) && event.getAmount() > 0.0F) {
            event.setCanceled(true);
        }
    }

    public static void specialDeath(LivingEntity killed, DamageSource damageSource) {
        Entity killer = damageSource.getEntity();
        Level world = killed.getCommandSenderWorld();
        if (killed instanceof PathfinderMob) {
            if (killed.hasEffect(GoetyEffects.GOLD_TOUCHED)) {
                if (world.getGameRules().getBoolean(GameRules.RULE_DOMOBLOOT)) {
                    int amp = Objects.requireNonNull(killed.getEffect(GoetyEffects.GOLD_TOUCHED)).getAmplifier() + 1;
                    for (int i = 0; i < (killed.level.random.nextInt(3) + 1) * amp; ++i) {
                        killed.spawnAtLocation(new ItemStack(Items.GOLD_NUGGET));
                    }
                }
            }
        }
        if (world instanceof ServerLevel serverLevel) {
            if (killed instanceof AbstractIllager illager) {
                if (!illager.getType().getDescriptionId().contains("magispeller")
                        && !illager.getType().getDescriptionId().contains("faker")
                        && !illager.getType().getDescriptionId().contains("freakager")
                        && !illager.getType().getDescriptionId().contains("spiritcaller")) {
                    for (Apostle apostle : world.getEntitiesOfClass(Apostle.class, illager.getBoundingBox().inflate(32))) {
                        if (apostle.hasLineOfSight(illager)) {
                            Damned damned = new Damned(ModEntityType.DAMNED, world);
                            damned.moveTo(illager.blockPosition().below(2), apostle.getYHeadRot(), apostle.getXRot());
                            damned.setTrueOwner(apostle);
                            damned.finalizeSpawn(serverLevel, serverLevel.getCurrentDifficultyAt(illager.blockPosition().below()), MobSpawnType.MOB_SUMMONED, null, null);
                            if (illager.hasCustomName()) {
                                damned.setCustomName(illager.getCustomName());
                            }
                            damned.setHuman(false);
                            if (apostle.getTarget() != null) {
                                damned.setTarget(apostle.getTarget());
                            }
                            damned.setLimitedLife(100);
                            ServerParticleUtil.addParticlesAroundSelf(serverLevel, ModParticleTypes.BIG_FIRE, damned);
                            world.addFreshEntity(damned);
                        }
                    }
                }
            }
        }
        if (killer instanceof Player player) {
            if (world.getGameRules().getBoolean(GameRules.RULE_DOMOBLOOT)) {
                Entity entity = damageSource.getDirectEntity();
                if (entity instanceof Fangs) {
                    ItemStack ring = CuriosFinder.findRing(player);
                    if (ring.getItem() == ModItems.RING_OF_WANT) {
                        int enchantment = EnchantmentHelper.getItemEnchantmentLevel(ModEnchantments.WANTING, ring);
                        if (enchantment >= 3) {
                            if (world.random.nextFloat() <= (enchantment / 9.0F)) {
                                if (killed.getType() == EntityType.SKELETON) {
                                    killed.spawnAtLocation(new ItemStack(Items.SKELETON_SKULL));
                                }
                                if (killed.getType() == EntityType.ZOMBIE) {
                                    killed.spawnAtLocation(new ItemStack(Items.ZOMBIE_HEAD));
                                }
                                if (killed.getType() == EntityType.CREEPER) {
                                    killed.spawnAtLocation(new ItemStack(Items.CREEPER_HEAD));
                                }
                                if (killed.getType() == EntityType.WITHER_SKELETON) {
                                    killed.spawnAtLocation(new ItemStack(Items.WITHER_SKELETON_SKULL));
                                }
                                if (killed.getType() == EntityType.PIGLIN) {
                                    killed.spawnAtLocation(new ItemStack(Items.PIGLIN_HEAD));
                                }
                                if (MobsConfig.TallSkullDrops.get()) {
                                    if (killed instanceof Villager || killed instanceof AbstractIllager) {
                                        killed.spawnAtLocation(new ItemStack(ModBlocks.TALL_SKULL_ITEM));
                                    }
                                    if (killed instanceof Witch || (killed instanceof Cultist && killed.getType() != ModEntityType.APOSTLE)) {
                                        killed.spawnAtLocation(new ItemStack(ModBlocks.TALL_SKULL_ITEM));
                                    }
                                }
                            }
                            if (killed instanceof Player player1) {
                                CompoundTag tag = new CompoundTag();
                                tag.putString("SkullOwner", player1.getDisplayName().getString());
                                ItemStack head = new ItemStack(Items.PLAYER_HEAD);
                                head.setTag(tag);
                                killed.spawnAtLocation(head);
                            }
                        }
                    }
                }
                if (killed.getType() == EntityType.SPIDER) {
                    if (CuriosFinder.hasCurio(player, itemStack -> itemStack.getItem() instanceof WarlockGarmentItem)) {
                        if (world.random.nextFloat() <= 0.075F) {
                            for (int i = 0; i < (world.random.nextInt(2) + 1); ++i) {
                                killed.spawnAtLocation(new ItemStack(ModItems.SPIDER_EGG));
                            }
                        }
                    }
                }
            }
        }
        if (killer instanceof WitherNecromancer necromancer) {
            MobUtil.createWitherRose(killed, necromancer);
        }
/*        if (killer instanceof LivingEntity livingEntity){
            net.minecraft.network.chat.Component deathMessage = killed.getCombatTracker().getDeathMessage();
            livingEntity.sendSystemMessage(deathMessage);
        }*/
        MiscCapHelper.setFreezing(killed, 0);
        MiscCapHelper.setShields(killed, 0);
        MiscCapHelper.setShieldTime(killed, 0);
        MiscCapHelper.setShakeTime(killed, 0);
    }

    public static void experienceEvents(LivingExperienceDropEvent event) {
        Player player = event.getAttackingPlayer();
        int exp = event.getDroppedExperience();
        if (player != null) {
            if (CuriosFinder.hasCurio(player, ModItems.RING_OF_THIRST)) {
                int i = ItemHelper.repairPlayerItems(player, exp);
                if (i > 0) {
                    player.giveExperiencePoints(i);
                }
                event.setCanceled(true);
            }
        }
    }

    public static void spellLoot(LootingLevelEvent event) {
        if (event.getDamageSource() != null) {
            if (event.getEntity() != null) {
                if (!event.getEntity().level.isClientSide) {
                    int looting = 0;
                    Player player = null;
                    Entity owner = event.getDamageSource().getEntity();
                    Entity direct = event.getDamageSource().getDirectEntity();
                    if (owner instanceof Player player1) {
                        player = player1;
                    } else if (MobUtil.getOwner(owner) instanceof Player player1) {
                        player = player1;
                    } else if (event.getEntity().lastHurtByPlayer != null) {
                        player = event.getEntity().lastHurtByPlayer;
                    }
                    if (player != null) {
                        ItemStack ring = CuriosFinder.findRing(player);
                        if (ring.getItem() == ModItems.RING_OF_WANT) {
                            if (ring.isEnchanted()) {
                                looting = EnchantmentHelper.getItemEnchantmentLevel(ModEnchantments.WANTING, ring);
                            }
                        }
                        if (looting > event.getLootingLevel()) {
                            int looting2 = 0;
                            if (owner == null || MobUtil.getOwner(owner) == player) {
                                looting2 = looting;
                            } else if (direct != null) {
                                if (direct.getType().is(ModTags.EntityTypes.WANTING_ENTITIES)) {
                                    looting2 = looting;
                                } else if (MobUtil.getOwner(direct) == player) {
                                    looting2 = looting;
                                }
                            } else if (ModDamageSource.wantingAttacks(event.getDamageSource())) {
                                looting2 = looting;
                            }
                            event.setLootingLevel(looting2);
                        }
                    }
                }
            }
        }
    }

    public static void dropEvents(LivingDropsEvent event) {
        if (event.getEntity() != null) {
            LivingEntity living = event.getEntity();
            if (living instanceof Player player) {
                if (CuriosFinder.hasWitchSet(player)) {
                    if (living.level.getServer() != null) {
                        LootTable loottable = living.level.getServer().getLootData().getLootTable(ModLootTables.PLAYER_WITCH);
                        LootParams.Builder lootcontext$builder = MobUtil.createLootContext(event.getSource(), living);
                        LootParams ctx = lootcontext$builder.create(LootContextParamSets.ENTITY);
                        loottable.getRandomItems(ctx).forEach((loot) -> event.getDrops().add(ItemHelper.itemEntityDrop(living, loot)));
                    }
                }
                if (!living.level.isClientSide) {
                    if (player instanceof ServerPlayer serverPlayer) {
                        for (LivingEntity livingEntity : serverPlayer.level.getEntitiesOfClass(LivingEntity.class, serverPlayer.getBoundingBox().inflate(64.0D))) {
                            if (livingEntity instanceof GraveGolem graveGolem) {
                                if (graveGolem.getTrueOwner() == serverPlayer) {
                                    graveGolem.addDrops(event.getDrops());
                                    event.getDrops().clear();
                                    break;
                                }
                            }
                        }
                    }
                }
            }
            if (living instanceof SpellcasterIllager || living instanceof Witch || living instanceof Cultist) {
                if (living.level.getGameRules().getBoolean(GameRules.RULE_DOMOBLOOT)) {
                    if (living.getTags().contains(ConstantPaths.structureMob())) {
                        float chance = 0.025F;
                        chance += (float) event.getLootingLevel() / 100;
                        if (living.level.random.nextFloat() <= chance) {
                            event.getDrops().add(ItemHelper.itemEntityDrop(living, new ItemStack(ModItems.FORBIDDEN_FRAGMENT)));
                        }
                    }
                }
            }
            if (MobsConfig.TallSkullDrops.get()) {
                if (living.level.getGameRules().getBoolean(GameRules.RULE_DOMOBLOOT)) {
                    if (living instanceof AbstractVillager || living instanceof Prisoner || living instanceof AbstractIllager || living instanceof Witch || living instanceof Cultist) {
                        if (living.level.getServer() != null) {
                            LootTable loottable = living.level.getServer().getLootData().getLootTable(ModLootTables.TALL_SKULL);
                            LootParams.Builder lootcontext$builder = MobUtil.createLootContext(event.getSource(), living);
                            LootParams lootparams = lootcontext$builder.create(LootContextParamSets.ENTITY);
                            loottable.getRandomItems(lootparams).forEach((loot) -> event.getDrops().add(ItemHelper.itemEntityDrop(living, loot)));
                        }
                    }
                }
            }
        }
    }

    public static void knockBackEvents(LivingKnockBackEvent event) {
        LivingEntity knocked = event.getEntity();
        if (!knocked.level.isClientSide) {
            CompoundTag tag = ((IEntityPersistentData) knocked).goety$getPersistentData();
            if (tag.contains(NO_KNOCKBACK_TAG)) {
                int stampedTick = tag.getInt(NO_KNOCKBACK_TAG);
                if (knocked.tickCount - stampedTick <= 1) {
                    event.setCanceled(true);
                }
                tag.remove(NO_KNOCKBACK_TAG);
            }
        }
    }

    public static void addVillagerTrade() {
        ModTradeUtil.addVillagerTrades(VillagerProfession.CARTOGRAPHER, 3, new ModTradeUtil.TreasureMapForEmeralds(14, ModTags.Structures.CRYPT_EXPLORER, "filled_map.goety.crypt", MapDecoration.Type.MANSION, 12, 10));
    }

    public static void addWanderTrade() {
        TradeOfferHelper.registerWanderingTraderOffers(1, genericTrades -> {
            genericTrades.add(new ModTradeUtil.ItemsForEmeralds(ModItems.JADE, 1, 64, 16));
            genericTrades.add(new ModTradeUtil.ItemsForEmeralds(ModBlocks.WINDSWEPT_SAPLING, 5, 1, 8));
            genericTrades.add(new ModTradeUtil.ItemsForEmeralds(ModBlocks.PINE_SAPLING, 5, 1, 8));
        });

        TradeOfferHelper.registerWanderingTraderOffers(2, rareTrades -> {
            rareTrades.add(new ModTradeUtil.TreasureMapForEmeralds(8, ModTags.Structures.OMINOUS_BLACKSMITH, "filled_map.goety.ominous_blacksmith", MapDecoration.Type.TARGET_X, 12, 10));
            rareTrades.add(new ModTradeUtil.TreasureMapForEmeralds(8, ModTags.Structures.WIND_SHRINE, "filled_map.goety.wind_shrine", MapDecoration.Type.TARGET_X, 12, 10));
            rareTrades.add(new ModTradeUtil.TreasureMapForEmeralds(8, ModTags.Structures.BLIGHTED_SHACK, "filled_map.goety.blighted_shack", MapDecoration.Type.MANSION, 12, 10));
            rareTrades.add(new ModTradeUtil.TreasureMapForEmeralds(8, ModTags.Structures.RUINED_MONASTERY, "filled_map.goety.ruined_monastery", MapDecoration.Type.MANSION, 12, 10));
        });
    }

    public static void lightningStruckEvent(EntityStruckByLightningEvent event) {
        Entity entity = event.getEntity();
        Level level = entity.level;
        if (level instanceof ServerLevel serverLevel) {
            if (entity instanceof Mob mob) {
                if (mob.getType().is(ModTags.EntityTypes.FRAYED_CONVERT)) {
                    if (MobsConfig.ZombieConvertFrayed.get()) {
                        EntityType<?> entityType = ModEntityType.FRAYED;
                        boolean servant = mob instanceof OwnableEntity;
                        if (event.getLightning().getCause() != null) {
                            if (CuriosFinder.hasNamelessSet(event.getLightning().getCause())) {
                                servant = true;
                            }
                        }
                        if (servant) {
                            entityType = ModEntityType.FRAYED_SERVANT;
                        }
                        Entity newMob = MobUtil.convertTo(mob, entityType, true, null);
                        if (newMob != null) {
                            if (newMob instanceof IServant servant2) {
                                if (event.getLightning().getCause() != null && CuriosFinder.hasNamelessSet(event.getLightning().getCause())) {
                                    servant2.setTrueOwner(event.getLightning().getCause());
                                } else if (MobUtil.getOwner(mob) != null) {
                                    servant2.setTrueOwner(MobUtil.getOwner(mob));
                                }
                                if (mob instanceof IServant servant1) {
                                    servant2.copyStance(servant1);
                                    servant2.setHostile(servant1.isHostile());
                                    servant2.setNatural(servant1.isNatural());
                                }
                            }
                        }
                    }
                }
                if (mob.getType().is(ModTags.EntityTypes.RATTLED_CONVERT)) {
                    if (MobsConfig.SkeletonConvertRattled.get()) {
                        EntityType<?> entityType = ModEntityType.RATTLED;
                        boolean servant = mob instanceof OwnableEntity;
                        if (event.getLightning().getCause() != null) {
                            if (CuriosFinder.hasNamelessSet(event.getLightning().getCause())) {
                                servant = true;
                            }
                        }
                        if (servant) {
                            entityType = ModEntityType.RATTLED_SERVANT;
                        }
                        Entity newMob = MobUtil.convertTo(mob, entityType, true, null);
                        if (newMob != null) {
                            if (newMob instanceof IServant servant2) {
                                if (event.getLightning().getCause() != null && CuriosFinder.hasNamelessSet(event.getLightning().getCause())) {
                                    servant2.setTrueOwner(event.getLightning().getCause());
                                } else if (MobUtil.getOwner(mob) != null) {
                                    servant2.setTrueOwner(MobUtil.getOwner(mob));
                                }
                                if (mob instanceof IServant servant1) {
                                    servant2.copyStance(servant1);
                                    servant2.setHostile(servant1.isHostile());
                                    servant2.setNatural(servant1.isNatural());
                                }
                            }
                        }
                    }
                }
            }
            if (entity instanceof WanderingTrader trader) {
                boolean hasConverted = false;
                if (event.getLightning().getCause() != null) {
                    if (CuriosFinder.hasUnholySet(event.getLightning().getCause()) && MobsConfig.TraderConvertReprobateUnholy.get()) {
                        ReprobateServant reprobate = ModEntityType.REPROBATE_SERVANT.create(serverLevel);
                        if (reprobate != null) {
                            reprobate.moveTo(trader.getX(), trader.getY(), trader.getZ(), trader.getYRot(), trader.getXRot());
                            reprobate.setTrueOwner(event.getLightning().getCause());
                            reprobate.finalizeSpawn(serverLevel, serverLevel.getCurrentDifficultyAt(reprobate.blockPosition()), MobSpawnType.CONVERSION, (SpawnGroupData) null, (CompoundTag) null);
                            reprobate.setNoAi(trader.isNoAi());
                            if (trader.hasCustomName()) {
                                reprobate.setCustomName(trader.getCustomName());
                                reprobate.setCustomNameVisible(trader.isCustomNameVisible());
                            }

                            reprobate.setPersistenceRequired();
                            ServerLivingEntityEvents.MOB_CONVERSION.invoker().onConversion(trader, reprobate, false);
                            serverLevel.addFreshEntityWithPassengers(reprobate);
                            hasConverted = true;
                            trader.discard();
                        }
                    }
                }
                if (!hasConverted) {
                    if (MobsConfig.TraderConvertReprobate.get()) {
                        if (serverLevel.getDifficulty() != Difficulty.PEACEFUL /*&& EventHooks.canLivingConvert(trader, ModEntityType.MAVERICK, (timer) -> {
                        })*/) {
                            Reprobate reprobate = ModEntityType.REPROBATE.create(serverLevel);
                            if (reprobate != null) {
                                reprobate.moveTo(trader.getX(), trader.getY(), trader.getZ(), trader.getYRot(), trader.getXRot());
                                reprobate.finalizeSpawn(serverLevel, serverLevel.getCurrentDifficultyAt(reprobate.blockPosition()), MobSpawnType.CONVERSION, (SpawnGroupData) null, (CompoundTag) null);
                                reprobate.setNoAi(trader.isNoAi());
                                if (trader.hasCustomName()) {
                                    reprobate.setCustomName(trader.getCustomName());
                                    reprobate.setCustomNameVisible(trader.isCustomNameVisible());
                                }

                                reprobate.setPersistenceRequired();
                                ServerLivingEntityEvents.MOB_CONVERSION.invoker().onConversion(trader, reprobate, false);
                                serverLevel.addFreshEntityWithPassengers(reprobate);
                                trader.discard();
                            }
                        }
                    }
                }
            }
        }
    }

    public static boolean explosionStartEvent(Level world, Explosion explosion) {
//        if (explosion != null && !(explosion instanceof LootingExplosion)) {
//            if (explosion.getIndirectSourceEntity() instanceof Player player) {
//                if (CuriosFinder.hasWanting(player)) {
//                    ExplosionAccessor accessor = (ExplosionAccessor) explosion;
//                    ExplosionUtil.lootExplode(
//                            accessor.goety$getLevel(), explosion.getDirectSourceEntity(),
//                            accessor.goety$getX(), accessor.goety$getY(), accessor.goety$getZ(),
//                            accessor.goety$getRadius(), accessor.goety$isFire(), accessor.goety$getBlockInteraction(), LootingExplosion.Mode.LOOT);
//                    return true;
//                }
//            }
//        }

        return false;
    }

    public static void explosionDetonateEvent(Level world, Explosion explosion, List<Entity> entities, double diameter) {
        if (explosion != null) {
            entities.removeIf(entity -> (entity instanceof ItemEntity && ((ItemEntity) entity).getItem().getItem() == ModItems.UNHOLY_BLOOD));
            entities.removeIf(entity -> (entity instanceof ItemEntity && ((ItemEntity) entity).getItem().getItem() == ModBlocks.NIGHT_BEACON_ITEM));
        }
    }

    public static void projectileImpactEvent(ProjectileImpactEvent event) {
        if (event.getProjectile() instanceof AbstractArrow arrowEntity) {
            if (arrowEntity.getTags().contains(ConstantPaths.rainArrow())) {
                arrowEntity.discard();
            }
        }
    }

    public static InteractionResult sleepEvents(Player player, BlockPos sleepingPos, boolean vanillaResult) {
        if (player != null) {
            if (!player.isCreative()) {
                double d0 = 8.0D;
                double d1 = 5.0D;
                Vec3 vec3 = Vec3.atBottomCenterOf(sleepingPos);
                List<LivingEntity> list = player.level.getEntitiesOfClass(LivingEntity.class, new AABB(vec3.x() - d0, vec3.y() - d1, vec3.z() - d0, vec3.x() + d0, vec3.y() + d1, vec3.z() + d0), (p_9062_) -> {
                    return p_9062_ instanceof IOwned owned
                            && owned.preventsSleep(player);
                });
                if (!list.isEmpty()) {
                    // event.setResult(Player.BedSleepingProblem.NOT_SAFE);
                    return InteractionResult.FAIL;
                }
            }
        }
        return InteractionResult.PASS;
    }

    public static void furnaceBurnItems() {
        Set<ItemLike> set1 = Sets.newHashSet();
        set1.add(ModBlocks.ROTTEN_BOOKSHELF);
        set1.add(ModBlocks.WINDSWEPT_BOOKSHELF);
        set1.add(ModBlocks.PINE_BOOKSHELF);
        set1.addAll(ModBlocks.BLOCKS.stream().filter(block -> block instanceof ModChestBlock && block.defaultBlockState().ignitedByLava()).toList());
        set1.add(ModBlocks.COMPACTED_WINDSWEPT_PLANKS);
        set1.add(ModBlocks.COMPACTED_PINE_PLANKS);
        set1.add(ModBlocks.THATCHED_PINE_PLANKS);
        set1.add(ModBlocks.SKY_WOOD_PLANKS);
        set1.add(ModBlocks.OVERGROWN_ROOTS);
        addFuels(set1, 300);

        Set<ItemLike> set2 = Sets.newHashSet();
        set2.add(ModBlocks.WITCH_POLE);
        addFuels(set2, 200);

        Set<ItemLike> set3 = Sets.newHashSet();
        set3.add(ModBlocks.WINDSWEPT_DEAD_BUSH);
        addFuels(set3, 100);
    }

    private static void addFuels(Collection<ItemLike> items, int burnTime) {
        for (ItemLike item : items) {
            FuelRegistry.INSTANCE.add(item, burnTime);
        }
    }

    public static void onFocusBagUpgrade(Player player, ItemStack crafted) {
        if (!(crafted.getItem() instanceof FocusPack)) return;

        if (player.level().isClientSide) return;
        if (!(player.containerMenu instanceof CraftingMenu menu)) return;

        var packHandler = FocusBagItemHandler.get(crafted);
        if (packHandler == null) return;

        for (int i = 0; i < 9; i++) {
            ItemStack bagStack = menu.getSlot(i).getItem();
            if (!(bagStack.getItem() instanceof FocusBag) || bagStack.getItem() instanceof FocusPack) {
                continue;
            }

            var bagHandler = FocusBagItemHandler.get(bagStack);
            if (bagHandler == null) continue;

            for (int bagSlot = 1; bagSlot < bagHandler.getSlotCount(); bagSlot++) {
                ItemStack itemInBag = bagHandler.getStackInSlot(bagSlot);
                if (itemInBag.isEmpty()) continue;

                ItemStack toInsert = itemInBag.copy();

                ItemVariant resource = ItemVariant.of(toInsert);
                try (Transaction tx = Transaction.openOuter()) {
                    int before = toInsert.getCount();
                    int after = before - (int) packHandler.insert(resource, before, tx);
                    toInsert = after == 0 ? ItemStack.EMPTY : resource.toStack(after);
                    tx.commit();
                }

                if (!toInsert.isEmpty()) {
                    player.drop(toInsert, false);
                }
            }
        }
    }

    public static void onTeleport(EntityTeleportEvent event) {
        if (!(event instanceof EntityTeleportEvent.TeleportCommand) && !(event instanceof EntityTeleportEvent.SpreadPlayersCommand)) {
            if (event.getEntity() instanceof Player player) {
                CuriosFinder.dragonBlast(player, event.getPrev());
            }
        }
    }
}
