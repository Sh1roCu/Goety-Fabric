package com.Polarice3.Goety.client.events;

import cn.sh1rocu.goety.api.event.*;
import cn.sh1rocu.goety.api.extension.ILeftClickEntity;
import com.Polarice3.Goety.Goety;
import com.Polarice3.Goety.api.blocks.entities.IBarrack;
import com.Polarice3.Goety.api.blocks.entities.IOwnedBlock;
import com.Polarice3.Goety.api.blocks.entities.ITrainingBlock;
import com.Polarice3.Goety.api.blocks.entities.IWaystoneBlock;
import com.Polarice3.Goety.api.entities.IOwned;
import com.Polarice3.Goety.api.items.magic.IWand;
import com.Polarice3.Goety.api.magic.ISpell;
import com.Polarice3.Goety.client.audio.*;
import com.Polarice3.Goety.client.gui.screen.inventory.BrewRadialMenuScreen;
import com.Polarice3.Goety.client.gui.screen.inventory.FocusRadialMenuScreen;
import com.Polarice3.Goety.client.render.*;
import com.Polarice3.Goety.client.render.item.CustomItemsRenderer;
import com.Polarice3.Goety.client.render.model.LichModeModel;
import com.Polarice3.Goety.common.blocks.entities.ArcaBlockEntity;
import com.Polarice3.Goety.common.blocks.entities.BrewCauldronBlockEntity;
import com.Polarice3.Goety.common.blocks.entities.CursedCageBlockEntity;
import com.Polarice3.Goety.common.blocks.entities.OminousIdolBlockEntity;
import com.Polarice3.Goety.common.effects.GoetyEffects;
import com.Polarice3.Goety.common.entities.ally.GuardianServant;
import com.Polarice3.Goety.common.entities.ally.Leapleaf;
import com.Polarice3.Goety.common.entities.ally.golem.SquallGolem;
import com.Polarice3.Goety.common.entities.ally.illager.CrusherServant;
import com.Polarice3.Goety.common.entities.ally.illager.StormCasterServant;
import com.Polarice3.Goety.common.entities.ally.illager.WindCallerServant;
import com.Polarice3.Goety.common.entities.boss.Apostle;
import com.Polarice3.Goety.common.entities.boss.EnderKeeper;
import com.Polarice3.Goety.common.entities.boss.Vizier;
import com.Polarice3.Goety.common.entities.hostile.Wight;
import com.Polarice3.Goety.common.entities.hostile.ender.Endersent;
import com.Polarice3.Goety.common.entities.hostile.illagers.HostileRedstoneGolem;
import com.Polarice3.Goety.common.entities.hostile.illagers.HostileRedstoneMonstrosity;
import com.Polarice3.Goety.common.entities.hostile.illagers.StormCaster;
import com.Polarice3.Goety.common.entities.hostile.servants.Inferno;
import com.Polarice3.Goety.common.entities.neutral.ApostleShade;
import com.Polarice3.Goety.common.entities.neutral.CarrionFly;
import com.Polarice3.Goety.common.entities.neutral.InsectSwarm;
import com.Polarice3.Goety.common.entities.neutral.Wildfire;
import com.Polarice3.Goety.common.entities.projectiles.CorruptedBeam;
import com.Polarice3.Goety.common.entities.projectiles.IceStorm;
import com.Polarice3.Goety.common.entities.util.CameraShake;
import com.Polarice3.Goety.common.items.ModItems;
import com.Polarice3.Goety.common.items.WaystoneItem;
import com.Polarice3.Goety.common.items.curios.GloveItem;
import com.Polarice3.Goety.common.items.curios.TargetingMonocleItem;
import com.Polarice3.Goety.common.magic.spells.abyss.PrismaBeamSpell;
import com.Polarice3.Goety.common.magic.spells.abyss.WaterJetSpell;
import com.Polarice3.Goety.common.magic.spells.geomancy.BurrowingSpell;
import com.Polarice3.Goety.common.network.ModNetwork;
import com.Polarice3.Goety.common.network.client.*;
import com.Polarice3.Goety.common.network.client.brew.CBrewBagKeyPacket;
import com.Polarice3.Goety.config.ItemConfig;
import com.Polarice3.Goety.config.MainConfig;
import com.Polarice3.Goety.config.SpellConfig;
import com.Polarice3.Goety.init.ModKeybindings;
import com.Polarice3.Goety.init.ModSounds;
import com.Polarice3.Goety.init.ModTags;
import com.Polarice3.Goety.utils.*;
import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import dev.emi.trinkets.api.TrinketsApi;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.player.Input;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.core.BlockPos;
import net.minecraft.core.GlobalPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ServerboundInteractPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.packs.resources.CloseableResourceManager;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.lwjgl.glfw.GLFW;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Predicate;

@Environment(EnvType.CLIENT)
public class ClientEvents {

    public static void onEntityJoinWorld(Entity entity, ClientLevel world) {
        Minecraft minecraft = Minecraft.getInstance();
        SoundManager soundHandler = minecraft.getSoundManager();
        if (entity instanceof CorruptedBeam) {
            soundHandler.play(new LoopSound(ModSounds.CORRUPT_BEAM_LOOP, entity));
            soundHandler.play(new LoopSound(ModSounds.CORRUPT_BEAM_SOUL, entity));
        }
        if (entity instanceof ApostleShade) {
            soundHandler.play(new LoopSound(ModSounds.APOSTLE_SHADE, entity));
        }
        if (entity instanceof Inferno) {
            soundHandler.play(new LoopSound(ModSounds.INFERNO_LOOP, entity));
        }
        if (entity instanceof Wildfire) {
            soundHandler.play(new LoopSound(ModSounds.WILDFIRE_LOOP, entity));
        }
        if (entity instanceof WindCallerServant || entity instanceof StormCaster || entity instanceof StormCasterServant) {
            soundHandler.play(new LoopSound(ModSounds.FLIGHT, 1.0F, 1.3F, entity));
        }
        if (entity instanceof InsectSwarm) {
            soundHandler.play(new LoopSound(ModSounds.INSECT_SWARM, entity));
        }
        if (entity instanceof CarrionFly) {
            soundHandler.play(new LoopSound(ModSounds.FLY_LOOP, 0.4F, 2.0F, entity));
        }
        if (entity instanceof Wight wight && !wight.isHallucination()) {
            soundHandler.play(new WightLoopSound(wight));
        }
        if (entity instanceof IceStorm) {
            soundHandler.play(new LoopSound(ModSounds.ICE_STORM_LOOP, entity));
        }
    }

    /**
     * Ripped from @BobMowzie's codes:<a href="https://github.com/BobMowzie/MowziesMobs/blob/master/src/main/java/com/bobmowzie/mowziesmobs/client/ClientEventHandler.java#L211">...</a>
     */
    public static void onSetupCamera(ViewportEvent.ComputeCameraAngles event) {
        Player player = Minecraft.getInstance().player;
        float delta = Minecraft.getInstance().getFrameTime();
        if (player != null) {
            float ticksExistedDelta = player.tickCount + delta;
            if (MainConfig.CameraShake.get() && !Minecraft.getInstance().isPaused()) {
                float shakeAmplitude = 0;
                for (CameraShake cameraShake : player.level.getEntitiesOfClass(CameraShake.class, player.getBoundingBox().inflate(20))) {
                    if (cameraShake.distanceTo(player) < cameraShake.getRadius()) {
                        shakeAmplitude += cameraShake.getShakeAmount(player, delta);
                    }
                }
                if (shakeAmplitude > 1.0F) {
                    shakeAmplitude = 1.0F;
                }
                event.setPitch((float) (event.getPitch() + shakeAmplitude * Math.cos(ticksExistedDelta * 3.0D + 2.0D) * 25.0D));
                event.setYaw((float) (event.getYaw() + shakeAmplitude * Math.cos(ticksExistedDelta * 5.0D + 1.0D) * 25.0D));
                event.setRoll((float) (event.getRoll() + shakeAmplitude * Math.cos(ticksExistedDelta * 4.0D) * 25.0D));
            }
        }
    }

    public static float PARTIAL_TICK = 0;

    public static void renderTick(RenderTickEvent event) {
        if (event.phase == RenderTickEvent.Phase.START) {
            PARTIAL_TICK = event.renderTickTime;
        }
    }

    public static void onPlayerTick(PlayerTickEvent event) {
        Player player = event.getEntity();
        if (SEHelper.hasCamera(player)) {
            player.turn(0.0F, 0.0F);
            player.xxa = 0.0F;
            player.zza = 0.0F;
            player.setJumping(false);
        }
    }

    public static void onInputInteract(InputEvent.InteractionKeyMappingTriggered event) {
        AbstractClientPlayer player = Minecraft.getInstance().player;
        if (player != null) {
            if (SEHelper.hasCamera(player)) {
                if (event.isAttack() || event.isPickBlock() || event.isUseItem()) {
                    event.setCanceled(true);
                }
            }
        }
    }

    public static void onItemUse(LivingEntityUseItemEvent.Start event) {
        if (event.getEntity().level instanceof ClientLevel) {
            Minecraft minecraft = Minecraft.getInstance();
            SoundManager soundHandler = minecraft.getSoundManager();
            if (WandUtil.getSpell(event.getEntity()) != null && event.getItem().getItem() instanceof IWand) {
                ISpell spells = WandUtil.getSpell(event.getEntity());
                if (spells != null) {
                    if (spells.loopSound(event.getEntity()) != null) {
                        soundHandler.play(new ItemLoopSound(spells.loopSound(event.getEntity()), event.getEntity()));
                    } else if (spells instanceof PrismaBeamSpell) {
                        soundHandler.play(new GuardianLaserSound(event.getEntity()));
                    }
                }
            }
        }
    }

    public static void onEntityTick(LivingTickEvent event) {
        Entity entity = event.getEntity();
        if (entity.level instanceof ClientLevel) {
            Minecraft minecraft = Minecraft.getInstance();
            SoundManager soundHandler = minecraft.getSoundManager();
            if (entity instanceof SquallGolem squallGolem) {
                if (squallGolem.noveltyTick == 1) {
                    soundHandler.play(new SummonNoveltySound(squallGolem, ModSounds.SQUALL_GOLEM_ALERT));
                }
            }
            if (event.getEntity() instanceof Leapleaf leapleaf) {
                if (leapleaf.noveltyTick == 1) {
                    soundHandler.play(new SummonNoveltySound(leapleaf, ModSounds.LEAPLEAF_ALERT));
                }
            }
            if (event.getEntity() instanceof GuardianServant guardianServant) {
                if (guardianServant.playAttackSound) {
                    soundHandler.play(new GuardianAttackSound(guardianServant));
                    guardianServant.playAttackSound = false;
                }
            }
            if (MainConfig.BossMusic.get()) {
                if (entity instanceof LivingEntity livingEntity) {
                    if (entity instanceof Wight wight && !wight.isNoAi()) {
                        playPreBossMusic(ModSounds.ENDERMAN_THEME_PRE, ModSounds.ARENA_END, wight, 0.75F, 1.0F, 64, true);
                    }
                    if ((MiscCapHelper.getMobTarget(livingEntity) instanceof Player)
                            || (MiscCapHelper.getMobTarget(livingEntity) instanceof OwnableEntity ownable && ownable.getOwner() instanceof Player)
                            || entity.getType().is(ModTags.EntityTypes.GLOBAL_MUSIC_BOSS)) {
                        if (entity instanceof Apostle apostle && !apostle.isNoAi()) {
                            playBossMusic(ModSounds.APOSTLE_THEME, ModSounds.APOSTLE_THEME_POST, apostle);
                        }
                        if (entity instanceof Vizier vizier && !vizier.isNoAi()) {
                            playBossMusic(ModSounds.VIZIER_THEME, vizier);
                        }
                        if (entity instanceof HostileRedstoneMonstrosity rm && !rm.isNoAi()) {
                            playBossMusic(ModSounds.RM_THEME, ModSounds.BOSS_POST_2, rm, 0.75F, 1.0F);
                        }
                        if (entity instanceof EnderKeeper enderKeeper && !enderKeeper.isNoAi() && !enderKeeper.isIntro()) {
                            playBossMusic(ModSounds.ENDER_KEEPER_THEME, ModSounds.ENDER_KEEPER_THEME_POST, enderKeeper, 0.75F, 0.825F);
                        }
                        if (entity instanceof HostileRedstoneGolem rm && !rm.isNoAi()) {
                            playBossMusic(ModSounds.RG_THEME, ModSounds.BOSS_POST_2, rm, 0.75F, 1.0F);
                        }
                        if (entity instanceof Endersent endersent && !endersent.isNoAi()) {
                            playBossMusic(ModSounds.ENDERSENT_THEME, ModSounds.ARENA_END, endersent, 0.75F, 1.0F);
                        }
                        if (entity instanceof Wight wight && !wight.isNoAi()) {
                            playBossMusic(ModSounds.ENDERMAN_THEME, ModSounds.ARENA_END, wight, 0.75F, 1.0F);
                        }
                    }
                }
            }
        }
    }

    public static AbstractTickableSoundInstance PRE_BOSS_MUSIC;
    public static AbstractTickableSoundInstance BOSS_MUSIC;

    public static void playPreBossMusic(SoundEvent soundEvent, SoundEvent postBossMusic, Mob mob) {
        playPreBossMusic(soundEvent, postBossMusic, mob, 1.0F, 1.0F, 0);
    }

    public static void playPreBossMusic(SoundEvent soundEvent, Mob mob, int withinRange) {
        playPreBossMusic(soundEvent, ModSounds.BOSS_POST, mob, 1.0F, 1.0F, withinRange);
    }

    public static void playPreBossMusic(SoundEvent soundEvent, SoundEvent postBossMusic, Mob mob, int withinRange) {
        playPreBossMusic(soundEvent, postBossMusic, mob, 1.0F, 1.0F, withinRange);
    }

    public static void playPreBossMusic(SoundEvent soundEvent, SoundEvent postBossMusic, Mob mob, float volume, float pitch, int withinRange) {
        playPreBossMusic(soundEvent, postBossMusic, mob, volume, pitch, withinRange, false);
    }

    public static void playPreBossMusic(SoundEvent soundEvent, SoundEvent postBossMusic, Mob mob, float volume, float pitch, int withinRange, boolean mustSee) {
        if (MainConfig.BossMusic.get()) {
            Minecraft minecraft = Minecraft.getInstance();
            boolean flag = true;
            if (mustSee) {
                Player player = Goety.PROXY.getPlayer();
                if (player != null) {
                    if (!MobUtil.hasVisualLineOfSight(player, mob)) {
                        flag = false;
                    }
                }
            }
            if (soundEvent != null && mob.isAlive()) {
                if (PRE_BOSS_MUSIC == null && flag) {
                    PRE_BOSS_MUSIC = new PreBossLoopMusic(soundEvent, postBossMusic, mob, volume, pitch, withinRange, mustSee);
                }
            } else {
                PRE_BOSS_MUSIC = null;
            }
            if (PRE_BOSS_MUSIC != null && !minecraft.getSoundManager().isActive(PRE_BOSS_MUSIC)) {
                Minecraft.getInstance().getSoundManager().play(PRE_BOSS_MUSIC);
            }
        }
    }

    public static void playBossMusic(SoundEvent soundEvent, Mob mob) {
        playBossMusic(soundEvent, mob, 1.0F, 1.0F);
    }

    public static void playBossMusic(SoundEvent soundEvent, SoundEvent postBossMusic, Mob mob) {
        playBossMusic(soundEvent, postBossMusic, mob, 1.0F, 1.0F);
    }

    public static void playBossMusic(SoundEvent soundEvent, Mob mob, float volume, float pitch) {
        playBossMusic(soundEvent, ModSounds.BOSS_POST, mob, volume, pitch);
    }

    public static void playBossMusic(SoundEvent soundEvent, SoundEvent postBossMusic, Mob mob, float volume, float pitch) {
        if (MainConfig.BossMusic.get()) {
            Minecraft minecraft = Minecraft.getInstance();
            if (soundEvent != null && mob.isAlive()) {
                if (BOSS_MUSIC == null) {
                    BOSS_MUSIC = new BossLoopMusic(soundEvent, postBossMusic, mob, volume, pitch);
                }
            } else {
                BOSS_MUSIC = null;
            }
            if (BOSS_MUSIC != null && !minecraft.getSoundManager().isActive(BOSS_MUSIC)) {
                Minecraft.getInstance().getSoundManager().play(BOSS_MUSIC);
            }
        }
    }

    public static void renderGlove(RenderArmEvent event) {

        if (event.isCanceled() || !ItemConfig.FirstPersonGloves.get()) {
            return;
        }

        ItemStack slotResult = TrinketsApi.getTrinketComponent(event.getPlayer()).map(
                        inv -> {
                            var equipped = inv.getEquipped(itemStack -> itemStack.getItem() instanceof GloveItem);
                            if (equipped.isEmpty())
                                return ItemStack.EMPTY;
                            return equipped.get(0).getB();
                        })
                .orElse(ItemStack.EMPTY);
        if (!slotResult.isEmpty()) {
            WearRenderer renderer = WearRenderer.getRenderer(slotResult);
            if (renderer != null) {
                renderer.renderFirstPersonArm(event.getPoseStack(), event.getMultiBufferSource(), event.getPackedLight(), event.getPlayer(), event.getArm(), slotResult.hasFoil());
            }
        }
    }

    public static void renderArm(RenderArmEvent event) {
        final AbstractClientPlayer player = event.getPlayer();
        if (!player.isSpectator() && LichdomHelper.isInLichMode(player)) {
            PoseStack poseStack = event.getPoseStack();
            poseStack.pushPose();
            final int i = OverlayTexture.pack(OverlayTexture.u(0.0F), OverlayTexture.v(false));
            final ResourceLocation texture = Goety.location("textures/entity/lich.png");
            LichModeModel<?> lichModeModel = new LichModeModel<>(Minecraft.getInstance().getEntityModels().bakeLayer(ModModelLayer.LICH));
            if (event.getArm() == HumanoidArm.RIGHT) {
                lichModeModel.rightArm.render(poseStack, event.getMultiBufferSource().getBuffer(RenderType.entityTranslucent(texture)), event.getPackedLight(), i);
                event.setCanceled(true);
            } else if (event.getArm() == HumanoidArm.LEFT) {
                lichModeModel.leftArm.render(poseStack, event.getMultiBufferSource().getBuffer(RenderType.entityTranslucent(texture)), event.getPackedLight(), i);
                event.setCanceled(true);
            }
            poseStack.popPose();
        }
        if (event.getPlayer().hasEffect(GoetyEffects.SHADOW_WALK)) {
            if (event.getPlayer().getMainHandItem().isEmpty() && event.getArm() == event.getPlayer().getMainArm()) {
                event.setCanceled(true);
            } else if (event.getPlayer().getOffhandItem().isEmpty() && event.getArm() != event.getPlayer().getMainArm()) {
                event.setCanceled(true);
            }
        }
        if (SEHelper.hasCamera(event.getPlayer())) {
            event.setCanceled(true);
        }
    }

    public static void renderHand(RenderHandEvent event) {
        final AbstractClientPlayer player = Minecraft.getInstance().player;
        if (player != null) {
            if (SEHelper.hasCamera(player)) {
                event.setCanceled(true);
            }
        }
    }

    public static void onPlayerRenderPre(RenderPlayerEvent.Pre event) {
        final Player player = event.getEntity();
        if (player.hasEffect(GoetyEffects.SHADOW_WALK)) {
            event.setCanceled(true);
        }
        if (player.isInvisible() && CuriosFinder.hasIllusionRobe(player)) {
            event.setCanceled(true);
        }
    }

    public static void renderLichHUD(AtomicBoolean cancelled) {
        Minecraft minecraft = Minecraft.getInstance();
        final Player player = minecraft.player;

        if (LichdomHelper.isLich(player)) {
            cancelled.set(true);
        }
    }

    public static void renderArcaAmount(GuiGraphics guiGraphics) {
        Minecraft minecraft = Minecraft.getInstance();
        final Player player = minecraft.player;

        if (player != null) {
            HitResult hitResult = minecraft.hitResult;
            Font fontRenderer = minecraft.font;
            PoseStack poseStack = guiGraphics.pose();
            if (minecraft.level != null) {
                if (hitResult instanceof BlockHitResult blockRayTraceResult) {
                    BlockEntity blockEntity = minecraft.level.getBlockEntity(blockRayTraceResult.getBlockPos());
                    int width = minecraft.getWindow().getGuiScaledWidth();
                    int height = minecraft.getWindow().getGuiScaledHeight();
                    if (blockEntity instanceof ArcaBlockEntity arcaTile) {
                        if ((player.isShiftKeyDown() || player.isCrouching())) {
                            if (arcaTile.getPlayer() == player && SEHelper.getSEActive(player)) {
                                poseStack.pushPose();
                                poseStack.translate((float) (width / 2), (float) (height - 68), 0.0F);
                                RenderSystem.enableBlend();
                                RenderSystem.defaultBlendFunc();
                                int SoulEnergy = SEHelper.getSESouls(player);
                                int SoulEnergyTotal = MainConfig.MaxArcaSouls.get();
                                String s = Component.translatable("tooltip.goety.blockSoul").getString() + SoulEnergy + "/" + SoulEnergyTotal;
                                int l = fontRenderer.width(s);
                                guiGraphics.drawString(fontRenderer, s, (-l / 2), -4, 0xFFFFFF);
                                RenderSystem.disableBlend();
                                poseStack.popPose();
                            } else if (arcaTile.getPlayer() != null) {
                                poseStack.pushPose();
                                poseStack.translate((float) (width / 2), (float) (height - 60), 0.0F);
                                RenderSystem.enableBlend();
                                RenderSystem.defaultBlendFunc();
                                String s = Component.translatable("tooltip.goety.blockOwner").getString() + arcaTile.getPlayer().getDisplayName().getString();
                                int l = fontRenderer.width(s);
                                guiGraphics.drawString(fontRenderer, s, (-l / 2), -4, 0xFFFFFF);
                                RenderSystem.disableBlend();
                                poseStack.popPose();
                            }
                        }
                    } else if (blockEntity instanceof IOwnedBlock ownedBlock && ownedBlock.getPlayer() != null && ownedBlock.screenView()) {
                        Player owner = ownedBlock.getPlayer();
                        if (owner != null) {
                            if (blockEntity instanceof ITrainingBlock trainingBlock) {
                                poseStack.pushPose();
                                poseStack.translate((float) (width / 2), (float) (height - 58), 0.0F);
                                RenderSystem.enableBlend();
                                RenderSystem.defaultBlendFunc();
                                String s = Component.translatable("tooltip.goety.blockOwner").getString() + owner.getDisplayName().getString();
                                int l = fontRenderer.width(s);
                                guiGraphics.drawString(fontRenderer, s, (-l / 2), -4, 0xFFFFFF);
                                RenderSystem.disableBlend();
                                poseStack.popPose();

                                if (owner == player) {
                                    if (trainingBlock.reachedLimit()) {
                                        poseStack.pushPose();
                                        poseStack.translate((float) (width / 2), (float) (height - 116), 0.0F);
                                        RenderSystem.enableBlend();
                                        RenderSystem.defaultBlendFunc();
                                        String s0 = Component.translatable("info.goety.summon.limit").getString();
                                        int l0 = fontRenderer.width(s0);
                                        guiGraphics.drawString(fontRenderer, s0, (-l0 / 2), -4, 0xFFFFFF);
                                        RenderSystem.disableBlend();
                                        poseStack.popPose();
                                    }
                                    poseStack.pushPose();
                                    poseStack.translate((float) (width / 2), (float) (height - 100), 0.0F);
                                    RenderSystem.enableBlend();
                                    RenderSystem.defaultBlendFunc();
                                    String mode = Component.translatable("tooltip.goety.blockGuard").getString();
                                    if (!trainingBlock.isGuarding()) {
                                        mode = Component.translatable("tooltip.goety.blockFollow").getString();
                                    }
                                    int length = fontRenderer.width(mode);
                                    guiGraphics.drawString(fontRenderer, mode, (-length / 2), -4, 0xFFFFFF);
                                    RenderSystem.disableBlend();
                                    poseStack.popPose();
                                    if (trainingBlock.isSensorSensitive()) {
                                        poseStack.pushPose();
                                        poseStack.translate((float) (width / 2), (float) (height - 90), 0.0F);
                                        RenderSystem.enableBlend();
                                        RenderSystem.defaultBlendFunc();
                                        String s0 = Component.translatable("tooltip.goety.blockSense").getString();
                                        int l0 = fontRenderer.width(s0);
                                        guiGraphics.drawString(fontRenderer, s0, (-l0 / 2), -4, 0xFFFFFF);
                                        RenderSystem.disableBlend();
                                        poseStack.popPose();
                                    }
                                    if (trainingBlock.isGrounding()) {
                                        poseStack.pushPose();
                                        poseStack.translate((float) (width / 2), (float) (height - 46), 0.0F);
                                        RenderSystem.enableBlend();
                                        RenderSystem.defaultBlendFunc();
                                        String s0 = Component.translatable("tooltip.goety.blockGrounded").getString();
                                        int l0 = fontRenderer.width(s0);
                                        guiGraphics.drawString(fontRenderer, s0, (-l0 / 2), -4, 0xFFFFFF);
                                        RenderSystem.disableBlend();
                                        poseStack.popPose();
                                    }

                                    poseStack.pushPose();
                                    poseStack.translate((float) (width / 2), (float) (height - 68), 0.0F);
                                    RenderSystem.enableBlend();
                                    RenderSystem.defaultBlendFunc();
                                    String s1 = Component.translatable("tooltip.goety.blockTrain").getString() + trainingBlock.amountTrainLeft() + "/" + trainingBlock.maxTrainAmount() + " " + trainingBlock.getTrainMob().getDescription().getString();
                                    int l1 = fontRenderer.width(s1);
                                    guiGraphics.drawString(fontRenderer, s1, (-l1 / 2), -4, 0xFFFFFF);
                                    RenderSystem.disableBlend();
                                    poseStack.popPose();

                                    poseStack.pushPose();
                                    int train = 64;
                                    train *= ((double) trainingBlock.getTrainingTime() / trainingBlock.getMaxTrainTime());
                                    guiGraphics.blit(Goety.location("textures/gui/train_bar.png"), ((width - 64) / 2), (height - 86), 0, 0, 64, 16, 64, 32);
                                    guiGraphics.blit(Goety.location("textures/gui/train_bar.png"), ((width - 64) / 2), (height - 86), 0, 16, train, 16, 64, 32);
                                    poseStack.popPose();
                                }
                            } else if (blockEntity instanceof IBarrack barrack) {
                                poseStack.pushPose();
                                poseStack.translate((float) (width / 2), (float) (height - 58), 0.0F);
                                RenderSystem.enableBlend();
                                RenderSystem.defaultBlendFunc();
                                String s = Component.translatable("tooltip.goety.blockOwner").getString() + owner.getDisplayName().getString();
                                int l = fontRenderer.width(s);
                                guiGraphics.drawString(fontRenderer, s, (-l / 2), -4, 0xFFFFFF);
                                RenderSystem.disableBlend();
                                poseStack.popPose();

                                poseStack.pushPose();
                                poseStack.translate((float) (width / 2), (float) (height - 68), 0.0F);
                                RenderSystem.enableBlend();
                                RenderSystem.defaultBlendFunc();
                                String s1 = Component.translatable("tooltip.goety.blockTrainType").getString() + Component.translatable(barrack.getCurrentMob()).getString();
                                int l1 = fontRenderer.width(s1);
                                guiGraphics.drawString(fontRenderer, s1, (-l1 / 2), -4, 0xFFFFFF);
                                RenderSystem.disableBlend();
                                poseStack.popPose();

                                poseStack.pushPose();
                                poseStack.translate((float) (width / 2), (float) (height - 78), 0.0F);
                                RenderSystem.enableBlend();
                                RenderSystem.defaultBlendFunc();
                                String s2 = Component.translatable("tooltip.goety.brew.capacity").getString() + barrack.getCurrentAmount() + "/" + barrack.trainLimit();
                                int l2 = fontRenderer.width(s2);
                                guiGraphics.drawString(fontRenderer, s2, (-l2 / 2), -4, 0xFFFFFF);
                                RenderSystem.disableBlend();
                                poseStack.popPose();
                            } else if (blockEntity instanceof OminousIdolBlockEntity idol) {
                                poseStack.pushPose();
                                poseStack.translate((float) (width / 2), (float) (height - 58), 0.0F);
                                RenderSystem.enableBlend();
                                RenderSystem.defaultBlendFunc();
                                String s = Component.translatable("tooltip.goety.blockOwner").getString() + owner.getDisplayName().getString();
                                int l = fontRenderer.width(s);
                                guiGraphics.drawString(fontRenderer, s, (-l / 2), -4, 0xFFFFFF);
                                RenderSystem.disableBlend();
                                poseStack.popPose();

                                poseStack.pushPose();
                                poseStack.translate((float) (width / 2), (float) (height - 68), 0.0F);
                                RenderSystem.enableBlend();
                                RenderSystem.defaultBlendFunc();
                                String s2 = Component.translatable("tooltip.goety.idol.count").getString() + idol.getClientCount() + "/" + MainConfig.OminousIdolLimit.get();
                                int l2 = fontRenderer.width(s2);
                                guiGraphics.drawString(fontRenderer, s2, (-l2 / 2), -4, 0xFFFFFF);
                                RenderSystem.disableBlend();
                                poseStack.popPose();
                            } else if ((player.isShiftKeyDown() || player.isCrouching()) && ownedBlock.getPlayer() != null) {
                                poseStack.pushPose();
                                poseStack.translate((float) (width / 2), (float) (height - 68), 0.0F);
                                RenderSystem.enableBlend();
                                RenderSystem.defaultBlendFunc();
                                String s = Component.translatable("tooltip.goety.blockOwner").getString() + owner.getDisplayName().getString();
                                int l = fontRenderer.width(s);
                                guiGraphics.drawString(fontRenderer, s, (-l / 2), -4, 0xFFFFFF);
                                RenderSystem.disableBlend();
                                poseStack.popPose();
                            }
                        }
                    } else if (blockEntity instanceof CursedCageBlockEntity cageBlockEntity) {
                        if (player.isShiftKeyDown() || player.isCrouching() && !cageBlockEntity.getItem().isEmpty()) {
                            poseStack.pushPose();
                            poseStack.translate((float) (width / 2), (float) (height - 68), 0.0F);
                            RenderSystem.enableBlend();
                            RenderSystem.defaultBlendFunc();
                            String s = Component.translatable("tooltip.goety.blockSoul").getString() + cageBlockEntity.getSouls();
                            int l = fontRenderer.width(s);
                            guiGraphics.drawString(fontRenderer, s, (-l / 2), -4, 0xFFFFFF);
                            RenderSystem.disableBlend();
                            poseStack.popPose();
                        }
                    } else if (blockEntity instanceof BrewCauldronBlockEntity cauldronBlock) {
                        if (player.isShiftKeyDown() || player.isCrouching()) {
                            poseStack.pushPose();
                            poseStack.translate((float) (width / 2), (float) (height - 60), 0.0F);
                            RenderSystem.enableBlend();
                            RenderSystem.defaultBlendFunc();
                            String s1 = Component.translatable("tooltip.goety.brew.capacity").getString() + cauldronBlock.getCapacityUsed() + "/" + cauldronBlock.getCapacity();
                            int l2 = fontRenderer.width(s1);
                            guiGraphics.drawString(fontRenderer, s1, (-l2 / 2), -4, 0xFFFFFF);
                            RenderSystem.disableBlend();
                            poseStack.popPose();
                            poseStack.pushPose();
                            poseStack.translate((float) (width / 2), (float) (height - 68), 0.0F);
                            RenderSystem.enableBlend();
                            RenderSystem.defaultBlendFunc();
                            String s = Component.translatable("tooltip.goety.blockSoulCost").getString() + (cauldronBlock.getBrewCost() - cauldronBlock.soulTime);
                            int l = fontRenderer.width(s);
                            guiGraphics.drawString(fontRenderer, s, (-l / 2), -4, 0xFFFFFF);
                            RenderSystem.disableBlend();
                            poseStack.popPose();
                        }
                    } else if (blockEntity instanceof IWaystoneBlock waystoneBlock) {
                        GlobalPos globalPos = waystoneBlock.getPosition();
                        if ((player.isShiftKeyDown() || player.isCrouching()) && globalPos != null) {
                            poseStack.pushPose();
                            poseStack.translate((float) (width / 2), (float) (height - 60), 0.0F);
                            RenderSystem.enableBlend();
                            RenderSystem.defaultBlendFunc();
                            BlockPos blockPos = globalPos.pos();
                            String s1 = Component.translatable("tooltip.goety.arcaCoords", blockPos.getX(), blockPos.getY(), blockPos.getZ()).getString();
                            int l2 = fontRenderer.width(s1);
                            guiGraphics.drawString(fontRenderer, s1, (-l2 / 2), -4, 0xFFFFFF);
                            RenderSystem.disableBlend();
                            poseStack.popPose();
                            poseStack.pushPose();
                            poseStack.translate((float) (width / 2), (float) (height - 68), 0.0F);
                            RenderSystem.enableBlend();
                            RenderSystem.defaultBlendFunc();
                            String s = Component.translatable("tooltip.goety.arcaDimension", globalPos.dimension().location().toString()).getString();
                            int l = fontRenderer.width(s);
                            guiGraphics.drawString(fontRenderer, s, (-l / 2), -4, 0xFFFFFF);
                            RenderSystem.disableBlend();
                            poseStack.popPose();
                            if (waystoneBlock.getSoulCost() > 0) {
                                poseStack.pushPose();
                                poseStack.translate((float) (width / 2), (float) (height - 76), 0.0F);
                                RenderSystem.enableBlend();
                                RenderSystem.defaultBlendFunc();
                                String s0 = Component.translatable("tooltip.goety.blockSoulCost").getString() + waystoneBlock.getSoulCost();
                                int l0 = fontRenderer.width(s0);
                                guiGraphics.drawString(fontRenderer, s0, (-l0 / 2), -4, 0xFFFFFF);
                                RenderSystem.disableBlend();
                                poseStack.popPose();
                            }
                        }
                    }
                }
            }
        }
    }

    public static void renderWorldLast(WorldRenderContext context) {
        Minecraft minecraft = Minecraft.getInstance();
        Player player = minecraft.player;
        Level level = minecraft.level;
        if (level != null) {
            List<AbstractClientPlayer> players = minecraft.level.players();
            if (player != null) {
                Level world = player.level;
                ItemStack stack = player.getMainHandItem();
                Map<BlockPos, ColorUtil> renderCubes = new HashMap<>();
                if (stack.getItem() instanceof WaystoneItem) {
                    if (stack.getTag() != null) {
                        GlobalPos loc = WaystoneItem.getPosition(stack);
                        if (loc != null) {
                            if (loc.dimension() == world.dimension()) {
                                renderCubes.put(loc.pos(), new ColorUtil(ChatFormatting.GOLD));
                            }
                        }
                    }
                }
                if (!renderCubes.isEmpty()) {
                    PoseStack matrix = context.matrixStack();
                    Vec3 view = Minecraft.getInstance().gameRenderer.getMainCamera().getPosition();
                    RenderBlockUtils.renderColourCubes(matrix, view, renderCubes, 1.0F, 1.0F);
                }
                for (Player player1 : players) {
                    if (player1.distanceToSqr(player) > 500.0F) {
                        continue;
                    }

                    if (player1.isUsingItem()) {
                        if (WandUtil.getSpell(player1) instanceof BurrowingSpell) {
                            BurrowingLaserRenderer.renderLaser(context, player1, Minecraft.getInstance().getFrameTime());
                        } else if (WandUtil.getSpell(player1) instanceof PrismaBeamSpell) {
                            PrismaBeamRenderer.renderLaser(context, player1, Minecraft.getInstance().getFrameTime());
                        } else if (WandUtil.getSpell(player1) instanceof WaterJetSpell) {
                            WaterJetRenderer.renderWaterJet(context, player1, Minecraft.getInstance().getFrameTime());
                        }
                    }
                }
            }
        }
    }

    public static void renderHealthBarPre(GuiGraphics guiGraphics, AtomicBoolean cancelled) {
        Minecraft minecraft = Minecraft.getInstance();
        Player player = minecraft.player;
        if (player == null) {
            return;
        }

        if (!minecraft.options.hideGui && (player.hasEffect(GoetyEffects.SPASMS)
                || player.hasEffect(GoetyEffects.CURSED))
                || player.hasEffect(GoetyEffects.ACID_VENOM)
                || player.hasEffect(GoetyEffects.NECROSIS)) {
            setHearts(guiGraphics, cancelled);
        }

    }

    private static final ResourceLocation CUSTOM_HEARTS = Goety.location("textures/gui/custom_hearts.png");

    private static int lastHealth;
    private static int displayHealth;
    private static long lastHealthTime;
    private static long healthBlinkTime;

    private static void setHearts(GuiGraphics stack, AtomicBoolean cancelled) {
        Minecraft mc = Minecraft.getInstance();
        Player player = mc.player;
        if (player == null) {
            return;
        }
        Gui gui = mc.gui;
        int width = stack.guiWidth();
        int height = stack.guiHeight();
        cancelled.set(true);
        RenderSystem.setShaderTexture(0, CUSTOM_HEARTS);
        RenderSystem.enableBlend();
        int health = Mth.ceil(player.getHealth());
        int tickCount = gui.getGuiTicks();
        boolean highlight = healthBlinkTime > (long) tickCount && (healthBlinkTime - (long) tickCount) / 3L % 2L == 1L;
        if (health < lastHealth && player.invulnerableTime > 0) {
            lastHealthTime = Util.getMillis();
            healthBlinkTime = tickCount + 20;
        } else if (health > lastHealth && player.invulnerableTime > 0) {
            lastHealthTime = Util.getMillis();
            healthBlinkTime = tickCount + 10;
        }

        if (Util.getMillis() - lastHealthTime > 1000L) {
            lastHealth = health;
            displayHealth = health;
            lastHealthTime = Util.getMillis();
        }

        lastHealth = health;
        int healthLast = displayHealth;
        float healthMax = (float) player.getAttributeValue(Attributes.MAX_HEALTH);
        int absorption = Mth.ceil(player.getAbsorptionAmount());
        int healthRows = Mth.ceil((healthMax + (float) absorption) / 2.0F / 10.0F);
        int rowHeight = Math.max(10 - (healthRows - 2), 3);
        Random random = new Random();
        random.setSeed((long) tickCount * 312871L);
        int left = width / 2 - 91;
        int top = height - 39;
//        int top = height - leftHeight;
//        gui.leftHeight += healthRows * rowHeight;
//        if (rowHeight != 10) {
//            gui.leftHeight += 10 - rowHeight;
//        }

        int regen = -1;
        if (player.hasEffect(MobEffects.REGENERATION)) {
            regen = tickCount % Mth.ceil(healthMax + 5.0F);
        }

        int TOP = player.level().getLevelData().isHardcore() ? 9 : 0;
        if (highlight) {
            TOP = player.level().getLevelData().isHardcore() ? 27 : 18;
        }
        int BACKGROUND = highlight ? 25 : 16;
        int heartX = 0;
        if (player.hasEffect(GoetyEffects.CURSED)) {
            heartX = 52;
        } else if (player.hasEffect(GoetyEffects.ACID_VENOM)) {
            heartX = 70;
        } else if (player.hasEffect(GoetyEffects.SPASMS)) {
            heartX = 34;
        } else if (player.hasEffect(GoetyEffects.NECROSIS)) {
            heartX = 88;
        }
        float absorptionRemaining = (float) absorption;

        for (int i = Mth.ceil((healthMax + (float) absorption) / 2.0F) - 1; i >= 0; --i) {
            int row = Mth.ceil((float) (i + 1) / 10.0F) - 1;
            int x = left + i % 10 * 8;
            int y = top - row * rowHeight;
            if (health <= 4) {
                y += random.nextInt(2);
            }

            if (i == regen) {
                y -= 2;
            }

            stack.blit(CUSTOM_HEARTS, x, y, BACKGROUND, 0, 9, 9);
            if (highlight) {
                if (i * 2 + 1 < healthLast) {
                    stack.blit(CUSTOM_HEARTS, x, y, heartX, TOP, 9, 9);
                } else if (i * 2 + 1 == healthLast) {
                    stack.blit(CUSTOM_HEARTS, x, y, heartX + 9, TOP, 9, 9);
                }
            }

            if (absorptionRemaining > 0.0F) {
                if (absorptionRemaining == (float) absorption && (float) absorption % 2.0F == 1.0F) {
                    stack.blit(CUSTOM_HEARTS, x, y, heartX + 9, TOP, 9, 9);
                    --absorptionRemaining;
                } else {
                    stack.blit(CUSTOM_HEARTS, x, y, heartX, TOP, 9, 9);
                    absorptionRemaining -= 2.0F;
                }
            } else if (i * 2 + 1 < health) {
                stack.blit(CUSTOM_HEARTS, x, y, heartX, TOP, 9, 9);
            } else if (i * 2 + 1 == health) {
                stack.blit(CUSTOM_HEARTS, x, y, heartX + 9, TOP, 9, 9);
            }
        }

        RenderSystem.disableBlend();
        RenderSystem.setShaderTexture(0, CUSTOM_HEARTS);
    }

    private static boolean prevJumpBindState = false;

    public static void tickEvents(boolean isStart) {
        if (isStart) {
            CustomItemsRenderer.incrementTick();
        } else {
            Minecraft minecraft = Minecraft.getInstance();

            if (minecraft.player != null) {
                Player player = minecraft.player;
                Wight wight = Wight.findWight(player);
                if (wight != null) {
                    if (MobUtil.isPlayerLookingTowards(player, minecraft.options.fov().get().floatValue(), wight) && MobUtil.hasVisualLineOfSight(player, wight)) {
                        wight.lookTime += 1;

                        if (wight.lookTime >= MathHelper.secondsToTicks(3)) {
                            if (wight.lookTime % 20 == 0 && wight.getRandom().nextInt(8) == 0) {
                                wight.lookTime = 0;
                                ModNetwork.sendToServer(CTargetPlayerPacket.ID, CTargetPlayerPacket.encode(wight));
                            }
                        }
                    } else {
                        if (wight.lookTime > 0) {
                            wight.lookTime -= 1;
                        }
                    }
                }
                if (minecraft.options.keyJump.isDown() && !prevJumpBindState && !player.isInWater() && SEHelper.getTicksInAir(player) > 2 && !player.isCreative() && !player.isSpectator() && !player.isPassenger()) {
                    ModNetwork.sendToServer(CMultiJumpPacket.ID, CMultiJumpPacket.DUMMY);
                    SEHelper.doubleJump(player);
                }
                prevJumpBindState = minecraft.options.keyJump.isDown();
            }
        }
    }

    /**
     * From here, code is stolen from @Tfarcenim LockOnHandler codes: <a href="https://github.com/Tfarcenim/LockOn/blob/1.20.1/src/main/java/tfar/lockon/LockOnHandler.java">...</a>
     */
    public static boolean lockedOn;
    public static Entity target;
    public static List<LivingEntity> targetList = new ArrayList<>();

    public static void targetMonocleEvents(Minecraft minecraft) {
        boolean leave = false;
        if (minecraft.player != null) {
            if (CuriosFinder.hasCurio(minecraft.player, ModItems.TARGETING_MONOCLE)) {
                ItemStack itemStack = CuriosFinder.findCurio(minecraft.player, ModItems.TARGETING_MONOCLE);
                if (TargetingMonocleItem.isActive(itemStack)) {
                    if (!lockedOn) {
                        attemptEnterLockOn(Minecraft.getInstance().player);
                    }
                    if (!minecraft.player.isCrouching()) {
                        if (ModKeybindings.useCurios() != null) {
                            while (ModKeybindings.useCurios().consumeClick()) {
                                tabToNextEnemy(Minecraft.getInstance().player);
                            }
                        }
                    }
                } else {
                    leave = lockedOn;
                }
            } else {
                leave = lockedOn;
            }
        } else {
            leave = lockedOn;
        }
        if (leave) {
            leaveLockOn();
        }
        tickLockedOn();
    }

    public static boolean handleKeyPress(Player player) {
        Minecraft minecraft = Minecraft.getInstance();
        if (player != null && !minecraft.isPaused()) {
            if (target != null) {
                Vec3 targetPos = target.position().add(0, target.getBbHeight() / 2.0D, 0);
                Vec3 targetVec = targetPos.subtract(player.position().add(0, player.getEyeHeight(), 0)).normalize();
                double targetAngleX = Mth.wrapDegrees(Math.atan2(-targetVec.x, targetVec.z) * 180 / Math.PI);
                double targetAngleY = Math.atan2(targetVec.y, targetVec.horizontalDistance()) * 180 / Math.PI;
                double xRot = Mth.wrapDegrees(player.getXRot());
                double yRot = Mth.wrapDegrees(player.getYRot());
                double toTurnX = Mth.wrapDegrees(yRot - targetAngleX);
                double toTurnY = Mth.wrapDegrees(xRot + targetAngleY);

                player.turn(-toTurnX, -toTurnY);
                return true;
            }
        }
        return false;
    }

    public static void logOff(ClientPacketListener handler, Minecraft client) {
        leaveLockOn();
    }

    public static void onDying(LivingEntity entity, DamageSource damageSource) {
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.player != null) {
            if (entity == minecraft.player) {
                leaveLockOn();
            }
        }
    }

    private static void attemptEnterLockOn(Player player) {
        tabToNextEnemy(player);
        if (target != null) {
            lockedOn = true;
        }
    }

    private static void tickLockedOn() {
        targetList.removeIf(livingEntity -> !livingEntity.isAlive());
        if (target != null) {
            if (!target.isAlive()) {
                target = null;
                lockedOn = false;
            }
        }
    }

    private static final Predicate<LivingEntity> ENTITY_PREDICATE = entity -> entity.isAlive() && entity.attackable() && !isFriendly(entity);

    private static boolean isFriendly(LivingEntity entity) {
        Player player = Minecraft.getInstance().player;
        if (player != null) {
            return MobUtil.areAllies(player, entity);
        }

        return false;
    }

    private static int cycle = -1;

    public static Entity findNearby(Player player) {
        int range = 16;
        final TargetingConditions selector = TargetingConditions.forCombat().range(range).selector(ENTITY_PREDICATE);
        List<LivingEntity> entities = player.level().getNearbyEntities(LivingEntity.class, selector, player, player.getBoundingBox().inflate(range)).stream().filter(player::hasLineOfSight).toList();
        if (lockedOn) {
            cycle++;
            for (LivingEntity entity : entities) {
                if (!targetList.contains(entity)) {
                    targetList.add(entity);
                    return entity;
                }
            }

            if (cycle >= targetList.size()) {
                cycle = 0;
            }
            return targetList.get(cycle);
        } else {
            if (!entities.isEmpty()) {
                LivingEntity first = entities.get(0);
                targetList.add(first);
                return entities.get(0);
            } else {
                return null;
            }
        }
    }

    private static void tabToNextEnemy(Player player) {
        if (target != findNearby(player)) {
            player.playSound(ModSounds.TOCK, 1.0F, 1.0F);
        }
        target = findNearby(player);
    }

    private static void leaveLockOn() {
        target = null;
        lockedOn = false;
        targetList.clear();
    }

    /**
     * To Here
     */

    public static void fogEvents(ViewportEvent.RenderFog event) {
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.player != null) {
            Player player = minecraft.player;
            Wight wight = Wight.findWight(player, EntitySelector.ENTITY_STILL_ALIVE::test);
            if (wight != null) {
                final float f = minecraft.gameRenderer.getRenderDistance();
                event.setNearPlaneDistance(f * 0.05F);
                event.setFarPlaneDistance(Math.min(f, 192.0F) * 0.5F);
                event.setCanceled(true);
            }
        }
    }

    /**
     * From here, code is modified and based of @gigaherz ClientEvents codes: <a href="https://github.com/gigaherz/ToolBelt/blob/master/src/main/java/dev/gigaherz/toolbelt/client/ClientEvents.java">...</a>
     */
    public static void wipeOpen() {
        if (ModKeybindings.wandCircle() != null) {
            while (ModKeybindings.wandCircle().consumeClick()) {
            }
        }
        if (ModKeybindings.brewCircle() != null) {
            while (ModKeybindings.brewCircle().consumeClick()) {
            }
        }
    }

    private static boolean toolMenuKeyWasDown = false;

    public static void handleKeys(Minecraft minecraft) {
        if (minecraft.screen == null && ModKeybindings.wandCircle() != null && ModKeybindings.brewCircle() != null) {
            boolean toolMenuKeyIsDown = ModKeybindings.wandCircle().isDown() || ModKeybindings.brewCircle().isDown();
            boolean wandCircle = ModKeybindings.wandCircle().isDown();
            boolean brewCircle = ModKeybindings.brewCircle().isDown();
            if (toolMenuKeyIsDown && !toolMenuKeyWasDown) {
                if (wandCircle) {
                    while (ModKeybindings.wandCircle().consumeClick()) {
                        if (minecraft.screen == null && minecraft.player != null) {
                            ItemStack inHand = WandUtil.findWand(minecraft.player);
                            if (!inHand.isEmpty() && ((TotemFinder.canOpenWandCircle(minecraft.player)))) {
                                minecraft.setScreen(new FocusRadialMenuScreen());
                            }
                        }
                    }
                } else if (brewCircle) {
                    while (ModKeybindings.brewCircle().consumeClick()) {
                        if (minecraft.screen == null && minecraft.player != null) {
                            if (CuriosFinder.hasBrewInBag(minecraft.player)) {
                                minecraft.setScreen(new BrewRadialMenuScreen());
                            }
                        }
                    }
                }
            }
            toolMenuKeyWasDown = toolMenuKeyIsDown;
        } else {
            toolMenuKeyWasDown = true;
        }
    }


    public static boolean isKeyDown0(KeyMapping keybind) {
        if (keybind.isUnbound()) {
            return false;
        }

        InputConstants.Key key = KeyBindingHelper.getBoundKeyOf(keybind);

        return switch (key.getType()) {
            case KEYSYM -> InputConstants.isKeyDown(Minecraft.getInstance().getWindow().getWindow(), key.getValue());
            case MOUSE ->
                    GLFW.glfwGetMouseButton(Minecraft.getInstance().getWindow().getWindow(), key.getValue()) == GLFW.GLFW_PRESS;
            default -> false;
        };
    }

    public static boolean isKeyDown(KeyMapping keybind) {
        if (keybind.isUnbound()) {
            return false;
        }

        return isKeyDown0(keybind) /*&& keybind.getKeyConflictContext().isActive() && keybind.getKeyModifier().isActive(keybind.getKeyConflictContext())*/;
    }

    public static void updateInputEvent(MovementInputUpdateEvent event) {
        Player player = event.getEntity();
        Input input = event.getInput();
        if (player instanceof LocalPlayer localPlayer) {
            if (MainConfig.WheelGuiMovement.get()) {
                if (Minecraft.getInstance().screen instanceof FocusRadialMenuScreen || Minecraft.getInstance().screen instanceof BrewRadialMenuScreen) {
                    Options settings = Minecraft.getInstance().options;
                    input.up = isKeyDown0(settings.keyUp);
                    input.down = isKeyDown0(settings.keyDown);
                    input.left = isKeyDown0(settings.keyLeft);
                    input.right = isKeyDown0(settings.keyRight);

                    input.forwardImpulse = input.up == input.down ? 0.0F : (input.up ? 1.0F : -1.0F);
                    input.leftImpulse = input.left == input.right ? 0.0F : (input.left ? 1.0F : -1.0F);
                    input.jumping = isKeyDown0(settings.keyJump);
                    input.shiftKeyDown = isKeyDown0(settings.keyShift);
                    if (localPlayer.isMovingSlowly()) {
                        input.leftImpulse = (float) ((double) input.leftImpulse * 0.3D);
                        input.forwardImpulse = (float) ((double) input.forwardImpulse * 0.3D);
                    }
                }
            }
            if (SpellConfig.FullStopCast.get()) {
                if (localPlayer.isUsingItem() && !localPlayer.isPassenger()) {
                    if (MobUtil.isSpellCasting(localPlayer)) {
                        input.leftImpulse = 0.0F;
                        input.forwardImpulse = 0.0F;
                        input.jumping = false;
                    }
                }
            }
        }
    }

    /**
     * To Here
     */

    public static void keyInputs(InputEvent.Key event) {
        Minecraft mc = Minecraft.getInstance();

        if (MainConfig.WheelGuiMovement.get()) {
            if (mc.screen instanceof FocusRadialMenuScreen || mc.screen instanceof BrewRadialMenuScreen) {
                InputConstants.Key inputconstants$key = InputConstants.getKey(event.getKey(), event.getScanCode());
                if (event.getAction() == 0) {
                    KeyMapping.set(inputconstants$key, false);
                    if (event.getKey() == 292) {
                        mc.options.renderDebug = !mc.options.renderDebug;
                        mc.options.renderDebugCharts = mc.options.renderDebug && Screen.hasShiftDown();
                        mc.options.renderFpsChart = mc.options.renderDebug && Screen.hasAltDown();
                    }
                } else {
                    if (event.getKey() == 293 && mc.gameRenderer != null) {
                        mc.gameRenderer.togglePostEffect();
                    }

                    boolean flag3 = InputConstants.isKeyDown(Minecraft.getInstance().getWindow().getWindow(), 292);
                    if (event.getKey() == 256) {
                        boolean flag2 = InputConstants.isKeyDown(Minecraft.getInstance().getWindow().getWindow(), 292);
                        mc.pauseGame(flag2);
                    }

                    if (event.getKey() == 290) {
                        mc.options.hideGui = !mc.options.hideGui;
                    }

                    if (flag3) {
                        KeyMapping.set(inputconstants$key, false);
                    } else {
                        KeyMapping.set(inputconstants$key, true);
                        KeyMapping.click(inputconstants$key);
                    }

                    if (mc.options.renderDebugCharts && event.getKey() >= 48 && event.getKey() <= 57) {
                        mc.debugFpsMeterKeyPress(event.getKey() - 48);
                    }
                }
            }
        }

        if (ModKeybindings.keyBindings[0].isDown() && mc.isWindowActive()) {
            ClientPlayNetworking.send(CWandKeyPacket.ID, CWandKeyPacket.DUMMY);
        }
        if (ModKeybindings.keyBindings[2].isDown() && mc.isWindowActive()) {
            ModNetwork.sendToServer(CBagKeyPacket.ID, CBagKeyPacket.DUMMY);
        }
        if (ModKeybindings.keyBindings[3].isDown() && mc.isWindowActive()) {
            ModNetwork.sendToServer(CWitchRobePacket.ID, CWitchRobePacket.DUMMY);
        }
        if (ModKeybindings.keyBindings[4].isDown() && mc.isWindowActive()) {
            ModNetwork.sendToServer(CStopAttackPacket.ID, CStopAttackPacket.DUMMY);
        }
        if (ModKeybindings.keyBindings[5].isDown() && mc.isWindowActive()) {
            ModNetwork.sendToServer(CMagnetPacket.ID, CMagnetPacket.DUMMY);
        }
        if (ModKeybindings.keyBindings[6].isDown() && mc.isWindowActive()) {
            ModNetwork.sendToServer(CSetLichNightVisionMode.ID, CSetLichNightVisionMode.DUMMY);
            if (mc.player != null && MainConfig.LichNightVision.get()) {
                if (LichdomHelper.isLich(mc.player)) {
                    mc.player.playSound(SoundEvents.END_PORTAL_FRAME_FILL);
                }
            }
        }
        if (ModKeybindings.keyBindings[7].isDown() && mc.isWindowActive()) {
            ModNetwork.sendToServer(CExtractPotionKeyPacket.ID, CExtractPotionKeyPacket.DUMMY);
        }
        if (ModKeybindings.keyBindings[8].isDown() && mc.isWindowActive()) {
            ModNetwork.sendToServer(CBrewBagKeyPacket.ID, CBrewBagKeyPacket.DUMMY);
        }
        if (ModKeybindings.keyBindings[10].isDown() && mc.isWindowActive()) {
            ModNetwork.sendToServer(CRavagerRoarPacket.ID, CRavagerRoarPacket.DUMMY);
        }
        if (ModKeybindings.keyBindings[11].isDown() && mc.isWindowActive()) {
            ModNetwork.sendToServer(CAutoRideablePacket.ID, CAutoRideablePacket.DUMMY);
        }
        if (ModKeybindings.keyBindings[12].isDown() && mc.isWindowActive()) {
            if (mc.player != null) {
                if (LichdomHelper.isLich(mc.player)) {
                    LichdomHelper.setLichMode(mc.player, !LichdomHelper.isInLichMode(mc.player));
                    if (!LichdomHelper.isInLichMode(mc.player)) {
                        mc.player.playSound(SoundEvents.ZOMBIE_VILLAGER_CONVERTED);
                    } else {
                        if (mc.level != null) {
                            for (int i = 0; i < 5; ++i) {
                                double d0 = mc.level.random.nextGaussian() * 0.02D;
                                double d1 = mc.level.random.nextGaussian() * 0.02D;
                                double d2 = mc.level.random.nextGaussian() * 0.02D;
                                mc.level.addParticle(ParticleTypes.SCULK_SOUL, mc.player.getRandomX(1.0D), mc.player.getRandomY() + 1.0D, mc.player.getRandomZ(1.0D), d0, d1, d2);
                            }
                        }
                        mc.player.playSound(ModSounds.SOUL_EXPLODE, 1.0F, 0.75F);
                    }
                    ModNetwork.sendToServer(CSetLichMode.ID, CSetLichMode.DUMMY);
                }
            }
        }
        if (ModKeybindings.keyBindings[13].isDown() && mc.isWindowActive()) {
            if (mc.player != null) {
                if (LichdomHelper.isLich(mc.player)) {
                    if (LichdomHelper.isInLichMode(mc.player)) {
                        mc.player.level.playLocalSound(mc.player.getX(), mc.player.getY(), mc.player.getZ(), ModSounds.LICH_LAUGH, mc.player.getSoundSource(), 2.0F, mc.player.getVoicePitch(), false);
                    }
                }
            }
        }
        if (ModKeybindings.keyBindings[14].isDown() && mc.isWindowActive()) {
            ModNetwork.sendToServer(CActivateCurioKeyPacket.ID, CActivateCurioKeyPacket.DUMMY);
        }
        if (ModKeybindings.keyBindings[15].isDown() && mc.isWindowActive()) {
            ModNetwork.sendToServer(CDismissServantsPacket.ID, CDismissServantsPacket.DUMMY);
        }
    }

    //Domestication Innovation work-a-round
    public static void interactionKeyEvent(InputEvent.InteractionKeyMappingTriggered event) {
        Player player = Minecraft.getInstance().player;
        if (player != null) {
            if (event.isAttack()
                    && Minecraft.getInstance().hitResult instanceof EntityHitResult result
                    && result.getEntity() instanceof IOwned owned
                    && owned.getTrueOwner() == player) {
                MultiPlayerGameMode gameMode = Minecraft.getInstance().gameMode;
                ClientPacketListener listener = Minecraft.getInstance().getConnection();
                if (gameMode != null && listener != null) {
                    ItemStack stack = player.getMainHandItem();
                    if (stack.getItem() instanceof ILeftClickEntity item && item.onLeftClickEntity(stack, player, result.getEntity())) {
                        listener.send(ServerboundInteractPacket.createAttackPacket(result.getEntity(), player.isShiftKeyDown()));
                        if (gameMode.getPlayerMode() != GameType.SPECTATOR) {
                            player.attack(result.getEntity());
                        }
                        event.setCanceled(true);
                    }
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    public <T extends LivingEntity> void followBodyRotations(final T livingEntity, final HumanoidModel<T> model) {
        EntityRenderer<? super T> render = Minecraft.getInstance().getEntityRenderDispatcher().getRenderer(livingEntity);
        if (render instanceof LivingEntityRenderer) {
            LivingEntityRenderer<T, EntityModel<T>> livingRenderer = (LivingEntityRenderer<T, EntityModel<T>>) render;
            EntityModel<T> entityModel = livingRenderer.getModel();
            if (entityModel instanceof HumanoidModel<T> humanoidModel) {
                humanoidModel.copyPropertiesTo(model);
            }
        }
    }

    public static void onRecipesUpdated(MinecraftServer server, CloseableResourceManager resourceManager, boolean success) {
        CrusherServant.invalidateRecipeCache();
    }
}
