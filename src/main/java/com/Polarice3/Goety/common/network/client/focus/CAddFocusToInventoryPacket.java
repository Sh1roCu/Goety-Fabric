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
import net.minecraft.world.item.ItemStack;

public class CAddFocusToInventoryPacket {

    public static final ResourceLocation ID = Goety.location("c2s_add_focus_to_inventory");

    public static final FriendlyByteBuf DUMMY = PacketByteBufs.empty();

    public static void consume(MinecraftServer server, ServerPlayer player, ServerGamePacketListenerImpl handler, FriendlyByteBuf buf, PacketSender responseSender) {
        server.execute(() -> {
            if (player != null) {
                ItemStack stack = WandUtil.findFocus(player);
                if (stack.getCount() <= 0) {
                    return;
                }

                SoulUsingItemHandler wandHandler = SoulUsingItemHandler.get(WandUtil.findWand(player));

                ItemStack wandFocus = wandHandler.getSlot();

                for (int i = 0; i < player.getInventory().items.size(); ++i) {
                    ItemStack itemStack = player.getInventory().getItem(i);
                    if (itemStack.isEmpty()) {
                        player.getInventory().setItem(i, wandFocus);
                        try (Transaction tx = Transaction.openOuter()) {
                            wandHandler.extractItem(tx);
                            tx.commit();
                        }
                        break;
                    }
                }
                player.connection.send(new ClientboundSoundPacket(BuiltInRegistries.SOUND_EVENT.wrapAsHolder(ModSounds.FOCUS_PICK), SoundSource.PLAYERS, player.position().x, player.position().y, player.position().z, 1.0F, 1.0F, player.level().getRandom().nextLong()));
            }
        });
    }
}
