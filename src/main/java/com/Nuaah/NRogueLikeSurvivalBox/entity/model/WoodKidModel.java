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

public class WoodKidModel<T extends Entity> extends HierarchicalModel<T> {

    private final ModelPart root;
    private final ModelPart wood_kid;
    private final ModelPart leg_right;
    private final ModelPart leg_left;
    private final ModelPart up;

    public WoodKidModel(ModelPart root) {
        this.root = root;
        this.wood_kid = root.getChild("wood_kid");
        this.leg_right = this.wood_kid.getChild("leg_right");
        this.leg_left = this.wood_kid.getChild("leg_left");
        this.up = this.wood_kid.getChild("up");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition wood_kid = partdefinition.addOrReplaceChild("wood_kid", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition leg_right = wood_kid.addOrReplaceChild("leg_right", CubeListBuilder.create().texOffs(38, 5).mirror().addBox(-1.0F, 0.5F, -1.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-2.0F, -3.5F, 0.0F));

        PartDefinition leg_left = wood_kid.addOrReplaceChild("leg_left", CubeListBuilder.create().texOffs(38, 0).mirror().addBox(-1.0F, 0.0F, -1.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(2.0F, -3.0F, 0.0F));

        PartDefinition up = wood_kid.addOrReplaceChild("up", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(-4.0F, -11.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(24, 28).mirror().addBox(4.0F, -12.0F, 2.0F, 1.0F, 9.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(18, 28).mirror().addBox(-5.0F, -13.0F, 2.0F, 1.0F, 10.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(0, 16).mirror().addBox(-5.0F, -14.0F, 0.0F, 1.0F, 12.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(12, 29).mirror().addBox(-5.0F, -12.0F, -2.0F, 1.0F, 9.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(12, 16).mirror().addBox(-5.0F, -13.0F, -4.0F, 1.0F, 11.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(18, 16).mirror().addBox(2.0F, -13.0F, 4.0F, 2.0F, 11.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(36, 30).mirror().addBox(2.0F, -11.0F, -5.0F, 2.0F, 8.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(0, 30).mirror().addBox(0.0F, -12.0F, -5.0F, 2.0F, 10.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(30, 16).mirror().addBox(-2.0F, -13.0F, -5.0F, 2.0F, 10.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(36, 20).mirror().addBox(-4.0F, -11.0F, -5.0F, 2.0F, 9.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(24, 16).mirror().addBox(-2.0F, -13.0F, 4.0F, 2.0F, 11.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(36, 10).mirror().addBox(0.0F, -12.0F, 4.0F, 2.0F, 9.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(6, 29).mirror().addBox(-4.0F, -14.0F, 4.0F, 2.0F, 10.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(6, 16).mirror().addBox(4.0F, -13.0F, 0.0F, 1.0F, 11.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(30, 27).mirror().addBox(4.0F, -11.0F, -2.0F, 1.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(32, 0).mirror().addBox(4.0F, -12.0F, -4.0F, 1.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    @Override
    public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        this.animateWalk(AnimationDefinitions.WOOD_KID_WALK, limbSwing, limbSwingAmount, 10.0F, 40.0F);
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        wood_kid.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }

    @Override
    public ModelPart root() {
        return this.root;
    }
}