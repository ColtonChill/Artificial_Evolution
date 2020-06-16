package com.sapphique1010.artrificial_evolution.objects.items;

import com.sapphique1010.artrificial_evolution.init.ItemInit;
import com.sapphique1010.artrificial_evolution.util.helpers.KeyboardHelper;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingEvent;

import javax.annotation.Nullable;
import java.awt.dnd.DnDConstants;
import java.util.List;

public class BloodSample extends Item {
    public BloodSample(Properties properties) {
        super(properties);
    }
    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        CompoundNBT nbt = stack.getTag();
        if (nbt!=null) {
            if (KeyboardHelper.isHoldingShift()) {
                INBT temp = nbt.get("Variant");
                if(temp!=null){
                    tooltip.add(new StringTextComponent("Variant:"+ temp));
                }
                temp = nbt.get("Health");
                if(temp!=null){
                    tooltip.add(new StringTextComponent("Health:"+ temp));
                }
                temp = nbt.get("Jump");
                if(temp!=null){
                    tooltip.add(new StringTextComponent("Jump:"+ temp));
                }
            } else {
                tooltip.add(new StringTextComponent("Blood type:"+nbt.get("base_type")));
            }
            super.addInformation(stack, worldIn, tooltip, flagIn);
        }
    }

    /**
     * Returns true if the item can be used on the given entity, e.g. shears on sheep.
     *
     * @param stack
     * @param playerIn
     * @param target
     * @param hand
     */
    @Override
    public boolean itemInteractionForEntity(ItemStack stack, PlayerEntity playerIn, LivingEntity target, Hand hand) {
        ItemStack DNA = new ItemStack(ItemInit.dna_bottle);
        DNA.setTag(stack.getTag());
        playerIn.inventory.setInventorySlotContents(playerIn.inventory.getSlotFor(stack),DNA);
        return super.itemInteractionForEntity(stack, playerIn, target, hand);
    }
}
