package com.Polarice3.Goety.common.network.server;

import com.Polarice3.Goety.Goety;
import com.Polarice3.Goety.utils.SEHelper;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;

public class SFocusCooldownPacket {

    public static final ResourceLocation ID = Goety.location("s2c_focus_cooldown");

    public static FriendlyByteBuf encode(Item item, int duration) {
        FriendlyByteBuf buffer = PacketByteBufs.create();
        buffer.writeId(BuiltInRegistries.ITEM, item);
        buffer.writeVarInt(duration);
        return buffer;
    }

    @Environment(EnvType.CLIENT)
    public static void consume(Minecraft client, ClientPacketListener handler, FriendlyByteBuf buf, PacketSender responseSender) {
        Item item = buf.readById(BuiltInRegistries.ITEM);
        int duration = buf.readVarInt();
        client.execute(() -> {
            Player player = Goety.PROXY.getPlayer();
            if (player != null) {
                if (duration == 0) {
                    SEHelper.getFocusCoolDown(player).removeCooldown(player, player.level, item);
                } else {
                    SEHelper.addCooldown(player, item, duration);
                }
            }
        });
    }
}
