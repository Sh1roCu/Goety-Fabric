package com.Polarice3.Goety.common.events.spell;

import cn.sh1rocu.goety.api.event.ICancellableEvent;
import cn.sh1rocu.goety.api.event.LivingEvent;
import com.Polarice3.Goety.api.magic.ISpell;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

/**
 * CastingMagicEvent is fired when using {@link com.Polarice3.Goety.common.items.magic.DarkWand} with a spell. <br>
 * <br>
 * This event is fired via the {@link GoetyEventFactory#onCastingSpell(LivingEntity, ItemStack, ISpell, int)}.<br>
 * <br>
 * This event is {@link ICancellableEvent}.<br>
 * If this event is canceled, the spell is not cast.<br>
 **/
public class CastingMagicEvent extends LivingEvent implements ICancellableEvent {
    private ISpell spell;
    private final ItemStack useItem;
    private final int castTime;

    public static final Event<Callback> EVENT = EventFactory.createArrayBacked(Callback.class, callbacks -> event -> {
        for (Callback callback : callbacks) {
            callback.post(event);
        }
    });

    public CastingMagicEvent(LivingEntity entity, ItemStack useItem, ISpell spell, int castTime) {
        super(entity);
        this.useItem = useItem;
        this.spell = spell;
        this.castTime = castTime;
    }

    public ISpell getSpell() {
        return this.spell;
    }

    public void setSpell(ISpell spell) {
        this.spell = spell;
    }

    public ItemStack getUseItem() {
        return this.useItem;
    }

    public int castingTime() {
        return this.castTime;
    }

    public interface Callback {
        void post(CastingMagicEvent event);
    }
}
