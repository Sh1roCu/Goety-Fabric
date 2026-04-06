package cn.sh1rocu.goety.mixin.accessor;

import net.minecraft.world.level.levelgen.structure.Structure;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Structure.class)
public interface StructureAccessor {

    @Accessor("settings")
    Structure.StructureSettings goety$getSettings();

    @Accessor("settings")
    void goety$setSettings(Structure.StructureSettings config);
}