package com.sapphique1010.artrificial_evolution.init;


import com.sapphique1010.artrificial_evolution.ArtificialEvolution;
import com.sapphique1010.artrificial_evolution.entities.FoxWolfEntity;
import net.minecraft.util.registry.Registry;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

/*
 *   private static final DeferredRegister<Item> ITEMS = new DeferredRegister<>(ForgeRegistries.ITEMS, MODID);
 *   private static final DeferredRegister<Block> BLOCKS = new DeferredRegister<>(ForgeRegistries.BLOCKS, MODID);
 *
 *   public static final RegistryObject<Block> ROCK_BLOCK = BLOCKS.register("rock", () -> new Block(Block.Properties.create(Material.ROCK)));
 *   public static final RegistryObject<Item> ROCK_ITEM = ITEMS.register("rock", () ->
 *           new BlockItem(ROCK_BLOCK.get(), new Item.Properties().group(ItemGroup.MISC)));
 *
 *   public ExampleMod() {
 *       ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
 *       BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
 *   }
 */
public class ModEntityType {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = new DeferredRegister<>(ForgeRegistries.ENTITIES, ArtificialEvolution.MOD_ID);
    public static final DeferredRegister<Item> ITEMS = new DeferredRegister<>(ForgeRegistries.ITEMS, ArtificialEvolution.MOD_ID);

    public static final RegistryObject<EntityType<FoxWolfEntity>> FOXWOLF_ENTITY = ENTITY_TYPES.register("fox_wolf_entity",
            () -> EntityType.Builder.create(FoxWolfEntity::new, EntityClassification.CREATURE)
                    .size(0.55F, 0.80F)
                    .build(new ResourceLocation(ArtificialEvolution.MOD_ID, "fox_wolf_entity").toString()));

//    public static final RegistryObject<Item> FOXWOLF_SWAWN_EGG = ITEMS.register(new SupplierSpawnEggItem(FOXWOLF_ENTITY.get(),FOXWOLF_ENTITY,14144467, 13396256,new Item.Properties().group(ArtificialEvolution.EvoItemGroup.instance)));
//
//
//
//    public static final RegistryObject<Item> FOXWOLF_SPAWN_EGG = ITEMS.register("fox_wolf_spawn_egg",
//            () -> new SpawnEggItem(FoxWolfInstance, 14144467, 13396256,
//                    new Item.Properties().group(ArtificialEvolution.EvoItemGroup.instance)));

//    public static final EntityType<UnicornEntity> UNICORN = register("unicorn_entity",
//            EntityType.Builder.create(UnicornEntity::new, EntityClassification.AMBIENT).size(0.5F, 0.9F));
//    // This is correct - it's how you should be doing your registration, but it shouldn't be in a class called ModItems
//    //public static final RegistryObject<EntityType<UnicornEntity>> UNICORN = ENTITY_TYPES.register("unicorn_entity", () -> EntityType.Builder.create(UnicornEntity::new, EntityClassification.AMBIENT).build(null));
//    public static final RegistryObject<Item> RAINBOW_INGOT = ITEMS.register("rainbow_ingot",() -> new Item(new Item.Properties().group(ModItemGroups.RAINBOW_MOD_GROUP)));
//    public static final RegistryObject<Item> UNICORN_ENTITY_EGG = ITEMS.register("unicorn_entity_egg",
//            () -> new SpawnEggItem(UNICORN, 0xf0f0f0, 0xdf51f5,
//                    new Item.Properties().group(ModItemGroups.RAINBOW_MOD_GROUP)));

}