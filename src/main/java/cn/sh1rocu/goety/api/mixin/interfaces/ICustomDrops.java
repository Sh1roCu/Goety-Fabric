package cn.sh1rocu.goety.api.mixin.interfaces;

import net.minecraft.world.entity.item.ItemEntity;

import java.util.Collection;

// From PortingLib
public interface ICustomDrops {

    default Collection<ItemEntity> goety$captureDrops() {
        throw new RuntimeException("this should be overridden via mixin. what?");
    }

    default Collection<ItemEntity> goety$captureDrops(Collection<ItemEntity> value) {
        throw new RuntimeException("this should be overridden via mixin. what?");
    }
}