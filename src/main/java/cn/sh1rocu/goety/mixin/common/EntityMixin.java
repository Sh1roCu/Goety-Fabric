package cn.sh1rocu.goety.mixin.common;

import cn.sh1rocu.goety.api.extension.IEntityListener;
import cn.sh1rocu.goety.api.extension.IEntityPersistentData;
import cn.sh1rocu.goety.api.extension.IPushedExtension;
import cn.sh1rocu.goety.api.mixin.interfaces.ICustomDrops;
import com.Polarice3.Goety.Goety;
import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Collection;

@Mixin(Entity.class)
public class EntityMixin implements ICustomDrops, IEntityPersistentData {

    @Inject(method = "setPosRaw", at = @At("TAIL"))
    private void goety$setPosRaw(double x, double y, double z, CallbackInfo ci) {
        Entity self = (Entity) (Object) this;
        if (self instanceof IEntityListener entity && entity.isAddedToWorld() && !self.level.isClientSide && !self.isRemoved())
            self.level.getChunk((int) Math.floor(x) >> 4, (int) Math.floor(z) >> 4);
    }

    // start1
    // Based on Kilt(https://github.com/KiltMC/Kilt/blob/version/1.20.1/src/main/java/xyz/bluspring/kilt/forgeinjects/world/entity/EntityInject.java)
    @WrapOperation(method = "updateFluidHeightAndDoFluidPushing", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/material/FluidState;is(Lnet/minecraft/tags/TagKey;)Z", ordinal = 0))
    private boolean goety$checkIsValidFluidType(FluidState instance, TagKey<Fluid> tag, Operation<Boolean> original, @Share(value = "fluidTag", namespace = Goety.MOD_ID) LocalRef<FluidState> fluidStateRef) {
        fluidStateRef.set(instance);
        return original.call(instance, tag);
    }

    @Definition(id = "bl", local = @Local(type = boolean.class, ordinal = 0))
    @Expression("bl")
    @ModifyExpressionValue(method = "updateFluidHeightAndDoFluidPushing", at = @At("MIXINEXTRAS:EXPRESSION"))
    private boolean goety$checkIsPushedByFluid(boolean original, @Share(value = "fluidTag", namespace = Goety.MOD_ID) LocalRef<FluidState> fluidStateRef) {
        Entity self = (Entity) (Object) this;
        if (self instanceof IPushedExtension custom) {
            return custom.isPushedByFluid(fluidStateRef.get());
        }

        return original;
    }
    // end1

    // start2
    // From PortingLib
    @Unique
    private Collection<ItemEntity> goety$captureDrops = null;

    @WrapWithCondition(
            method = "spawnAtLocation(Lnet/minecraft/world/item/ItemStack;F)Lnet/minecraft/world/entity/item/ItemEntity;",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/Level;addFreshEntity(Lnet/minecraft/world/entity/Entity;)Z"
            )
    )
    public boolean goety$captureDrops(Level level, Entity entity) {
        if (goety$captureDrops != null && entity instanceof ItemEntity item) {
            goety$captureDrops.add(item);
            return false;
        }
        return true;
    }

    @Unique
    @Override
    public Collection<ItemEntity> goety$captureDrops() {
        return goety$captureDrops;
    }

    @Unique
    @Override
    public Collection<ItemEntity> goety$captureDrops(Collection<ItemEntity> value) {
        Collection<ItemEntity> ret = goety$captureDrops;
        goety$captureDrops = value;
        return ret;
    }
    //end2

    @Unique
    private CompoundTag goety$persistentData;

    @Unique
    @Override
    public CompoundTag goety$getPersistentData() {
        if (this.goety$persistentData == null) {
            this.goety$persistentData = new CompoundTag();
        }
        return goety$persistentData;
    }

    @Inject(method = "saveWithoutId", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;addAdditionalSaveData(Lnet/minecraft/nbt/CompoundTag;)V"))
    private void goety$savePersistentData(CompoundTag nbt, CallbackInfoReturnable<CompoundTag> cir) {
        if (this.goety$persistentData != null) {
            nbt.put(FORGE_TAG, this.goety$persistentData.copy());
        }
    }

    @Inject(method = "load", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;readAdditionalSaveData(Lnet/minecraft/nbt/CompoundTag;)V"))
    private void goety$loadPersistentData(CompoundTag nbt, CallbackInfo ci) {
        if (nbt.contains(FORGE_TAG, Tag.TAG_COMPOUND)) {
            goety$persistentData = nbt.getCompound(FORGE_TAG);
        }
    }
}
