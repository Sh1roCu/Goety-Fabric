package com.Polarice3.Goety.common.events.spell;

import cn.sh1rocu.goety.api.event.ICancellableEvent;
import cn.sh1rocu.goety.api.event.PlayerEvent;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.world.entity.player.Player;

/**
 * ChangeSoulEnergyEvent is fired when player gains or loss Soul Energy. <br>
 * <br>
 * This event is fired via both {@link GoetyEventFactory#onSoulEnergyGain(Player, int)} & {@link GoetyEventFactory#onSoulEnergyLoss(Player, int)}.<br>
 * <br>
 * This event is {@link ICancellableEvent}.<br>
 * If this event is canceled, the amount is set to 0.<br>
 **/
public class ChangeSoulEnergyEvent extends PlayerEvent {
    private int soulChange;

    public static final Event<Gain.Callback> GAIN = EventFactory.createArrayBacked(Gain.Callback.class, callbacks -> event -> {
        for (Gain.Callback callback : callbacks) {
            callback.post(event);
        }
    });
    public static final Event<Loss.Callback> LOSS = EventFactory.createArrayBacked(Loss.Callback.class, callbacks -> event -> {
        for (Loss.Callback callback : callbacks) {
            callback.post(event);
        }
    });

    private ChangeSoulEnergyEvent(Player entity, int soulChange) {
        super(entity);
        this.soulChange = soulChange;
    }

    public int getSoulChange() {
        return this.soulChange;
    }

    public void setSoulChange(int soulChange) {
        this.soulChange = soulChange;
    }

    public static class Gain extends ChangeSoulEnergyEvent implements ICancellableEvent {
        public Gain(Player e, int soulChange) {
            super(e, soulChange);
        }

        public interface Callback {
            void post(Gain event);
        }
    }

    public static class Loss extends ChangeSoulEnergyEvent implements ICancellableEvent {
        public Loss(Player e, int soulChange) {
            super(e, soulChange);
        }

        public interface Callback {
            void post(Loss event);
        }
    }
}
