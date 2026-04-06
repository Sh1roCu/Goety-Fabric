package cn.sh1rocu.goety.api.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class LivingUseTotemEvent extends LivingEvent implements ICancellableEvent {
    private final DamageSource source;
    private final ItemStack totem;
    private final InteractionHand hand;

    public static final Event<Callback> EVENT = EventFactory.createArrayBacked(Callback.class, callbacks -> event -> {
        for (final Callback callback : callbacks)
            callback.post(event);
    });

    public LivingUseTotemEvent(LivingEntity entity, DamageSource source, ItemStack totem, InteractionHand hand) {
        super(entity);
        this.source = source;
        this.totem = totem;
        this.hand = hand;
    }

    public DamageSource getSource() {
        return source;
    }

    public ItemStack getTotem() {
        return totem;
    }

    public InteractionHand getHandHolding() {
        return hand;
    }

    public interface Callback {
        void post(LivingUseTotemEvent event);
    }
}