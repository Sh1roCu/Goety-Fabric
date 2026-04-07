package com.Polarice3.Goety.common.network.server;

import com.Polarice3.Goety.Goety;
import com.Polarice3.Goety.client.particles.GatherTrailParticleOption;
import com.Polarice3.Goety.utils.ColorUtil;
import com.Polarice3.Goety.utils.ModelUtil;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.CameraType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.Optional;

public class SStaffParticlePacket {

    public static final ResourceLocation ID = Goety.location("s2c_staff_particle");

    public static FriendlyByteBuf encode(int playerId, float staffHeight, float range, int color, boolean offHand) {
        FriendlyByteBuf buffer = PacketByteBufs.create();
        buffer.writeInt(playerId);
        buffer.writeFloat(staffHeight);
        buffer.writeFloat(range);
        buffer.writeInt(color);
        buffer.writeBoolean(offHand);
        return buffer;
    }

    @Environment(EnvType.CLIENT)
    public static void consume(Minecraft client, ClientPacketListener handler, FriendlyByteBuf buf, PacketSender responseSender) {
        int playerId = buf.readInt();
        float staffHeight = buf.readFloat();
        float range = buf.readFloat();
        int color = buf.readInt();
        boolean offHand = buf.readBoolean();
        client.execute(() -> {
            Level level = Goety.PROXY.getLevel();
            if (level instanceof ClientLevel clientWorld && clientWorld.getEntity(playerId) instanceof Player player) {
                Optional<Vec3> staffEndPos;
                if (player == Minecraft.getInstance().player && Minecraft.getInstance().options.getCameraType() == CameraType.FIRST_PERSON) {
                    // first-person staff end position
                    int arm = (player.getMainArm() == HumanoidArm.RIGHT ? 1 : -1) * (offHand ? -1 : 1);
                    double fovFactor = 960.0 / (double) Minecraft.getInstance().options.fov().get();
                    float horizontalFactor = 0.125F * staffHeight + 0.35F, verticalFactor = 1.5F * staffHeight - 0.45F;
                    Vec3 vec3 = Minecraft.getInstance().gameRenderer.getMainCamera().getNearPlane().getPointOnPlane(horizontalFactor * arm, verticalFactor).scale(fovFactor);
                    staffEndPos = Optional.of(player.getEyePosition().add(vec3));
                } else {
                    // third person staff end position
                    staffEndPos = ModelUtil.getThirdPersonPlayerHandPosition(
                            player,
                            Minecraft.getInstance().getEntityRenderDispatcher(),
                            player.yBodyRotO,
                            0,
                            offHand ? player.getMainArm().getOpposite() : player.getMainArm(),
                            new Vec3(0, 0.55, -staffHeight)
                    );
                }
                if (staffEndPos.isPresent()) {
                    Vec3 pos = staffEndPos.get().add(new Vec3(level.random.nextDouble() - 0.5, level.random.nextDouble() - 0.5, level.random.nextDouble() - 0.5).normalize().scale(range));
                    level.addParticle(
                            new GatherTrailParticleOption(new ColorUtil(color), staffEndPos.get()),
                            pos.x, pos.y, pos.z,
                            0, 0, 0
                    );
                }
            }
        });
    }
}
