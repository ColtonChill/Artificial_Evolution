package com.sapphique1010.artrificial_evolution.entities.ai.goals;

import net.minecraft.entity.ai.goal.Goal;

public class PounceGoal extends Goal {
    /**
     * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
     * method as well.
     */
    @Override
    public boolean shouldExecute() {
        return false;
    }
}
