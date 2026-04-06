package cn.sh1rocu.goety.loot;

import com.Polarice3.Goety.init.ModLootModifier;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

public class InstantPickFunction extends LootItemConditionalFunction {

    public InstantPickFunction(LootItemCondition[] lootItemConditions) {
        super(lootItemConditions);
    }

    @Override
    public LootItemFunctionType getType() {
        return ModLootModifier.INSTANT_PICK_TYPE;
    }

    @Override
    protected ItemStack run(ItemStack itemStack, LootContext context) {
        Entity entity = context.getParamOrNull(LootContextParams.THIS_ENTITY);
        if (entity instanceof Player player && player.addItem(itemStack)) {
            return ItemStack.EMPTY;
        }
        return itemStack;
    }

    public static class Serializer extends LootItemConditionalFunction.Serializer<InstantPickFunction> {
        @Override
        public InstantPickFunction deserialize(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext, LootItemCondition[] lootItemConditions) {
            return new InstantPickFunction(lootItemConditions);
        }
    }
}
