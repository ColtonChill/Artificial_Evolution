package com.sapphique1010.artrificial_evolution.util;

import com.sapphique1010.artrificial_evolution.ArtificialEvolution;
import com.sapphique1010.artrificial_evolution.client.entity.render.FoxWolfEntityRender;
import com.sapphique1010.artrificial_evolution.init.ModEntityType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import javax.swing.*;

@Mod.EventBusSubscriber(modid = ArtificialEvolution.MOD_ID, bus= Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEventBusSubscriber {

    @SubscribeEvent
    public static void clientSetup(FMLClientSetupEvent event){
        RenderingRegistry.registerEntityRenderingHandler(ModEntityType.FOXWOLF_ENTITY.get(), FoxWolfEntityRender::new);
    }
}
