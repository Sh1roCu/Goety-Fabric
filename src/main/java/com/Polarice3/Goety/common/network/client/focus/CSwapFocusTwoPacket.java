package com.Polarice3.Goety.common.network.client.focus;

import com.Polarice3.Goety.Goety;
import com.Polarice3.Goety.common.items.handler.SoulUsingItemHandler;
import com.Polarice3.Goety.init.ModSounds;
import com.Polarice3.Goety.utils.WandUtil;
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
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class CSwapFocusTwoPacket {

    public static final ResourceLocation ID = Goety.location("c2s_swap_focus_two");

    public static FriendlyByteBuf encode(int swapWith) {
        FriendlyByteBuf buffer = PacketByteBufs.create();
        buffer.writeInt(swapWith);
        return buffer;
    }

    public static void consume(MinecraftServer server, ServerPlayer player, ServerGamePacketListenerImpl handler, FriendlyByteBuf buf, PacketSender responseSender) {
        int swapWith = buf.readInt();
        server.execute(() -> swapFocus(swapWith, player));
    }

    public static void swapFocus(int swapSlot, Player player) {
        SoulUsingItemHandler wandHandler = SoulUsingItemHandler.get(WandUtil.findWand(player));

        ItemStack wandFocus = wandHandler.getSlot();

        ItemStack invFocus = player.getInventory().getItem(swapSlot);
        player.getInventory().setItem(swapSlot, wandFocus);
        try (Transaction tx = Transaction.openOuter()) {
            wandHandler.extractItem(tx);
            tx.commit();
        }
        try (Transaction tx = Transaction.openOuter()) {
            wandHandler.insertItem(invFocus, tx);
            tx.commit();
        }
        if (player instanceof ServerPlayer serverPlayer) {
            serverPlayer.connection.send(new ClientboundSoundPacket(BuiltInRegistries.SOUND_EVENT.wrapAsHolder(ModSounds.FOCUS_PICK), SoundSource.PLAYERS, serverPlayer.position().x, serverPlayer.position().y, serverPlayer.position().z, 1.0F, 1.0F, serverPlayer.level().getRandom().nextLong()));
        }
    }
}
