package cn.sh1rocu.goety.api.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrownEnderpearl;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public abstract class EntityTeleportEvent extends EntityEvent implements ICancellableEvent {
    protected double targetX;
    protected double targetY;
    protected double targetZ;

    public static final Event<TeleportCommand.Callback> TP_CMD = EventFactory.createArrayBacked(TeleportCommand.Callback.class, callbacks -> event -> {
        for (final TeleportCommand.Callback callback : callbacks)
            callback.post(event);
    });
    public static final Event<SpreadPlayersCommand.Callback> SPREAD_PLAYERS_CMD = EventFactory.createArrayBacked(SpreadPlayersCommand.Callback.class, callbacks -> event -> {
        for (final SpreadPlayersCommand.Callback callback : callbacks)
            callback.post(event);
    });
    public static final Event<EnderEntity.Callback> ENDER_ENTITY = EventFactory.createArrayBacked(EnderEntity.Callback.class, callbacks -> event -> {
        for (final EnderEntity.Callback callback : callbacks)
            callback.post(event);
    });
    public static final Event<EnderPearl.Callback> ENDER_PEARL = EventFactory.createArrayBacked(EnderPearl.Callback.class, callbacks -> event -> {
        for (final EnderPearl.Callback callback : callbacks)
            callback.post(event);
    });
    public static final Event<ChorusFruit.Callback> CHORUS = EventFactory.createArrayBacked(ChorusFruit.Callback.class, callbacks -> event -> {
        for (ChorusFruit.Callback callback : callbacks)
            callback.post(event);
    });

    public EntityTeleportEvent(Entity entity, double targetX, double targetY, double targetZ) {
        super(entity);
        this.targetX = targetX;
        this.targetY = targetY;
        this.targetZ = targetZ;
    }

    public double getTargetX() {
        return targetX;
    }

    public void setTargetX(double targetX) {
        this.targetX = targetX;
    }

    public double getTargetY() {
        return targetY;
    }

    public void setTargetY(double targetY) {
        this.targetY = targetY;
    }

    public double getTargetZ() {
        return targetZ;
    }

    public void setTargetZ(double targetZ) {
        this.targetZ = targetZ;
    }

    public Vec3 getTarget() {
        return new Vec3(this.targetX, this.targetY, this.targetZ);
    }

    public double getPrevX() {
        return getEntity().getX();
    }

    public double getPrevY() {
        return getEntity().getY();
    }

    public double getPrevZ() {
        return getEntity().getZ();
    }

    public Vec3 getPrev() {
        return getEntity().position();
    }

    public static class TeleportCommand extends EntityTeleportEvent implements ICancellableEvent {
        public TeleportCommand(Entity entity, double targetX, double targetY, double targetZ) {
            super(entity, targetX, targetY, targetZ);
        }

        public interface Callback {
            void post(TeleportCommand event);
        }
    }

    public static class SpreadPlayersCommand extends EntityTeleportEvent implements ICancellableEvent {
        public SpreadPlayersCommand(Entity entity, double targetX, double targetY, double targetZ) {
            super(entity, targetX, targetY, targetZ);
        }

        public interface Callback {
            void post(SpreadPlayersCommand event);
        }
    }

    public static class EnderEntity extends EntityTeleportEvent implements ICancellableEvent {
        private final LivingEntity entityLiving;

        public EnderEntity(LivingEntity entity, double targetX, double targetY, double targetZ) {
            super(entity, targetX, targetY, targetZ);
            this.entityLiving = entity;
        }

        public LivingEntity getEntityLiving() {
            return entityLiving;
        }

        public interface Callback {
            void post(EnderEntity event);
        }
    }

    public static class EnderPearl extends EntityTeleportEvent implements ICancellableEvent {
        private final ServerPlayer player;
        private final ThrownEnderpearl pearlEntity;
        private float attackDamage;
        private final HitResult hitResult;

        public EnderPearl(ServerPlayer entity, double targetX, double targetY, double targetZ, ThrownEnderpearl pearlEntity, float attackDamage, HitResult hitResult) {
            super(entity, targetX, targetY, targetZ);
            this.pearlEntity = pearlEntity;
            this.player = entity;
            this.attackDamage = attackDamage;
            this.hitResult = hitResult;
        }

        public ThrownEnderpearl getPearlEntity() {
            return pearlEntity;
        }

        public ServerPlayer getPlayer() {
            return player;
        }

        @Nullable
        public HitResult getHitResult() {
            return this.hitResult;
        }

        public float getAttackDamage() {
            return attackDamage;
        }

        public void setAttackDamage(float attackDamage) {
            this.attackDamage = attackDamage;
        }

        public interface Callback {
            void post(EnderPearl event);
        }
    }

    public static class ChorusFruit extends EntityTeleportEvent implements ICancellableEvent {
        private final LivingEntity entityLiving;

        public ChorusFruit(LivingEntity entity, double targetX, double targetY, double targetZ) {
            super(entity, targetX, targetY, targetZ);
            this.entityLiving = entity;
        }

        public LivingEntity getEntityLiving() {
            return entityLiving;
        }

        public interface Callback {
            void post(ChorusFruit event);
        }
    }
}