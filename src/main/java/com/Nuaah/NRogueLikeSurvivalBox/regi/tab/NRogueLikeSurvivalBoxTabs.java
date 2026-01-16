package com.Nuaah.NRogueLikeSurvivalBox.regi.tab;

import com.Nuaah.NRogueLikeSurvivalBox.main.NRogueLikeSurvivalBox;
import com.Nuaah.NRogueLikeSurvivalBox.regi.NRogueLikeSurvivalBoxBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class NRogueLikeSurvivalBoxTabs {

    public static final DeferredRegister<CreativeModeTab> MOD_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, NRogueLikeSurvivalBox.MOD_ID);

    public static final RegistryObject<CreativeModeTab> NROGUELIKESURVIVALBOX_MAIN = MOD_TABS.register("nroguelikesurvivalbox_main",
        () -> {return CreativeModeTab.builder()
            .icon(() -> new ItemStack(Items.TNT))
            .title(Component.translatable("itemGroup.NRogueLikeSurvivalBoxMain"))
            .displayItems((param,output) -> {
                for(Item item : NRogueLikeSurvivalBoxMain.items){
                    output.accept(item);
                }
            })
            .build();
        });
}
