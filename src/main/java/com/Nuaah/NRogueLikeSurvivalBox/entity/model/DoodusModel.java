package com.Nuaah.NRogueLikeSurvivalBox.entity.model;// Made with Blockbench 5.0.7
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.Nuaah.NRogueLikeSurvivalBox.entity.animations.AnimationDefinitions;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.world.entity.Entity;

public class DoodusModel<T extends Entity> extends HierarchicalModel<T> {

    private final ModelPart doodus;
    private final ModelPart body;

    public DoodusModel(ModelPart root) {
        this.doodus = root.getChild("doodus");
        this.body = this.doodus.getChild("body");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition doodus = partdefinition.addOrReplaceChild("doodus", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition body = doodus.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -32.0F, -4.0F, 8.0F, 32.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(32, 0).addBox(5.0F, -30.0F, -1.0F, 3.0F, 14.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(32, 17).addBox(-8.0F, -23.0F, -1.0F, 3.0F, 12.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(32, 32).addBox(-5.0F, -15.0F, -1.0F, 1.0F, 4.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(32, 39).addBox(4.0F, -20.0F, -1.0F, 1.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    @Override
    public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        doodus.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }

    @Override
    public ModelPart root() {
        return doodus;
    }
}