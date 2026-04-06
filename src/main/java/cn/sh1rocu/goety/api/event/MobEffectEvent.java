package cn.sh1rocu.goety.api.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.Nullable;

public abstract class MobEffectEvent extends LivingEvent {
    @Nullable
    protected final MobEffectInstance effectInstance;

    public static final Event<Remove.Callback> REMOVE = EventFactory.createArrayBacked(Remove.Callback.class, callbacks -> event -> {
        for (Remove.Callback c : callbacks)
            c.post(event);
    });
    public static final Event<Applicable.Callback> APPLICABLE = EventFactory.createArrayBacked(Applicable.Callback.class, callbacks -> effect -> {
        for (final Applicable.Callback callback : callbacks)
            callback.post(effect);
    });
    public static final Event<Added.Callback> ADDED = EventFactory.createArrayBacked(Added.Callback.class, callbacks -> event -> {
        for (final Added.Callback callback : callbacks)
            callback.post(event);
    });
    public static final Event<Expired.Callback> EXPIRED = EventFactory.createArrayBacked(Expired.Callback.class, callbacks -> event -> {
        for (final Expired.Callback callback : callbacks)
            callback.post(event);
    });

    protected MobEffectEvent(LivingEntity living, @Nullable MobEffectInstance effectInstance) {
        super(living);
        this.effectInstance = effectInstance;
    }

    @Nullable
    public MobEffectInstance getEffectInstance() {
        return effectInstance;
    }

    public enum Result {
        DENY,
        DEFAULT,
        ALLOW
    }

    public static class Remove extends MobEffectEvent implements ICancellableEvent {
        private final MobEffect effect;

        public Remove(LivingEntity living, MobEffect effect) {
            super(living, living.getEffect(effect));
            this.effect = effect;
        }

        public Remove(LivingEntity living, MobEffectInstance effectInstance) {
            super(living, effectInstance);
            this.effect = effectInstance.getEffect();
        }

        public MobEffect getEffect() {
            return this.effect;
        }

        @Override
        @Nullable
        public MobEffectInstance getEffectInstance() {
            return super.getEffectInstance();
        }

        public interface Callback {
            void post(Remove event);
        }
    }

    public static class Applicable extends MobEffectEvent {
        protected Result result = Result.DEFAULT;

        public Applicable(LivingEntity living, MobEffectInstance effectInstance) {
            super(living, effectInstance);
        }

        @Override
        public MobEffectInstance getEffectInstance() {
            return super.getEffectInstance();
        }

        public void setResult(Result result) {
            this.result = result;
        }

        public Result getResult() {
            return this.result;
        }

        public interface Callback {
            void post(Applicable event);
        }
    }

    public static class Added extends MobEffectEvent {
        private final MobEffectInstance oldEffectInstance;
        private final Entity source;

        public Added(LivingEntity living, MobEffectInstance oldEffectInstance, MobEffectInstance newEffectInstance, Entity source) {
            super(living, newEffectInstance);
            this.oldEffectInstance = oldEffectInstance;
            this.source = source;
        }

        @Override
        public MobEffectInstance getEffectInstance() {
            return super.getEffectInstance();
        }

        @Nullable
        public MobEffectInstance getOldEffectInstance() {
            return oldEffectInstance;
        }

        @Nullable
        public Entity getEffectSource() {
            return source;
        }

        public interface Callback {
            void post(Added event);
        }
    }

    public static class Expired extends MobEffectEvent implements ICancellableEvent {
        public Expired(LivingEntity living, MobEffectInstance effectInstance) {
            super(living, effectInstance);
        }

        public interface Callback {
            void post(Expired event);
        }
    }
}