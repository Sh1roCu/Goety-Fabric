package com.Polarice3.Goety.common.world.processors.ruletest;

import com.Polarice3.Goety.Goety;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTestType;

import java.util.function.Supplier;

public class ModRuleTests {

    public static final RuleTestType<RandomTagMatchTest> RANDOM_TAG_MATCH_TEST = register("random_tag_match", () -> () -> RandomTagMatchTest.CODEC);

    public static void init() {

    }

    private static <T extends RuleTest> RuleTestType<T> register(String name, Supplier<RuleTestType<T>> supplier) {
        return Registry.register(BuiltInRegistries.RULE_TEST, Goety.location(name), supplier.get());
    }
}
