package com.sapphique1010.artrificial_evolution.objects.blocks.machines.centrifuge;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ContainerBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.BrewingStandTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class CentrifugeBlock extends ContainerBlock {
    public static final BooleanProperty[] HAS_BOTTLE = new BooleanProperty[]{BlockStateProperties.HAS_BOTTLE_0, BlockStateProperties.HAS_BOTTLE_1, BlockStateProperties.HAS_BOTTLE_2};

    protected CentrifugeBlock(Properties builder) {
        super(builder);
        this.setDefaultState(this.stateContainer.getBaseState().with(HAS_BOTTLE[0], Boolean.FALSE).with(HAS_BOTTLE[1], Boolean.FALSE).with(HAS_BOTTLE[2], Boolean.FALSE));
    }

    @Override
    public TileEntity createNewTileEntity(IBlockReader worldIn) {
        return new BrewingStandTileEntity();
    }

    /**
     * Called by ItemBlocks after a block is set in the world, to allow post-place logic
     */
    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
        if (stack.hasDisplayName()) {
            TileEntity tileentity = worldIn.getTileEntity(pos);
            if (tileentity instanceof BrewingStandTileEntity) {
                ((BrewingStandTileEntity)tileentity).setCustomName(stack.getDisplayName());
            }
        }
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(HAS_BOTTLE[0], HAS_BOTTLE[1], HAS_BOTTLE[2]);
    }
}
