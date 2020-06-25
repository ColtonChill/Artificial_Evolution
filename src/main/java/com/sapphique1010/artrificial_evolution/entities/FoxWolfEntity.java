package com.sapphique1010.artrificial_evolution.entities;

import com.sapphique1010.artrificial_evolution.init.ModEntityType;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.passive.*;
import net.minecraft.item.*;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class FoxWolfEntity extends TameableEntity {

    public FoxWolfEntity(EntityType<? extends TameableEntity> type, World worldIn) {
        super(type, worldIn);
    }

    @Nullable
    @Override
    public AgeableEntity createChild(AgeableEntity ageable) {
        FoxWolfEntity entity = new FoxWolfEntity(ModEntityType.FOXWOLF_ENTITY.get(), this.world);
        entity.onInitialSpawn(this.world, this.world.getDifficultyForLocation(new BlockPos(entity)),
                SpawnReason.BREEDING, (ILivingEntityData) null, (CompoundNBT) null);
//        entity.setGlowing(true);
        return entity;
    }


    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.sitGoal = new SitGoal(this);
    }

    @Override
    protected void updateAITasks() {
        super.updateAITasks();
    }

    /**
     * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
     * use this to react to sunlight and start to burn.
     */
    @Override
    public void livingTick() {
        super.livingTick();
    }

    @Override
    protected void registerAttributes() {
        super.registerAttributes();
    }

    @Override
    public Entity getEntity() {
        return null;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {

    }

    @Override
    public CompoundNBT serializeNBT() {
        return null;
    }

    /**
     * Used in model rendering to determine if the entity riding this entity should be in the 'sitting' position.
     *
     * @return false to prevent an entity that is mounted to this entity from displaying the 'sitting' animation.
     */
    @Override
    public boolean shouldRiderSit() {
        return false;
    }

    /**
     * Called when a user uses the creative pick block button on this entity.
     *
     * @param target The full target the player is looking at
     * @return A ItemStack to add to the player's inventory, empty ItemStack if nothing should be added.
     */
    @Override
    public ItemStack getPickedResult(RayTraceResult target) {
        return null;
    }

    /**
     * If a rider of this entity can interact with this entity. Should return true on the
     * ridden entity if so.
     *
     * @return if the entity can be interacted with from a rider
     */
    @Override
    public boolean canRiderInteract() {
        return false;
    }

    /**
     * Checks if this entity can continue to be ridden while it is underwater.
     *
     * @param rider The entity that is riding this entity
     * @return {@code true} if the entity can continue to ride underwater. {@code false} otherwise.
     */
    @Override
    public boolean canBeRiddenInWater(Entity rider) {
        return false;
    }

    /**
     * Returns The classification of this entity
     *
     * @param forSpawnCount If this is being invoked to check spawn count caps.
     * @return If the creature is of the type provided
     */
    @Override
    public EntityClassification getClassification(boolean forSpawnCount) {
        return EntityClassification.CREATURE;
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap) {
        return null;
    }
}