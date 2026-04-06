package com.Polarice3.Goety.common.network.server;

import com.Polarice3.Goety.Goety;
import com.Polarice3.Goety.common.capabilities.soulenergy.FocusCooldown;
import com.Polarice3.Goety.utils.SEHelper;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class SFocusSpecificCooldownPacket {

    public static final ResourceLocation ID = Goety.location("s2c_focus_specific_cooldown");

    public static FriendlyByteBuf encode(String key, int duration) {
        return encode(ItemStack.EMPTY, key, duration);
    }

    public static FriendlyByteBuf encode(ItemStack itemStack, int duration) {
        return encode(itemStack, FocusCooldown.keyOf(itemStack), duration);
    }

    public static FriendlyByteBuf encode(ItemStack itemStack, String key, int duration) {
        FriendlyByteBuf buffer = PacketByteBufs.create();
        buffer.writeItem(itemStack);
        buffer.writeUtf(key);
        buffer.writeVarInt(duration);
        return buffer;
    }

    @Environment(EnvType.CLIENT)
    public static void consume(Minecraft client, ClientPacketListener handler, FriendlyByteBuf buf, PacketSender responseSender) {
        ItemStack itemStack = buf.readItem();
        String key = buf.readUtf();
        int duration = buf.readVarInt();
        client.execute(() -> {
            Player player = Goety.PROXY.getPlayer();
            if (player != null) {
                if (duration == 0) {
                    SEHelper.getFocusCoolDown(player).removeSpecificCooldown(player, player.level, key);
                } else {
                    SEHelper.addSpecificCooldown(player, itemStack, duration);
                }
            }
        });
    }
}
