package com.Nuaah.NRogueLikeSurvivalBox.regi.datagen;

import com.Nuaah.NRogueLikeSurvivalBox.entity.NRogueLikeSurvivalBoxEntities;
import net.minecraft.data.loot.EntityLootSubProvider;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import java.util.stream.Stream;

public class ModEntityLootTables extends EntityLootSubProvider {
    protected ModEntityLootTables() {
        super(FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    public void generate() {
        this.add(NRogueLikeSurvivalBoxEntities.WOOD_KID.get(),
            LootTable.lootTable()
            .withPool(LootPool.lootPool()
            .setRolls(ConstantValue.exactly(1.0F))
            .add(LootItem.lootTableItem(Items.STICK)
            .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F))))
            )
        );

        this.add(NRogueLikeSurvivalBoxEntities.ROCKY.get(),
            LootTable.lootTable()
            .withPool(LootPool.lootPool()
            .setRolls(ConstantValue.exactly(1.0F))
            .add(LootItem.lootTableItem(Blocks.COBBLESTONE)
            .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F))))
            )
        );

        this.add(NRogueLikeSurvivalBoxEntities.DOODUS.get(),
            LootTable.lootTable()
            .withPool(LootPool.lootPool()
            .setRolls(ConstantValue.exactly(1.0F))
            .add(LootItem.lootTableItem(Items.GREEN_DYE)
            .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F))))
            )
        );

        this.add(NRogueLikeSurvivalBoxEntities.DOODUS.get(),
            LootTable.lootTable()
            .withPool(LootPool.lootPool()
            .setRolls(ConstantValue.exactly(0.2F))
            .add(LootItem.lootTableItem(Blocks.CACTUS)
            .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 1.0F))))
            )
        );

        this.add(NRogueLikeSurvivalBoxEntities.SPRING_WEED.get(),
            LootTable.lootTable()
            .withPool(LootPool.lootPool()
            .setRolls(ConstantValue.exactly(1.0F))
            .add(LootItem.lootTableItem(Blocks.ACACIA_PLANKS)
            .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))))
            )
        );

        this.add(NRogueLikeSurvivalBoxEntities.SPRING_WEED.get(),
            LootTable.lootTable()
            .withPool(LootPool.lootPool()
            .setRolls(ConstantValue.exactly(0.5F))
            .add(LootItem.lootTableItem(Blocks.ACACIA_LEAVES)
            .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 1.0F))))
            )
        );
    }

    @Override
    protected Stream<EntityType<?>> getKnownEntityTypes() {
        // ここに登録したエンティティを全て並べる（これをもとにJSONが生成される）
        return Stream.of(
                NRogueLikeSurvivalBoxEntities.WOOD_KID.get()
        );
    }
}
