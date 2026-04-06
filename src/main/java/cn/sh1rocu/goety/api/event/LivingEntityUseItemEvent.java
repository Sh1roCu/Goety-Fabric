package cn.sh1rocu.goety.api.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public abstract class LivingEntityUseItemEvent extends LivingEvent {
    private final ItemStack item;
    private int duration;

    public static final Event<Start.Callback> START = EventFactory.createArrayBacked(Start.Callback.class, callbacks -> event -> {
        for (Start.Callback callback : callbacks)
            callback.post(event);
    });
    public static final Event<Tick.Callback> TICK = EventFactory.createArrayBacked(Tick.Callback.class, callbacks -> event -> {
        for (Tick.Callback callback : callbacks)
            callback.post(event);
    });
    public static final Event<Finish.Callback> FINISH = EventFactory.createArrayBacked(Finish.Callback.class, callbacks -> event -> {
        for (Finish.Callback callback : callbacks)
            callback.post(event);
    });


    private LivingEntityUseItemEvent(LivingEntity entity, ItemStack item, int duration) {
        super(entity);
        this.item = item;
        this.setDuration(duration);
    }

    public ItemStack getItem() {
        return item;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public static class Start extends LivingEntityUseItemEvent implements ICancellableEvent {
        public Start(LivingEntity entity, ItemStack item, int duration) {
            super(entity, item, duration);
        }

        public interface Callback {
            void post(Start event);
        }
    }

    public static class Tick extends LivingEntityUseItemEvent implements ICancellableEvent {
        public Tick(LivingEntity entity, ItemStack item, int duration) {
            super(entity, item, duration);
        }

        public interface Callback {
            void post(Tick event);
        }
    }

    public static class Finish extends LivingEntityUseItemEvent {
        private ItemStack result;

        public Finish(LivingEntity entity, ItemStack item, int duration, ItemStack result) {
            super(entity, item, duration);
            this.setResultStack(result);
        }

        public ItemStack getResultStack() {
            return result;
        }

        public void setResultStack(ItemStack result) {
            this.result = result;
        }

        public interface Callback {
            void post(Finish event);
        }

    }
}