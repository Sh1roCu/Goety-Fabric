package cn.sh1rocu.goety.mixin.accessor;

import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureSpawnOverride;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

@Mixin(Structure.StructureSettings.class)
public interface StructureSettingsAccessor {

    @Accessor("spawnOverrides")
    void goety$setSpawnOverrides(Map<MobCategory, StructureSpawnOverride> spawnOverrides);
}