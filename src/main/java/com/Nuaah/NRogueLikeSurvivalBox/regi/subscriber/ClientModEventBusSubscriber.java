package com.Nuaah.NRogueLikeSurvivalBox.regi.subscriber;

import com.Nuaah.NRogueLikeSurvivalBox.entity.NRogueLikeSurvivalBoxEntities;
import com.Nuaah.NRogueLikeSurvivalBox.entity.model.DoodusModel;
import com.Nuaah.NRogueLikeSurvivalBox.entity.model.RockyModel;
import com.Nuaah.NRogueLikeSurvivalBox.entity.model.SpringWeedModel;
import com.Nuaah.NRogueLikeSurvivalBox.entity.model.WoodKidModel;
import com.Nuaah.NRogueLikeSurvivalBox.entity.renderer.*;
import com.Nuaah.NRogueLikeSurvivalBox.gui.container.NRogueLikeSurvivalBoxContainerTypes;
import com.Nuaah.NRogueLikeSurvivalBox.gui.screen.StatusPuzzleScreen;
import com.Nuaah.NRogueLikeSurvivalBox.main.NRogueLikeSurvivalBox;
import com.Nuaah.NRogueLikeSurvivalBox.regi.DeathScreenHandler;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = NRogueLikeSurvivalBox.MOD_ID,bus = Mod.EventBusSubscriber.Bus.MOD,value = Dist.CLIENT)
public class ClientModEventBusSubscriber {

    @SubscribeEvent
    public static void registerLayer(EntityRenderersEvent.RegisterLayerDefinitions event){
        event.registerLayerDefinition(NRogueLikeSurvivalBoxRenderers.WOOD_KID_LAYER, WoodKidModel::createBodyLayer);
        event.registerLayerDefinition(NRogueLikeSurvivalBoxRenderers.ROCKY_LAYER, RockyModel::createBodyLayer);
        event.registerLayerDefinition(NRogueLikeSurvivalBoxRenderers.DOODUS_LAYER, DoodusModel::createBodyLayer);
        event.registerLayerDefinition(NRogueLikeSurvivalBoxRenderers.SPRING_WEED_LAYER, SpringWeedModel::createBodyLayer);
    }

    @SubscribeEvent
    public static void onRegisterRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(NRogueLikeSurvivalBoxEntities.CACTUS_NEEDLE_PROJECTILE.get(), ThrownItemRenderer::new);
    }

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event){
        EntityRenderers.register(NRogueLikeSurvivalBoxEntities.WOOD_KID.get(), WoodKidRenderer::new);
        EntityRenderers.register(NRogueLikeSurvivalBoxEntities.ROCKY.get(), RockyRenderer::new);
        EntityRenderers.register(NRogueLikeSurvivalBoxEntities.DOODUS.get(), DoodusRenderer::new);
        EntityRenderers.register(NRogueLikeSurvivalBoxEntities.SPRING_WEED.get(), SpringWeedRenderer::new);

        MenuScreens.register(NRogueLikeSurvivalBoxContainerTypes.STATUS_PUZZLE_MENU.get(), StatusPuzzleScreen::new);
        MinecraftForge.EVENT_BUS.register(new DeathScreenHandler());
    }
}
