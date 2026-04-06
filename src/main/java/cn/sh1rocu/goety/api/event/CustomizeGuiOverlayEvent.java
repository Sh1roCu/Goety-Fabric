package cn.sh1rocu.goety.api.event;

import com.mojang.blaze3d.platform.Window;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.LerpingBossEvent;

@Environment(EnvType.CLIENT)
public abstract class CustomizeGuiOverlayEvent extends BaseEvent {

    private final Window window;
    private final GuiGraphics guiGraphics;
    private final float partialTick;

    public static final Event<BossEventProgressCallback> BOSS_EVENT_PROGRESS = EventFactory.createArrayBacked(BossEventProgressCallback.class, callbacks -> event -> {
        for (BossEventProgressCallback callback : callbacks) {
            callback.post(event);
        }
    });

    public interface BossEventProgressCallback {
        void post(BossEventProgress event);
    }

    protected CustomizeGuiOverlayEvent(Window window, GuiGraphics guiGraphics, float partialTick) {
        this.window = window;
        this.guiGraphics = guiGraphics;
        this.partialTick = partialTick;
    }

    public Window getWindow() {
        return window;
    }

    public GuiGraphics getGuiGraphics() {
        return guiGraphics;
    }

    public float getPartialTick() {
        return partialTick;
    }

    public static class BossEventProgress extends CustomizeGuiOverlayEvent implements ICancellableEvent {

        private final LerpingBossEvent bossEvent;
        private final int x;
        private final int y;
        private int increment;

        public BossEventProgress(Window window, GuiGraphics guiGraphics, float partialTick, LerpingBossEvent bossEvent, int x, int y, int increment) {
            super(window, guiGraphics, partialTick);
            this.bossEvent = bossEvent;
            this.x = x;
            this.y = y;
            this.increment = increment;
        }

        public LerpingBossEvent getBossEvent() {
            return bossEvent;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public int getIncrement() {
            return increment;
        }

        public void setIncrement(int increment) {
            this.increment = increment;
        }
    }
}
