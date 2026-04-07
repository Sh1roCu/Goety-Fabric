package cn.sh1rocu.goety.mixin.accessor;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(LivingEntity.class)
public interface LivingEntityAccessor {

    @Accessor("lastDamageSource")
    void goety$setLastDamageSource(DamageSource lastDamageSource);

    @Accessor("lastDamageStamp")
    void goety$setLastDamageStamp(long lastDamageStamp);
}
