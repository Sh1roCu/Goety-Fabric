package com.Polarice3.Goety.common.events.spell;

import cn.sh1rocu.goety.api.event.ICancellableEvent;
import cn.sh1rocu.goety.api.event.LivingEvent;
import com.Polarice3.Goety.api.magic.ISpell;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

/**
 * CastingMagicEvent is fired when stopped using {@link com.Polarice3.Goety.common.items.magic.DarkWand} to cast a spell. <br>
 * <br>
 * This event is fired via the {@link GoetyEventFactory#onStopSpell(LivingEntity, ItemStack, ISpell, int, int)}.<br>
 * <br>
 * This event is {@link ICancellableEvent}.<br>
 * If this event is canceled, the spell is not cast and no effects that happen when stopped in the middle of casting will occur.<br>
 **/
public class StopMagicEvent extends LivingEvent implements ICancellableEvent {
    private final ISpell spell;
    private final ItemStack useItem;
    private final int castTime;
    private final int timeRemaining;

    public static final Event<Callback> EVENT = EventFactory.createArrayBacked(Callback.class, callbacks -> event -> {
        for (Callback callback : callbacks) {
            callback.post(event);
        }
    });

    public StopMagicEvent(LivingEntity entity, ItemStack useItem, ISpell spell, int castTime, int timeRemaining) {
        super(entity);
        this.useItem = useItem;
        this.spell = spell;
        this.castTime = castTime;
        this.timeRemaining = timeRemaining;
    }

    public ISpell getSpell() {
        return this.spell;
    }

    public ItemStack getUseItem() {
        return this.useItem;
    }

    public int castingTime() {
        return this.castTime;
    }

    public int getTimeRemaining() {
        return this.timeRemaining;
    }

    public interface Callback {
        void post(StopMagicEvent event);
    }
}
