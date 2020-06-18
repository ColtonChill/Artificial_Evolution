package com.sapphique1010.artrificial_evolution.entities.ai.goals;

import com.sapphique1010.artrificial_evolution.entities.FoxWolfEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.passive.FoxEntity;

import java.util.EnumSet;

public class JumpGoal extends Goal {
    int delay;
    FoxWolfEntity foxwolf ;

    public JumpGoal(FoxWolfEntity foxwolf) {
        this.foxwolf = foxwolf;
        this.setMutexFlags(EnumSet.of(Goal.Flag.LOOK, Goal.Flag.JUMP, Goal.Flag.MOVE));
    }


    /**
     * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
     * method as well.
     */
    @Override
    public boolean shouldExecute() {
        return false;
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    @Override
    public boolean shouldContinueExecuting() {
        return this.shouldExecute() && this.delay > 0;
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    @Override
    public void startExecuting() {
        this.delay = 40;
    }

//    /**
//     * Reset the task's internal state. Called when this task is interrupted by another one
//     */
//    @Override
//    public void resetTask() {
//        foxwolf.setStuck(false);
//    }

    /**
     * Keep ticking a continuous task that has already been started
     */
    @Override
    public void tick() {
        --this.delay;
    }
}
