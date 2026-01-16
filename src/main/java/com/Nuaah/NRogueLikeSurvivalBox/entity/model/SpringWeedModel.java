package com.Nuaah.NRogueLikeSurvivalBox.entity.model;// Made with Blockbench 5.0.7
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.world.entity.Entity;

public class SpringWeedModel<T extends Entity> extends HierarchicalModel<T> {

    private final ModelPart spring_weed;
    private final ModelPart body;

    public SpringWeedModel(ModelPart root) {
        this.spring_weed = root.getChild("spring_weed");
        this.body = this.spring_weed.getChild("body");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition spring_weed = partdefinition.addOrReplaceChild("spring_weed", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition body = spring_weed.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-7.0F, -14.0F, -7.0F, 14.0F, 14.0F, 14.0F, new CubeDeformation(0.0F))
                .texOffs(12, 48).addBox(2.0F, -11.0F, -8.0F, 6.0F, 5.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(54, 40).addBox(-7.0F, -7.0F, -8.0F, 3.0F, 5.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(40, 40).addBox(-8.0F, -5.0F, -3.0F, 1.0F, 5.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(0, 48).addBox(-8.0F, -15.0F, 3.0F, 1.0F, 5.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(36, 51).addBox(-7.0F, -13.0F, 7.0F, 5.0F, 5.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(12, 54).addBox(0.0F, -3.0F, 7.0F, 5.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(56, 0).addBox(5.0F, -15.0F, 7.0F, 3.0F, 5.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(40, 28).addBox(7.0F, -7.0F, 1.0F, 1.0F, 5.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(26, 48).addBox(7.0F, -15.0F, -6.0F, 1.0F, 5.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(48, 51).addBox(7.0F, -4.0F, -8.0F, 1.0F, 3.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(0, 28).addBox(-5.0F, -12.0F, -5.0F, 10.0F, 10.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    @Override
    public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        spring_weed.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }

    @Override
    public ModelPart root() {
        return spring_weed;
    }
}