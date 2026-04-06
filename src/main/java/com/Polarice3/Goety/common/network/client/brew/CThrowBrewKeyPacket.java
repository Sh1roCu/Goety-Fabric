package com.Polarice3.Goety.common.network.client.brew;

import com.Polarice3.Goety.Goety;
import com.Polarice3.Goety.common.entities.projectiles.ThrownBrew;
import com.Polarice3.Goety.common.items.brew.ThrowableBrewItem;
import com.Polarice3.Goety.common.items.handler.BrewBagItemHandler;
import com.Polarice3.Goety.utils.BrewUtils;
import com.Polarice3.Goety.utils.CuriosFinder;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.game.ClientboundSoundPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class CThrowBrewKeyPacket {

    public static final ResourceLocation ID = Goety.location("c2s_throw_bag_key");

    public static FriendlyByteBuf encode(int chosenBrew) {
        FriendlyByteBuf buffer = PacketByteBufs.create();
        buffer.writeInt(chosenBrew);
        return buffer;
    }

    public static void consume(MinecraftServer server, ServerPlayer playerEntity, ServerGamePacketListenerImpl handler, FriendlyByteBuf buf, PacketSender responseSender) {
        int chosenBrew = buf.readInt();
        server.execute(() -> swapFocus(chosenBrew, playerEntity));
    }

    public static void swapFocus(int swapSlot, Player player) {
        ItemStack stack = CuriosFinder.findBrewBag(player);
        if (stack.getCount() <= 0) {
            return;
        }

        BrewBagItemHandler bagHandler = BrewBagItemHandler.get(stack);

        ItemStack bagFocus = bagHandler.getStackInSlot(swapSlot);
        if (bagFocus.getItem() instanceof ThrowableBrewItem) {
            if (!player.level.isClientSide) {
                ThrownBrew thrownBrew = new ThrownBrew(player.level, player);
                thrownBrew.setItem(bagFocus);
                float velocity = 0.5F + BrewUtils.getVelocity(bagFocus);
                thrownBrew.shootFromRotation(player, player.getXRot(), player.getYRot(), -20.0F, velocity, 1.0F);
                player.level.addFreshEntity(thrownBrew);
            }

            player.awardStat(Stats.ITEM_USED.get(bagFocus.getItem()));
            if (!player.getAbilities().instabuild) {
                try (Transaction tx = Transaction.openOuter()) {
                    bagFocus.shrink(1);
                    bagHandler.setStackInSlot(swapSlot, bagFocus);
                    tx.commit();
                }
            }
            if (player instanceof ServerPlayer serverPlayer) {
                serverPlayer.connection.send(new ClientboundSoundPacket(BuiltInRegistries.SOUND_EVENT.wrapAsHolder(SoundEvents.SPLASH_POTION_THROW), SoundSource.PLAYERS, serverPlayer.position().x, serverPlayer.position().y, serverPlayer.position().z, 1.0F, 1.0F, serverPlayer.level().getRandom().nextLong()));
            }
        }
    }
}
