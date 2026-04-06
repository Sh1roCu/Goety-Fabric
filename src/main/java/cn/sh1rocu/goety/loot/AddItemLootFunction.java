package cn.sh1rocu.goety.loot;

import com.Polarice3.Goety.init.ModLootModifier;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

public class AddItemLootFunction extends LootItemConditionalFunction {

    private final Item item;
    private final int count;

    public AddItemLootFunction(LootItemCondition[] conditionsIn, Item addedItemIn) {
        this(conditionsIn, addedItemIn, 1);
    }

    public AddItemLootFunction(LootItemCondition[] conditionsIn, Item addedItemIn, int count) {
        super(conditionsIn);
        this.item = addedItemIn;
        this.count = count;
    }

    @Override
    protected ItemStack run(ItemStack itemStack, LootContext context) {
        return new ItemStack(this.item, this.count);
    }

    @Override
    public LootItemFunctionType getType() {
        return ModLootModifier.ADD_LOOT_TYPE;
    }

    public static Builder<?> builder(Item item, int count) {
        return simpleBuilder(conditions -> new AddItemLootFunction(conditions, item, count));
    }

    public static class Serializer extends LootItemConditionalFunction.Serializer<AddItemLootFunction> {

        @Override
        public void serialize(JsonObject object, AddItemLootFunction function, JsonSerializationContext context) {
            super.serialize(object, function, context);
            object.addProperty("item", BuiltInRegistries.ITEM.getKey(function.item).toString());
            object.addProperty("count", function.count);
        }

        @Override
        public AddItemLootFunction deserialize(JsonObject object, JsonDeserializationContext context, LootItemCondition[] conditions) {
            return new AddItemLootFunction(conditions, BuiltInRegistries.ITEM.get(new ResourceLocation(GsonHelper.getAsString(object, "item"))),
                    GsonHelper.getAsInt(object, "count", 1));
        }
    }
}
