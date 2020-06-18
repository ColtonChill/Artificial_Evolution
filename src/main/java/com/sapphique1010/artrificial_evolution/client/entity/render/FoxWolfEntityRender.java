package com.sapphique1010.artrificial_evolution.client.entity.render;

import com.sapphique1010.artrificial_evolution.ArtificialEvolution;
import com.sapphique1010.artrificial_evolution.client.entity.model.FoxWolfEntityModel;
import com.sapphique1010.artrificial_evolution.entities.FoxWolfEntity;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;

public class FoxWolfEntityRender extends MobRenderer<FoxWolfEntity, FoxWolfEntityModel<FoxWolfEntity>> {
    protected static final ResourceLocation TEXTURE = new ResourceLocation(ArtificialEvolution.MOD_ID,"textures/entity/fox_wolf_entity.png");

    public FoxWolfEntityRender(EntityRendererManager rendererManager){
        super(rendererManager, new FoxWolfEntityModel<>(), 0.5f);
    }

    /**
     * Returns the location of an entity's texture.
     *
     * @param entity
     */
    @Override
    public ResourceLocation getEntityTexture(FoxWolfEntity entity) {
        return TEXTURE;
    }
}
