package com.sapphique1010.artrificial_evolution.init;

import net.minecraft.item.SpawnEggItem;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.GenericEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraft.entity.EntityType;

import java.util.function.Consumer;


public class SupplierSpawnEggItem extends SpawnEggItem {
    private final RegistryObject<?> supplier;

    public SupplierSpawnEggItem(EntityType<?> typeIn, RegistryObject<?> supplierIn, int primaryColorIn, int secondaryColorIn, Properties builder) {
        super(typeIn, primaryColorIn, secondaryColorIn, builder);
        supplier = supplierIn;
    }

    @Override
    public EntityType<?> getType(CompoundNBT p_208076_1_) {
        return (EntityType<?>) supplier.get();
    }
}