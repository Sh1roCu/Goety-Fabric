package com.Polarice3.Goety.common.network.server;

import com.Polarice3.Goety.Goety;
import com.Polarice3.Goety.common.items.ModItems;
import com.Polarice3.Goety.utils.BrewUtils;
import com.Polarice3.Goety.utils.ParticleUtil;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.particle.Particle;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class SAddBrewParticlesPacket {

    public static final ResourceLocation ID = Goety.location("s2c_add_brew_particles");

    public static FriendlyByteBuf encode(ItemStack itemStack, BlockPos blockPos, boolean instant, int color) {
        FriendlyByteBuf buffer = PacketByteBufs.create();
        buffer.writeItem(itemStack);
        buffer.writeBlockPos(blockPos);
        buffer.writeBoolean(instant);
        buffer.writeInt(color);
        return buffer;
    }

    @Environment(EnvType.CLIENT)
    public static void consume(Minecraft client, ClientPacketListener handler, FriendlyByteBuf buf, PacketSender responseSender) {
        ItemStack itemStack = buf.readItem();
        BlockPos blockPos = buf.readBlockPos();
        boolean instant = buf.readBoolean();
        int color = buf.readInt();
        client.execute(() -> {
            Level level = Goety.PROXY.getLevel();
            if (level instanceof ClientLevel clientWorld) {
                int area = BrewUtils.getAreaOfEffect(itemStack) + 4;
                Vec3 vec3 = Vec3.atBottomCenterOf(blockPos);

                for (int l = 0; l < 8; ++l) {
                    clientWorld.addParticle(new ItemParticleOption(ParticleTypes.ITEM, new ItemStack(ModItems.SPLASH_BREW)), vec3.x, vec3.y, vec3.z, clientWorld.random.nextGaussian() * 0.15D, clientWorld.random.nextDouble() * 0.2D, clientWorld.random.nextGaussian() * 0.15D);
                }

                float f0 = (float) (color >> 16 & 255) / 255.0F;
                float f1 = (float) (color >> 8 & 255) / 255.0F;
                float f2 = (float) (color & 255) / 255.0F;
                ParticleOptions particleoptions = instant ? ParticleTypes.INSTANT_EFFECT : ParticleTypes.EFFECT;

                for (int j3 = 0; j3 < 100 * (BrewUtils.getAreaOfEffect(itemStack) + 1); ++j3) {
                    float f3 = clientWorld.random.nextFloat() * area;
                    float f4 = clientWorld.random.nextFloat() * (float) (Math.PI * (area / 2.0F));
                    double d1 = Math.cos(f4) * f3;
                    double d2 = 0.01D + clientWorld.random.nextDouble() * 0.5D + (area / 10.0D);
                    double d3 = Math.sin(f4) * f3;
                    Particle particle = ParticleUtil.addParticleInternal(particleoptions, particleoptions.getType().getOverrideLimiter(), vec3.x + d1 * 0.1D, vec3.y + 0.3D, vec3.z + d3 * 0.1D, d1, d2, d3);
                    if (particle != null) {
                        float f5 = 0.75F + clientWorld.random.nextFloat() * 0.25F;
                        particle.setColor(f0 * f5, f1 * f5, f2 * f5);
                        particle.setPower(f3);
                    }
                }

                clientWorld.playLocalSound(blockPos.getX(), blockPos.getY(), blockPos.getZ(), SoundEvents.SPLASH_POTION_BREAK, SoundSource.NEUTRAL, 1.0F, clientWorld.random.nextFloat() * 0.1F + 0.9F, false);
            }
        });
    }
}
