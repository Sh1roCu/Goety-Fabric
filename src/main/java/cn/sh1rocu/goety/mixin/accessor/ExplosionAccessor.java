package cn.sh1rocu.goety.mixin.accessor;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.ExplosionDamageCalculator;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Explosion.class)
public interface ExplosionAccessor {

    @Accessor("level")
    Level goety$getLevel();

    @Accessor("x")
    double goety$getX();

    @Accessor("y")
    double goety$getY();

    @Accessor("z")
    double goety$getZ();

    @Accessor("radius")
    float goety$getRadius();

    @Accessor("fire")
    boolean goety$isFire();

    @Accessor("blockInteraction")
    Explosion.BlockInteraction goety$getBlockInteraction();

    @Accessor("damageCalculator")
    ExplosionDamageCalculator goety$getDamageCalculator();

    @Accessor("random")
    RandomSource goety$getRandom();

    @Accessor("toBlow")
    ObjectArrayList<BlockPos> goety$getToBlow();
}
