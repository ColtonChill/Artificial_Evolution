package com.sapphique1010.artrificial_evolution.entities;

import com.sapphique1010.artrificial_evolution.entities.ai.goals.*;
import com.sapphique1010.artrificial_evolution.entities.ai.goals.BegGoal;
import com.sapphique1010.artrificial_evolution.entities.ai.goals.FindItemsGoal;
import com.sapphique1010.artrificial_evolution.entities.ai.goals.JumpGoal;
import com.sapphique1010.artrificial_evolution.entities.ai.goals.SleepGoal;
import com.sapphique1010.artrificial_evolution.entities.ai.goals.EatBerriesGoal;
import com.sapphique1010.artrificial_evolution.init.ModEntityType;
import com.sun.org.apache.xpath.internal.operations.Bool;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.monster.AbstractSkeletonEntity;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.entity.monster.GhastEntity;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.passive.fish.AbstractFishEntity;
import net.minecraft.entity.passive.fish.AbstractGroupFishEntity;
import net.minecraft.entity.passive.horse.AbstractHorseEntity;
import net.minecraft.entity.passive.horse.LlamaEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.*;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import javax.swing.text.StyledEditorKit;
import java.util.*;
import java.util.function.Predicate;

public class FoxWolfEntity extends TameableEntity {
    public final Predicate<LivingEntity> TARGET_ENTITIES = (p_213440_0_) -> {
        EntityType<?> entitytype = p_213440_0_.getType();
        return entitytype == EntityType.SHEEP || entitytype == EntityType.RABBIT || entitytype == EntityType.CHICKEN
                || entitytype==EntityType.COD || entitytype==EntityType.SALMON || entitytype==EntityType.TROPICAL_FISH;
    };
    private static final DataParameter<Boolean> BEGGING = EntityDataManager.createKey(WolfEntity.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> SLEEPING = EntityDataManager.createKey(WolfEntity.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Integer> COLLAR_COLOR = EntityDataManager.createKey(WolfEntity.class, DataSerializers.VARINT);
    private float headRotationCourse;
    private float headRotationCourseOld;
    private boolean isWet;
    private boolean isShaking;
    private float timeWolfIsShaking;
    private float prevTimeWolfIsShaking;


    public int luck = 1; // scale of one to 10


    public FoxWolfEntity(EntityType<? extends FoxWolfEntity> type, World worldIn) {
        super(type, worldIn);
        this.setTamed(false);
        this.luck = (int)(Math.random()*10);
    }


    @Override
    protected void registerGoals() {
        this.sitGoal = new SitGoal(this);
        this.goalSelector.addGoal(1, new SwimGoal(this));
        this.goalSelector.addGoal(2, this.sitGoal);
        this.goalSelector.addGoal(1, new JumpGoal(this));
        this.goalSelector.addGoal(6, new PounceGoal());
        this.goalSelector.addGoal(2, new PanicGoal(this,2.2D));
        this.goalSelector.addGoal(3, new FoxWolfEntity.AvoidEntityGoal<>(this, LlamaEntity.class, 24.0F, 1.5D, 1.5D));
        this.goalSelector.addGoal(4, new LeapAtTargetGoal(this, 0.4F));
        this.goalSelector.addGoal(5, new MeleeAttackGoal(this, 1.0D, true));
        this.goalSelector.addGoal(6, new FollowOwnerGoal(this, 1.0D, 10.0F, 2.0F, false));
        this.goalSelector.addGoal(7, new BreedGoal(this, 1.0D));
        this.goalSelector.addGoal(7, new SleepGoal(this));
        this.goalSelector.addGoal(8, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
        this.goalSelector.addGoal(9, new BegGoal(this, 8.0F));
        this.goalSelector.addGoal(10, new LookAtGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.addGoal(10, new LookRandomlyGoal(this));
        this.goalSelector.addGoal(11, new FindItemsGoal(this));

        this.goalSelector.addGoal(4, new net.minecraft.entity.ai.goal.AvoidEntityGoal<>(this, PlayerEntity.class, 16.0F, 1.6D, 1.4D, (p_213497_1_) -> {
            return (this.getOwnerId() == p_213497_1_.getUniqueID()) && !this.isAngry();
        }));

        this.goalSelector.addGoal(4, new net.minecraft.entity.ai.goal.AvoidEntityGoal<>(this, LlamaEntity.class, 8.0F, 1.6D, 1.4D, (p_213469_1_) -> {
            return !((WolfEntity) p_213469_1_).isTamed() && !this.isAngry();
        }));

        this.goalSelector.addGoal(10, new EatBerriesGoal(this, (double) 1.2F, 12, 2));
        this.goalSelector.addGoal(10, new LeapAtTargetGoal(this, 0.4F));

        this.targetSelector.addGoal(1, new OwnerHurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new OwnerHurtTargetGoal(this));
        this.targetSelector.addGoal(3, (new HurtByTargetGoal(this)).setCallsForHelp());
        this.targetSelector.addGoal(4, new NonTamedTargetGoal<>(this, AnimalEntity.class, false, TARGET_ENTITIES));
        this.targetSelector.addGoal(4, new NonTamedTargetGoal<>(this, TurtleEntity.class, false, TurtleEntity.TARGET_DRY_BABY));
        this.targetSelector.addGoal(5, new NearestAttackableTargetGoal<>(this, AbstractSkeletonEntity.class, false));
    }

    public boolean isLucky(){
        return 0 == Math.random()*(10-this.luck);
    }

    @Override
    protected void registerAttributes() {
        super.registerAttributes();
        this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue((double) 0.3F);
        if (this.isTamed()) {
            this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(20.0D);
        } else {
            this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(8.0D);
        }

        this.getAttributes().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(2.0D);
    }

    /**
     * Sets the active target the Task system uses for tracking
     */
    @Override
    public void setAttackTarget(@Nullable LivingEntity entitylivingbaseIn) {
        super.setAttackTarget(entitylivingbaseIn);
        if (entitylivingbaseIn == null) {
            this.setAngry(false);
        } else if (!this.isTamed()) {
            this.setAngry(true);
        }

    }

    @Override
    protected void registerData() {
        super.registerData();
        this.dataManager.register(BEGGING, false);
        this.dataManager.register(SLEEPING, false);
        this.dataManager.register(COLLAR_COLOR, DyeColor.RED.getId());
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState blockIn) {
        this.playSound(SoundEvents.ENTITY_WOLF_STEP, 0.15F, 1.0F);
    }

    @Override
    public void writeAdditional(CompoundNBT compound) {
        super.writeAdditional(compound);
        compound.putBoolean("Angry", this.isAngry());
        compound.putByte("CollarColor", (byte) this.getCollarColor().getId());
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    @Override
    public void readAdditional(CompoundNBT compound) {
        super.readAdditional(compound);
        this.setAngry(compound.getBoolean("Angry"));
        if (compound.contains("CollarColor", 99)) {
            this.setCollarColor(DyeColor.byId(compound.getInt("CollarColor")));
        }

    }

    @Override
    protected SoundEvent getAmbientSound() {
        if (this.isAngry()) {
            return SoundEvents.ENTITY_WOLF_GROWL;
        } else if (this.rand.nextInt(3) == 0) {
            return this.isTamed() && this.getHealth() < 10.0F ? SoundEvents.ENTITY_WOLF_WHINE : SoundEvents.ENTITY_WOLF_PANT;
        } else {
            return SoundEvents.ENTITY_WOLF_AMBIENT;
        }
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundEvents.ENTITY_WOLF_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_WOLF_DEATH;
    }

    /**
     * Returns the volume for the sounds this mob makes.
     */
    @Override
    protected float getSoundVolume() {
        return 0.4F;
    }

    /**
     * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
     * use this to react to sunlight and start to burn.
     */
    @Override
    public void livingTick() {
        super.livingTick();
        if (!this.world.isRemote && this.isWet && !this.isShaking && !this.hasPath() && this.onGround) {
            this.isShaking = true;
            this.timeWolfIsShaking = 0.0F;
            this.prevTimeWolfIsShaking = 0.0F;
            this.world.setEntityState(this, (byte) 8);
        }

        if (!this.world.isRemote && this.getAttackTarget() == null && this.isAngry()) {
            this.setAngry(false);
        }

    }

    /**
     * Called to update the entity's position/logic.
     */
    @Override
    public void tick() {
        super.tick();
        if (this.isAlive()) {
            this.headRotationCourseOld = this.headRotationCourse;
            if (this.isBegging()) {
                this.headRotationCourse += (1.0F - this.headRotationCourse) * 0.4F;
            } else {
                this.headRotationCourse += (0.0F - this.headRotationCourse) * 0.4F;
            }

            if (this.isInWaterRainOrBubbleColumn()) {
                this.isWet = true;
                this.isShaking = false;
                this.timeWolfIsShaking = 0.0F;
                this.prevTimeWolfIsShaking = 0.0F;
            } else if ((this.isWet || this.isShaking) && this.isShaking) {
                if (this.timeWolfIsShaking == 0.0F) {
                    this.playSound(SoundEvents.ENTITY_WOLF_SHAKE, this.getSoundVolume(), (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
                }

                this.prevTimeWolfIsShaking = this.timeWolfIsShaking;
                this.timeWolfIsShaking += 0.05F;
                if (this.prevTimeWolfIsShaking >= 2.0F) {
                    this.isWet = false;
                    this.isShaking = false;
                    this.prevTimeWolfIsShaking = 0.0F;
                    this.timeWolfIsShaking = 0.0F;
                }

                if (this.timeWolfIsShaking > 0.4F) {
                    float f = (float) this.getPosY();
                    int i = (int) (MathHelper.sin((this.timeWolfIsShaking - 0.4F) * (float) Math.PI) * 7.0F);
                    Vec3d vec3d = this.getMotion();

                    for (int j = 0; j < i; ++j) {
                        float f1 = (this.rand.nextFloat() * 2.0F - 1.0F) * this.getWidth() * 0.5F;
                        float f2 = (this.rand.nextFloat() * 2.0F - 1.0F) * this.getWidth() * 0.5F;
                        this.world.addParticle(ParticleTypes.SPLASH, this.getPosX() + (double) f1, (double) (f + 0.8F), this.getPosZ() + (double) f2, vec3d.x, vec3d.y, vec3d.z);
                    }
                }
            }

        }
    }

    /**
     * Called when the mob's health reaches 0.
     */
    @Override
    public void onDeath(DamageSource cause) {
        this.isWet = false;
        this.isShaking = false;
        this.prevTimeWolfIsShaking = 0.0F;
        this.timeWolfIsShaking = 0.0F;
        super.onDeath(cause);
    }

    /**
     * True if the wolf is wet
     */
    @OnlyIn(Dist.CLIENT)
    public boolean isWolfWet() {
        return this.isWet;
    }

    /**
     * Used when calculating the amount of shading to apply while the wolf is wet.
     */
    @OnlyIn(Dist.CLIENT)
    public float getShadingWhileWet(float partialTicks) {
        return 0.75F + MathHelper.lerp(partialTicks, this.prevTimeWolfIsShaking, this.timeWolfIsShaking) / 2.0F * 0.25F;
    }

    @OnlyIn(Dist.CLIENT)
    public float getShakeAngle(float partialTicks, float offset) {
        float f = (MathHelper.lerp(partialTicks, this.prevTimeWolfIsShaking, this.timeWolfIsShaking) + offset) / 1.8F;
        if (f < 0.0F) {
            f = 0.0F;
        } else if (f > 1.0F) {
            f = 1.0F;
        }

        return MathHelper.sin(f * (float) Math.PI) * MathHelper.sin(f * (float) Math.PI * 11.0F) * 0.15F * (float) Math.PI;
    }

    @OnlyIn(Dist.CLIENT)
    public float getInterestedAngle(float partialTicks) {
        return MathHelper.lerp(partialTicks, this.headRotationCourseOld, this.headRotationCourse) * 0.15F * (float) Math.PI;
    }

    @Override
    protected float getStandingEyeHeight(Pose poseIn, EntitySize sizeIn) {
        return sizeIn.height * 0.8F;
    }

    /**
     * The speed it takes to move the entityliving's rotationPitch through the faceEntity method. This is only currently
     * use in wolves.
     */
    @Override
    public int getVerticalFaceSpeed() {
        return this.isSitting() ? 20 : super.getVerticalFaceSpeed();
    }

    /**
     * Called when the entity is attacked.
     */
    @Override
    public boolean attackEntityFrom(DamageSource source, float amount) {
        if (this.isInvulnerableTo(source)) {
            return false;
        } else {
            Entity entity = source.getTrueSource();
            if (this.sitGoal != null) {
                this.sitGoal.setSitting(false);
            }

            if (entity != null && !(entity instanceof PlayerEntity) && !(entity instanceof AbstractArrowEntity)) {
                amount = (amount + 1.0F) / 2.0F;
            }

            return super.attackEntityFrom(source, amount);
        }
    }

    @Override
    public boolean attackEntityAsMob(Entity entityIn) {
        boolean flag = entityIn.attackEntityFrom(DamageSource.causeMobDamage(this), (float) ((int) this.getAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getValue()));
        if (flag) {
            this.applyEnchantments(this, entityIn);
        }

        return flag;
    }

    @Override
    public void setTamed(boolean tamed) {
        super.setTamed(tamed);
        if (tamed) {
            this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(20.0D);
            this.setHealth(20.0F);
        } else {
            this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(8.0D);
        }

        this.getAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(4.0D);
    }

    @Override
    public boolean processInteract(PlayerEntity player, Hand hand) {
        ItemStack itemstack = player.getHeldItem(hand);
        Item item = itemstack.getItem();
        if (itemstack.getItem() instanceof SpawnEggItem) {
            return super.processInteract(player, hand);
        } else if (this.world.isRemote) {
            return this.isOwner(player) || item == Items.BONE && !this.isAngry();
        } else {
            if (this.isTamed()) {
                if (item.isFood() && item.getFood().isMeat() && this.getHealth() < this.getMaxHealth()) {
                    if (!player.abilities.isCreativeMode) {
                        itemstack.shrink(1);
                    }

                    this.heal((float) item.getFood().getHealing());
                    return true;
                }

                if (!(item instanceof DyeItem)) {
                    boolean flag = super.processInteract(player, hand);
                    if (!flag || this.isChild()) {
                        this.sitGoal.setSitting(!this.isSitting());
                    }

                    return flag;
                }

                DyeColor dyecolor = ((DyeItem) item).getDyeColor();
                if (dyecolor != this.getCollarColor()) {
                    this.setCollarColor(dyecolor);
                    if (!player.abilities.isCreativeMode) {
                        itemstack.shrink(1);
                    }

                    return true;
                }

                if (this.isOwner(player) && !this.isBreedingItem(itemstack)) {
                    this.sitGoal.setSitting(!this.isSitting());
                    this.isJumping = false;
                    this.navigator.clearPath();
                    this.setAttackTarget((LivingEntity) null);
                }
            } else if (item == Items.BONE && !this.isAngry()) {
                if (!player.abilities.isCreativeMode) {
                    itemstack.shrink(1);
                }

                if (this.rand.nextInt(3) == 0 && !net.minecraftforge.event.ForgeEventFactory.onAnimalTame(this, player)) {
                    this.setTamedBy(player);
                    this.navigator.clearPath();
                    this.setAttackTarget((LivingEntity) null);
                    this.sitGoal.setSitting(true);
                    this.world.setEntityState(this, (byte) 7);
                } else {
                    this.world.setEntityState(this, (byte) 6);
                }

                return true;
            }

            return super.processInteract(player, hand);
        }
    }

    /**
     * Handler for {@link World#setEntityState}
     */
    @Override
    @OnlyIn(Dist.CLIENT)
    public void handleStatusUpdate(byte id) {
        if (id == 8) {
            this.isShaking = true;
            this.timeWolfIsShaking = 0.0F;
            this.prevTimeWolfIsShaking = 0.0F;
        } else {
            super.handleStatusUpdate(id);
        }

    }

    @OnlyIn(Dist.CLIENT)
    public float getTailRotation() {
        if (this.isAngry()) {
            return 1.5393804F;
        } else {
            return this.isTamed() ? (0.55F - (this.getMaxHealth() - this.getHealth()) * 0.02F) * (float) Math.PI : ((float) Math.PI / 5F);
        }
    }

    /**
     * Checks if the parameter is an item which this animal can be fed to breed it (wheat, carrots or seeds depending on
     * the animal type)
     */
    @Override
    public boolean isBreedingItem(ItemStack stack) {
        Item item = stack.getItem();
        return item.isFood() && (item.getFood().isMeat() || item.getItem() == Items.SWEET_BERRIES);
    }

    /**
     * Will return how many at most can spawn in a chunk at once.
     */
    @Override
    public int getMaxSpawnedInChunk() {
        return 8;
    }

    /**
     * Determines whether this wolf is angry or not.
     */
    public boolean isAngry() {
        return (this.dataManager.get(TAMED) & 2) != 0;
    }

    /**
     * Sets whether this wolf is angry or not.
     */
    public void setAngry(boolean angry) {
        byte b0 = this.dataManager.get(TAMED);
        if (angry) {
            this.dataManager.set(TAMED, (byte) (b0 | 2));
        } else {
            this.dataManager.set(TAMED, (byte) (b0 & -3));
        }

    }


    public DyeColor getCollarColor() {
        return DyeColor.byId(this.dataManager.get(COLLAR_COLOR));
    }

    public void setCollarColor(DyeColor collarcolor) {
        this.dataManager.set(COLLAR_COLOR, collarcolor.getId());
    }

    @Override
    public FoxWolfEntity createChild(AgeableEntity ageable) {
        FoxWolfEntity foxwolfentity = new FoxWolfEntity(ModEntityType.FOXWOLF_ENTITY.get(), this.world);
        UUID uuid = this.getOwnerId();
        if (uuid != null) {
            foxwolfentity.setOwnerId(uuid);
            foxwolfentity.setTamed(true);
        }

        return foxwolfentity;
    }

    public void setBegging(boolean beg) {
        this.dataManager.set(BEGGING, beg);
    }

    /**
     * Returns true if the mob is currently able to mate with the specified mob.
     */
    @Override
    public boolean canMateWith(AnimalEntity otherAnimal) {
        if (otherAnimal == this) {
            return false;
        } else if (!this.isTamed()) {
            return false;
        } else if (!(otherAnimal instanceof FoxWolfEntity)) {
            return false;
        } else {
            FoxWolfEntity foxwolfentity = (FoxWolfEntity) otherAnimal;
            if (!foxwolfentity.isTamed()) {
                return false;
            } else if (foxwolfentity.isSitting()) {
                return false;
            } else {
                return this.isInLove() && foxwolfentity.isInLove();
            }
        }
    }

    public boolean isBegging() {
        return this.dataManager.get(BEGGING);
    }


    @Override
    public boolean isSleeping() {
        return this.dataManager.get(SLEEPING);
    }

    public void setSleeping(boolean sleep) {
        this.dataManager.set(SLEEPING, sleep);
    }



    @Override
    public boolean shouldAttackEntity(LivingEntity target, LivingEntity owner) {
        if (!(target instanceof CreeperEntity) && !(target instanceof GhastEntity)) {
            if (target instanceof FoxWolfEntity) {
                FoxWolfEntity foxwolfentity = (FoxWolfEntity) target;
                return !foxwolfentity.isTamed() || foxwolfentity.getOwner() != owner;
            } else if (target instanceof PlayerEntity && owner instanceof PlayerEntity && !((PlayerEntity) owner).canAttackPlayer((PlayerEntity) target)) {
                return false;
            } else if (target instanceof AbstractHorseEntity && ((AbstractHorseEntity) target).isTame()) {
                return false;
            } else {
                return !(target instanceof TameableEntity) || !((TameableEntity) target).isTamed();
            }
        } else {
            return false;
        }
    }

    @Override
    public boolean canBeLeashedTo(PlayerEntity player) {
        return !this.isAngry() && super.canBeLeashedTo(player);
    }

    class AvoidEntityGoal<T extends LivingEntity> extends net.minecraft.entity.ai.goal.AvoidEntityGoal<T> {
        private final FoxWolfEntity foxwolf;

        public AvoidEntityGoal(FoxWolfEntity wolfIn, Class<T> entityClassToAvoidIn, float avoidDistanceIn, double farSpeedIn, double nearSpeedIn) {
            super(wolfIn, entityClassToAvoidIn, avoidDistanceIn, farSpeedIn, nearSpeedIn);
            this.foxwolf = wolfIn;
        }

        /**
         * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
         * method as well.
         */
        @Override
        public boolean shouldExecute() {
            if (super.shouldExecute() && this.avoidTarget instanceof LlamaEntity) {
                return !this.foxwolf.isTamed() && this.avoidLlama((LlamaEntity) this.avoidTarget);
            } else {
                return false;
            }
        }

        private boolean avoidLlama(LlamaEntity llamaIn) {
            return llamaIn.getStrength() >= FoxWolfEntity.this.rand.nextInt(5);
        }

        /**
         * Execute a one shot task or start executing a continuous task
         */
        @Override
        public void startExecuting() {
            FoxWolfEntity.this.setAttackTarget((LivingEntity) null);
            super.startExecuting();
        }

        /**
         * Keep ticking a continuous task that has already been started
         */
        @Override
        public void tick() {
            FoxWolfEntity.this.setAttackTarget((LivingEntity) null);
            super.tick();
        }
    }
    public boolean func_220813_g() {
        BlockPos blockpos = new BlockPos(FoxWolfEntity.this);
        return !FoxWolfEntity.this.world.canSeeSky(blockpos) && FoxWolfEntity.this.getBlockPathWeight(blockpos) >= 0.0F;
    }
}