package com.Polarice3.Goety.common.network.server;

import com.Polarice3.Goety.Goety;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;

public class SPurifyEffectPacket {

    public static final ResourceLocation ID = Goety.location("s2c_purify_effect");

    public static FriendlyByteBuf encode(int mob, boolean removeDebuff) {
        FriendlyByteBuf buffer = PacketByteBufs.create();
        buffer.writeInt(mob);
        buffer.writeBoolean(removeDebuff);
        return buffer;
    }

    @Environment(EnvType.CLIENT)
    public static void consume(Minecraft client, ClientPacketListener handler, FriendlyByteBuf buf, PacketSender responseSender) {
        int mob = buf.readInt();
        boolean removeDebuff = buf.readBoolean();
        client.execute(() -> {
            Level level = Goety.PROXY.getLevel();
            if (level instanceof ClientLevel clientWorld) {
                Entity entity = clientWorld.getEntity(mob);
                if (entity instanceof LivingEntity livingEntity) {
                    for (MobEffect mobEffect : BuiltInRegistries.MOB_EFFECT) {
                        boolean flag;
                        if (removeDebuff) {
                            flag = !mobEffect.isBeneficial();
                        } else {
                            flag = mobEffect.isBeneficial();
                        }
                        if (flag /*&& !mobEffect.getCurativeItems().isEmpty()*/) {
                            livingEntity.removeEffect(mobEffect);
                        }
                    }
                }
            }
        });
    }

}
