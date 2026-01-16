package com.Nuaah.NRogueLikeSurvivalBox.entity.renderer;

import com.Nuaah.NRogueLikeSurvivalBox.entity.custom.DoodusEntity;
import com.Nuaah.NRogueLikeSurvivalBox.entity.custom.RockyEntity;
import com.Nuaah.NRogueLikeSurvivalBox.entity.model.DoodusModel;
import com.Nuaah.NRogueLikeSurvivalBox.entity.model.RockyModel;
import com.Nuaah.NRogueLikeSurvivalBox.main.NRogueLikeSurvivalBox;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

@SuppressWarnings("removal")
public class DoodusRenderer extends MobRenderer<DoodusEntity, DoodusModel<DoodusEntity>> {
    public DoodusRenderer(EntityRendererProvider.Context context) {
        super(context,new DoodusModel<>(context.bakeLayer(NRogueLikeSurvivalBoxRenderers.DOODUS_LAYER)), 0.5F);
    }

    @Override
    public ResourceLocation getTextureLocation(DoodusEntity p_114482_) {
        return new ResourceLocation(NRogueLikeSurvivalBox.MOD_ID,"textures/entity/doodus.png");
    }
}
