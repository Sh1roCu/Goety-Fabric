package cn.sh1rocu.goety.api.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;

import java.util.List;

// PortingLib
public class ExplosionEvent {

    public static Event<Start> START = EventFactory.createArrayBacked(Start.class, callbacks -> ((world, explosion) -> {
        for (Start event : callbacks)
            if (event.post(world, explosion))
                return true;
        return false;
    }));

    public static Event<Detonate> DETONATE = EventFactory.createArrayBacked(Detonate.class, callbacks -> ((world, explosion, list, diameter) -> {
        for (Detonate event : callbacks)
            event.post(world, explosion, list, diameter);
    }));

    @FunctionalInterface
    public interface Start {
        boolean post(Level world, Explosion explosion);
    }

    @FunctionalInterface
    public interface Detonate {
        void post(Level world, Explosion explosion, List<Entity> list, double diameter);
    }
}