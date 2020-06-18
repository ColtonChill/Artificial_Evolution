package com.sapphique1010.artrificial_evolution.init;


import com.sapphique1010.artrificial_evolution.ArtificialEvolution;
import com.sapphique1010.artrificial_evolution.entities.FoxWolfEntity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;


public class ModEntityType {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = new DeferredRegister<>(ForgeRegistries.ENTITIES, ArtificialEvolution.MOD_ID);
    public static final RegistryObject<EntityType<FoxWolfEntity>> FOXWOLF_ENTITY = ENTITY_TYPES.register("fox_wolf_entity",
            ()->EntityType.Builder.create(FoxWolfEntity::new,EntityClassification.CREATURE)
            .size(0.9f,1.3f)
            .build(new ResourceLocation(ArtificialEvolution.MOD_ID,"fox_wolf_entity").toString()));
}
