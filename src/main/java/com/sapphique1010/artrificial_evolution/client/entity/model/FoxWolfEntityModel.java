package com.sapphique1010.artrificial_evolution.client.entity.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.sapphique1010.artrificial_evolution.entities.FoxWolfEntity;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;

import java.util.function.Consumer;

public class FoxWolfEntityModel<T extends FoxWolfEntity>  extends EntityModel<T> {
    private final ModelRenderer Body;

    public FoxWolfEntityModel() {
        textureWidth = 64;
        textureHeight = 32;

        Body = new ModelRenderer(this);
        Body.setRotationPoint(0.0F, 24.0F, 0.0F);


        ModelRenderer head = new ModelRenderer(this);
        head.setRotationPoint(0.0F, -8.0F, -4.0F);
        Body.addChild(head);


        ModelRenderer face = new ModelRenderer(this);
        face.setRotationPoint(0.0F, 0.0F, 0.0F);
        head.addChild(face);
        face.setTextureOffset(0, 0).addBox(-3.5F, -4.0F, -4.0F, 7.0F, 6.0F, 5.0F, 0.0F, false);

        ModelRenderer leftEar = new ModelRenderer(this);
        leftEar.setRotationPoint(0.0F, 8.0F, 4.0F);
        head.addChild(leftEar);
        leftEar.setTextureOffset(20, 14).addBox(1.5F, -14.0F, -6.0F, 2.0F, 2.0F, 1.0F, 0.0F, false);

        ModelRenderer rightEar = new ModelRenderer(this);
        rightEar.setRotationPoint(0.0F, 0.0F, 1.0F);
        head.addChild(rightEar);
        rightEar.setTextureOffset(14, 14).addBox(-3.5F, -6.0F, -3.0F, 2.0F, 2.0F, 1.0F, 0.0F, false);

        ModelRenderer nose = new ModelRenderer(this);
        nose.setRotationPoint(0.0F, 8.0F, 4.0F);
        head.addChild(nose);
        nose.setTextureOffset(0, 10).addBox(-1.5F, -8.0F, -11.0F, 3.0F, 2.0F, 4.0F, 0.0F, false);

        ModelRenderer torso = new ModelRenderer(this);
        torso.setRotationPoint(0.0F, -8.0F, 4.0F);
        Body.addChild(torso);


        ModelRenderer mane = new ModelRenderer(this);
        mane.setRotationPoint(0.0F, -1.0F, -4.0F);
        torso.addChild(mane);
        mane.setTextureOffset(21, 0).addBox(-4.5F, -4.0F, -3.0F, 9.0F, 7.0F, 4.0F, 0.0F, false);

        ModelRenderer chest = new ModelRenderer(this);
        chest.setRotationPoint(0.0F, -1.0F, 1.0F);
        torso.addChild(chest);
        chest.setTextureOffset(24, 13).addBox(-3.0F, -3.0F, -4.0F, 6.0F, 6.0F, 9.0F, 0.0F, false);

        ModelRenderer tail = new ModelRenderer(this);
        tail.setRotationPoint(0.0F, -11.0F, 10.0F);
        Body.addChild(tail);
        tail.setTextureOffset(9, 18).addBox(-1.5F, -1.0F, -2.0F, 3.0F, 7.0F, 4.0F, 0.0F, false);

        ModelRenderer legs = new ModelRenderer(this);
        legs.setRotationPoint(0.0F, 0.0F, 0.0F);
        Body.addChild(legs);


        ModelRenderer frontRight = new ModelRenderer(this);
        frontRight.setRotationPoint(-1.5F, -6.0F, -1.0F);
        legs.addChild(frontRight);
        frontRight.setTextureOffset(0, 18).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 7.0F, 2.0F, 0.0F, false);

        ModelRenderer backRight = new ModelRenderer(this);
        backRight.setRotationPoint(-1.5F, -6.0F, 8.0F);
        legs.addChild(backRight);
        backRight.setTextureOffset(0, 18).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 7.0F, 2.0F, 0.0F, false);

        ModelRenderer frontLeft = new ModelRenderer(this);
        frontLeft.setRotationPoint(1.5F, -6.0F, -1.0F);
        legs.addChild(frontLeft);
        frontLeft.setTextureOffset(0, 18).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 7.0F, 2.0F, 0.0F, false);

        ModelRenderer backLeft = new ModelRenderer(this);
        backLeft.setRotationPoint(1.5F, -6.0F, 8.0F);
        legs.addChild(backLeft);
        backLeft.setTextureOffset(0, 18).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 7.0F, 2.0F, 0.0F, false);
    }

    @Override
    public void setRotationAngles(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch){
        //previously the render function, render code was moved to a method below
    }

    @Override
    public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
        Body.render(matrixStack, buffer, packedLight, packedOverlay);
    }

    @Override
    public void setLivingAnimations(T entityIn, float limbSwing, float limbSwingAmount, float partialTick) {
        super.setLivingAnimations(entityIn, limbSwing, limbSwingAmount, partialTick);
    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}