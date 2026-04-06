package cn.sh1rocu.goety.api.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.world.entity.LivingEntity;

public class LivingTickEvent extends LivingEvent implements ICancellableEvent {
    public static final Event<Callback> EVENT = EventFactory.createArrayBacked(Callback.class, callbacks -> (event) -> {
        for (Callback callback : callbacks)
            callback.post(event);
    });

    public LivingTickEvent(LivingEntity e) {
        super(e);
    }

    public interface Callback {
        void post(LivingTickEvent event);
    }
}