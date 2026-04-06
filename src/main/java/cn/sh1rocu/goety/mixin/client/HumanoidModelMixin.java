package cn.sh1rocu.goety.mixin.client;

import com.Polarice3.Goety.api.magic.SpellPoses;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HumanoidModel.class)
public abstract class HumanoidModelMixin<T extends LivingEntity> {

    @Shadow
    public HumanoidModel.ArmPose rightArmPose;

    @Shadow
    public HumanoidModel.ArmPose leftArmPose;

    @Inject(method = "poseRightArm", at = @At("TAIL"))
    public void goety$poseRightArm(T livingEntity, CallbackInfo ci) {
        HumanoidModel<T> humanoidModel = (HumanoidModel<T>) (Object) this;
        if (this.rightArmPose == SpellPoses.SPELL) {
            SpellPoses.SPELL_HANDLER.handle(humanoidModel, livingEntity, HumanoidArm.RIGHT);
        } else if (this.rightArmPose == SpellPoses.FLIGHT_POSE) {
            SpellPoses.FLIGHT_POSE_HANDLER.handle(humanoidModel, livingEntity, HumanoidArm.RIGHT);
        } else if (this.rightArmPose == SpellPoses.HOLD_STAFF) {
            SpellPoses.HOLD_STAFF_HANDLER.handle(humanoidModel, livingEntity, HumanoidArm.RIGHT);
        }
    }

    @Inject(method = "poseLeftArm", at = @At("TAIL"))
    public void goety$poseLeftArm(T livingEntity, CallbackInfo ci) {
        HumanoidModel<T> humanoidModel = (HumanoidModel<T>) (Object) this;
        if (this.rightArmPose == SpellPoses.SPELL) {
            SpellPoses.SPELL_HANDLER.handle(humanoidModel, livingEntity, HumanoidArm.LEFT);
        } else if (this.rightArmPose == SpellPoses.FLIGHT_POSE) {
            SpellPoses.FLIGHT_POSE_HANDLER.handle(humanoidModel, livingEntity, HumanoidArm.LEFT);

        } else if (this.rightArmPose == SpellPoses.HOLD_STAFF) {
            SpellPoses.HOLD_STAFF_HANDLER.handle(humanoidModel, livingEntity, HumanoidArm.LEFT);
        }
    }
}