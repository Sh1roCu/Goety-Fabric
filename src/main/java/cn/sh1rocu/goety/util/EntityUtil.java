package cn.sh1rocu.goety.util;

import cn.sh1rocu.goety.mixin.accessor.EntityAccessor;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;

import java.util.function.BiPredicate;

public class EntityUtil {

    public static boolean isInFluid(Entity entity) {
        return !((EntityAccessor) entity).goety$fluidHeight().isEmpty();
    }

    public static boolean isInFluid(Entity entity, FluidState fluidState) {
        return fluidState.getTags().anyMatch(tag -> isInFluid(entity, tag));
    }

    public static boolean isInFluid(Entity entity, TagKey<Fluid> tagKey) {
        return entity.getFluidHeight(tagKey) > 0.0D;
    }

    public static boolean isInFluid(Entity entity, BiPredicate<TagKey<Fluid>, Double> predicate) {
        return isInFluid(entity, predicate, false);
    }

    public static boolean isInFluid(Entity entity, BiPredicate<TagKey<Fluid>, Double> predicate, boolean forAllTypes) {
        return forAllTypes ? ((EntityAccessor) entity).goety$fluidHeight().object2DoubleEntrySet().stream().allMatch(e -> predicate.test(e.getKey(), e.getDoubleValue()))
                : ((EntityAccessor) entity).goety$fluidHeight().object2DoubleEntrySet().stream().anyMatch(e -> predicate.test(e.getKey(), e.getDoubleValue()));
    }
}
