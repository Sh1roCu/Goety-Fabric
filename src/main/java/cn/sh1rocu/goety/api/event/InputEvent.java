package cn.sh1rocu.goety.api.event;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.client.KeyMapping;
import net.minecraft.world.InteractionHand;

@Environment(EnvType.CLIENT)
public abstract class InputEvent extends BaseEvent {
    public static class InteractionKeyMappingTriggered extends InputEvent implements ICancellableEvent {
        public static final Event<IKMTCallback> EVENT = EventFactory.createWithPhases(IKMTCallback.class, callbacks -> event -> {
            for (IKMTCallback e : callbacks) e.onInteractionKeyMappingTriggered(event);
        }, HIGHEST, HIGH, Event.DEFAULT_PHASE, LOW, LOWEST);

        private final int button;
        private final KeyMapping keyMapping;
        private final InteractionHand hand;
        private boolean handSwing = true;

        public InteractionKeyMappingTriggered(int button, KeyMapping keyMapping, InteractionHand hand) {
            this.button = button;
            this.keyMapping = keyMapping;
            this.hand = hand;
        }

        public void setSwingHand(boolean value) {
            this.handSwing = value;
        }

        public boolean shouldSwingHand() {
            return this.handSwing;
        }

        public InteractionHand getHand() {
            return this.hand;
        }

        public boolean isAttack() {
            return this.button == 0;
        }

        public boolean isUseItem() {
            return this.button == 1;
        }

        public boolean isPickBlock() {
            return this.button == 2;
        }

        public KeyMapping getKeyMapping() {
            return this.keyMapping;
        }
    }

    public static class Key extends InputEvent {
        public static final Event<KeyCallback> EVENT = EventFactory.createArrayBacked(KeyCallback.class, callbacks -> event -> {
            for (KeyCallback e : callbacks) e.onKey(event);
        });

        private final int key;
        private final int scanCode;
        private final int action;
        private final int modifiers;

        public Key(int key, int scanCode, int action, int modifiers) {
            this.key = key;
            this.scanCode = scanCode;
            this.action = action;
            this.modifiers = modifiers;
        }

        public int getKey() {
            return this.key;
        }

        public int getScanCode() {
            return this.scanCode;
        }

        public int getAction() {
            return this.action;
        }

        public int getModifiers() {
            return this.modifiers;
        }
    }

    public interface IKMTCallback {
        void onInteractionKeyMappingTriggered(InteractionKeyMappingTriggered event);
    }

    public interface KeyCallback {
        void onKey(Key event);
    }
}
