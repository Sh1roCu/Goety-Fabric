package com.Polarice3.Goety.common.events.spell;

import cn.sh1rocu.goety.api.event.ICancellableEvent;
import cn.sh1rocu.goety.api.event.LivingEvent;
import com.Polarice3.Goety.api.magic.ISpell;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

/**
 * CastMagicEvent is fired when right-clicking on a target {@link com.Polarice3.Goety.common.items.magic.DarkWand} with a spell. <br>
 * <br>
 * This event is fired via the {@link GoetyEventFactory#onTouchBasedSpell(LivingEntity, ItemStack, ISpell)}.<br>
 * <br>
 * This event is {@link ICancellableEvent}.<br>
 **/
public class TouchMagicEvent extends LivingEvent implements ICancellableEvent {
    private ISpell spell;
    private final ItemStack useItem;

    public static final Event<Callback> EVENT = EventFactory.createArrayBacked(Callback.class, callbacks -> event -> {
        for (Callback callback : callbacks) {
            callback.post(event);
        }
    });

    public TouchMagicEvent(LivingEntity entity, ItemStack useItem, ISpell spell) {
        super(entity);
        this.useItem = useItem;
        this.spell = spell;
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

    public interface Callback {
        void post(TouchMagicEvent event);
    }
}
