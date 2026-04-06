package cn.sh1rocu.goety.api.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.client.player.Input;
import net.minecraft.world.entity.player.Player;

public class MovementInputUpdateEvent extends BaseEvent {

    private final Player player;
    private final Input input;

    public static final Event<Callback> EVENT = EventFactory.createArrayBacked(Callback.class, callbacks -> event -> {
        for (Callback callback : callbacks) {
            callback.post(event);
        }
    });

    public interface Callback {
        void post(MovementInputUpdateEvent event);
    }

    public MovementInputUpdateEvent(Player player, Input input) {
        this.player = player;
        this.input = input;
    }

    public Player getEntity() {
        return player;
    }

    public Input getInput() {
        return input;
    }
}
