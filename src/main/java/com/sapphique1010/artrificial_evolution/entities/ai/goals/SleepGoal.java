package com.sapphique1010.artrificial_evolution.entities.ai.goals;

import com.sapphique1010.artrificial_evolution.entities.FoxWolfEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.passive.FoxEntity;

import java.util.EnumSet;
import java.util.Random;

public class SleepGoal extends BaseGoal {
    FoxWolfEntity foxwolf;
    Random rand = new Random();
    private int field_220825_c = rand.nextInt (140);

    public SleepGoal(FoxWolfEntity foxwolf) {
        super(foxwolf);
        this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK, Goal.Flag.JUMP));
    }

    /**
     * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
     * method as well.
     */
    @Override
    public boolean shouldExecute() {
        if (foxwolf.moveStrafing == 0.0F && foxwolf.moveVertical == 0.0F && foxwolf.moveForward == 0.0F) {
            return this.func_220823_j() || foxwolf.isSleeping();
        } else {
            return false;
        }
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    @Override
    public boolean shouldContinueExecuting() {
        return this.func_220823_j();
    }

    private boolean func_220823_j() {
        if (this.field_220825_c > 0) {
            --this.field_220825_c;
            return false;
        } else {
            return foxwolf.world.isDaytime() && foxwolf.func_220813_g() && !this.func_220814_h();
        }
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    @Override
    public void startExecuting() {
        foxwolf.setSitting(false);
        foxwolf.setJumping(false);
        foxwolf.setSleeping(true);
        foxwolf.getNavigator().clearPath();
        foxwolf.getMoveHelper().setMoveTo(foxwolf.getPosX(), foxwolf.getPosY(), foxwolf.getPosZ(), 0.0D);
    }
}