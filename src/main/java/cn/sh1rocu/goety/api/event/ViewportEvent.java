package cn.sh1rocu.goety.api.event;

import com.mojang.blaze3d.shaders.FogShape;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.world.level.material.FogType;

@Environment(EnvType.CLIENT)
public abstract class ViewportEvent extends BaseEvent {
    private final GameRenderer renderer;
    private final Camera camera;
    private final double partialTick;

    public ViewportEvent(GameRenderer renderer, Camera camera, double partialTick) {
        this.renderer = renderer;
        this.camera = camera;
        this.partialTick = partialTick;
    }

    public static final Event<FovCallback> FOV = EventFactory.createArrayBacked(FovCallback.class, callbacks -> event -> {
        for (FovCallback callback : callbacks) callback.post(event);
    });

    public static final Event<CameraCallback> CAMERA = EventFactory.createArrayBacked(CameraCallback.class, callbacks -> event -> {
        for (CameraCallback post : callbacks) post.post(event);
    });

    public static final Event<RenderFogCallback> RENDER_FOG = EventFactory.createArrayBacked(RenderFogCallback.class, callbacks -> event -> {
        for (RenderFogCallback post : callbacks) post.post(event);
    });

    public interface FovCallback {
        void post(ComputeFov event);
    }

    public interface CameraCallback {
        void post(ComputeCameraAngles event);
    }

    public interface RenderFogCallback {
        void post(RenderFog event);
    }

    public GameRenderer getRenderer() {
        return this.renderer;
    }

    public Camera getCamera() {
        return this.camera;
    }

    public double getPartialTick() {
        return this.partialTick;
    }

    public static class ComputeFov extends ViewportEvent {
        private final boolean usedConfiguredFov;
        private double fov;

        public ComputeFov(GameRenderer renderer, Camera camera, double renderPartialTicks, double fov, boolean usedConfiguredFov) {
            super(renderer, camera, renderPartialTicks);
            this.usedConfiguredFov = usedConfiguredFov;
            this.setFOV(fov);
        }

        public double getFOV() {
            return this.fov;
        }

        public void setFOV(double fov) {
            this.fov = fov;
        }

        public boolean usedConfiguredFov() {
            return this.usedConfiguredFov;
        }
    }

    public static class ComputeCameraAngles extends ViewportEvent {
        private float yaw;
        private float pitch;
        private float roll;

        public ComputeCameraAngles(Camera camera, double renderPartialTicks, float yaw, float pitch, float roll) {
            super(Minecraft.getInstance().gameRenderer, camera, renderPartialTicks);
            this.setYaw(yaw);
            this.setPitch(pitch);
            this.setRoll(roll);
        }

        public float getYaw() {
            return this.yaw;
        }

        public void setYaw(float yaw) {
            this.yaw = yaw;
        }

        public float getPitch() {
            return this.pitch;
        }

        public void setPitch(float pitch) {
            this.pitch = pitch;
        }

        public float getRoll() {
            return this.roll;
        }

        public void setRoll(float roll) {
            this.roll = roll;
        }
    }

    public static class RenderFog extends ViewportEvent implements ICancellableEvent {
        private final FogRenderer.FogMode mode;
        private final FogType type;
        private float farPlaneDistance;
        private float nearPlaneDistance;
        private FogShape fogShape;

        public RenderFog(FogRenderer.FogMode mode, FogType type, Camera camera, float partialTicks, float nearPlaneDistance, float farPlaneDistance, FogShape fogShape) {
            super(Minecraft.getInstance().gameRenderer, camera, partialTicks);
            this.mode = mode;
            this.type = type;
            setFarPlaneDistance(farPlaneDistance);
            setNearPlaneDistance(nearPlaneDistance);
            setFogShape(fogShape);
        }

        public FogRenderer.FogMode getMode() {
            return mode;
        }

        public FogType getType() {
            return type;
        }

        public float getFarPlaneDistance() {
            return farPlaneDistance;
        }

        public float getNearPlaneDistance() {
            return nearPlaneDistance;
        }

        public FogShape getFogShape() {
            return fogShape;
        }

        public void setFarPlaneDistance(float distance) {
            farPlaneDistance = distance;
        }

        public void setNearPlaneDistance(float distance) {
            nearPlaneDistance = distance;
        }

        public void setFogShape(FogShape shape) {
            fogShape = shape;
        }

        public void scaleFarPlaneDistance(float factor) {
            farPlaneDistance *= factor;
        }

        public void scaleNearPlaneDistance(float factor) {
            nearPlaneDistance *= factor;
        }
    }
}
