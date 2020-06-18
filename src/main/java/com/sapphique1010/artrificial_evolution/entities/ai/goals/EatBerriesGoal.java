package com.sapphique1010.artrificial_evolution.entities.ai.goals;

import com.sapphique1010.artrificial_evolution.entities.FoxWolfEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SweetBerryBushBlock;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.entity.passive.FoxEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;

import java.security.PublicKey;

public class EatBerriesGoal extends MoveToBlockGoal {
    FoxWolfEntity foxwolf;
    protected int field_220731_g;

    public EatBerriesGoal(FoxWolfEntity foxwolf, double speed, int length, int p_i50737_5_) {
        super(foxwolf, speed, length, p_i50737_5_);
    }

    @Override
    public double getTargetDistanceSq() {
        return 2.0D;
    }

    @Override
    public boolean shouldMove() {
        return this.timeoutCounter % 100 == 0;
    }

    /**
     * Return true to set given position as destination
     */
    @Override
    protected boolean shouldMoveTo(IWorldReader worldIn, BlockPos pos) {
        BlockState blockstate = worldIn.getBlockState(pos);
        return blockstate.getBlock() == Blocks.SWEET_BERRY_BUSH && blockstate.get(SweetBerryBushBlock.AGE) >= 2;
    }

    /**
     * Keep ticking a continuous task that has already been started
     */
    @Override
    public void tick() {
        if (this.getIsAboveDestination()) {
            if (this.field_220731_g >= 40) {
                this.eatBerry();
            } else {
                ++this.field_220731_g;
            }
        } else if (!this.getIsAboveDestination() && foxwolf.world.rand.nextFloat() < 0.05F) {
            foxwolf.playSound(SoundEvents.ENTITY_FOX_SNIFF, 1.0F, 1.0F);
        }

        super.tick();
    }

    protected void eatBerry() {
        if (net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(foxwolf.world, foxwolf)) {
            BlockState blockstate = foxwolf.world.getBlockState(this.destinationBlock);
            if (blockstate.getBlock() == Blocks.SWEET_BERRY_BUSH) {
                int i = blockstate.get(SweetBerryBushBlock.AGE);
                blockstate.with(SweetBerryBushBlock.AGE, Integer.valueOf(1));
                int j = 1 + foxwolf.world.rand.nextInt(2) + (i == 3 ? 1 : 0);
                ItemStack itemstack = foxwolf.getItemStackFromSlot(EquipmentSlotType.MAINHAND);
                if (itemstack.isEmpty()) {
                    foxwolf.setItemStackToSlot(EquipmentSlotType.MAINHAND, new ItemStack(Items.SWEET_BERRIES));
                    --j;
                }

                if (j > 0) {
                    Block.spawnAsEntity(foxwolf.world, this.destinationBlock, new ItemStack(Items.SWEET_BERRIES, j));
                }

                foxwolf.playSound(SoundEvents.ITEM_SWEET_BERRIES_PICK_FROM_BUSH, 1.0F, 1.0F);
                foxwolf.world.setBlockState(this.destinationBlock, blockstate.with(SweetBerryBushBlock.AGE, Integer.valueOf(1)), 2);
            }
        }
    }

    /**
     * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
     * method as well.
     */
    @Override
    public boolean shouldExecute() {
        return !foxwolf.isSleeping() && super.shouldExecute();
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    @Override
    public void startExecuting() {
        this.field_220731_g = 0;
        foxwolf.setSitting(false);
        super.startExecuting();
    }
}