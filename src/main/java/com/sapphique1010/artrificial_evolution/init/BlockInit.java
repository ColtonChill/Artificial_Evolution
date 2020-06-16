package com.sapphique1010.artrificial_evolution.init;

import com.sapphique1010.artrificial_evolution.ArtificialEvolution;
import com.sapphique1010.artrificial_evolution.ArtificialEvolution.*;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ObjectHolder;

@ObjectHolder(ArtificialEvolution.MOD_ID)
@Mod.EventBusSubscriber(modid = ArtificialEvolution.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class BlockInit {
    public static final Block centrifuge = null;
    public static final Block meteorite_ore = null;

    @SubscribeEvent
    public static void registerBlocks(final RegistryEvent.Register<Block> event){
        event.getRegistry().register(new Block(Block.Properties.create(Material.IRON)
                .hardnessAndResistance(1.5f,10.0f)
                .sound(SoundType.ANVIL))
                .setRegistryName("centrifuge"));
        event.getRegistry().register(new Block(Block.Properties.create(Material.ROCK)
                .hardnessAndResistance(50,1200)
                .sound(SoundType.STONE))
                .setRegistryName("meteorite_ore"));
    }
    @SubscribeEvent
    public static void registerBlockItems(final RegistryEvent.Register<Item> event) {
        event.getRegistry().register(new BlockItem(centrifuge, new Item.Properties()
                .group(EvoItemGroup.instance)
                .maxStackSize(1))
                .setRegistryName("centrifuge"));
        event.getRegistry().register(new BlockItem(meteorite_ore,new Item.Properties()
                .group(AscendantItemGroup.instance))
                .setRegistryName("meteorite_ore"));
    }
}
