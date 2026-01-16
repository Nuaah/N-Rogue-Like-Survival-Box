package com.Nuaah.NRogueLikeSurvivalBox.entity.renderer;

import com.Nuaah.NRogueLikeSurvivalBox.entity.custom.RockyEntity;
import com.Nuaah.NRogueLikeSurvivalBox.entity.model.RockyModel;
import com.Nuaah.NRogueLikeSurvivalBox.entity.model.WoodKidModel;
import com.Nuaah.NRogueLikeSurvivalBox.main.NRogueLikeSurvivalBox;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

@SuppressWarnings("removal")
public class RockyRenderer extends MobRenderer<RockyEntity, RockyModel<RockyEntity>> {
    public RockyRenderer(EntityRendererProvider.Context context) {
        super(context,new RockyModel<>(context.bakeLayer(NRogueLikeSurvivalBoxRenderers.ROCKY_LAYER)), 0.5F);
    }

    @Override
    public ResourceLocation getTextureLocation(RockyEntity p_114482_) {
        return new ResourceLocation(NRogueLikeSurvivalBox.MOD_ID,"textures/entity/rocky.png");
    }
}
