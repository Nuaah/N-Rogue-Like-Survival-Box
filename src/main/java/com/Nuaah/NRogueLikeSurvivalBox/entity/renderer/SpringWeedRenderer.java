package com.Nuaah.NRogueLikeSurvivalBox.entity.renderer;

import com.Nuaah.NRogueLikeSurvivalBox.entity.custom.RockyEntity;
import com.Nuaah.NRogueLikeSurvivalBox.entity.custom.SpringWeedEntity;
import com.Nuaah.NRogueLikeSurvivalBox.entity.model.RockyModel;
import com.Nuaah.NRogueLikeSurvivalBox.entity.model.SpringWeedModel;
import com.Nuaah.NRogueLikeSurvivalBox.main.NRogueLikeSurvivalBox;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

@SuppressWarnings("removal")
public class SpringWeedRenderer extends MobRenderer<SpringWeedEntity, SpringWeedModel<SpringWeedEntity>> {
    public SpringWeedRenderer(EntityRendererProvider.Context context) {
        super(context,new SpringWeedModel<>(context.bakeLayer(NRogueLikeSurvivalBoxRenderers.SPRING_WEED_LAYER)), 0.5F);
    }

    @Override
    public ResourceLocation getTextureLocation(SpringWeedEntity p_114482_) {
        return new ResourceLocation(NRogueLikeSurvivalBox.MOD_ID,"textures/entity/spring_weed.png");
    }
}
