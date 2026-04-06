package cn.sh1rocu.goety.asm;

import com.chocohead.mm.api.ClassTinkerers;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.MappingResolver;

public class EnumRiser implements Runnable {

    @Override
    public void run() {
        MappingResolver mappingResolver = FabricLoader.getInstance().getMappingResolver();

        String armPose = mappingResolver.mapClassName("intermediary", "net.minecraft.class_572$class_573");
        ClassTinkerers.enumBuilder(armPose, boolean.class)
                .addEnum("GOETY_SPELL", false)
                .addEnum("GOETY_FLYING", false)
                .addEnum("HOLD_STAFF", false)
                .build();

        String enchantCategory = mappingResolver.mapClassName("intermediary", "net.minecraft.class_1886");
        ClassTinkerers.enumBuilder(enchantCategory)
                .addEnumSubclass("RINGS", "cn.sh1rocu.goety.asm.enchantment.RingsCategory")
                .addEnumSubclass("FOCUS", "cn.sh1rocu.goety.asm.enchantment.FocusCategory")
                .build();
    }
}
