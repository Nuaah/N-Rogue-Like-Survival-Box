package com.Nuaah.NRogueLikeSurvivalBox.main;

import com.Nuaah.NRogueLikeSurvivalBox.entity.NRogueLikeSurvivalBoxEntities;
import com.Nuaah.NRogueLikeSurvivalBox.gui.container.NRogueLikeSurvivalBoxContainerTypes;
import com.Nuaah.NRogueLikeSurvivalBox.regi.NRogueLikeSurvivalBoxBlocks;
import com.Nuaah.NRogueLikeSurvivalBox.regi.NRogueLikeSurvivalBoxItems;
import com.Nuaah.NRogueLikeSurvivalBox.regi.net.NetworkHandler;
import com.Nuaah.NRogueLikeSurvivalBox.regi.tab.NRogueLikeSurvivalBoxTabs;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod("nroguelikesurvivalbox")
@SuppressWarnings("removal")
public class NRogueLikeSurvivalBox {

    public static final String MOD_ID = "nroguelikesurvivalbox";

    public NRogueLikeSurvivalBox(){
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        NRogueLikeSurvivalBoxBlocks.Blocks.BLOCKS.register(bus);
        NRogueLikeSurvivalBoxBlocks.BlockItems.BLOCK_ITEMS.register(bus);
        NRogueLikeSurvivalBoxItems.ITEMS.register(bus);
        NRogueLikeSurvivalBoxTabs.MOD_TABS.register(bus);
        NRogueLikeSurvivalBoxContainerTypes.MENU_TYPES.register(bus);
        NRogueLikeSurvivalBoxEntities.ENTITY_TYPES.register(bus);

        bus.addListener(this::commonSetup);
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(NetworkHandler::register);
    }
}
