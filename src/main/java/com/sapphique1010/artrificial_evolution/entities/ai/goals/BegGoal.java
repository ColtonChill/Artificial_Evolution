package com.sapphique1010.artrificial_evolution.entities.ai.goals;

import java.util.EnumSet;

import com.sapphique1010.artrificial_evolution.entities.FoxWolfEntity;
import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class BegGoal extends Goal {
    private final FoxWolfEntity foxwolf;
    private PlayerEntity player;
    private final World world;
    private final float minPlayerDistance;
    private int timeoutCounter;
    private final EntityPredicate field_220688_f;

    public BegGoal(FoxWolfEntity foxwolf, float minDistance) {
        this.foxwolf = foxwolf;
        this.world = foxwolf.world;
        this.minPlayerDistance = minDistance;
        this.field_220688_f = (new EntityPredicate()).setDistance((double)minDistance).allowInvulnerable().allowFriendlyFire().setSkipAttackChecks();
        this.setMutexFlags(EnumSet.of(Goal.Flag.LOOK));
    }

    /**
     * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
     * method as well.
     */
    @Override
    public boolean shouldExecute() {
        this.player = this.world.getClosestPlayer(this.field_220688_f, this.foxwolf);
        return this.player != null && this.hasTemptationItemInHand(this.player);
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    @Override
    public boolean shouldContinueExecuting() {
        if (!this.player.isAlive()) {
            return false;
        } else if (this.foxwolf.getDistanceSq(this.player) > (double)(this.minPlayerDistance * this.minPlayerDistance)) {
            return false;
        } else {
            return this.timeoutCounter > 0 && this.hasTemptationItemInHand(this.player);
        }
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    @Override
    public void startExecuting() {
        this.foxwolf.setBegging(true);
        this.timeoutCounter = 40 + this.foxwolf.getRNG().nextInt(40);
    }

    /**
     * Reset the task's internal state. Called when this task is interrupted by another one
     */
    @Override
    public void resetTask() {
        this.foxwolf.setBegging(false);
        this.player = null;
    }

    /**
     * Keep ticking a continuous task that has already been started
     */
    @Override
    public void tick() {
        this.foxwolf.getLookController().setLookPosition(this.player.getPosX(), this.player.getPosYEye(), this.player.getPosZ(), 10.0F, (float)this.foxwolf.getVerticalFaceSpeed());
        --this.timeoutCounter;
    }

    /**
     * Gets if the Player has the Bone in the hand.
     */
    private boolean hasTemptationItemInHand(PlayerEntity player) {
        for(Hand hand : Hand.values()) {
            ItemStack itemstack = player.getHeldItem(hand);
            if (this.foxwolf.isTamed() && itemstack.getItem() == Items.BONE) {
                return true;
            }

            if (this.foxwolf.isBreedingItem(itemstack)) {
                return true;
            }
        }

        return false;
    }
}
