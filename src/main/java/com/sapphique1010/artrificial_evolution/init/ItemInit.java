package com.sapphique1010.artrificial_evolution.init;

import com.sapphique1010.artrificial_evolution.entities.FoxWolfEntity;
import com.sapphique1010.artrificial_evolution.objects.items.BloodSample;
import com.sapphique1010.artrificial_evolution.objects.items.GeneticMaterial;
import com.sapphique1010.artrificial_evolution.objects.items.SyringeItem;
import com.sapphique1010.artrificial_evolution.ArtificialEvolution;
import com.sapphique1010.artrificial_evolution.ArtificialEvolution.*;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.LazyValue;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ObjectHolder;

import java.util.function.Supplier;

import static com.sapphique1010.artrificial_evolution.init.ModEntityType.ENTITY_TYPES;
import static com.sapphique1010.artrificial_evolution.init.ModEntityType.FOXWOLF_ENTITY;

@Mod.EventBusSubscriber(modid = ArtificialEvolution.MOD_ID, bus = Bus.MOD)
@ObjectHolder(ArtificialEvolution.MOD_ID)
public class ItemInit {
//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\
    public static final Item blood_bottle = null;
    public static final Item dna_bottle = null;
    public static final Item syringe = null;
    public static final Item needle = null;
    //\\//\\//\\
    public static final Item meteorite_ingot = null;
//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\
    public static final Item blood_sword = null;
//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\
    public static final Item blood_helmet = null;
    public static final Item blood_chestplate = null;
    public static final Item blood_leggings = null;
    public static final Item blood_boots = null;
//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\
    public static final Item fox_wolf_spawn_egg = null;
//    public static final EntityType<FoxWolfEntity> foxwolf = EntityType.Builder.create(EntityType::create,EntityClassification.CREATURE).tracker(32, 1, true).build("v0idstestmod.test_entity");

    @SubscribeEvent
        public static void registerItems(final RegistryEvent.Register<Item> event){
        //Medical
        event.getRegistry().register(new BloodSample(new Item.Properties().group(EvoItemGroup.instance).maxStackSize(1)).setRegistryName("blood_bottle"));
        event.getRegistry().register(new GeneticMaterial(new Item.Properties().group(EvoItemGroup.instance).maxStackSize(1)).setRegistryName("dna_bottle"));
        event.getRegistry().register(new SyringeItem(new Item.Properties().group(EvoItemGroup.instance).maxStackSize(1)).setRegistryName("syringe"));
        event.getRegistry().register(new Item(new Item.Properties().group(EvoItemGroup.instance)).setRegistryName("needle"));
        event.getRegistry().register(new Item(new Item.Properties().group(AscendantItemGroup.instance)).setRegistryName("meteorite_ingot"));
        //tools
        event.getRegistry().register(new SwordItem(ModItemTier.OBSIDIAN,2,-2,new Item.Properties().group(EldritchItemGroup.instance).maxStackSize(1)).setRegistryName("blood_sword"));
        //armor
        event.getRegistry().register(new ArmorItem(MyArmorMaterials.BLOOD, EquipmentSlotType.HEAD, new Item.Properties().group(EldritchItemGroup.instance).maxStackSize(1)).setRegistryName("blood_helmet"));
        event.getRegistry().register(new ArmorItem(MyArmorMaterials.BLOOD, EquipmentSlotType.CHEST, new Item.Properties().group(EldritchItemGroup.instance).maxStackSize(1)).setRegistryName("blood_chestplate"));
        event.getRegistry().register(new ArmorItem(MyArmorMaterials.BLOOD, EquipmentSlotType.LEGS, new Item.Properties().group(EldritchItemGroup.instance).maxStackSize(1)).setRegistryName("blood_leggings"));
        event.getRegistry().register(new ArmorItem(MyArmorMaterials.BLOOD, EquipmentSlotType.FEET, new Item.Properties().group(EldritchItemGroup.instance).maxStackSize(1)).setRegistryName("blood_boots"));
        //spawn egg
//        () -> EntityType.Builder.create(FoxWolfEntity::new, EntityClassification.CREATURE)
//                .size(0.55F, 0.80F)
//                .build(new ResourceLocation(ArtificialEvolution.MOD_ID, "fox_wolf_entity").toString());
        event.getRegistry().register(new SupplierSpawnEggItem(null, FOXWOLF_ENTITY,14144467,13396256,new Item.Properties().group(EvoItemGroup.instance)).setRegistryName("fox_wolf_spawn_egg"));
    }

    public enum ModItemTier implements IItemTier{
        OBSIDIAN(4,50,10f,7f,15,()->{
            return Ingredient.fromItems(ItemInit.blood_bottle);
        });
        private final int harvestLevel;
        private final int maxUses;
        private final float efficiency;
        private final float attackDamage;
        private final int enchantability;
        private final LazyValue<Ingredient> repairMaterial;

        private ModItemTier(int harvestLevel, int maxUses, float efficiency, float attackDamage,int enchantability, Supplier<Ingredient> repairMaterial){
            this.harvestLevel = harvestLevel;
            this.maxUses = maxUses;
            this.efficiency = enchantability;
            this.attackDamage = attackDamage;
            this.enchantability = enchantability;
            this.repairMaterial = new LazyValue<>(repairMaterial);
        }

        @Override
        public int getMaxUses() {
            return this.maxUses;
        }

        @Override
        public float getEfficiency() {
            return this.efficiency;
        }

        @Override
        public float getAttackDamage() {
            return this.attackDamage;
        }

        @Override
        public int getHarvestLevel() {
            return this.harvestLevel;
        }

        @Override
        public int getEnchantability() {
            return this.enchantability;
        }

        @Override
        public Ingredient getRepairMaterial() {
            return this.repairMaterial.getValue();
        }
    }

    public enum MyArmorMaterials implements IArmorMaterial {
        BLOOD(ArtificialEvolution.MOD_ID+":blood_armor",5,new int[] {3,6,8,3},25, SoundEvents.ENTITY_ENDERMAN_DEATH,2,()-> Ingredient.fromItems(ItemInit.blood_bottle));

        private static final int[] MAX_DAMAGE_ARRAY = new int[] {16,16,16,16};
        private final String name;
        private final int maxDamageFactor;
        private final int[] DamageReductionAmountArray;
        private final int enchantability;
        private final SoundEvent soundEvent;
        private final float toughness;
        private final LazyValue<Ingredient> repairMaterial;

        MyArmorMaterials(String name, int maxDamageFactor, int[] DamageReductionAmountArray, int enchantability,
                         SoundEvent soundEvent, float toughness, Supplier<Ingredient> repairMaterial) {
            this.name = name;
            this.maxDamageFactor = maxDamageFactor;
            this.DamageReductionAmountArray = DamageReductionAmountArray;
            this.enchantability = enchantability;
            this.soundEvent = soundEvent;
            this.toughness = toughness;
            this.repairMaterial = new LazyValue<>(repairMaterial);
        }

        @Override
        public int getDurability(EquipmentSlotType slotIn) {
            return MAX_DAMAGE_ARRAY[slotIn.getIndex()] * this.maxDamageFactor;
        }

        @Override
        public int getDamageReductionAmount(EquipmentSlotType slotIn) {
            return DamageReductionAmountArray[slotIn.getIndex()];
        }

        @Override
        public int getEnchantability() {
            return enchantability;
        }

        @Override
        public SoundEvent getSoundEvent() {
            return soundEvent;
        }

        @Override
        public Ingredient getRepairMaterial() {
            return repairMaterial.getValue();
        }

        @Override
        @OnlyIn(Dist.CLIENT)
        public String getName() {
            return name;
        }

        @Override
        public float getToughness() {
            return this.toughness;
        }
    }
}
