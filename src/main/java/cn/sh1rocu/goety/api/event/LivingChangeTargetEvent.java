package cn.sh1rocu.goety.api.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.world.entity.LivingEntity;

public class LivingChangeTargetEvent extends LivingEvent implements ICancellableEvent {
    public static final Event<Callback> EVENT = EventFactory.createArrayBacked(Callback.class, callbacks -> event -> {
        for (final Callback callback : callbacks)
            callback.post(event);
    });

    private final ILivingTargetType targetType;
    private final LivingEntity originalTarget;
    private LivingEntity newTarget;
    private boolean changedTarget = false;

    public LivingChangeTargetEvent(LivingEntity entity, LivingEntity originalTarget, ILivingTargetType targetType) {
        super(entity);
        this.originalTarget = originalTarget;
        this.newTarget = originalTarget;
        this.targetType = targetType;
    }

    public LivingEntity getNewTarget() {
        return newTarget;
    }

    public void setNewTarget(LivingEntity newTarget) {
        this.newTarget = newTarget;
        this.changedTarget = true;
    }

    public ILivingTargetType getTargetType() {
        return targetType;
    }

    public LivingEntity getOriginalTarget() {
        return originalTarget;
    }

    public boolean changedTarget() {
        return this.changedTarget;
    }

    public interface ILivingTargetType {

    }

    public enum LivingTargetType implements ILivingTargetType {

        MOB_TARGET,
        BEHAVIOR_TARGET
    }

    public interface Callback {
        void post(LivingChangeTargetEvent event);
    }
}