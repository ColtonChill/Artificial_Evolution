package com.sapphique1010.artrificial_evolution.entities.ai.goals;

import com.sapphique1010.artrificial_evolution.entities.FoxWolfEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.entity.passive.FoxEntity;
import net.minecraft.entity.passive.RabbitEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.entity.passive.FoxEntity.AlertablePredicate;

import java.util.function.Predicate;




abstract class BaseGoal extends Goal {

    FoxWolfEntity foxwolf;

    private final EntityPredicate field_220816_b = new EntityPredicate().setDistance(12.0D).setLineOfSiteRequired().setCustomPredicate(foxwolf.TARGET_ENTITIES);

    public BaseGoal(FoxWolfEntity FoxWolf) {
        foxwolf = FoxWolf;
    }

    protected boolean func_220813_g() {
        BlockPos blockpos = new BlockPos(foxwolf);
        return !foxwolf.world.canSeeSky(blockpos) && foxwolf.getBlockPathWeight(blockpos) >= 0.0F;
    }

    protected boolean func_220814_h() {
        return !foxwolf.world.getTargettableEntitiesWithinAABB(LivingEntity.class, this.field_220816_b, foxwolf, foxwolf.getBoundingBox().grow(12.0D, 6.0D, 12.0D)).isEmpty();
    }
}