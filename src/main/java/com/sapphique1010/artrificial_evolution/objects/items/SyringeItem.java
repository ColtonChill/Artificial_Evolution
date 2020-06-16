package com.sapphique1010.artrificial_evolution.objects.items;

import com.mojang.datafixers.util.Pair;
import com.sapphique1010.artrificial_evolution.init.ItemInit;
import com.sapphique1010.artrificial_evolution.util.helpers.KeyboardHelper;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.passive.horse.DonkeyEntity;
import net.minecraft.entity.passive.horse.HorseEntity;
import net.minecraft.entity.passive.horse.LlamaEntity;
import net.minecraft.entity.passive.horse.MuleEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import javax.annotation.Nullable;
import javax.security.auth.callback.CallbackHandler;
import javax.swing.plaf.basic.BasicToolBarUI;
import java.util.ArrayList;
import java.util.List;

public class SyringeItem extends Item {

    public SyringeItem(Properties properties) {
        super(properties);
    }

    @Override
    public boolean hasEffect(ItemStack stack){
        return true;
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        if(KeyboardHelper.isHoldingShift()) {
            tooltip.add(new StringTextComponent("Test Information"));
        }else{
            tooltip.add(new StringTextComponent("Hold SHIFT for more information!"));
        }
        super.addInformation(stack, worldIn, tooltip, flagIn);
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
        ItemStack singleBottle = new ItemStack(Items.GLASS_BOTTLE);
        if(playerIn.inventory.hasItemStack(singleBottle)){
            boolean wasSampled = false;
            String bloodType = "???";
            ArrayList<Pair<String,String>> data = new ArrayList<>();
            if (target instanceof CowEntity){
                wasSampled = true;
                bloodType = "Cow";
            }else if(target instanceof OcelotEntity){
                wasSampled = true;
                bloodType = "Cat";
            }else if(target instanceof CatEntity ){
                CatEntity cat = (CatEntity)(target);
                wasSampled = true;
                bloodType = "Cat";
                data.add(new Pair<>("VARIANT",String.valueOf(cat.getCatType())));
            }else if(target instanceof PigEntity ){
                wasSampled = true;
                bloodType = "Pig";
            }else if(target instanceof SheepEntity ){
                SheepEntity sheep = (SheepEntity)(target);
                wasSampled = true;
                bloodType = "Sheep";
                data.add(new Pair<>("Variant",String.valueOf(sheep.getFleeceColor())));
            }else if(target instanceof RabbitEntity ){
                wasSampled = true;
                bloodType = "Rabbit";
            }else if(target instanceof HorseEntity ){
                HorseEntity horse = (HorseEntity)(target);
                wasSampled = true;
                bloodType = "Horse";
                data.add(new Pair<>("Variant",String.valueOf(horse.getHorseVariant())));
                data.add(new Pair<>("Jump",String.valueOf(horse.getHorseJumpStrength())));
                data.add(new Pair<>("Health",String.valueOf(horse.getHealth())));
            }else if(target instanceof MuleEntity ){
                wasSampled = true;
                MuleEntity mule = (MuleEntity)(target);
                bloodType = "Mule";
                data.add(new Pair<>("JUMP",String.valueOf(mule.getHorseJumpStrength())));
                data.add(new Pair<>("Health",String.valueOf(mule.getHealth())));
            }else if(target instanceof DonkeyEntity ){
                wasSampled = true;
                DonkeyEntity donkey = (DonkeyEntity)(target);
                bloodType = "Donkey";
                data.add(new Pair<>("JUMP",String.valueOf(donkey.getHorseJumpStrength())));
                data.add(new Pair<>("Health",String.valueOf(donkey.getHealth())));
            }else if(target instanceof LlamaEntity ){
                wasSampled = true;
                LlamaEntity horse = (LlamaEntity)(target);
                bloodType = "Llama";
                data.add(new Pair<>("JUMP",String.valueOf(horse.getHorseJumpStrength())));
                data.add(new Pair<>("Health",String.valueOf(horse.getHealth())));
            }else if(target instanceof ChickenEntity ){
                wasSampled = true;
                bloodType = "Chicken";
            }else if(target instanceof ParrotEntity ){
                wasSampled = true;
                ParrotEntity parrot = (ParrotEntity)(target);
                bloodType = "Parrot";
                data.add(new Pair<>("Variant", String.valueOf(parrot.getVariant())));
            }else if(target instanceof BatEntity ){
                wasSampled = true;
                bloodType = "Bat";
            }else if(target instanceof DolphinEntity ){
                wasSampled = true;
                bloodType = "Dolphin";
            }else if(target instanceof FoxEntity ){
                wasSampled = true;
                FoxEntity fox = (FoxEntity)(target);
                bloodType = "Fox";
                data.add(new Pair<>("Variant",String.valueOf(fox.getVariantType())));
            }else if(target instanceof WolfEntity ){
                wasSampled = true;
                bloodType = "Wolf";
            }else if(target instanceof PandaEntity ){
                wasSampled = true;
                bloodType = "Panda";
            }else if(target instanceof PolarBearEntity){
                wasSampled = true;
                bloodType = "PolarBear";
            }else if(target instanceof SquidEntity ){
                wasSampled = true;
                bloodType = "Squid";
            }else if(target instanceof TurtleEntity) {
                wasSampled = true;
                bloodType = "Turtle";
            }
            //remove empty_bottle
            if(wasSampled) {
                for (int i = 0; i < playerIn.inventory.getSizeInventory(); ++i) {
                    if (playerIn.inventory.getStackInSlot(i).isItemEqual(singleBottle)) {
                        ItemStack temp = playerIn.inventory.getStackInSlot(i);
                        temp.setCount(temp.getCount() - 1);
                        break;
                    }
                }
                //add blood bottle
                ItemStack bloodBottle = new ItemStack(ItemInit.blood_bottle);
                CompoundNBT nbt = bloodBottle.getOrCreateTag();
                nbt.putString("base_type", bloodType);
                for (Pair<String,String> element:data) {
                    nbt.putString(element.getFirst(), element.getSecond());
                }
                bloodBottle.setTag(nbt);
                playerIn.inventory.addItemStackToInventory(bloodBottle);
                return true;
            }
        }
        return false;
    }
}
