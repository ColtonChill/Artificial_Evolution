package com.sapphique1010.artrificial_evolution.objects.items;

import com.sapphique1010.artrificial_evolution.util.helpers.KeyboardHelper;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class GeneticMaterial extends Item {
    public GeneticMaterial(Properties properties) {
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
}
