package cn.sh1rocu.goety.api.event;

import net.minecraft.world.entity.Entity;

public class EntityEvent extends BaseEvent {

    private final Entity entity;

    public EntityEvent(Entity entity) {
        this.entity = entity;
    }

    public Entity getEntity() {
        return entity;
    }
}
