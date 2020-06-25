package com.sapphique1010.artrificial_evolution;

import com.sapphique1010.artrificial_evolution.init.ModContainerTypes;
import com.sapphique1010.artrificial_evolution.init.ItemInit;
import com.sapphique1010.artrificial_evolution.init.ModEntityType;
import com.sapphique1010.artrificial_evolution.world.gen.MeteoriteOreGen;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("artificial_evolution")
@Mod.EventBusSubscriber(modid = ArtificialEvolution.MOD_ID, bus = Bus.MOD)
public class ArtificialEvolution
{
    // Directly reference a log4j logger.
    public static final Logger LOGGER = LogManager.getLogger();
    public static final String MOD_ID ="artificial_evolution";
    public static ArtificialEvolution instance;

    public ArtificialEvolution() {
        instance = this;
        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::setup);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
        ModEntityType.ITEMS.register(modEventBus);
        ModEntityType.ENTITY_TYPES.register(modEventBus);
//        ModContainerTypes.CONTAINER_TYPE.register(modEventBus);

        modEventBus.addListener(this::doClientStuff);
    }

    private void setup(final FMLCommonSetupEvent event) {    }

    private void doClientStuff(final FMLClientSetupEvent event){ }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(FMLServerStartingEvent event){ }

    @SubscribeEvent
    public static void LoadCompleteEvent(FMLLoadCompleteEvent event){
        MeteoriteOreGen.generateOre();
    }
    public static class EvoItemGroup extends ItemGroup {
        public static final EvoItemGroup instance = new EvoItemGroup(ItemGroup.GROUPS.length,"evo_tab");
        private EvoItemGroup(int index, String label){
            super(index, label);
        }
        @Override
        public ItemStack createIcon() {
            return new ItemStack(ItemInit.syringe);
        }
    }


    public static class EldritchItemGroup extends ItemGroup {
        public static final EldritchItemGroup instance = new EldritchItemGroup(ItemGroup.GROUPS.length,"eld_tab");
        private EldritchItemGroup(int index, String label){
            super(index, label);
        }
        @Override
        public ItemStack createIcon() {
            return new ItemStack(ItemInit.blood_sword);
        }
    }
    public static class AscendantItemGroup extends ItemGroup {
        public static final AscendantItemGroup instance = new AscendantItemGroup(ItemGroup.GROUPS.length,"asc_tab");
        private AscendantItemGroup(int index, String label){
            super(index, label);
        }
        @Override
        public ItemStack createIcon() {
            return new ItemStack(Items.BEACON);
        }
    }
}
