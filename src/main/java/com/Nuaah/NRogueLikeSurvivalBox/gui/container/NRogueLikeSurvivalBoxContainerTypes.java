package com.Nuaah.NRogueLikeSurvivalBox.gui.container;

import com.Nuaah.NRogueLikeSurvivalBox.main.NRogueLikeSurvivalBox;
import net.minecraft.core.BlockPos;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class NRogueLikeSurvivalBoxContainerTypes {

    public static final DeferredRegister<MenuType<?>> MENU_TYPES =
            DeferredRegister.create(ForgeRegistries.MENU_TYPES, NRogueLikeSurvivalBox.MOD_ID);

    public static final RegistryObject<MenuType<StatusPuzzleManu>> STATUS_PUZZLE_MENU =
            MENU_TYPES.register("status_puzzle_menu", () -> IForgeMenuType.create(StatusPuzzleManu::new));
}
