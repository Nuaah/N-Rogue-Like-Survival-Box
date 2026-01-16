package com.Nuaah.NRogueLikeSurvivalBox.entity.renderer;

import com.Nuaah.NRogueLikeSurvivalBox.entity.custom.WoodKidEntity;
import com.Nuaah.NRogueLikeSurvivalBox.entity.model.WoodKidModel;
import com.Nuaah.NRogueLikeSurvivalBox.main.NRogueLikeSurvivalBox;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

@SuppressWarnings("removal")
public class WoodKidRenderer extends MobRenderer<WoodKidEntity, WoodKidModel<WoodKidEntity>> {
    public WoodKidRenderer(EntityRendererProvider.Context context) {
        super(context,new WoodKidModel<>(context.bakeLayer(NRogueLikeSurvivalBoxRenderers.WOOD_KID_LAYER)), 0.5F);
    }

    @Override
    public ResourceLocation getTextureLocation(WoodKidEntity p_114482_) {
        return new ResourceLocation(NRogueLikeSurvivalBox.MOD_ID,"textures/entity/wood_kid.png");
    }
}
