package cn.sh1rocu.goety.util.transfer;

import net.fabricmc.fabric.api.lookup.v1.item.ItemApiLookup;
import net.fabricmc.fabric.api.transfer.v1.context.ContainerItemContext;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import net.minecraft.resources.ResourceLocation;

// TODO: 1.21Faric的ItemStorage已实现ITEM，移植到1.21时注意用自带的
public final class ItemItemStorage {
    public static final ItemApiLookup<Storage<ItemVariant>, ContainerItemContext> ITEM = ItemApiLookup.get(
            new ResourceLocation("fabric", "item_storage"), Storage.asClass(), ContainerItemContext.class);

}