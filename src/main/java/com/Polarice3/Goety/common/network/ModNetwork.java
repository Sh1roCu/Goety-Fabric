package com.Polarice3.Goety.common.network;

import cn.sh1rocu.goety.api.extension.IEntityAdditionalSpawnData;
import com.Polarice3.Goety.common.network.client.*;
import com.Polarice3.Goety.common.network.client.brew.CBrewBagKeyPacket;
import com.Polarice3.Goety.common.network.client.brew.CThrowBrewKeyPacket;
import com.Polarice3.Goety.common.network.client.focus.CAddFocusToBagPacket;
import com.Polarice3.Goety.common.network.client.focus.CAddFocusToInventoryPacket;
import com.Polarice3.Goety.common.network.client.focus.CSwapFocusPacket;
import com.Polarice3.Goety.common.network.client.focus.CSwapFocusTwoPacket;
import com.Polarice3.Goety.common.network.server.*;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.LevelChunk;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class ModNetwork {

    @Environment(EnvType.CLIENT)
    public static void registerS2CPackets() {
        ClientPlayNetworking.registerGlobalReceiver(IEntityAdditionalSpawnData.EXTRA_DATA_PACKET, (client, handler, buf, responseSender) -> {
            int entityId = buf.readVarInt();
            buf.retain();
            client.execute(() -> {
                Entity entity = Objects.requireNonNull(client.level).getEntity(entityId);
                if (entity instanceof IEntityAdditionalSpawnData extra) {
                    extra.readSpawnData(buf);
                }
                buf.release();
            });
        });

        ClientPlayNetworking.registerGlobalReceiver(EntityUpdatePacket.ID, EntityUpdatePacket::consume);
        ClientPlayNetworking.registerGlobalReceiver(TotemDeathPacket.ID, TotemDeathPacket::consume);
        ClientPlayNetworking.registerGlobalReceiver(SPlayPlayerSoundPacket.ID, SPlayPlayerSoundPacket::consume);
        ClientPlayNetworking.registerGlobalReceiver(SPlayWorldSoundPacket.ID, SPlayWorldSoundPacket::consume);
        ClientPlayNetworking.registerGlobalReceiver(SPlayEntitySoundPacket.ID, SPlayEntitySoundPacket::consume);
        ClientPlayNetworking.registerGlobalReceiver(SPlayFollowSoundPacket.ID, SPlayFollowSoundPacket::consume);
        ClientPlayNetworking.registerGlobalReceiver(SPlayLoopSoundPacket.ID, SPlayLoopSoundPacket::consume);
        ClientPlayNetworking.registerGlobalReceiver(SPlayEffectLoopSoundPacket.ID, SPlayEffectLoopSoundPacket::consume);
        ClientPlayNetworking.registerGlobalReceiver(SFungusExplosionPacket.ID, SFungusExplosionPacket::consume);
        ClientPlayNetworking.registerGlobalReceiver(SLootingExplosionPacket.ID, SLootingExplosionPacket::consume);
        ClientPlayNetworking.registerGlobalReceiver(SApostleSmitePacket.ID, SApostleSmitePacket::consume);
        ClientPlayNetworking.registerGlobalReceiver(SRCGlowPacket.ID, SRCGlowPacket::consume);
        ClientPlayNetworking.registerGlobalReceiver(STentacleRangePacket.ID, STentacleRangePacket::consume);
        ClientPlayNetworking.registerGlobalReceiver(SSoulExplodePacket.ID, SSoulExplodePacket::consume);
        ClientPlayNetworking.registerGlobalReceiver(SAddBrewParticlesPacket.ID, SAddBrewParticlesPacket::consume);
        ClientPlayNetworking.registerGlobalReceiver(SLightningPacket.ID, SLightningPacket::consume);
        ClientPlayNetworking.registerGlobalReceiver(SThunderBoltPacket.ID, SThunderBoltPacket::consume);
        ClientPlayNetworking.registerGlobalReceiver(SLightningBoltPacket.ID, SLightningBoltPacket::consume);
        ClientPlayNetworking.registerGlobalReceiver(SSetPlayerOwnerPacket.ID, SSetPlayerOwnerPacket::consume);
        ClientPlayNetworking.registerGlobalReceiver(SUpdateBossBar.ID, SUpdateBossBar::consume);
        ClientPlayNetworking.registerGlobalReceiver(SFocusCooldownPacket.ID, SFocusCooldownPacket::consume);
        ClientPlayNetworking.registerGlobalReceiver(SFocusSpecificCooldownPacket.ID, SFocusSpecificCooldownPacket::consume);
        ClientPlayNetworking.registerGlobalReceiver(SRemoveEffectPacket.ID, SRemoveEffectPacket::consume);
        ClientPlayNetworking.registerGlobalReceiver(SPurifyEffectPacket.ID, SPurifyEffectPacket::consume);
        ClientPlayNetworking.registerGlobalReceiver(SPlayerRotationPacket.ID, SPlayerRotationPacket::consume);
        ClientPlayNetworking.registerGlobalReceiver(SRepositionPacket.ID, SRepositionPacket::consume);
        ClientPlayNetworking.registerGlobalReceiver(SInstaLookPacket.ID, SInstaLookPacket::consume);
        ClientPlayNetworking.registerGlobalReceiver(SStaffParticlePacket.ID, SStaffParticlePacket::consume);
        ClientPlayNetworking.registerGlobalReceiver(SInstaLookAtPacket.ID, SInstaLookAtPacket::consume);

    }

    public static void registerC2SPackets() {
        ServerPlayNetworking.registerGlobalReceiver(CWandKeyPacket.ID, CWandKeyPacket::consume);
        ServerPlayNetworking.registerGlobalReceiver(CBagKeyPacket.ID, CBagKeyPacket::consume);
        ServerPlayNetworking.registerGlobalReceiver(CStopAttackPacket.ID, CStopAttackPacket::consume);
        ServerPlayNetworking.registerGlobalReceiver(CWitchRobePacket.ID, CWitchRobePacket::consume);
        ServerPlayNetworking.registerGlobalReceiver(CAddWitchFuelKeyPacket.ID, CAddWitchFuelKeyPacket::consume);
        ServerPlayNetworking.registerGlobalReceiver(CAddCatalystKeyPacket.ID, CAddCatalystKeyPacket::consume);
        ServerPlayNetworking.registerGlobalReceiver(CExtractPotionKeyPacket.ID, CExtractPotionKeyPacket::consume);
        ServerPlayNetworking.registerGlobalReceiver(CRavagerRoarPacket.ID, CRavagerRoarPacket::consume);
        ServerPlayNetworking.registerGlobalReceiver(CAutoRideablePacket.ID, CAutoRideablePacket::consume);
        ServerPlayNetworking.registerGlobalReceiver(CScytheStrikePacket.ID, CScytheStrikePacket::consume);
        ServerPlayNetworking.registerGlobalReceiver(CBoEStrikePacket.ID, CBoEStrikePacket::consume);
        ServerPlayNetworking.registerGlobalReceiver(CLichKissPacket.ID, CLichKissPacket::consume);
        ServerPlayNetworking.registerGlobalReceiver(CMagnetPacket.ID, CMagnetPacket::consume);
        ServerPlayNetworking.registerGlobalReceiver(CSetLichMode.ID, CSetLichMode::consume);
        ServerPlayNetworking.registerGlobalReceiver(CSetLichNightVisionMode.ID, CSetLichNightVisionMode::consume);
        ServerPlayNetworking.registerGlobalReceiver(CBeamPacket.ID, CBeamPacket::consume);
        ServerPlayNetworking.registerGlobalReceiver(CTargetPlayerPacket.ID, CTargetPlayerPacket::consume);
        ServerPlayNetworking.registerGlobalReceiver(CSwapFocusPacket.ID, CSwapFocusPacket::consume);
        ServerPlayNetworking.registerGlobalReceiver(CSwapFocusTwoPacket.ID, CSwapFocusTwoPacket::consume);
        ServerPlayNetworking.registerGlobalReceiver(CAddFocusToBagPacket.ID, CAddFocusToBagPacket::consume);
        ServerPlayNetworking.registerGlobalReceiver(CAddFocusToInventoryPacket.ID, CAddFocusToInventoryPacket::consume);
        ServerPlayNetworking.registerGlobalReceiver(CBrewBagKeyPacket.ID, CBrewBagKeyPacket::consume);
        ServerPlayNetworking.registerGlobalReceiver(CThrowBrewKeyPacket.ID, CThrowBrewKeyPacket::consume);
        ServerPlayNetworking.registerGlobalReceiver(CMultiJumpPacket.ID, CMultiJumpPacket::consume);
        ServerPlayNetworking.registerGlobalReceiver(CSetDeltaMovement.ID, CSetDeltaMovement::consume);
        ServerPlayNetworking.registerGlobalReceiver(CTramplerPacket.ID, CTramplerPacket::consume);
        ServerPlayNetworking.registerGlobalReceiver(CPrisonerMinePacket.ID, CPrisonerMinePacket::consume);
        ServerPlayNetworking.registerGlobalReceiver(CActivateCurioKeyPacket.ID, CActivateCurioKeyPacket::consume);
        ServerPlayNetworking.registerGlobalReceiver(CDismissServantsPacket.ID, CDismissServantsPacket::consume);
        ServerPlayNetworking.registerGlobalReceiver(CBroomCollisionPacket.ID, CBroomCollisionPacket::consume);
    }

    public static void sendTo(Player player, ResourceLocation channel, FriendlyByteBuf buf) {
        if (player instanceof ServerPlayer serverPlayer) {
            sendToClient(serverPlayer, channel, buf);
        }
    }

    @Environment(EnvType.CLIENT)
    public static void sendToServer(ResourceLocation channel, FriendlyByteBuf buf) {
        ClientPlayNetworking.send(channel, buf);
    }

    public static void sentToTrackingChunk(LevelChunk chunk, ResourceLocation channel, FriendlyByteBuf buf) {
        Level level = chunk.getLevel();
        if (level instanceof ServerLevel serverLevel) {
            PlayerLookup.tracking(serverLevel, chunk.getPos()).forEach(player -> sendToClient(player, channel, buf));
        }
    }

    public static void sentToTrackingEntity(Entity entity, ResourceLocation channel, FriendlyByteBuf buf) {
        PlayerLookup.tracking(entity).forEach(player -> sendToClient(player, channel, buf));
    }

    public static void sentToTrackingEntityAndPlayer(Entity entity, ResourceLocation channel, FriendlyByteBuf buf) {
        if (entity instanceof ServerPlayer serverPlayer) {
            sendToClient(serverPlayer, channel, buf);
        }
        PlayerLookup.tracking(entity).forEach(player -> sendToClient(player, channel, buf));
    }

    public static void sendToALL(@NotNull MinecraftServer server, ResourceLocation channel, FriendlyByteBuf buf) {
        PlayerLookup.all(server).forEach(player -> sendToClient(player, channel, buf));
    }

    public static void sendToClient(ServerPlayer player, ResourceLocation channel, FriendlyByteBuf buf) {
        ServerPlayNetworking.send(player, channel, buf);
    }

}
