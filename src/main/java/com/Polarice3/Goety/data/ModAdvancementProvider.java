package com.Polarice3.Goety.data;

import com.Polarice3.Goety.Goety;
import com.Polarice3.Goety.common.items.ModItems;
import com.Polarice3.Goety.common.items.magic.MagicFocus;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricAdvancementProvider;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.network.chat.Component;

import java.util.function.Consumer;

public class ModAdvancementProvider extends FabricAdvancementProvider {

    protected ModAdvancementProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateAdvancement(Consumer<Advancement> consumer) {
        ModAdvancementGenerator.generate(consumer);
    }

    public static class ModAdvancementGenerator {

        public static void generate(Consumer<Advancement> saver) {
            Advancement advancement = Advancement.Builder.advancement().build(Goety.location("goety/craft_empty_focus"));
            addFocuses(Advancement.Builder.advancement()).parent(advancement).display(
                            ModItems.FOCUS_PACK,
                            Component.translatable("advancements.goety.craft_all_focus.title"),
                            Component.translatable("advancements.goety.craft_all_focus.description"),
                            null, FrameType.CHALLENGE, true, true, false)
                    .rewards(AdvancementRewards.Builder.experience(100))
                    .save(saver, "goety:goety/craft_all_focus");
        }
    }

    private static Advancement.Builder addFocuses(Advancement.Builder p_248814_) {
        ModItems.ITEMS.forEach(item ->
        {
            if (item instanceof MagicFocus) {
                p_248814_.addCriterion(item.getDescriptionId(), InventoryChangeTrigger.TriggerInstance.hasItems(item));
            }
        });

        return p_248814_;
    }
}
