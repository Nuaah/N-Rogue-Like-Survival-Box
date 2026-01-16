package com.Nuaah.NRogueLikeSurvivalBox.regi;

import com.Nuaah.NRogueLikeSurvivalBox.main.NRogueLikeSurvivalBox;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.PushReaction;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class NRogueLikeSurvivalBoxBlocks {

    public static class Blocks{
        public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, NRogueLikeSurvivalBox.MOD_ID);
//        public static final RegistryObject<Block> MILLSTONE = BLOCKS.register("millstone", BlockMillstone::new);
    }

    public static class BlockItems{
        public static final DeferredRegister<Item> BLOCK_ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, NRogueLikeSurvivalBox.MOD_ID);
//        public static final RegistryObject<Item> BELLFLOWER = BLOCK_ITEMS.register("bellflower",
//                () -> new BlockItem(Blocks.BELLFLOWER.get(),new Item.Properties()));
    }
}
