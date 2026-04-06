package com.Polarice3.Goety.api.magic;

import cn.sh1rocu.goety.util.client.MinecraftUtil;
import com.Polarice3.Goety.utils.MathHelper;
import com.chocohead.mm.api.ClassTinkerers;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;

@Environment(EnvType.CLIENT)
public class SpellPoses {

    @FunctionalInterface
    public interface CustomArmPoseHandler {
        void handle(HumanoidModel<?> model, LivingEntity entity, HumanoidArm arm);
    }

    private SpellPoses() {

    }

    public static final HumanoidModel.ArmPose SPELL = ClassTinkerers.getEnum(HumanoidModel.ArmPose.class, "GOETY_SPELL");
    public static final HumanoidModel.ArmPose FLIGHT_POSE = ClassTinkerers.getEnum(HumanoidModel.ArmPose.class, "GOETY_FLYING");
    public static final HumanoidModel.ArmPose HOLD_STAFF = ClassTinkerers.getEnum(HumanoidModel.ArmPose.class, "HOLD_STAFF");

    public static final CustomArmPoseHandler SPELL_HANDLER = (model, entity, arm) -> {
        float f5 = entity.walkAnimation.position(MinecraftUtil.getPartialTick());
        if (arm == HumanoidArm.RIGHT) {
            model.rightArm.xRot -= MathHelper.modelDegrees(105);
            model.rightArm.zRot = Mth.cos(f5 * 0.6662F) * 0.25F;
            model.leftArm.xRot += MathHelper.modelDegrees(25);
        } else {
            model.leftArm.xRot -= MathHelper.modelDegrees(105);
            model.leftArm.zRot = -Mth.cos(f5 * 0.6662F) * 0.25F;
            model.rightArm.xRot += MathHelper.modelDegrees(25);
        }
    };
    public static final CustomArmPoseHandler FLIGHT_POSE_HANDLER = (model, entity, arm) -> {
        float f5 = 1.0F;
        if (arm == HumanoidArm.RIGHT) {
            model.rightArm.xRot = -MathHelper.modelDegrees(105);
            model.rightArm.zRot = Mth.cos(f5 * 0.6662F) * 0.25F;
            model.leftArm.xRot = MathHelper.modelDegrees(25);
        } else {
            model.leftArm.xRot = -MathHelper.modelDegrees(105);
            model.leftArm.zRot = -Mth.cos(f5 * 0.6662F) * 0.25F;
            model.rightArm.xRot = MathHelper.modelDegrees(25);
        }
        model.rightLeg.xRot = MathHelper.modelDegrees(17.5F);
        model.leftLeg.xRot = MathHelper.modelDegrees(17.5F);

        model.rightLeg.xRot += 1.0F * Mth.sin(MinecraftUtil.getPartialTick() * 0.067F) * 0.05F;
        model.leftLeg.xRot += -1.0F * Mth.sin(MinecraftUtil.getPartialTick() * 0.067F) * 0.05F;
    };
    public static final CustomArmPoseHandler HOLD_STAFF_HANDLER = (model, entity, arm) -> {
        float f5 = entity.walkAnimation.position(MinecraftUtil.getPartialTick());
        if (arm == HumanoidArm.RIGHT) {
            model.rightArm.xRot -= MathHelper.modelDegrees(90);
            model.rightArm.zRot = Mth.cos(f5 * 0.6662F) * 0.1F;
        } else {
            model.leftArm.xRot -= MathHelper.modelDegrees(90);
            model.leftArm.zRot = -Mth.cos(f5 * 0.6662F) * 0.1F;
        }
    };
}
