package cn.sh1rocu.goety.api.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.Nullable;

public class LivingVisibilityEvent extends LivingEvent {
    private double visibilityModifier;
    @Nullable
    private final Entity lookingEntity;

    public static final Event<Callback> EVENT = EventFactory.createArrayBacked(Callback.class, callbacks -> event -> {
        for (Callback callback : callbacks)
            callback.post(event);
    });

    public LivingVisibilityEvent(LivingEntity livingEntity, @Nullable Entity lookingEntity, double originalMultiplier) {
        super(livingEntity);
        this.visibilityModifier = originalMultiplier;
        this.lookingEntity = lookingEntity;
    }

    public void modifyVisibility(double mod) {
        visibilityModifier *= mod;
    }

    public double getVisibilityModifier() {
        return visibilityModifier;
    }

    @Nullable
    public Entity getLookingEntity() {
        return lookingEntity;
    }

    public interface Callback {
        void post(LivingVisibilityEvent event);
    }
}