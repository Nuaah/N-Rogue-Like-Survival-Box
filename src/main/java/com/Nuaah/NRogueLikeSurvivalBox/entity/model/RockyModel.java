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

public class RockyModel<T extends Entity> extends HierarchicalModel<T> {

    private final ModelPart root;
    private final ModelPart rocky;
    private final ModelPart leg_right;
    private final ModelPart leg_left;
    private final ModelPart up;

    public RockyModel(ModelPart root) {
        this.root = root;
        this.rocky = root.getChild("rocky");
        this.leg_right = this.rocky.getChild("leg_right");
        this.leg_left = this.rocky.getChild("leg_left");
        this.up = this.rocky.getChild("up");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition rocky = partdefinition.addOrReplaceChild("rocky", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition leg_right = rocky.addOrReplaceChild("leg_right", CubeListBuilder.create().texOffs(32, 0).addBox(-1.0F, 0.5F, -1.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, -3.5F, 0.0F));

        PartDefinition leg_left = rocky.addOrReplaceChild("leg_left", CubeListBuilder.create().texOffs(24, 30).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0F, -3.0F, 0.0F));

        PartDefinition up = rocky.addOrReplaceChild("up", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -11.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(32, 5).addBox(-4.0F, -7.0F, -5.0F, 3.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 34).addBox(0.0F, -7.0F, 4.0F, 3.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(16, 36).addBox(-4.0F, -8.0F, 4.0F, 3.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(8, 39).addBox(-4.0F, -12.0F, 4.0F, 2.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(38, 26).addBox(-3.0F, -5.0F, 4.0F, 3.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(40, 4).addBox(-4.0F, -3.0F, 4.0F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(38, 38).addBox(1.0F, -9.0F, 4.0F, 3.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(40, 6).addBox(2.0F, -12.0F, 4.0F, 2.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(40, 16).addBox(0.0F, -12.0F, 4.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 39).addBox(-2.0F, -10.0F, 4.0F, 3.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(32, 16).addBox(-5.0F, -7.0F, -4.0F, 1.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(22, 23).addBox(-5.0F, -12.0F, -4.0F, 1.0F, 4.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(30, 23).addBox(-5.0F, -5.0F, -3.0F, 1.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(40, 0).addBox(-5.0F, -4.0F, 2.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 16).addBox(-5.0F, -7.0F, 0.0F, 1.0F, 3.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(32, 38).addBox(-5.0F, -12.0F, 0.0F, 1.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(32, 29).addBox(-5.0F, -11.0F, 2.0F, 1.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(12, 23).addBox(-5.0F, -9.0F, -1.0F, 1.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(32, 34).addBox(0.0F, -5.0F, -5.0F, 3.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(8, 35).addBox(1.0F, -8.0F, -5.0F, 3.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(32, 10).addBox(-3.0F, -9.0F, -5.0F, 4.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(14, 40).addBox(3.0F, -10.0F, -5.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(12, 16).addBox(4.0F, -6.0F, -5.0F, 1.0F, 3.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(22, 16).addBox(4.0F, -5.0F, -1.0F, 1.0F, 3.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(24, 39).addBox(4.0F, -6.0F, 3.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(38, 21).addBox(4.0F, -8.0F, 0.0F, 1.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(8, 29).addBox(4.0F, -10.0F, 2.0F, 1.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(16, 30).addBox(4.0F, -12.0F, -1.0F, 1.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(24, 35).addBox(4.0F, -7.0F, -3.0F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(0, 28).addBox(4.0F, -10.0F, -4.0F, 1.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(0, 24).addBox(-1.0F, -12.0F, -5.0F, 4.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(32, 13).addBox(-5.0F, -11.0F, -5.0F, 4.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    @Override
    public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        this.animateWalk(AnimationDefinitions.ROCKY_WALK, limbSwing, limbSwingAmount, 10.0F, 40.0F);
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        rocky.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }

    @Override
    public ModelPart root() {
        return this.root;
    }
}