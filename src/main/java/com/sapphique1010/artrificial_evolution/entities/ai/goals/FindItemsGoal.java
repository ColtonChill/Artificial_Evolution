package com.sapphique1010.artrificial_evolution.entities.ai.goals;

import com.sapphique1010.artrificial_evolution.entities.FoxWolfEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.passive.FoxEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;

import java.util.EnumSet;
import java.util.List;

public class FindItemsGoal extends Goal {
    private final FoxWolfEntity foxwolf;
    public FindItemsGoal(FoxWolfEntity foxwolf) {
        this.foxwolf = foxwolf;
        this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE));
    }

    /**
     * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
     * method as well.
     */
    @Override
    public boolean shouldExecute() {
        if (foxwolf.getItemStackFromSlot(EquipmentSlotType.MAINHAND).isEmpty()) {
            return false;
        } else if (foxwolf.getAttackTarget() == null) {
            if (foxwolf.isSleeping() || foxwolf.isSitting()) {
                return false;
            } else if (foxwolf.isLucky()) {
                return false;
            } else {
                List<ItemEntity> list = foxwolf.world.getEntitiesWithinAABB(ItemEntity.class, foxwolf.getBoundingBox().grow(8.0D, 8.0D, 8.0D));
                return !list.isEmpty() && foxwolf.getItemStackFromSlot(EquipmentSlotType.MAINHAND).isEmpty();
            }
        } else {
            return false;
        }
    }

    /**
     * Keep ticking a continuous task that has already been started
     */
    @Override
    public void tick() {
        List<ItemEntity> list = foxwolf.world.getEntitiesWithinAABB(ItemEntity.class, foxwolf.getBoundingBox().grow(8.0D, 8.0D, 8.0D));
        ItemStack itemstack = foxwolf.getItemStackFromSlot(EquipmentSlotType.MAINHAND);
        if (itemstack.isEmpty() && !list.isEmpty()) {
            foxwolf.getNavigator().tryMoveToEntityLiving(list.get(0), (double)1.2F);
        }

    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    @Override
    public void startExecuting() {
        List<ItemEntity> list = foxwolf.world.getEntitiesWithinAABB(ItemEntity.class, foxwolf.getBoundingBox().grow(8.0D, 8.0D, 8.0D));
        if (!list.isEmpty()) {
            foxwolf.getNavigator().tryMoveToEntityLiving(list.get(0), (double)1.2F);
        }
    }
}
