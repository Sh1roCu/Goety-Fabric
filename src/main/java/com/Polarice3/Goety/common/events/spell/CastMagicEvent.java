package com.Polarice3.Goety.common.events.spell;

import cn.sh1rocu.goety.api.event.ICancellableEvent;
import cn.sh1rocu.goety.api.event.LivingEvent;
import com.Polarice3.Goety.api.magic.ISpell;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.world.entity.LivingEntity;

/**
 * CastMagicEvent is fired when finished using {@link com.Polarice3.Goety.common.items.magic.DarkWand} to cast spell. <br>
 * <br>
 * This event is fired via the {@link GoetyEventFactory#onCastSpell(LivingEntity, ISpell)}.<br>
 * <br>
 * This event is {@link ICancellableEvent}.<br>
 * If this event is canceled, the spell is not cast.<br>
 **/
public class CastMagicEvent extends LivingEvent implements ICancellableEvent {
    private ISpell spell;

    public static final Event<Callback> EVENT = EventFactory.createArrayBacked(Callback.class, callbacks -> event -> {
        for (Callback callback : callbacks) {
            callback.post(event);
        }
    });

    public CastMagicEvent(LivingEntity entity, ISpell spell) {
        super(entity);
        this.spell = spell;
    }

    public ISpell getSpell() {
        return this.spell;
    }

    public void setSpell(ISpell spell) {
        this.spell = spell;
    }

    public interface Callback {
        void post(CastMagicEvent event);
    }
}
