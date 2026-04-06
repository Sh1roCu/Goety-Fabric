package cn.sh1rocu.goety.api.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;

public class LivingHurtEvent extends LivingEvent implements ICancellableEvent {
    private final DamageSource source;
    private float amount;

    public static final Event<Callback> EVENT = EventFactory.createWithPhases(Callback.class, callbacks -> event -> {
        for (Callback e : callbacks)
            e.post(event);
    }, BaseEvent.HIGHEST, BaseEvent.HIGH, Event.DEFAULT_PHASE, BaseEvent.LOW, BaseEvent.LOWEST);

    public LivingHurtEvent(LivingEntity entity, DamageSource source, float amount) {
        super(entity);
        this.source = source;
        this.amount = amount;
    }

    public DamageSource getSource() {
        return source;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public interface Callback {
        void post(LivingHurtEvent event);
    }
}