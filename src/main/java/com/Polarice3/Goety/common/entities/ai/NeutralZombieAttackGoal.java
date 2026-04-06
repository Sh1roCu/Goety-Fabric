package com.Polarice3.Goety.common.entities.ai;

import net.minecraft.world.entity.PathfinderMob;

public class NeutralZombieAttackGoal extends ModMeleeAttackGoal {
    private final PathfinderMob zombie;
    private int raiseArmTicks;

    public NeutralZombieAttackGoal(PathfinderMob zombieIn, double speedIn, boolean longMemoryIn) {
        super(zombieIn, speedIn, longMemoryIn);
        this.zombie = zombieIn;
    }

    @Override
    public void start() {
        super.start();
        this.raiseArmTicks = 0;
    }

    @Override
    public void stop() {
        super.stop();
        this.zombie.setAggressive(false);
    }

    @Override
    public void tick() {
        super.tick();
        ++this.raiseArmTicks;
        this.zombie.setAggressive(this.raiseArmTicks >= 5 && this.getTicksUntilNextAttack() < this.getAttackInterval() / 2);

    }
}
