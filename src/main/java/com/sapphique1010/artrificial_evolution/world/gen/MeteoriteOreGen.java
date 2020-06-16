package com.sapphique1010.artrificial_evolution.world.gen;

import com.sapphique1010.artrificial_evolution.init.BlockInit;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.placement.ConfiguredPlacement;
import net.minecraft.world.gen.placement.CountRangeConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.registries.ForgeRegistries;

public class MeteoriteOreGen {
    public static void generateOre(){
        for(Biome biome: ForgeRegistries.BIOMES){
            ConfiguredPlacement customConfig = Placement.COUNT_RANGE
                    .configure(new CountRangeConfig(20,5,5,25));
            biome.addFeature(GenerationStage
                    .Decoration.UNDERGROUND_ORES, Feature.ORE.withConfiguration(
                            new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE,
                                    BlockInit.meteorite_ore.getDefaultState(),10)).withPlacement(customConfig));

        }
    }
}
